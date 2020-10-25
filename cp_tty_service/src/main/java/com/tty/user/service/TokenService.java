package com.tty.user.service;/**
 * Created by shenwei on 2017/3/29.
 */

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;
import com.tty.user.dao.entity.UserLoginRecordENT;

/**
 * @author shenwei
 * @create 2017-03-29
 */
public interface TokenService {
    Result verifyToken(String token, Integer userType, String uuid);

    String saveToken(String userName, String userId, String mobile, Integer userType, ClientRequestHeader header, String loginFrom, Result result, Integer loginType);

    void loginRecord(UserLoginRecordENT userLoginRecordENT);

    void emptyToken(String userId);
}
