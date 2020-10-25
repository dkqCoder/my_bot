package com.tty.user.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.user.dao.UserFilterDao;
import com.tty.user.dao.entity.UserFilterENT;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author chenpengfei {chenpengfei@jiangduoduo.com}
 * @Date 2017/10/30 16:30
 */
@Repository("userFilterDao")
public class UserFilterDaoImpl extends BaseDao<UserFilterENT> implements UserFilterDao {


    @Override
    public void saveUserFilter(UserFilterENT userFilterENT) {
        save(userFilterENT);
    }

    @Override
    public void updateUserFilter(UserFilterENT userFilterENT) {
        update(userFilterENT);
    }

    @Override
    public List<UserFilterENT> findUserFilterList(UserFilterENT userFilterENT) {
        StringBuilder sql = new StringBuilder("FROM UserFilterENT WHERE 1=1");
        if (StringUtils.isNotBlank(userFilterENT.getCode())) {
            sql.append(" AND code= '" + userFilterENT.getCode() + "'");
        }
        if (StringUtils.isNotBlank(userFilterENT.getType())) {
            sql.append(" AND type= '" + userFilterENT.getType() + "'");
        }
        if (null != userFilterENT.getEndTime()) {
            String endTimeStr = DateUtil.format(userFilterENT.getEndTime());
            sql.append(" AND endTime > '" + endTimeStr + "'");
        }
        if (StringUtils.isNotBlank(userFilterENT.getStatus())) {
            sql.append(" AND status = '" + userFilterENT.getStatus() + "'");
        }
        if (userFilterENT.getId() != null &&
                Long.valueOf(0).compareTo(userFilterENT.getId()) < 0) {
            sql.append(" AND id = '" + userFilterENT.getId() + "'");
        }
        return getQuery(sql.toString()).list();
    }

    //    =========================admin==================================
    @Override
    public List<UserFilterENT> listUserFilter(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserFilterENT where 1 = 1")
                .andEq("code", data.getString("code"))
                .andEq("type", data.getString("type"))
                .andEq("status", data.getString("status"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }
}
