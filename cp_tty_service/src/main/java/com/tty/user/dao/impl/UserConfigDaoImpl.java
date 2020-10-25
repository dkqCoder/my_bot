package com.tty.user.dao.impl;

import com.jdd.fm.core.db.ds.DataSource;
import com.tty.user.dao.UserConfigDao;
import com.tty.user.dao.entity.UserConfigENT;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import com.jdd.fm.core.db.BaseDao;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.utils.WhereUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhuxinhai
 */
@Repository("userConfigDao")
public class UserConfigDaoImpl extends BaseDao<UserConfigENT> implements UserConfigDao {


    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public UserConfigENT getUserConfig(String key) {
        String hql = "FROM UserConfigENT WHERE key = ? ";
        List<UserConfigENT> list = find(hql, new Object[]{key});
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public void saveUserConfig(UserConfigENT ent) {
        save(ent);
    }

    public void updateUserConfig(UserConfigENT ent) {
        update(ent);
    }

    public void saveOrUpdateUserConfig(UserConfigENT ent) {
        saveOrUpdate(ent);
    }


    //    =========================admin==================================
    @Override
    public List<UserConfigENT> listUserConfig(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserConfigENT where 1=1 ")
                .andEq("name", data.getString("name"))
                .andEq("type",data.getString("type"))
                .andEq("key",data.getString("key"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listUserConfigCount(JSONObject data) {
        String hql = "select count(id) from UserConfigENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("name", data.getString("name"))
                .andEq("type",data.getString("type"))
                .andEq("key",data.getString("key"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

}
