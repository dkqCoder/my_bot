package com.tty.ots.trade.context;

/**
 * Created by liuzhenpeng on 2017/4/21.
 */
public enum ErrorMsgEnum {

    OPERATE_LIMIT(10000, "操作频繁"),
    PARAMETER_ERROR(10001, "参数错误"),
    USER_PAY_CONFIG_NOT_EXIST(10002, "用户支付配置信息不存在"),
    GET_USER_BASEINFO_FAILURE(10003, "获取用户基础信息失败"),
    USER_PAY_PWD_ERROR(10004, "支付密码不存在"),
    USER_PWD_ERROR(10005, "密码错误"),
    USER_BIND_CARD_NOT_EXIST(10006, "未获取到用户绑定银行信息"),

    CITY_NOT_EXIST(20000, "城市信息不存在"),

    /**
     * 银行卡
     */
    USER_BANK_CARD_IN_AUDIT(30000, "银行卡审核中"),
    USER_BANK_CARD_NO_AVAIL(40000, "该银行卡暂不可用");

    private int errorCode; //错误码
    private String errorMsg;// 描述信息

    ErrorMsgEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
