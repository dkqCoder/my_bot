package com.tty.task.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.tty.common.utils.HttpUtils;
import com.tty.data.dto.JczqDataDTO;
import com.tty.task.context.CrawlingSourceContext;
import com.tty.task.context.CrawlingUrlContext;
import com.tty.task.context.LogContext;
import com.tty.task.dao.CrawlingDao;
import com.tty.task.dao.impl.CrawlingDaoImpl;
import com.tty.task.ent.CompetitionEnt;
import com.tty.task.ent.crawling.jcgw.*;
import com.tty.task.service.CompetitionCrawlingService;
import com.tty.task.utils.AnalysisUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Int;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author: dukeqiang
 * @Date: 2020/10/12 17:57
 */
@Service("competitionCrawlingService")
public class CompetitionCrawlingServiceImpl implements CompetitionCrawlingService {
    protected final Logger logger = LoggerFactory.getLogger(CompetitionCrawlingServiceImpl.class);

    @Autowired
    private CrawlingDao crawlingDao;

    @Override
    public void crawlingWDLInfo(String traceId,String source) {
        try {
            String wdlResult = "";
            if (source.equals(CrawlingSourceContext.JCGW_SOURCE)) {
                List<WdlEnt> wdlEntList = new ArrayList<WdlEnt>();
                wdlResult = HttpUtils.sendGet(CrawlingUrlContext.JCGW_WDL_URL,"",3000,3000);

                JSONObject data = (JSONObject) JSONObject.parseObject(wdlResult).get("data");
                Set<String> keys = data.keySet();
                keys.stream().forEach(kes->{
                    WdlEnt wdlEnt = JSON.parseObject(JSONObject.toJSONString(data.get(kes)),WdlEnt.class);
                    wdlEntList.add(wdlEnt);
                });

                wdlEntList.stream().forEach(wdlEnt -> {
                    JczqDataDTO jczqDataDTO = new JczqDataDTO();
                    jczqDataDTO.setIssueName(wdlEnt.getB_date().replace("-",""));
                    jczqDataDTO.setIssueMatchName(jczqDataDTO.getIssueName() + AnalysisUtils.analysisJCGWMatchNo(wdlEnt.getNum()));
                    jczqDataDTO.setWeekday(AnalysisUtils.analysisJCGWWeekDay(wdlEnt.getNum()));
                    jczqDataDTO.setWeekdayName(AnalysisUtils.analysisJCGWWeekName(wdlEnt.getNum()));
                    jczqDataDTO.setMatchNo(AnalysisUtils.analysisJCGWMatchNo(wdlEnt.getNum()));
                    jczqDataDTO.setHostTeam(wdlEnt.getH_cn());
                    jczqDataDTO.setHostTeamId(Integer.valueOf(wdlEnt.getH_id()));
                    jczqDataDTO.setVisitTeam(wdlEnt.getA_cn());
                    jczqDataDTO.setVisitTeamId(Integer.valueOf(wdlEnt.getA_id()));
                    jczqDataDTO.setMatchStartTime(AnalysisUtils.analysisJCGWMatchStartTime(wdlEnt.getDate(),wdlEnt.getTime()));
//                    jczqDataDTO.setBuyEndTime(); //todo:比赛销售截止日期
                    jczqDataDTO.setRq(Integer.valueOf(wdlEnt.getHhad().getFixedodds()));
//                    jczqDataDTO.setEuroOdds();
                    jczqDataDTO.setSpfSp(wdlEnt.getHad().getA() + "," + wdlEnt.getHad().getD() + "," + wdlEnt.getHad().getH());
                    jczqDataDTO.setRqspfSp(wdlEnt.getHhad().getA() + "," + wdlEnt.getHhad().getD() + "," + wdlEnt.getHhad().getH());
                    jczqDataDTO.setMatchId(Integer.valueOf(wdlEnt.getId()));
                    jczqDataDTO.setTournamentId(Integer.valueOf(wdlEnt.getL_id()));
                    jczqDataDTO.setTournamentName(wdlEnt.getL_cn_abbr());


                    crawlingDao.saveWDLCompetitionInfo(traceId,jczqDataDTO);
                });
            } else if (source.equals(CrawlingSourceContext.WB_SOURCE)) {

            } else if (source.equals(CrawlingSourceContext.WB_SOURCE)) {

            } else {

            }
        } catch (Exception e) {
            logger.error("{}:胜平负玩法信息异常,e:{},traceId:{}", LogContext.CRAWLING_LOG_PRE, LogExceptionStackTrace.erroStackTrace(e),traceId);
        }
    }

