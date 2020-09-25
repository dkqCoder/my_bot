package com.tty.user.service.impl;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;
import com.tty.user.dao.ent.UserLoginRecord;
import com.tty.user.service.TokenService;
import org.springframework.stereotype.Service;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {
    public Result verifyToken(String token, Integer userType, String uuid) {
        return null;
    }

    public String saveToken(String userName, String userId, String mobile, Integer userType, ClientRequestHeader header, String loginFrom, Result result, Integer loginType) {
        return null;
    }

    public void loginRecord(UserLoginRecord userLoginRecord) {

    }

    public void emptyToken(String userId) {

    }
}
