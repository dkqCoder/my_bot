package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ExtModel;
import com.tty.common.utils.Result;
import com.tty.user.context.LoginTypeEnum;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.entity.UserIdcardResetENT;
import com.tty.user.dao.entity.UserInfoENT;

import java.util.List;

/**
 * @author zxh
 * @create 2017-03-06 18:02:49
 **/
public interface UserInfoService {

    void userRegisterWarn();

    List<UserInfoENT> getUserInfoByMobileAndUserId(String mobileNumber, Long userId);


    UserBaseInfoENT refreshUserBaseInfo(String userId);

    void updateUserRedisByAdmin(UserBaseInfoENT currentUserBaseInfo, UserInfoENT currentUserInfo);


    String getUserDefaultName(String mobile, String traceId);

    Boolean checkValidateCode(String mobile);

    UserIdcardResetENT getResetIdCardInfo(String traceId, String userId);

    Result bindUserPayPassword(String traceId, String oldPw, String newPw, String userId);


    /**
     * 重置身份证
     *
     * @param userId       用户ID
     * @param idcardNumber 身份证号
     * @param name         姓名
     * @param frontUrl     身份证正面URL
     * @param backUrl      身份证反面URL
     * @param traceId
     * @return
     */
    Result resetIdCardNumber(String userId, String idcardNumber, String name,
                             String frontUrl, String backUrl, String traceId);

    Result editNickName(String userId, String nickname, String traceId);

    List<UserInfoENT> refreshUserInfoByMobile(String mobile);

    void userListByLoginTime(List<UserInfoENT> items, Result result);

    /**
     * @Author shenwei
     * @Date 2017/3/13 11:13
     * @Description 100用户申请注册
     */
    Result register(String userName, Integer registerType, String passWord, Integer smsType, String traceId, ClientRequestHeader header);

    /**
     * @Author shenwei
     * @Date 2017/3/29 11:17
     * @Description 对内使用第三方用户注册
     */
    void registerThird(String thirdUserId, String thirdUserName, LoginTypeEnum thirdType, String entranceCode, Result result, String traceId, ClientRequestHeader header);

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
    Result userLoginOut(String userId, String token, String traceId);

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

    /**
     * 重置登入密码 action 16011,16012
     *
     * @param userId
     * @param newPwd
     * @param traceId
     * @return
     */
    Result changeUserPassword(String userId, String newPwd, String traceId);

    /**
     * 第三方登录设置登录密码
     */
    Result thdPartChangeUserPassword(String userId, String newPwd, String traceId);

    ExtModel listUserInfo(JSONObject jsonParm, ExtModel result);

    void saveUserInfo(UserInfoENT UserInfo);

    void updateUserInfo(UserInfoENT UserInfo);

    UserBaseInfoENT getCurrentUserBaseInfo(String userId);

    List<UserInfoENT> getUserInfoByMobile(String mobile);

    UserInfoENT getUserInfoByLoginName(String loginName);

    UserInfoENT getCurrentUserInfo(String userId);
}
