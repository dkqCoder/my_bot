package com.tty.user.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.StringUtils;
import com.tty.user.dao.UserIdcardResetDao;
import com.tty.user.dao.entity.UserIdcardResetENT;
import com.tty.user.model.params.UserIdcardResetParam;
import com.tty.user.dto.UserIdcardResetDTO;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuxinhai
 */
@Repository("userIdcardResetDao")
public class UserIdcardResetDaoImpl extends BaseDao<UserIdcardResetENT> implements UserIdcardResetDao {

    public void saveUserIdcardReset(UserIdcardResetENT ent) {
        save(ent);
    }

    public void updateUserIdcardReset(UserIdcardResetENT ent) {
        update(ent);
    }

    public void deleteUserIdcardReset(UserIdcardResetENT ent) {
        delete(ent);
    }

    public void saveOrUpdateUserIdcardReset(UserIdcardResetENT ent) {
        saveOrUpdate(ent);
    }

    @Override
    public List<UserIdcardResetDTO> listUserIdcardReset(JSONObject searchCondition) {
        int start = searchCondition.getInteger("page");
        int limit = searchCondition.getInteger("limit");
        StringBuilder selectSql = new StringBuilder(" SELECT t0.n_id AS id,  t0.n_user_id AS userId,  t2.s_login_name AS userName, ");
        selectSql.append(" t0.s_idcard_number AS idcardNumber, t0.s_real_name  AS realName, t1.s_idcard_number AS oldIdcardNumber, t1.s_real_name AS oldRealName, ")
                .append(" t0.s_front_url AS frontUrl, t0.s_back_url AS backUrl, t0.n_status AS status, t0.s_remark AS remark, t0.d_create_time AS createTime, t0.d_update_time AS updateTime ")
                .append(" FROM user_idcard_reset t0 LEFT JOIN user_base_info t1 ON t1.n_user_id = t0.n_user_id LEFT JOIN user_info t2 ON t0.n_user_id = t2.n_user_id WHERE 1=1 ");
        SQLQuery sqlQuery = getUserIdcardResetSQLQuery(selectSql.toString(), searchCondition);
        sqlQuery.addScalar("id", StandardBasicTypes.INTEGER)
                .addScalar("userId", StandardBasicTypes.LONG)
                .addScalar("userName", StandardBasicTypes.STRING)
                .addScalar("idcardNumber", StandardBasicTypes.STRING)
                .addScalar("realName", StandardBasicTypes.STRING)
                .addScalar("oldIdcardNumber", StandardBasicTypes.STRING)
                .addScalar("oldRealName", StandardBasicTypes.STRING)
                .addScalar("frontUrl", StandardBasicTypes.STRING)
                .addScalar("backUrl", StandardBasicTypes.STRING)
                .addScalar("status", StandardBasicTypes.INTEGER)
                .addScalar("remark", StandardBasicTypes.STRING)
                .addScalar("createTime", StandardBasicTypes.STRING)
                .addScalar("updateTime", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(UserIdcardResetDTO.class))
                .setFirstResult((start - 1) * limit)
                .setMaxResults(limit);

        return sqlQuery.list();
    }

    @Override
    public Long getUserIdcardResetCount(JSONObject searchCondition) {
        String selectSql = " SELECT  COUNT(1) FROM user_idcard_reset t0  LEFT JOIN user_base_info t1 ON t1.n_user_id = t0.n_user_id  LEFT JOIN user_info t2 ON t0.n_user_id = t2.n_user_id WHERE 1=1";
        SQLQuery sqlQuery = getUserIdcardResetSQLQuery(selectSql, searchCondition);
        return Long.valueOf(sqlQuery.uniqueResult().toString());
    }

    @Override
    public Boolean auditUserIdcardReset(Long id, int status, String remark) {
        String hql = "UPDATE UserIdcardResetENT SET status = ?,remark = ? WHERE id = ? ";
        Object[] params = new Object[]{status, remark, id};
        return executeHql(hql, params) > 0;
    }

    /**
     * 获取身份证重置信息
     *
     * @param userId 用户ID
     * @param status 审核状态 null 查该用户所有
     * @return
     */
    @Override
    public UserIdcardResetENT getUserIdcardReset(String userId, Integer status) {
        String hql = "FROM UserIdcardResetENT WHERE userId = ? ";
        Object[] params = new Object[]{userId};
        if (status != null) {
            hql += "AND status = ? ";
            params = new Object[]{Long.valueOf(userId), status};
        }

        return get(hql, params);
    }

    private SQLQuery getUserIdcardResetSQLQuery(String selectSql, JSONObject searchCondition) {
        StringBuilder sbSql = new StringBuilder(selectSql);

        UserIdcardResetParam userIdcardResetParam = GfJsonUtil.parseObject(searchCondition.getString("data"), UserIdcardResetParam.class);

        if (StringUtils.isNotBlank(userIdcardResetParam.getUserName())) {
            sbSql.append(" AND t2.s_login_name = :s_login_name ");
        }
        if (null != userIdcardResetParam.getStatus()) {
            sbSql.append(" AND t0.n_status = :n_status ");
        }
        if (null != userIdcardResetParam.getStartCreateTime()) {
            sbSql.append(" AND t0.d_create_time>= :s_d_create_time ");
        }
        if (null != userIdcardResetParam.getEndCreateTime()) {
            sbSql.append(" AND t0.d_create_time<= :e_d_create_time ");
        }
        if (StringUtils.isNotBlank(userIdcardResetParam.getIdcardNumber())) {
            sbSql.append(" AND t0.s_idcard_number = :s_idcard_number ");
        }
        if (StringUtils.isNotBlank(userIdcardResetParam.getRealName())) {
            sbSql.append(" AND t0.s_real_name = :s_real_name ");
        }
        sbSql.append(" order by t0.d_create_time desc ");

        SQLQuery sqlQuery = getSQLQuery(sbSql.toString());

        if (StringUtils.isNotBlank(userIdcardResetParam.getUserName())) {
            sqlQuery.setString("s_login_name", userIdcardResetParam.getUserName());
        }
        if (null != userIdcardResetParam.getStatus()) {
            sqlQuery.setInteger("n_status", userIdcardResetParam.getStatus());
        }
        if (null != userIdcardResetParam.getStartCreateTime()) {
            sqlQuery.setDate("s_d_create_time", userIdcardResetParam.getStartCreateTime());
        }
        if (null != userIdcardResetParam.getEndCreateTime()) {
            sqlQuery.setDate("e_d_create_time", userIdcardResetParam.getEndCreateTime());
        }
        if (StringUtils.isNotBlank(userIdcardResetParam.getIdcardNumber())) {
            sqlQuery.setString("s_idcard_number", userIdcardResetParam.getIdcardNumber());
        }
        if (StringUtils.isNotBlank(userIdcardResetParam.getRealName())) {
            sqlQuery.setString("s_real_name", userIdcardResetParam.getRealName());
        }

        return sqlQuery;
    }

}
