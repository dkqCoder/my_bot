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
                userBaseInfoDTO.setMobileNumber("已绑定");
            } else {
                userBaseInfoDTO.setMobileNumber("未绑定");
            }

            if (StringUtils.isNotBlank(userBaseInfoDTO.getIdcardNumber())) {
                userBaseInfoDTO.setIdcardNumber("已绑定");
            } else {
                userBaseInfoDTO.setIdcardNumber("未绑定");
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
     * 管理员更新用户信息 realName, mobileName, idcardNumber
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
        //更新数据库
        userBaseInfoDao.updateUserBaseInfoByAdmin(userId, realName, idcardNumber);
        userInfoDao.updateUserInfoByAdmin(userId, mobileNumber);

        //更新缓存 同步更新net redis缓存
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
        //用户禁用
        if (status == UserContext.USER_STATUS_STOP) {
            userInfoDao.updateUserInfoStatus(userId, status, reason);
            userInfoDao.emptyUserInfoRedisCache(userId);
            //强制下线
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
     * @Description 102 绑定用户真实姓名和身份证
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result bindUserRealityNameAndCertCard(String userId, String realityName, String certCard, String traceId, ClientRequestHeader header) {
        Result result = new Result();
        UserBaseInfoENT userBaseInfoENT = userInfoService.getCurrentUserBaseInfo(userId);
        if (userBaseInfoENT == null) {
            result.setCode(Result.ERROR);
            result.setMsg("用户不存在");
            return result;
        }
        if (StringUtils.isNotBlank(userBaseInfoENT.getRealName()) && StringUtils.isNotBlank(userBaseInfoENT.getIdcardNumber())) {
            result.setCode(Result.ERROR);
            result.setMsg("已经绑定过真实姓名、身份证");
            return result;
        }
        Boolean idcardValidate = RegexUtils.isIDCard15(certCard) || RegexUtils.isIDCard18(certCard.toUpperCase());
        if (StringUtils.isBlank(realityName) || StringUtils.isBlank(certCard) || !idcardValidate) {
            result.setCode(Result.ERROR);
            result.setMsg("姓名与身份证不匹配，请重新输入");
            logger.warn("真实姓名,身份证号码不正确 userId:{} realName:{} certCard:{} idCardValidate:{},traceId:{}", userId, realityName, certCard, idcardValidate, traceId);
            return result;
        }
        String birthDay = certCard.substring(6, 14);
        Date birthDate = DateUtil.parseSampleDate(birthDay);
        Calendar c = Calendar.getInstance();
        c.setTime(birthDate);
        c.add(Calendar.YEAR, 18);
        if (c.getTime().after(new Date())) {
            result.setCode(Result.ERROR);
            result.setMsg("很抱歉,根据法律规定未满18岁禁止购买彩票");
            return result;
        }
        if (userBaseInfoDao.updateIdcardnoAndRealName(Long.valueOf(userId), certCard, realityName)) {
            UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId);
            userBaseInfoENT.setRealName(realityName);
            userBaseInfoENT.setIdcardNumber(certCard);
            shardedRedisService.delUserInfo(userInfoENT);
            //publicCommonActiveMqUtil.reportBindIDCard(userId, traceId, header.getPlatformCode(), header.getCmdName());
            result.setCode(Result.SUCCESS);
            result.setData("操作成功");
            result.setMsg("操作成功");
            return result;
        } else {
            logger.error("绑定真实姓名和身份证失败 userId:{},certCard:{},realName:{},traceId:{}", userId, certCard, realityName, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("绑定失败");
            return result;
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/13 16:57
     * @Description 102 后台绑定用户真实姓名和身份证
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result bindUserRealityNameAndCertCardForAdmin(String userId, String realityName, String certCard, String traceId) {
        Result result = new Result();
        UserBaseInfoENT userBaseInfoENT = userInfoService.getCurrentUserBaseInfo(userId);
        if (userBaseInfoENT == null) {
            result.setCode(Result.ERROR);
            result.setMsg("用户不存在");
            return result;
        }
        if (StringUtils.isNotBlank(userBaseInfoENT.getRealName()) && StringUtils.isNotBlank(userBaseInfoENT.getIdcardNumber())) {
            result.setCode(Result.ERROR);
            result.setMsg("已经绑定过真实姓名、身份证");
            return result;
        }
        Boolean idcardValidate = RegexUtils.isIDCard15(certCard) || RegexUtils.isIDCard18(certCard.toUpperCase());
        if (StringUtils.isBlank(realityName) || StringUtils.isBlank(certCard) || !idcardValidate) {
            result.setCode(Result.ERROR);
            result.setMsg("输入真实姓名、身份证号码不正确!");
            logger.warn("真实姓名,身份证号码不正确 userId:{} realName:{} certCard:{} idCardValidate:{},traceId:{}", userId, realityName, certCard, idcardValidate, traceId);
            return result;
        }
        String birthDay = certCard.substring(6, 14);
        Date birthDate = DateUtil.parseSampleDate(birthDay);
        Calendar c = Calendar.getInstance();
        c.setTime(birthDate);
        c.add(Calendar.YEAR, 18);
        if (c.getTime().after(new Date())) {
            result.setCode(Result.ERROR);
            result.setMsg("很抱歉，未满18岁禁止购买彩票");
            return result;
        }
        if (userBaseInfoDao.updateIdcardnoAndRealName(Long.valueOf(userId), certCard, realityName)) {
            UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId);
            userBaseInfoENT.setRealName(realityName);
            userBaseInfoENT.setIdcardNumber(certCard);
            shardedRedisService.delUserInfo(userInfoENT);
            result.setCode(Result.SUCCESS);
            result.setData("操作成功");
            result.setMsg("操作成功");
            return result;
        } else {
            logger.error("绑定真实姓名和身份证失败 userId:{},certCard:{},realName:{},traceId:{}", userId, certCard, realityName, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("绑定失败");
            return result;
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/14 16:39
     * @Description 修改昵称
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result editUserNickName(String userId, String nickName, String traceId) {
        Result result = new Result();
        UserBaseInfoENT userBaseInfoENT = userInfoService.getCurrentUserBaseInfo(userId);
        if (StringUtils.isNotBlank(userBaseInfoENT.getNickName()) && !("jdd" + userId).equals(userBaseInfoENT.getNickName())) {
            result.setCode(Result.ERROR);
            result.setMsg("昵称只能修改一次");
            return result;
        }
        if (userBaseInfoDao.nickNameAlreadyExists(nickName)) {
            result.setCode(Result.ERROR);
            result.setMsg("当前昵称已存在");
            return result;
        }
        if (userBaseInfoDao.editUserNickName(userId, nickName)) {
            userInfoService.refreshUserBaseInfo(userId);
            result.setCode(Result.SUCCESS);
            result.setMsg("昵称修改成功");
            return result;
        } else {
            result.setCode(Result.ERROR);
            result.setMsg("昵称修改失败");
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
     * @Description 批量获取用户等级
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
            if (isGetUserLevel) {//是否获取用户等级
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
            if (isGetUserInteral) {//是否获取用户积分
                strBalance = publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_INTEGRAL_BALANCE_FIELD);
            }
            if (StringUtils.isBlank(strBalance)) {
                item.setUserExperience(0);
            } else {
                item.setUserExperience(Long.parseLong(strBalance));
            }
            Boolean isSign = false;
            UserSignInModel userSignInModel = null;
            if (isGetUserSignInfo) {//是否获取用户签到信息
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
            integralToAdd = 10; //明日再签增加积分
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
     * 重置用户密码
     * 同步操作net redis
     *
     * @param userId
     * @return
     */
    @Override
    public String resetUserPasswd(Long userId) {
        //更新数据库
        String randowmPasswd = MobileUtil.getRandomPass();
        String md5RandowmPasswd = MD5Utils.encrypt(randowmPasswd, UserContext.MD5Key);
        userInfoDao.resetUserPasswd(userId, md5RandowmPasswd);

        //同步更新redis + net Redis
        UserInfoENT currentUserInfo = userInfoService.getCurrentUserInfo(String.valueOf(userId));
        UserBaseInfoENT currentUserBaseInfo = userInfoService.getCurrentUserBaseInfo(String.valueOf(userId));
        currentUserInfo.setPassword(md5RandowmPasswd);
        userInfoService.updateUserRedisByAdmin(currentUserBaseInfo, currentUserInfo);
        return randowmPasswd;
    }

    /**
     * 清空用户支付密码
     * 同步操作net redis
     *
     * @param userId
     */
    @Override
    public void resetUserPayPasswd(Long userId) {
        //更新数据库
        userInfoDao.resetUserPayPasswd(userId);

        //同步更新redis +  net Redis
        UserInfoENT currentUserInfo = userInfoService.getCurrentUserInfo(String.valueOf(userId));
        currentUserInfo.setPayPassword("");
        UserBaseInfoENT currentUserBaseInfo = userInfoService.getCurrentUserBaseInfo(String.valueOf(userId));
        userInfoService.updateUserRedisByAdmin(currentUserBaseInfo, currentUserInfo);
    }

    @Override
    public void resetUserNickNameAndHeadIcon(Long userId) {
        // 更新数据库
        userBaseInfoDao.resetUserNickNameAndHeadIcon(userId);
        // 同步刷新redis缓存，后更新.net缓存
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
        //从缓存读取打败多少彩友信息
        String strBeat = publicCommonRedisUtil.getUserInfoExtension(userBaseInfo.getUserId(), UserInfoExtensionFields.USER_BEAT_FIELD);
        if (com.jdd.fm.core.utils.StringUtils.isNotBlank(strBeat)) {
            userBaseInfo.setBeatInfo(Double.parseDouble(strBeat));
        } else {
            //如果获取不到，设最小值作为默认值
            userBaseInfo.setBeatInfo(UserContext.USER_BEATINFO_MIN);
        }
        //今日是否签到
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
     * @Description 107用户中心
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
                result.setMsg("获取用户基本信息失败");
                logger.error("[用户中心]获取用户基本信息失败,userId:{} traceId:{}", userID, traceId);
                return result;
            }
            UserBaseInfoForApiDTO userBaseInfo = userBaseInfoList.get(0);
            UserCenterDTO userCenter = new UserCenterDTO();
            //基础信息
            userCenter.setUserId(userBaseInfo.getUserId());
            userCenter.setUserName(userBaseInfo.getUserName());
            userCenter.setRealName(userBaseInfo.getRealName());
            //昵称为510 版本以下ios做了版本兼容
            userCenter.setNickName(userBaseInfo.getNickName());
            if (Optional.ofNullable(header.getPlatformCode()).orElse("").toLowerCase().equals("iphone")) {
                if (Integer.valueOf(Optional.ofNullable(header.getAppVersion()).orElse("0").replace(".", "")) <= 510) {
                    if (Optional.ofNullable(userBaseInfo.getNickName()).orElse("").equals("jdd" + userID)) {
                        userCenter.setNickName("");
                    }
                }
            }
            //安卓389 版本一下版本兼容
            if (Optional.ofNullable(header.getPlatformCode()).orElse("").toLowerCase().equals("android")) {
                if (Integer.valueOf(Optional.ofNullable(header.getAppVersion()).orElse("0").replace(".", "")) <= 389) {
                    if (Optional.ofNullable(userBaseInfo.getNickName()).orElse("").equals("jdd" + userID)) {
                        userCenter.setNickName("");
                    }
                }
            }
            //昵称为空或者为自动生成的昵称
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
            Integer phoneBindWx = -1;  //手机号绑定微信状态。-1:无手机号(或多账号), 0：手机号未绑定微信，1：手机号已绑定微信
            String bindWxName = ""; //绑定微信号
            if (StringUtils.isNotBlank(userBaseInfo.getMobile())) {
                List<UserInfoENT> infos = userInfoService.getUserInfoByMobile(userBaseInfo.getMobile());
                //多账号排除
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
            //用户余额
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
                logger.error("[用户中心]获取用户余额失败,userId:{} traceId:{} stackTrace:{}", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
                userCenter.setBalance(new BigDecimal(-1));
                userCenter.setDistillBalance(new BigDecimal(-1));
                userCenter.setNoDistillBalance(new BigDecimal(-1));
            }
            //银行卡相关 status -10:未绑定，1:已绑定 0：已删除 -1：审核中 -2:审核失败
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
                logger.error("[用户中心]获取用户银行卡相关信息失败,userId:{} traceId:{} stackTrace:{}", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
            }
            userCenter.setCityId(cityId);
            userCenter.setStatus(status);
            userCenter.setBankId(bankId);
            userCenter.setBankTypeId(bankTypeId);
            userCenter.setBankName(bankName);
            userCenter.setBankCardNumber(bankCardNumber);
            //用户红包数量
            /*try {
                userCenter.setUsableCount(redpacketHandselService.getUserHandselCardCount(Long.valueOf(userID)));
            } catch (Exception e) {
                logger.error("[用户中心]获取用户可用红包数量异常,userId:{} traceId:{} stackTrace:{}", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
                userCenter.setUsableCount(new Long(0));
            }*/
            //用户积分等级
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
            logger.error("[用户中心]获取用户中心信息异常 userId:{} traceId:{}", userID, traceId);
        }
        return result;
    }

    @Override
    public void batchFobiddenLogin(List<Long> userIds, Integer status) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            Date now = new Date();
            for (Long userId : userIds) {
                userInfoDao.updateUserInfoStatus(userId, status, status.equals(0) ? "批量禁止登录" : "批量解禁登录");
                UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId.toString());
                shardedRedisService.delUserInfo(userInfoENT);
                UserStopReasonENT userStopReason = new UserStopReasonENT();
                userStopReason.setUserId(userId);
                userStopReason.setOperateTime(now);
                userStopReason.setReason(status.equals(0) ? "批量禁止登录" : "批量解禁登录");
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
                    userInfoDao.updateUserInfoStatus(userInfoENT.getUserId(), 0, "批量禁止登录");
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
                    userInfoDao.updateUserInfoStatus(userInfoENT.getUserId(), 0, "批量禁止登录");
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
                case "1"://根据用户ID获取用户信息
                    sb.append("{\"USER_INFO_BY_USERID\":" + GfJsonUtil.toJSONString(userInfoService.getCurrentUserInfo(userID)) + "}");
                    break;
                case "2"://根据用户名获取用户信息
                    sb.append("{\"USER_INFO_BY_LOGINNAME\":" + GfJsonUtil.toJSONString(userInfoService.getUserInfoByLoginName(loginName)) + "}");
                    break;
                case "3"://根据手机号获取用户信息list
                    sb.append("{\"USER_INFO_BY_MOBILE\":" + GfJsonUtil.toJSONString(userInfoService.getUserInfoByMobile(loginName)) + "}");
                    break;
                case "4"://根据用户名，手机号获取redis所有信息
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
                case "1"://根据用户ID删除用户信息
                    userRedis.del(String.format(UserRedisKeys.USER_INFO_KEY_ID, userID));
                    break;
                case "2"://根据用户名删除用户信息
                    userRedis.del(String.format(UserRedisKeys.USER_INFO_NAME_KEY_NAME, loginName));
                    break;
                case "3"://根据手机号获取删除信息list
                    userRedis.del(String.format(UserRedisKeys.USER_INFO_NAME_KEY_NAME, loginName));
                    break;
                case "4"://根据用户ID，用户名，手机号删除redis所有信息
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
        // 更新数据库
        userBaseInfoDao.resetUserFace(userId);
        // 同步刷新redis缓存，后更新.net缓存
        userInfoService.updateUserRedisByAdmin(userInfoService.refreshUserBaseInfo(String.valueOf(userId)),
                userInfoService.getCurrentUserInfo(String.valueOf(userId)));
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void resetUserNickName(Long userId) {
        // 更新数据库
        userBaseInfoDao.resetNickName(userId);
        // 同步刷新redis缓存，后更新.net缓存
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
