package com.tty.user.dao.impl;

import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.utils.DateUtil;
import com.tty.user.dao.UserIntegralRecordsDao;
import com.tty.user.dao.pojo.UserIntegralRecords;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author chenlongfei
 */
@Repository("userIntegralRecordsDao")
public class UserIntegralRecordsDaoImpl extends BaseDao<UserIntegralRecords> implements UserIntegralRecordsDao {

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    public void saveUserIntegralRecords(UserIntegralRecords ent) {
        save(ent);
    }

    public void updateUserIntegralRecords(UserIntegralRecords ent) {
        update(ent);
    }

    public void deleteUserIntegralRecords(UserIntegralRecords ent) {
        delete(ent);
    }

    public void saveOrUpdateUserIntegralRecords(UserIntegralRecords ent) {
        saveOrUpdate(ent);
    }

    public List<UserIntegralRecords> listUserIntegralRecords(long userId, Integer pageIndex, Integer pageSize, long lastId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select n_id as id,n_userid as userid,n_type as type,d_create_date as createDate,n_count as count")
                .append(",n_sourceid as sourceid,s_description as description from user_integral_records  where dateDiff(now(),d_create_date)<=30  and n_userid=? order by n_id desc");
        return getSQLQuery(sql.toString())
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("userid", StandardBasicTypes.LONG)
                .addScalar("type", StandardBasicTypes.INTEGER)
                .addScalar("createDate", StandardBasicTypes.DATE)
                .addScalar("count", StandardBasicTypes.INTEGER)
                .addScalar("sourceid", StandardBasicTypes.LONG)
                .addScalar("description", StandardBasicTypes.STRING)
                .setParameter(0, userId)
                .setFirstResult((pageIndex - 1) * pageSize)
                .setMaxResults(pageSize)
                .setResultTransformer(Transformers.aliasToBean(UserIntegralRecords.class))
                .list();
    }

    /**
     * @Author shenwei
     * @Date 2017/8/31 9:57
     * @Description uc查询使用
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public List<UserIntegralRecords> listUserIntegralRecordsByTime(long userId, Integer pageIndex, Integer pageSize, Date startTime, Date endTime) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select n_id as id,n_userid as userid,n_type as type,d_create_date as createDate,n_count as count")
                .append(",n_sourceid as sourceid,s_description as description from user_integral_records  where d_create_date<=? and d_create_date>=? and n_userid=? order by n_id desc");
        return getSQLQuery(sql.toString())
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("userid", StandardBasicTypes.LONG)
                .addScalar("type", StandardBasicTypes.INTEGER)
                .addScalar("createDate", StandardBasicTypes.DATE)
                .addScalar("count", StandardBasicTypes.INTEGER)
                .addScalar("sourceid", StandardBasicTypes.LONG)
                .addScalar("description", StandardBasicTypes.STRING)
                .setParameter(0, endTime)
                .setParameter(1, startTime)
                .setParameter(2, userId)
                .setFirstResult((pageIndex - 1) * pageSize)
                .setMaxResults(pageSize)
                .setResultTransformer(Transformers.aliasToBean(UserIntegralRecords.class))
                .list();
    }

    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public void removeIntegralRecords() {
        BigInteger minId = getMinRecordId();
        if (minId.equals(0)) {
            return;
        }
        BigInteger maxId = minId.add(BigInteger.valueOf(5000));
        delRecords(minId, maxId);
    }

    private BigInteger getMinRecordId() {
        Date date = DateUtil.preDate(60);
        String sql = " select IFNULL(min(n_id),0) as n_id from user_integral_records where d_create_date<=?";
        BigInteger minId = (BigInteger) getSQLQuery(sql)
                .setDate(0, date)
                .uniqueResult();
        return minId;
    }
    
    private void delRecords(BigInteger minId, BigInteger maxId) {
        String delSql = "delete from user_integral_records where n_id>=? and n_id<=?";
        getSQLQuery(delSql)
                .setBigInteger(0, minId)
                .setBigInteger(1, maxId)
                .executeUpdate();
    }
}
