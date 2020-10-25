package com.tty.user.service;

import com.jdd.fm.core.model.ResultModel;
import com.tty.common.utils.Result;
import com.tty.user.context.VerifyCodeEnum;
import com.tty.user.controller.model.params.SecurityMsgParams;

/**
 * Created by shenwei on 2017/3/16.
 */
public interface VerifyCodeService {
    /**
     * 获取图形验证码
     */
    ResultModel getCaptcha();

    /**
     * 校验图形验证码
     **/
    boolean checkCaptcha(String captchaKey, String captchaValue);

    /**
     * @Author shenwei
     * @Date 2017/8/8 10:02
     * @Description 获取验证码
     */
    Result getVerifyCode(String userId, String mobile, VerifyCodeEnum verifyCodeEnum, String traceId, String oldMobile);

    /**
     * @Author linian
     * @Date 2018/1/23 15:00
     * @Description 获取验证码
     */
    Result getVerifyCode(String userId, String mobile, VerifyCodeEnum verifyCodeEnum, Integer smsType, String traceId, String oldMobile);

    /**
     * @Author shenwei
     * @Date 2017/8/3 9:48
     * @Description 自动化测试获取验证码
     */
    String testVerifyCode(String type, String userId, String mobile, String traceId);

}
