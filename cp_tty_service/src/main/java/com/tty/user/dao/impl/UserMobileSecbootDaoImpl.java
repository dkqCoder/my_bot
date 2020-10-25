package com.tty.user.dao.impl;

import com.tty.user.dao.UserMobileSecbootDao;
import com.tty.user.dao.entity.UserMobileSecbootENT;
import org.springframework.stereotype.Repository;
import com.jdd.fm.core.db.BaseDao;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.utils.WhereUtils;
import java.util.List;

/**
 *
 * @author zhuxinhai
 *
 */
@Repository("userMobileSecbootDao")
public class UserMobileSecbootDaoImpl extends BaseDao<UserMobileSecbootENT> implements UserMobileSecbootDao {

    public void saveUserMobileSecboot(UserMobileSecbootENT ent) {
        save(ent);
    }

    public void updateUserMobileSecboot(UserMobileSecbootENT ent) {
        update(ent);
    }

    public void saveOrUpdateUserMobileSecboot(UserMobileSecbootENT ent) {
        saveOrUpdate(ent);
    }











//    =========================admin==================================
     @Override
     public List<UserMobileSecbootENT> listUserMobileSecboot(Integer page, Integer limit, JSONObject data) {
         WhereUtils where = WhereUtils.ins("from UserMobileSecbootENT where 1=1 ")
          .andEq("name",data.getString("name"));
         return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
     }

     @Override
     public Long listUserMobileSecbootCount(JSONObject data) {
        String hql = "select count(id) from UserMobileSecbootENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
         .andEq("name",data.getString("name"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
     }

}
