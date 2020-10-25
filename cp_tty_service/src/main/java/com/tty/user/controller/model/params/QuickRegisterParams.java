package com.tty.user.controller.model.params;

/**
 * @author sunyishun
 * @create 2020-10-23
 * 手机注册验证码注册
 */
public class QuickRegisterParams {
    private String mobile;
    private String verifyCode; // 短信验证码

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
