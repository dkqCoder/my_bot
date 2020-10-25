package com.tty.user.dao.impl;

import com.jdd.fm.core.db.ds.DataSource;
import org.springframework.stereotype.Repository;
import com.jdd.fm.core.db.BaseDao;
import com.tty.user.dao.entity.UserLoginRecordENT;
import com.tty.user.dao.UserLoginRecordDao;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.utils.WhereUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author zhuxinhai
 *
 */
@Repository("userLoginRecordDao")
public class UserLoginRecordDaoImpl extends BaseDao<UserLoginRecordENT> implements UserLoginRecordDao {

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    public void saveUserLoginRecord(UserLoginRecordENT ent) {
        save(ent);
    }

    public void updateUserLoginRecord(UserLoginRecordENT ent) {
        update(ent);
    }

    public void deleteUserLoginRecord(UserLoginRecordENT ent) {
        delete(ent);
    }

    public void saveOrUpdateUserLoginRecord(UserLoginRecordENT ent) {
        saveOrUpdate(ent);
    }











//    =========================admin==================================
     @Override
     public List<UserLoginRecordENT> listUserLoginRecord(Integer page, Integer limit, JSONObject data) {
         WhereUtils where = WhereUtils.ins("from UserLoginRecordENT where 1=1 ")
          .andEq("name",data.getString("name"));
         return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
     }

     @Override
     public Long listUserLoginRecordCount(JSONObject data) {
        String hql = "select count(id) from UserLoginRecordENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
         .andEq("name",data.getString("name"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
     }

}
