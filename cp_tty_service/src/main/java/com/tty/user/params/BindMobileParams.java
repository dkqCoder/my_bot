package com.tty.user.params;/**
 * Created by shenwei on 2017/3/15.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-15
 */
public class BindMobileParams {
    @JSONField(name = "PhoneCode")
    private String verifyCode;
    /*校验类型，1、原手机号校验，其他是绑定手机号校验*/
    private Integer type;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
