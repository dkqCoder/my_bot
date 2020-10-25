package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/3/23.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-23
 */
public class CheckForgetPayPassParams {
    @JSONField(name = "vcode")
    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
