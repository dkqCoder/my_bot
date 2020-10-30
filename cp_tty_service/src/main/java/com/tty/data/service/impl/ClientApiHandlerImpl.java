package com.tty.data.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.ehcache.EhcacheManager;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.common.utils.ScoreUtil;
import com.tty.data.common.bean.JCOptimize;
import com.tty.data.common.util.MathUtil;
import com.tty.data.context.BasedataContext;
import com.tty.data.context.ResponseCodeAndMsg;
import com.tty.data.controller.api.vo.ClientBonusOptimizeBetInfoVO;
import com.tty.data.controller.api.vo.ClientBonusOptimizeParams;
import com.tty.data.controller.api.vo.ClientJczqDggpMatchVO;
import com.tty.data.controller.vo.ClientForecastInfoVo;
import com.tty.data.controller.vo.ClientJclqMatchResultVo;
import com.tty.data.controller.vo.ClientJclqMatchVO;
import com.tty.data.controller.vo.ClientJczqMatchResultVo;
import com.tty.data.controller.vo.ClientJczqMatchVO;
import com.tty.data.dao.entity.BasedataIssueENT;
import com.tty.data.dto.BasedataTeamInjureDTO;
import com.tty.data.dto.ClientDgMatchVO;
import com.tty.data.dto.JclqDataDTO;
import com.tty.data.dto.JczqDataDTO;
import com.tty.data.dto.redis.RedisJclqMatchDTO;
import com.tty.data.dto.redis.RedisJclqMatchStopSaleDTO;
import com.tty.data.dto.redis.RedisJczqDggpMatchDTO;
import com.tty.data.dto.redis.RedisJczqMatchDTO;
import com.tty.data.dto.redis.RedisJczqMatchStopSaleDTO;
import com.tty.data.dao.entity.BasedataDgMatchENT;
import com.tty.data.dto.redis.RedisLotteryDTO;
import com.tty.data.service.BasedataShardedRedisService;
import com.tty.data.service.ClientApiHandler;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * app端接口调用业务处理
 */
@Service("clientApiHandler")
public class ClientApiHandlerImpl implements ClientApiHandler {

    Logger logger = LoggerFactory.getLogger(ClientApiHandlerImpl.class);

    @Autowired
    private BasedataShardedRedisService basedataShareRedisService;

    @Override
    public ResultModel getJczq(String platformCode, String strAppVersion, String body) {
        ResultModel resultModel = new ResultModel();
        List data = new ArrayList();
        Date curDate = new Date();

        //1.从redis（basedata:client:match:jczq）获取期次对阵信息
        List<RedisJczqMatchDTO> jczqMatchIssueList = basedataShareRedisService.getJczqMatchIssueList();
        if (CollectionUtils.isEmpty(jczqMatchIssueList)) {//未取到，返回空
            resultModel.setCode(ResultModel.SUCCESS);
            resultModel.setData(new ArrayList());
            return resultModel;
        }

        //从redis（basedata:client:stopsale:jczq）中获取停售的比赛
        List<RedisJczqMatchStopSaleDTO> jczqStopSale = basedataShareRedisService.getJczqStopSale();

        //筛选出未超过停售时间的赛事，按期次排序
        jczqMatchIssueList = jczqMatchIssueList.stream().filter(x -> x.getEndTime().after(curDate)).sorted(new Comparator<RedisJczqMatchDTO>() {
            @Override
            public int compare(RedisJczqMatchDTO o1, RedisJczqMatchDTO o2) {
                return o1.getIssueName().compareTo(o2.getIssueName());
            }
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(jczqMatchIssueList)) {
            resultModel.setCode(ResultModel.SUCCESS);
            resultModel.setData(new ArrayList());
            return resultModel;
        }

        //分平台版本以上才控制玩法开停售
        // todo 玩法修改为不控制
        boolean isNewVersion = true;
        /*int appVersion = Integer.parseInt(String.valueOf(strAppVersion).replaceAll("\\.", ""));
        if ("IPHONE".equalsIgnoreCase(platformCode) && appVersion >= 377) {
            isNewVersion = true;
        } else if ("Android".equalsIgnoreCase(platformCode)) {
            isNewVersion = true;
        } else if ("pc".equalsIgnoreCase(platformCode)) {
            isNewVersion = true;
        } else if ("h5mobile".equalsIgnoreCase(platformCode)) {
            isNewVersion = true;
        } else {
            isNewVersion = false;
        }*/

        ArrayList<String> issueList = new ArrayList<>();
        for (RedisJczqMatchDTO redisJczqMatchDTO : jczqMatchIssueList) {
            if (issueList.contains(redisJczqMatchDTO.getIssueName())) {
                continue;
            }
            issueList.add(redisJczqMatchDTO.getIssueName());

            List<RedisJczqMatchDTO> matchs = jczqMatchIssueList.stream().filter(x -> x.getIssueName().equals(redisJczqMatchDTO.getIssueName())).sorted(new Comparator<RedisJczqMatchDTO>() {
                @Override
                public int compare(RedisJczqMatchDTO o1, RedisJczqMatchDTO o2) {
                    return o1.getIssueMatchName().compareTo(o2.getIssueMatchName());
                }
            }).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(matchs)) {
                continue;
            }

            Map issueMap = new HashMap();
            issueMap.put("issue", redisJczqMatchDTO.getIssueName());
            List matchResult = new ArrayList();

            for (RedisJczqMatchDTO match : matchs) {
                ClientJczqMatchVO clientJczqMatchVO = new ClientJczqMatchVO();
                clientJczqMatchVO.setHot(match.isHot());
                clientJczqMatchVO.setMatchId(match.getMatchId());
                clientJczqMatchVO.setmId(match.getIssueMatchName());
                clientJczqMatchVO.setMatchName(match.getMatchName());
                clientJczqMatchVO.setTournamentId(match.getTournamentId());
                clientJczqMatchVO.setEndTime(match.getEndTime());
                clientJczqMatchVO.setHostTeam(match.getHostTeam());
                clientJczqMatchVO.setVistorTeam(match.getVisitTeam());
                clientJczqMatchVO.setRq(String.valueOf(match.getRq()));
                clientJczqMatchVO.setDgSPFStatus(match.getDgspfStatus());
                clientJczqMatchVO.setDgRQSPFStatus(match.getDgrqspfStatus());
                clientJczqMatchVO.setDgZJQStatus(match.getDgzjqStatus());
                clientJczqMatchVO.setDgCBFStatus(1);
                clientJczqMatchVO.setDgBQCStatus(match.getDgbqcStatus());
                clientJczqMatchVO.setHostRank(match.getHostRank());
                clientJczqMatchVO.setHomeFieldRank(match.getHomeFieldRank());
                clientJczqMatchVO.setVistorRank(match.getVisitRank());
                clientJczqMatchVO.setAwayFieldRank(match.getAwayFieldRank());
                clientJczqMatchVO.setHaHistory(match.getFightHistory());
                clientJczqMatchVO.setHostHistory(match.getHostRecentHistory());
                clientJczqMatchVO.setVistorHistory(match.getVisitRecentHistory());
                clientJczqMatchVO.setAvgSp(match.getAverageSp());
                String[] psStatus = match.getPsStatus().split(",");
                if (psStatus.length == 5) { //玩法sp值 9001 让球胜平负，9002 总进球，9003 猜比分，9004 半全场，9006 胜平负
                    clientJczqMatchVO.setSpRQSPF(psStatus[0].equals("0") ? getJczqSp("0", 9001) : getJczqSp(match.getSprqs(), 9001));
                    clientJczqMatchVO.setSpZJQ(psStatus[1].equals("0") ? getJczqSp("0", 9002) : getJczqSp(match.getSpjqs(), 9002));
                    clientJczqMatchVO.setSpCBF(psStatus[2].equals("0") ? getJczqSp("0", 9003) : getJczqSp(match.getSpcbf(), 9003));
                    clientJczqMatchVO.setSpBQC(psStatus[3].equals("0") ? getJczqSp("0", 9004) : getJczqSp(match.getSpbqc(), 9004));
                    clientJczqMatchVO.setSpSPF(psStatus[4].equals("0") ? getJczqSp("0", 9006) : getJczqSp(match.getSpspf(), 9006));
                    StringBuffer newPsStatus = new StringBuffer();
                    if (psStatus[0].equals("0") && StringUtils.isBlank(match.getSprqs())) {
                        newPsStatus.append("2,");
                    } else {
                        newPsStatus.append(psStatus[0]);
                        newPsStatus.append(",");
                    }
                    if (psStatus[1].equals("0") && StringUtils.isBlank(match.getSpjqs())) {
                        newPsStatus.append("2,");
                    } else {
                        newPsStatus.append(psStatus[1]);
                        newPsStatus.append(",");
                    }

                    if (psStatus[2].equals("0") && StringUtils.isBlank(match.getSpcbf())) {
                        newPsStatus.append("2,");
                    } else {
                        newPsStatus.append(psStatus[2]);
                        newPsStatus.append(",");
                    }

                    if (psStatus[3].equals("0") && StringUtils.isBlank(match.getSpbqc())) {
                        newPsStatus.append("2,");
                    } else {
                        newPsStatus.append(psStatus[3]);
                        newPsStatus.append(",");
                    }

                    if (psStatus[4].equals("0") && StringUtils.isBlank(match.getSpspf())) {
                        newPsStatus.append("2");
                    } else {
                        newPsStatus.append(psStatus[4]);
                    }
                    clientJczqMatchVO.setPlayState(newPsStatus.toString());
                } else {
                    clientJczqMatchVO.setSpRQSPF(getJczqSp(match.getSprqs(), 9001));
                    clientJczqMatchVO.setSpZJQ(getJczqSp(match.getSpjqs(), 9002));
                    clientJczqMatchVO.setSpCBF(getJczqSp(match.getSpcbf(), 9003));
                    clientJczqMatchVO.setSpBQC(getJczqSp(match.getSpbqc(), 9004));
                    clientJczqMatchVO.setSpSPF(getJczqSp(match.getSpspf(), 9006));
                    clientJczqMatchVO.setPlayState(match.getPsStatus());
                }
                clientJczqMatchVO.setOptionSPF(match.getSpfOptionRate());
                clientJczqMatchVO.setOptionRQSPF(match.getRqspfOptionRate());
                clientJczqMatchVO.setBgColor(match.getBgColor());
                clientJczqMatchVO.setHostTeamId(match.getHostTeamId());
                clientJczqMatchVO.setVisitTeamId(match.getVisitTeamId());
                clientJczqMatchVO.setMatchStartTime(match.getMatchTime());

                if (isNewVersion) {

                    clientJczqMatchVO.setStatus(match.getHd());
                    List<RedisJczqMatchStopSaleDTO> collect = null;
                    if (CollectionUtils.isNotEmpty(jczqStopSale)) {
                        collect = jczqStopSale.stream().filter(x -> x.getIssueMatchName().equals(match.getIssueMatchName())).collect(Collectors.toList());
                    }
                    if (CollectionUtils.isEmpty(collect)) {
                        clientJczqMatchVO.setReason("");
                        clientJczqMatchVO.setPlayDetailState("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");
                    } else {
                        RedisJczqMatchStopSaleDTO rjmsst = collect.get(0);
                        clientJczqMatchVO.setReason(rjmsst.getReason());
                        clientJczqMatchVO.setPlayDetailState(rjmsst.getPlayDetailState());
                    }
                }
                matchResult.add(clientJczqMatchVO);
            }
            issueMap.put("MCount", matchResult.size());
            issueMap.put("Matches", matchResult);
            issueMap.put("Wk", redisJczqMatchDTO.getWeek());
            issueMap.put("CurrentTime", DateUtil.format(new Date()));
            data.add(issueMap);
        }

        resultModel.setCode(ResultModel.SUCCESS);
        resultModel.setData(data);
        return resultModel;
    }

