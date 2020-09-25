package com.tty.user.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;
import com.tty.user.dao.ent.UserInfoENT;

import java.util.List;

/**
 * @author zxh
 * @create 2017-03-06 18:02:49
 **/
public interface UserInfoService {

    /**
     * @Author shenwei
     * @Date 2017/3/13 11:13
     * @Description 100用户申请注册
     */
    Result register(String userName, Integer registerType, String passWord, Integer smsType, String traceId, ClientRequestHeader header);


       /**
     * @Author shenwei
     * @Date 2017/3/30 10:50
     * @Description 对内使用用户名密码存入 返回userId
     */
    Long registerByUserNameAndPwd(String userName, String mobile, String entrance, String password, String traceId, ClientRequestHeader header, Result result);

    /**
     * @Author shenwei
     * @Date 2017/3/13 11:14
     * @Description 绑定手机号
     */
    Result bindUserMobile(String userId, String verifyCode, String traceId, ClientRequestHeader header, Integer type);

       /**
     * 110 退出登录
     */
    Result userLoginOut(String userId, Integer userType, String token, String traceId);

    /**
     * @Author shenwei
     * @Date 2017/3/13 20:33
     * @Description 修改密码
     */
    Result changeUserPassword(String userId, String oldPwd, String newPwd, String traceId);

    /**
     * @Author shenwei
     * @Date 2017/3/14 10:55
     * @Description 提现密码绑定修改
     */
    Result changeUserPayPassword(String userId, String oldPwd, String newPwd, String traceId);

    /**
     * 1203 修改用户提现密码
     */
    Result changeUserPayPassword(String userId, String newPwd, String traceId);

    void loginWithNamePwd(String userName, String password, Boolean pwdEncrypted, ClientRequestHeader header, Result result, Integer loginType);

    Boolean checkValidateCode(String mobile);

    List<UserInfoENT> getUserInfoByMobile(String mobile);

    String getUserDefaultName(String mobile, String traceId);

    UserInfoENT getUserInfoByLoginName(String loginName);

    /**
     * 重置登入密码 action 16011,16012
     *
     * @param userId
     * @param newPwd
     * @param traceId
     * @return
     */
    Result changeUserPassword(String userId, String newPwd, String traceId);

    UserInfoENT getCurrentUserInfo(String userId);
}
