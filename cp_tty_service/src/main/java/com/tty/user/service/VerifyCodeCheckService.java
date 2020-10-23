package com.tty.user.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;

/**
 * Created by shenwei on 2017/3/20.
 */
public interface VerifyCodeCheckService {

    Result checkBindUserMobile(String verifyCode, String mobile, String userId);

    Result checkUserOldMobile(String verifyCode, String mobile, String userId);

    Result checkLoginBySms(String verifyCode, String mobile);

    Result checkLoginByMobile(String verifyCode, String mobile);

    Result checkForgetLoginPass(String verifyCode, String userId);

    Result checkPCQuickRegister(String verifyCode, String mobile, String traceId, ClientRequestHeader header);

    Result checkQuickRegister(String verifyCode, String mobile, String traceId, ClientRequestHeader header);

    Result checkForgetPayPass(String verifyCode, String userId);

    Result checkCmttVerifyCode(String verifyCode, String userId, String mobile, String passWord, String traceId, ClientRequestHeader header, int from);

    Result checkWapVerifyCode(String verifyCode, String mobile, String traceId, ClientRequestHeader header);

    Result checkWeChatBindVerifyCode(String verifyCode, String mobile, String traceId);

    Result checkHuaweiVerifyCode(String verifyCode,String mobile,String traceId);
}
