package com.tty.user.common.utils;

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.StringUtils;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserInfoExtensionFields;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.controller.model.result.UserSignInModel;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.mission.pojo.MisUserEndMission;
import com.tty.user.model.result.UserIntegralMissionResult;
import com.tty.user.service.UserInfoService;
import com.tty.user.service.UserIntegralCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.tty.common.utils.Result;

import java.util.*;

/**
 * @author ln
 */
@Component
public class PublicCommonRedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(PublicCommonRedisUtil.class);
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;
    @Autowired
    private UserIntegralCoreService userIntegralCoreService;
    @Autowired
    private UserInfoService userInfoService;

    public String getHideUserName(Long userId) {
        String userName = UserContext.DEFAULT_USER_NAME;
        if (userId == null) {
            return userName;
        }
        if (userId.longValue() == UserContext.INFO_SYSTEM_MANAGER_ID.longValue()) {
            return UserContext.INFO_SYSTEM_MANAGER_NAME;
        }
        try {
            UserBaseInfoENT userBaseInfo = userInfoService.getCurrentUserBaseInfo(String.valueOf(userId));
            if (userBaseInfo != null) {
                if (StringUtils.isNotBlank(userBaseInfo.getNickName())) {
                    userName = userBaseInfo.getNickName();
                } else {
                    if (StringUtils.isNotBlank(userBaseInfo.getName())) {
                        userName = MobileUtil.getSaveMobileNO(userBaseInfo.getName());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("userId={},?????????????????????????????????????????????", userId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return userName;
    }

    public String getFullUserName(Long userId) {
        String userName = UserContext.DEFAULT_USER_NAME;
        try {
            UserBaseInfoENT userBaseInfo = userInfoService.getCurrentUserBaseInfo(String.valueOf(userId));
            if (userBaseInfo != null) {
                if (StringUtils.isNotBlank(userBaseInfo.getNickName())) {
                    userName = userBaseInfo.getNickName();
                } else {
                    if (StringUtils.isNotBlank(userBaseInfo.getName())) {
                        userName = userBaseInfo.getName();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("userId={},????????????????????????????????????", userId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return userName;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/12 11:48
     * @Description ??????????????????
     */
    public String getUserFace(Long userId) {
        String faceUrl = null;
        UserBaseInfoENT userBaseInfo = userInfoService.getCurrentUserBaseInfo(String.valueOf(userId));
        if (userBaseInfo != null) {
            try {
                if (StringUtils.isNotBlank(userBaseInfo.getFaceUrl())) {
                    faceUrl = userBaseInfo.getFaceUrl();
                }
            } catch (Exception e) {
                logger.error("???????????????????????????????????????", userId, LogExceptionStackTrace.erroStackTrace(e));
            }
        }
        if (faceUrl == null) {
            faceUrl = "";
            return faceUrl;
        }
        if (!faceUrl.contains("http")) {
            faceUrl = UserContext.USER_FACE_URL + faceUrl;
        }
        return faceUrl;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/19 11:17
     * @Description ??????????????????Redis Hash Value
     */
    public String getUserInfoExtension(Long userId, String field) {
        String value = null;
        try {
            value = userRedis.hget(String.format(UserRedisKeys.USER_INFO_EXTENSION_KEY, userId.toString()), field);
        } catch (Exception e) {
            logger.error("????????????hashvalue?????? ???userId={};field={};stackTrace:{}???", userId, field, LogExceptionStackTrace.erroStackTrace(e));
        }
        return value;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/19 11:44
     * @Description ??????????????????Redis Hash Value
     */
    public void setUserInfoExtension(Long userId, String field, String value) {
        try {
            userRedis.hset(String.format(UserRedisKeys.USER_INFO_EXTENSION_KEY, userId.toString()), field, value);
        } catch (Exception e) {
            logger.error("????????????hashvalue?????? ???userId={};field={};value:{};stackTrace:{}???", userId, field, value, LogExceptionStackTrace.erroStackTrace(e));
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/1/4 13:07
     * @Description ?????????????????????
     */
    public Long increaseUserGrowups(Long userId, Long value) {
        return userRedis.hincrBy(String.format(UserRedisKeys.USER_INFO_EXTENSION_KEY, userId), UserInfoExtensionFields.USER_GROWUPS, value);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/27 19:37
     * @Description ??????????????????
     */
    public boolean increaseUserIntegral(Long userId, String field, Integer value) {
        try {
            Long result = userRedis.hincrBy(String.format(UserRedisKeys.USER_INFO_EXTENSION_KEY, userId), field, new Long(value));
            if (result >= 0) {
                return true;
            } else {
                userRedis.hincrBy(String.format(UserRedisKeys.USER_INFO_EXTENSION_KEY, userId), field, new Long(value * (-1)));
                return false;
            }

        } catch (Exception e) {
            logger.error("???????????????????????????userId={};field={};value={};stackTrace:{}???", userId, field, value, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public com.tty.common.utils.Result increaseUserIntegralResult(String traceId, Long userId, String field, Integer value) {
        Result resStatus = new Result();
        resStatus.setCode(0);

        try {
            //hincrBy????????????????????????
            Long result = userRedis.hincrBy(String.format(UserRedisKeys.USER_INFO_EXTENSION_KEY, userId), field, new Long(value));
            HashMap map = new HashMap<String, Long>();
            if (result >= 0) {
                //????????????0??????????????????
                resStatus.setCode(UserContext.USER_DEDUCTION_INTEGRAL_CORRECT_FLAG);
                map.put("integral", result);
                resStatus.setData(map);
                resStatus.setMsg(UserContext.USER_DEDUCTION_INTEGRAL_CORRECT);
                logger.info("????????????redis???????????? traceId:{},userid:{},integral:{},balance:{}",
                        traceId, userId, value, result);
            } else {
                //??????0??????????????????(???????????????????????????)
                result = userRedis.hincrBy(String.format(UserRedisKeys.USER_INFO_EXTENSION_KEY, userId), field, new Long(value * (-1)));
                resStatus.setCode(UserContext.USER_DEDUCTION_INTEGRAL_LOW_FLAG);
                map.put("integral", result);
                resStatus.setData(map);
                resStatus.setMsg(UserContext.USER_DEDUCTION_INTEGRAL_LOW);
                logger.info("????????????redis??????????????????????????? traceId:{},userid:{},integral:{},balance:{}",
                        traceId, userId, value, result);
            }
        } catch (Exception e) {
            resStatus.setCode(Result.ERROR);
            resStatus.setMsg(Result.MSG_ERROR_DESC);
            logger.error("???????????????????????????traceId={};userId={};field={};integral={};stackTrace:{}???", traceId, userId, field,
                    value, LogExceptionStackTrace.erroStackTrace(e));
        }
        return resStatus;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/19 13:42
     * @Description ???????????????????????????????????????20 set
     */
    public boolean isInFirstSinglePaySet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_SINGLEPAY_TWENTY_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2016/12/19 14:00
     * @Description
     */
    public void insertIntoRedisSet(String setKey, Long userId) {
        userRedis.sadd(setKey, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 20:00
     * @Description
     */
    public void insertIntoRedisSet(String setKey, Long userId, Integer seconds) {
        insertIntoRedisSet(setKey, userId);
        userRedis.expire(setKey, seconds);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 16:06
     * @Description ??????
     */
    public String getValue(String redisKey) {
        return userRedis.get(redisKey);
    }

    public void setValue(String key, String value, Integer seconds) {
        userRedis.set(key, value);
        userRedis.expire(key, seconds);
    }

    public void setValue(String key, String value) {
        userRedis.set(key, value);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/19 13:43
     * @Description ???????????????????????????????????????set
     */
    public boolean isInFirstFollowSchemeSet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_FOLLOWSCHEME_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2016/12/19 13:31
     * @Description ?????????????????????????????????????????? (???set?????????????????????)
     */
    public boolean isInFirstBuySchemeSet(Long userId, Integer lotteryId) {
        String redisKey = String.format(UserRedisKeys.USER_BUYSCHEME_KEY, lotteryId);
        return userRedis.sismember(redisKey, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2016/12/19 17:18
     * @Description ???????????????????????????????????????
     */
    public boolean isInFirstBindPhoneSet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_BINDPHONR_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 18:58
     * @Description ???????????????????????????????????????
     */
    public boolean isInFirstTimeBindBankCardSet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_BINDBANK_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:02
     * @Description ???????????????????????????????????????
     */
    public boolean isInFirstTimeBindCertCardSet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_BINDCERT_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 20:57
     * @Description
     */
    public boolean isInEnterIntegralSet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_ENTER_INTEGRAL_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2016/12/19 14:45
     * @Description ???????????????????????????????????????
     */
    public Integer getUserDailyIntegral(Long userId) {
        String redisKey = String.format(UserRedisKeys.USER_DAILYBUY_KEY, userId);
        String value = userRedis.get(redisKey);
        if (value == null) {
            return 0;
        } else {
            return Integer.parseInt(value);
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/1/3 10:02
     * @Description ????????????????????????????????????
     */
    public void setUserDailyBuyIntegral(Long userId, Long integralToAdd, Integer seconds) {
        String redisKey = String.format(UserRedisKeys.USER_DAILYBUY_KEY, userId);
        userRedis.incr(redisKey, integralToAdd);
        userRedis.expire(redisKey, seconds);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:33
     * @Description ?????????????????????????????????set
     */
    public boolean isInShowSchemeTodaySet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_DAILY_SHOWSCHEME_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:44
     * @Description ?????????????????????????????????set
     */
    public boolean isInShareTodaySet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_DAILY_SHARE_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:45
     * @Description ?????????????????????????????????set
     */
    public boolean isInCommentTodaySet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_DAILY_COMMENT_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2016/12/28 17:58
     * @Description ????????????set
     */
    public boolean isInSignInTodaySet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_DAILY_SIGNIN_KEY, userId.toString());
    }

    /**
     * ????????????????????????
     *
     * @param userId
     * @param days
     * @param expireTime
     * @return
     */
    public boolean setSignInDays(Long userId, Long days, Long expireTime) {
        try {
            userRedis.set(String.format(UserRedisKeys.USER_SIGN_DAYS_KEY, userId.toString()), days.toString());
            userRedis.expire(String.format(UserRedisKeys.USER_SIGN_DAYS_KEY, userId.toString()), expireTime.intValue());
        } catch (Exception e) {
            logger.error("?????????????????????????????? ???UserId={};days={};StackTrace: {}???", userId, days, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
        return true;
    }

    /**
     * ???????????????????????????????????????null
     *
     * @param userId
     * @return
     */
    public Long getSignInDays(Long userId) {
        try {
            String days = userRedis.get(String.format(UserRedisKeys.USER_SIGN_DAYS_KEY, userId.toString()));
            if (StringUtils.isNotBlank(days)) {
                return Long.valueOf(days);
            }
        } catch (Exception e) {
            logger.error("???????????????????????????????????? ???UserId={};StackTrace: {}???", userId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return null;
    }

    public void delSignInDays(Long userId) {
        userRedis.del(String.format(UserRedisKeys.USER_SIGN_DAYS_KEY, userId.toString()));
    }

    /**
     * @Author shenwei
     * @Date 2017/1/6 20:20
     * @Description ????????????set
     */
    public boolean isInLoginTodaySet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_DAILY_LOGIN_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2017/1/16 15:44
     * @Description ????????????set
     */
    public boolean isInBuyedUserSet(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_BUYED_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2017/1/9 10:11
     * @Description ??????????????????????????????????????????
     */
    public Long getUserDailyBuyGrowup(Long userId) {
        String redisKey = String.format(UserRedisKeys.USER_DAILYBUY_GROWUP_KEY, userId);
        String value = userRedis.get(redisKey);
        if (value == null) {
            return new Long(0);
        } else {
            return Long.parseLong(value);
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/1/3 10:02
     * @Description ???????????????????????????????????????
     */
    public void setUserDailyBuyGrowup(Long userId, Long growupToAdd, Integer seconds) {
        String redisKey = String.format(UserRedisKeys.USER_DAILYBUY_GROWUP_KEY, userId);
        userRedis.incr(redisKey, growupToAdd);
        userRedis.expire(redisKey, seconds);
    }

    /**
     * @Author shenwei
     * @Date 2017/1/13 15:40
     * @Description ??????????????????????????????????????????
     */
    public boolean isEnterLevelCenter(Long userId) {
        return userRedis.sismember(UserRedisKeys.USER_ENTER_LEVEL_CENTER_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2017/1/13 16:00
     * @Description ????????????????????????
     */
    public void enterLevelCenter(Long userId) {
        userRedis.sadd(UserRedisKeys.USER_ENTER_LEVEL_CENTER_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2017/1/13 16:03
     * @Description ????????????????????????userId
     */
    public void removeEnterLevelCenter(Long userId) {
        userRedis.srem(UserRedisKeys.USER_ENTER_LEVEL_CENTER_KEY, userId.toString());
    }

    /**
     * @Author shenwei
     * @Date 2017/1/16 15:50
     * @Description ???????????????????????????set
     */
    public void insertIntoUserBuySet(Long userId) {
        userRedis.sadd(UserRedisKeys.USER_BUYED_KEY, userId.toString());
    }

    /**
     * @param UserId
     * @param day    (???????????? 1???????????????2???????????????3???????????????4???????????????5???????????????6???????????????0????????????)
     * @return
     * @author zhudonghai
     * @Date 2016-12-22
     * @Description ????????????????????????????????????????????????
     */
    public Boolean saddForvevrdaysByWeek(Long UserId, Integer day, Integer expireTime) {

        try {
            userRedis.sadd(String.format(UserRedisKeys.USER_MISSION_FOREVERLOVEDAYS_KEY, UserId.toString()), day.toString());
            userRedis.expire(String.format(UserRedisKeys.USER_MISSION_FOREVERLOVEDAYS_KEY, UserId.toString()), expireTime);
        } catch (Exception e) {
            logger.error("???????????????????????????????????????????????? ???UserId={};day={};StackTrace: {}???", UserId, day, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
        return true;
    }

    /**
     * @param UserId
     * @return
     * @author zhudonghai
     * @Date 2016-12-22
     * @Description ???????????????????????????????????????
     */
    public Set<String> smembersForvevrdaysByWeek(Long UserId) {
        Set<String> smembers = new HashSet<String>();
        try {
            smembers = userRedis.smembers(String.format(UserRedisKeys.USER_MISSION_FOREVERLOVEDAYS_KEY, UserId.toString()), "");
        } catch (Exception e) {
            logger.error("??????????????????????????????????????? ???UserId={};StackTrace: {}???", UserId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return smembers;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/23 13:11
     * @Description remove cache
     */
    public void removeCache(String cacheKey) {
        userRedis.del(cacheKey);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/23 13:32
     * @Description ????????????????????????????????????
     */
    public void removeUserIntegralRecordsCache(Long userId) {
        String pagesCacheKey = String.format(UserRedisKeys.USER_INTEGRAL_RECORDS_PAGESKEYS_KEY, userId);
        String[] keys = pagesCacheKey.split(",");
        if (keys != null) {
            for (String key : keys) {
                removeCache(key);
            }
        }
        removeCache(pagesCacheKey);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/26 14:46
     * @Description
     */
    public void appendUserIntegralRecordsCache(Long userId, String key) {
        String pagesCacheKey = String.format(UserRedisKeys.USER_INTEGRAL_RECORDS_PAGESKEYS_KEY, userId);
        String value = getValue(pagesCacheKey);
        if (value.isEmpty()) {
            setValue(pagesCacheKey, key);
        } else {
            setValue(pagesCacheKey, value + "," + key);
        }
    }

    /**
     * ????????????????????????(?????????????????????)
     *
     * @param UserId
     * @param missList
     * @param expireTime
     * @return
     */
    public Boolean setUserMissionInfo(Long UserId, String missList, Long expireTime) {
        try {
            userRedis.set(String.format(UserRedisKeys.USER_MISSION_INFO_KEY, UserId.toString()), missList);
            userRedis.expire(String.format(UserRedisKeys.USER_MISSION_INFO_KEY, UserId.toString()), expireTime.intValue());
        } catch (Exception e) {
            logger.error("?????????????????????????????? ???UserId={};StackTrace: {}???", UserId, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
        return true;
    }

    public List<UserIntegralMissionResult> getUserMissionInfo(Long UserId) {
        List<UserIntegralMissionResult> missList = null;
        try {
            missList = GfJsonUtil.parseArray(userRedis.get(String.format(UserRedisKeys.USER_MISSION_INFO_KEY, UserId.toString())),
                    UserIntegralMissionResult.class);
        } catch (Exception e) {
            logger.error("??????????????????????????????????????? ???UserId={};StackTrace: {}???", UserId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return missList;
    }

    public Boolean clearUserMissionInfo(Long UserId) {
        try {
            userRedis.del(String.format(UserRedisKeys.USER_MISSION_INFO_KEY, UserId.toString()));
        } catch (Exception e) {
            logger.error("???????????????????????????????????? ???UserId={};StackTrace: {}???", UserId, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
        return true;
    }

    public Boolean clearUserMissionInfo(MisUserEndMission endMiss) {
        try {
            List<UserIntegralMissionResult> missList = getUserMissionInfo(endMiss.getUserid());
            if (missList != null && missList.size() > 0) {
                List<UserIntegralMissionResult> newMissList = new ArrayList<UserIntegralMissionResult>();
                for (UserIntegralMissionResult miss : missList) {
                    UserIntegralMissionResult newMiss = miss;
                    if (miss.getMisId() != null && miss.getMisId().equals(endMiss.getMissionId())) {
                        newMiss.setCreateDate(endMiss.getCreateDate());
                        newMiss.setIsGet(endMiss.getIsAdd());
                        newMiss.setIsEnd(endMiss.getIsEnd());
                    }
                    newMissList.add(newMiss);
                }
                newMissList = userIntegralCoreService.getNewMissionInfoList(newMissList);
                if (newMissList != null && newMissList.size() > 0) {
                    userRedis.del(String.format(UserRedisKeys.USER_MISSION_INFO_KEY, endMiss.getUserid().toString()));
                    setUserMissionInfo(endMiss.getUserid(), GfJsonUtil.toJSONString(newMissList), DateUtils.getSecondsToTomorrow());
                }
            }

        } catch (Exception e) {
            logger.error("???????????????????????????????????? ???UserId={};StackTrace: {}???", endMiss.getUserid(), LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
        return true;
    }

    public void flushUserSignInfo(UserSignInModel userSignInModel, String userId) {
        String key = String.format(UserRedisKeys.USER_SIGN_INFO, userId);
        userRedis.set(key, GfJsonUtil.toJSONString(userSignInModel));
        userRedis.expire(key, DateUtils.getSecondsToTomorrow().intValue());
    }
}
