package com.tty.user.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;
import com.tty.user.dao.ent.UserLoginRecord;

/**
 * @author
 * @create 2017-03-29
 */
public interface TokenService {

    Result verifyToken(String token, Integer userType, String uuid);

    String saveToken(String userName, String userId, String mobile, Integer userType, ClientRequestHeader header, String loginFrom, Result result, Integer loginType);

    void loginRecord(UserLoginRecord userLoginRecord);

    void emptyToken(String userId);
}
