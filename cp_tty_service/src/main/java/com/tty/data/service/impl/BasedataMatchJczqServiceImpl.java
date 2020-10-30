package com.tty.data.service.impl;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.data.context.BasedataRedisKeys;
import com.tty.data.dao.BasedataMatchJczqDao;
import com.tty.data.dao.entity.BasedataMatchJczqENT;
import com.tty.data.service.BasedataMatchJczqService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("basedataMatchJczqService")
public class BasedataMatchJczqServiceImpl implements BasedataMatchJczqService {

    @Autowired
    private BasedataMatchJczqDao basedataMatchJczqDao;

    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;


    private static final Logger logger = LoggerFactory.getLogger(BasedataMatchJczqService.class);


    public ExtModel listBasedataMatchJczqByHostTeamAndVisitTeam(JSONObject jsonParm, ExtModel result) {
        result.setData(basedataMatchJczqDao.listBasedataMatchJczqByHostTeamAndVisitTeam(
            jsonParm.getInteger("page"),
            jsonParm.getInteger("limit"),
            jsonParm.getJSONObject("data")
            )
        );
        result.setTotal(basedataMatchJczqDao.listBasedataMatchJczqByHostTeamAndVisitTeamCount(jsonParm.getJSONObject("data")));
        return result;
    }

    public void findBasedataMatchJczq(Integer matchId, ExtModel result) {
        BasedataMatchJczqENT ent = basedataMatchJczqDao.findBasedataMatchJczq(matchId);
        result.setData(ent);
    }

    /**
     * 根据比赛编号或者去今日对阵信息
     */
    public BasedataMatchJczqENT getCurrentJczqMatch(String traceId, String issueMatchName) {
        //从redis 获取对阵信息
        BasedataMatchJczqENT basedataMatchJczqENT = getJczqMatchFromRedis(issueMatchName);
        if (null != basedataMatchJczqENT) {
            return basedataMatchJczqENT;
        }

        //从数据库获取对阵信息
        List<BasedataMatchJczqENT> list = basedataMatchJczqDao.listBasedataMatchJczqByIssueMatchName(issueMatchName);
        if (!CollectionUtils.isEmpty(list)) {
            BasedataMatchJczqENT dbMatch = list.get(0);
            userRedis.hset(BasedataRedisKeys.BASEDATA_JCZQ_MATCHS, issueMatchName, GfJsonUtil.toJSONString(dbMatch));
            logger.error("[推送][期次对阵][竞彩足球]-缓存中不存在，但数据库中存在!!!; traceId{},jczq_match{}", traceId, GfJsonUtil.toJSONString(dbMatch));
            return dbMatch;
        }

        return null;
    }

    /**
     * 获取缓存中所有的对阵数据
     */
    public BasedataMatchJczqENT getJczqMatchFromRedis(String issueMatchName) {
        String value = userRedis.hget(BasedataRedisKeys.BASEDATA_JCZQ_MATCHS, issueMatchName);
        return GfJsonUtil.parseObject(value, BasedataMatchJczqENT.class);
    }
}
