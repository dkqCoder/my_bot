package com.tty.user.dao.impl;

import com.jdd.fm.core.db.BaseDao;
import com.tty.user.controller.model.params.UserListParam;
import com.tty.user.dao.UserInfoQueryDao;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dto.UserBaseInfoDTO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linian on 2017/10/23.
 */
@Repository("userInfoQueryDao")
public class UserInfoQueryDaoImpl extends BaseDao<UserBaseInfoENT> implements UserInfoQueryDao {

    public List<UserBaseInfoDTO> listUserBaseInfo(UserListParam userListParam, Boolean all) {
        StringBuilder selectSql = new StringBuilder();
        List<Object> params = new ArrayList();

        assembleUserBaseInfoSql(selectSql, userListParam, params, true);
        SQLQuery sqlQuery = getSQLQuery(selectSql.toString());
        for (int i = 0; i < params.size(); i++) {
            sqlQuery.setParameter(i, params.get(i));
        }
        sqlQuery.addScalar("userId", StandardBasicTypes.LONG)
                .addScalar("loginName", StandardBasicTypes.STRING)
                .addScalar("mobileNumber", StandardBasicTypes.STRING)
                .addScalar("thdPartName", StandardBasicTypes.STRING)
                .addScalar("thdPartType", StandardBasicTypes.STRING)
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

    public Long listUserBaseInfoCount(UserListParam userListParam, Boolean all){
        StringBuilder selectSql = new StringBuilder();
        StringBuilder allSql = new StringBuilder();
        List<Object> parms = new ArrayList();
        assembleUserBaseInfoSql(selectSql, userListParam, parms, false);
        allSql.append("SELECT count(s.userId) FROM (")
                .append(selectSql.toString())
                .append(") s");

        SQLQuery sqlQuery = getSQLQuery(allSql.toString());
        for (int i = 0; i < parms.size(); i++) {
            sqlQuery.setParameter(i, parms.get(i));
        }
        return Long.valueOf(sqlQuery.uniqueResult().toString());
    }

    private void assembleUserBaseInfoSql(StringBuilder selectSql, UserListParam userListParam, List<Object> parms, boolean isPage) {

        int start = userListParam.getPageIndex();
        int limit = userListParam.getPageSize();
        selectSql.append(" SELECT ui.n_user_id userId, ui.s_login_name loginName, ui.s_mobile_number mobileNumber, ui.s_thd_part_name thdPartName, ui.s_thd_part_type thdPartType, ui.s_entrance_code entranceCode, ui.d_register_time registerTime, ui.n_status 'status', ui.d_last_login_time as lastLoginTime, ");
        selectSql.append(" ubi.n_user_id as ubi_userId, ubi.s_nick_name nickName, ubi.s_real_name realName, ubi.s_idcard_number idcardNumber, ubi.s_remark remark FROM user_info ui  LEFT JOIN  user_base_info ubi on ui.n_user_id = ubi.n_user_id  WHERE 1=1 ");
        if (StringUtils.isNotBlank(userListParam.getUserId())) {
            selectSql.append(" AND ui.n_user_id = ?");
            parms.add(userListParam.getUserId());
        }
        if (StringUtils.isNotBlank(userListParam.getLoginName())) {
            selectSql.append(" AND ui.s_login_name = ?");
            parms.add(userListParam.getLoginName());
        }

        if (StringUtils.isNotBlank(userListParam.getMobileNumber())) {
            selectSql.append(" AND ui.s_mobile_number = ?");
            parms.add(userListParam.getMobileNumber());
        }

        if (StringUtils.isNotBlank(userListParam.getIdcardNumber())) {
            selectSql.append(" AND ubi.s_idcard_number = ?");
            parms.add(userListParam.getIdcardNumber());
        }

        if (StringUtils.isNotBlank(userListParam.getEntranceCode())) {
            selectSql.append(" AND ui.s_entrance_code = ?");
            parms.add(userListParam.getEntranceCode());
        }

        if (userListParam.getBeginRegisterDate() != null) {
            selectSql.append(" AND ui.d_register_time >= ?");
            parms.add(userListParam.getBeginRegisterDate());
        }

        if (userListParam.getEndRegisterDate() != null) {
            selectSql.append(" AND ui.d_register_time <= ?");
            parms.add(userListParam.getEndRegisterDate());
        }

        if (userListParam.getStatus() != null) {
            selectSql.append(" AND ui.n_status = ? ");
            parms.add(userListParam.getStatus());
        }

        if (StringUtils.isNotBlank(userListParam.getNickName())) {
            selectSql.append(" AND ubi.s_nick_name = ?");
            parms.add(userListParam.getNickName());
        }

        if (StringUtils.isNotBlank(userListParam.getRealName())) {
            selectSql.append(" AND ubi.s_real_name = ?");
            parms.add(userListParam.getRealName());
        }

        selectSql.append(" ORDER BY d_last_login_time DESC ");
        if (isPage) {
            selectSql.append(" limit ?, ? ");
            parms.add((start - 1) * limit);
            parms.add(limit);
        }
    }

}
