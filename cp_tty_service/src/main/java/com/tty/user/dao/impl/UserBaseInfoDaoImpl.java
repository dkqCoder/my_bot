package com.tty.user.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.tty.user.dao.UserBaseInfoDao;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.dto.UserBaseInfoDTO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donne on 17/03/07.
 */
@Repository("userBaseInfoDao")
public class UserBaseInfoDaoImpl extends BaseDao<UserBaseInfoENT> implements UserBaseInfoDao {

    public void saveUserBaseInfo(UserBaseInfoENT ent) {
        save(ent);
    }

    public List<UserBaseInfoDTO> listUserBaseInfo(JSONObject searchCondition, Boolean all) {
        StringBuilder selectSql = new StringBuilder();
        List<Object> params = new ArrayList();
        JSONObject searchConditionJSONObject = searchCondition.getJSONObject("data");
        if (!all) {
            if (searchConditionJSONObject.getLong("userId") == null && StringUtils.isBlank(searchConditionJSONObject.getString("loginName")) && StringUtils.isBlank(searchConditionJSONObject.getString("mobileNumber")) && StringUtils.isBlank(searchConditionJSONObject.getString("nickName")) && StringUtils.isBlank(searchConditionJSONObject.getString("realName"))) {
                return new ArrayList<>();
            }
        }
        assembleUserBaseInfoSql(selectSql, searchCondition, params, true);
        SQLQuery sqlQuery = getSQLQuery(selectSql.toString());
        for (int i = 0; i < params.size(); i++) {
            sqlQuery.setParameter(i, params.get(i));
        }
        sqlQuery.addScalar("userId", StandardBasicTypes.LONG)
                .addScalar("loginName", StandardBasicTypes.STRING)
                .addScalar("mobileNumber", StandardBasicTypes.STRING)
                .addScalar("thdPartName", StandardBasicTypes.STRING)
                .addScalar("realName", StandardBasicTypes.STRING)
                .addScalar("nickName", StandardBasicTypes.STRING)
                .addScalar("remark", StandardBasicTypes.STRING)
                .addScalar("idcardNumber", StandardBasicTypes.STRING)
                .addScalar("entranceCode", StandardBasicTypes.STRING)
                .addScalar("registerTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("lastLoginTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("status", StandardBasicTypes.INTEGER);

        List<UserBaseInfoDTO> rows = sqlQuery.setResultTransformer(Transformers.aliasToBean(UserBaseInfoDTO.class)).list();
        return rows;
    }

    private void assembleUserBaseInfoSql(StringBuilder selectSql, JSONObject searchCondition, List<Object> parms, boolean isPage) {
        JSONObject searchConditionJSONObject = searchCondition.getJSONObject("data");
        int start = searchCondition.getInteger("page");
        int limit = searchCondition.getInteger("limit");
        selectSql.append(" SELECT ui.n_user_id userId, ui.s_login_name loginName, ui.s_mobile_number mobileNumber, ui.s_thd_part_name thdPartName, ui.s_thd_part_type thdPartType, ui.s_entrance_code entranceCode, ui.d_register_time registerTime, ui.n_status 'status', ui.d_last_login_time as lastLoginTime, ");
        selectSql.append(" ubi.n_user_id as ubi_userId, ubi.s_nick_name nickName, ubi.s_real_name realName, ubi.s_idcard_number idcardNumber, ubi.s_remark remark FROM user_info ui  LEFT JOIN  user_base_info ubi on ui.n_user_id = ubi.n_user_id  WHERE 1=1 ");
        if (searchConditionJSONObject.getLong("userId") != null) {
            selectSql.append(" AND ui.n_user_id = ?");
            parms.add(searchConditionJSONObject.getLong("userId"));
        }

        if (searchConditionJSONObject.getString("loginName") != null && StringUtils.isNotBlank(searchConditionJSONObject.getString("loginName"))) {
            selectSql.append(" AND ui.s_login_name = ?");
            parms.add(searchConditionJSONObject.getString("loginName"));
        }

        if (searchConditionJSONObject.getString("mobileNumber") != null && StringUtils.isNotBlank(searchConditionJSONObject.getString("mobileNumber"))) {
            selectSql.append(" AND ui.s_mobile_number = ?");
            parms.add(searchConditionJSONObject.getString("mobileNumber"));
        }

        if (searchConditionJSONObject.getString("idcardNumber") != null && StringUtils.isNotBlank(searchConditionJSONObject.getString("idcardNumber"))) {
            selectSql.append(" AND ubi.s_idcard_number = ?");
            parms.add(searchConditionJSONObject.getString("idcardNumber"));
        }

        if (searchConditionJSONObject.getString("entranceCode") != null && StringUtils.isNotBlank(searchConditionJSONObject.getString("entranceCode"))) {
            selectSql.append(" AND ui.s_entrance_code = ?");
            parms.add(searchConditionJSONObject.getString("entranceCode"));
        }

        if (searchConditionJSONObject.getDate("registerTime") != null) {
            selectSql.append(" AND ui.d_register_time >= ?");
            parms.add(searchConditionJSONObject.getDate("registerTime"));
        }

        if (searchConditionJSONObject.getDate("registerTime1") != null) {
            selectSql.append(" AND ui.d_register_time <= ?");
            parms.add(searchConditionJSONObject.getDate("registerTime1"));
        }
        if (searchConditionJSONObject.getDate("lastLoginTime") != null) {
            selectSql.append(" AND ui.d_last_login_time >= ?");
            parms.add(searchConditionJSONObject.getDate("lastLoginTime"));
        }

        if (searchConditionJSONObject.getDate("lastLoginTime1") != null) {
            selectSql.append(" AND ui.d_last_login_time <= ?");
            parms.add(searchConditionJSONObject.getDate("lastLoginTime1"));
        }

        if (searchConditionJSONObject.getString("entranceCode") != null && StringUtils.isNotBlank(searchConditionJSONObject.getString("entranceCode"))) {
            selectSql.append(" AND ui.s_entrance_code = ?");
            parms.add(searchConditionJSONObject.getString("entranceCode"));
        }

        if (searchConditionJSONObject.getInteger("status") != null) {
            selectSql.append(" AND ui.n_status = ? ");
            parms.add(searchConditionJSONObject.getInteger("status"));
        }

        if (searchConditionJSONObject.getString("nickName") != null && StringUtils.isNotBlank(searchConditionJSONObject.getString("nickName"))) {
            selectSql.append(" AND ubi.s_nick_name = ?");
            parms.add(searchConditionJSONObject.getString("nickName"));
        }

        if (searchConditionJSONObject.getString("realName") != null && StringUtils.isNotBlank(searchConditionJSONObject.getString("realName"))) {
            selectSql.append(" AND ubi.s_real_name = ?");
            parms.add(searchConditionJSONObject.getString("realName"));
        }

        if (searchConditionJSONObject.getString("idcardNumber") != null && StringUtils.isNotBlank(searchConditionJSONObject.getString("idcardNumber"))) {
            selectSql.append(" AND ubi.s_idcard_number = ?");
            parms.add(searchConditionJSONObject.getString("idcardNumber"));
        }

        selectSql.append(" ORDER BY d_last_login_time DESC ");
        if (isPage) {
            selectSql.append(" limit ?, ? ");
            parms.add((start - 1) * limit);
            parms.add(limit);
        }
    }

    public Long getUserBaseInfoCount(JSONObject searchCondition) {
        StringBuilder selectSql = new StringBuilder();
        StringBuilder allSql = new StringBuilder();
        List<Object> parms = new ArrayList();
        assembleUserBaseInfoSql(selectSql, searchCondition, parms, false);
        allSql.append("SELECT count(s.userId) FROM (")
                .append(selectSql.toString())
                .append(") s");

        SQLQuery sqlQuery = getSQLQuery(allSql.toString());
        for (int i = 0; i < parms.size(); i++) {
            sqlQuery.setParameter(i, parms.get(i));
        }
        return Long.valueOf(sqlQuery.uniqueResult().toString());
    }

    public boolean updateIdcardnoAndRealName(Long userId, String idcardNumber, String realName) {
        String hql = "UPDATE UserBaseInfoENT SET realName = ?,idcardNumber= ?,updateTime = now() WHERE userId = ? ";
        Object[] params = new Object[]{realName, idcardNumber, userId};
        return executeHql(hql, params) > 0;
    }

    @Override
    public boolean nickNameAlreadyEdited(String userId) {
        String sql = "select s_nick_name as nickName from user_base_info where  n_user_id =:userId";
        return getSQLQuery(sql).setString("userId", userId).uniqueResult() != null;
    }

    @Override
    public boolean nickNameAlreadyExists(String nickName) {
        String sql = "select 1 from user_base_info where s_nick_name = :nickName";
        return getSQLQuery(sql).setString("nickName", nickName).uniqueResult() != null;
    }

    @Override
    public boolean editUserNickName(String userId, String nickName) {
        String hql = "UPDATE UserBaseInfoENT SET nickName = ?,updateTime = now() WHERE userId = ? ";
        Object[] params = new Object[]{nickName, Long.valueOf(userId)};
        return executeHql(hql, params) > 0;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public UserBaseInfoENT getUserBaseInfo(String userId) {
        if (org.apache.commons.lang.StringUtils.isBlank(userId)) {
            return null;
        }
        return get(UserBaseInfoENT.class, Long.valueOf(userId));
    }

    @Override
    public void updateUserBaseInfo(UserBaseInfoENT userBaseInfo) {
        saveOrUpdate(userBaseInfo);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public List<UserBaseInfoENT> listUserBaseInfoByIdcardNumber(String idcardNumber) {
        String hql = "FROM UserBaseInfoENT WHERE idcardNumber = ?";
        return find(hql, new Object[]{idcardNumber});
    }


    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void updateUserBaseInfoByAdmin(Long userId, String realName, String idcardNumber) {
        String sql = "UPDATE user_base_info SET s_real_name=:realName, s_idcard_number=:idcardNumber WHERE n_user_id = :userId";
        getSQLQuery(sql).setString("realName", realName).setString("idcardNumber", idcardNumber).setLong("userId", userId).executeUpdate();
    }

    @Override
    public UserBaseInfoDTO getUserBaseInfoById(Long userId) {
        StringBuilder allSql = new StringBuilder();
        allSql.append(" SELECT ui.n_user_id userId, ui.s_login_name loginName, ui.s_mobile_number mobileNumber, ui.s_thd_part_name thdPartName, ui.s_thd_part_type thdPartType, ui.s_entrance_code entranceCode, ui.d_register_time registerTime, ui.n_status 'status', ui.d_last_login_time as lastLoginTime, ");
        allSql.append(" ubi.n_user_id as ubi_userId, ubi.s_nick_name nickName, ubi.s_real_name realName, ubi.s_idcard_number idcardNumber FROM user_info ui  LEFT JOIN  user_base_info ubi on ui.n_user_id = ubi.n_user_id  WHERE 1=1 ");
        allSql.append(" AND ui.n_user_id =:userId");
        SQLQuery sqlQuery = getSQLQuery(allSql.toString());
        sqlQuery.addScalar("userId", StandardBasicTypes.LONG)
                .addScalar("loginName", StandardBasicTypes.STRING)
                .addScalar("mobileNumber", StandardBasicTypes.STRING)
                .addScalar("thdPartName", StandardBasicTypes.STRING)
                .addScalar("realName", StandardBasicTypes.STRING)
                .addScalar("nickName", StandardBasicTypes.STRING)
                .addScalar("idcardNumber", StandardBasicTypes.STRING)
                .addScalar("entranceCode", StandardBasicTypes.STRING)
                .addScalar("registerTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("lastLoginTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("status", StandardBasicTypes.INTEGER);
        sqlQuery.setLong("userId", userId);
        UserBaseInfoDTO user = (UserBaseInfoDTO) sqlQuery.setResultTransformer(Transformers.aliasToBean(UserBaseInfoDTO.class)).uniqueResult();
        return user;
    }

    @Override
    public List<UserInfoENT> getUserInfoByMobileAndUserId(String mobileNumber, Long userId) {
        String hql = "FROM UserInfoENT where mobileNumber=:mobileNumber AND userId !=:userId";
        Query query = getQuery(hql).setString("mobileNumber", mobileNumber).setLong("userId", userId);
        return query.list();
    }

    @Override
    public void resetUserNickNameAndHeadIcon(Long userId) {
        String sql = "UPDATE user_base_info SET s_nick_name=null, s_face_url=null WHERE n_user_id = :userId";
        getSQLQuery(sql).setLong("userId", userId).executeUpdate();
    }
    @Override
    public void resetUserFace(Long userId){
        String sql = "UPDATE user_base_info SET s_face_url=null WHERE n_user_id = :userId";
        getSQLQuery(sql).setLong("userId", userId).executeUpdate();
    }
    @Override
    public void resetNickName(Long userId){
        String sql = "UPDATE user_base_info SET s_nick_name=null WHERE n_user_id = :userId";
        getSQLQuery(sql).setLong("userId", userId).executeUpdate();
    }
}
