package com.tty.user.dao.impl;/**
 * Created by shenwei on 2017/1/4.
 */

import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.ehcache.EhcacheManager;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.UserGrowupDao;
import com.tty.user.dao.pojo.UserGrowupRecord;
import com.tty.user.dao.pojo.UserLevelAuthority;
import com.tty.user.dao.pojo.UserLevelMapping;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shenwei
 * @create 2017-01-04
 */
@Repository("UserGrowupDao")
public class UserGrowupDaoImpl extends BaseDao<UserGrowupRecord> implements UserGrowupDao {

    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;
    @Autowired
    private EhcacheManager ehcacheManager;

    /**
     * @Author shenwei
     * @Date 2017/1/4 15:35
     * @Description 获取用户等级和成长值
     */
    public List<Map<String, Object>> getUserLevelAndGrowup(Long userId) {
        String sql = "select ifNull(n_user_growups,0) as growups,ifNull(n_user_level,0) as level from user_level_growup where n_user_id=:userId";
        List<Map<String, Object>> list = getSQLQuery(sql)
                .addScalar("growups", StandardBasicTypes.LONG)
                .addScalar("level", StandardBasicTypes.INTEGER)
                .setLong("userId", userId)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .list();
        return list;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/4 13:29
     * @Description 用户成长值, 等级变动
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    public Boolean changeUserGrowup(Long userId, Long growUps, Integer level, String description, Boolean levelChanged) {
        //成长值变动明细
        UserGrowupRecord ent = new UserGrowupRecord();
        ent.setUserId(userId);
        ent.setDescription(description);
        ent.setGrowups(growUps);
        ent.setCreateDate(new Date());
        save(ent);
        String sql = "insert into user_level_growup(n_user_id, n_user_growups,n_user_level) values(:userId,:growups,:level) on duplicate key update n_user_growups=n_user_growups+:growups,n_user_level=:level";
        //如果等级变动,需记录等级变动时间,留作自动降级使用
        if (levelChanged) {
            sql = "insert into user_level_growup(n_user_id, n_user_growups,n_user_level,d_level_changetime) values(:userId,:growups,:level,now()) on duplicate key update n_user_growups=n_user_growups+:growups,n_user_level=:level,d_level_changetime=now()";
        }
        int rows = getSQLQuery(sql).setLong("userId", userId).setLong("growups", growUps).setInteger("level", level).executeUpdate();
        return rows > 0;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/4 15:34
     * @Description 获取用户成长值明细
     */
    public List<UserGrowupRecord> getUserGrowupRecord(Long userId, Integer pageIndex, Integer pageSize) {
        String sql = "select n_growups as growups,s_description as description,d_create_date as createDate from user_growup_record where datediff(now(),d_create_date)<=7 and n_user_id=:userId order by d_create_date desc";
        List<UserGrowupRecord> list = getSQLQuery(sql)
                .addScalar("growups", StandardBasicTypes.LONG)
                .addScalar("description", StandardBasicTypes.STRING)
                .addScalar("createDate", StandardBasicTypes.DATE)
                .setLong("userId", userId)
                .setFirstResult((pageIndex - 1) * pageSize)
                .setMaxResults(pageSize)
                .setResultTransformer(Transformers.aliasToBean(UserGrowupRecord.class))
                .list();
        return list;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/5 11:36
     * @Description 获取等级成长值映射
     */
    public List<UserLevelMapping> getUserLevelMapping() {
        String sql = "select n_level as level,n_growup_min as growupMin,n_growup_max as growupMax,s_remark as remark from user_level_mapping order by n_level asc";
        List<UserLevelMapping> list = getSQLQuery(sql)
                .addScalar("level", StandardBasicTypes.INTEGER)
                .addScalar("growupMin", StandardBasicTypes.LONG)
                .addScalar("growupMax", StandardBasicTypes.LONG)
                .addScalar("remark", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(UserLevelMapping.class))
                .list();
        return list;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/6 10:31
     * @Description 获取等级对应特权
     */
    public List<UserLevelAuthority> getLevelAuthority(Integer level) {
        String keys = String.format(UserRedisKeys.USER_LEVEL_AUTHORITY_KEY, level);
        List<UserLevelAuthority> list;
        list = (List<UserLevelAuthority>) ehcacheManager.get(keys);
        if (CollectionUtils.isEmpty(list)) {
            list = GfJsonUtil.parseArray(userRedis.get(keys), UserLevelAuthority.class);
            if (!CollectionUtils.isEmpty(list)) {
                ehcacheManager.set(keys, list);
            } else {
                String sql = "SELECT n_id as id,s_authority_name as authorityName,s_authority_description as authorityDescription,n_authority_value as authorityValue,n_category as category from user_level_authority where n_level =:level";
                list = getSQLQuery(sql)
                        .addScalar("id", StandardBasicTypes.INTEGER)
                        .addScalar("authorityName", StandardBasicTypes.STRING)
                        .addScalar("authorityDescription", StandardBasicTypes.STRING)
                        .addScalar("authorityValue", StandardBasicTypes.BIG_DECIMAL)
                        .addScalar("category", StandardBasicTypes.INTEGER)
                        .setInteger("level", level)
                        .setResultTransformer(Transformers.aliasToBean(UserLevelAuthority.class))
                        .list();
                userRedis.set(keys, GfJsonUtil.toJSONString(list));
                ehcacheManager.set(keys, list);
            }
        }
        return list;
    }


    /**
     * @Author shenwei
     * @Date 2017/1/12 13:56
     * @Description 获取用户等级击败用户比例, 以等级占比进行计算
     */
    public String getUserLevelBeat(Integer level) {
        String levelBeat = "";
        String sqlTotal = "select count(1) as count from user_level_growup";
        Long totalCount = Long.parseLong(getSQLQuery(sqlTotal).uniqueResult().toString());
        String sql = "select count(1) as num from user_level_growup where n_user_level <:level";
        Long beatCount = Long.parseLong(getSQLQuery(sql).setInteger("level", level).uniqueResult().toString());
        double beatLevel = (double) beatCount / (double) totalCount;
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(1); //百分比保留1位小数
        levelBeat = format.format(beatLevel);
        //临界值控制，仅为体验，0级用户击败0%,无小数点默认加0 产品要求
        if (level == 0) {
            levelBeat = "0.0%";
            return levelBeat;
        }
        if (beatCount == 0) {
            levelBeat = "5.0%";
            return levelBeat;
        }
        if (!levelBeat.contains(".")) {
            levelBeat = levelBeat.substring(0, levelBeat.length() - 1).concat(".0%");
        }
        return levelBeat;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/16 14:10
     * @Description 同步用户名
     */
    public Boolean syncUserBaseInfo(Long userId, String userName) {
        String sql = "INSERT INTO user_base_info(n_user_id, s_nick_name) VALUES(:userId,:userName) on duplicate key update s_name=:userName";
        int result = getSQLQuery(sql)
                .setLong("userId", userId)
                .setString("userName", userName)
                .executeUpdate();
        return result > 0;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/18 20:07
     * @Description 用户是否已领取某升级礼包
     */
    public Boolean IsAlreadyGetGift(Long userId, Integer giftId) {
        String sql = "select count(1) as num from user_gift_record where n_gift_id=:giftId and n_user_id=:userId";
        Integer num = Integer.parseInt(getSQLQuery(sql).setInteger("giftId", giftId).setLong("userId", userId).uniqueResult().toString());
        return num > 0;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/18 20:22
     * @Description 获取礼包所匹配等级
     */
    public List<Map<String, Object>> getGiftMappingLevel(Integer giftId) {
        String sql = "select n_level as level,n_category as category from user_level_authority where n_id=:giftId";
        List<Map<String, Object>> list = getSQLQuery(sql).setInteger("giftId", giftId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/19 9:57
     * @Description 存入用户领取礼包记录
     */
    public void saveUserGiftRecord(Long userId, Integer giftId) {
        String sql = "insert into user_gift_record (n_user_id,n_gift_id,d_create_date) values(:userId,:giftId,now())";
        getSQLQuery(sql).setLong("userId", userId).setInteger("giftId", giftId).executeUpdate();
    }


    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public void removeGrowupRecords() {
        BigInteger minId = getMinRecordId();
        if (minId.equals(0)) {
            return;
        }
        BigInteger maxId = minId.add(BigInteger.valueOf(5000));
        delRecords(minId, maxId);
    }

    private BigInteger getMinRecordId() {
        Date date = DateUtil.preDate(60);
        String sql = " select IFNULL(min(n_id),0) as n_id from user_growup_record where d_create_date<=?";
        BigInteger minId = (BigInteger) getSQLQuery(sql)
                .setDate(0, date)
                .uniqueResult();
        return minId;
    }

    private void delRecords(BigInteger minId, BigInteger maxId) {
        String delSql = "delete from user_growup_record where n_id>=? and n_id<=?";
        getSQLQuery(delSql)
                .setBigInteger(0, minId)
                .setBigInteger(1, maxId)
                .executeUpdate();
    }
}
