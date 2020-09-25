package com.tty.user.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;

public interface UserInfoHandler {

    Result getBindMobileVerifyCode(String traceId, String userID, String params);

    Result bindUserMobile(String traceId, String userID, String params, ClientRequestHeader header);

    Result bindUserRealityNameAndCertCard(String traceId, String userID, String params, ClientRequestHeader header);

    Result changeUserPassword(String traceId, String userID, String params);

    Result resetPassword(String traceId, String params, ClientRequestHeader header);

    Result userLogout(String traceId, String userId, Integer userType, String token);
}
