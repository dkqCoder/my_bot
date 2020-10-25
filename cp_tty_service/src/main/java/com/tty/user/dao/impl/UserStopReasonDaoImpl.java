package com.tty.user.dao.impl;

import com.jdd.fm.core.db.ds.DataSource;
import org.springframework.stereotype.Repository;
import com.jdd.fm.core.db.BaseDao;
import com.tty.user.dao.entity.UserStopReasonENT;
import com.tty.user.dao.UserStopReasonDao;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.utils.WhereUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhuxinhai
 */
@Repository("userStopReasonDao")
public class UserStopReasonDaoImpl extends BaseDao<UserStopReasonENT> implements UserStopReasonDao {

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    public void saveUserStopReason(UserStopReasonENT ent) {
        save(ent);
    }

    @Override
    public List<UserStopReasonENT> listUserStopReason(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserStopReasonENT where 1=1 ")
                .andEq("userId", data.getLong("userId"))
                .andEq("type", data.getInteger("type"))
                .andGe("operateTime", data.getDate("operateTime"))
                .andLe("operateTime", data.getDate("operateTime1"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listUserStopReasonCount(JSONObject data) {
        String hql = "select count(id) from UserStopReasonENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("userId", data.getLong("userId"))
                .andEq("type", data.getInteger("type"))
                .andGe("operateTime", data.getDate("operateTime"))
                .andLe("operateTime", data.getDate("operateTime1"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

}
