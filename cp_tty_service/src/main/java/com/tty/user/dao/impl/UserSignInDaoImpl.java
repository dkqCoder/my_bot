package com.tty.user.dao.impl;

import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.tty.user.dao.UserSignInDao;
import com.tty.user.dao.pojo.UserSigninRecord;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shenwei on 2016/12/14.
 */
@Repository("UserSignInDao")
public class UserSignInDaoImpl extends BaseDao<UserSigninRecord> implements UserSignInDao {


    /**
     * @Author shenwei
     * @Date 2016/12/16 11:42
     * @Description 签到
     */
    public void saveUserSign(UserSigninRecord ent) {
        save(ent);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/16 11:43
     * @Description 获取某天已连续签到次数
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public Long getContiniousSigninDays(Long userId, Date signInDate) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT n_sign_days FROM user_signin_record WHERE n_user_id=:userId ");
        sql.append(" AND DATE_FORMAT(d_user_date,'%Y-%m-%d')=date_format(:signindate,'%Y-%m-%d') LIMIT 1");
        Query query = getSQLQuery(sql.toString()).setLong("userId", userId).setDate("signindate", signInDate);
        BigInteger result = (BigInteger) query.uniqueResult();
        return result == null ? 0 : result.longValue();
    }

    /**
     * @Author shenwei
     * @Date 2016/12/16 11:43
     * @Description 获取当月已签到次数
     */
    @Override
    public Integer getCurrentMonthSinginDays(Long userId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(1) from user_signin_record where month(d_user_date)=month(now())");
        sql.append(" and n_user_id=").append(userId);
        Query query = getSQLQuery(sql.toString());
        BigInteger result = (BigInteger) query.uniqueResult();
        return result.intValue();
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 14:42
     * @Description 用户今日是否已签到
     */
    public Boolean IsAlreadySigned(Long userId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT count(1) FROM user_signin_record ");
        sql.append(" WHERE DATE_FORMAT(d_user_date,'%Y-%m-%d')=DATE_FORMAT(SYSDATE(),'%Y-%m-%d') AND n_user_id=:userId");
        BigInteger result = (BigInteger) getSQLQuery(sql.toString()).setParameter("userId", userId).uniqueResult();
        return result.intValue() > 0;
    }

    @Override
    public List<Long> getUserIdListForSignInPush(Integer page, Integer pageSize) {
        String sql = "select DISTINCT a.id from (SELECT a.n_user_id ID, MAX(a.d_user_date) maxSignTime  FROM user_signin_record a WHERE a.d_user_date > DATE_SUB(curdate(), INTERVAL 7 DAY ) GROUP BY a.n_user_id HAVING DATE_FORMAT(MAX(a.d_user_date),'%Y-%m-%d') < curdate()) a order by a.id ";
        List<BigInteger> list = getSQLQuery(sql).setFirstResult(page * pageSize - pageSize).setMaxResults(pageSize).list();
        List<Long> rows = new ArrayList<>();
        if(list!=null && list.size()>0) {
            for (BigInteger item : list) {
                rows.add(item.longValue());
            }
        }
        return rows;
    }

}
