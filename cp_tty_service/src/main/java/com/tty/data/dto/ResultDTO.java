package com.tty.data.dto;

import java.io.Serializable;

/**
 * @author zhudonghai
 * @Date 2017/5/22
 * @Description dubbo使用
 */
public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = 5576237395711742681L;
    public static final Integer SUCCESS = Integer.valueOf(0);
    public static final Integer SUCCESS_1 = Integer.valueOf(1);
    public static final String MSG_SUCCESS_DESC = "操作成功！";
    public static final Integer ERROR = Integer.valueOf(-1);
    public static final String MSG_ERROR_DESC = " 系统忙！";
    public static final String MSG_ERROR_SIGN = " 请求签名错误！";
    public static final Integer ILLEGAL = Integer.valueOf(-2);
    public static final String MSG_ILLEGAL_DESC = "参数不正确！";
    private int code = 0;
    private String msg = "";
    private String data;
    private int stamp = 0;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }
}
