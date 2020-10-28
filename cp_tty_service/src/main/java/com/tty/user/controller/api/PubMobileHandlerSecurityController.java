package com.tty.user.controller.api;

import com.jdd.fm.core.annotations.Action;
import com.jdd.fm.core.annotations.ActionController;
import com.jdd.fm.core.base.APIBaseController;
import com.jdd.fm.core.ehcache.EhcacheManager;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.token.UserVO;
import com.jdd.fm.core.utils.web.WebActionDispatcher;
import com.tty.common.utils.BeanUtils;
import com.tty.common.utils.HttpUtils;
import com.tty.common.utils.Result;
import com.tty.user.controller.model.params.LoginParams;
import com.tty.user.service.VerifyCodeCheckService;
import com.tty.user.service.VerifyCodeService;
import com.tty.user.service.handler.LoginHandler;
import com.tty.user.service.handler.UserInfoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

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
    private EhcacheManager ehcacheManager;

    @Autowired
    private LoginHandler loginHandler;

    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private VerifyCodeCheckService verifyCodeCheckService;

    @RequestMapping(value = "/router")
    public ResultModel router(HttpServletRequest request) {
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
     * 获取图形验证码
     */
    @Action(value = "12001")
    public ResultModel getCAPTCHA(ClientRequestHeader header, Object body) {
        return verifyCodeService.getCaptcha();
    }

    /**
     * 发送手机注册验证码短信
     */
    @Action(value = "12002")
    public ResultModel sendRegisterCode(ClientRequestHeader header, Object body) {
        Result result = userInfoHandler.getQuickRegisterVerifyCode(header.getTraceID(), String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * 用户注册
     */
    @Action(value = "12003")
    public ResultModel register(ClientRequestHeader header, Object body) {
        Result result = verifyCodeCheckService.checkQuickRegister(String.valueOf(body), header);
        return resultToResultModel(result);
    }

    /**
     * 发送安全认证短信(登陆)
     */
    @Action(value = "12004")
    public ResultModel getLoginBySmsVerifyCode(ClientRequestHeader header, Object body) {
        Result result = loginHandler.getLoginBySmsVerifyCode(header.getTraceID(), String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * 发送安全认证短信(手机换绑)
     */
    @Action(value = "12005", tokenValidated = true)
    public ResultModel getBindMobileVerifyCode(ClientRequestHeader header, Object body, UserVO userVO) {
        Result result = userInfoHandler.getBindMobileVerifyCode(header.getTraceID(), userVO.getUserid().toString(), String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * 发送安全认证短信(忘记密码)
     */
    @Action(value = "12006")
    public ResultModel getForgetPassVerifyCode(ClientRequestHeader header, Object body) {
        Result result = userInfoHandler.getForgetPassVerifyCode(header.getTraceID(), String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * 发送安全认证短信(设置支付密码)
     */
    @Action(value = "12007", tokenValidated = true)
    public ResultModel getForgetPayPassVerifyCode(ClientRequestHeader header, Object body, UserVO userVO) {
        Result result = userInfoHandler.getForgetPayPassVerifyCode(header.getTraceID(), userVO.getUserid().toString(), String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * 用户登陆(验证码登陆、密码登陆)
     */
    @Action(value = "12008")
    public ResultModel login(ClientRequestHeader header, Object body) {
        String traceId = header.getTraceID();
        Result result;
        LoginParams loginParams = GfJsonUtil.parseObject(String.valueOf(body), LoginParams.class);
        if (loginParams == null) {
            logger.error("[用户登录] 参数错误 params:{} traceId:{}", String.valueOf(body), traceId);
            return getFailResult("参数错误");
        }
        if (loginParams.getLoginType() == 1) { // 用户名密码登陆
            result = loginHandler.login(traceId, header.getUuid(), header, loginParams);
        } else if (loginParams.getLoginType() == 2) { // 手机验证码登陆
            result = loginHandler.loginWithSmsVerifyCode(header, loginParams);
        } else {
            return getFailResult("不支持的登陆类型");
        }
        return resultToResultModel(result);
    }


    /**
     * 登陆密码设置
     * 第一次设定无需填写旧密码  第一次设定后必须旧密码正确才可修改新密码
     */
    @Action(value = "12009", tokenValidated = true)
    public ResultModel setPassword(ClientRequestHeader header, Object body, UserVO userVO) {
        Result result = userInfoHandler.changeUserPassword(header.getTraceID(), userVO.getUserid().toString(), String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * 支付密码设置(先获取短信验证码，凭短信验证码设置支付密码)
     */
    @Action(value = "12010", tokenValidated = true)
    public ResultModel setPayPassword(ClientRequestHeader header, Object body, UserVO userVO) {
        Result result = userInfoHandler.changeUserPayPassword(header.getTraceID(), userVO.getUserid().toString(), String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * 找回密码(先获取短信验证码，凭短信验证码修改密码)
     */
    @Action(value = "12011")
    public ResultModel findPwd(ClientRequestHeader header, Object body) {
        Result result = userInfoHandler.resetPassword(header.getTraceID(), String.valueOf(body), header);
        return resultToResultModel(result);
    }

    /**
     * 手机换绑
     */
    @Action(value = "12012", tokenValidated = true)
    public ResultModel changPhoneNo(ClientRequestHeader header, Object body,  UserVO userVO) {
        Result result = userInfoHandler.bindUserMobile(header.getTraceID(), userVO.getUserid().toString(), String.valueOf(body), header);
        return resultToResultModel(result);
    }

    /**
     * 获取实名认证信息
     */
    @Action(value = "12013", tokenValidated = true)
    public ResultModel getUserInfo(ClientRequestHeader header, Object body, UserVO userVO) {
        Result result = userInfoHandler.getBasicUserInfo(header.getTraceID(), header, String.valueOf(body), userVO.getUserid().toString());
        return resultToResultModel(result);
    }

    /**
     * 129 添加用户头像
     *
     * @return 129
     */
    @Action(value = "12014", tokenValidated = true, request = true)
    public ResultModel addUserFacePic(ClientRequestHeader header, Object body, HttpServletRequest request, UserVO user) {
        String bfile = request.getParameter("bfile");
        String fileName = request.getParameter("bfilename");
        Result result = new Result();
        if (bfile != null) {
            result = userInfoHandler.addUserFacePic(HttpUtils.getTraceId(request), null, bfile, fileName, user.getUserid().toString());
        } else {
            MultipartHttpServletRequest mr = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            if (mr == null) {
                result.setCode(Result.ERROR);
                result.setMsg("上传图片为空");
                logger.warn("上传图片为空，userId:{}", user.getUserid().toString());
            }
            MultipartFile file = mr.getFile("file");
            result = userInfoHandler.addUserFacePic(HttpUtils.getTraceId(request), file, null, null, user.getUserid().toString());
        }
        return resultToResultModel(result);
    }

    /**
     * 修改昵称
     */
    @Action(value = "12015", tokenValidated = true)
    public ResultModel editNickName(ClientRequestHeader header, Object body, UserVO userVO) {
        Result result = userInfoHandler.editNickName(header.getTraceID(), userVO.getUserid().toString(), String.valueOf(body));
        return resultToResultModel(result);
    }

    /**
     * 实名认证
     */
    @Action(value = "12016", tokenValidated = true)
    public ResultModel authentication(ClientRequestHeader header, Object body, UserVO userVO) {
        Result result = userInfoHandler.bindUserRealityNameAndCertCard(header.getTraceID(), userVO.getUserid().toString(), String.valueOf(body), header);
        return resultToResultModel(result);
    }

    /**
     * 退出登录
     */
    @Action(value = "12017", tokenValidated = true)
    public ResultModel userLogout(ClientRequestHeader header, Object body, UserVO user) {
        Result result = userInfoHandler.userLogout(header.getTraceID(), user.getUserid().toString(), header.getToken());
        return resultToResultModel(result);
    }

    private ResultModel resultToResultModel(Result result) {
        ResultModel rm = new ResultModel();
        BeanUtils.copy(result, rm);
        return rm;
    }

    private ResultModel getFailResult(String msg) {
        ResultModel result = new ResultModel();
        result.setCode(Result.ERROR);
        result.setMsg(msg);
        return result;
    }

    private ResultModel getFailResult() {
        ResultModel result = new ResultModel();
        result.setCode(Result.ERROR);
        result.setMsg(Result.MSG_ERROR_DESC);
        return result;
    }

}
