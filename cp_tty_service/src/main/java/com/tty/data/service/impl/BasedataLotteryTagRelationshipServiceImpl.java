package com.tty.data.service.impl;

import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.data.common.util.ListUtil;
import com.tty.data.context.BasedataSharedRedisKeys;
import com.tty.data.dao.BasedataLotteryTagRelationshipDao;
import com.tty.data.service.BasedataLotteryTagRelationshipService;
import com.tty.data.dao.entity.BasedataLotteryTagRelationshipENT;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("basedataLotteryTagRelationshipService")
public class BasedataLotteryTagRelationshipServiceImpl implements BasedataLotteryTagRelationshipService {

    @Autowired
    @Qualifier("jedisClusterFactory")
    private JedisClusterFactory jedisClusterFactory;

    @Autowired
    private BasedataLotteryTagRelationshipDao basedataLotteryTagRelationshipDao;


    @Transactional(readOnly = true)
    @DataSource(name = "dataDataSourceRead")
    @Override
    public List<BasedataLotteryTagRelationshipENT> listAllRelationship() {
        Map<String, String> relationshipCache = jedisClusterFactory.hgetAll(BasedataSharedRedisKeys.BASEDATA_LOTTERY_RELATIONSHIP);
        List<BasedataLotteryTagRelationshipENT> resultRows = new ArrayList<>();
        if (relationshipCache != null && relationshipCache.size() != 0) {
            for (String relationShip : relationshipCache.values()) {
                BasedataLotteryTagRelationshipENT ent = GfJsonUtil.parseObject(relationShip, BasedataLotteryTagRelationshipENT.class);
                resultRows.add(ent);
            }
            return resultRows;
        } else {
            List<BasedataLotteryTagRelationshipENT> rows = basedataLotteryTagRelationshipDao.listAllRelationship();
            for (BasedataLotteryTagRelationshipENT row : rows) {
                jedisClusterFactory.hset(BasedataSharedRedisKeys.BASEDATA_LOTTERY_RELATIONSHIP, String.valueOf(row.getId()), GfJsonUtil.toJSONString(row));
            }
            return rows;
        }
    }

    /**
     * @Description 是否高频彩
     */
    @DataSource(name = "dataDataSourceRead")
    @Transactional
    @Override
    public Boolean isGPC(Integer lotteryId) {
        List<BasedataLotteryTagRelationshipENT> list = basedataLotteryTagRelationshipDao.listLotteryTagRelationByLotteryId(lotteryId);
        return ListUtil.filter(x -> x.getTagCode().equals("GPC"), list).size() > 0;
    }

    /**
     * @Description 是否竞彩或足彩
     */
    @DataSource(name = "dataDataSourceRead")
    @Transactional
    @Override
    public Boolean isJcOrZc(Integer lotteryId) {
        List<BasedataLotteryTagRelationshipENT> list = basedataLotteryTagRelationshipDao.listLotteryTagRelationByLotteryId(lotteryId);
        boolean isJcOrZc = ListUtil.filter(x -> "JC".equals(x.getTagCode()), list).size() > 0;
        if (isJcOrZc) {
            return isJcOrZc;
        }
        return ListUtil.filter(x -> "ZC".equals(x.getTagCode()), list).size() > 0;
    }

    /**
     * @Description 是否奖池彩
     */
    @DataSource(name = "dataDataSourceRead")
    @Transactional
    @Override
    public Boolean isJcc(Integer lotteryId) {
        List<BasedataLotteryTagRelationshipENT> list = basedataLotteryTagRelationshipDao.listLotteryTagRelationByLotteryId(lotteryId);
        return ListUtil.filter(x -> x.getTagCode().equals("JCC"), list).size() > 0;
    }
}
