package com.tty.user.service;


import com.tty.user.context.VerifyCodeEnum;

/**
 * Created by shenwei on 2017/3/29.
 */
public interface SmsService {
    Boolean sendVerifyCode(Integer siteId, String mobile, VerifyCodeEnum verifyCodeEnum, String userId, String verifyCode, Integer smsType, String traceId);

}
