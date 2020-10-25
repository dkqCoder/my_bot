package com.tty.user.service.impl;/**
 * Created by shenwei on 2017/3/24.
 */

import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;
import com.tty.user.common.utils.HttpUtils;
import com.tty.user.controller.model.result.TokenLoginResult;
import com.tty.user.dao.UserInfoDao;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author shenwei
 * @create 2017-03-24
 */
@Service("userMobileLoginService")
public class UserMobileLoginServiceImpl implements UserMobileLoginService {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private final Logger logger = LoggerFactory.getLogger(UserMobileLoginServiceImpl.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * @Author shenwei
     * @Date 2017/3/24 17:55
     * @Description 1017 android and ios mobile login  密码传过来是MD5加密过的
     */
    public Result login(String mobile, String passWord, String token, Integer userType, String traceId, ClientRequestHeader header, String thirdId) {
        Result result = new Result();
        Boolean carryToken = StringUtils.isNotBlank(token);

        if (carryToken) {
            result = tokenService.verifyToken(token, userType, header.getUuid());
            if (result.getCode() < 0) {
                return result;
            } else {
                //上报登录积分任务
                Long userId;
                if (StringUtils.isBlank(thirdId)) {
                    TokenLoginResult tokenLoginResult = (TokenLoginResult) result.getData();
                    userId = Long.valueOf(HttpUtils.decode(tokenLoginResult.getId()));
                    //publicCommonActiveMqUtil.reportUserLoginMission(userId, traceId);
                } else {
                    userId = Long.valueOf(HttpUtils.decode(header.getUserID()));
                    //publicCommonActiveMqUtil.reportUserLoginMission(userId, traceId);
                }

                threadPoolTaskExecutor.execute(() -> {
                    userInfoDao.updateUserLastLoginTime(String.valueOf(userId), new Date());
                });
                return result;
            }
        }
        return loginWithMobile(mobile, passWord, traceId, header);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/30 16:00
     * @Description 1017 手机号密码登录 密码是加密过的
     */
    @Override
    public Result loginWithMobile(String mobile, String passWord, String traceId, ClientRequestHeader header) {
        Result result = new Result();
        if (StringUtils.isBlank(mobile)) {
            result.setCode(Result.ERROR);
            result.setMsg("手机号为空");
            return result;
        }
        if (StringUtils.isBlank(passWord)) {
            result.setCode(Result.ERROR);
            result.setMsg("密码为空");
            return result;
        }
        UserInfoENT userInfoENT = userInfoService.getUserInfoByLoginName(mobile);
        if (userInfoENT != null) {
            Integer loginType = 2;  //-1未知登录方式 0其他 1手机号 2用户名 3微信快捷
            if (mobile.equals(userInfoENT.getMobileNumber())) {
                loginType = 1;  //用户名(mobile变量)和手机号相同，认为是手机号登录
            }
            userLoginService.loginWithNamePwd(mobile, passWord, false, header, result, loginType);
            return result;
        }
        List<UserInfoENT> list = userInfoService.getUserInfoByMobile(mobile);
        if (CollectionUtils.isEmpty(list)) {
            result.setCode(Result.ERROR);
            result.setMsg("您好，该手机号码或用户名还未注册过账户，请先完成注册");
            return result;
        }
        if (list.size() == 1) {
            userLoginService.loginWithNamePwd(list.get(0).getLoginName(), passWord, false, header, result, 1);
            return result;
        }
        String defaultName = userInfoService.getUserDefaultName(mobile, traceId);
        //默认用户名不存在则进行多账户验证,如果不存在则手机号作为用户名登录验证 存在提示短信验证码登录
        if (StringUtils.isNotBlank(defaultName)) {
            userLoginService.loginWithNamePwd(defaultName, passWord, false, header, result, 2);
            return result;
        }
        result.setCode(Result.ERROR);
        result.setMsg("该手机号绑定多个账号,请使用短信登录");
        return result;
    }

    /**
     * @Author
     * @Date 2017/3/24 17:55
     * @Description 1017 android and ios mobile login  密码传过来是MD5加密过的
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public Result loginWithOnlyMobile(String mobile, String passWord, String token, Integer userType, String traceId, ClientRequestHeader header, String thirdId) {
        Result result = new Result();
        Boolean carryToken = StringUtils.isNotBlank(token);
        if (carryToken) {
            result = tokenService.verifyToken(token, userType, header.getUuid());
            if (result.getCode() < 0) {
                return result;
            } else {
                //上报登录积分任务
                Long userId;
                if (StringUtils.isBlank(thirdId)) {
                    TokenLoginResult tokenLoginResult = (TokenLoginResult) result.getData();
                    userId = Long.valueOf(HttpUtils.decode(tokenLoginResult.getId()));
                    //publicCommonActiveMqUtil.reportUserLoginMission(userId, traceId);
                } else {
                    userId = Long.valueOf(HttpUtils.decode(header.getUserID()));
                    //publicCommonActiveMqUtil.reportUserLoginMission(userId, traceId);
                }
                threadPoolTaskExecutor.execute(() -> {
                    userInfoDao.updateUserLastLoginTime(String.valueOf(userId), new Date());
                });
                return result;
            }
        }
        return loginWithOnlyMobile(mobile, passWord, traceId, header);
    }

    /**
     * @Author linian
     * @Date 2017/4/24 16:00
     * @Description 1017 手机号密码登录 密码是加密过的
     */
    @Override
    public Result loginWithOnlyMobile(String mobile, String passWord, String traceId, ClientRequestHeader header) {
        Result result = new Result();
        if (StringUtils.isBlank(mobile)) {
            result.setCode(Result.ERROR);
            result.setMsg("手机号为空");
            return result;
        }
        if (StringUtils.isBlank(passWord)) {
            result.setCode(Result.ERROR);
            result.setMsg("密码为空");
            return result;
        }
        List<UserInfoENT> list = userInfoService.getUserInfoByMobile(mobile);
        if (CollectionUtils.isEmpty(list)) {
            result.setCode(Result.ERROR);
            result.setMsg("该用户名或手机号尚未注册");
            return result;
        }
        if (list.size() == 1) {
            userLoginService.loginWithNamePwd(list.get(0).getLoginName(), passWord, false, header, result, 0);
            return result;
        }
        String defaultName = userInfoService.getUserDefaultName(mobile, traceId);
        //默认用户名不存在则进行多账户验证,如果不存在则手机号作为用户名登录验证 存在提示短信验证码登录
        if (StringUtils.isNotBlank(defaultName)) {
            userLoginService.loginWithNamePwd(defaultName, passWord, false, header, result, 0);
            return result;
        }
        result.setCode(Result.ERROR);
        result.setMsg("该手机号绑定多个账号,请使用短信登录");
        return result;
    }
}