    @Autowired
    private EhcacheManager ehcacheManager;

//    @Autowired
//    private AppCfgService dubboAppCfgService;

    /**
     * 玩法sp值 9001 让球胜平负，9002 总进球，9003 猜比分，9004 半全场，9006 胜平负
     */
    private String getJczqSp(String sp, int playId) {
        return ScoreUtil.getJczqSp(sp, playId, "|");
    }

    /**
     * 获取单关数据
     * @param header
     * @param body
     * @return
     */
    @Override
    public ResultModel getDgMatch(ClientRequestHeader header, Object body) {
        ResultModel resultModel = new ResultModel();
        //从redis（basedata:dgmatch:cfg）中获取赔率
        List<BasedataDgMatchENT> dgMatchList = basedataShareRedisService.getDgMatchList();

        if (CollectionUtils.isEmpty(dgMatchList)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("暂无数据");
            return resultModel;
        }

        Date nowDate = new Date();
        dgMatchList = dgMatchList.stream().filter(x -> x.getRecommEndTime().after(nowDate)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dgMatchList)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("暂无数据");
            return resultModel;
        }

        //获取可售期次(redis)
        List<RedisJczqMatchDTO> issueList = basedataShareRedisService.getJczqMatchIssueList();

        issueList = issueList.stream().filter(x -> x.getEndTime().after(nowDate)).collect(Collectors.toList());
        List<RedisJczqMatchDTO> issueRqList = issueList.stream().filter(x -> x.getRq().equals(1) || x.getRq().equals(-1)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(issueRqList)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("暂无可匹配的数据");
            return resultModel;
        }
        issueRqList.sort(new Comparator<RedisJczqMatchDTO>() {
            @Override
            public int compare(RedisJczqMatchDTO o1, RedisJczqMatchDTO o2) {
                return o1.getIssueMatchName().compareTo(o2.getIssueMatchName());
            }
        });
        ArrayList result = new ArrayList();
        //组装数据
        for (BasedataDgMatchENT basedataDgMatchENT : dgMatchList) {
            String issueMatchname = basedataDgMatchENT.getIssueMatchName();
            RedisJczqMatchDTO jczqMatchByIssue = getJczqMatchByIssue(issueList, issueMatchname);
            if (jczqMatchByIssue == null) {
                continue;
            }

            Map map = new HashMap();
            map.put("IssueName", jczqMatchByIssue.getIssueName());
            ClientDgMatchVO dgMatchParentVO = new ClientDgMatchVO();
            dgMatchParentVO.setMatchId(jczqMatchByIssue.getMatchId());
            dgMatchParentVO.setmId(Integer.parseInt(issueMatchname));
            dgMatchParentVO.setMatchName(jczqMatchByIssue.getMatchName());
            dgMatchParentVO.setEndTime(jczqMatchByIssue.getEndTime());
            dgMatchParentVO.setHostTeam(jczqMatchByIssue.getHostTeam());
            dgMatchParentVO.setVistorTeam(jczqMatchByIssue.getVisitTeam());
            dgMatchParentVO.setRq(String.valueOf(jczqMatchByIssue.getRq()));
            dgMatchParentVO.setSpSPF(getJczqSp(jczqMatchByIssue.getSpspf(), 9006));
            dgMatchParentVO.setSpRQSPF(getJczqSp(jczqMatchByIssue.getSprqs(), 9001));
            dgMatchParentVO.setSpZJQ(getJczqSp(jczqMatchByIssue.getSpjqs(), 9002));
            dgMatchParentVO.setSpCBF(getJczqSp(jczqMatchByIssue.getSpcbf(), 9003));
            dgMatchParentVO.setSpBQC(getJczqSp(jczqMatchByIssue.getSpbqc(), 9004));
            dgMatchParentVO.setImgUrl(basedataDgMatchENT.getBgImgUrl());
            dgMatchParentVO.setUse(basedataDgMatchENT.getStatus().equals(1));
            map.put("ParentName", dgMatchParentVO);

            RedisJczqMatchDTO childMatch = getDgMatchChild(issueRqList, issueMatchname);
            if (childMatch != null) {
                ClientDgMatchVO dgMatchChildVO = new ClientDgMatchVO();
                dgMatchChildVO.setMatchId(childMatch.getMatchId());
                dgMatchChildVO.setmId(Integer.parseInt(issueMatchname));
                dgMatchChildVO.setMatchName(childMatch.getMatchName());
                dgMatchChildVO.setEndTime(childMatch.getEndTime());
                dgMatchChildVO.setHostTeam(childMatch.getHostTeam());
                dgMatchChildVO.setVistorTeam(childMatch.getVisitTeam());
                dgMatchChildVO.setRq(String.valueOf(childMatch.getRq()));
                dgMatchChildVO.setSpSPF(getJczqSp(childMatch.getSpspf(), 9006));
                dgMatchChildVO.setSpRQSPF(getJczqSp(childMatch.getSprqs(), 9001));
                dgMatchChildVO.setSpZJQ(getJczqSp(childMatch.getSpjqs(), 9002));
                dgMatchChildVO.setSpCBF(getJczqSp(childMatch.getSpcbf(), 9003));
                dgMatchChildVO.setSpBQC(getJczqSp(childMatch.getSpbqc(), 9004));
                map.put("ChildName", dgMatchChildVO);
            } else {
                map.put("ChildName", null);
            }
            map.put("IsJoin", basedataDgMatchENT.getRecommEndTime().before(nowDate) ? "no" : "yes");
            map.put("CurrentTime", DateUtil.format(new Date()));
            result.add(map);
        }
        if (CollectionUtils.isEmpty(result)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("暂无数据");
            return resultModel;
        }
        resultModel.setCode(ResultModel.SUCCESS);
        resultModel.setData(result);
        return resultModel;
    }

    /**
     *
     * @param issueList
     * @param issueMatchName
     * @return
     */
    private RedisJczqMatchDTO getJczqMatchByIssue(List<RedisJczqMatchDTO> issueList, String issueMatchName) {
        for (RedisJczqMatchDTO redisJczqMatchDTO : issueList) {
            if (redisJczqMatchDTO.getIssueMatchName().equals(issueMatchName)) {
                return redisJczqMatchDTO;
            }
        }
        return null;
    }

    /**
     * 获取当前场次相邻的让1球的场次
     *
     * @param issueRqList
     * @param issueMatchName
     * @return
     */
    private RedisJczqMatchDTO getDgMatchChild(List<RedisJczqMatchDTO> issueRqList, String issueMatchName) {
        int index = -1;
        RedisJczqMatchDTO childMatch = null;
        for (int i = 0; i < issueRqList.size(); i++) {
            RedisJczqMatchDTO curMatch = issueRqList.get(i);
            if (curMatch.getIssueMatchName().equals(issueMatchName)) {
                index = i;
                continue;
            }
            if (index == -1 && index < issueRqList.size() - 1) {//当前场次是最后一个场次时取前一场次
                childMatch = curMatch;
                break;
            }
            if (index > -1) {//当前场次是第一个场次时取后一场次
                childMatch = curMatch;
                break;
            }
        }
        return childMatch;
    }

    @Override
    public ResultModel getJclq(String platformCode, String strAppVersion, Object body) {
        ResultModel resultModel = new ResultModel();
        List result = new ArrayList();
        //分平台版本以上才控制玩法开停售
        boolean isNewVersion = true;
        /*int appVersion = Integer.parseInt(strAppVersion.replaceAll("\\.", ""));
        if ("IPHONE".equalsIgnoreCase(platformCode) && appVersion >= 377) {
            isNewVersion = true;
        } else if ("Android".equalsIgnoreCase(platformCode)) {
            isNewVersion = true;
        } else if ("pc".equalsIgnoreCase(platformCode)) {
            isNewVersion = true;
        } else if ("h5mobile".equalsIgnoreCase(platformCode)) {
            isNewVersion = true;
        } else {
            isNewVersion = false;
        }*/

        //获取彩种基本信息
        RedisLotteryDTO basedataLottery = basedataShareRedisService.getLotteryByLotteryId(BasedataContext.LOTTERY_JCLQ_ID);
        //获取在售期次
        List<BasedataIssueENT> issuesByLotteryId = basedataShareRedisService.getIssuesByLotteryId(BasedataContext.LOTTERY_JCLQ_ID.toString());

        issuesByLotteryId = issuesByLotteryId.stream().filter(x -> x.getEndTime().after(new Date())).collect(Collectors.toList());
        //获取篮球对阵
        List<RedisJclqMatchDTO> jclqMatch = basedataShareRedisService.getJcLqMatch();
        //获取篮球玩法停售
        List<RedisJclqMatchStopSaleDTO> jclqStopSales = basedataShareRedisService.getJcLqMatchStopSale();
        Date curDate = new Date();
        if (CollectionUtils.isNotEmpty(issuesByLotteryId)) {
            issuesByLotteryId.sort(new Comparator<BasedataIssueENT>() {
                @Override
                public int compare(BasedataIssueENT o1, BasedataIssueENT o2) {
                    return o1.getIssueName().compareTo(o2.getIssueName());
                }
            });
            for (BasedataIssueENT issue : issuesByLotteryId) {
                Map<Object, Object> objectObjectHashMap = new LinkedHashMap<>();
                objectObjectHashMap.put("issue", issue.getIssueName());
                //获取售期下对阵
                List<RedisJclqMatchDTO> issueMatchs = null;

                if (isNewVersion) {
                    issueMatchs = jclqMatch.stream().filter(x -> x.getIssueName().equals(issue.getIssueName()) && x.getEndTime().after(curDate)).collect(Collectors.toList());
                } else {
                    issueMatchs = jclqMatch.stream().filter(x -> x.getIssueName().equals(issue.getIssueName()) && x.getEndTime().after(curDate) && x.getHd().equals(1)).collect(Collectors.toList());
                }
                if (CollectionUtils.isEmpty(issueMatchs)) {
                    continue;
                }
                issueMatchs.sort(new Comparator<RedisJclqMatchDTO>() {
                    @Override
                    public int compare(RedisJclqMatchDTO o1, RedisJclqMatchDTO o2) {
                        return o1.getMatchNo().compareTo(o2.getMatchNo());
                    }
                });
                ArrayList<ClientJclqMatchVO> clientJclqMatchVOs = new ArrayList<>();
                for (RedisJclqMatchDTO jdlqM : issueMatchs) {
                    ClientJclqMatchVO clientJclqMatchVO = new ClientJclqMatchVO();
                    clientJclqMatchVO.setmId(jdlqM.getIssueMatchName());
                    clientJclqMatchVO.setTournamentId(jdlqM.getTournamentId() == null ? "" : String.valueOf(jdlqM.getTournamentId()));
                    clientJclqMatchVO.setMatchName(jdlqM.getMatchName());
                    clientJclqMatchVO.setHostTeam(jdlqM.getHostTeam());
                    clientJclqMatchVO.setVistorTeam(jdlqM.getVisitTeam());
                    clientJclqMatchVO.setEndTime(jdlqM.getEndTime());
                    clientJclqMatchVO.setMatchId(String.valueOf(jdlqM.getMatchId()));
                    String[] psStatus = jdlqM.getPsStatus().split(",");
                    if (psStatus.length == 4) {
                        clientJclqMatchVO.setSpRFSF(psStatus[0].equals("0") ? getJclqSp("0", 9101) : getJclqSp(jdlqM.getSprfsf(), 9101));
                        clientJclqMatchVO.setSpSF(psStatus[1].equals("0") ? getJclqSp("0", 9102) : getJclqSp(jdlqM.getSpsf(), 9102));
                        clientJclqMatchVO.setSpSFC(psStatus[2].equals("0") ? getJclqSp("0", 9103) : getJclqSp(jdlqM.getSpsfc(), 9103));
                        clientJclqMatchVO.setSpDXF(psStatus[3].equals("0") ? getJclqSp("0", 9104) : getJclqSp(jdlqM.getSpdxf(), 9104));
                    } else {
                        clientJclqMatchVO.setSpRFSF(getJclqSp(jdlqM.getSprfsf(), 9101));
                        clientJclqMatchVO.setSpSF(getJclqSp(jdlqM.getSpsf(), 9102));
                        clientJclqMatchVO.setSpSFC(getJclqSp(jdlqM.getSpsfc(), 9103));
                        clientJclqMatchVO.setSpDXF(getJclqSp(jdlqM.getSpdxf(), 9104));
                    }
                    clientJclqMatchVO.setDgRFSFStatus(jdlqM.getDgrfsfStatus());
                    clientJclqMatchVO.setDgSFStatus(jdlqM.getDgsfStatus());
                    clientJclqMatchVO.setDgDXFStatus(jdlqM.getDgdxfStatus());
                    clientJclqMatchVO.setDgSFCStatus(1);
                    if (isNewVersion) {
                        clientJclqMatchVO.setStatus(jdlqM.getHd());
                        if (CollectionUtils.isNotEmpty(jclqStopSales)) {
                            List<RedisJclqMatchStopSaleDTO> collect = jclqStopSales.stream().filter(x -> x.getIssueMatchName().equals(jdlqM.getIssueMatchName())).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(collect)) {
                                RedisJclqMatchStopSaleDTO jclqMatchStopSaleDTO = collect.get(0);
                                clientJclqMatchVO.setReason(jclqMatchStopSaleDTO.getReason());
                                clientJclqMatchVO.setPlayDetailState(jclqMatchStopSaleDTO.getPlayDetailState());
                            } else {
                                clientJclqMatchVO.setPlayDetailState("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");
                            }

                        } else {
                            clientJclqMatchVO.setPlayDetailState("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");
                            clientJclqMatchVO.setReason("");
                        }

                        if (psStatus.length == 4) {
                            StringBuffer newPsStatus = new StringBuffer();
                            if (psStatus[0].equals("0") && StringUtils.isBlank(jdlqM.getSprfsf())) {
                                newPsStatus.append("2,");
                            } else {
                                newPsStatus.append(psStatus[0]);
                                newPsStatus.append(",");
                            }
                            if (psStatus[1].equals("0") && StringUtils.isBlank(jdlqM.getSpsf())) {
                                newPsStatus.append("2,");
                            } else {
                                newPsStatus.append(psStatus[1]);
                                newPsStatus.append(",");
                            }
                            if (psStatus[2].equals("0") && StringUtils.isBlank(jdlqM.getSpsfc())) {
                                newPsStatus.append("2,");
                            } else {
                                newPsStatus.append(psStatus[2]);
                                newPsStatus.append(",");
                            }
                            if (psStatus[3].equals("0") && StringUtils.isBlank(jdlqM.getSpdxf())) {
                                newPsStatus.append("2");
                            } else {
                                newPsStatus.append(psStatus[3]);
                            }
                            clientJclqMatchVO.setPlayState(newPsStatus.toString());
                        } else {
                            clientJclqMatchVO.setPlayState(jdlqM.getPsStatus());
                        }

                    }
                    clientJclqMatchVO.setHostRank(String.valueOf(jdlqM.getHostRank()));
                    clientJclqMatchVO.setHostAlias(jdlqM.getHostArea());
                    clientJclqMatchVO.setVistorRank(String.valueOf(jdlqM.getVisitRank()));
                    clientJclqMatchVO.setVistorAlias(jdlqM.getVisitArea());
                    clientJclqMatchVO.setHaHistory(jdlqM.getHostAgainstVisitHistory());
                    clientJclqMatchVO.setHostHistory(jdlqM.getHostRecentHistory());
                    clientJclqMatchVO.setHostRFHistory(jdlqM.getHostRfHistory());
                    clientJclqMatchVO.setHostDXFHistory(jdlqM.getHostDxfHistory());
                    clientJclqMatchVO.setVistorHistory(jdlqM.getVisitRecentHistory());
                    clientJclqMatchVO.setVistorRFHistory(jdlqM.getVisitRfHistory());
                    clientJclqMatchVO.setVistorDXFHistory(jdlqM.getVisitDxfHistory());
                    clientJclqMatchVO.setBgColor(jdlqM.getBgColor());
                    clientJclqMatchVO.setVisitTeamId(jdlqM.getVisitTeamId());
                    clientJclqMatchVO.setHostTeamId(jdlqM.getHostTeamId());
                    clientJclqMatchVOs.add(clientJclqMatchVO);
                    objectObjectHashMap.put("Wk", jdlqM.getWeek());
                }
                objectObjectHashMap.put("MCount", clientJclqMatchVOs.size());
                objectObjectHashMap.put("Matches", clientJclqMatchVOs);
                objectObjectHashMap.put("CurrentTime", DateUtil.format(new Date()));
                if (CollectionUtils.isNotEmpty(clientJclqMatchVOs)) {
                    result.add(objectObjectHashMap);
                }
            }
        }
        resultModel.setCode(ResultModel.SUCCESS);
        resultModel.setData(result);
        return resultModel;
    }

    private String getJclqSp(String sp, int playId) {
        if (StringUtils.isBlank(sp)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        String[] sps = sp.split(",");
        switch (playId) {
            case 9101:
                if (sps.length < 3) {
                    sb.append("0.00|0.00|0.00");
                } else {
                    sb.append(sps[0]);
                    sb.append("|");
                    sb.append(sps[1]);
                    sb.append("|");
                    sb.append(sps[2]);
                }
                break;
            case 9102:
                if (sps.length < 2) {
                    sb.append("0.00|0.00");
                } else {
                    sb.append(sps[0]);
                    sb.append("|");
                    sb.append(sps[1]);
                }
                break;
            case 9103:
                if (sps.length < 12) {
                    sb.append("0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00");
                } else {
                    sb.append(sps[0]);
                    sb.append("|");
                    sb.append(sps[1]);
                    sb.append("|");
                    sb.append(sps[2]);
                    sb.append("|");
                    sb.append(sps[3]);
                    sb.append("|");
                    sb.append(sps[4]);
                    sb.append("|");
                    sb.append(sps[5]);
                    sb.append("|");
                    sb.append(sps[6]);
                    sb.append("|");
                    sb.append(sps[7]);
                    sb.append("|");
                    sb.append(sps[8]);
                    sb.append("|");
                    sb.append(sps[9]);
                    sb.append("|");
                    sb.append(sps[10]);
                    sb.append("|");
                    sb.append(sps[11]);
                }
                break;
            case 9104:
                if (sps.length < 3) {
                    sb.append("0.00|0.00|0.00");
                } else {
                    sb.append(sps[0]);
                    sb.append("|");
                    sb.append(sps[1]);
                    sb.append("|");
                    sb.append(sps[2]);
                }
                break;
            default:
                sb.append("");
        }
        return sb.toString();
    }

    /**
     *
     * @param traceId
     * @param platformCode
     * @param body
     * @return
     */
    @Override
    public ResultModel bonusOptimize(String traceId, String platformCode, Object body) {
        ResultModel resultModel = new ResultModel();
        try {
            ClientBonusOptimizeParams params = GfJsonUtil.parseObject(String.valueOf(body), ClientBonusOptimizeParams.class);
            String ehKey = "EH_BONUS_OPTIMIZE" + params.getInputParams();
            Object data = null;

            if (data != null) {
                resultModel.setData(data);
            } else {
                JSONObject inputJson = GfJsonUtil.parseObject(params.getInputParams(), JSONObject.class);
                if ("load".equals(params.getType().toLowerCase())) {
                    resultModel = checkOptimize(inputJson, params.getOrigin(), platformCode, traceId);
                    if (resultModel.getCode() < 0) {
                        return resultModel;
                    }
                }
                switch (params.getType().toLowerCase()) {
                    case "load":
                        data = packageBonusOptimizeLoadData(params);
                        break;
                    case "optimize":
                        data = packageBonusOptimizeData(params);
                        break;
                    default:
                        data = null;
                        break;
                }
                if (data == null) {
                    resultModel.setCode(ResultModel.ERROR);
                    resultModel.setData("优化类型不存在");
                } else {
                    resultModel.setData(data);
                    ehcacheManager.set(ehKey, data, 60);
                }
            }
        } catch (Exception e) {
            logger.error("奖金优化异常：traceId={}，body={}，platformCode={},{}", traceId, body, platformCode, LogExceptionStackTrace.erroStackTrace(e));
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setData("系统忙，请重试");
        }

        return resultModel;
    }

    private ResultModel checkOptimize(JSONObject inputParam, String origin, String platformCode, String traceId) {
        ResultModel resultModel = new ResultModel();
        if ("1".equals(origin)) {
            resultModel.setCode(ResultModel.SUCCESS);
            return resultModel;
        }
        int minute = 30;
        boolean onoff = true;
        String desc = "奖金优化限赛前30分钟使用，请您使用正常投注";
//        Map bonusOptimizeByPlatform = dubboAppCfgService.getBonusOptimizeByPlatform(platformCode, traceId);
        // TODO: 2020/10/30 未实现外调接口
        Map bonusOptimizeByPlatform = new HashMap();
        if (MapUtils.isNotEmpty(bonusOptimizeByPlatform)) {
            if ("iphone".equals(platformCode.toLowerCase())) {
                Map<String, Map> versions = (Map) bonusOptimizeByPlatform.get("versions");
                for (Map.Entry<String, Map> stringMapEntry : versions.entrySet()) {
                    onoff = Boolean.valueOf(String.valueOf(stringMapEntry.getValue().getOrDefault("optimizeOn", true)));
                    desc = String.valueOf(stringMapEntry.getValue().getOrDefault("optimizeDesc", desc));
                    minute = Integer.parseInt(String.valueOf(stringMapEntry.getValue().getOrDefault("optimizeMinute", minute)));
                    break;
                }
            } else if ("android".equals(platformCode.toLowerCase())) {
                Map optimize = (Map) bonusOptimizeByPlatform.get("optimize");
                onoff = Boolean.valueOf(String.valueOf(optimize.getOrDefault("onoff", true)));
                desc = String.valueOf(optimize.getOrDefault("desc", desc));
                minute = Integer.parseInt(String.valueOf(optimize.getOrDefault("minute", minute)));
            }
        }
        JSONObject schemes = (JSONObject) inputParam.get("schemes");
        List<RedisJczqMatchDTO> jczqMatchIssueList = basedataShareRedisService.getJczqMatchIssueList();
        if (CollectionUtils.isEmpty(jczqMatchIssueList)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("无期次对阵数据。");
            return resultModel;
        }
        Date curDate = new Date();
        for (Map.Entry<String, Object> stringObjectEntry : schemes.entrySet()) {
            JSONObject value = (JSONObject) stringObjectEntry.getValue();
            String issueMatchName = String.valueOf(value.get("id"));
            Stream<RedisJczqMatchDTO> redisJczqMatchDTOStream = jczqMatchIssueList.stream().filter(x -> x.getIssueMatchName().equals(issueMatchName));
            if (redisJczqMatchDTOStream == null && redisJczqMatchDTOStream.count() < 1) {
                resultModel.setCode(ResultModel.ERROR);
                resultModel.setMsg("无期次对阵数据。");
                return resultModel;
            }
            Optional<RedisJczqMatchDTO> first = redisJczqMatchDTOStream.findFirst();
            if (!first.isPresent()) {
                resultModel.setCode(ResultModel.ERROR);
                resultModel.setMsg("无期次对阵数据。");
                return resultModel;
            }
            RedisJczqMatchDTO redisJczqMatchDTO = first.get();
            if (DateUtils.addMinutes(redisJczqMatchDTO.getEndTime(), -minute).before(curDate)) {
                resultModel.setCode(ResultModel.ERROR);
                resultModel.setMsg(desc);
                return resultModel;
            }
        }
        resultModel.setCode(ResultModel.SUCCESS);
        return resultModel;
    }

    private Object packageBonusOptimizeData(ClientBonusOptimizeParams params) {
        JSONObject inputParams = GfJsonUtil.parseObject(params.getInputParams(), JSONObject.class);
        int planBuy = Integer.parseInt(String.valueOf(inputParams.get("planBuy")));
        String onePData = String.valueOf(inputParams.get("onePList"));
        String type = String.valueOf(inputParams.get("type"));
        String[] onePDatas = onePData.split(",");
        List<Double> onePList = new ArrayList<Double>();
        for (String pData : onePDatas) {
            onePList.add(Double.parseDouble(pData));
        }
        List<Integer> optimize = JCOptimize.optimize(onePList, planBuy, type);
        Map map = new HashMap();
        map.put("countList", optimize);
        map.put("time", "");
        return map;
    }

    private Object packageBonusOptimizeLoadData(ClientBonusOptimizeParams params) {
        HashMap<Object, Object> data = new HashMap<>();
        JSONObject inputParams = GfJsonUtil.parseObject(params.getInputParams(), JSONObject.class);
        JSONObject schemes = (JSONObject) inputParams.get("schemes");
        JSONObject groupArr = (JSONObject) inputParams.get("groupArr");
        //1.根据所选比赛数目和组合过关数组求出所有比赛组合；
        int matchCnt = schemes.size();
        //所有赛事组合，key是过关N串1的N值,value是所有组合的对阵的list
        Map<String, List> matchCombination = new HashMap<String, List>();
        //所有赛事集合，key为赛事下标
        Map<String, JSONObject> matchMap = new HashMap<String, JSONObject>();

        int[] schemeIds = new int[schemes.size()];
        int si = 0;
        for (Map.Entry<String, Object> schemesObjectEntry : schemes.entrySet()) {
            if (!matchMap.containsKey(schemesObjectEntry.getKey())) {
                matchMap.put(schemesObjectEntry.getKey(), (JSONObject) schemesObjectEntry.getValue());
            }
            schemeIds[si] = Integer.parseInt(schemesObjectEntry.getKey());
            si++;
        }

        for (Map.Entry<String, Object> groupArrEntry : groupArr.entrySet()) {
            String passWay = String.valueOf(groupArrEntry.getValue());
            String passWayKey = groupArrEntry.getKey();
            //根据过关方式组装赛事组合,每个list 是int[],其内容是每场赛事的下标
            matchCombination.put(passWayKey, MathUtil.combine(schemeIds, Integer.parseInt(passWay)));
        }
        //2.求出所有投注组合，如：[0,2,-1,-1,1],若该场比赛没有则用-1表示，否则填充比赛下标
        List<ClientBonusOptimizeBetInfoVO> betInfolist = new ArrayList<ClientBonusOptimizeBetInfoVO>();
        //每注奖金列表
        List<Double> onePList = new ArrayList<Double>();
        for (Map.Entry<String, List> matchCombinationListEntry : matchCombination.entrySet()) {
            //每个过关方式的赛事组合list,里面保存的是赛事下标
            List<int[]> matchCombinationList = matchCombinationListEntry.getValue();
            //遍历比赛组合，matchCombinations为N串1的N
            for (int[] matchCombinations : matchCombinationList) {
                //该比赛组合的投注数目列表
                int[] matchBetCnt = new int[matchCombinations.length];
                for (int i = 0; i < matchCombinations.length; i++) {
                    //前端传入的每个赛事信息，包含赔率等
                    JSONObject schemeJsonObject = matchMap.get(String.valueOf(matchCombinations[i]));
                    matchBetCnt[i] = ((JSONObject) schemeJsonObject.get("sp")).size();
                }
                List<int[]> betArrList = getBetArr(matchBetCnt);
                //生成由每项投注组合生成全投注
                for (int[] betArr : betArrList) {
                    int[] ba = new int[matchCnt];
                    double onePrize = 1;
                    //按照选择的赛事数初始化投注赛事下标，初始化为-1，即没有该场赛事
                    for (int i = 0; i < matchCnt; i++) {
                        ba[i] = -1;
                    }
                    //计算sp
                    for (int i = 0; i < matchCombinations.length; i++) {
                        //前端传入的每个赛事信息，包含赔率等
                        JSONObject schemeJsonObject = matchMap.get(String.valueOf(matchCombinations[i]));
                        JSONObject spJsonObject = (JSONObject) schemeJsonObject.get("sp");
                        JSONObject spValue = (JSONObject) spJsonObject.get(String.valueOf(betArr[i]));
                        ba[matchCombinations[i]] = betArr[i];
                        double sp = Double.parseDouble(String.valueOf(spValue.get("value")));
                        onePrize *= sp;
                    }
                    //考察定胆
                    if (hasDan(schemes, ba)) {
                        continue;
                    }
                    double op = onePrize * 2;
                    ClientBonusOptimizeBetInfoVO clientBonusOptimizeBetInfoVO = new ClientBonusOptimizeBetInfoVO();
                    clientBonusOptimizeBetInfoVO.setBia(ba);
                    clientBonusOptimizeBetInfoVO.setOp(op);
                    betInfolist.add(clientBonusOptimizeBetInfoVO);
                    onePList.add(op);
                }
            }
        }
        onePList.sort(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return o1.compareTo(o2);
            }
        });
        betInfolist.sort(new Comparator<ClientBonusOptimizeBetInfoVO>() {
            @Override
            public int compare(ClientBonusOptimizeBetInfoVO o1, ClientBonusOptimizeBetInfoVO o2) {
                return o1.getOp().compareTo(o2.getOp());
            }
        });
        data.put("betInfoArr", betInfolist);
        int[] conIndexArr = new int[betInfolist.size()];
        for (int i = 0; i < betInfolist.size(); i++) {
            conIndexArr[i] = i;
        }
        data.put("conIndexArr", conIndexArr);
        //平均优化
        //倍数
        int palyBuy = 1;
        int multiple = Integer.parseInt(String.valueOf(inputParams.get("multiple")));
        if ("1".equals(params.getOrigin())) {
            palyBuy = conIndexArr.length * multiple;
        } else {
            palyBuy = conIndexArr.length * multiple * 2;
        }
        List<Integer> btnAverage = JCOptimize.optimize(onePList, palyBuy, "btnAverage");
        for (int i = 0; i < btnAverage.size(); i++) {
            betInfolist.get(i).setCount(btnAverage.get(i));
        }
        data.put("PrizeTableHTML", createPrizeJson(betInfolist, inputParams, conIndexArr));
        data.put("loadForecast", forecast(inputParams, betInfolist, matchCombination));
        return data;
    }

    //对matchBetCnt组数求出全组合索引，支持最多15组
    private List<int[]> getBetArr(int[] betCountList) {
        List<int[]> arr = new ArrayList<int[]>();
        int count = betCountList.length;
        for (int i1 = 0; i1 < betCountList[0]; i1++) {
            if (count == 1) {
                arr.add(new int[]{i1});
                continue;
            }
            for (int i2 = 0; i2 < betCountList[1]; i2++) {
                if (count == 2) {
                    arr.add(new int[]{i1, i2});
                    continue;
                }
                for (int i3 = 0; i3 < betCountList[2]; i3++) {
                    if (count == 3) {
                        arr.add(new int[]{i1, i2, i3});
                        continue;
                    }
                    for (int i4 = 0; i4 < betCountList[3]; i4++) {
                        if (count == 4) {
                            arr.add(new int[]{i1, i2, i3, i4});
                            continue;
                        }
                        for (int i5 = 0; i5 < betCountList[4]; i5++) {
                            if (count == 5) {
                                arr.add(new int[]{i1, i2, i3, i4, i5});
                                continue;
                            }
                            for (int i6 = 0; i6 < betCountList[5]; i6++) {
                                if (count == 6) {
                                    arr.add(new int[]{i1, i2, i3, i4, i5, i6});
                                    continue;
                                }
                                for (int i7 = 0; i7 < betCountList[6]; i7++) {
                                    if (count == 7) {
                                        arr.add(new int[]{i1, i2, i3, i4, i5, i6, i7});
                                        continue;
                                    }
                                    for (int i8 = 0; i8 < betCountList[7]; i8++) {
                                        if (count == 8) {
                                            arr.add(new int[]{i1, i2, i3, i4, i5, i6, i7, i8});
                                            continue;
                                        }
                                        for (int i9 = 0; i9 < betCountList[8]; i9++) {
                                            if (count == 9) {
                                                arr.add(new int[]{i1, i2, i3, i4, i5, i6, i7, i8, i9});
                                                continue;
                                            }
                                            for (int i10 = 0; i10 < betCountList[9]; i10++) {
                                                if (count == 10) {
                                                    arr.add(new int[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10});
                                                    continue;
                                                }
                                                for (int i11 = 0; i11 < betCountList[10]; i11++) {
                                                    if (count == 11) {
                                                        arr.add(new int[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11});
                                                        continue;
                                                    }
                                                    for (int i12 = 0; i12 < betCountList[11]; i12++) {
                                                        if (count == 12) {
                                                            arr.add(new int[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12});
                                                            continue;
                                                        }
                                                        for (int i13 = 0; i13 < betCountList[12]; i13++) {
                                                            if (count == 13) {
                                                                arr.add(new int[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13});
                                                                continue;
                                                            }
                                                            for (int i14 = 0; i14 < betCountList[13]; i14++) {
                                                                if (count == 14) {
                                                                    arr.add(new int[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14});
                                                                    continue;
                                                                }
                                                                for (int i15 = 0; i15 < betCountList[14]; i15++) {
                                                                    if (count == 15) {
                                                                        arr.add(new int[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15});
                                                                        continue;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return arr;
    }

    //ba中是否有所有定胆的比赛
    private boolean hasDan(JSONObject schemes, int[] ba) {
        for (int i = 0; i < ba.length; i++) {
            JSONObject o = (JSONObject) schemes.get(String.valueOf(ba[i]));
            if (o == null) {
                return false;
            }
            if ("true".equals(o.get("dan")) && ba[i] == -1) {
                return true;
            }
        }
        return false;
    }

    public List createPrizeJson(List<ClientBonusOptimizeBetInfoVO> betInfoArr, JSONObject inputParams, int[] conIndexArr) {
        if (conIndexArr.length == 0) return new ArrayList();
        boolean isOdd = false;
        StringBuilder sb = new StringBuilder();
        int oriIndex;
        ClientBonusOptimizeBetInfoVO betInfo;
        String strTr;
        String strRow;
        double prize = 0;
        ArrayList arr = new ArrayList();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        for (int rowIndex = 0; rowIndex < conIndexArr.length; rowIndex++) {
            Map item = new HashMap();
            oriIndex = conIndexArr[rowIndex];
            betInfo = betInfoArr.get(oriIndex);
            double op = betInfo.getCount() * betInfo.getOp() * 1.0d;
            prize = Double.parseDouble(decimalFormat.format(op));//四舍六入 五留双
            isOdd = !isOdd;
            item.put("sheme", GetFormatSchemeJson(betInfo, inputParams));
            item.put("multi", betInfo.getCount());
            item.put("prize", prize);

            arr.add(item);
        }
        return arr;
    }

    public Map forecast(JSONObject inputParams, List<ClientBonusOptimizeBetInfoVO> oriBetInfoArr, Map<String, List> matchCombination) {
        List<List<int[]>> m_allHittintBet = getAllHittingBet(inputParams, matchCombination);
        Map info = new HashMap();
        info.put("minHitBet", m_allHittintBet.get(0)); //能中奖的最少场次
        info.put("maxHitBet", m_allHittintBet.get(m_allHittintBet.size() - 1)); //全场次
        List<ClientForecastInfoVo> clientForecastInfoVos = forecastByHitCount(inputParams, oriBetInfoArr, m_allHittintBet);
        info.put("forecastInfoList", clientForecastInfoVos);
        info.put("minPrize", clientForecastInfoVos.get(0).minPrize);
        info.put("maxPrize", clientForecastInfoVos.get(clientForecastInfoVos.size() - 1).maxPrize);
        return info;
    }

    private List GetFormatSchemeJson(ClientBonusOptimizeBetInfoVO betInfo, JSONObject inputParams) {
        JSONObject schemes = (JSONObject) inputParams.get("schemes");
        JSONObject groupArr = (JSONObject) inputParams.get("groupArr");
        int selMaxCount = Integer.parseInt(String.valueOf(groupArr.get(String.valueOf(groupArr.size() - 1))));
        int betIndex = 0;
        int rq = 0;
        List objArr = new ArrayList();
        //第i场比赛
        for (int i = 0; i < betInfo.getBia().length; i++) {
            Map ojb = new HashMap();
            JSONObject scheme = (JSONObject) schemes.get(String.valueOf(i));
            JSONObject schemeSp = (JSONObject) scheme.get("sp");
            String rqInput = String.valueOf(scheme.get("rq"));
            if (StringUtils.isNotBlank(rqInput)) {
                rq = Integer.parseInt(rqInput);
            }
            //第betInfo.betIndexArr个投注
            if (betInfo.getBia()[i] == -1) {
                continue;
            }
            JSONObject spItem = (JSONObject) schemeSp.get(String.valueOf(betInfo.getBia()[i]));
            betIndex = Integer.parseInt(String.valueOf(spItem.get("bet")));
            ojb.put("id", scheme.get("id"));
            if (selMaxCount < 4) {
                ojb.put("wk", scheme.get("cname"));
            } else {
                ojb.put("wk", (i + 1));
            }
            ojb.put("vteam", scheme.get("sguest"));
            ojb.put("hteam", scheme.get("shost"));
            //让球胜平负
            if (betIndex >= 0 && betIndex <= 2) {
                // ojb["rq"] = rq > 0 ? "(+" + rq + ")" : rq < 0 ? "(" + rq + ")" : "";
                ojb.put("rq", rq > 0 ? "(+" + rq + ")" : rq < 0 ? "(" + rq + ")" : "");
            } else {
                ojb.put("rq", "");
            }
            ojb.put("bet", betIndex);
            ojb.put("value", spItem.get("value"));
            objArr.add(ojb);
        }
        return objArr;
    }

    private List<List<int[]>> getAllHittingBet(JSONObject inputParams, Map<String, List> matchCombination) {
        JSONObject schemes = (JSONObject) inputParams.get("schemes");
        JSONObject groupArr = (JSONObject) inputParams.get("groupArr");
        List<List<int[]>> dic_allHittintBet = new ArrayList<List<int[]>>();
        List<int[]> matIndexArrList = new ArrayList<int[]>();
        int matchCount = schemes.size();

        int[] emptyBet = new int[matchCount];
        for (int m = 0; m < matchCount; m++) {
            emptyBet[m] = -1;//全部初始化为-1;
        }

        List<Integer> groupList = new ArrayList<Integer>();
        for (Map.Entry<String, Object> groupArrEntry : groupArr.entrySet()) {
            int group = Integer.parseInt(String.valueOf(groupArrEntry.getValue()));
            List<int[]> hitting = new ArrayList<int[]>();

            //matchCount选group个组合
            matIndexArrList = matchCombination.get(String.valueOf(groupArrEntry.getKey()));
            for (int[] matIndexArr : matIndexArrList) {
                //该比赛组合的投注数目列表
                int[] betCountList = new int[matIndexArr.length];
                for (int j = 0; j < matIndexArr.length; j++) {
                    //第matIndexArr[j]场比赛有多少个投注
                    JSONObject scheme = (JSONObject) schemes.get(String.valueOf(matIndexArr[j]));
                    JSONObject schemeSp = (JSONObject) scheme.get("sp");
                    betCountList[j] = schemeSp.size();
                }
                List<int[]> betArrlist = getBetArr(betCountList);
                //生成由每项投注组合生成全投注
                for (int[] betArr : betArrlist) {
                    int[] ba = emptyBet.clone();
                    for (int n = 0; n < matIndexArr.length; n++) {
                        //第matIndexArr[n]场比赛的投注为betArr[n]
                        ba[matIndexArr[n]] = betArr[n];
                    }
                    hitting.add(ba);
                }
            }
            dic_allHittintBet.add(hitting);
        }

        return dic_allHittintBet;
    }

    private List<ClientForecastInfoVo> forecastByHitCount(JSONObject inputParams, List<ClientBonusOptimizeBetInfoVO> betInfoArr, List<List<int[]>> m_allHittintBet) {
        List<ClientForecastInfoVo> forecastList = new ArrayList<ClientForecastInfoVo>();
        JSONObject groupArr = (JSONObject) inputParams.get("groupArr");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        //理论奖金列表
        List<Double> prizeList = new ArrayList<Double>();
        for (ClientBonusOptimizeBetInfoVO item : betInfoArr) {
            prizeList.add(item.getOp() * item.getCount());
        }
        try {
            //命中场数
            int hitCount = Integer.parseInt(String.valueOf(groupArr.get("0")));
            for (List<int[]> m_allHittintBetEntry : m_allHittintBet) {
                ClientForecastInfoVo finfo = new ClientForecastInfoVo();
                //命中场数
                finfo.hitCount = hitCount++;
                //最小奖金
                finfo.minPrize = Double.MAX_VALUE;
                //最小中奖注数
                finfo.minWinCount = Integer.MAX_VALUE;
                //遍历该命中场数下的所有可能中奖投注
                for (int[] hittingBet : m_allHittintBetEntry) {
                    //中奖注数
                    int winCount = 0;
                    //命中奖金
                    double allPrize = 0.0;
                    //针对某一项中奖投注，遍历优化表中的所有方案，找出中奖注数和命中奖金
                    for (int j = 0; j < betInfoArr.size(); j++) {
                        ClientBonusOptimizeBetInfoVO betInfo = betInfoArr.get(j);
                        //该注没中
                        int[] bia = betInfo.getBia();
                        boolean noHit = false;
                        for (int i = 0; i < hittingBet.length; i++) {
                            if (hittingBet[i] != bia[i]) {
                                noHit = true;
                                break;
                            }
                        }
                        if (noHit) {
                            continue;
                        }
                        //该注中了
                        allPrize = allPrize + prizeList.get(j);
                        winCount++;
                    }
                    if (winCount == 0) {
                        continue;
                    }
                    if (winCount >= finfo.maxWinCount) {
                        finfo.minWinCount = winCount;
                        finfo.maxWinCount = winCount;
                        if (allPrize < finfo.minPrize) {
                            finfo.minPrize = Double.parseDouble(decimalFormat.format(allPrize));
                        }
                        if (allPrize > finfo.maxPrize) {
                            finfo.maxPrize = Double.parseDouble(decimalFormat.format(allPrize));
                        }
                    }
                }
                if (finfo.minPrize == Double.MAX_VALUE) {
                    finfo.minPrize = 0;
                    finfo.minWinCount = 0;
                }
                forecastList.add(finfo);
            }
            return forecastList;
        } catch (Exception e) {
            logger.error("组装forecastByHitCount异常,inputParams={}，m_allHittintBet={},betInfoArr={},{}", m_allHittintBet, inputParams, GfJsonUtil.toJSONString(betInfoArr), LogExceptionStackTrace.erroStackTrace(e));
            return forecastList;
        }
    }

    @Override
    public ResultModel getDggpMatch(Object body) {
        ResultModel resultModel = new ResultModel();
        String playId = String.valueOf(GfJsonUtil.parseObject(String.valueOf(body), Map.class).get("playId"));
        List<RedisJczqDggpMatchDTO> jczqDggpMatch = basedataShareRedisService.getJczqDggpMatch();
        if (CollectionUtils.isEmpty(jczqDggpMatch)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("您好,当前没有推荐赛事!");
            resultModel.setData(new HashMap());
            return resultModel;
        }
        jczqDggpMatch = jczqDggpMatch.stream().filter(x -> x.getPlayTypeId().equals(playId) && x.getEndTime().after(new Date())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(jczqDggpMatch)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("您好,当前没有推荐赛事!");
            resultModel.setData(new HashMap());
            return resultModel;
        }
        List<RedisJczqDggpMatchDTO> dggpMatchOnSale = getDggpMatchOnSale(jczqDggpMatch, playId);
        if (CollectionUtils.isEmpty(dggpMatchOnSale)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("您好,当前没有推荐赛事!");
            resultModel.setData(new HashMap());
            return resultModel;
        }
        dggpMatchOnSale.sort(new Comparator<RedisJczqDggpMatchDTO>() {
            @Override
            public int compare(RedisJczqDggpMatchDTO o1, RedisJczqDggpMatchDTO o2) {
                return o1.getEndTime().compareTo(o2.getEndTime());
            }
        });
        List<ClientJczqDggpMatchVO> clientJczqDggpMatchVOs = packageJczqDggpDTO(dggpMatchOnSale, playId);
        if (CollectionUtils.isEmpty(clientJczqDggpMatchVOs)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("您好,当前没有推荐赛事!");
            resultModel.setData(new HashMap());
            return resultModel;
        }
        resultModel.setData(clientJczqDggpMatchVOs);
        resultModel.setCode(ResultModel.SUCCESS);

        return resultModel;
    }

    private List<RedisJczqDggpMatchDTO> getDggpMatchOnSale(List<RedisJczqDggpMatchDTO> jczqDggpMatch, String playId) {
        List<RedisJczqDggpMatchDTO> list = new ArrayList<RedisJczqDggpMatchDTO>();
        for (RedisJczqDggpMatchDTO redisJczqDggpMatchDTO : jczqDggpMatch) {
            String[] psStatus = redisJczqDggpMatchDTO.getPsStatus().split(",");

            switch (playId) {
                case "9001":
                    if ("0".equals(psStatus[0]) && redisJczqDggpMatchDTO.getHd().equals(1)) {
                        list.add(redisJczqDggpMatchDTO);
                    }
                    break;
                case "9002":
                    if ("0".equals(psStatus[1]) && redisJczqDggpMatchDTO.getHd().equals(1)) {
                        list.add(redisJczqDggpMatchDTO);
                    }
                    break;
                case "9003":
                    if ("0".equals(psStatus[2]) && redisJczqDggpMatchDTO.getHd().equals(1)) {
                        list.add(redisJczqDggpMatchDTO);
                    }
                    break;
                case "9004":
                    if ("0".equals(psStatus[3]) && redisJczqDggpMatchDTO.getHd().equals(1)) {
                        list.add(redisJczqDggpMatchDTO);
                    }
                    break;
                case "9005":
                    if (redisJczqDggpMatchDTO.getHd().equals(1)) {
                        list.add(redisJczqDggpMatchDTO);
                    }
                    break;
                case "9006":
                    if ("0".equals(psStatus[4]) && redisJczqDggpMatchDTO.getHd().equals(1)) {
                        list.add(redisJczqDggpMatchDTO);
                    }
                    break;
                default:
                    break;
            }
        }

        return list;
    }

    private List<ClientJczqDggpMatchVO> packageJczqDggpDTO(List<RedisJczqDggpMatchDTO> jczqDggpMatch, String playId) {
        List<ClientJczqDggpMatchVO> list = new ArrayList<ClientJczqDggpMatchVO>();
        List tmpList = new ArrayList();
        for (RedisJczqDggpMatchDTO redisJczqDggpMatchDTO : jczqDggpMatch) {
            if (tmpList.contains(redisJczqDggpMatchDTO.getIssueMatchName())) {
                continue;
            }
            ClientJczqDggpMatchVO clientJczqDggpMatchVO = new ClientJczqDggpMatchVO();
            clientJczqDggpMatchVO.setMatchId(redisJczqDggpMatchDTO.getMatchId());
            clientJczqDggpMatchVO.setIssueNo(Integer.parseInt(redisJczqDggpMatchDTO.getIssueNo()));
            clientJczqDggpMatchVO.setDayOfWeek(redisJczqDggpMatchDTO.getWeekName());
            clientJczqDggpMatchVO.setDayNumber(redisJczqDggpMatchDTO.getMatchNo());
            clientJczqDggpMatchVO.setMatchTitle(redisJczqDggpMatchDTO.getMatchName());
            clientJczqDggpMatchVO.setMatchName(redisJczqDggpMatchDTO.getMatchName().length() > 4 ? redisJczqDggpMatchDTO.getMatchName().substring(0, 4) : redisJczqDggpMatchDTO.getMatchName());
            clientJczqDggpMatchVO.setEndTime(redisJczqDggpMatchDTO.getEndTime().getTime());
            clientJczqDggpMatchVO.setMatchTime(redisJczqDggpMatchDTO.getMatchTime().getTime());
            clientJczqDggpMatchVO.setRq(String.valueOf(redisJczqDggpMatchDTO.getRq()));
            clientJczqDggpMatchVO.setPlayID(redisJczqDggpMatchDTO.getPlayTypeId());
            Double lastWinRate = redisJczqDggpMatchDTO.getEuropeanBetOffer().getLastWinRate();
            clientJczqDggpMatchVO.setWinRate(lastWinRate == null ? 0 : lastWinRate);
            Double lastDrawRate = redisJczqDggpMatchDTO.getEuropeanBetOffer().getLastDrawRate();
            clientJczqDggpMatchVO.setDrawRate(lastDrawRate == null ? 0 : lastDrawRate);
            Double lastLossRate = redisJczqDggpMatchDTO.getEuropeanBetOffer().getLastLossRate();
            clientJczqDggpMatchVO.setLoseRate(lastLossRate == null ? 0 : lastLossRate);
            clientJczqDggpMatchVO.setDes(redisJczqDggpMatchDTO.getContext());
            ClientJczqDggpMatchVO.TeamInfo teamInfo = new ClientJczqDggpMatchVO.TeamInfo();
            teamInfo.setName(redisJczqDggpMatchDTO.getHostTeam().length() > 5 ? redisJczqDggpMatchDTO.getHostTeam().substring(0, 5) : redisJczqDggpMatchDTO.getHostTeam());
            BigDecimal hostTeamWinRate = redisJczqDggpMatchDTO.getHostTeamWinRate();
            teamInfo.setOffer(hostTeamWinRate == null ? 0 : hostTeamWinRate.doubleValue());
            teamInfo.setRank(redisJczqDggpMatchDTO.getHostRank());
            teamInfo.setTitle(redisJczqDggpMatchDTO.getHostTeam());
            teamInfo.setId(redisJczqDggpMatchDTO.getHostTeamId());
            ClientJczqDggpMatchVO.HistoryZj historyZj = new ClientJczqDggpMatchVO.HistoryZj();
            String hostRecentHistory = redisJczqDggpMatchDTO.getHostRecentHistory();
            if (StringUtils.isBlank(hostRecentHistory)) {
                historyZj.setWin(0);
                historyZj.setDraw(0);
                historyZj.setLose(0);
            } else {
                String[] hostRecentHistorys = hostRecentHistory.split(",");
                historyZj.setWin(Integer.parseInt(hostRecentHistorys[0]));
                historyZj.setDraw(Integer.parseInt(hostRecentHistorys[1]));
                historyZj.setLose(Integer.parseInt(hostRecentHistorys[2]));
            }

            teamInfo.setHistory(historyZj);
            List<BasedataTeamInjureDTO> hostTeamInjuresList = redisJczqDggpMatchDTO.getHostTeamInjuresList();
            if (CollectionUtils.isEmpty(hostTeamInjuresList)) {
                teamInfo.setInjure(new ArrayList<>());
            } else {
                List<ClientJczqDggpMatchVO.InjureInfoVO> injureInfoVOList = new ArrayList<>();
                for (BasedataTeamInjureDTO basedataTeamInjureDTO : hostTeamInjuresList) {
                    ClientJczqDggpMatchVO.InjureInfoVO injureInfoVO = new ClientJczqDggpMatchVO.InjureInfoVO();
                    injureInfoVO.setMain(basedataTeamInjureDTO.getMain());
                    injureInfoVO.setPlayer(basedataTeamInjureDTO.getPlayerName());
                    injureInfoVO.setReason(basedataTeamInjureDTO.getReason());
                    injureInfoVOList.add(injureInfoVO);
                }
                teamInfo.setInjure(injureInfoVOList);
            }
            clientJczqDggpMatchVO.setHostTeam(teamInfo);

            teamInfo = new ClientJczqDggpMatchVO.TeamInfo();
            teamInfo.setName(redisJczqDggpMatchDTO.getVisitTeam().length() > 5 ? redisJczqDggpMatchDTO.getVisitTeam().substring(0, 5) : redisJczqDggpMatchDTO.getVisitTeam());
            teamInfo.setOffer(redisJczqDggpMatchDTO.getVisitTeamWinRate().doubleValue());
            teamInfo.setRank(redisJczqDggpMatchDTO.getVisitRank());
            teamInfo.setTitle(redisJczqDggpMatchDTO.getVisitTeam());
            teamInfo.setId(redisJczqDggpMatchDTO.getVisitTeamId());
            historyZj = new ClientJczqDggpMatchVO.HistoryZj();
            String visitRecentHistory = redisJczqDggpMatchDTO.getVisitRecentHistory();
            if (StringUtils.isBlank(visitRecentHistory)) {
                historyZj.setWin(0);
                historyZj.setDraw(0);
                historyZj.setLose(0);
            } else {
                String[] visitRecentHistorys = visitRecentHistory.split(",");
                historyZj.setWin(Integer.parseInt(visitRecentHistorys[0]));
                historyZj.setDraw(Integer.parseInt(visitRecentHistorys[1]));
                historyZj.setLose(Integer.parseInt(visitRecentHistorys[2]));
            }
            teamInfo.setHistory(historyZj);
            List<BasedataTeamInjureDTO> visitTeamInjuresList = redisJczqDggpMatchDTO.getVisitTeamInjureList();
            if (CollectionUtils.isEmpty(visitTeamInjuresList)) {
                teamInfo.setInjure(new ArrayList<>());
            } else {
                List<ClientJczqDggpMatchVO.InjureInfoVO> injureInfoVOList = new ArrayList<>();
                for (BasedataTeamInjureDTO basedataTeamInjureDTO : visitTeamInjuresList) {
                    ClientJczqDggpMatchVO.InjureInfoVO injureInfoVO = new ClientJczqDggpMatchVO.InjureInfoVO();
                    injureInfoVO.setMain(basedataTeamInjureDTO.getMain());
                    injureInfoVO.setPlayer(basedataTeamInjureDTO.getPlayerName());
                    injureInfoVO.setReason(basedataTeamInjureDTO.getReason());
                    injureInfoVOList.add(injureInfoVO);
                }
                teamInfo.setInjure(injureInfoVOList);
            }
            clientJczqDggpMatchVO.setVistorTeam(teamInfo);
            packageSpForDggpMatch(clientJczqDggpMatchVO, redisJczqDggpMatchDTO, playId);
            list.add(clientJczqDggpMatchVO);
            tmpList.add(redisJczqDggpMatchDTO.getIssueMatchName());
        }
        return list;
    }

    private void packageSpForDggpMatch(ClientJczqDggpMatchVO clientJczqDggpMatchVO, RedisJczqDggpMatchDTO redisJczqDggpMatchDTO, String playId) {
        String[] psStatus = redisJczqDggpMatchDTO.getPsStatus().split(",");
        ClientJczqDggpMatchVO.SpVo spVo = null;
        switch (playId) {
            case "9002":
                spVo = new ClientJczqDggpMatchVO.SpVo();
                spVo.setOdds(redisJczqDggpMatchDTO.getOdds());
                spVo.setState(StringUtils.isBlank(psStatus[0]) ? 0 : Integer.parseInt(psStatus[0]));
                clientJczqDggpMatchVO.setSpZJQ(spVo);
                break;
            case "9003":
                spVo = new ClientJczqDggpMatchVO.SpVo();
                spVo.setOdds(getJczqSp(redisJczqDggpMatchDTO.getSpcbf(), 9003));
                spVo.setState(StringUtils.isBlank(psStatus[4]) ? 0 : Integer.parseInt(psStatus[4]));
                clientJczqDggpMatchVO.setSpCBF(spVo);
                break;
            case "9004":
                spVo = new ClientJczqDggpMatchVO.SpVo();
                spVo.setOdds(getJczqSp(redisJczqDggpMatchDTO.getSpbqc(), 9004));
                spVo.setState(StringUtils.isBlank(psStatus[3]) ? 0 : Integer.parseInt(psStatus[3]));
                clientJczqDggpMatchVO.setSpBQC(spVo);
                break;
            case "9005":
                String childPlayTypes = redisJczqDggpMatchDTO.getChildPlayTypes();
                spVo = new ClientJczqDggpMatchVO.SpVo();
                spVo.setOdds(childPlayTypes.indexOf("4") > -1 ? getJczqSp(redisJczqDggpMatchDTO.getSpbqc(), 9004) : "");
                spVo.setState(StringUtils.isBlank(psStatus[3]) ? 0 : Integer.parseInt(psStatus[3]));
                clientJczqDggpMatchVO.setSpBQC(spVo);
                spVo = new ClientJczqDggpMatchVO.SpVo();
                spVo.setOdds(childPlayTypes.indexOf("3") > -1 ? getJczqSp(redisJczqDggpMatchDTO.getSpcbf(), 9003) : "");
                spVo.setState(StringUtils.isBlank(psStatus[4]) ? 0 : Integer.parseInt(psStatus[4]));
                clientJczqDggpMatchVO.setSpCBF(spVo);
                spVo = new ClientJczqDggpMatchVO.SpVo();
                spVo.setOdds(childPlayTypes.indexOf("2") > -1 ? getJczqSp(redisJczqDggpMatchDTO.getSpjqs(), 9002) : "");
                spVo.setState(StringUtils.isBlank(psStatus[2]) ? 0 : Integer.parseInt(psStatus[2]));
                clientJczqDggpMatchVO.setSpZJQ(spVo);
                spVo = new ClientJczqDggpMatchVO.SpVo();
                spVo.setOdds(childPlayTypes.indexOf("1") > -1 ? getJczqSp(redisJczqDggpMatchDTO.getSprqs(), 9001) : "");
                spVo.setState(StringUtils.isBlank(psStatus[1]) ? 0 : Integer.parseInt(psStatus[1]));
                clientJczqDggpMatchVO.setSpRQSPF(spVo);
                spVo = new ClientJczqDggpMatchVO.SpVo();
                spVo.setOdds(childPlayTypes.indexOf("6") > -1 ? getJczqSp(redisJczqDggpMatchDTO.getSpspf(), 9006) : "");
                spVo.setState(StringUtils.isBlank(psStatus[0]) ? 0 : Integer.parseInt(psStatus[0]));
                clientJczqDggpMatchVO.setSpSPF(spVo);
                break;
            case "9006":
                String spspf = redisJczqDggpMatchDTO.getSpspf();
                String[] spspfs = spspf.split(",");
                spVo = new ClientJczqDggpMatchVO.SpVo();
                spVo.setOdds(StringUtils.isBlank(spspf) ? "" : spspfs[2] + "," + spspfs[1] + "," + spspfs[0]);
                spVo.setState(StringUtils.isBlank(psStatus[0]) ? 0 : Integer.parseInt(psStatus[0]));
                clientJczqDggpMatchVO.setSpSPF(spVo);
                break;
            default:
                break;
        }
    }

    private ClientJczqDggpMatchVO packageJczqDggpDTO(RedisJczqDggpMatchDTO redisJczqDggpMatchDTO, String playId) {

        ClientJczqDggpMatchVO clientJczqDggpMatchVO = new ClientJczqDggpMatchVO();
        clientJczqDggpMatchVO.setMatchId(redisJczqDggpMatchDTO.getMatchId());
        clientJczqDggpMatchVO.setIssueNo(Integer.parseInt(redisJczqDggpMatchDTO.getIssueNo()));
        clientJczqDggpMatchVO.setIssueName(redisJczqDggpMatchDTO.getIssueName());
        clientJczqDggpMatchVO.setDayOfWeek(redisJczqDggpMatchDTO.getWeekName());
        clientJczqDggpMatchVO.setDayNumber(redisJczqDggpMatchDTO.getMatchNo());
        if (redisJczqDggpMatchDTO.getMatchName() == null) {
            redisJczqDggpMatchDTO.setMatchName("");
        }
        clientJczqDggpMatchVO.setMatchTitle(redisJczqDggpMatchDTO.getMatchName());
        clientJczqDggpMatchVO.setMatchName(redisJczqDggpMatchDTO.getMatchName().length() > 4 ? redisJczqDggpMatchDTO.getMatchName().substring(0, 4) : redisJczqDggpMatchDTO.getMatchName());
        clientJczqDggpMatchVO.setEndTime(redisJczqDggpMatchDTO.getEndTime().getTime());
        clientJczqDggpMatchVO.setMatchTime(redisJczqDggpMatchDTO.getMatchTime().getTime());
        String rq = "";
        if (redisJczqDggpMatchDTO.getRq().intValue() == 0) {
            rq = "";
        } else if (redisJczqDggpMatchDTO.getRq().intValue() > 0) {
            rq = "+" + redisJczqDggpMatchDTO.getRq();
        } else {
            rq = String.valueOf(redisJczqDggpMatchDTO.getRq());
        }
        clientJczqDggpMatchVO.setRq(rq);
        clientJczqDggpMatchVO.setPlayID(redisJczqDggpMatchDTO.getPlayTypeId());
        Double lastWinRate = redisJczqDggpMatchDTO.getEuropeanBetOffer().getLastWinRate();
        clientJczqDggpMatchVO.setWinRate(lastWinRate == null ? 0 : lastWinRate);
        Double lastDrawRate = redisJczqDggpMatchDTO.getEuropeanBetOffer().getLastDrawRate();
        clientJczqDggpMatchVO.setDrawRate(lastDrawRate == null ? 0 : lastDrawRate);
        Double lastLossRate = redisJczqDggpMatchDTO.getEuropeanBetOffer().getLastLossRate();
        clientJczqDggpMatchVO.setLoseRate(lastLossRate == null ? 0 : lastLossRate);
        clientJczqDggpMatchVO.setDes(redisJczqDggpMatchDTO.getContext());
        ClientJczqDggpMatchVO.TeamInfo teamInfo = new ClientJczqDggpMatchVO.TeamInfo();
        teamInfo.setName(redisJczqDggpMatchDTO.getHostTeam().length() > 5 ? redisJczqDggpMatchDTO.getHostTeam().substring(0, 5) : redisJczqDggpMatchDTO.getHostTeam());
        BigDecimal hostTeamWinRate = redisJczqDggpMatchDTO.getHostTeamWinRate();
        teamInfo.setOffer(hostTeamWinRate == null ? 0 : hostTeamWinRate.doubleValue());
        teamInfo.setRank(redisJczqDggpMatchDTO.getHostRank());
        teamInfo.setTitle(redisJczqDggpMatchDTO.getHostTeam());
        teamInfo.setId(redisJczqDggpMatchDTO.getHostTeamId());
        ClientJczqDggpMatchVO.HistoryZj historyZj = new ClientJczqDggpMatchVO.HistoryZj();
        String hostRecentHistory = redisJczqDggpMatchDTO.getHostRecentHistory();
        if (StringUtils.isBlank(hostRecentHistory)) {
            historyZj.setWin(0);
            historyZj.setDraw(0);
            historyZj.setLose(0);
        } else {
            String[] hostRecentHistorys = hostRecentHistory.split(",");
            historyZj.setWin(Integer.parseInt(hostRecentHistorys[0]));
            historyZj.setDraw(Integer.parseInt(hostRecentHistorys[1]));
            historyZj.setLose(Integer.parseInt(hostRecentHistorys[2]));
        }

        teamInfo.setHistory(historyZj);
        List<BasedataTeamInjureDTO> hostTeamInjuresList = redisJczqDggpMatchDTO.getHostTeamInjuresList();
        if (CollectionUtils.isEmpty(hostTeamInjuresList)) {
            teamInfo.setInjure(new ArrayList<>());
        } else {
            List<ClientJczqDggpMatchVO.InjureInfoVO> injureInfoVOList = new ArrayList<>();
            for (BasedataTeamInjureDTO basedataTeamInjureDTO : hostTeamInjuresList) {
                ClientJczqDggpMatchVO.InjureInfoVO injureInfoVO = new ClientJczqDggpMatchVO.InjureInfoVO();
                injureInfoVO.setMain(basedataTeamInjureDTO.getMain());
                injureInfoVO.setPlayer(basedataTeamInjureDTO.getPlayerName());
                injureInfoVO.setReason(basedataTeamInjureDTO.getReason());
                injureInfoVOList.add(injureInfoVO);
            }
            teamInfo.setInjure(injureInfoVOList);
        }
        clientJczqDggpMatchVO.setHostTeam(teamInfo);

        teamInfo = new ClientJczqDggpMatchVO.TeamInfo();
        teamInfo.setName(redisJczqDggpMatchDTO.getVisitTeam().length() > 5 ? redisJczqDggpMatchDTO.getVisitTeam().substring(0, 5) : redisJczqDggpMatchDTO.getVisitTeam());
        BigDecimal visitTeamWinRate = redisJczqDggpMatchDTO.getVisitTeamWinRate();
        teamInfo.setOffer(visitTeamWinRate == null ? 0 : visitTeamWinRate.doubleValue());
        teamInfo.setRank(redisJczqDggpMatchDTO.getVisitRank());
        teamInfo.setTitle(redisJczqDggpMatchDTO.getVisitTeam());
        teamInfo.setId(redisJczqDggpMatchDTO.getVisitTeamId());
        historyZj = new ClientJczqDggpMatchVO.HistoryZj();
        String visitRecentHistory = redisJczqDggpMatchDTO.getVisitRecentHistory();
        if (StringUtils.isBlank(visitRecentHistory)) {
            historyZj.setWin(0);
            historyZj.setDraw(0);
            historyZj.setLose(0);
        } else {
            String[] visitRecentHistorys = visitRecentHistory.split(",");
            historyZj.setWin(Integer.parseInt(visitRecentHistorys[0]));
            historyZj.setDraw(Integer.parseInt(visitRecentHistorys[1]));
            historyZj.setLose(Integer.parseInt(visitRecentHistorys[2]));
        }
        teamInfo.setHistory(historyZj);
        List<BasedataTeamInjureDTO> visitTeamInjuresList = redisJczqDggpMatchDTO.getVisitTeamInjureList();
        if (CollectionUtils.isEmpty(visitTeamInjuresList)) {
            teamInfo.setInjure(new ArrayList<>());
        } else {
            List<ClientJczqDggpMatchVO.InjureInfoVO> injureInfoVOList = new ArrayList<>();
            for (BasedataTeamInjureDTO basedataTeamInjureDTO : visitTeamInjuresList) {
                ClientJczqDggpMatchVO.InjureInfoVO injureInfoVO = new ClientJczqDggpMatchVO.InjureInfoVO();
                injureInfoVO.setMain(basedataTeamInjureDTO.getMain());
                injureInfoVO.setPlayer(basedataTeamInjureDTO.getPlayerName());
                injureInfoVO.setReason(basedataTeamInjureDTO.getReason());
                injureInfoVOList.add(injureInfoVO);
            }
            teamInfo.setInjure(injureInfoVOList);
        }
        clientJczqDggpMatchVO.setVistorTeam(teamInfo);
        packageSpForDggpMatch(clientJczqDggpMatchVO, redisJczqDggpMatchDTO, playId);
        return clientJczqDggpMatchVO;
    }

    @Override
    public ResultModel getDggpAwardMatch(Object body) {

        ResultModel resultModel = new ResultModel();
        String playId = "9005";
        if (StringUtils.isNotBlank(String.valueOf(body))) {
            playId = String.valueOf(GfJsonUtil.parseObject(String.valueOf(body), Map.class).get("playId"));
        }

        List<RedisJczqDggpMatchDTO> jczqDggpMatch = basedataShareRedisService.getJczqDggpMatch();
        if (CollectionUtils.isEmpty(jczqDggpMatch)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("您好,当前没有推荐赛事!");
            resultModel.setData(new HashMap());
            return resultModel;
        }
        Integer finalPlayId = Integer.valueOf(playId);
        Date curDate = new Date();
        jczqDggpMatch = jczqDggpMatch.stream().filter(x -> x.getPlayTypeId().equals(finalPlayId) && x.getEndTime().after(curDate)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(jczqDggpMatch)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("您好,当前没有推荐赛事!");
            resultModel.setData(new HashMap());
            return resultModel;
        }
        List<RedisJczqDggpMatchDTO> dggpMatchOnSale = getDggpMatchOnSale(jczqDggpMatch, playId);
        if (CollectionUtils.isEmpty(dggpMatchOnSale)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("您好,当前没有推荐赛事!");
            resultModel.setData(new HashMap());
            return resultModel;
        }
        //获取单关配置数据
        List<BasedataDgMatchENT> dgMatchList = basedataShareRedisService.getDgMatchList();
        if (CollectionUtils.isEmpty(dgMatchList)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("您好,当前没有加奖赛事！");
            resultModel.setData(new HashMap());
            return resultModel;
        }
        dgMatchList = dgMatchList.stream().filter(x -> x.getPlaytypeId().equals(finalPlayId) && x.getRecommEndTime().after(curDate)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dgMatchList)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("您好,当前没有加奖赛事！");
            resultModel.setData(new HashMap());
            return resultModel;
        }
        dgMatchList.sort(new Comparator<BasedataDgMatchENT>() {
            @Override
            public int compare(BasedataDgMatchENT o1, BasedataDgMatchENT o2) {
                int order = o1.getOrder().compareTo(o2.getOrder());
                if (order == 0) {
                    return o1.getRecommEndTime().compareTo(o2.getRecommEndTime());
                }
                return order;
            }
        });
        List data = new ArrayList();
        for (BasedataDgMatchENT basedataDgMatchENT : dgMatchList) {
            RedisJczqDggpMatchDTO redisJczqDggpMatchDTO = dggpMatchOnSale.stream().filter(x -> x.getIssueMatchName().equals(basedataDgMatchENT.getIssueMatchName())).findFirst().get();
            if (redisJczqDggpMatchDTO == null) {
                continue;
            }
            ClientJczqDggpMatchVO clientJczqDggpMatchVO = packageJczqDggpDTO(redisJczqDggpMatchDTO, String.valueOf(finalPlayId));
            if (clientJczqDggpMatchVO == null) {
                continue;
            }
            clientJczqDggpMatchVO.setImg(basedataDgMatchENT.getBgImgUrl());
            data.add(clientJczqDggpMatchVO);
        }

        if (CollectionUtils.isEmpty(data)) {
            resultModel.setCode(ResultModel.ERROR);
            resultModel.setMsg("您好,当前没有推荐赛事!");
            resultModel.setData(new HashMap().put("list", Collections.EMPTY_LIST));
            return resultModel;
        }
        resultModel.setData(data);
        resultModel.setCode(ResultModel.SUCCESS);

        return resultModel;
    }

    //根据期次ID获取竞彩足球赛果
    @Override
    public ResultModel getJczqMatchResult(Object body) {
        ResultModel rm = new ResultModel();
        Map<String, Object> params = GfJsonUtil.parseObject(String.valueOf(body), Map.class);

        String issueId = String.valueOf(params.get("IssueID"));
        String pageNoParm = String.valueOf(params.get("PageNo"));
        String pageSizeParm = String.valueOf(params.get("PageSize"));

        if (StringUtils.isEmpty(issueId) || StringUtils.isEmpty(pageNoParm) || StringUtils.isEmpty(pageSizeParm)) {
            rm.setCode(ResponseCodeAndMsg.PARAM_ERROR);
            rm.setMsg(ResponseCodeAndMsg.MSG_PARAM_ERROR);
            return rm;
        }

        Integer pageNo = Integer.valueOf(pageNoParm);
        Integer pageSize = Integer.valueOf(pageSizeParm);
        List<JczqDataDTO> jczqDataList = basedataShareRedisService.getJczqMatchResultByIssueId(issueId);
        List<ClientJczqMatchResultVo> matchResultVoList;

        int size = jczqDataList.size();
        int count = (size + pageSize - 1) / pageSize;

        if (pageNo > count) {
            Map<String, Object> result = new HashMap<>();
            result.put("Count", jczqDataList.size());
            result.put("item", Collections.EMPTY_LIST);
            rm.setCode(0);
            rm.setData(result);
            return rm;
        }

        if (jczqDataList.size() <= pageSize) {
            matchResultVoList = screenJczqResult(jczqDataList);
        } else {
            int toIndex = pageNo * pageSize;
            if (toIndex >= jczqDataList.size()) {
                toIndex = jczqDataList.size();
            }
            List<JczqDataDTO> jczqDataDTOList = jczqDataList.subList((pageNo - 1) * pageSize, toIndex);
            matchResultVoList = screenJczqResult(jczqDataDTOList);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("Count", jczqDataList.size());
        result.put("item", matchResultVoList);
        rm.setCode(0);
        rm.setData(result);
        return rm;
    }

    private List<ClientJczqMatchResultVo> screenJczqResult(List<JczqDataDTO> jczqDataList) {
        List<ClientJczqMatchResultVo> matchResultVoList = new ArrayList<>();
        for (JczqDataDTO jczqDataDTO : jczqDataList) {
            if(jczqDataDTO.getAuditStatus() != 1){
                jczqDataDTO.setFullScore("");
                jczqDataDTO.setMatchResult("");
                jczqDataDTO.setHalfScore("");
            }
            ClientJczqMatchResultVo clientJczqMatchResultVo = new ClientJczqMatchResultVo();
            clientJczqMatchResultVo.setWeekday(jczqDataDTO.getWeekday());
            clientJczqMatchResultVo.setMatchNo(jczqDataDTO.getMatchNo());
            clientJczqMatchResultVo.setTournamentName(jczqDataDTO.getTournamentName());
            clientJczqMatchResultVo.setBuyEndTime(jczqDataDTO.getBuyEndTime());
            clientJczqMatchResultVo.setHostTeam(jczqDataDTO.getHostTeam());
            clientJczqMatchResultVo.setVisitTeam(jczqDataDTO.getVisitTeam());
            clientJczqMatchResultVo.setRq(jczqDataDTO.getRq());
            clientJczqMatchResultVo.setFullScore(jczqDataDTO.getFullScore());
            clientJczqMatchResultVo.setHalfScore(jczqDataDTO.getHalfScore());
            //            Map<String, String> cg = new LinkedHashMap<>();
            //            cg.put("RZ", jczqDataDTO.getMatchResult()); //前端需要确保RZ为第一个属性
            //            cg.put("SP", "");
            //            cg.put("DG", "");
            String spResult ="";
            if(jczqDataDTO.getAuditStatus() == 1){
                String[] results =  jczqDataDTO.getMatchResult().split(",");
                spResult = String.format("%s,%s,%s,%s,%s", ScoreUtil.generateJczqSp(jczqDataDTO.getRqspfSp(),9001,results[0]),
                    ScoreUtil.generateJczqSp(jczqDataDTO.getJqsSp(),9002,results[1]),
                    ScoreUtil.generateJczqSp(jczqDataDTO.getCbfSp(),9003,results[2]),
                    ScoreUtil.generateJczqSp(jczqDataDTO.getBqcSp(),9004,results[3]),
                    ScoreUtil.generateJczqSp(jczqDataDTO.getSpfSp(),9006,results[4])
                );
            }

            clientJczqMatchResultVo.setCg("{RZ:\"" + jczqDataDTO.getMatchResult() + "\", SP:\"" + spResult + "\", DG:\"\"}");
            matchResultVoList.add(clientJczqMatchResultVo);
        }
        return matchResultVoList;
    }

    @Override
    public ResultModel getJclqMatchResult(Object body) {
        ResultModel rm = new ResultModel();
        Map<String, Object> params = GfJsonUtil.parseObject(String.valueOf(body), Map.class);
        String issueId = String.valueOf(params.get("IssueID"));
        String pageNoParm = String.valueOf(params.get("PageNo"));
        String pageSizeParm = String.valueOf(params.get("PageSize"));
        if (StringUtils.isEmpty(issueId) || StringUtils.isEmpty(pageNoParm) || StringUtils.isEmpty(pageSizeParm)) {
            rm.setCode(ResponseCodeAndMsg.PARAM_ERROR);
            rm.setMsg(ResponseCodeAndMsg.MSG_PARAM_ERROR);
            return rm;
        }

        Integer pageNo = Integer.valueOf(pageNoParm);
        Integer pageSize = Integer.valueOf(pageSizeParm);
        List<JclqDataDTO> jclqDataList = basedataShareRedisService.getJclqMatchResultByIssueId(issueId);
        List<ClientJclqMatchResultVo> matchResultVoList;

        int size = jclqDataList.size();
        int count = (size + pageSize - 1) / pageSize;

        if (pageNo > count) {
            Map<String, Object> result = new HashMap<>();
            result.put("Count", jclqDataList.size());
            result.put("item", Collections.EMPTY_LIST);
            rm.setCode(0);
            rm.setData(result);
            return rm;
        }

        if (jclqDataList.size() <= pageSize) {
            matchResultVoList = screenJclqResult(jclqDataList);
        } else {
            int toIndex = pageNo * pageSize;
            if (toIndex >= jclqDataList.size()) {
                toIndex = jclqDataList.size();
            }
            List<JclqDataDTO> jclqDataDTOList = jclqDataList.subList((pageNo - 1) * pageSize, toIndex);
            matchResultVoList = screenJclqResult(jclqDataDTOList);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("Count", jclqDataList.size());
        result.put("item", matchResultVoList);
        rm.setCode(0);
        rm.setData(result);
        return rm;
    }

    private List<ClientJclqMatchResultVo> screenJclqResult(List<JclqDataDTO> jclqDataList) {
        List<ClientJclqMatchResultVo> matchResultVoList = new ArrayList<>();
        for (JclqDataDTO jclqDataDTO : jclqDataList) {
            if(jclqDataDTO.getAuditStatus() != 1){
                jclqDataDTO.setFullScore("");
                jclqDataDTO.setMatchResult("");
            }
            ClientJclqMatchResultVo clientJclqMatchResultVo = new ClientJclqMatchResultVo();
            clientJclqMatchResultVo.setWeekday(jclqDataDTO.getWeekday());
            clientJclqMatchResultVo.setTournamentName(jclqDataDTO.getTournamentName());
            clientJclqMatchResultVo.setMatchNo(jclqDataDTO.getIssueMatchName());
            clientJclqMatchResultVo.setBuyEndTime(jclqDataDTO.getBuyEndTime());
            clientJclqMatchResultVo.setHostTeam(jclqDataDTO.getHostTeam());
            clientJclqMatchResultVo.setVisitTeam(jclqDataDTO.getVisitTeam());
            clientJclqMatchResultVo.setFullScore(jclqDataDTO.getFullScore());
            clientJclqMatchResultVo.setHrz("");
            //            Map<String, Object> cg = new LinkedHashMap<>();
            //            cg.put("RZ", jclqDataDTO.getMatchResult());
            //            cg.put("SP", "");
            //            cg.put("DG", "");
            String spResult ="";
            if(jclqDataDTO.getAuditStatus() == 1){
                String[] results =  jclqDataDTO.getMatchResult().split(",");
                spResult = String.format("%s,%s,%s,%s", ScoreUtil.getJclqSp(jclqDataDTO.getRfsfSp(),9101,results[0]),
                    ScoreUtil.getJclqSp(jclqDataDTO.getSfSp(),9102,results[1]),
                    ScoreUtil.getJclqSp(jclqDataDTO.getSfcSp(),9103,results[2]),
                    ScoreUtil.getJclqSp(jclqDataDTO.getDxfSp(),9104,results[3])
                );
            }
            clientJclqMatchResultVo.setCg("{RZ:\"" + jclqDataDTO.getMatchResult() + "\", SP:\"" + spResult + "\", DG:\"\"}");
            matchResultVoList.add(clientJclqMatchResultVo);

        }

        return matchResultVoList;
    }


}
