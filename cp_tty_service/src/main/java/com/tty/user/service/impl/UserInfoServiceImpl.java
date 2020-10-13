package com.tty.user.service.impl;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;
import com.tty.user.dao.ent.UserInfoENT;
import com.tty.user.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userInfoService")
public class UserInfoServiceImpl   implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    public Result register(String userName, Integer registerType, String passWord, Integer smsType, String traceId, ClientRequestHeader header) {
        return null;
    }

    public Long registerByUserNameAndPwd(String userName, String mobile, String entrance, String password, String traceId, ClientRequestHeader header, Result result) {
        return null;
    }

    public Result bindUserMobile(String userId, String verifyCode, String traceId, ClientRequestHeader header, Integer type) {
        return null;
    }

    public Result userLoginOut(String userId, Integer userType, String token, String traceId) {
        return null;
    }

    public Result changeUserPassword(String userId, String oldPwd, String newPwd, String traceId) {
        return null;
    }

    public Result changeUserPayPassword(String userId, String oldPwd, String newPwd, String traceId) {
        return null;
    }

    public Result changeUserPayPassword(String userId, String newPwd, String traceId) {
        return null;
    }

    public void loginWithNamePwd(String userName, String password, Boolean pwdEncrypted, ClientRequestHeader header, Result result, Integer loginType) {

    }

    @Override
    public Boolean checkValidateCode(String mobile) {
        return null;
    }

    @Override
    public List<UserInfoENT> getUserInfoByMobile(String mobile) {
        return null;
    }

    @Override
    public String getUserDefaultName(String mobile, String traceId) {
        return null;
    }

    @Override
    public UserInfoENT getUserInfoByLoginName(String loginName) {
        return null;
    }

    @Override
    public Result changeUserPassword(String userId, String newPwd, String traceId) {
        return null;
    }

    @Override
    public UserInfoENT getCurrentUserInfo(String userId) {
        return null;
    }
}
