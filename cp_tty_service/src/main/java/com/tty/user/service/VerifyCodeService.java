package com.tty.user.service;

import com.tty.common.utils.Result;
import com.tty.user.context.VerifyCodeEnum;

/**
 * Created by shenwei on 2017/3/16.
 */
public interface VerifyCodeService {

    /**
     * @Author shenwei
     * @Date 2017/8/8 10:02
     * @Description 获取验证码
     */
    Result getVerifyCode(String userId, String mobile, VerifyCodeEnum verifyCodeEnum, String traceId, String oldMobile);

}
