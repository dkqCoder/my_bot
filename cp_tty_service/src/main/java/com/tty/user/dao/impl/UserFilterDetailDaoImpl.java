package com.tty.user.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.user.dao.UserFilterDetailDao;
import com.tty.user.dao.entity.UserFilterDetailENT;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author chenpengfei {chenpengfei@jiangduoduo.com}
 * @Date 2017/10/30 17:29
 */
@Repository("userFilterDetailDao")
public class UserFilterDetailDaoImpl extends BaseDao<UserFilterDetailENT> implements UserFilterDetailDao {


    @Override
    public void addUserFilterDetail(UserFilterDetailENT userFilterDetailENT) {
        save(userFilterDetailENT);
    }

    @Override
    public void updateUserFilter(UserFilterDetailENT userFilterDetailENT) {
        update(userFilterDetailENT);
    }

    @Override
    public List<UserFilterDetailENT> findUserFilterDetailList(UserFilterDetailENT userFilterDetailENT, List<Long> userFilterIds) {
        StringBuilder sql = new StringBuilder("FROM UserFilterDetailENT WHERE 1=1");
        if (null != userFilterDetailENT.getUserFilterId()) {
            sql.append(" AND userFilterId= " + userFilterDetailENT.getUserFilterId().longValue());
        }
        if (StringUtils.isNotBlank(userFilterDetailENT.getCode())) {
            sql.append(" AND code= '" + userFilterDetailENT.getCode() + "'");
        }
        if (StringUtils.isNotBlank(userFilterDetailENT.getStatus())) {
            sql.append(" AND status= '" + userFilterDetailENT.getStatus() + "'");
        }
        if (StringUtils.isNotBlank(userFilterDetailENT.getType())) {
            sql.append(" AND type= '" + userFilterDetailENT.getType() + "'");
        }
        if (userFilterIds != null && userFilterIds.size() > 0) {
            sql.append(" AND userFilterId in (" + ArrayUtils.toString(userFilterIds.toArray()).replace("{", "").replace("}", "") + ")");
        }
        return getQuery(sql.toString()).list();
    }

    //    =========================admin==================================

    @Override
    public List<UserFilterDetailENT> listUserFilterDetail(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserFilterDetailENT where 1 = 1")
                .andEq("code", data.getString("code"))
                .andEq("type", data.getString("type"))
                .andEq("context", data.getString("context"))
                .andEq("status", data.getString("status"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public boolean userWhiteListExists(String uuid, String ip) {
        try {
            String sql = "select IFNULL(COUNT(1),0) as num from user_filter_detail WHERE s_status=1 AND s_type=1 AND (s_code='ip' or s_code='uuid') AND (s_context=? or s_context=?)";
            Integer num = (Integer) getSQLQuery(sql)
                    .setString(0, uuid)
                    .setString(1, ip)
                    .uniqueResult();
            return num > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Long getUserFilterDetailCount(JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserFilterDetailENT where 1 = 1")
                .andEq("code", data.getString("code"))
                .andEq("type", data.getString("type"))
                .andEq("context", data.getString("context"))
                .andEq("status", data.getString("status"));
        return count(where.getCountSql(), where.getParms());
    }
}
