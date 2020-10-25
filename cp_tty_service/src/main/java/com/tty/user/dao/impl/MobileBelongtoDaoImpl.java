package com.tty.user.dao.impl;

import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.MobileBelongtoDao;
import com.tty.user.dao.entity.MobileBelongtoENT;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * @author zhuxinhai
 */
@Repository("mobileBelongtoDao")
public class MobileBelongtoDaoImpl extends BaseDao<MobileBelongtoENT> implements MobileBelongtoDao {

    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;

    public void saveMobileBelongto(MobileBelongtoENT ent) {
        save(ent);
    }

    public void updateMobileBelongto(MobileBelongtoENT ent) {
        update(ent);
    }

    public void saveOrUpdateMobileBelongto(MobileBelongtoENT ent) {
        saveOrUpdate(ent);
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public MobileBelongtoENT findMobileBelong(String mobile_seg) {
        String key = String.format(UserRedisKeys.USER_MOBILE_BELONG_KEY, mobile_seg);
        String cacheStr = userRedis.get(key);
        if (StringUtils.isNotBlank(cacheStr)) {
            return GfJsonUtil.parseObject(cacheStr, MobileBelongtoENT.class);
        }
        String hql = "FROM MobileBelongtoENT WHERE mobileSegment = ?";
        List<MobileBelongtoENT> list = find(hql, new Object[]{mobile_seg});
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        userRedis.set(key, GfJsonUtil.toJSONString(list.get(0)));
        userRedis.expire(key, 7 * 24 * 60 * 60);
        return list.get(0);
    }


    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public List<Map<String, String>> findMobileBelongByProvince(String province) {
        String sql = "select s_province_name as provinceName,s_city_name as cityName,count(1) as count from mobile_belongto where s_province_name=:province group by s_province_name,s_city_name";
        List<Map<String, String>> list = getSQLQuery(sql).setString("province", province).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }


}
