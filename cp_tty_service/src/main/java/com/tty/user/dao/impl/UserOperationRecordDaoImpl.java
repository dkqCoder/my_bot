package com.tty.user.dao.impl;

import org.springframework.stereotype.Repository;
import com.jdd.fm.core.db.BaseDao;
import com.tty.user.dao.entity.UserOperationRecordENT;
import com.tty.user.dao.UserOperationRecordDao;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.utils.WhereUtils;

import java.util.List;

/**
 * @author zhuxinhai
 */
@Repository("userOperationRecordDao")
public class UserOperationRecordDaoImpl extends BaseDao<UserOperationRecordENT> implements UserOperationRecordDao {

    public void saveUserOperationRecord(UserOperationRecordENT ent) {
        save(ent);
    }

    public void updateUserOperationRecord(UserOperationRecordENT ent) {
        update(ent);
    }

    public void saveOrUpdateUserOperationRecord(UserOperationRecordENT ent) {
        saveOrUpdate(ent);
    }


    //    =========================admin==================================
    @Override
    public List<UserOperationRecordENT> listUserOperationRecord(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserOperationRecordENT where 1=1 ")
                .andEq("userId", data.getLong("userId"))
                .andEq("group", data.getInteger("group"))
                .andEq("type", data.getInteger("type"))
                .andGe("createTime",data.getDate("createTime"))
                .andLe("createTime",data.getDate("createTime1"))
                .addOrderBy(" order by createTime desc ");
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listUserOperationRecordCount(JSONObject data) {
        String hql = "select count(id) from UserOperationRecordENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("userId", data.getLong("userId"))
                .andEq("group", data.getInteger("group"))
                .andEq("type", data.getInteger("type"))
                .andGe("createTime",data.getDate("createTime"))
                .andLe("createTime",data.getDate("createTime1"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

}
