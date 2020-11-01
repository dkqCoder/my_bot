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
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: sunyishun
 * @Date: 2020/11/1
 * @Description:
 */
@Service("userTokenVerifyService")
public class UserTokenVerifyService {

    @Autowired
    @Qualifier("tokenRedis")
    private JedisClusterFactory tokenRedis;

    private final Logger logger = LoggerFactory.getLogger(UserTokenVerifyService.class);
    public ResultModel verifyToken(String token, String uuid){
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
