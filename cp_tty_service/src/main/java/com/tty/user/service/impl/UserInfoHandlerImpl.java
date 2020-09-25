package com.tty.user.service.impl;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.common.utils.HttpUtils;
import com.tty.common.utils.MobileUtil;
import com.tty.common.utils.Result;
import com.tty.user.context.UserContext;
import com.tty.user.context.VerifyCodeEnum;
import com.tty.user.dao.ent.UserInfoENT;
import com.tty.user.params.*;
import com.tty.user.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by donne on 2017/3/14.
 */
@Component("userInfoHandler")
public class UserInfoHandlerImpl implements UserInfoHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoHandlerImpl.class);

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserBaseInfoService userBaseInfoService;

    @Autowired
    private TokenService tokenService;


    public Result getBindMobileVerifyCode(String traceId, String userID, String params) {
        Result result = new Result();
        BindMobileVerifyParam bindMobileVerifyParam = GfJsonUtil.parseObject(params, BindMobileVerifyParam.class);
        if (bindMobileVerifyParam.getType() != null && bindMobileVerifyParam.getType() == 1) {
            String oldMobile = bindMobileVerifyParam.getOldMobile();
            return verifyCodeService.getVerifyCode(userID, "", VerifyCodeEnum.VALIDATEOLDMOBILE, traceId, oldMobile);
        }
        if (bindMobileVerifyParam == null) {
            logger.error("绑定手机获取验证码失败,参数错误,userId:{},params:{},traceId:{}", userID, params, traceId);
            return getFailResult();
        }
        if (StringUtils.isBlank(bindMobileVerifyParam.getMobile())) {
            result.setCode(Result.ERROR);
            result.setMsg("号码不能为空");
            return result;
        }
        if (!MobileUtil.isMobileNO(bindMobileVerifyParam.getMobile())) {
            result.setCode(Result.ERROR);
            result.setMsg("手机号码格式不正确");
            return result;
        }
        String oldMobile = bindMobileVerifyParam.getOldMobile();
        String mobile = bindMobileVerifyParam.getMobile().trim();
        return verifyCodeService.getVerifyCode(userID, mobile, VerifyCodeEnum.BINDMOBILE, traceId, oldMobile);
    }

    public Result bindUserMobile(String traceId, String userID, String params, ClientRequestHeader header) {
        Result result = new Result();
        BindMobileParams bindMobileParams = GfJsonUtil.parseObject(params, BindMobileParams.class);
        if (bindMobileParams == null) {
            logger.error("绑定手机失败,参数格式错误,userId:{},params:{},traceId:{}", userID, params, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("参数格式错误");
            return result;
        }
        return userInfoService.bindUserMobile(userID, bindMobileParams.getVerifyCode(), traceId, header, bindMobileParams.getType());
    }

    public Result bindUserRealityNameAndCertCard(String traceId, String userID, String params, ClientRequestHeader header) {
        RealityNameIDCardParams realityNameIDCardParams = GfJsonUtil.parseObject(params, RealityNameIDCardParams.class);
        if (realityNameIDCardParams == null) {
            return getFailResult();
        }
        String realityName = "";
        if (realityNameIDCardParams.getRealityName() != null) {
            realityName = realityNameIDCardParams.getRealityName().trim();
        }
        String idCard = realityNameIDCardParams.getIdCard();
        return userBaseInfoService.bindUserRealityNameAndCertCard(userID, realityName, idCard, traceId, header);

    }

    public Result changeUserPassword(String traceId, String userID, String params) {
        Result result = new Result();
        PasswordParams passwordParams = GfJsonUtil.parseObject(params, PasswordParams.class);
        if (passwordParams == null) {
            logger.error("修改密码失败,params:{} traceId:{}", params, traceId);
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            return result;
        }
        String oldPwd = passwordParams.getOldPw();
        String newPwd = passwordParams.getNewPw();
        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
            result.setCode(Result.ERROR);
            result.setMsg("密码不能为空");
            return result;
        }
        if (newPwd.length() < UserContext.passMinLength || newPwd.length() > UserContext.passMaxLength) {
            result.setCode(Result.ERROR);
            result.setMsg("密码长度应在6-密码长度6-15位,请重新输入");
            return result;
        }
        if (newPwd.equals("123456") || newPwd.equals("111111") || newPwd.equals("abcdef")) {
            result.setCode(Result.ERROR);
            result.setMsg("密码过于简单");
            return result;
        }
        if (newPwd.equals(oldPwd)) {
            result.setCode(Result.ERROR);
            result.setMsg("新旧密码相同,请重新设置新密码");
            return result;
        }
        if (newPwd != newPwd.trim()) {
            result.setCode(Result.ERROR);
            result.setMsg("密码含有特殊字符,请输入6-15位数字、字母");
            return result;
        }
        return userInfoService.changeUserPassword(userID, oldPwd, newPwd, traceId);
    }

    public Result resetPassword(String traceId, String params, ClientRequestHeader header) {
        Result result = new Result();
        ResetPwdParam resetPwdParam = GfJsonUtil.parseObject(params, ResetPwdParam.class);
        if (null == resetPwdParam) {
            logger.error("修改密码失败,params:{} traceId:{}", params, traceId);
            return getFailResult();
        }
        Boolean needCheckValidateCode = false;
        if (resetPwdParam.getType() == null || resetPwdParam.getType() == 0) {
            needCheckValidateCode = true;
        }
        if (StringUtils.isBlank(resetPwdParam.getPassword())) {
            logger.error("密码不能为空,params:{} traceId:{}", params, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("密码输入不可为空,请重新输入");
            return result;
        }
        String realUserId = "";
        if (needCheckValidateCode) {
            if (!userInfoService.checkValidateCode(resetPwdParam.getMobileNumber())) {
                logger.error("验证码验证失败,params:{} traceId:{}", params, traceId);
                result.setCode(Result.ERROR);
                result.setMsg("验证码输入错误,请重新输入");
                return result;
            }
            List<UserInfoENT> list = userInfoService.getUserInfoByMobile(resetPwdParam.getMobileNumber());
            if (CollectionUtils.isEmpty(list)) {
                result.setCode(Result.ERROR);
                result.setMsg("该手机号尚未绑定用户,请重新输入");
                return result;
            }
            if (CollectionUtils.isNotEmpty(list) && list.size() > 1) {
                String defaultName = userInfoService.getUserDefaultName(resetPwdParam.getMobileNumber(), traceId);
                if (StringUtils.isBlank(defaultName)) {
                    result.setCode(Result.ERROR);
                    result.setMsg("存在多账号,请先选择默认用户");
                    return result;
                }
                UserInfoENT userInfoENT = userInfoService.getUserInfoByLoginName(defaultName);
                if (userInfoENT == null) {
                    logger.warn("1061 根据默认用户名未获取到用户 traceId:{},defaultName:{}", traceId, defaultName);
                }
                realUserId = String.valueOf(userInfoENT.getUserId());
            } else {
                realUserId = String.valueOf(list.get(0).getUserId());
            }
        } else {
            result = tokenService.verifyToken(header.getToken(), resetPwdParam.getType(), header.getUuid());
            if (result.getCode() < 0) {
                return result;
            }
            TokenLoginResult tokenLoginResult = (TokenLoginResult) result.getData();
            realUserId = HttpUtils.decode(tokenLoginResult.getId());
        }
        return userInfoService.changeUserPassword(realUserId, resetPwdParam.getPassword(), traceId);
    }

    public Result userLogout(String traceId, String userId, Integer userType, String token) {
        return userInfoService.userLoginOut(userId, userType, token, traceId);
    }

    private Result getFailResult() {
        Result result = new Result();
        result.setCode(Result.ERROR);
        result.setMsg(Result.MSG_ERROR_DESC);
        return result;
    }
}
