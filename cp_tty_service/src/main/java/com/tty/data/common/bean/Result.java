package com.tty.data.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;

/**
 * @author zhudonghai
 * @date 2016-12-14
 * @Description 操作结果集
 */
public class Result implements Serializable {

    private static final long serialVersionUID = 5576237395711742681L;
    public static final Integer SUCCESS = 0;
    public static final String MSG_SUCCESS_DESC = "操作成功！";
    public static final Integer ERROR = -1;
    public static final Integer ERROR_THREE = -3;
    public static final Integer ERROR_FOUR = -4;
    public static final String MSG_ERROR_DESC = " 抱歉，系统忙！";
    public static final Integer NOTLOGIN = -2;
    public static final String MSG_NOTLOGIN_DESC = "用户未登录！";
    public static final String MSG_LOGIN_DESC = "登录过期，请您重新登录！";
    public static final String MSG_NODATA = "无数据";
    public static final String MSG_FAILED = "领取失败";
    public static final String MSG_SUCCESS = "领取成功!";

    @JSONField(name = "Code")
    private int code = 0;
    @JSONField(name = "Msg")
    private String msg = "";
    @JSONField(name = "Data")
    private Object data = new Object();

    @JSONField(name = "Code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @JSONField(name = "Msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JSONField(name = "Data")
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result [code=" + code + ", msg=" + msg + "]";
    }
}
