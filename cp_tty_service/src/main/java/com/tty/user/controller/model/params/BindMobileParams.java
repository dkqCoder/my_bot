package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/3/15.
 */

/**
 * @author shenwei
 * @create 2017-03-15
 */
public class BindMobileParams {
    private String mobile;
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
