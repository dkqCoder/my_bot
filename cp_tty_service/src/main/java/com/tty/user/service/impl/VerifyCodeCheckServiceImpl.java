package com.tty.user.service.impl;/**
 * Created by shenwei on 2017/3/20.
 */

import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.tty.common.utils.Result;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.context.VerifyCodeEnum;
import com.tty.user.dao.ent.UserInfoENT;
import com.tty.user.service.SmsService;
import com.tty.user.service.UserInfoService;
import com.tty.user.service.UserLoginService;
import com.tty.user.service.VerifyCodeCheckService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author shenwei
 * @create 2017-03-20
 */
@Service("verifyCodeCheckService")
public class VerifyCodeCheckServiceImpl implements VerifyCodeCheckService {

    private final Logger logger = LoggerFactory.getLogger(VerifyCodeCheckServiceImpl.class);
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private SmsService smsService;
   /* @Autowired
    private DubboActivityBaseInfoService dubboActivityBaseInfoService;
    @Autowired
    private UserEntranceDenyTimeDao userEntranceDenyTimeDao;
    @Autowired
    private UserMobileWhitelistDao userMobileWhitelistDao;*/

    /**
     * @Author shenwei
     * @Date 2017/3/21 16:15
     * @Description 绑定手机号验证码校验
     */
    @Override
    public Result checkBindUserMobile(String verifyCode, String mobile, String userId) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_BIND_MOBILE_VERIFY_CODE_USERID, userId);
        String verifyErrorCountKey = String.format(UserRedisKeys.USER_BIND_MOBILE_ERROR_COUNT_USERID, userId);
        return verifyCheck(null, mobile, verifyCode, verifyCodeKey, verifyErrorCountKey, VerifyCodeEnum.BINDMOBILE);
    }

    /**
     * @Author linian
     * @Date 2017/8/2 13:15
     * @Description 绑定手机号原手机号验证码校验
     */
    @Override
    public Result checkUserOldMobile(String verifyCode, String mobile, String userId) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_OLD_MOBILE_VERIFY_CODE_USERID, userId);
        String verifyErrorCountKey = String.format(UserRedisKeys.USER_OLD_MOBILE_ERROR_COUNT_USERID, userId);
        return verifyCheck(null, mobile, verifyCode, verifyCodeKey, verifyErrorCountKey, VerifyCodeEnum.VALIDATEOLDMOBILE);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/21 16:32
     * @Description 短信验证码登陆校验
     */
    @Override
    public Result checkLoginBySms(String verifyCode, String mobile) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_LOGIN_SMS_VERIFY_CODE_MOBILE, mobile);
        String verifyErrorCountKey = String.format(UserRedisKeys.USER_LOGIN_SMS_ERROR_COUNT_MOBILE, mobile);
        return verifyCheck(null, mobile, verifyCode, verifyCodeKey, verifyErrorCountKey, VerifyCodeEnum.LOGINBYSMS);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/27 11:53
     * @Description 1013 手机号登录验证码校验
     */
    @Override
    public Result checkLoginByMobile(String verifyCode, String mobile) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_LOGIN_MOBILE_VERIFY_CODE_MIBILE, mobile);
        String verifyErrorCountKey = String.format(UserRedisKeys.USER_LOGIN_MOBILE_ERROR_COUNT_MIBILE, mobile);
        Result result = verifyCheck(null, mobile, verifyCode, verifyCodeKey, verifyErrorCountKey, null);
        if (Objects.equals(result.getCode(), Result.SUCCESS)) {
            List<UserInfoENT> userInfoENTS = userInfoService.getUserInfoByMobile(mobile);
            //userInfoService.userListByLoginTime(userInfoENTS, result);
        }
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/20 15:45
     * @Description 1032 忘记登陆密码校验
     */
    @Override
    public Result checkForgetLoginPass(String verifyCode, String mobile) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_FORGET_PASS_VERIFY_CODE, mobile);
        String verifyErrorCountKey = String.format(UserRedisKeys.USER_FORGET_PASS_ERROR_COUNT, mobile);
        return verifyCheck(null, mobile, verifyCode, verifyCodeKey, verifyErrorCountKey, VerifyCodeEnum.FORGETLOGINPASS);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/20 16:35
     * @Description 1035 PC快速注册校验
     */
    @Override
    public Result checkPCQuickRegister(String verifyCode, String mobile, String traceId, ClientRequestHeader header) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_QUICK_REGISTER_VERIFY_CODE, mobile);
        String verifyErrorCountKey = String.format(UserRedisKeys.USER_QUICK_REGISTER_ERROR_COUNT, mobile);
        return verifyCheck(null, mobile, verifyCode, verifyCodeKey, verifyErrorCountKey, VerifyCodeEnum.QUICKREGISTER);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/20 16:35
     * @Description 1034 快速注册校验
     */
    @Override
    public Result checkQuickRegister(String verifyCode, String mobile, String traceId, ClientRequestHeader header) {
        Result result = new Result();
        String cachedPass = userRedis.get(String.format(UserRedisKeys.USER_REGISTER_PWD, mobile));
        if (StringUtils.isBlank(cachedPass)) {
            result.setCode(Result.ERROR);
            result.setMsg("密码遗失,请重新注册");
            return result;
        }
        String verifyCodeKey = String.format(UserRedisKeys.USER_QUICK_REGISTER_VERIFY_CODE, mobile);
        String verifyErrorCountKey = String.format(UserRedisKeys.USER_QUICK_REGISTER_ERROR_COUNT, mobile);
        result = verifyCheck(null, mobile, verifyCode, verifyCodeKey, verifyErrorCountKey, VerifyCodeEnum.QUICKREGISTER);
        if (result.getCode() < 0) {
            return result;
        }
        if (userNameExists(mobile)) {
            return getNameExistsResult(result);
        }
        Long userId = userInfoService.registerByUserNameAndPwd(mobile, mobile, header.getPlatformCode(), cachedPass, traceId, header, result);
        /*if (userId > 0) {
            threadPoolTaskExecutor.execute(() -> {
                if (userEntranceDenyTimeDao.notInEntranceDenyTimeRegionWithcmdName(Optional.ofNullable(header.getCmdName()).orElse(""))) {
                    UserMobileWhitelistENT ent = new UserMobileWhitelistENT();
                    ent.setMobile(mobile);
                    userMobileWhitelistDao.saveUserMobileWhitelist(ent);
                }
            });
            UserRegisterResult userRegisterResult = new UserRegisterResult();
            userRegisterResult.setUserId(HttpUtils.encodedId(userId));
            userRegisterResult.setPassWord(cachedPass);
            result.setCode(Result.SUCCESS);
            result.setData(userRegisterResult);
            result.setMsg("注册成功");
            return result;
        }*/
        result.setCode(Result.ERROR);
        result.setMsg("注册失败,请重新尝试");
        return result;
    }

    private Boolean userNameExists(String userName) {
        return userInfoService.getUserInfoByLoginName(userName) != null || CollectionUtils.isNotEmpty(userInfoService.getUserInfoByMobile(userName));
    }

    /**
     * @Author shenwei
     * @Date 2017/3/20 16:37
     * @Description 1202忘记支付密码校验
     */
    @Override
    public Result checkForgetPayPass(String verifyCode, String userId) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_FORGET_PAYPASS_VERIFY_CODE, userId);
        String verifyErrorCountKey = String.format(UserRedisKeys.USER_FORGET_PAYPASS_ERROR_COUNT, userId);
        return verifyCheck(null, null, verifyCode, verifyCodeKey, verifyErrorCountKey, VerifyCodeEnum.FORGETPAYPASS);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/31 19:51
     * @Description 1021 猎豹登录验证码校验
     */
    @Override
    public Result checkCmttVerifyCode(String verifyCode, String userId, String mobile, String passWord, String traceId, ClientRequestHeader header, int from) {
        Result result = new Result();
        String verifyCodeKey = String.format(UserRedisKeys.USER_CMTT_VERIFY_CODE, mobile);
        String verifyErrorCountKey = String.format(UserRedisKeys.USER_CMTT_ERROR_COUNT, mobile);
        result = verifyCheck(userId, mobile, verifyCode, verifyCodeKey, verifyErrorCountKey, VerifyCodeEnum.CMMTLOGIN);
        if (result.getCode() < 0) {
            return result;
        }
        if (userNameExists(mobile)) {
            return getNameExistsResult(result);
        }
        if (userInfoService.registerByUserNameAndPwd(mobile, mobile, header.getCmdName(), passWord, traceId, header, result) > 0) {
            /*if (from == 0 || from == 2) {
                smsService.sendWapRandomPass(0, mobile, userId, passWord, traceId);
            }
            if (from == 1) {
                TokenLoginResult tokenLoginResult = (TokenLoginResult) result.getData();
                TokenLoginResultExtModel tokenLoginResultExtModel = new TokenLoginResultExtModel();
                tokenLoginResultExtModel.setId(tokenLoginResult.getId());
                tokenLoginResultExtModel.setMobile(tokenLoginResult.getMobile());
                tokenLoginResultExtModel.setToken(tokenLoginResult.getToken());
                tokenLoginResultExtModel.setUserName(tokenLoginResult.getUserName());
                tokenLoginResultExtModel.setUserType(tokenLoginResult.getUserType());
                String randomNumber = "", issueNumber = "";

                List<UserInfoENT> userList = userInfoService.getUserInfoByMobile(mobile);
                Long userID = userList.get(0).getUserId();
                String base64UserID = new BASE64Encoder().encode(userID.toString().getBytes());
                RequestHeaderDTO colorBallRequest = new RequestHeaderDTO();
                BeanUtils.copy(header, colorBallRequest);
                colorBallRequest.setUserID(base64UserID);
                Response response = dubboActivityBaseInfoService.getFreeDoubleColorBall(colorBallRequest);
                logger.info("checkCmttVerifyCodeAndSend ret:{}", base64UserID);
                if (response != null && response.getData() != null && response.getCode() == 0) {
                    Map<String, Object> jObject = (HashMap) response.getData();
                    randomNumber = jObject.get("randomNumber").toString();
                    issueNumber = jObject.get("issueNumber").toString();
                    tokenLoginResultExtModel.setNumber(randomNumber);
                    tokenLoginResultExtModel.setIssueNumber(issueNumber);
                }

                result.setData(tokenLoginResultExtModel);
                String messageBody = String.format("【奖多多】感谢注册金山奖多多，新人礼包已到账，请登录APP至我的-福利任务领取。");
                smsService.sendSMS(mobile, messageBody, traceId);
            }*/
            return result;

        } else {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            return result;
        }
    }

    private Result getNameExistsResult(Result result) {
        result.setCode(Result.ERROR);
        result.setMsg("手机号已注册,请直接登陆");
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/24 14:06
     * @Description 1019 wap快速注册验证码验证
     */
    @Override
    public Result checkWapVerifyCode(String verifyCode, String mobile, String traceId, ClientRequestHeader header) {
        Result result = new Result();
        String verifyCodeKey = String.format(UserRedisKeys.USER_WAP_VERIFY_CODE, mobile);
        if (!verifyWapCheck(verifyCode, verifyCodeKey, result)) {
            return result;
        }
        List<UserInfoENT> userInfoENTS = userInfoService.getUserInfoByMobile(mobile);
        if (CollectionUtils.isEmpty(userInfoENTS)) {
            //如果该手机号作用户名却找到了,PC兼容
            if (userInfoService.getUserInfoByLoginName(mobile) != null) {
                result.setCode(Result.ERROR);
                result.setMsg("该手机号已经注册过账号");
                return result;
            }
            /*String randomPass = MobileUtil.getRandomPass();
            Long userId = userInfoService.registerByUserNameAndPwd(mobile, mobile, header.getCmdName(), randomPass, traceId, header, result);
            if (userId > 0) {
                userLoginService.loginWithNamePwd(mobile, randomPass, false, header, result, 1);
                if (!smsService.sendWapRandomPass(0, mobile, String.valueOf(userId), randomPass, traceId)) {
                    logger.error("wap 快速注册随机密码发送失败: mobile:{} traceId:{}", mobile, traceId);
                }
            } else {
                result.setCode(Result.ERROR);
                result.setMsg(Result.MSG_ERROR_DESC);
            }*/
            return result;
        }
        if (userInfoENTS.size() > 1) {
            result.setCode(Result.ERROR);
            result.setMsg("该手机号绑定多个账号,请使用短信登录");
            return result;
        }
        //userLoginService.loginWithNamePwd(userInfoENTS.get(0).getLoginName(), userInfoENTS.get(0).getPassword(), true, header, result, 0);
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/6/28 10:49
     * @Description 20004 现有账号绑定微信验证码校验
     */
    @Override
    public Result checkWeChatBindVerifyCode(String verifyCode, String mobile, String traceId) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_WECHAT_BIND_VERIFY_CODE, mobile);
        String verifyErrorCountKey = String.format(UserRedisKeys.USER_WECHAT_BIND_ERROR_COUNT, mobile);
        return verifyCheck(null, mobile, verifyCode, verifyCodeKey, verifyErrorCountKey, VerifyCodeEnum.WECHATBIND);
    }

    /**
     * @Author shenwei
     * @Date 2017/10/26 11:08
     * @Description 华为验证码校验
     */
    @Override
    public Result checkHuaweiVerifyCode(String verifyCode, String mobile, String traceId) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_HUAWEI_VERIFY_CODE_MOBILE, mobile);
        String verifyErrorCountKey = String.format(UserRedisKeys.USER_HUAWEI_ERROR_COUNT_MOBILE, mobile);
        return verifyCheck(null, mobile, verifyCode, verifyCodeKey, verifyErrorCountKey, VerifyCodeEnum.HUAWEI);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/20 15:50
     * @Description 验证码校验
     */
    private Result verifyCheck(String userId, String mobile, String verifyCode, String verifyCodeKey, String verifyErrorCountKey, VerifyCodeEnum verifyCodeEnum) {
        Result result = new Result();
        if (verifyCode.equals(userRedis.get(verifyCodeKey))) {
            result.setCode(Result.SUCCESS);
            result.setMsg("验证成功");
            if (verifyCodeEnum != null && VerifyCodeEnum.FORGETLOGINPASS.equals(verifyCodeEnum)) {
                userRedis.set(String.format(UserRedisKeys.USER_FORGET_PASS_VERIFY_CODE_VALIDE, mobile), "1");
                userRedis.expire(String.format(UserRedisKeys.USER_FORGET_PASS_VERIFY_CODE_VALIDE, mobile), 10 * 60);
            }
            //手机短信登录 状态录入
            if (verifyCodeEnum != null && VerifyCodeEnum.LOGINBYSMS.equals(verifyCodeEnum)) {
                userRedis.set(String.format(UserRedisKeys.USER_SMS_LOGIN_VERIFY_CODE_VALIDE, mobile), "1");
                userRedis.expire(String.format(UserRedisKeys.USER_SMS_LOGIN_VERIFY_CODE_VALIDE, mobile), 10 * 60);
            }
            return result;
        }
        Integer errorCount = userRedis.get(verifyErrorCountKey) == null ? 0 : Integer.parseInt(userRedis.get(verifyErrorCountKey));
        errorCount++;
        /*userRedis.set(verifyErrorCountKey, errorCount.toString());
        userRedis.expire(verifyCodeKey, Integer.valueOf(DateUtils.getSecondsToTomorrow().toString()));
        if (errorCount <= UserContext.VERIFY_FAIL_PER_MAX) {
            result.setCode(Result.ERROR);
            result.setMsg("验证失败" + errorCount + "次,请重试");
            return result;
        } else {
            userRedis.del(verifyErrorCountKey);
            result.setCode(Result.ERROR);
            result.setMsg("验证失败" + errorCount + "次,请重新获取验证码");
        }*/
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/24 14:20
     * @Description wap验证码校验
     */
    private Boolean verifyWapCheck(String verifyCode, String verifyCodeKey, Result result) {
        String CachedVerifyCode = userRedis.get(verifyCodeKey);
        if (StringUtils.isBlank(CachedVerifyCode)) {
            result.setCode(Result.ERROR);
            result.setMsg("验证码丢失,请重新获取验证码!");
            return false;
        }
        if (!verifyCode.equals(CachedVerifyCode)) {
            result.setCode(Result.ERROR);
            result.setMsg("输入的验证码与获取的验证码不一致");
            return false;
        }
        return true;
    }
}
