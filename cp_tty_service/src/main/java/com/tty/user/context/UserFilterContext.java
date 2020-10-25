package com.tty.user.context;

/**
 * @Author chenpengfei {chenpengfei@jiangduoduo.com}
 * @Date 2017/10/30 16:56
 */
public class UserFilterContext {

    /** IP **/
    public static final String USER_FILTER_TYPE_IP = "ip";
    /** 设备号 **/
    public static final String USER_FILTER_TYPE_UUID = "uuid";
    /** 密码 **/
    public static final String USER_FILTER_TYPE_PASSWORD = "password";
    /** 平台名称 **/
    public static final String USER_FILTER_TYPE_PLATFORM_CODE = "platformCode";

    public static final String USER_FILTER_STATUS_TRUE = "1";
    public static final String USER_FILTER_STATUS_FALSE = "-1";

    public static final String USER_FILTER_OPERATOR_SYS = "system";

    public static final String USER_FILTER_LIST_WHITE = "1";
    public static final String USER_FILTER_LIST_BLACK = "-1";

}
