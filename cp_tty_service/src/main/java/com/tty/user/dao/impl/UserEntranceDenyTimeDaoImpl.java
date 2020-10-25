package com.tty.user.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.user.dao.UserEntranceDenyTimeDao;
import com.tty.user.dao.entity.UserEntranceDenyTimeENT;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhuxinhai
 */
@Repository("userEntranceDenyTimeDao")
public class UserEntranceDenyTimeDaoImpl extends BaseDao<UserEntranceDenyTimeENT> implements UserEntranceDenyTimeDao {

    public void saveUserEntranceDenyTime(UserEntranceDenyTimeENT ent) {
        save(ent);
    }

    public void updateUserEntranceDenyTime(UserEntranceDenyTimeENT ent) {
        update(ent);
    }

    public void saveOrUpdateUserEntranceDenyTime(UserEntranceDenyTimeENT ent) {
        saveOrUpdate(ent);
    }

    /**
     * @Author shenwei
     * @Date 2017/8/24 14:31
     * @Description
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public Boolean isInEntranceDenyTimeRegion(String cmdName) {
        Date now = new Date();
        now.setYear(100);
        now.setMonth(0);
        now.setDate(1);
        String hql = "FROM UserEntranceDenyTimeENT WHERE cmdName = ? ";
        List<UserEntranceDenyTimeENT> list = find(hql, new Object[]{cmdName});
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        for (UserEntranceDenyTimeENT item : list) {
            if (item.getStartTime().before(now) && item.getEndTime().after(now)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Author shenwei
     * @Date 2017/8/24 15:14
     * @Description 渠道存在，同时不再时间段
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public Boolean notInEntranceDenyTimeRegionWithcmdName(String cmdName) {
        Date now = new Date();
        now.setYear(100);
        now.setMonth(0);
        now.setDate(1);
        String hql = "FROM UserEntranceDenyTimeENT WHERE cmdName = ? ";
        List<UserEntranceDenyTimeENT> list = find(hql, new Object[]{cmdName});
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        boolean flag = true;
        for (UserEntranceDenyTimeENT item : list) {
            if (item.getStartTime().before(now) && item.getEndTime().after(now)) {
                flag = false;
                return flag;
            }
        }
        return flag;
    }


    //    =========================admin==================================
    @Override
    public List<UserEntranceDenyTimeENT> listUserEntranceDenyTime(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserEntranceDenyTimeENT where 1=1 ")
                .andEq("cmdName", data.getString("cmdName"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listUserEntranceDenyTimeCount(JSONObject data) {
        String hql = "select count(id) from UserEntranceDenyTimeENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("cmdName", data.getString("cmdName"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

    @Override
    public void deleteEntranceDenyTime(Long id) {
        getSQLQuery("delete from  user_entrance_deny_time  where n_id=:id").setLong("id", id).executeUpdate();
    }


}
