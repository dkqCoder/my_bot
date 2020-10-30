package com.tty.data.service.impl;


import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.data.context.BasedataLotteryTagEnum;
import com.tty.data.context.BasedataRedisKeys;
import com.tty.data.context.BasedataSharedRedisKeys;
import com.tty.data.dao.BasedataDgMatchDao;
import com.tty.data.dao.BasedataIssueDao;
import com.tty.data.dao.BasedataLotteryDao;
import com.tty.data.dao.entity.BasedataIssueENT;
import com.tty.data.dao.entity.BasedataLotteryENT;
import com.tty.data.dto.JclqDataDTO;
import com.tty.data.dto.JczqDataDTO;
import com.tty.data.dto.redis.RedisJclqMatchDTO;
import com.tty.data.dto.redis.RedisJclqMatchStopSaleDTO;
import com.tty.data.dto.redis.RedisJczqDggpMatchDTO;
import com.tty.data.service.BaseDataRetrieveAddiMatchService;
import com.tty.data.service.BasedataLotteryTagRelationshipService;
import com.tty.data.dao.entity.BasedataLotteryTagRelationshipENT;
import com.tty.data.dto.redis.RedisJczqMatchDTO;
import com.tty.data.dto.redis.RedisJczqMatchStopSaleDTO;
import com.tty.data.dao.entity.BasedataDgMatchENT;
import com.tty.data.dto.redis.RedisLotteryDTO;
import com.tty.data.service.BasedataShardedRedisService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 操作sharedRedisManager
 */
@Service("basedataShardedRedisService")
public class BasedataShardedRedisServiceImpl implements BasedataShardedRedisService {

    @Autowired
    @Qualifier("jedisClusterFactory")
    private JedisClusterFactory jedisClusterFactory;

    @Autowired
    private BasedataDgMatchDao basedataDgMatchDao;

    @Autowired
    private BasedataLotteryTagRelationshipService basedataLotteryTagRelationshipService;

    @Autowired
    BasedataLotteryDao basedataLotteryDao;

    @Autowired
    private BasedataIssueDao basedataIssueDao;

    @Autowired
    BaseDataRetrieveAddiMatchService baseDataRetrieveAddiMatchService;


