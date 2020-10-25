package com.tty.user.dao.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.UserInfoDao;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.model.params.EntranceCountParams;
import com.tty.user.dto.RefreshUserInfoDTO;
import com.tty.user.dto.RegisterCountDTO;
import com.tty.user.dto.UserMobileDTO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;


/**
 * @author zhuxinhai
 */
@Repository("userInfoDao")
public class UserInfoDaoImpl extends BaseDao<UserInfoENT> implements UserInfoDao {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoDaoImpl.class);

    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;

    public void saveUserInfo(UserInfoENT ent) {
        save(ent);
    }

    public void updateUserInfo(UserInfoENT ent) {
        update(ent);
    }

    public void deleteUserInfo(UserInfoENT ent) {
        delete(ent);
    }

    public void saveOrUpdateUserInfo(UserInfoENT ent) {
        saveOrUpdate(ent);
    }

    @Override
    public List<UserInfoENT> listUserInfo(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserInfoENT where 1=1 ")
                .andEq("name", data.getString("name"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listUserInfoCount(JSONObject data) {
        String hql = "select count(id) from UserInfoENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("name", data.getString("name"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

    @Override
    public Boolean updateUserMobileNumberAndPassword(String userId, String mobileNumber, String password) {
        String hql = "UPDATE UserInfoENT SET mobileNumber = ?,password = ? WHERE userId = ? ";
        Object[] params = new Object[]{mobileNumber, password, userId};
        return executeHql(hql, params) > 0;
    }

    @Override
    public Boolean updateUserMobileNumber(String userId, String mobileNumber) {
        String hql = "UPDATE UserInfoENT SET mobileNumber = ?,updateTime = now() WHERE userId = ? ";
        Object[] params = new Object[]{mobileNumber, Long.valueOf(userId)};
        return executeHql(hql, params) > 0;
    }

    @Override
    public Boolean updateUserPass(String userId, String password) {
        String hql = "UPDATE UserInfoENT SET password = ?,updateTime = now() WHERE userId = ? ";
        Object[] params = new Object[]{password, Long.valueOf(userId)};
        return executeHql(hql, params) > 0;
    }

    @Override
    public Boolean updateUserPayPass(String userId, String payPassword) {
        String hql = "UPDATE UserInfoENT SET payPassword = ?,updateTime = now() WHERE userId = ? ";
        Object[] params = new Object[]{payPassword, Long.valueOf(userId)};
        return executeHql(hql, params) > 0;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public Boolean updateUserWeChat(String userId, String thdPartId, String thdPartName, String thdPartType) {
        String hql = "UPDATE UserInfoENT SET thdPartId = ?,thdPartName = ?,thdPartType = ?,updateTime = now() WHERE userId = ? ";
        Object[] params = new Object[]{thdPartId, thdPartName, thdPartType, Long.valueOf(userId)};
        return executeHql(hql, params) > 0;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public UserInfoENT getUserInfo(String userId) {
        if (StringUtils.isBlank(userId) || !StringUtils.isNumeric(userId)) {
            return null;
        }
        String hql = "FROM UserInfoENT WHERE userId = ?";
        List<UserInfoENT> list = find(hql, Long.valueOf(userId));
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public UserInfoENT getUserInfoByLoginName(String loginName) {
        String hql = "FROM UserInfoENT WHERE loginName = ?";
        List<UserInfoENT> list = find(hql, new Object[]{loginName});
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public UserInfoENT getUserInfoByThdId(String thdId) {
        String hql = "FROM UserInfoENT WHERE thdPartId = ?";
        List<UserInfoENT> list = find(hql, new Object[]{thdId});
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public UserInfoENT getUserInfoByThdId(String thdId, String thdType) {
        String hql = "FROM UserInfoENT WHERE thdPartId = ? and thdPartType = ? ";
        List<UserInfoENT> list = find(hql, new Object[]{thdId, thdType});
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<UserInfoENT> getUserThirdInfo() {
        String hql = "FROM UserInfoENT WHERE thdPartId ";
        List<UserInfoENT> list = find(hql, new Object[]{});
        return list;
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public void emptyUserInfoRedisCache(Long userId) {
        try {
            //删除
            UserInfoENT info = getUserInfo(String.valueOf(userId));
            if (info == null || info.getUserId() == null) {
                return;
            }
            userRedis.del(String.format(UserRedisKeys.USER_INFO_KEY_ID, String.valueOf(userId)));
            userRedis.del(String.format(UserRedisKeys.USER_BASE_INFO_KEY_ID, String.valueOf(userId)));
            if (info.getMobileNumber() != null) {
                userRedis.del(String.format(UserRedisKeys.USRE_INFO_MOBILE_LIST_NUMBER, info.getMobileNumber()));
            }
            if (info.getLoginName() != null) {
                userRedis.del(String.format(UserRedisKeys.USER_INFO_NAME_KEY_NAME, info.getLoginName()));
            }

        } catch (Exception e) {
            logger.error("清空用户缓存异常 useriD:{}", userId);
        }
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public void netEmptyUserInfoRedisCache(Long userId) {
        try {
            //删除
            UserInfoENT info = getUserInfo(String.valueOf(userId));
            if (info == null || info.getUserId() == null) {
                return;
            }
        } catch (Exception e) {
            logger.error("清空net用户缓存异常 userId:{}", userId);
        }
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void resetUserPasswd(Long userId, String randowmPasswd) {
        String sql = "UPDATE user_info SET s_password = :passwd WHERE n_user_id = :userId";
        getSQLQuery(sql).setString("passwd", randowmPasswd).setLong("userId", userId).executeUpdate();
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void resetUserPayPasswd(Long userId) {
        String sql = "UPDATE user_info SET s_pay_password = '' WHERE n_user_id = :userId";
        getSQLQuery(sql).setLong("userId", userId).executeUpdate();
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void updateUserInfoByAdmin(Long userId, String mobileNumber) {
        String sql = "UPDATE user_info SET s_mobile_number = :mobileNumber WHERE n_user_id = :userId";
        getSQLQuery(sql).setString("mobileNumber", mobileNumber).setLong("userId", userId).executeUpdate();
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void updateUserLastLoginTime(String userId, Date date) {
        String hql = "UPDATE UserInfoENT SET lastLoginTime = ? WHERE userId = ? ";
        Object[] params = new Object[]{date, Long.valueOf(userId)};
        executeHql(hql, params);
    }

    @Override
    public List<UserInfoENT> getUserInfoByMobileNumber(Long userId, Integer size) {
        WhereUtils where = WhereUtils.ins("from UserInfoENT where 1=1  ")
                .andLt("userId", userId)
                .addOrderBy(" ORDER BY userId desc ");
        List<UserInfoENT> list = findPageByListParam(where.getAllSql(), 1, size, where.getParms());
        return list;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void updateUserInfoStatus(Long userId, int status, String reason) {
        String sql = "UPDATE user_info SET n_status =:status,d_update_time = now() WHERE n_user_id =:userId ";
        getSQLQuery(sql).setInteger("status", status).setLong("userId", userId).executeUpdate();
        String sql2 = "UPDATE user_base_info SET s_remark=:reason WHERE n_user_id = :userId";
        getSQLQuery(sql2).setString("reason", reason).setLong("userId", userId).executeUpdate();
    }

    @Override
    public Boolean updateUserWxInfoSetEmpty(Long userId) {
        String hql = "UPDATE UserInfoENT SET thdPartId='',thdPartName='',thdPartType='',updateTime = now() WHERE userId=? AND thdPartType=?";
        Object[] params = new Object[]{userId, "WXDL"};
        return executeHql(hql, params) > 0;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public List<RegisterCountDTO> listRegisterCount(JSONObject searchCondition) {
        EntranceCountParams entranceCountParams = GfJsonUtil.parseObject(searchCondition.getString("data"), EntranceCountParams.class);
        if (entranceCountParams.getEndDate() == null || entranceCountParams.getStartDate() == null) {
            return new ArrayList<>();
        }

        StringBuilder selectSql = new StringBuilder(" select s_entrance_code as entranceCode, count(1) as num from user_info ");
        selectSql.append(" where 1=1 " );
        if(StringUtils.isNotBlank(entranceCountParams.getCmdNames())) {
            String cmdNames = Arrays.stream(entranceCountParams.getCmdNames().split(",")).map(x -> "'" + x + "'").reduce((r1, r2) -> r1 + "," + r2).orElse("");
            cmdNames = cmdNames.substring(0, cmdNames.length());
            if (StringUtils.isNotBlank(cmdNames)) {
                selectSql.append(" and s_entrance_code in (" + cmdNames + ")");
            }
        }
        selectSql.append(" and d_register_time>=:startTime and d_register_time<:endTime group by s_entrance_code");

        SQLQuery sqlQuery = getSQLQuery(selectSql.toString());
        sqlQuery.addScalar("entranceCode", StandardBasicTypes.STRING)
                .addScalar("num", StandardBasicTypes.STRING)
                .setTimestamp("startTime", entranceCountParams.getStartDate())
                .setTimestamp("endTime", entranceCountParams.getEndDate())
                .setResultTransformer(Transformers.aliasToBean(RegisterCountDTO.class));

        return sqlQuery.list();
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public List<UserInfoENT> listUserInfoByUuid(String uuid) {
        String hql = "FROM UserInfoENT WHERE uuid = ?";
        return find(hql, new Object[]{uuid});
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public List<UserInfoENT> listUserInfoByUuidAndCmdName(String uuid, String cmdName) {
        String hql = "FROM UserInfoENT WHERE uuid = ? and entranceCode = ?";
        return find(hql, new Object[]{uuid, cmdName});
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public List<UserMobileDTO> getMobilesLastOneHour() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - 1);
        Date anHourAgo = c.getTime();
        String sql = " select s_mobile_number as mobileNumber from user_info where d_register_time > :anHourAgo and s_mobile_number is not null";
        SQLQuery sqlQuery = getSQLQuery(sql);
        sqlQuery.addScalar("mobileNumber", StandardBasicTypes.STRING)
                .setTimestamp("anHourAgo", anHourAgo)
                .setResultTransformer(Transformers.aliasToBean(UserMobileDTO.class));
        return sqlQuery.list();
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public List<UserMobileDTO> getMobilesBetween(Long minUserId, Long maxUserId) {
        String sql = " select s_mobile_number as mobileNumber from user_info where n_user_id >= :minUserId and n_user_id <=:maxUserId and s_mobile_number is not null and n_status=1";
        SQLQuery sqlQuery = getSQLQuery(sql);
        sqlQuery.addScalar("mobileNumber", StandardBasicTypes.STRING)
                .setLong("minUserId", minUserId)
                .setLong("maxUserId", maxUserId)
                .setResultTransformer(Transformers.aliasToBean(UserMobileDTO.class));
        return sqlQuery.list();
    }

    @Override
    public List<UserInfoENT> getUserInfoByMobile(String mobile) {
        String hql = "FROM UserInfoENT WHERE mobileNumber = ?";
        return find(hql, new Object[]{mobile});
    }

    private RefreshUserInfoDTO createRefreshUserInfoDTO(UserInfoENT info, UserBaseInfoENT baseInfo) {
        RefreshUserInfoDTO dto = new RefreshUserInfoDTO();
        if (info != null) {
            dto.setUserId(info.getUserId());
            dto.setMobile(info.getMobileNumber() == null ? "" : info.getMobileNumber());
            if (info.getMobileNumber() != null && info.getMobileNumber().length() > 1) {
                dto.setIsMobileValidated(true);
            } else {
                dto.setIsMobileValidated(false);
            }
        }
        if (baseInfo != null) {
            dto.setIdCardNumber(baseInfo.getIdcardNumber() == null ? "" : baseInfo.getIdcardNumber());
            dto.setRealityName(baseInfo.getRealName() == null ? "" : baseInfo.getRealName());
        }
        return dto;
    }

    @Override
    public List<UserInfoENT> getUserInfoByDate(Integer queryHour) {
        StringBuilder sqlBuilder = new StringBuilder("select n_user_id as userId, s_login_name as loginName, s_ipaddress as ipAddress, s_password as password," +
                " s_uuid as uuid, s_platform_code as platformCode,s_mobile_number as mobileNumber, s_phone_name as phoneName from user_info where d_register_time >=(NOW() - interval :queryHour hour)");
        return getSQLQuery(sqlBuilder.toString())
                .addScalar("userId", StandardBasicTypes.LONG)
                .addScalar("loginName")
                .addScalar("ipAddress")
                .addScalar("password")
                .addScalar("uuid")
                .addScalar("platformCode")
                .addScalar("mobileNumber")
                .addScalar("phoneName")
                .setInteger("queryHour", queryHour)
                .setResultTransformer(Transformers.aliasToBean(UserInfoENT.class)).list();
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public Integer getUuidsByDate(Date date, String uuid) {
        String sql = "select COUNT(1) as uuid from user_info WHERE s_uuid=? AND d_register_time>=?";
        BigInteger result = (BigInteger) Optional.ofNullable(getSQLQuery(sql)
                .setString(0, uuid)
                .setDate(1, date)
                .uniqueResult()).orElse(0);
        return result.intValue();
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public Integer getIpsByDate(Date date, String ip) {
        String sql = "select COUNT(1) as ip from user_info WHERE s_ipaddress=? AND d_register_time>=?";
        BigInteger result = (BigInteger) Optional.ofNullable(getSQLQuery(sql)
                .setString(0, ip)
                .setDate(1, date)
                .uniqueResult()).orElse(0);
        return result.intValue();
    }
}
