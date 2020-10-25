package com.tty.user.service.impl;/**
 * Created by shenwei on 2017/3/29.
 */

import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.tty.common.utils.Result;
import com.tty.user.common.utils.HttpUtils;
import com.tty.user.common.utils.MD5Utils;
import com.tty.user.context.LoginTypeEnum;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.UserInfoDao;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.service.TokenService;
import com.tty.user.service.UserInfoService;
import com.tty.user.service.UserLoginService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shenwei
 * @create 2017-03-29
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoDao userInfoDao;


    /**
     * @Author shenwei
     * @Date 2017/3/8 10:43
     * @Description 1011 用户登录(包含第三方登录)
     */
    public Result login(String token, String userName, String password, String userId, String uuid, Integer userType, String traceId, ClientRequestHeader header) {
        Result result = new Result();
        try {
            Boolean carryToken = StringUtils.isNotBlank(token);
            if (carryToken) { // 携带token则直接验证token
                result = tokenService.verifyToken(token, userType, header.getUuid());
                if (result.getCode() < 0) {
                    return result;
                } else {
                    threadPoolTaskExecutor.execute(() -> {
                        userInfoDao.updateUserLastLoginTime(HttpUtils.decode(userId), new Date());
                    });
                    return result;
                }
            }
            if (!carryToken) {
                if (LoginTypeEnum.APP.getValue().equals(userType)) {
                    loginWithNamePwd(userName, password, false, header, result, 2);
                } else {
                    result.setCode(Result.ERROR);
                    result.setData("不存在的登录方式");
                }

            }
            return result;
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("用户登录（包括第三方登录）失败，traceId:{},userid:{},errorStack:{}", traceId, userId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/28 20:44
     * @Description 用户名密码登录
     */
    public void loginWithNamePwd(String userName, String password, Boolean pwdEncrypted, ClientRequestHeader header, Result result, Integer loginType) {
        try {
            if (userName == null || password == null) {
                result.setCode(Result.ERROR);
                result.setMsg("请输入用户名密码");
                return;
            }
            UserInfoENT userInfoENT = userInfoService.getUserInfoByLoginName(userName);
            if (userInfoENT == null) {
                result.setCode(Result.ERROR);
                result.setMsg("您好，该手机号码或用户名还未注册过账户，请先完成注册");
                return;
            }
            if (userInfoENT.getStatus() != null && userInfoENT.getStatus() == 0) {
                result.setCode(Result.ERROR);
                result.setMsg(UserContext.USER_LOGIN_FORBIDDEN);
                return;
            }
            if (isUserFreeze(result, userInfoENT)) {
                return;
            }
            String encryptPassword = pwdEncrypted ? password : MD5Utils.encrypt(password, UserContext.MD5Key);
            if (passWordCheck(result, userInfoENT, encryptPassword)) {
                return;
            }
            tokenService.saveToken(userInfoENT.getLoginName(), String.valueOf(userInfoENT.getUserId()), userInfoENT.getMobileNumber(), LoginTypeEnum.APP.getValue(), header, "用户名", result, loginType);
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("用户名密码登录异常stackTrace:{} traceId:{}", LogExceptionStackTrace.erroStackTrace(e));
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/3/30 16:53
     * @Description 用户是否被冻结
     */
    private boolean isUserFreeze(Result result, UserInfoENT userInfoENT) throws ParseException {
        if (userRedis.exists(String.format(UserRedisKeys.USER_LOGIN_ERROR_FREEZE_USERID, userInfoENT.getUserId()))) {
            String freezeTime = userRedis.get(String.format(UserRedisKeys.USER_LOGIN_ERROR_FREEZE_USERID, userInfoENT.getUserId()));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long freezeMinutes = (System.currentTimeMillis() - format.parse(freezeTime).getTime()) / 1000 / 60;
            if (freezeMinutes < UserContext.FREEZEMINUTES) {
                result.setCode(-10);
                result.setMsg(String.format("密码多次错误,冻结登录,%s分钟后解冻", UserContext.FREEZEMINUTES - freezeMinutes));
                return true;
            }
        }
        return false;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/30 16:51
     * @Description 密码校验
     */
    private boolean passWordCheck(Result result, UserInfoENT userInfoENT, String passWord) {
        if (!passWord.equals(userInfoENT.getPassword())) {
            Integer errorCount = 0;
            String errorSize = userRedis.get(String.format(UserRedisKeys.USER_LOGIN_ERROR_COUNT_USERID, userInfoENT.getUserId()));
            errorCount = errorSize == null ? 0 : Integer.valueOf(errorSize);
            if (errorCount >= UserContext.LOGIN_FAIL_MAX) {
                String freezeTimeCacheKey = String.format(UserRedisKeys.USER_LOGIN_ERROR_FREEZE_USERID, userInfoENT.getUserId());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userRedis.set(freezeTimeCacheKey, format.format(new Date()));
                userRedis.expire(freezeTimeCacheKey, UserContext.FREEZEMINUTES * 60);
                result.setCode(Result.ERROR);
                result.setMsg("密码错误次数达到5次,帐户冻结30分钟!");
                return true;
            } else {
                errorCount++;
                userRedis.set(String.format(UserRedisKeys.USER_LOGIN_ERROR_COUNT_USERID, userInfoENT.getUserId()), errorCount.toString());
                userRedis.expire(String.format(UserRedisKeys.USER_LOGIN_ERROR_COUNT_USERID, userInfoENT.getUserId()), UserContext.FREEZEMINUTES * 60);
                result.setCode(Result.ERROR);
                result.setMsg("密码错误,您还有" + (UserContext.LOGIN_FAIL_MAX - errorCount) + "次尝试机会");
                return true;
            }
        }
        userRedis.del(String.format(UserRedisKeys.USER_LOGIN_ERROR_COUNT_USERID, userInfoENT.getUserId()));
        return false;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/30 19:14
     * @Description 用户选择默认用户名登录
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result chooseUserDefaultLoginAccounts(String userName, String mobile, String uuid, String traceId, ClientRequestHeader header) {
        Result result = new Result();
        if (StringUtils.isEmpty(userName)) {
            result.setCode(Result.ERROR);
            result.setMsg("用户名为空");
            return result;
        }
        if (StringUtils.isEmpty(mobile)) {
            result.setCode(Result.ERROR);
            result.setMsg("手机号为空");
            return result;
        }
        //短信验证码登录验证通过
        String value = userRedis.get(String.format(UserRedisKeys.USER_SMS_LOGIN_VERIFY_CODE_VALIDE, mobile));
        if (StringUtils.isEmpty(value)) {
            result.setCode(Result.ERROR);
            result.setMsg("未获取到验证通过状态");
            return result;
        }
        UserInfoENT userInfo = userInfoService.getUserInfoByLoginName(userName);
        if (userInfo == null) {
            result.setCode(Result.ERROR);
            result.setMsg("用户名不存在");
            return result;
        }
        tokenService.saveToken(userName, String.valueOf(userInfo.getUserId()), mobile, LoginTypeEnum.APP.getValue(), header, "短信验证码", result, 0);
        userRedis.hset(UserRedisKeys.USER_DEFAULT_REGISTER_NAME, mobile, userName);
        return result;
    }
}
