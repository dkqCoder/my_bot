package com.tty.user.service.handler;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;
import com.tty.user.controller.model.params.LoginParams;

/**
 * @author ln
 */
public interface LoginHandler {

    Result login(String traceId, String uuid, ClientRequestHeader header, LoginParams loginParams);

    Result getLoginBySmsVerifyCode(String traceId, String params);

    Result loginWithSmsVerifyCode(ClientRequestHeader header, LoginParams loginParams);

    Result loginWithMobile(String traceId, ClientRequestHeader header, String params);

    Result chooseUserDefaultLoginAccounts(String traceId, ClientRequestHeader header, String params);
}
