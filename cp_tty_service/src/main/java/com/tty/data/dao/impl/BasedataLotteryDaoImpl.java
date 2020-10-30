package com.tty.data.dao.impl;

import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.data.context.BasedataRedisKeys;
import com.tty.data.dao.BasedataLotteryDao;
import com.tty.data.dao.entity.BasedataLotteryENT;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author zhuxinhai
 */
@Repository("basedataLotteryDao")
public class BasedataLotteryDaoImpl extends BaseDao<BasedataLotteryENT> implements BasedataLotteryDao {

    @Autowired
    @Qualifier("jedisClusterFactory")
    private JedisClusterFactory jedisClusterFactory;


    @Override
    public BasedataLotteryENT getBasedataLottery(Integer lotteryId) {
        String key = String.format(BasedataRedisKeys.BASEDATA_ADMIN_LOTTERY_INFO, lotteryId);
        String val = jedisClusterFactory.get(key);

        BasedataLotteryENT ent;
        if (val == null) {
            ent = findById(lotteryId);
            jedisClusterFactory.set(key, GfJsonUtil.toJSONString(ent));
        } else {
            ent = GfJsonUtil.parseObject(val, BasedataLotteryENT.class);
        }
        return ent;
    }

    @Override
    public BasedataLotteryENT findById(Integer lotteryId) {
        Query query = getQuery("select * FROM basedata_lottery WHERE lotteryId=:lotteryId");
        query.setInteger("lotteryId", lotteryId);
        return (BasedataLotteryENT) query.uniqueResult();
    }

}
