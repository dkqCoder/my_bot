package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ln
 */
public class FreeWelfareInfoDTO {
    @JSONField(name = "Code")
    private Integer code = 0;
    @JSONField(name = "Data")
    private Object data = "";
    @JSONField(name = "Msg")
    private String msg = "";
    @JSONField(name = "Stamp")
    private Long stamp = Long.valueOf(0);


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getStamp() {
        return stamp;
    }

    public void setStamp(Long stamp) {
        this.stamp = stamp;
    }
}
