package com.tty.user.service;/**
 * Created by shenwei on 2017/3/28.
 */

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;

/**
 * @author shenwei
 * @create 2017-03-28
 */
public interface UserSmsLoginService {
    Result login(String mobile, String verifyCode, ClientRequestHeader header);

    Result login(String mobile, ClientRequestHeader header);

    Result loginWithoutVerifyCode(String traceId, ClientRequestHeader header, String params);

    boolean isUserFreeze(String userId, Result result);
}
