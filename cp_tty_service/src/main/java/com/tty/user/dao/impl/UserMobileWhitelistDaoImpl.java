package com.tty.user.dao.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.user.dao.UserMobileWhitelistDao;
import com.tty.user.dao.entity.UserMobileWhitelistENT;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhuxinhai
 */
@Repository("userMobileWhitelistDao")
public class UserMobileWhitelistDaoImpl extends BaseDao<UserMobileWhitelistENT> implements UserMobileWhitelistDao {

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void saveUserMobileWhitelist(UserMobileWhitelistENT ent) {
        save(ent);
    }

    public void updateUserMobileWhitelist(UserMobileWhitelistENT ent) {
        update(ent);
    }

    public void saveOrUpdateUserMobileWhitelist(UserMobileWhitelistENT ent) {
        saveOrUpdate(ent);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public UserMobileWhitelistENT getMobileWhiteList(String mobile) {
        String hql = "FROM UserMobileWhitelistENT WHERE mobile = ?";
        List<UserMobileWhitelistENT> list = find(hql, mobile);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }


    @Override
    public List<UserMobileWhitelistENT> listMobileWhitelist(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserMobileWhitelistENT where 1=1 ")
                .andEq("mobile", data.getString("mobile"))
                .addOrderBy(" order by mobile asc ");
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listMobileWhitelistCount(JSONObject data) {
        String hql = "select count(id) from UserMobileWhitelistENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("mobile", data.getString("mobile"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

    @Override
    public void deleteMobileWhitelist(String mobile) {
        getSQLQuery("delete from  user_mobile_whitelist  where s_mobile=:mobile").setString("mobile", mobile).executeUpdate();
    }

    @Override
    public void deleteMobileWhitelist(Long id) {
        getSQLQuery("delete from  user_mobile_whitelist  where n_id=:id").setLong("id", id).executeUpdate();
    }

}