    @Override
    public void crawlingLetBallWDLInfo(String traceId, String source) {

    }

    @Override
    public void crawlingCrsWDLInfo(String traceId, String source) {
        try {
            String crsResult = "";
            if (source.equals(CrawlingSourceContext.JCGW_SOURCE)) {
                List<CrsEnt> crsEntList = new ArrayList<>();
                crsResult = HttpUtils.sendGet(CrawlingUrlContext.JCGW_CRS_WDL_URL,"",3000,3000);
                JSONObject data = (JSONObject) JSONObject.parseObject(crsResult).get("data");
                Set<String> keys = data.keySet();
                keys.stream().forEach(key->{
                    CrsEnt crsEnt = JSON.parseObject(JSONObject.toJSONString(data.get(key)),CrsEnt.class);
                    crsEntList.add(crsEnt);
                });
                crsEntList.stream().forEach(crsEnt -> {
                    JczqDataDTO jczqDataDTO = new JczqDataDTO();
                    jczqDataDTO.setIssueName(crsEnt.getB_date().replace("-",""));
                    jczqDataDTO.setIssueMatchName(jczqDataDTO.getIssueName() + AnalysisUtils.analysisJCGWMatchNo(crsEnt.getNum()));
                    String crs = crsEnt.getCrs();
                    JSONObject crsJob = JSONObject.parseObject(crs);
                    List<Object> sbfList = new ArrayList<>();
                    sbfList.add(crsJob.get("-1-a"));
                    sbfList.add(crsJob.get("-1-d"));
                    sbfList.add(crsJob.get("-1-h"));
                    sbfList.add(crsJob.get("0000"));
                    sbfList.add(crsJob.get("0001"));
                    sbfList.add(crsJob.get("0002"));
                    sbfList.add(crsJob.get("0003"));
                    sbfList.add(crsJob.get("0004"));
                    sbfList.add(crsJob.get("0005"));
                    sbfList.add(crsJob.get("0100"));
                    sbfList.add(crsJob.get("0101"));
                    sbfList.add(crsJob.get("0102"));
                    sbfList.add(crsJob.get("0103"));
                    sbfList.add(crsJob.get("0104"));
                    sbfList.add(crsJob.get("0105"));
                    sbfList.add(crsJob.get("0200"));
                    sbfList.add(crsJob.get("0201"));
                    sbfList.add(crsJob.get("0202"));
                    sbfList.add(crsJob.get("0203"));
                    sbfList.add(crsJob.get("0204"));
                    sbfList.add(crsJob.get("0205"));
                    sbfList.add(crsJob.get("0300"));
                    sbfList.add(crsJob.get("0301"));
                    sbfList.add(crsJob.get("0302"));
                    sbfList.add(crsJob.get("0303"));
                    sbfList.add(crsJob.get("0400"));
                    sbfList.add(crsJob.get("0401"));
                    sbfList.add(crsJob.get("0402"));
                    sbfList.add(crsJob.get("0500"));
                    sbfList.add(crsJob.get("0501"));
                    sbfList.add(crsJob.get("0502"));

                    jczqDataDTO.setCbfSp(StringUtils.join(sbfList,","));

                    crawlingDao.saveCrsCompetitionInfo(traceId,jczqDataDTO);
                });
            }
        } catch (Exception e) {
            logger.error("{}:比分玩法信息异常,e:{},traceId:{}", LogContext.CRAWLING_LOG_PRE, LogExceptionStackTrace.erroStackTrace(e),traceId);
        }
    }

