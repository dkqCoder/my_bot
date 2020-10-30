package com.tty.data.context;

/**
 * Created by pdl on 2017/3/23.
 */
public class ResponseCodeAndMsg {
    //成功
    public static final int SUCCESS = 0;
    public static final String  MSG_SUCCESS = "操作成功！";
    //参数错误
    public static final int PARAM_ERROR = -1;
    public static final String MSG_PARAM_ERROR = "参数不正确";
    //未登入
    public static final int NO_LOGIN = -2;
    public static final String MSG_NO_LOGIN = "请您登录后再试";
    //无用户信息
    public static final int NO_USER = -90;
    public static final String MSG_NO_USER = "请您先注册";
    //TOKEN校验不一致
    public static final int TOKEN_ERROR = -91;
    public static final String MSG_TOKEN_ERROR = "签名已过期，请重新登录";
    //服务器内部错误
    public static final int SERVER_ERROR = -92;
    public static final String MSG_SERVER_ERROR = "系统忙";
    //停服
    public static final int STOP_SERVER = -93;
    public static final String MSG_STOP_SERVER = "服务升级中，敬请期待...";
    //禁止登录
    public static final int FORBIDDEN = -94;
    public static final String MSG_FORBIDDEN = "用户被禁止登陆，请联系客服";
    //无数据
    public static final int NO_DATA = -95;
    public static final String MSG_NO_DATA = "抱歉，没有您想要的数据！";
    //token 过期
    public static final int TOKEN_TIME_OUT = -96;
    public static final String MSG_TOKEN_TIME_OUT = "您的登录信息已过期，为了您购彩安全，请重新登录";
    //更换设备token失效
    public static final int CHANGE_DIVICE_TOKEN_INVALID = -97;
    public static final String MSG_CHANGE_DIVICE_TOKEN_INVALID = "您的登录信息已失效，为了您购彩安全，请重新登录";
    //系统忙
    public static final int SYSTEM_ERROR = -99;
    public static final String MSG_SYSTEM_ERROR = "系统忙";

    public static final String MSG_LOAD_ERROR = "加载失败，请重试";
}
