package com.tty.user.service.impl;/**
 * Created by shenwei on 2017/3/29.
 */

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.Base64Util;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.token.UserLoginVO;
import com.jdd.fm.core.utils.token.UserTokenUtil;
import com.jdd.fm.core.utils.token.UserVO;
import com.tty.common.utils.Result;
import com.tty.user.common.utils.HttpUtils;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.controller.model.result.TokenLoginResult;
import com.tty.user.dao.UserInfoDao;
import com.tty.user.dao.UserLoginRecordDao;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.dao.entity.UserLoginRecordENT;
import com.tty.user.service.TokenService;
import com.tty.user.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shenwei
 * @create 2017-03-29
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    @Autowired
    @Qualifier("tokenRedis")
    private JedisClusterFactory tokenRedis;
    @Autowired
    private UserLoginRecordDao userLoginRecordDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * @Author shenwei
     * @Date 2017/3/28 10:26
     * @Description verify
     */
    public Result verifyToken(String token, Integer userType, String uuid) {
        Result result = new Result();
        try {
            if (StringUtils.isNotBlank(token)) {
                ResultModel resultModel = verifyToken(token, uuid);
                if (resultModel.getCode() < 0) {
                    result.setCode(resultModel.getCode());
                    result.setMsg(resultModel.getMsg());
                    return result;
                }
                UserVO userToken = UserTokenUtil.getUserByToken(token);
                result.setCode(ResultModel.SUCCESS);
                TokenLoginResult tokenLoginResult = new TokenLoginResult();
                tokenLoginResult.setId(HttpUtils.encodedId(userToken.getUserid()));
                tokenLoginResult.setToken(token);
                tokenLoginResult.setUserType(userType);
                UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(Base64Util.decode(tokenLoginResult.getId()));
                if (userInfoENT != null) {
                    tokenLoginResult.setUserName(userInfoENT.getLoginName());
                }
                result.setData(tokenLoginResult);
                tokenRedis.expire(String.format(UserRedisKeys.USER_LOGIN_TOKEN, userToken.getUserid()), UserContext.TOKEN_LOSE_SECONDS);
                return result;
            }
        } catch (Exception e) {
            logger.error("身份认证出错！Exception={}  token={}", LogExceptionStackTrace.erroStackTrace(e), token);
        }
        result.setCode(ResultModel.ILLEGAL);
        result.setMsg(UserContext.USER_LOGIN_ERROR_MSG);
        return result;
    }

    private ResultModel verifyToken(String token, String uuid) {
        ResultModel rm = new ResultModel();
        UserVO userToken = UserTokenUtil.getUserByToken(token);
        if (userToken == null) {
            rm.setCode(ResultModel.ILLEGAL);
            rm.setMsg(UserContext.USER_LOGIN_ERROR_MSG);
            return rm;
        }
        try {
            if (com.jdd.fm.core.utils.StringUtils.isNotBlank(token)) {
                String key = String.format(UserRedisKeys.USER_LOGIN_TOKEN, userToken.getUserid());
                String cacheToken = tokenRedis.get(key);
                if (!com.jdd.fm.core.utils.StringUtils.isNotBlank(cacheToken)) {
                    rm.setCode(ResultModel.ILLEGAL);
                    rm.setMsg(UserContext.USER_LOGIN_PAST_MSG);
                    return rm;
                }
                UserLoginVO ul = GfJsonUtil.parseObject(cacheToken, UserLoginVO.class);
                if (ul.getToken().equals(token)) {
                    rm.setCode(ResultModel.SUCCESS);
                    rm.setData(userToken);
                    return rm;
                } else {
                    if (!uuid.equals(ul.getUuid())) {
                        rm.setCode(ResultModel.ILLEGAL);
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

    /**
     * @Author shenwei
     * @Date 2017/3/28 20:17
     * @Description token save
     */
    public String saveToken(String userName, String userId, String mobile, Integer userType, ClientRequestHeader header, String loginFrom, Result result, Integer loginType) {
        try {
            UserVO userVO = new UserVO();
            userVO.setUserid(Long.valueOf(userId));
            userVO.setUserType(userType);
            userVO.setUuid(header.getUuid());
            String newToken = UserTokenUtil.createToken(userVO);
            UserLoginVO userLoginVO = new UserLoginVO();
            userLoginVO.setLoginTime(DateUtil.getNowTime());
            userLoginVO.setPhoneName(StringUtils.isBlank(header.getPhoneName()) ? "其他" : header.getPhoneName());
            userLoginVO.setUuid(header.getUuid());
            userLoginVO.setToken(newToken);
            userLoginVO.setLoginType(loginFrom);
            String tokenKey = String.format(UserRedisKeys.USER_LOGIN_TOKEN, userId);
            tokenRedis.set(tokenKey, GfJsonUtil.toJSONString(userLoginVO));
            tokenRedis.expire(tokenKey, UserContext.TOKEN_LOSE_SECONDS);
            TokenLoginResult tokenLoginResult = new TokenLoginResult();
            tokenLoginResult.setId(HttpUtils.encodedId(userId));
            tokenLoginResult.setUserName(userName);
            tokenLoginResult.setToken(newToken);
            tokenLoginResult.setUserType(userType);
            tokenLoginResult.setMobile(mobile);
            tokenLoginResult.setLoginType(loginType); //0其他 1手机号  2用户名 3微信快捷
            threadPoolTaskExecutor.execute(() -> {
                UserLoginRecordENT ent = new UserLoginRecordENT();
                ent.setUserId(Long.valueOf(userId));
                ent.setLoginTime(new Date());
                ent.setCmdName(header.getCmdName());
                ent.setAppVersion(header.getAppVersion());
                ent.setPhoneType(header.getPlatformCode());
                ent.setOsVersion(header.getPlatformVersion());
                ent.setPhoneType(StringUtils.isBlank(header.getPhoneName()) ? "其他" : header.getPhoneName());
                loginRecord(ent);
                userInfoDao.updateUserLastLoginTime(userId, new Date());
            });
            result.setCode(Result.SUCCESS);
            result.setData(tokenLoginResult);
            result.setMsg("登陆成功");
            return newToken;
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg("登陆异常");
            logger.error("stackTrace:{},traceID={}", LogExceptionStackTrace.erroStackTrace(e), header.getTraceID());
        }
        return null;
    }

    /**
     * 记录用户登陆时间
     *
     * @param userLoginRecordENT
     */
    @Override
    public void loginRecord(UserLoginRecordENT userLoginRecordENT) {
        userLoginRecordDao.saveUserLoginRecord(userLoginRecordENT);
        userRedis.hset(UserRedisKeys.USER_LAST_LOGIN_TIME, userLoginRecordENT.getUserId().toString(), GfJsonUtil.toJSONString(userLoginRecordENT));
    }

    /**
     * @Author shenwei
     * @Date 2017/4/3 17:41
     * @Description 服务端token清除
     */
    @Override
    public void emptyToken(String userId) {
        tokenRedis.del(String.format(UserRedisKeys.USER_LOGIN_TOKEN, userId));
    }
}
