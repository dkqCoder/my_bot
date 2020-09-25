package com.tty.user.service.impl;/**
 * Created by shenwei on 2017/3/16.
 */

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.tty.common.utils.DateUtils;
import com.tty.common.utils.MobileUtil;
import com.tty.common.utils.Result;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.context.VerifyCodeEnum;
import com.tty.user.service.SmsService;
import com.tty.user.service.UserInfoService;
import com.tty.user.service.VerifyCodeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.tty.user.dao.ent.UserInfoENT;

import java.util.List;
import java.util.Objects;


/**
 * @author shenwei
 * @create 2017-03-16
 */
@Service("verifyCodeService")
public class VerifyCodeServiceImpl implements VerifyCodeService {

    private static final Logger logger = LoggerFactory.getLogger(VerifyCodeServiceImpl.class);
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SmsService smsService;

    public Result getVerifyCode(String userId, String mobile, VerifyCodeEnum verifyCodeEnum, String traceId, String oldMobile) {
        Integer smsType = 1;
        switch (verifyCodeEnum) {
            case BINDMOBILE:
                return bindUserMobile(userId, oldMobile, mobile, traceId);
            case LOGINBYSMS:
                return loginBySms(mobile, traceId);
            case QUICKREGISTER:
                return quickRegister(mobile, smsType, traceId);
            case FORGETLOGINPASS:
                return forgetLoginPass(userId, null, mobile, traceId);
            case FORGETPAYPASS:
                return forgetPayPass(userId, mobile, traceId);
            case VALIDATEOLDMOBILE:
                return sendUserOldMobile(userId, oldMobile, traceId);
            default:
                return notMappingResult();
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/16 10:09
     * @Description 103 get verify code when bind user mobile
     */
    private Result bindUserMobile(String userId, String oldMobile, String mobile, String traceId) {
        Result result = new Result();
        UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId);
        if (StringUtils.isNotBlank(userInfoENT.getMobileNumber()) && userInfoENT.getMobileNumber().equals(mobile)) {
            result.setCode(Result.ERROR);
            result.setMsg("无需重复绑定");
            return result;
        }
        if (StringUtils.isNotBlank(userInfoENT.getMobileNumber()) && !Objects.equals(userInfoENT.getMobileNumber(), oldMobile)) {
            result.setCode(Result.ERROR);
            result.setMsg("原手机号输入有误");
            return result;
        }
        String verifyCodeKey = String.format(UserRedisKeys.USER_BIND_MOBILE_VERIFY_CODE_USERID, userId, mobile);
        String verifyGetCountKey = String.format(UserRedisKeys.USER_BIND_MOBILE_GET_COUNT_USERID, userId, mobile);
        return verifySend(verifyCodeKey, verifyGetCountKey, userId, mobile, VerifyCodeEnum.BINDMOBILE, traceId);
    }

    /**
     * @Author linian
     * @Date 2018/1/23 14:30
     * @Description 验证码保存并发送
     */
    private Result verifySend(String verifyCodeKey, String verifyGetCountKey, String userId, String mobile, VerifyCodeEnum verifyCodeEnum, String traceId) {
        Integer smsType = 1;
        return verifySend(verifyCodeKey, verifyGetCountKey, userId, mobile, verifyCodeEnum, smsType, traceId);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/16 11:52
     * @Description 验证码保存并发送
     */
    private Result verifySend(String verifyCodeKey, String verifyGetCountKey, String userId, String mobile, VerifyCodeEnum verifyCodeEnum, Integer smsType, String traceId) {
        Result result = new Result();
        try {
            // 获取验证码次数验证
            if (!canStillGetVerifyCodeToday(verifyGetCountKey)) {
                result.setCode(Result.ERROR);
                result.setMsg("今日获取验证码次数已达上限,请明日再尝试");
                return result;
            }
            String verifyCode = MobileUtil.getVerifyCode();
            userRedis.set(verifyCodeKey, verifyCode);
            userRedis.expire(verifyCodeKey, 10 * 60);
            if (verifyCodeEnum.getValue().equals(VerifyCodeEnum.BINDMOBILE.getValue())) {
                cacheMobileByUserIdAndVerifyCode(userId, mobile, verifyCode);
            }
            if (smsService.sendVerifyCode(0, mobile, verifyCodeEnum, userId, verifyCode, smsType, traceId)) {
                if (VerifyCodeEnum.FORGETPAYPASS.equals(verifyCodeEnum)) {
                    result.setCode(1);
                    result.setMsg("发送验证码成功");
                    return result;
                }
                result.setCode(Result.SUCCESS);
                result.setMsg("发送验证码成功");
                return result;
            } else {
                result.setCode(-97);
                result.setMsg("发送验证码失败");
                return result;
            }
        } catch (Exception e) {
            logger.error("[发送验证码失败] stackTrace:{}", LogExceptionStackTrace.erroStackTrace(e));
            result.setCode(-97);
            result.setMsg(Result.MSG_ERROR_DESC);
        }
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/17 15:09
     * @Description 今日获取同一类别验证码次数是否已达上限
     */
    private Boolean canStillGetVerifyCodeToday(String verifyGetCountKey) {
        // 获取验证码次数验证
        String getCountStr = userRedis.get(verifyGetCountKey);
        Integer getVerifyCodeCount = StringUtils.isEmpty(getCountStr) ? 0 : Integer.valueOf(getCountStr);
        getVerifyCodeCount++;
        userRedis.set(verifyGetCountKey, getVerifyCodeCount.toString());
        userRedis.expire(verifyGetCountKey, DateUtils.getSecondsToTomorrow().intValue());
        if (getVerifyCodeCount > UserContext.VERIFY_FAIL_MAX) {
            return false;
        }
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/27 14:44
     * @Description 因接口未传入mobile 故被迫如此实现
     */
    private void cacheMobileByUserIdAndVerifyCode(String userId, String mobile, String verifyCode) {
        String mobileKey = String.format(UserRedisKeys.USER_BIND_MOBILE_VERIFY_CODE_MOBILE, userId, verifyCode);
        userRedis.set(mobileKey, mobile);
        userRedis.expire(mobileKey, 10 * 60);
    }


    /**
     * @Author shenwei
     * @Date 2017/3/16 11:45
     * @Description 1014 get verify code when login by sms
     */
    private Result loginBySms(String mobile, String traceId) {
        Result result = new Result();
        List<UserInfoENT> userInfoENTList = userInfoService.getUserInfoByMobile(mobile);
        if (CollectionUtils.isEmpty(userInfoENTList)) {
            result.setCode(Result.ERROR);
            result.setMsg("该手机号没有绑定任何账号,请联系客服或进行注册");
            return result;
        }
        String verifyCodeKey = String.format(UserRedisKeys.USER_LOGIN_SMS_VERIFY_CODE_MOBILE, mobile);
        String verifyGetCountKey = String.format(UserRedisKeys.USER_LOGIN_SMS_GET_COUNT_MOBILE, mobile);
        return verifySend(verifyCodeKey, verifyGetCountKey, "", mobile, VerifyCodeEnum.LOGINBYSMS, traceId);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/20 14:41
     * @Description 1033 get verify code when quick register
     */
    private Result quickRegister(String mobile,Integer smsType, String traceId) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_QUICK_REGISTER_VERIFY_CODE, mobile);
        String verifyGetCountKey = String.format(UserRedisKeys.USER_QUICK_REGISTER_GET_COUNT, mobile);
        return verifySend(verifyCodeKey, verifyGetCountKey, "", mobile, VerifyCodeEnum.QUICKREGISTER, smsType, traceId);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/20 12:01
     * @Description 1030 get verify code when you forget login pass
     */
    private Result forgetLoginPass(String userId, String userName, String mobile, String traceId) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_FORGET_PASS_VERIFY_CODE, mobile);
        String verifyGetCountKey = String.format(UserRedisKeys.USER_FORGET_PASS_GET_COUNT, mobile);
        return verifySend(verifyCodeKey, verifyGetCountKey, userId, mobile, VerifyCodeEnum.FORGETLOGINPASS, traceId);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/20 12:11
     * @Description 1201 get verify code when you forget pay pass
     */
    private Result forgetPayPass(String userId, String mobile, String traceId) {
        String verifyCodeKey = String.format(UserRedisKeys.USER_FORGET_PAYPASS_VERIFY_CODE, userId);
        String verifyGetCountKey = String.format(UserRedisKeys.USER_FORGET_PAYPASS_GET_COUNT, userId);
        return verifySend(verifyCodeKey, verifyGetCountKey, userId, mobile, VerifyCodeEnum.FORGETPAYPASS, traceId);
    }

    /**
     * @Author linian
     * @Date 2017/8/2 11:00
     * @Description 103 get verify code when bind user mobile
     */
    private Result sendUserOldMobile(String userId, String oldMobile, String traceId) {
        Result result = new Result();
        UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId);
        if (StringUtils.isNotBlank(userInfoENT.getMobileNumber()) && !Objects.equals(userInfoENT.getMobileNumber(), oldMobile)) {
            result.setCode(Result.ERROR);
            result.setMsg("原手机号输入有误");
            return result;
        }
        String verifyCodeKey = String.format(UserRedisKeys.USER_OLD_MOBILE_VERIFY_CODE_USERID, userId, oldMobile);
        String verifyGetCountKey = String.format(UserRedisKeys.USER_OLD_MOBILE_GET_COUNT_USERID, userId, oldMobile);
        return verifySend(verifyCodeKey, verifyGetCountKey, userId, oldMobile, VerifyCodeEnum.BINDMOBILE, traceId);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/16 11:35
     * @Description 不存在的验证码方式
     */
    private Result notMappingResult() {
        Result result = new Result();
        result.setCode(Result.ERROR);
        result.setMsg("不存在的验证码获取方式");
        return result;
    }

}
