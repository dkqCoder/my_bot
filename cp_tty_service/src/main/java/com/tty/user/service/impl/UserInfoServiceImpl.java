package com.tty.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.NetworkUtil;
import com.jdd.fm.core.utils.PropertiesUtil;
import com.jdd.fm.core.utils.hib.DateUtil;
import com.tty.common.utils.Result;
import com.tty.ots.trade.outservice.TradeOutService;
import com.tty.sms.dto.MessageDTO;
import com.tty.sms.outservice.SmsOutService;
import com.tty.user.common.EmailModel;
import com.tty.user.common.utils.MD5Utils;
import com.tty.user.common.utils.MobileUtil;
import com.tty.user.common.utils.RegexUtils;
import com.tty.user.context.LoginTypeEnum;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.context.VerifyCodeEnum;
import com.tty.user.controller.model.params.PayPassParams;
import com.tty.user.controller.model.result.UserListDTO;
import com.tty.user.dao.UserBaseInfoDao;
import com.tty.user.dao.UserIdcardResetDao;
import com.tty.user.dao.UserInfoDao;
import com.tty.user.dao.UserThirdInfoDao;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.entity.UserIdcardResetENT;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.dao.entity.UserThirdInfoENT;
import com.tty.user.dto.RegisterCountDTO;
import com.tty.user.dto.UserInfoDTO;
import com.tty.user.dto.UserRegisterQueueDTO;
import com.tty.user.service.*;
import com.tty.user.service.biz.UserFilterBiz;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserBaseInfoDao userBaseInfoDao;
    @Autowired
    private UserIdcardResetDao userIdcardResetDao;
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private VerifyCodeCheckService verifyCodeCheckService;
    @Autowired
    private ShardedRedisService shardedRedisService;
    @Autowired
    private SmsOutService smsOutService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserFilterBiz userFilterBiz;
    @Autowired
    private UserThirdInfoDao userThirdInfoDao;
    @Autowired
    private TradeOutService tradeOutService;


    /**
     * @Author shenwei
     * @Date 2017/3/7 11:11
     * @Description 100 用户注册  registerType 1 用户名密码注册 2 手机号注册  此时userName为手机号 增加SmsType 如果传3(语音），不传默认1
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result register(String userName, Integer registerType, String passWord, Integer smsType, String traceId, ClientRequestHeader header) {
        Result result = new Result();
        try {
            if (!checkNamePattern(userName)) {
                result.setCode(Result.ERROR);
                result.setMsg("用户名格式错误!");
                return result;
            }
            if (userNameExists(userName)) {
                result.setCode(Result.ERROR);
                result.setMsg(registerType == 1 ? "用户名已存在" : "手机号已注册,请直接登陆");
                return result;
            }
            if (registerType == 1) {
                //用户名密码注册
                String mobile = null;
                if (MobileUtil.isMobileNO(userName)) {
                    mobile = userName;
                }
                Long registerUserId = registerByUserNameAndPwd(userName, mobile, null, passWord, traceId, header, result);
                if (registerUserId > 0) {

                    UserInfoDTO ent = new UserInfoDTO();
                    ent.setUserId(registerUserId.toString());
                    result.setCode(Result.SUCCESS);
                    result.setData(ent);
                    result.setMsg("恭喜您注册成功!");
                    return result;
                } else {
                    result.setCode(Result.ERROR);
                    result.setMsg("注册失败");
                    return result;
                }
            }
            if (registerType == 2) {
                //手机号密码注册，获取验证码
                if (!MobileUtil.isMobileNO(userName)) {
                    result.setCode(Result.ERROR);
                    result.setMsg("请输入正确格式的手机号码");
                    return result;
                }
                if (registerByMobileAndPwd(userName, passWord, smsType, traceId)) {
                    result.setCode(Result.SUCCESS);
                    result.setMsg("验证码发送成功!");
                    return result;
                } else {
                    result.setCode(Result.ERROR);
                    result.setMsg(String.format("获取验证码发送短信失败,手机号:%s", userName));
                    return result;
                }
            }
        } catch (Exception e) {
            logger.error("用户注册失败 userName:{} registerType:{} traceId:{} stackTrace:{}", userName, registerType, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/29 11:19
     * @Description 第三方用户注册
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public void registerThird(String thirdUserId, String thirdUserName, LoginTypeEnum thirdType, String entranceCode, Result result, String traceId, ClientRequestHeader header) {
        try {
            Long userId = getNewUserId();
            //为防止重复注册,借助userThirdInfo 表的唯一索引
            UserThirdInfoENT userThirdInfoENT = new UserThirdInfoENT();
            userThirdInfoENT.setUserId(userId);
            userThirdInfoENT.setThirdId(thirdUserId);
            userThirdInfoENT.setThirdName(thirdUserName);
            userThirdInfoENT.setRegisterType(thirdType.getName());
            userThirdInfoENT.setRegisterTime(new Date());
            userThirdInfoDao.saveUserThirdInfo(userThirdInfoENT);
            UserInfoENT userInfoENT = new UserInfoENT();
            userInfoENT.setUserId(userId);
            String userName = generateUserName(String.valueOf(userId));
            userInfoENT.setLoginName(userName);
            userInfoENT.setPassword("");
            userInfoENT.setThdPartId(thirdUserId);
            userInfoENT.setThdPartName(thirdUserName);
            userInfoENT.setThdPartType(thirdType.getName());
            userInfoENT.setRegisterTime(new Date());
            userInfoENT.setEntranceCode(header.getCmdName());
            userInfoENT.setStatus(1);
            userInfoENT.setPlatformCode(header.getPlatformCode());
            userInfoENT.setPlatformVersion(header.getPlatformVersion());
            userInfoENT.setCmdId(header.getCmdID());
            userInfoENT.setAppVersion(header.getAppVersion());
            userInfoENT.setTs(header.getTs());
            userInfoENT.setPhoneName(header.getPhoneName());
            userInfoENT.setAppVersionNum(header.getChooseType());
            userInfoENT.setUuid(header.getUuid());
            userInfoENT.setIpAddress(getIPAddress());
            userInfoDao.saveUserInfo(userInfoENT);
            UserBaseInfoENT userBaseInfoENT = new UserBaseInfoENT();
            userBaseInfoENT.setUserId(userId);
            userBaseInfoENT.setName(userName);
            userBaseInfoENT.setNickName("jdd" + userId);
            userBaseInfoDao.saveUserBaseInfo(userBaseInfoENT);
            Integer loginType = 0;

            tokenService.saveToken(userName, String.valueOf(userId), null, thirdType.getValue(), header, "微信", result, loginType);
            UserRegisterQueueDTO userRegisterQueueDTO = new UserRegisterQueueDTO(userId, "", header.getPlatformCode(), header.getCmdName(), traceId);
            //publicCommonActiveMqUtil.reportedUserRegister(userRegisterQueueDTO, header);
        } catch (Exception e) {
            logger.error("第三方用户注册失败: thirdUserId:{} traceId:{}", thirdUserId, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("注册失败,请退出重试");
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/29 11:42
     * @Description 自动生成用户名
     */
    private String generateUserName(String userId) {
        return "wx" + userId;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/7 14:01
     * @Description 根据手机号密码注册获取验证码, 使用1034校验
     */
    private Boolean registerByMobileAndPwd(String mobile, String password, Integer smsType, String traceId) {
        userRedis.set(String.format(UserRedisKeys.USER_REGISTER_PWD, mobile), password);
        userRedis.expire(String.format(UserRedisKeys.USER_REGISTER_PWD, mobile), 10 * 60);
        Result result = verifyCodeService.getVerifyCode(null, mobile, VerifyCodeEnum.QUICKREGISTER, smsType, traceId, null);
        return result.getCode() >= 0;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/7 16:34
     * @Description 根据用户名密码注册
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Long registerByUserNameAndPwd(String userName, String mobile, String entrance, String password, String traceId, ClientRequestHeader header, Result result) {
        UserInfoENT userInfoENT = new UserInfoENT();
        Long userId = getNewUserId();
        userInfoENT.setUserId(userId);
        userInfoENT.setLoginName(userName);
        userInfoENT.setMobileNumber(mobile);
        userInfoENT.setRegisterTime(new Date());
        userInfoENT.setEntranceCode(header.getCmdName());
        if (StringUtils.isNotEmpty(password)) {
            userInfoENT.setPassword(MD5Utils.encrypt(password, UserContext.MD5Key));
        }
        userInfoENT.setStatus(1);
        userInfoENT.setPlatformCode(header.getPlatformCode());
        userInfoENT.setPlatformVersion(header.getPlatformVersion());
        userInfoENT.setCmdId(header.getCmdID());
        userInfoENT.setAppVersion(header.getAppVersion());
        userInfoENT.setTs(header.getTs());
        userInfoENT.setPhoneName(header.getPhoneName());
        userInfoENT.setAppVersionNum(header.getChooseType());
        userInfoENT.setUuid(header.getUuid());
        userInfoENT.setIpAddress(getIPAddress());
        userInfoDao.saveUserInfo(userInfoENT);
        UserBaseInfoENT userBaseInfoENT = new UserBaseInfoENT();
        userBaseInfoENT.setUserId(userId);
        userBaseInfoENT.setName(userName);
        userBaseInfoENT.setNickName("tty" + userId);
        userBaseInfoDao.saveUserBaseInfo(userBaseInfoENT);
        tokenService.saveToken(userName, String.valueOf(userId), mobile, LoginTypeEnum.APP.getValue(), header, "用户名", result, 2);
        // 异步防刷
        userFilterBiz.asyncFilterUserByRegister(userInfoENT);
        // 添加注册成功异步发送注册成功短信
        if (isSendSuccessSms() && !isExistedOrSetUUID(header.getUuid())) {
            asyncSendRegisterSms(StringUtils.isEmpty(mobile) ? userName : mobile, header.getPlatformCode(), traceId);
        }
        return userId;
    }

    /**
     * 判断缓存中是否存在已经发送的UUID.
     * 判断缓存中是否存在uuid,如果存在直接返回false,如果不存在插入缓存，返回true.
     *
     * @param uId uuid
     * @return true 缓存中已存在，false缓存中不存在
     */
    private boolean isExistedOrSetUUID(String uId) {
        if (!StringUtils.isEmpty(uId)) {
            String value = userRedis.hget(UserRedisKeys.USER_REGISTER_UUID_KEY, String.format(UserRedisKeys.USER_REGISTER_UUID_USER_KEY, uId));
            if (value != null && UserContext.USER_REGISTER_UUID_IS_EXISTED.equals(String.valueOf(value))) {
                logger.info("UUID为：{} ，曾经发送过注册成功短信", uId);
                return true;
            }
            userRedis.hset(UserRedisKeys.USER_REGISTER_UUID_KEY, String.format(UserRedisKeys.USER_REGISTER_UUID_USER_KEY, uId),
                    UserContext.USER_REGISTER_UUID_IS_EXISTED);
            return false;
        }
        return false;
    }

    /**
     * 获取注册成功发送短信开关
     *
     * @return true 发送；false不发送
     */
    private boolean isSendSuccessSms() {
        String value = PropertiesUtil.get(UserContext.USER_REGISTER_SEND_SMS_KEY);
        return StringUtils.isEmpty(value) || UserContext.USER_REGISTER_SEND_SMS_VALUE.equals(value);
    }

    /**
     * 异步发送注册成功短信
     * luo yinghua 2017-05-11
     *
     * @param mobile   手机号
     * @param platform 注册来源
     */
    private void asyncSendRegisterSms(final String mobile, final String platform, final String traceId) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(platform)) {
            return;
        }
        final String smsContent = getRegisterSmsContent(platform);
        if (StringUtils.isEmpty(smsContent)) {
            return;
        }
        logger.info("traceId - {} ,来自 {} 的手机号 {} 注册成功, 发送成功短信！ ", traceId, platform, mobile);
        threadPoolTaskExecutor.submit(() -> {
            MessageDTO message = new MessageDTO(mobile, smsContent, 1, "tty_user");
            if (!smsOutService.sendMsg(message)) {
                logger.error("traceId - {} ,来自 {} 的手机号 {} 注册成功, 发送成功失败！ ", traceId, platform, mobile);
            }
        });
    }

    /**
     * 生成注册成功短信内容
     * luo yinghua 2017-05-11
     *
     * @param platform 注册来源
     */
    private String getRegisterSmsContent(final String platform) {
        switch (platform) {
            case UserContext.PLATFORM_CODE_ANDROID:
            case UserContext.PLATFORM_CODE_IPHONE:
                return UserContext.USER_REGISTER_APP_SMS_CONTENT;
            case UserContext.PLATFORM_CODE_PC:
            case UserContext.PLATFORM_CODE_WAP:
                return UserContext.USER_REGISTER_PC_SMS_CONTENT;
            default:
                return "";
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/13 11:18
     * @Description bind user mobile
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result bindUserMobile(String userId,String mobile, String verifyCode, String traceId, ClientRequestHeader header) {
        Result result = new Result();
        UserInfoENT userInfoENT = getCurrentUserInfo(userId);
        if (userInfoENT == null) {
            result.setCode(Result.ERROR);
            result.setData("当前用户不存在");
            return result;
        }
        result = verifyCodeCheckService.checkBindUserMobile(verifyCode, null, userId);
        if (result.getCode() < 0) {
            return result;
        }
        List<UserInfoENT> userInfoENTS = getUserInfoByMobile(mobile);
        if (CollectionUtils.isNotEmpty(userInfoENTS)) {
            result.setCode(Result.ERROR);
            result.setMsg("您的手机号码已绑定其它用户，请您更换号码");
            return result;
        }
        try {
            Boolean success = userInfoDao.updateUserMobileNumber(userId, mobile);
            if (success) {
                shardedRedisService.delUserInfo(userInfoENT);
                userInfoENT.setMobileNumber(mobile);
                //publicCommonActiveMqUtil.reportBindMobile(Long.valueOf(userId), traceId, header.getPlatformCode(), header.getCmdName());
                result.setCode(Result.SUCCESS);
                result.setMsg("绑定手机成功");
            } else {
                result.setCode(Result.ERROR);
                result.setMsg(Result.MSG_ERROR_DESC);
                logger.warn("绑定手机失败");
            }
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("【绑定手机失败,详细信息：userId:{},traceId:{},error:{}】", userId, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }


    @Override
    public Result userLoginOut(String userId, String token, String traceId) {
        Result result = new Result();
        tokenService.emptyToken(userId);
        logger.info("用户退出登录traceId:{},userid:{},integral:{}", traceId, userId);
        result.setMsg("退出登录成功");
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/13 20:34
     * @Description 修改用户密码
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result changeUserPassword(String userId, String oldPwd, String newPwd, String traceId) {
        Result result = new Result();
        UserInfoENT userInfoENT = getCurrentUserInfo(userId);
        try {
            if (StringUtils.isEmpty(userInfoENT.getPassword())) { // 密码为空时为初使状态不校验原密码

            } else if (!userInfoENT.getPassword().equals(MD5Utils.encrypt(oldPwd, UserContext.MD5Key))) {
                result.setCode(Result.ERROR);
                result.setMsg("原密码错误,请重新输入");
                result.setData("原密码错误,请重新输入");
                return result;
            }
        } catch (Exception e) {
            logger.error("用户修改密码失败:userId:{} traceId:{},stackTrace:{}", userId, traceId, LogExceptionStackTrace.erroStackTrace(e));
            result.setCode(Result.ERROR);
            result.setMsg("操作失败!");
            result.setData("操作失败!");
            return result;
        }
        //新密码加密
        newPwd = MD5Utils.encrypt(newPwd, UserContext.MD5Key);
        if (userInfoDao.updateUserPass(userId, newPwd)) {
            shardedRedisService.delUserInfo(userInfoENT);
            result.setCode(Result.SUCCESS);
            result.setMsg("密码修改成功,将直接登录");
            result.setData("密码修改成功,将直接登录");
            return result;
        } else {
            logger.error("用户修改密码失败:userId:{} traceId:{}", userId, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("操作失败!");
            result.setData("操作失败!");
            return result;
        }
    }

    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result changeUserPayPassword(String userId, String newPwd, String verifyCode, String traceId) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(newPwd)) {
                result.setCode(Result.ERROR);
                result.setMsg("密码不能为空");
                return result;
            }
            if (StringUtils.isEmpty(verifyCode)) {
                result.setCode(Result.ERROR);
                result.setMsg("短信验证码不能为空");
                return result;
            }
            UserInfoENT userInfo = getCurrentUserInfo(userId);
            if (userInfo == null) {
                result.setCode(Result.ERROR);
                result.setMsg("未能获取到用户基本信息");
                return result;
            }
            String mobile = userInfo.getMobileNumber();
            if (StringUtils.isEmpty(mobile)) {
                result.setCode(Result.ERROR);
                result.setMsg("用户未绑定手机");
                return result;
            }
            // 验证短信验证码
            result = verifyCodeCheckService.checkForgetPayPass(verifyCode, mobile);
            if (result.getCode() < 0) {
                return result;
            }
            updateUserPayPass(userId, newPwd, result, traceId);
        } catch (Exception e) {
            logger.error("支付密码修改失败,userId:{},traceId:{},stackTrace:{}", userId, traceId, LogExceptionStackTrace.erroStackTrace(e));
            result.setCode(Result.ERROR);
            result.setMsg("支付密码修改失败");
        }
        return result;
    }

    private void updateUserPayPass(String userId, String newPwd, Result result, String traceId) {
        String encryptPass = MD5Utils.encrypt(newPwd, UserContext.MD5Key);
        if (userInfoDao.updateUserPayPass(userId, encryptPass)) {
            shardedRedisService.delUserInfo(getCurrentUserInfo(userId));
            logger.info("提现密码修改成功,userId:{},traceId:{}", userId, traceId);
            result.setCode(1);
            result.setMsg("提现密码修改成功");
        } else {
            logger.error("提现密码修改失败,userId:{},traceId:{}", userId, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("提现密码修改失败");
        }
    }

    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result editNickName(String userId, String nickname, String traceId) {
        Result result = new Result();
        result.setCode(Result.ERROR);
        UserInfoENT userInfoENT = getCurrentUserInfo(userId);
        if (userInfoENT == null) {
            result.setMsg("该用户不存在");
            return result;
        }
        if (StringUtils.isBlank(nickname)) {
            result.setMsg("昵称不能为空");
            return result;
        }
        if (nickname.length() > 5 || nickname.length() < 2 || !RegexUtils.isNickName(nickname)) {
            result.setMsg("昵称错误，昵称只能输入2-5个汉字");
            return result;
        }
        UserBaseInfoENT userBaseInfoENT = getCurrentUserBaseInfo(userId);
        if (StringUtils.isNotBlank(userBaseInfoENT.getNickName()) && !("tty" + userId).equals(userBaseInfoENT.getNickName())) {
            result.setMsg("昵称只能修改一次");
            return result;
        }
        if (userBaseInfoDao.nickNameAlreadyExists(nickname)) {
            result.setMsg("当前昵称已存在");
            return result;
        }
        if (userBaseInfoDao.editUserNickName(userId, nickname)) {
            shardedRedisService.delUserInfo(userInfoENT);
            result.setCode(Result.SUCCESS);
            result.setMsg("昵称修改成功");
        } else {
            result.setMsg(Result.MSG_ERROR_DESC);
        }
        return result;
    }


    /**
     * 管理员修改 realName mobileNumber idcardNumber userPasswd payPasswd 刷新缓存
     *
     * @param currentUserBaseInfo
     * @param currentUserInfo
     */
    @Override
    public void updateUserRedisByAdmin(UserBaseInfoENT currentUserBaseInfo, UserInfoENT currentUserInfo) {
        shardedRedisService.delUserInfo(currentUserInfo);
    }


    @Transactional(readOnly = true)
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public List<UserInfoENT> getUserInfoByMobileAndUserId(String mobileNumber, Long userId) {
        return userBaseInfoDao.getUserInfoByMobileAndUserId(mobileNumber, userId);
    }


    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result bindUserPayPassword(String traceId, String oldPw, String newPw, String userId) {
        Result result = new Result();
        UserInfoENT userInfoENT = getCurrentUserInfo(userId);
        if (userInfoENT == null) {
            result.setCode(Result.ERROR);
            result.setMsg("请登录后再试");
            return result;
        }
        //支付密码原来为空表示第一次设置支付密码
        if (StringUtils.isBlank(userInfoENT.getPayPassword())) {
            updateUserPayPass(userId, newPw, result, userId);
            return result;
        }
        if (Objects.equals(oldPw, newPw)) {
            result.setCode(Result.ERROR);
            result.setMsg("新旧密码不能一致");
            return result;
        }
        try {
            boolean freezed = tradeOutService.checkPayPassword(Long.valueOf(userId));
            if (freezed) {
                result.setCode(Result.ERROR);
                result.setMsg("密码冻结期间无法修改,请等候解冻再尝试");
                return result;
            }
        } catch (Exception e) {
            logger.error("调用提现密码是否冻结rpc失败,traceId:{},stackTrace:{}", traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        //加密后和数据库比较
        String encryptPass = MD5Utils.encrypt(oldPw, UserContext.MD5Key);
        if (!encryptPass.equals(userInfoENT.getPayPassword())) {
            result.setCode(Result.ERROR);
            result.setMsg("旧密码错误");
            return result;
        }
        updateUserPayPass(userId, newPw, result, userId);
        return result;
    }

    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result changeUserPassword(String userId, String newPwd, String traceId) {
        Result result = new Result();
        if (userInfoDao.updateUserPass(userId, MD5Utils.encrypt(newPwd, UserContext.MD5Key))) {
            userInfoDao.emptyUserInfoRedisCache(Long.valueOf(userId));
            result.setCode(Result.SUCCESS);
            result.setMsg("修改密码成功");
        } else {
            logger.error("用户修改密码失败:userId:{} traceId:{}", userId, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("操作失败!");
        }
        return result;
    }

    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result thdPartChangeUserPassword(String userId, String newPwd, String traceId) {
        Result result = new Result();
        if (userId == null) {
            result.setCode(Result.ERROR);
            result.setMsg("请登录后再设置密码");
            return result;
        }
        UserInfoENT userInfo = userInfoDao.getUserInfo(userId);
        if (userInfo == null) {
            result.setCode(Result.ERROR);
            result.setMsg("用户不存在!");
            return result;
        }
        if (StringUtils.isEmpty(userInfo.getMobileNumber())) {
            result.setCode(Result.ERROR);
            result.setMsg("手机号未绑定!");
            return result;
        }
        if (StringUtils.isEmpty(newPwd)) {
            result.setCode(Result.ERROR);
            result.setMsg("密码不能为空");
            return result;
        }
        if (newPwd.length() < 6 && newPwd.length() > 15) {
            result.setCode(Result.ERROR);
            result.setMsg("密码长度应在6至15位");
            return result;
        }
        if (Objects.equals(newPwd, "123456") || Objects.equals(newPwd, "111111") || Objects.equals(newPwd, "abcdef")) {
            result.setCode(Result.ERROR);
            result.setMsg("密码过于简单");
            return result;
        }
        if (!newPwd.equals(newPwd.trim())) {
            result.setCode(Result.ERROR);
            result.setMsg("密码带有特殊含义字符,请重新输入!");
            return result;
        }
        //设置登录密码
        String encryptedPass = MD5Utils.encrypt(newPwd, UserContext.MD5Key);
        if (userInfoDao.updateUserPass(userId, encryptedPass)) {
            shardedRedisService.delUserInfo(userInfo);
            logger.debug("用户修改密码成功:userId:{} traceId:{}", userId, traceId);
            result.setCode(Result.SUCCESS);
            result.setMsg("修改密码成功");
        } else {
            logger.error("用户修改密码失败:userId:{} traceId:{}", userId, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("操作失败!");
        }
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/7 20:16
     * @Description 获取用户id 自增
     */
    public Long getNewUserId() {
        return userRedis.incr(UserRedisKeys.USER_NEW_ID);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/7 11:23
     * @Description 用户名格式验证
     */
    private Boolean checkNamePattern(String userName) {
        if (userName == null || userName.length() < 4 || userName.length() > 16) {
            return false;
        }
        return RegexUtils.isMatch("^[\\u4e00-\\u9fa50-9a-zA-Z_]\\w{1,16}$", userName);
    }

    /**
     * @Author wenxiaoqing
     * @Date 2017/3/28 20:47
     * @Description 用户名是否存在
     */
    private Boolean userNameExists(String userName) {
        return CollectionUtils.isNotEmpty(getUserInfoByMobile(userName)) || getUserInfoByLoginName(userName) != null;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/30 20:28
     * @Description 根据用户名获取用户信息
     */
    public UserInfoENT getUserInfoByLoginName(String loginName) {
        if (StringUtils.isBlank(loginName)) {
            return null;
        }
        String json = userRedis.get(String.format(UserRedisKeys.USER_INFO_NAME_KEY_NAME, loginName));
        if (StringUtils.isBlank(json)) {
            UserInfoENT userInfoENT = userInfoDao.getUserInfoByLoginName(loginName);
            if (userInfoENT != null) {
                userRedis.set(String.format(UserRedisKeys.USER_INFO_NAME_KEY_NAME, loginName), GfJsonUtil.toJSONString(userInfoENT));
                userRedis.expire(String.format(UserRedisKeys.USER_INFO_NAME_KEY_NAME, loginName), UserRedisKeys.WEEK);
            }
            return userInfoENT;
        }
        return GfJsonUtil.parseObject(json, UserInfoENT.class);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/16 13:49
     * @Description get user info
     */
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    public UserInfoENT getCurrentUserInfo(String userId) {
        String json = userRedis.get(String.format(UserRedisKeys.USER_INFO_KEY_ID, userId));
        if (StringUtils.isEmpty(json)) {
            return refreshUserInfo(userId);
        }
        return GfJsonUtil.parseObject(json, UserInfoENT.class);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/16 15:23
     * @Description 根据手机号获取用户信息
     */
    public List<UserInfoENT> getUserInfoByMobile(String mobile) {
        String userInfos = userRedis.get(String.format(UserRedisKeys.USRE_INFO_MOBILE_LIST_NUMBER, mobile));
        try {
            if (StringUtils.isNotBlank(userInfos)) {
                return GfJsonUtil.parseArray(userInfos, UserInfoENT.class);
            }
            return refreshUserInfoByMobile(mobile);
        } catch (Exception e) {
            logger.error("根据手机号获取用户信息 stackTrace:{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        return null;

    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    @Override
    public List<UserInfoENT> refreshUserInfoByMobile(String mobile) {
        List<UserInfoENT> userInfoENTs = userInfoDao.getUserInfoByMobile(mobile);
        if (CollectionUtils.isNotEmpty(userInfoENTs)) {
            userRedis.set(String.format(UserRedisKeys.USRE_INFO_MOBILE_LIST_NUMBER, mobile), GfJsonUtil.toJSONString(userInfoENTs));
        }
        return userInfoENTs;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/16 13:14
     * @Description refresh user info hash
     */
    public UserInfoENT refreshUserInfo(String userId) {
        try {
            UserInfoENT userInfoENT = userInfoDao.getUserInfo(userId);
            if (userInfoENT != null) {
                userRedis.set(String.format(UserRedisKeys.USER_INFO_KEY_ID, userId), GfJsonUtil.toJSONString(userInfoENT));
                userRedis.expire(String.format(UserRedisKeys.USER_INFO_KEY_ID, userId), UserRedisKeys.WEEK);
            }
            return userInfoENT;
        } catch (Exception e) {
            logger.error("stackTrace:{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        return null;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/16 13:58
     * @Description get user base info
     */
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    public UserBaseInfoENT getCurrentUserBaseInfo(String userId) {
        String json = userRedis.get(String.format(UserRedisKeys.USER_BASE_INFO_KEY_ID, userId));
        if (StringUtils.isEmpty(json)) {
            return refreshUserBaseInfo(userId);
        }
        return GfJsonUtil.parseObject(json, UserBaseInfoENT.class);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/16 13:46
     * @Description refresh user base info hash
     */
    public UserBaseInfoENT refreshUserBaseInfo(String userId) {
        try {
            UserBaseInfoENT userBaseInfoENT = userBaseInfoDao.getUserBaseInfo(userId);
            if (userBaseInfoENT != null) {
                userRedis.set(String.format(UserRedisKeys.USER_BASE_INFO_KEY_ID, userId), GfJsonUtil.toJSONString(userBaseInfoENT));
                userRedis.expire(String.format(UserRedisKeys.USER_BASE_INFO_KEY_ID, userId), UserRedisKeys.WEEK);
            }
            return userBaseInfoENT;
        } catch (Exception e) {
            logger.error("stackTrace:{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        return null;
    }

    //    =========================admin==================================
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listUserInfo(JSONObject jsonParm, ExtModel result) {
        result.setData(userInfoDao.listUserInfo(jsonParm.getInteger("page"), jsonParm.getInteger("limit"), jsonParm.getJSONObject("data")));
        result.setTotal(userInfoDao.listUserInfoCount(jsonParm.getJSONObject("data")));
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void saveUserInfo(UserInfoENT userInfo) {
        userInfoDao.saveUserInfo(userInfo);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void updateUserInfo(UserInfoENT userInfo) {
        userInfoDao.updateUserInfo(userInfo);
    }

    public Boolean checkValidateCode(String mobile) {
        String validateCodeKey = String.format(UserRedisKeys.USER_FORGET_PASS_VERIFY_CODE_VALIDE, mobile);
        String valid = userRedis.get(validateCodeKey);
        return StringUtils.isNotBlank(valid) && valid.equals("1");
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public Result resetIdCardNumber(String userId, String idcardNumber, String name,
                                    String frontUrl, String backUrl, String traceId) {
        Result result = new Result();
        logger.info("身份证重置：userId:{},idcardNumber:{},name:{},frontUrl:{},backUrl:{}", userId, idcardNumber, name, frontUrl, backUrl);
        String idresetKey = String.format(UserRedisKeys.USER_RESET_IDCARDNUM_UNCHECK, userId);
        //获取未审核的身份重置信息
        UserIdcardResetENT userIdcardResetENT = null;
        String userStr = userRedis.get(idresetKey);
        if (StringUtils.isNotBlank(userStr)) {
            userIdcardResetENT = GfJsonUtil.parseObject(userStr, UserIdcardResetENT.class);
        }
        if (userIdcardResetENT == null) {
            userIdcardResetENT = userIdcardResetDao.getUserIdcardReset(userId, 0);
        }
        if (userIdcardResetENT != null && userIdcardResetENT.getStatus() == 0) {
            result.setCode(Result.ERROR);
            result.setMsg("您有未审核的请求，请稍后再试");
            return result;
        }
        UserIdcardResetENT ent = new UserIdcardResetENT();
        ent.setUserId(Long.valueOf(userId));
        ent.setIdcardNumber(idcardNumber);
        ent.setRealName(name);
        ent.setFrontUrl(PropertiesUtil.get("fastdfs.access.url") + frontUrl);
        ent.setBackUrl(PropertiesUtil.get("fastdfs.access.url") + backUrl);
        ent.setStatus(0);
        ent.setCreateTime(new Date());
        ent.setUpdateTime(new Date());
        try {
            userIdcardResetDao.saveUserIdcardReset(ent);
            userRedis.set(idresetKey, GfJsonUtil.toJSONString(ent));
            userRedis.expire(idresetKey, UserContext.USER_RESETIDCARDNUM_LOSE_SECONDS);
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg("身份重置失败");
            logger.error("身份证重置失败：userId:{},traceId:{},error: {}", userId, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    @Override
    public UserIdcardResetENT getResetIdCardInfo(String traceId, String userId) {
        String idresetKey = String.format(UserRedisKeys.USER_RESET_IDCARDNUM_UNCHECK, userId);
        //获取未审核的身份重置信息
        UserIdcardResetENT userIdcardResetENT = null;
        String userStr = userRedis.get(idresetKey);
        if (StringUtils.isNotBlank(userStr)) {
            userIdcardResetENT = GfJsonUtil.parseObject(userStr, UserIdcardResetENT.class);
        }
        return userIdcardResetENT;
    }


    /**
     * @Author shenwei
     * @Date 2017/3/24 18:53
     * @Description 获取用户设置的默认用户名
     */
    @Override
    public String getUserDefaultName(String mobile, String traceId) {
        return userRedis.hget(UserRedisKeys.USER_DEFAULT_REGISTER_NAME, mobile);
    }

    /**
     * 登录账号含有多个用户，列出用户及最后登陆时间
     *
     * @param items
     * @param result
     */
    @Override
    public void userListByLoginTime(List<UserInfoENT> items, Result result) {
        List<UserListDTO> list = new ArrayList<>();
        for (UserInfoENT item : items) {
            UserListDTO userListDTO = new UserListDTO();
            userListDTO.setId(item.getUserId().toString());
            userListDTO.setName(item.getLoginName());
            UserBaseInfoENT userBaseInfoENT = getCurrentUserBaseInfo(String.valueOf(item.getUserId()));
            if (userBaseInfoENT != null) {
                userListDTO.setUserFace(userBaseInfoENT.getFaceUrl());
            }
            if (item.getLastLoginTime() != null) {
                userListDTO.setLastLoginTime(DateUtil.format(item.getLastLoginTime()));
            } else {
                userListDTO.setLastLoginTime(DateUtil.format(item.getRegisterTime()));
            }
            list.add(userListDTO);
        }
        result.setCode(1);
        result.setData(list);
    }

    @Override
    public void userRegisterWarn() {
        try {
            Integer minWarnRegisterCount = 100;
            String minCountStr = PropertiesUtil.get("user.register.warn.mincount");
            if (StringUtils.isNotBlank(minCountStr)) {
                minWarnRegisterCount = Integer.valueOf(minCountStr);
            }
            Date now = new Date();
            String startTime = DateUtil.format(DateUtil.moveMinit(now, -40, true), "yyyy-MM-dd HH:mm:ss");
            String endTime = DateUtil.format(DateUtil.moveMinit(now, -10, true), "yyyy-MM-dd HH:mm:ss");
            JSONObject dataObject = new JSONObject();
            dataObject.put("startCreateTime", startTime);
            dataObject.put("endCreateTime", endTime);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", GfJsonUtil.toJSONString(dataObject));
            List<RegisterCountDTO> registerCountDTOs = userInfoDao.listRegisterCount(jsonObject);
            if (registerCountDTOs != null) {
                boolean isWarn = false;
                StringBuilder warnInfo = new StringBuilder();
                warnInfo.append("<div>注册人数预警：</div><br />");
                warnInfo.append("<table style=\"BORDER-COLLAPSE:collapse\" borderColor=#000000 cellSpacing=0 width=95% align=center bgColor=#ffffff border=1>");
                warnInfo.append("<tr><td align=center>开始日期</td><td align=center>结束日期</td><td align=center>渠道</td><td align=center>注册人数</td></tr>");
                for (RegisterCountDTO registerCountDTO : registerCountDTOs) {
                    if (StringUtils.isNotBlank(registerCountDTO.getNum())) {
                        Integer registerCount = Integer.valueOf(registerCountDTO.getNum());
                        if (registerCount >= minWarnRegisterCount) {
                            isWarn = true;
                            warnInfo.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                                    startTime, endTime, registerCountDTO.getEntranceCode(), registerCount.toString()));
                        }
                    }
                }
                warnInfo.append("</table>");
                warnInfo.append("请尽快确认");
                if (isWarn) {
                    sendEmail(startTime, endTime, warnInfo.toString());
                }
            }
        } catch (Exception e) {
            logger.error("userRegisterWarn异常!! StackTrace = {}", LogExceptionStackTrace.erroStackTrace(e));
        }
    }

    private void sendEmail(String startTime, String endTime, String content) {
        String mailTo = PropertiesUtil.get("user.register.warn.send.mails");
        EmailModel em = new EmailModel();
        String fromEmail = PropertiesUtil.get("smtp.fromemail");
        em.setFromEmail(fromEmail);
        List<String> toEms = java.util.Arrays.asList(mailTo.split(","));
        em.setToEmail(toEms);
        em.setSubject("JDD-注册人数预警,开始时间:" + startTime + ",结束时间：" + endTime);
        em.setText(content);
        //EmailUtil.sendEmail(em);
    }

    private String getIPAddress() {
        String ip = "";
        try {
            ip = NetworkUtil.getIpAddress(request);
            int maxLength = 50;
            if (StringUtils.isNotBlank(ip) && ip.length() > maxLength) {
                ip = ip.substring(0, maxLength - 1);
            }
        } catch (Exception ex) {
            ip = "";
        }
        return ip;
    }
}