    @Override
    public void crawlingGoalsWDLInfo(String traceId, String source) {
        try {
            String goalResult = "";
            if (source.equals(CrawlingSourceContext.JCGW_SOURCE)) {
                List<GoalsEnt> goalsEntList = new ArrayList<>();
                goalResult = HttpUtils.sendGet(CrawlingUrlContext.JCGW_GOALS_WDL_URL,"",3000,3000);
                JSONObject data = (JSONObject) JSONObject.parseObject(goalResult).get("data");
                Set<String> keys = data.keySet();
                keys.stream().forEach(kes->{
                    GoalsEnt goalsEnt = JSON.parseObject(JSONObject.toJSONString(data.get(kes)),GoalsEnt.class);
                    goalsEntList.add(goalsEnt);
                });

                goalsEntList.stream().forEach(goalsEnt -> {
                    JczqDataDTO jczqDataDTO = new JczqDataDTO();
                    jczqDataDTO.setIssueName(goalsEnt.getB_date().replace("-",""));
                    jczqDataDTO.setIssueMatchName(jczqDataDTO.getIssueName() + AnalysisUtils.analysisJCGWMatchNo(goalsEnt.getNum()));
                    List<String> ttgList = new ArrayList<>();
                    Ttg ttg = goalsEnt.getTtg();
                    ttgList.add(ttg.getS0());
                    ttgList.add(ttg.getS1());
                    ttgList.add(ttg.getS2());
                    ttgList.add(ttg.getS3());
                    ttgList.add(ttg.getS4());
                    ttgList.add(ttg.getS5());
                    ttgList.add(ttg.getS6());
                    ttgList.add(ttg.getS7());
                    jczqDataDTO.setJqsSp(StringUtils.join(ttgList,","));
                    crawlingDao.saveGoalsCompetitionInfo(traceId,jczqDataDTO);
                });
            }
        } catch (Exception e) {
            logger.error("{}:进球数玩法信息异常,e:{},traceId:{}", LogContext.CRAWLING_LOG_PRE, LogExceptionStackTrace.erroStackTrace(e),traceId);
        }
    }

    @Override
    public void crawlingHafuWDLInfo(String traceId, String source) {
        try {
            String hafuResult = "";
            if (source.equals(CrawlingSourceContext.JCGW_SOURCE)) {
                List<HafuEnt> hafuEntList = new ArrayList<>();
                hafuResult = HttpUtils.sendGet(CrawlingUrlContext.JCGW_HAFU_WDL_URL,"",3000,3000);
                JSONObject data = (JSONObject) JSONObject.parseObject(hafuResult).get("data");
                Set<String> keys = data.keySet();
                keys.stream().forEach(kes->{
                    HafuEnt hafuEnt = JSON.parseObject(JSONObject.toJSONString(data.get(kes)),HafuEnt.class);
                    hafuEntList.add(hafuEnt);
                });

                hafuEntList.stream().forEach(hafuEnt -> {
                    JczqDataDTO jczqDataDTO = new JczqDataDTO();
                    jczqDataDTO.setIssueName(hafuEnt.getB_date().replace("-",""));
                    jczqDataDTO.setIssueMatchName(jczqDataDTO.getIssueName() + AnalysisUtils.analysisJCGWMatchNo(hafuEnt.getNum()));
                    List<String> hafuList = new ArrayList<>();
                    Hafu hafu = hafuEnt.getHafu();
                    hafuList.add(hafu.getAa());
                    hafuList.add(hafu.getAd());
                    hafuList.add(hafu.getAh());
                    hafuList.add(hafu.getDa());
                    hafuList.add(hafu.getDd());
                    hafuList.add(hafu.getDh());
                    hafuList.add(hafu.getHa());
                    hafuList.add(hafu.getHd());
                    hafuList.add(hafu.getHh());
                    jczqDataDTO.setBqcSp(StringUtils.join(hafuList,","));
                    crawlingDao.saveHafuCompetitionInfo(traceId,jczqDataDTO);
                });
            } else {

            }
        } catch (Exception e) {
            logger.error("{}:半全场玩法信息异常,e:{},traceId:{}", LogContext.CRAWLING_LOG_PRE, LogExceptionStackTrace.erroStackTrace(e),traceId);
        }
    }


    public static void main(String[] args) {
        new CompetitionCrawlingServiceImpl().crawlingWDLInfo("123",CrawlingSourceContext.JCGW_SOURCE);
    }
}


