package com.tty.user.dao.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.UserThirdInfoDao;
import com.tty.user.dao.entity.UserThirdInfoENT;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuxinhai
 */
@Repository("userThirdInfoDao")
public class UserThirdInfoDaoImpl extends BaseDao<UserThirdInfoENT> implements UserThirdInfoDao {
    @Autowired
     @Qualifier("userRedis")
     private JedisClusterFactory userRedis;

    public void saveUserThirdInfo(UserThirdInfoENT ent) {
        save(ent);
    }

    public void updateUserThirdInfo(UserThirdInfoENT ent) {
        update(ent);
    }

    public void deleteUserThirdInfo(UserThirdInfoENT ent) {
        delete(ent);
    }

    public void saveOrUpdateUserThirdInfo(UserThirdInfoENT ent) {
        saveOrUpdate(ent);
    }


    //    =========================admin==================================
    @Override
    public List<UserThirdInfoENT> listUserThirdInfo(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserThirdInfoENT where 1=1 ")
                .andEq("name", data.getString("name"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listUserThirdInfoCount(JSONObject data) {
        String hql = "select count(id) from UserThirdInfoENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("name", data.getString("name"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

    @Override
    public UserThirdInfoENT getUserThirdInfoByThirdId(String thirdId, String registerType) {
        if (StringUtils.isBlank(thirdId) || StringUtils.isBlank(registerType)) {
            return null;
        }
        UserThirdInfoENT ent = null;
        String key = String.format(UserRedisKeys.USER_USER_THIRD_INFO_KEY, registerType, thirdId);
        String value = userRedis.get(key);
        if (StringUtils.isBlank(value)) {
            String hql = "FROM UserThirdInfoENT WHERE thirdId = ? and registerType = ?";
            List<UserThirdInfoENT> list = find(hql, thirdId, registerType);
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }
            ent = list.get(0);
            if(ent!=null){
                userRedis.set(key, GfJsonUtil.toJSONString(ent));
            }
        } else {
            ent = GfJsonUtil.parseObject(value, UserThirdInfoENT.class);
        }
        return ent;
    }

}
