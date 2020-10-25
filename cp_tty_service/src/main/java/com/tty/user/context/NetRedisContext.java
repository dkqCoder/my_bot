package com.tty.user.context;

/**
 * @author LiuJin
 * @date 2017/1/13
 * @Description
 **/

public class NetRedisContext {
    /**
     * @Author shenwei
     * @Date 2017/3/30 20:53
     * @Description 用户第三方信息
     */
    public final static String USER_THIRD_INFO_KEY = "user_103:%s_%s";
    
    //    com.tty.user.dto.RefreshInfoDTO
    public final static String NET_USER_BASE_KEY = "user_101:%s";
    //    com.tty.user.dto.UserDTO
    public final static String NET_USER_INFO_KEY = "user_102:%s";

    public final static String NET_USER_NAME_KEY = "user_104:%s";

    //    com.tty.user.dto.UserThirdInfoDTO
    public final static String NET_USER_HIRD_INFO_KEY = "user_103:_%s_%s";
    // idcardNumber->   List<com.tty.user.dto.RefreshUserInfoDTO>
    public final static String NET_USERS_VALIDATE_IDCARDNO_KEY = "user_203:%s";
    // mobileNumber ->    List<com.tty.user.dto.RefreshUserInfoDTO>
    public final static String NET_USERS_VALIDATE_MOBILE_KEY = "user_202:%s";
    // useId ->   com.tty.user.dto.RefreshUserInfoDTO
    public final static String NET_USERS_VALIDATE_USERID_KEY = "user_201:%s";

    //
    public final static String USER_VALIDATE_GROUPBY_IDCARD = "UserValidate_IdCard_202";
    public final static String USER_VALIDATE_MOBILE_IDCARD = "UserValidate_Mobile_202";
    public final static String USER_VALIDATE_USERID_IDCARD = "UserValidate_UserId_202";
    public final static int NET_USER_EXP_MINUTE = 60 * 60 * 24 * 7;

}
