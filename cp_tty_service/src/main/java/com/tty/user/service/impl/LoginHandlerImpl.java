package com.tty.user.service.impl;

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.common.utils.MobileUtil;
import com.tty.common.utils.Result;
import com.tty.user.context.VerifyCodeEnum;
import com.tty.user.params.LoginParams;
import com.tty.user.params.LoginSmsParams;
import com.tty.user.params.MobileLoginParams;
import com.tty.user.params.SmsVerifyParam;
import com.tty.user.service.*;
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

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private UserSmsLoginService userSmsLoginService;

    @Autowired
    private UserMobileLoginService userMobileLoginService;


    private static final Logger logger = LoggerFactory.getLogger(LoginHandlerImpl.class);

    public Result login(String traceId, String uuid, ClientRequestHeader header, String params) {
        try {
            LoginParams loginParams = GfJsonUtil.parseObject(params, LoginParams.class);
            if (loginParams == null) {
                logger.error("[用户登录] 参数错误 params:{} traceId:{}", params, traceId);
                return getFailResult();
            }
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
            logger.error("traceId:{} params:{} stackTrace:{}", traceId, params, LogExceptionStackTrace.erroStackTrace(e));
            return getFailResult();
        }
    }

    public Result getLoginBySmsVerifyCode(String traceId, String params) {
        try {
            Result result = new Result();
            SmsVerifyParam smsVerifyParam = GfJsonUtil.parseObject(params, SmsVerifyParam.class);
            if (smsVerifyParam == null) {
                logger.error("[短信验证码登录获取验证码] 参数错误 params:{} traceId:{}", params, traceId);
                return getFailResult();
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

    public Result loginWithSmsVerifyCode(String traceId, ClientRequestHeader header, String params) {
        try {
            Result result = new Result();
            LoginSmsParams loginSmsParams = GfJsonUtil.parseObject(params, LoginSmsParams.class);
            if (loginSmsParams == null) {
                logger.error("[短信验证码登录] 参数错误, params:{} traceId:{}", params, traceId);
                return getFailResult();
            }
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
            logger.error("traceId:{} params:{} stackTrace:{}", traceId, params, LogExceptionStackTrace.erroStackTrace(e));
            return getFailResult();
        }
    }

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
