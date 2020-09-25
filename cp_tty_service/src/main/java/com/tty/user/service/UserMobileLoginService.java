package com.tty.user.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;

/**
 * Created by shenwei on 2017/3/24.
 */
public interface UserMobileLoginService {
    Result login(String mobile, String passWord, String token, Integer userType, String traceId, ClientRequestHeader header, String thirdId);

    Result loginWithMobile(String mobile, String passWord, String traceId, ClientRequestHeader header);

    Result loginWithOnlyMobile(String mobile, String passWord, String token, Integer userType, String traceId, ClientRequestHeader header, String thirdId);

    Result loginWithOnlyMobile(String mobile, String passWord, String traceId, ClientRequestHeader header);

}
