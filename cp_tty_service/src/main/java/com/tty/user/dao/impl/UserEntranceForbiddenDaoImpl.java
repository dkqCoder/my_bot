package com.tty.user.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.UserEntranceForbiddenDao;
import com.tty.user.dao.entity.UserEntranceForbiddenENT;
import org.apache.commons.collections.CollectionUtils;
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
@Repository("userEntranceForbiddenDao")
public class UserEntranceForbiddenDaoImpl extends BaseDao<UserEntranceForbiddenENT> implements UserEntranceForbiddenDao {

    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;

    public void saveUserEntranceForbidden(UserEntranceForbiddenENT ent) {
        save(ent);
    }

    public void updateUserEntranceForbidden(UserEntranceForbiddenENT ent) {
        update(ent);
    }

    public void saveOrUpdateUserEntranceForbidden(UserEntranceForbiddenENT ent) {
        saveOrUpdate(ent);
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public UserEntranceForbiddenENT getUserEntranceForbidden(String cmdName, String province, String city) {
        try {
            String key = String.format(UserRedisKeys.USER_ENTRANCE_FOBIDDEN_KEY, cmdName, province, city);
            if (userRedis.exists(key)) {
                return GfJsonUtil.parseObject(userRedis.get(key), UserEntranceForbiddenENT.class);
            }
            String hql = "FROM UserEntranceForbiddenENT WHERE cmdName = ? and provinceName=? and cityName=? ";
            List<UserEntranceForbiddenENT> list = find(hql, new Object[]{cmdName, province, city});
            if (CollectionUtils.isEmpty(list)) {
                hql = "FROM UserEntranceForbiddenENT WHERE cmdName = ? and provinceName=? and cityName is NULL ";
                list = find(hql, new Object[]{cmdName, province});
                if (CollectionUtils.isEmpty(list)) {
                    return null;
                }
            }
            userRedis.set(key, GfJsonUtil.toJSONString(list.get(0)));
            userRedis.expire(key, 30 * 60);
            return list.get(0);
        } catch (Exception e) {
        }
        return null;
    }


    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public UserEntranceForbiddenENT getUserEntranceForbiddenByProvince(String cmdName, String province) {
        try {
            String key = String.format(UserRedisKeys.USER_ENTRANCE_FOBIDDEN_KEY, cmdName, province, "");
            if (userRedis.exists(key)) {
                return GfJsonUtil.parseObject(userRedis.get(key), UserEntranceForbiddenENT.class);
            }
            String hql = "FROM UserEntranceForbiddenENT WHERE cmdName = ? and provinceName=? and IFNULL(s_city_name,'') ='' ";
            List<UserEntranceForbiddenENT> list = find(hql, new Object[]{cmdName, province});
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }
            userRedis.set(key, GfJsonUtil.toJSONString(list.get(0)));
            userRedis.expire(key, 30 * 60);
            return list.get(0);
        } catch (Exception e) {
        }
        return null;
    }


    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public UserEntranceForbiddenENT getEntranceForbidden(String cmdName, String province, String city) {
        try {
            String key = String.format(UserRedisKeys.USER_ENTRANCE_FOBIDDEN_KEY, cmdName, province, city);
            if (userRedis.exists(key)) {
                return GfJsonUtil.parseObject(userRedis.get(key), UserEntranceForbiddenENT.class);
            }
            String hql = "FROM UserEntranceForbiddenENT WHERE cmdName = ? and provinceName=? and cityName=? ";
            List<UserEntranceForbiddenENT> list = find(hql, new Object[]{cmdName, province, city});
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }
            userRedis.set(key, GfJsonUtil.toJSONString(list.get(0)));
            userRedis.expire(key, 30 * 60);
            return list.get(0);
        } catch (Exception e) {
        }
        return null;
    }


    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public Boolean isExitstEntranceForbiddenByProvince(String cmdName, String province) {
        try {
            String key = String.format(UserRedisKeys.USER_ENTRANCE_FOBIDDEN_KEY, cmdName, province, "");
            if (userRedis.exists(key)) {
                return true;
            }
            String hql = "FROM UserEntranceForbiddenENT WHERE cmdName = ? and provinceName = ?";
            List<UserEntranceForbiddenENT> list = find(hql, new Object[]{cmdName, province});
            if (CollectionUtils.isEmpty(list)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }


    @Override
    public List<UserEntranceForbiddenENT> listUserEntranceForbidden(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserEntranceForbiddenENT where 1=1 ")
                .andEq("cmdName", data.getString("cmdName"))
                .andEq("provinceName", data.getString("provinceName"))
                .andEq("cityName", data.getString("cityName")).addOrderBy(" order by provinceName asc ");
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listUserEntranceForbiddenCount(JSONObject data) {
        String hql = "select count(id) from UserEntranceForbiddenENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("cmdName", data.getString("cmdName"))
                .andEq("provinceName", data.getString("provinceName"))
                .andEq("cityName", data.getString("cityName"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

    @Override
    public void delUserEntranceForbidden(Long id) {
        getSQLQuery("delete from  user_entrance_forbidden  where n_id=:id").setLong("id", id).executeUpdate();
    }

    @Override
    public UserEntranceForbiddenENT getById(Long id) {
        String hql = "from UserEntranceForbiddenENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("id", id);
        return find(fullHql.getAllSql(), fullHql.getParms()).get(0);
    }

    @Override
    public List<Map<String, String>> listCmdName() {
        return getSQLQuery("SELECT DISTINCT s_cmd_name as cmdName from user_entrance_forbidden ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }
}
