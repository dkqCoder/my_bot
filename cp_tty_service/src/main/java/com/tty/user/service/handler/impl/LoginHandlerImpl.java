package com.tty.user.service.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.common.utils.Result;
import com.tty.user.common.utils.MobileUtil;
import com.tty.user.context.VerifyCodeEnum;
import com.tty.user.controller.model.params.*;
import com.tty.user.service.*;
import com.tty.user.service.handler.LoginHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ln
 */
@Component("loginHandler")
public class LoginHandlerImpl implements LoginHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginHandlerImpl.class);
    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private UserSmsLoginService userSmsLoginService;
    @Autowired
    private UserMobileLoginService userMobileLoginService;
    @Autowired
    private UserLoginService userLoginService;

    /**
     * @Author shenwei
     * @Date 2017/3/14 15:47
     * @Description 1011 用户登录(包含第三方登录)
     */
    @Override
    public Result login(String traceId, String uuid, ClientRequestHeader header, LoginParams loginParams) {
        try {
            String token = null;
            if (StringUtils.isNotBlank(loginParams.getToken())) {
                token = loginParams.getToken();
            }
            String userName = loginParams.getName();
            String password = loginParams.getPassword();
            Integer userType = loginParams.getUserType();
            String userId = loginParams.getUserId();  //第三方登录才有userId
            return userLoginService.login(token, userName, password, userId, uuid, userType, traceId, header);
        } catch (Exception e) {
            logger.error("traceId:{} params:{} stackTrace:{}", traceId, JSONObject.toJSONString(loginParams), LogExceptionStackTrace.erroStackTrace(e));
            return getFailResult();
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/16 14:49
     * @Description 1014 短信验证码登录获取验证码
     */
    @Override
    public Result getLoginBySmsVerifyCode(String traceId, String params) {
        try {
            Result result = new Result();
            SmsVerifyParam smsVerifyParam = GfJsonUtil.parseObject(params, SmsVerifyParam.class);
            if (smsVerifyParam == null) {
                logger.error("[短信验证码登录获取验证码] 参数错误 params:{} traceId:{}", params, traceId);
                return getFailResult();
            }
            boolean checkFlag = verifyCodeService.checkCaptcha(smsVerifyParam.getCaptchaKey(), smsVerifyParam.getCaptchaValue());
            if (!checkFlag) {
                result.setCode(Result.ERROR);
                result.setMsg("请输入正确的图形验证码");
                return result;
            }
            String mobile = smsVerifyParam.getMobile();
            if (!MobileUtil.isMobileNO(mobile)) {
                result.setCode(Result.ERROR);
                result.setMsg("请输入正确格式的手机号码");
                return result;
            }
            return verifyCodeService.getVerifyCode(null, mobile, VerifyCodeEnum.LOGINBYSMS, traceId, null);
        } catch (Exception e) {
            logger.error("traceId:{} params:{} stackTrace:{}", traceId, params, LogExceptionStackTrace.erroStackTrace(e));
            return getFailResult();
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/30 19:18
     * @Description 1015 选择默认用户名登录
     */
    @Override
    public Result chooseUserDefaultLoginAccounts(String traceId, ClientRequestHeader header, String params) {
        try {
            UserNameAndMobileParam userNameAndMobileParam = GfJsonUtil.parseObject(params, UserNameAndMobileParam.class);
            if (userNameAndMobileParam == null) {
                return getFailResult();
            }
            return userLoginService.chooseUserDefaultLoginAccounts(userNameAndMobileParam.getUserName(),
                    userNameAndMobileParam.getMobile(), null, traceId, header);
        } catch (Exception e) {
            logger.error("traceId:{} params:{} stackTrace:{}", traceId, params, LogExceptionStackTrace.erroStackTrace(e));
            return getFailResult();
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/15 14:53
     * @Description 1016 短信验证码登录
     */
    @Override
    public Result loginWithSmsVerifyCode(ClientRequestHeader header, LoginParams loginSmsParams) {
        String traceId = header.getTraceID();
        try {
            Result result = new Result();
            if (StringUtils.isEmpty(loginSmsParams.getMobile()) || StringUtils.isEmpty(loginSmsParams.getVerifyCode())) {
                result.setCode(Result.ERROR);
                result.setMsg("手机号验证码不能为空");
                return result;
            }
            if (!MobileUtil.isMobileNO(loginSmsParams.getMobile())) {
                result.setCode(Result.ERROR);
                result.setMsg("请输入正确格式的手机号码");
                return result;
            }
            String mobile = loginSmsParams.getMobile();
            String verifyCode = loginSmsParams.getVerifyCode();
            return userSmsLoginService.login(mobile, verifyCode, header);
        } catch (Exception e) {
            logger.error("traceId:{} params:{} stackTrace:{}", traceId, JSONObject.toJSONString(loginSmsParams), LogExceptionStackTrace.erroStackTrace(e));
            return getFailResult();
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/24 18:16
     * @Description 1017 android and ios mobile login
     */
    @Override
    public Result loginWithMobile(String traceId, ClientRequestHeader header, String params) {
        try {
            MobileLoginParams mobileLoginParams = GfJsonUtil.parseObject(params, MobileLoginParams.class);
            if (mobileLoginParams == null) {
                return getFailResult();
            }
            String token = null;
            if (StringUtils.isNotBlank(mobileLoginParams.getToken())) {
                token = mobileLoginParams.getToken();
            }
            return userMobileLoginService.login(mobileLoginParams.getMobile(), mobileLoginParams.getPassword(), token,
                    mobileLoginParams.getUserType(), traceId, header, mobileLoginParams.getUserId());
        } catch (Exception e) {
            logger.error("登录异常 traceId:{} stackTrace:{}", traceId, LogExceptionStackTrace.erroStackTrace(e));
            return getFailResult();
        }
    }


    private Result getFailResult() {
        Result result = new Result();
        result.setCode(Result.ERROR);
        result.setMsg(Result.MSG_ERROR_DESC);
        result.setData(Result.MSG_ERROR_DESC);
        return result;
    }
}
