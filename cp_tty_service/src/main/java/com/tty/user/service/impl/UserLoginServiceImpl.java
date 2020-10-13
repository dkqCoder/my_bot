package com.tty.user.service.impl;

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.HttpUtils;
import com.tty.common.utils.Result;
import com.tty.user.service.TokenService;
import com.tty.user.service.UserLoginService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userLoginService")
public class UserLoginServiceImpl  implements UserLoginService {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    @Autowired
    private TokenService tokenService;


    public Result login(String token, String userName, String password, String userId, String uuid, Integer userType, String traceId, ClientRequestHeader header) {
        Result result = new Result();
        try {
            Boolean carryToken = StringUtils.isNotBlank(token);
            if (carryToken) {
                result = tokenService.verifyToken(token, userType, header.getUuid());
                return  result;
            }
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("用户登录（包括第三方登录）失败，traceId:{},userid:{},errorStack:{}", traceId, userId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }
}
