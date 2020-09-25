package com.tty.user.controller.api;

import com.jdd.fm.core.annotations.Action;
import com.jdd.fm.core.annotations.ActionController;
import com.jdd.fm.core.base.APIBaseController;
import com.jdd.fm.core.ehcache.EhcacheManager;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.token.UserVO;
import com.jdd.fm.core.utils.web.WebActionDispatcher;
import com.tty.common.utils.BeanUtils;
import com.tty.common.utils.Result;
import com.tty.user.service.LoginHandler;
import com.tty.user.service.RegisterHandler;
import com.tty.user.service.UserInfoHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@ActionController
@RequestMapping(value = "/user/public", method = RequestMethod.POST)

public class PubMobileHandlerSecurityController extends APIBaseController {
    Logger logger = LoggerFactory.getLogger(PubMobileHandlerSecurityController.class);

    @Autowired
    private UserInfoHandler userInfoHandler;

    @Autowired
    private RegisterHandler registerHandler;

    @Autowired
    private EhcacheManager ehcacheManager;

    @Autowired
    private LoginHandler loginHandler;

    @RequestMapping(value = "/securityMobileHandler")
    public ResultModel mobileHandler(HttpServletRequest request) {
        ResultModel rm = new ResultModel();
        try {
            rm = (ResultModel) WebActionDispatcher.invoke(request, ehcacheManager);
        } catch (Exception e) {
            logger.error("user接口异常,stackTrace={}", LogExceptionStackTrace.erroStackTrace(e));
            rm.setCode(Result.ERROR);
            rm.setMsg(Result.MSG_ERROR_DESC);
        }
        return rm;
    }


    /**
     * 通过验证token 重置密码
     */
    @Action(value = "1061")
    public ResultModel resetPasswordWithToken(ClientRequestHeader header, Object body) {
        Result result = userInfoHandler.resetPassword(header.getTraceID(), String.valueOf(body), header);
        return resultToResultModel(result);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/22 10:36
     * @Description 103 绑定手机号发送验证码
     */
    @Action(value = "103", tokenValidated = true)
    public ResultModel getBindMobileVerifyCode(ClientRequestHeader header, Object body, UserVO user) {
        Result result = userInfoHandler.getBindMobileVerifyCode(header.getTraceID(), String.valueOf(user.getUserid()), String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/23 14:46
     * @Description 104 绑定手机号
     */
    @Action(value = "104", tokenValidated = true)
    public ResultModel bindUserMobile(ClientRequestHeader header, Object body, UserVO user) {
        Result result = userInfoHandler.bindUserMobile(header.getTraceID(), String.valueOf(user.getUserid()), String.valueOf(body), header);
        return resultToResultModel(result);
    }

    /**
     * @Author shenwei
     * @Date 2017/4/1 11:32
     * @Description
     */
    @Action(value = "106", tokenValidated = true)
    public ResultModel changeUserPassword(ClientRequestHeader header, Object body, UserVO user) {
        Result result = userInfoHandler.changeUserPassword(header.getTraceID(), String.valueOf(user.getUserid()), String.valueOf(body));
        return resultToResultModel(result);
    }


    /**
     * @Author shenwei
     * @Date 2017/3/28 10:32
     * @Description 100 用户注册
     */
    @Action(value = "100")
    public ResultModel register(ClientRequestHeader header, Object body) {
        Result result = registerHandler.register(header.getTraceID(), header, String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/23 18:04
     * @Description 1011 用户登录
     */
    @Action(value = "1011")
    public ResultModel LoginByToken(ClientRequestHeader header, Object body) {
        Result result = loginHandler.login(header.getTraceID(), header.getUuid(), header, String.valueOf(body));
        return resultToResultModel(result);
    }


    /**
     * @Author shenwei
     * @Date 2017/3/23 15:17
     * @Description 1014 短信验证码登录获取验证码
     */
    @Action(value = "1014")
    public ResultModel getLoginBySmsVerifyCode(ClientRequestHeader header, Object body) {
        Result result = loginHandler.getLoginBySmsVerifyCode(header.getTraceID(), String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/23 15:55
     * @Description 1016短信验证码登录
     */
    @Action(value = "1016")
    public ResultModel LoginBySmsVerifyCode(ClientRequestHeader header, Object body) {
        Result result = loginHandler.loginWithSmsVerifyCode(header.getTraceID(), header, String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/23 18:22
     * @Description 1017 手机号登录
     */
    @Action(value = "1017")
    public ResultModel loginWithMobile(ClientRequestHeader header, Object body) {
        Result result = loginHandler.loginWithMobile(header.getTraceID(), header, String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * @Author zxh
     * @Description 110，退出登录
     */
    @Action(value = "110", tokenValidated = true)
    public ResultModel userLogout(ClientRequestHeader header, Object body, UserVO user) {
        String userTypeStr = header.getUserType();
        if (StringUtils.isBlank(userTypeStr) || !StringUtils.isNumeric(userTypeStr)) {
            ResultModel rm = new ResultModel();
            rm.setCode(ResultModel.ERROR);
            rm.setMsg("用户类型为数字类型");
            return rm;
        }
        Result result = userInfoHandler.userLogout(header.getTraceID(), String.valueOf(user.getUserid()),
                Integer.valueOf(header.getUserType()), header.getToken());
        return resultToResultModel(result);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/28 10:32
     * @Description 102 用户绑定真实姓名和身份证
     */
    @Action(value = "102", tokenValidated = true)
    public ResultModel bindUserRealityNameAndCertCard(ClientRequestHeader header, Object body, UserVO user) {
        Result result = userInfoHandler.bindUserRealityNameAndCertCard(header.getTraceID(), String.valueOf(user.getUserid()), String.valueOf(body), header);
        return resultToResultModel(result);
    }


    private ResultModel resultToResultModel(Result result) {
        ResultModel rm = new ResultModel();
        BeanUtils.copy(result, rm);
        return rm;
    }

    private Result getFailResult() {
        Result result = new Result();
        result.setCode(Result.ERROR);
        result.setMsg(Result.MSG_ERROR_DESC);
        return result;
    }
}
