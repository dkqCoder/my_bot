package com.tty.user.service.impl;/**
 * Created by shenwei on 2017/3/28.
 */

import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.common.utils.MobileUtil;
import com.tty.common.utils.Result;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.ent.UserInfoENT;
import com.tty.user.params.LoginSmsParams;
import com.tty.user.service.TokenService;
import com.tty.user.service.UserInfoService;
import com.tty.user.service.UserSmsLoginService;
import com.tty.user.service.VerifyCodeCheckService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shenwei
 * @create 2017-03-28
 */
@Service("userSmsLoginService")
public class UserSmsLoginServiceImpl implements UserSmsLoginService {

    private static final Logger logger = LoggerFactory.getLogger(UserSmsLoginServiceImpl.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private VerifyCodeCheckService verifyCodeCheckService;
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;

    /**
     * @Author shenwei
     * @Date 2017/3/20 11:50
     * @Description 1016 短信验证码登录
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result login(String mobile, String verifyCode, ClientRequestHeader header) {
        Result result = new Result();
        result = verifyCodeCheckService.checkLoginBySms(verifyCode, mobile);
        if (result.getCode() < 0) {
            return result;
        }
        /*List<UserInfoENT> list = userInfoService.refreshUserInfoByMobile(mobile);
        if (CollectionUtils.isEmpty(list)) {
            result.setCode(Result.ERROR);
            result.setMsg("该手机号没有绑定任何账号,请联系客服或进行注册");
            return result;
        }
        Boolean hasMoreThanOneUser = list.size() > 1;
        if (hasMoreThanOneUser) {
            userInfoService.userListByLoginTime(list, result);
            return result;
        }*/
        /*UserInfoENT ent = userInfoService.getCurrentUserInfo(String.valueOf(list.get(0).getUserId()));
        if (ent == null) {
            result.setCode(Result.ERROR);
            result.setMsg("该手机号没有绑定任何账号,请联系客服或进行注册");
            return result;
        }
        if (ent.getStatus() != null && ent.getStatus() == 0) {
            result.setCode(Result.NOTLOGIN);
            result.setMsg(UserContext.USER_LOGIN_FORBIDDEN);
            return result;
        }
        if (isUserFreeze(String.valueOf(ent.getUserId()), result)) {
            return result;
        }
        tokenService.saveToken(ent.getLoginName(), ent.getUserId().toString(), ent.getMobileNumber(), 1, header, "短信验证码", result, 1);*/
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/30 16:53
     * @Description 用户是否被冻结
     */
    public boolean isUserFreeze(String userId, Result result) {
        if (userRedis.exists(String.format(UserRedisKeys.USER_LOGIN_ERROR_FREEZE_USERID, userId))) {
            String freezeTime = userRedis.get(String.format(UserRedisKeys.USER_LOGIN_ERROR_FREEZE_USERID, userId));
            Long freezeMinutes = (System.currentTimeMillis() - DateUtil.parseTime(freezeTime).getTime()) / 1000 / 60;
            if (freezeMinutes < UserContext.FREEZEMINUTES) {
                result.setCode(-10);
                result.setMsg(String.format("密码多次错误,冻结登录,%s分钟后解冻", UserContext.FREEZEMINUTES - freezeMinutes));
                return true;
            }
        }
        return false;
    }

    /**
     * @Author linian
     * @Date 2017/3/20 11:50
     * @Description 直播吧用户登录
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result login(String mobile, ClientRequestHeader header) {
        Result result = new Result();
        List<UserInfoENT> list = userInfoService.getUserInfoByMobile(mobile);
        if (CollectionUtils.isEmpty(list)) {
            result.setCode(Result.ERROR);
            result.setMsg("该手机号没有绑定任何账号,请联系客服或进行注册.");
            return result;
        }
        Boolean hasMoreThanOneUser = list.size() > 1;
        if (hasMoreThanOneUser) {
            //userInfoService.userListByLoginTime(list, result);
            return result;
        }
        UserInfoENT ent = userInfoService.getCurrentUserInfo(String.valueOf(list.get(0).getUserId()));
        if (ent == null) {
            result.setCode(Result.ERROR);
            result.setMsg("该手机号没有绑定任何账号,请联系客服或进行注册");
            return result;
        }
        if (isUserFreeze(String.valueOf(ent.getUserId()), result)) {
            return result;
        }
        tokenService.saveToken(ent.getLoginName(), ent.getUserId().toString(), ent.getMobileNumber(), 1, header, "短信验证码", result, 0);
        return result;
    }

    /**
     * @Author linian
     * @Date 2017/4/19 11:30
     * @Description 直播吧用户登录
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result loginWithoutVerifyCode(String traceId, ClientRequestHeader header, String params) {
        Result result = new Result();
        try {
            logger.info("[直播吧短信验证码登录],params:{},traceId:{}", params, traceId);
            LoginSmsParams loginSmsParams = GfJsonUtil.parseObject(params, LoginSmsParams.class);
            if (loginSmsParams == null) {
                logger.error("[直播吧短信验证码登录] 参数错误, params:{} traceId:{}", params, traceId);
                result.setCode(Result.ERROR);
                result.setMsg("参数错误");
                return result;
            }
            if (StringUtils.isEmpty(loginSmsParams.getMobile())) {
                result.setCode(Result.ERROR);
                result.setMsg("直播吧手机号验证码不能为空");
                return result;
            }
            if (!MobileUtil.isMobileNO(loginSmsParams.getMobile())) {
                result.setCode(Result.ERROR);
                result.setMsg("请输入正确格式的手机号码");
                return result;
            }
            String mobile = loginSmsParams.getMobile();
            return login(mobile, header);
        } catch (Exception e) {
            logger.error("traceId:{} params:{} stackTrace:{}", traceId, params, LogExceptionStackTrace.erroStackTrace(e));
            result.setCode(Result.ERROR);
            result.setMsg("抱歉，系统忙！");
            return result;
        }
    }
}
