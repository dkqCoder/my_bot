package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/3/24.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-24
 */
public class CheckWapVerifyParams {
    private String mobile;
    @JSONField(name = "verifycode")
    private String verifyCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