    @Override
    public List<RedisJczqMatchDTO> getJczqMatchIssueList() {
        String key = BasedataSharedRedisKeys.BASEDATA_JCZQ_MATCH;
        Map<String, String> map = jedisClusterFactory.hgetAll(key);
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        List<RedisJczqMatchDTO> list = new ArrayList<RedisJczqMatchDTO>();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            list.add(GfJsonUtil.parseObject(stringStringEntry.getValue(), RedisJczqMatchDTO.class));
        }
        return list;
    }

    @Override
    public List<RedisJczqMatchStopSaleDTO> getJczqStopSale() {
        String key = String.format(BasedataSharedRedisKeys.BASEDATA_JCZQ_MATCH_STOP_SALE);
        Map<String, String> map = jedisClusterFactory.hgetAll(key);
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        List<RedisJczqMatchStopSaleDTO> list = new ArrayList<RedisJczqMatchStopSaleDTO>();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            list.add(GfJsonUtil.parseObject(stringStringEntry.getValue(), RedisJczqMatchStopSaleDTO.class));
        }
        return list;
    }

    @Override
    public List<BasedataDgMatchENT> getDgMatchList() {
        String key = BasedataRedisKeys.BASEDATA_MATCH_CFG;
        String valueStr = jedisClusterFactory.get(key);
        List<BasedataDgMatchENT> dgMatchENTList = null;
        if (StringUtils.isNotBlank(valueStr)) {
            dgMatchENTList = GfJsonUtil.parseArray(valueStr, BasedataDgMatchENT.class);
            return dgMatchENTList;
        } else {
            List<BasedataDgMatchENT> basedataDgMatchs = basedataDgMatchDao.listBasedataDgMatch();
            if (CollectionUtils.isEmpty(basedataDgMatchs)) {
                jedisClusterFactory.del(key);
                return null;
            }
            jedisClusterFactory.set(key, GfJsonUtil.toJSONString(basedataDgMatchs));
            return basedataDgMatchs;
        }
    }

    @Override
    public RedisLotteryDTO getLotteryByLotteryId(Integer lotteryId) {
        String val = jedisClusterFactory.hget(BasedataSharedRedisKeys.BASEDATA_LOTTERY, String.valueOf(lotteryId));

        if (StringUtils.isNotEmpty(val)) {
            RedisLotteryDTO ent = GfJsonUtil.parseObject(val, RedisLotteryDTO.class);
            return ent;
        }

        //缓存失效从数据库获取
        List<BasedataLotteryTagRelationshipENT> relationshipList = basedataLotteryTagRelationshipService.listAllRelationship();
        Map<Integer, List<String>> lotteryId_tagCodeList = new HashMap<>();
        for (BasedataLotteryTagRelationshipENT ent : relationshipList) {
            if (lotteryId_tagCodeList.containsKey(ent.getLotteryId())) {
                if (lotteryId_tagCodeList.get(ent.getLotteryId()) != null) {
                    List<String> itemList = lotteryId_tagCodeList.get(ent.getLotteryId());
                    itemList.add(ent.getTagCode());
                    lotteryId_tagCodeList.put(ent.getLotteryId(), itemList);
                }
            } else {
                List<String> tagCodeList = new ArrayList<>();
                tagCodeList.add(ent.getTagCode());
                lotteryId_tagCodeList.put(ent.getLotteryId(), tagCodeList);
            }
        }

        BasedataLotteryENT lottery = basedataLotteryDao.getBasedataLottery(lotteryId);
        RedisLotteryDTO dto = new RedisLotteryDTO();
        dto.setLotteryId(lottery.getLotteryId());
        dto.setLotteryName(lottery.getLotteryName());
        dto.setShortName(lottery.getShortName());
        dto.setLotteryCode(lottery.getLotteryCode());
        dto.setWinNumberTemplate(lottery.getWinNumberTemplate());
        dto.setMaxChaseCount(lottery.getMaxChaseCount());
        dto.setEndbuyBeforeSecond(lottery.getEndbuyBeforeSecond());
        dto.setStartbuyAfterChaseSecond(lottery.getStartbuyAfterChaseSecond());
        dto.setEndissueAfterOpenSecond(lottery.getEndissueAfterOpenSecond());
        dto.setExtraWinningsStatus(lottery.getExtraWinningsStatus());
        dto.setStatus(lottery.getStatus());
        dto.setTags(lotteryId_tagCodeList.get(lottery.getLotteryId()));
        dto.setDescStatus(lottery.getDescStatus());
        dto.setDetailDesc(lottery.getDetailDesc());
        dto.setLotteryIcon(lottery.getLotteryIcon());
        jedisClusterFactory.hset(BasedataSharedRedisKeys.BASEDATA_LOTTERY, String.valueOf(lottery.getLotteryId()), GfJsonUtil.toJSONString(dto));

        return dto;
    }

    @Override
    public List<BasedataIssueENT> getIssuesByLotteryId(String lotteryId) {
        String key = String.format(BasedataSharedRedisKeys.BASEDATA_ISSUES_BY_LOTTERY, lotteryId);
        Map<String, String> map = jedisClusterFactory.hgetAll(key);
        if (MapUtils.isEmpty(map)) {
            List<BasedataIssueENT> recentIssueList = getBasedataIssueListByLotteryId(lotteryId);
            new Thread(() -> {
                String issueName_issue_key = String.format(BasedataSharedRedisKeys.BASEDATA_ISSUES_BY_LOTTERY, lotteryId);
                String issueId_issue_key = String.format(BasedataSharedRedisKeys.BASEDATA_ISSUESID_BY_LOTTERY, lotteryId);
                for (BasedataIssueENT issue : recentIssueList) {
                    jedisClusterFactory.hset(issueName_issue_key, String.valueOf(issue.getIssueName()), GfJsonUtil.toJSONString(issue));
                    jedisClusterFactory.hset(issueId_issue_key, String.valueOf(issue.getIssueId()), GfJsonUtil.toJSONString(issue));
                }
            }, "同步最近期次至缓存").start();
            return recentIssueList;
        }
        List<BasedataIssueENT> list = new ArrayList<BasedataIssueENT>();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            list.add(GfJsonUtil.parseObject(stringStringEntry.getValue(), BasedataIssueENT.class));
        }
        return list;
    }

    private List<BasedataIssueENT> getBasedataIssueListByLotteryId(String lotteryId) {
        BasedataLotteryTagEnum tagEnum = BasedataLotteryTagEnum.NOTAG;
        if (basedataLotteryTagRelationshipService.isGPC(Integer.valueOf(lotteryId))) {
            tagEnum = BasedataLotteryTagEnum.GPC;
        } else if (basedataLotteryTagRelationshipService.isJcOrZc(Integer.valueOf(lotteryId))) {
            tagEnum = BasedataLotteryTagEnum.JCZC;
        } else if (basedataLotteryTagRelationshipService.isJcc(Integer.valueOf(lotteryId))) {
            tagEnum = BasedataLotteryTagEnum.JCC;
        }
        return basedataIssueDao.getBasedataIssue(Integer.valueOf(lotteryId), tagEnum);
    }

    @Override
    public List<RedisJclqMatchDTO> getJcLqMatch() {
        String key = String.format(BasedataSharedRedisKeys.BASEDATA_JCLQ_MATCH);
        Map<String, String> map = jedisClusterFactory.hgetAll(key);
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        List<RedisJclqMatchDTO> list = new ArrayList<RedisJclqMatchDTO>();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            list.add(GfJsonUtil.parseObject(stringStringEntry.getValue(), RedisJclqMatchDTO.class));
        }
        return list;
    }

    @Override
    public List<RedisJclqMatchStopSaleDTO> getJcLqMatchStopSale() {
        String key = String.format(BasedataSharedRedisKeys.BASEDATA_JCLQ_MATCH_STOP_SALE);
        Map<String, String> map = jedisClusterFactory.hgetAll(key);
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        List<RedisJclqMatchStopSaleDTO> list = new ArrayList<RedisJclqMatchStopSaleDTO>();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            list.add(GfJsonUtil.parseObject(stringStringEntry.getValue(), RedisJclqMatchStopSaleDTO.class));
        }
        return list;
    }

    /**
     * 获取最近竞彩足球单关固陪对阵信息
     *
     * @return
     */
    @Override
    public List<RedisJczqDggpMatchDTO> getJczqDggpMatch() {
        String key = String.format(BasedataSharedRedisKeys.BASEDATA_DGGP_MATCH);
        Map<String, String> map = jedisClusterFactory.hgetAll(key);
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        List<RedisJczqDggpMatchDTO> list = new ArrayList<RedisJczqDggpMatchDTO>();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            list.add(GfJsonUtil.parseObject(stringStringEntry.getValue(), RedisJczqDggpMatchDTO.class));
        }
        return list;
    }

    /**
     * 竞彩足球-开奖期次对阵 已开奖对阵信息
     *
     * @param issueId
     * @return
     */
    @Override
    public List<JczqDataDTO> getJczqMatchResultByIssueId(String issueId) {
        return baseDataRetrieveAddiMatchService.getSeparateMatchResultByIssueId("90",issueId).stream().map(
            (String p) -> GfJsonUtil.parseObject(p, JczqDataDTO.class)
        ).collect(Collectors.toList());
    }

    /**
     * 竞彩篮球-开奖期次对阵 已开奖对阵信息
     *
     * @param issueId
     * @return
     */
    @Override
    public List<JclqDataDTO> getJclqMatchResultByIssueId(String issueId) {
        return baseDataRetrieveAddiMatchService.getSeparateMatchResultByIssueId("91",issueId).stream().map(
            (String p) -> GfJsonUtil.parseObject(p, JclqDataDTO.class)
        ).collect(Collectors.toList());
    }

}
