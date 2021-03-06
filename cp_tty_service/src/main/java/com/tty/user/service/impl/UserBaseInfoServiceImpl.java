package com.tty.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.ehcache.EhcacheManager;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.common.utils.Result;
import com.tty.ots.trade.dto.UserBalanceDTO;
import com.tty.ots.trade.dto.UserBankCardDTO;
import com.tty.ots.trade.outservice.TradeOutService;
import com.tty.user.common.utils.*;
import com.tty.user.context.*;
import com.tty.user.controller.model.result.UserSignInModel;
import com.tty.user.dao.UserBaseInfoDao;
import com.tty.user.dao.UserInfoDao;
import com.tty.user.dao.UserStopReasonDao;
import com.tty.user.dao.WebUserLevelGrowUpDao;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.dao.entity.UserLoginRecordENT;
import com.tty.user.dao.entity.UserStopReasonENT;
import com.tty.user.dto.UserBaseInfoDTO;
import com.tty.user.dto.UserBaseInfoForApiDTO;
import com.tty.user.dto.UserCenterDTO;
import com.tty.user.dto.UserLevelAndIntegralDTO;
import com.tty.user.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.util.AssertionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by donne on 17/03/07.
 */
@Service("userBaseInfoService")
public class UserBaseInfoServiceImpl implements UserBaseInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserBaseInfoServiceImpl.class);

    @Autowired
    private UserBaseInfoDao userBaseInfoDao;
    @Autowired
    private WebUserLevelGrowUpDao webUserLevelGrowUpDao;
    @Autowired
    private UserStopReasonDao userStopReasonDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;
    @Autowired
    private UserIntegralCoreService userIntegralCoreService;
    @Autowired
    private UserSignInService userSignInService;
    @Autowired
    private ShardedRedisService shardedRedisService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EhcacheManager ehcacheManager;
    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private TradeOutService tradeOutService;

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listUserBaseInfo(JSONObject jsonParam) {
        ExtModel em = new ExtModel();
        List<UserBaseInfoDTO> userBaseInfoDTOS = userBaseInfoDao.listUserBaseInfo(jsonParam, false);
        for (UserBaseInfoDTO userBaseInfoDTO : userBaseInfoDTOS) {
            if (StringUtils.isNotBlank(userBaseInfoDTO.getMobileNumber())) {
                userBaseInfoDTO.setMobileNumber("?????????");
            } else {
                userBaseInfoDTO.setMobileNumber("?????????");
            }

            if (StringUtils.isNotBlank(userBaseInfoDTO.getIdcardNumber())) {
                userBaseInfoDTO.setIdcardNumber("?????????");
            } else {
                userBaseInfoDTO.setIdcardNumber("?????????");
            }
        }
        em.setData(userBaseInfoDTOS);
        em.setTotal(userBaseInfoDao.getUserBaseInfoCount(jsonParam));
        return em;
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listUserBaseInfoAll(JSONObject jsonParam) {
        ExtModel em = new ExtModel();
        em.setData(userBaseInfoDao.listUserBaseInfo(jsonParam, true));
        em.setTotal(userBaseInfoDao.getUserBaseInfoCount(jsonParam));
        return em;
    }

    /**
     * ??????????????????????????? realName, mobileName, idcardNumber
     *
     * @param userId
     * @param realName
     * @param mobileNumber
     * @param idcardNumber
     * @return
     */
    @Override
    public ExtModel updateUserBaseInfoByAdmin(Long userId, String realName, String mobileNumber, String idcardNumber) {
        ExtModel em = new ExtModel();
        //???????????????
        userBaseInfoDao.updateUserBaseInfoByAdmin(userId, realName, idcardNumber);
        userInfoDao.updateUserInfoByAdmin(userId, mobileNumber);

        //???????????? ????????????net redis??????
        UserInfoENT currentUserInfo = userInfoService.getCurrentUserInfo(String.valueOf(userId));
        currentUserInfo.setMobileNumber(mobileNumber);

        UserBaseInfoENT currentUserBaseInfo = userInfoService.getCurrentUserBaseInfo(String.valueOf(userId));
        currentUserBaseInfo.setIdcardNumber(idcardNumber);
        currentUserBaseInfo.setRealName(realName);
        userInfoService.updateUserRedisByAdmin(currentUserBaseInfo, currentUserInfo);

        return em;
    }


    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public ExtModel updateUserBaseInfoStatus(Long userId, int status, String reason) {
        ExtModel em = new ExtModel();
        //????????????
        if (status == UserContext.USER_STATUS_STOP) {
            userInfoDao.updateUserInfoStatus(userId, status, reason);
            userInfoDao.emptyUserInfoRedisCache(userId);
            //????????????
            tokenService.emptyToken(String.valueOf(userId));

            UserStopReasonENT stopReason = new UserStopReasonENT();
            stopReason.setOperateTime(new Date());
            stopReason.setReason(reason);
            stopReason.setType(status);
            stopReason.setUserId(userId);
            stopReason.setOperator(AssertionHolder.getAssertion().getPrincipal().getName());
            userStopReasonDao.saveUserStopReason(stopReason);

        } else {
            userInfoDao.updateUserInfoStatus(userId, status, reason);
            userInfoDao.emptyUserInfoRedisCache(userId);

            UserStopReasonENT stopReason = new UserStopReasonENT();
            stopReason.setOperateTime(new Date());
            stopReason.setReason(reason);
            stopReason.setType(status);
            stopReason.setUserId(userId);
            stopReason.setOperator(AssertionHolder.getAssertion().getPrincipal().getName());
            userStopReasonDao.saveUserStopReason(stopReason);
        }
        return em;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/13 16:57
     * @Description 102 ????????????????????????????????????
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result bindUserRealityNameAndCertCard(String userId, String realityName, String certCard, String traceId, ClientRequestHeader header) {
        Result result = new Result();
        UserBaseInfoENT userBaseInfoENT = userInfoService.getCurrentUserBaseInfo(userId);
        if (userBaseInfoENT == null) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????");
            return result;
        }
        if (StringUtils.isNotBlank(userBaseInfoENT.getRealName()) && StringUtils.isNotBlank(userBaseInfoENT.getIdcardNumber())) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????????????????");
            return result;
        }
        Boolean idcardValidate = RegexUtils.isIDCard15(certCard) || RegexUtils.isIDCard18(certCard.toUpperCase());
        if (StringUtils.isBlank(realityName) || StringUtils.isBlank(certCard) || !idcardValidate) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????????????????????????????");
            logger.warn("????????????,???????????????????????? userId:{} realName:{} certCard:{} idCardValidate:{},traceId:{}", userId, realityName, certCard, idcardValidate, traceId);
            return result;
        }
        String birthDay = certCard.substring(6, 14);
        Date birthDate = DateUtil.parseSampleDate(birthDay);
        Calendar c = Calendar.getInstance();
        c.setTime(birthDate);
        c.add(Calendar.YEAR, 18);
        if (c.getTime().after(new Date())) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????,????????????????????????18?????????????????????");
            return result;
        }
        if (userBaseInfoDao.updateIdcardnoAndRealName(Long.valueOf(userId), certCard, realityName)) {
            UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId);
            userBaseInfoENT.setRealName(realityName);
            userBaseInfoENT.setIdcardNumber(certCard);
            shardedRedisService.delUserInfo(userInfoENT);
            //publicCommonActiveMqUtil.reportBindIDCard(userId, traceId, header.getPlatformCode(), header.getCmdName());
            result.setCode(Result.SUCCESS);
            result.setData("????????????");
            result.setMsg("????????????");
            return result;
        } else {
            logger.error("???????????????????????????????????? userId:{},certCard:{},realName:{},traceId:{}", userId, certCard, realityName, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("????????????");
            return result;
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/13 16:57
     * @Description 102 ??????????????????????????????????????????
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result bindUserRealityNameAndCertCardForAdmin(String userId, String realityName, String certCard, String traceId) {
        Result result = new Result();
        UserBaseInfoENT userBaseInfoENT = userInfoService.getCurrentUserBaseInfo(userId);
        if (userBaseInfoENT == null) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????");
            return result;
        }
        if (StringUtils.isNotBlank(userBaseInfoENT.getRealName()) && StringUtils.isNotBlank(userBaseInfoENT.getIdcardNumber())) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????????????????");
            return result;
        }
        Boolean idcardValidate = RegexUtils.isIDCard15(certCard) || RegexUtils.isIDCard18(certCard.toUpperCase());
        if (StringUtils.isBlank(realityName) || StringUtils.isBlank(certCard) || !idcardValidate) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????????????????????????????!");
            logger.warn("????????????,???????????????????????? userId:{} realName:{} certCard:{} idCardValidate:{},traceId:{}", userId, realityName, certCard, idcardValidate, traceId);
            return result;
        }
        String birthDay = certCard.substring(6, 14);
        Date birthDate = DateUtil.parseSampleDate(birthDay);
        Calendar c = Calendar.getInstance();
        c.setTime(birthDate);
        c.add(Calendar.YEAR, 18);
        if (c.getTime().after(new Date())) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????18?????????????????????");
            return result;
        }
        if (userBaseInfoDao.updateIdcardnoAndRealName(Long.valueOf(userId), certCard, realityName)) {
            UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId);
            userBaseInfoENT.setRealName(realityName);
            userBaseInfoENT.setIdcardNumber(certCard);
            shardedRedisService.delUserInfo(userInfoENT);
            result.setCode(Result.SUCCESS);
            result.setData("????????????");
            result.setMsg("????????????");
            return result;
        } else {
            logger.error("???????????????????????????????????? userId:{},certCard:{},realName:{},traceId:{}", userId, certCard, realityName, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("????????????");
            return result;
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/14 16:39
     * @Description ????????????
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result editUserNickName(String userId, String nickName, String traceId) {
        Result result = new Result();
        UserBaseInfoENT userBaseInfoENT = userInfoService.getCurrentUserBaseInfo(userId);
        if (StringUtils.isNotBlank(userBaseInfoENT.getNickName()) && !("jdd" + userId).equals(userBaseInfoENT.getNickName())) {
            result.setCode(Result.ERROR);
            result.setMsg("????????????????????????");
            return result;
        }
        if (userBaseInfoDao.nickNameAlreadyExists(nickName)) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????");
            return result;
        }
        if (userBaseInfoDao.editUserNickName(userId, nickName)) {
            userInfoService.refreshUserBaseInfo(userId);
            result.setCode(Result.SUCCESS);
            result.setMsg("??????????????????");
            return result;
        } else {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????");
            return result;
        }
    }

    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public void updateUserBaseInfo(UserBaseInfoENT userBaseInfo) {
        userBaseInfoDao.updateUserBaseInfo(userBaseInfo);
        userInfoDao.emptyUserInfoRedisCache(userBaseInfo.getUserId());
    }

    /**
     * @Author shenwei
     * @Date 2017/1/11 14:16
     * @Description ????????????????????????
     */
    @Override
    public List<UserBaseInfoForApiDTO> listUserBaseInfo(String traceId, Long[] userIds, boolean isGetUserLevel, boolean isGetUserInteral, boolean isGetUserSignInfo) {
        List<UserBaseInfoForApiDTO> list = new ArrayList<UserBaseInfoForApiDTO>();
        if (userIds == null || userIds.length < 1) {
            return list;
        }
        for (Long userId : userIds) {
            String userLevel = "";
            String levelName = "";
            if (isGetUserLevel) {//????????????????????????
                String ehcacheUserLevelKey = String.format(UserEhcacheKeys.USER_LEVEL_USERID, userId);
                Object ehcacheUserLevel = ehcacheManager.get(ehcacheUserLevelKey);
                if (ehcacheUserLevel == null) {
                    userLevel = publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_LEVEL);
                } else {
                    userLevel = String.valueOf(ehcacheUserLevel);
                }
                if (StringUtils.isBlank(userLevel)) {
                    userLevel = "0";
                }
                if (StringUtils.isNotBlank(levelName)) {
                    ehcacheManager.set(ehcacheUserLevelKey, userLevel);
                }
                levelName = UserContext.USER_LEVEL_NAME_MAP.get(Integer.parseInt(userLevel));
            }
            UserBaseInfoForApiDTO item = new UserBaseInfoForApiDTO();
            item.setUserId(userId);
            item.setUserLevel(userLevel);
            item.setLevelName(levelName);
            UserBaseInfoENT userBaseInfoENT = userInfoService.getCurrentUserBaseInfo(String.valueOf(userId));
            if (userBaseInfoENT != null) {
                item.setiDCardNumber(userBaseInfoENT.getIdcardNumber() == null ? "" : userBaseInfoENT.getIdcardNumber());
                item.setNickName(userBaseInfoENT.getNickName() == null ? "" : userBaseInfoENT.getNickName());
                item.setHasNickName(StringUtils.isNotBlank(userBaseInfoENT.getNickName()) ? 1 : 0);
                item.setRealName(userBaseInfoENT.getRealName() == null ? "" : userBaseInfoENT.getRealName());
                item.setUserFace(userBaseInfoENT.getFaceUrl() == null ? "" : userBaseInfoENT.getFaceUrl());
            } else {
                item.setiDCardNumber("");
                item.setNickName("");
                item.setHasNickName(0);
                item.setRealName("");
                item.setUserFace("");
            }
            UserInfoENT currentUserInfo = userInfoService.getCurrentUserInfo(String.valueOf(userId));
            if (currentUserInfo != null) {
                item.setIsPayPwd(StringUtils.isNotBlank(currentUserInfo.getPayPassword()) ? 1 : 0);
                item.setMobile(currentUserInfo.getMobileNumber() == null ? "" : currentUserInfo.getMobileNumber());
                item.setMobileValied(StringUtils.isNotBlank(currentUserInfo.getMobileNumber()));
                item.setRegisterTime(currentUserInfo.getRegisterTime() == null ? new Date() : currentUserInfo.getRegisterTime());
                item.setUserName(currentUserInfo.getLoginName() == null ? "" : currentUserInfo.getLoginName());
                item.setThdPartId(currentUserInfo.getThdPartId() == null ? "" : currentUserInfo.getThdPartId());
                item.setThdPartName(currentUserInfo.getThdPartName() == null ? "" : currentUserInfo.getThdPartName());
                item.setThdPartType(currentUserInfo.getThdPartType() == null ? "" : currentUserInfo.getThdPartType());
                item.setPlatformCode(StringUtils.isEmpty(currentUserInfo.getPlatformCode()) ? "" : currentUserInfo.getPlatformCode());
                item.setIsLoginPwd(StringUtils.isNotBlank(currentUserInfo.getPassword()) ? 1 : 0);
            } else {
                item.setIsPayPwd(0);
                item.setMobile("");
                item.setMobileValied(false);
                item.setRegisterTime(new Date());
                item.setUserName("");
                item.setThdPartId("");
                item.setThdPartName("");
                item.setThdPartType("");
                item.setPlatformCode("");
                item.setIsLoginPwd(0);
            }
            String strBalance = "";
            if (isGetUserInteral) {//????????????????????????
                strBalance = publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_INTEGRAL_BALANCE_FIELD);
            }
            if (StringUtils.isBlank(strBalance)) {
                item.setUserExperience(0);
            } else {
                item.setUserExperience(Long.parseLong(strBalance));
            }
            Boolean isSign = false;
            UserSignInModel userSignInModel = null;
            if (isGetUserSignInfo) {//??????????????????????????????
                isSign = publicCommonRedisUtil.isInSignInTodaySet(userId);
                String key = String.format(UserRedisKeys.USER_SIGN_INFO, userId);
                String signInfoStr = userRedis.get(key);
                if (StringUtils.isBlank(signInfoStr)) {
                    userSignInModel = getUserSignInfo(userId);
                } else {
                    userSignInModel = GfJsonUtil.parseObject(signInfoStr, UserSignInModel.class);
                }
            }
            if (isSign != null && isSign.booleanValue() == true) {
                item.setIsSignToday(1);
            } else {
                item.setIsSignToday(0);
            }
            if (userSignInModel != null) {
                item.setSign(userSignInModel.getContiniousDays().longValue());
                item.setSignAward(userSignInModel.getIntegralsToAdd());
            } else {
                item.setSign(0L);
                item.setSignAward(0);
            }
            String ehcacheUserLastLoginTimeKey = String.format(UserEhcacheKeys.USER_LAST_LOGIN_TIME_USERID, userId);
            Object userLoginRecord = ehcacheManager.get(ehcacheUserLastLoginTimeKey);
            String userLoginRecordENTStr = "";
            if (userLoginRecord == null) {
                userLoginRecordENTStr = userRedis.hget(UserRedisKeys.USER_LAST_LOGIN_TIME, userId.toString());
                if (StringUtils.isNotBlank(userLoginRecordENTStr)) {
                    ehcacheManager.set(ehcacheUserLastLoginTimeKey, userLoginRecordENTStr, 3600);
                }
            } else {
                userLoginRecordENTStr = String.valueOf(userLoginRecord);
            }
            if (StringUtils.isNotBlank(userLoginRecordENTStr)) {
                UserLoginRecordENT ent = GfJsonUtil.parseObject(userLoginRecordENTStr, UserLoginRecordENT.class);
                item.setLastLoginTime(ent.getLoginTime());
            }
            list.add(item);

        }
        return list;
    }


    private UserSignInModel getUserSignInfo(Long userId) {
        Long balance = userIntegralCoreService.getUserIntegralBalance(userId);
        Long continiousDays = new Long(0);
        continiousDays = userSignInService.getContiniousSigninDays(userId, DateUtil.preDate(1));

        Integer integralToAdd = 0;
        if (continiousDays == 0) {
            integralToAdd = 10; //????????????????????????
        } else if (continiousDays == 1) {
            integralToAdd = 15;
        } else {
            integralToAdd = 30;
        }
        UserSignInModel model = new UserSignInModel();
        model.setIntegrals(balance);
        model.setIntegralsToAdd(integralToAdd);
        model.setContiniousDays(Integer.parseInt(continiousDays.toString()));
        return model;
    }


    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public void getUserStopReasonList(ExtModel em, JSONObject jsonObject) {
        em.setData(userStopReasonDao.listUserStopReason(jsonObject.getInteger("page"), jsonObject.getInteger("limit"), jsonObject.getJSONObject("data")));
        em.setTotal(userStopReasonDao.listUserStopReasonCount(jsonObject));
    }

    /**
     * ??????????????????
     * ????????????net redis
     *
     * @param userId
     * @return
     */
    @Override
    public String resetUserPasswd(Long userId) {
        //???????????????
        String randowmPasswd = MobileUtil.getRandomPass();
        String md5RandowmPasswd = MD5Utils.encrypt(randowmPasswd, UserContext.MD5Key);
        userInfoDao.resetUserPasswd(userId, md5RandowmPasswd);

        //????????????redis + net Redis
        UserInfoENT currentUserInfo = userInfoService.getCurrentUserInfo(String.valueOf(userId));
        UserBaseInfoENT currentUserBaseInfo = userInfoService.getCurrentUserBaseInfo(String.valueOf(userId));
        currentUserInfo.setPassword(md5RandowmPasswd);
        userInfoService.updateUserRedisByAdmin(currentUserBaseInfo, currentUserInfo);
        return randowmPasswd;
    }

    /**
     * ????????????????????????
     * ????????????net redis
     *
     * @param userId
     */
    @Override
    public void resetUserPayPasswd(Long userId) {
        //???????????????
        userInfoDao.resetUserPayPasswd(userId);

        //????????????redis +  net Redis
        UserInfoENT currentUserInfo = userInfoService.getCurrentUserInfo(String.valueOf(userId));
        currentUserInfo.setPayPassword("");
        UserBaseInfoENT currentUserBaseInfo = userInfoService.getCurrentUserBaseInfo(String.valueOf(userId));
        userInfoService.updateUserRedisByAdmin(currentUserBaseInfo, currentUserInfo);
    }

    @Override
    public void resetUserNickNameAndHeadIcon(Long userId) {
        // ???????????????
        userBaseInfoDao.resetUserNickNameAndHeadIcon(userId);
        // ????????????redis??????????????????.net??????
        userInfoService.updateUserRedisByAdmin(userInfoService.refreshUserBaseInfo(String.valueOf(userId)),
                userInfoService.getCurrentUserInfo(String.valueOf(userId)));
    }

    @Transactional(readOnly = true)
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public UserBaseInfoDTO getUserBaseInfoById(Long userId) {
        UserBaseInfoDTO userBaseInfo = userBaseInfoDao.getUserBaseInfoById(userId);
        UserLevelAndIntegralDTO userLevelAndIntegralDTO = webUserLevelGrowUpDao.getUserLevelByUserId(userId);
        if (userLevelAndIntegralDTO != null) {
            userBaseInfo.setUserGrowUps(userLevelAndIntegralDTO.getUserGrowUps());
            userBaseInfo.setUserLevel(userLevelAndIntegralDTO.getUserLevel());
            userBaseInfo.setUserIntegral(userLevelAndIntegralDTO.getUserIntegral());
        }
        //???????????????????????????????????????
        String strBeat = publicCommonRedisUtil.getUserInfoExtension(userBaseInfo.getUserId(), UserInfoExtensionFields.USER_BEAT_FIELD);
        if (com.jdd.fm.core.utils.StringUtils.isNotBlank(strBeat)) {
            userBaseInfo.setBeatInfo(Double.parseDouble(strBeat));
        } else {
            //????????????????????????????????????????????????
            userBaseInfo.setBeatInfo(UserContext.USER_BEATINFO_MIN);
        }
        //??????????????????
        if (publicCommonRedisUtil.isInSignInTodaySet(userBaseInfo.getUserId())) {
            userBaseInfo.setIsSignInToday(1);
        } else {
            userBaseInfo.setIsSignInToday(0);
        }

        return userBaseInfo;
    }

    @Transactional(readOnly = true)
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public UserInfoENT getUserBaseInfoByName(String traceId, String userName) {
        return userInfoService.getUserInfoByLoginName(userName);
    }

    /**
     * @Author shenwei
     * @Date 2017/5/10 14:43
     * @Description 107????????????
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public Result getBasicUserInfo(String traceId, ClientRequestHeader header, String userID, String params) {
        Result result = new Result();
        try {
            String isGetUserLevel = "1", isGetUserIntegral = "1", isGetUserSignInfo = "0";
            Long[] userIds = new Long[]{Long.parseLong(userID)};
            List<UserBaseInfoForApiDTO> userBaseInfoList = listUserBaseInfo(traceId, userIds,
                    "1".equals(isGetUserLevel) ? true : false,
                    "1".equals(isGetUserIntegral) ? true : false,
                    "1".equals(isGetUserSignInfo) ? true : false);
            if (CollectionUtils.isEmpty(userBaseInfoList)) {
                result.setCode(Result.ERROR);
                result.setMsg("??????????????????????????????");
                logger.error("[????????????]??????????????????????????????,userId:{} traceId:{}", userID, traceId);
                return result;
            }
            UserBaseInfoForApiDTO userBaseInfo = userBaseInfoList.get(0);
            UserCenterDTO userCenter = new UserCenterDTO();
            //????????????
            userCenter.setUserId(userBaseInfo.getUserId());
            userCenter.setUserName(userBaseInfo.getUserName());
            userCenter.setRealName(userBaseInfo.getRealName());
            //?????????510 ????????????ios??????????????????
            userCenter.setNickName(userBaseInfo.getNickName());
            if (Optional.ofNullable(header.getPlatformCode()).orElse("").toLowerCase().equals("iphone")) {
                if (Integer.valueOf(Optional.ofNullable(header.getAppVersion()).orElse("0").replace(".", "")) <= 510) {
                    if (Optional.ofNullable(userBaseInfo.getNickName()).orElse("").equals("jdd" + userID)) {
                        userCenter.setNickName("");
                    }
                }
            }
            //??????389 ????????????????????????
            if (Optional.ofNullable(header.getPlatformCode()).orElse("").toLowerCase().equals("android")) {
                if (Integer.valueOf(Optional.ofNullable(header.getAppVersion()).orElse("0").replace(".", "")) <= 389) {
                    if (Optional.ofNullable(userBaseInfo.getNickName()).orElse("").equals("jdd" + userID)) {
                        userCenter.setNickName("");
                    }
                }
            }
            //??????????????????????????????????????????
            if (StringUtils.isBlank(userBaseInfo.getNickName()) || userBaseInfo.getNickName().equals("jdd" + userID)) {
                userCenter.setHasNickName(0);
            } else {
                userCenter.setHasNickName(1);
            }
            userCenter.setUserFace(userBaseInfo.getUserFace());
            userCenter.setIdCardNumber(userBaseInfo.getiDCardNumber());
            userCenter.setMobile(getHideMobile(userBaseInfo.getMobile()));
            userCenter.setMobileValidated(userBaseInfo.isMobileValied());
            userCenter.setHasPayPwd(userBaseInfo.getIsPayPwd());
            userCenter.setRegisterTime(DateUtil.sampleTimeFormat(userBaseInfo.getRegisterTime()));
            userCenter.setLevel(1);
            userCenter.setSecurityQuestion("");
            userCenter.setHasLoginPwd(userBaseInfo.getIsLoginPwd());
            Integer phoneBindWx = -1;  //??????????????????????????????-1:????????????(????????????), 0??????????????????????????????1???????????????????????????
            String bindWxName = ""; //???????????????
            if (StringUtils.isNotBlank(userBaseInfo.getMobile())) {
                List<UserInfoENT> infos = userInfoService.getUserInfoByMobile(userBaseInfo.getMobile());
                //???????????????
                if (CollectionUtils.isNotEmpty(infos) && infos.size() == 1) {
                    phoneBindWx = 0;
                    if (StringUtils.isNotBlank(userBaseInfo.getThdPartId()) && "WXDL".equals(userBaseInfo.getThdPartType())) {
                        phoneBindWx = 1;
                        bindWxName = Optional.ofNullable(userBaseInfo.getThdPartName()).orElse("");
                    }
                }
            }
            userCenter.setPhoneBindWx(phoneBindWx);
            userCenter.setBindWxName(bindWxName);
            //????????????
            try {
                UserBalanceDTO userBalanceDTO = tradeOutService.getUserBalanceDTO(traceId, Long.valueOf(userID));
                if (userBalanceDTO != null) {
                    userCenter.setBalance(userBalanceDTO.getBalance());
                    userCenter.setDistillBalance(userBalanceDTO.getDistillBalance());
                    userCenter.setNoDistillBalance(userBalanceDTO.getNoDistillBalance());
                } else {
                    userCenter.setBalance(new BigDecimal(0));
                    userCenter.setDistillBalance(new BigDecimal(0));
                    userCenter.setNoDistillBalance(new BigDecimal(0));
                }
            } catch (Exception e) {
                logger.error("[????????????]????????????????????????,userId:{} traceId:{} stackTrace:{}", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
                userCenter.setBalance(new BigDecimal(-1));
                userCenter.setDistillBalance(new BigDecimal(-1));
                userCenter.setNoDistillBalance(new BigDecimal(-1));
            }
            //??????????????? status -10:????????????1:????????? 0???????????? -1???????????? -2:????????????
            Integer status = -10, cityId = 0, bankId = 0, bankTypeId = 0;
            String bankName = "", bankCardNumber = "";
            try {
                List<UserBankCardDTO> bankCards = tradeOutService.getUserBankCardList(traceId, Long.valueOf(userID));
                if (CollectionUtils.isNotEmpty(bankCards)) {
                    Optional<UserBankCardDTO> userBankOptional = Optional.empty();
                    if (bankCards.stream().filter(x -> x.getDefaultStatus() == 1).count() > 0) {
                        userBankOptional = bankCards.stream().filter(x -> x.getDefaultStatus() == 1).findFirst();
                    } else if (bankCards.stream().filter(x -> x.getStatus() == 1).count() > 0) {
                        userBankOptional = bankCards.stream().filter(x -> x.getStatus() == 1).findFirst();
                    } else if (bankCards.stream().filter(x -> x.getStatus() == -1).count() > 0) {
                        userBankOptional = bankCards.stream().filter(x -> x.getStatus() == -1).findFirst();
                    } else if (bankCards.stream().filter(x -> x.getStatus() == -2).count() > 0) {
                        userBankOptional = bankCards.stream().filter(x -> x.getStatus() == -2).findFirst();
                    } else {
                        userBankOptional = bankCards.stream().findFirst();
                    }
                    if (userBankOptional.isPresent()) {
                        bankTypeId = Optional.ofNullable(userBankOptional.get().getBankId()).orElse(0);
                        bankId = Integer.valueOf(Optional.ofNullable(userBankOptional.get().getId()).orElse(new Long(0)).toString());
                        bankCardNumber = Optional.ofNullable(userBankOptional.get().getCardNumber()).orElse("");
                        if (bankCardNumber.length() > 3) {
                            bankCardNumber = bankCardNumber.substring(0, 3) + "***" + bankCardNumber.substring(bankCardNumber.length() - 3, bankCardNumber.length());
                        }
                        status = Optional.ofNullable(userBankOptional.get().getStatus()).orElse(-10);
                        bankName = Optional.ofNullable(userBankOptional.get().getBankName()).orElse("");
                        cityId = Optional.ofNullable(userBankOptional.get().getCardCityId()).orElse(0);
                    }
                }
            } catch (Exception e) {
                logger.error("[????????????]???????????????????????????????????????,userId:{} traceId:{} stackTrace:{}", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
            }
            userCenter.setCityId(cityId);
            userCenter.setStatus(status);
            userCenter.setBankId(bankId);
            userCenter.setBankTypeId(bankTypeId);
            userCenter.setBankName(bankName);
            userCenter.setBankCardNumber(bankCardNumber);
            //??????????????????
            /*try {
                userCenter.setUsableCount(redpacketHandselService.getUserHandselCardCount(Long.valueOf(userID)));
            } catch (Exception e) {
                logger.error("[????????????]????????????????????????????????????,userId:{} traceId:{} stackTrace:{}", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
                userCenter.setUsableCount(new Long(0));
            }*/
            //??????????????????
            userCenter.setUserIntegral(userBaseInfo.getUserExperience());
            userCenter.setUserLevel(userBaseInfo.getUserLevel());
            userCenter.setLevelName(userBaseInfo.getLevelName());
            userCenter.setLastLoginTime(userBaseInfo.getLastLoginTime());


            result.setCode(Result.SUCCESS);
            result.setData(userCenter);
            return result;
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("[????????????]?????????????????????????????? userId:{} traceId:{}", userID, traceId);
        }
        return result;
    }

    @Override
    public void batchFobiddenLogin(List<Long> userIds, Integer status) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            Date now = new Date();
            for (Long userId : userIds) {
                userInfoDao.updateUserInfoStatus(userId, status, status.equals(0) ? "??????????????????" : "??????????????????");
                UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId.toString());
                shardedRedisService.delUserInfo(userInfoENT);
                UserStopReasonENT userStopReason = new UserStopReasonENT();
                userStopReason.setUserId(userId);
                userStopReason.setOperateTime(now);
                userStopReason.setReason(status.equals(0) ? "??????????????????" : "??????????????????");
                userStopReason.setType(status);
                userStopReasonDao.saveUserStopReason(userStopReason);
                tokenService.emptyToken(String.valueOf(userId));
            }
        }
    }

    @Override
    public void batchFobiddenLogin(List<Long> userIds, Integer status, String reason) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            Date now = new Date();
            for (Long userId : userIds) {
                userInfoDao.updateUserInfoStatus(userId, status, reason);
                UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId.toString());
                shardedRedisService.delUserInfo(userInfoENT);
                UserStopReasonENT userStopReason = new UserStopReasonENT();
                userStopReason.setUserId(userId);
                userStopReason.setOperateTime(now);
                userStopReason.setReason(reason);
                userStopReason.setType(status);
                userStopReasonDao.saveUserStopReason(userStopReason);
                tokenService.emptyToken(String.valueOf(userId));
            }
        }
    }

    @Override
    public void batchFobiddenLoginByMobile(List<String> mobiles, String reason) {
        if (CollectionUtils.isNotEmpty(mobiles)) {
            Date now = new Date();
            for (String mobile : mobiles) {
                List<UserInfoENT> userInfoENTs = userInfoService.getUserInfoByMobile(mobile.toString());
                if (CollectionUtils.isNotEmpty(userInfoENTs)) {
                    UserInfoENT userInfoENT = userInfoENTs.get(0);
                    userInfoDao.updateUserInfoStatus(userInfoENT.getUserId(), 0, "??????????????????");
                    shardedRedisService.delUserInfo(userInfoENT);
                    UserStopReasonENT userStopReason = new UserStopReasonENT();
                    userStopReason.setUserId(userInfoENT.getUserId());
                    userStopReason.setOperateTime(now);
                    userStopReason.setReason(reason);
                    userStopReason.setType(0);
                    userStopReasonDao.saveUserStopReason(userStopReason);
                    tokenService.emptyToken(String.valueOf(userInfoENT.getUserId()));
                }
            }
        }
    }

    @Override
    public void batchFobiddenLoginByUserId(List<Long> userIds, String reason) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            Date now = new Date();
            for (Long userId : userIds) {
                UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId.toString());
                if (null != userInfoENT) {
                    userInfoDao.updateUserInfoStatus(userInfoENT.getUserId(), 0, "??????????????????");
                    shardedRedisService.delUserInfo(userInfoENT);
                    UserStopReasonENT userStopReason = new UserStopReasonENT();
                    userStopReason.setUserId(userInfoENT.getUserId());
                    userStopReason.setOperateTime(now);
                    userStopReason.setReason(reason);
                    userStopReason.setType(0);
                    userStopReasonDao.saveUserStopReason(userStopReason);
                    tokenService.emptyToken(String.valueOf(userId));
                }
            }
        }
    }

    @Override
    public Result userRedisExamine(String traceId, String userID, String loginName, String type, String operate) {
        Result result = new Result();
        StringBuilder sb = new StringBuilder();
        if (operate.equals("get")) {
            switch (type) {
                case "1"://????????????ID??????????????????
                    sb.append("{\"USER_INFO_BY_USERID\":" + GfJsonUtil.toJSONString(userInfoService.getCurrentUserInfo(userID)) + "}");
                    break;
                case "2"://?????????????????????????????????
                    sb.append("{\"USER_INFO_BY_LOGINNAME\":" + GfJsonUtil.toJSONString(userInfoService.getUserInfoByLoginName(loginName)) + "}");
                    break;
                case "3"://?????????????????????????????????list
                    sb.append("{\"USER_INFO_BY_MOBILE\":" + GfJsonUtil.toJSONString(userInfoService.getUserInfoByMobile(loginName)) + "}");
                    break;
                case "4"://?????????????????????????????????redis????????????
                    UserInfoENT userInfo = userInfoService.getUserInfoByLoginName(loginName);
                    Long userIdLong = Long.valueOf(0);
                    if (userInfo == null) {
                        sb.append("{\"RET\":\"NO DATA\"}");
                    } else {
                        userIdLong = userInfo.getUserId();
                        sb.append("{\"USER_INFO_BY_USERID\":" + GfJsonUtil.toJSONString(userInfoService.getCurrentUserInfo(userIdLong.toString())));
                        sb.append(",\"USER_INFO_BY_LOGINNAME\":" + GfJsonUtil.toJSONString(userInfoService.getUserInfoByLoginName(loginName)));
                        sb.append(",\"USER_INFO_BY_MOBILE\":" + GfJsonUtil.toJSONString(userInfoService.getUserInfoByMobile(loginName)) + "}");
                    }
                    break;
                default:
                    ;
                    break;
            }
        }
        if (operate.equals("remove")) {
            switch (type) {
                case "1"://????????????ID??????????????????
                    userRedis.del(String.format(UserRedisKeys.USER_INFO_KEY_ID, userID));
                    break;
                case "2"://?????????????????????????????????
                    userRedis.del(String.format(UserRedisKeys.USER_INFO_NAME_KEY_NAME, loginName));
                    break;
                case "3"://?????????????????????????????????list
                    userRedis.del(String.format(UserRedisKeys.USER_INFO_NAME_KEY_NAME, loginName));
                    break;
                case "4"://????????????ID??????????????????????????????redis????????????
                    UserInfoENT userInfo = userInfoService.getUserInfoByLoginName(loginName);
                    Long userIdLong = Long.valueOf(0);
                    if (userInfo == null) {
                        sb.append("{\"RET\":\"NO DATA\"}");
                    } else {
                        userRedis.del(String.format(UserRedisKeys.USER_INFO_KEY_ID, userIdLong.toString()));
                        userRedis.del(String.format(UserRedisKeys.USER_INFO_NAME_KEY_NAME, loginName));
                    }
                    break;
                default:
                    ;
                    break;
            }
        }
        result.setData(sb.toString());
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void resetUserFace(Long userId) {
        // ???????????????
        userBaseInfoDao.resetUserFace(userId);
        // ????????????redis??????????????????.net??????
        userInfoService.updateUserRedisByAdmin(userInfoService.refreshUserBaseInfo(String.valueOf(userId)),
                userInfoService.getCurrentUserInfo(String.valueOf(userId)));
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void resetUserNickName(Long userId) {
        // ???????????????
        userBaseInfoDao.resetNickName(userId);
        // ????????????redis??????????????????.net??????
        userInfoService.updateUserRedisByAdmin(userInfoService.refreshUserBaseInfo(String.valueOf(userId)),
                userInfoService.getCurrentUserInfo(String.valueOf(userId)));
    }


    private String getHideMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }
        return mobile.length() < 3 ? "" : mobile.substring(0, 3) + "***" + mobile.substring(mobile.length() - 3, mobile.length());
    }
}
