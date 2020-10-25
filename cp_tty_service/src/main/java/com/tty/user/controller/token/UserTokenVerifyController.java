package com.tty.user.controller.token;


import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.StringUtils;
import com.jdd.fm.core.utils.token.UserLoginVO;
import com.jdd.fm.core.utils.token.UserTokenUtil;
import com.jdd.fm.core.utils.token.UserVO;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserRedisKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author chenlongfei
 * @date 2017-03-13
 * @description 验证用户token
 * code=0 验证通过
 * code=-2 验证不通过
 */
@RestController
@RequestMapping("/user/protected/userToken")
public class UserTokenVerifyController {
    private final Logger logger = LoggerFactory.getLogger(UserTokenVerifyController.class);
    @Autowired
    @Qualifier("tokenRedis")
    private JedisClusterFactory tokenRedis;

    @RequestMapping(value = "/verifyToken", method = RequestMethod.POST)
    public ResultModel verifyToken(String token, String uuid) {
        ResultModel rm = new ResultModel();
        String traceId = UUID.randomUUID().toString();
        UserVO userToken = UserTokenUtil.getUserByToken(token);
        if (userToken == null) {
            logger.error("traceId={} 校验token错误token={}", traceId, token);
            rm.setCode(ResultModel.ILLEGAL);
            rm.setMsg(UserContext.USER_LOGIN_ERROR_MSG);
            return rm;
        }
        try {
            if (StringUtils.isNotBlank(token)) {
                String key = String.format(UserRedisKeys.USER_LOGIN_TOKEN, userToken.getUserid());
                String cacheToken = tokenRedis.get(key);
                if (!StringUtils.isNotBlank(cacheToken)) {
                    rm.setCode(ResultModel.ILLEGAL);
                    rm.setMsg(UserContext.USER_LOGIN_PAST_MSG);
                    return rm;
                }
                UserLoginVO ul = GfJsonUtil.parseObject(cacheToken, UserLoginVO.class);
                if (ul.getToken().equals(token)) {
                    tokenRedis.expire(key, UserContext.TOKEN_LOSE_SECONDS);
                    rm.setCode(ResultModel.SUCCESS);
                    rm.setData(userToken);
                    return rm;
                } else {
                    if (!uuid.equals(ul.getUuid())) {
                        rm.setCode(-97);
                        rm.setMsg(String.format(UserContext.USER_LOGIN_WRAN_MSG, ul.getLoginTime(), ul.getPhoneName(), ul.getLoginType()));
                        return rm;
                    }
                }
            } else {
                rm.setCode(ResultModel.ILLEGAL);
                rm.setMsg(UserContext.USER_LOGIN_PAST_MSG);
                return rm;
            }
        } catch (Exception e) {
            logger.error("身份认证出错！Exception={}  token={}", LogExceptionStackTrace.erroStackTrace(e), token);
        }
        rm.setCode(ResultModel.ILLEGAL);
        rm.setMsg(UserContext.USER_LOGIN_PAST_MSG);
        return rm;
    }


}
