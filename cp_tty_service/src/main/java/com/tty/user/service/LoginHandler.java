package com.tty.user.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;

/**
 * @author ln
 */
public interface LoginHandler {

    Result login(String traceId, String uuid, ClientRequestHeader header, String params);

    Result getLoginBySmsVerifyCode(String traceId, String params);

    Result loginWithSmsVerifyCode(String traceId, ClientRequestHeader header, String params);

    Result loginWithMobile(String traceId, ClientRequestHeader header, String params);
}
