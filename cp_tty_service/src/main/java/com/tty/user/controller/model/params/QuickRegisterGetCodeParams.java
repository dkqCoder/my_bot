package com.tty.user.controller.model.params;

/**
 * @author sunyishun
 * @create 2020-10-23
 * 获取手机注册短信验证码
 */
public class QuickRegisterGetCodeParams {
    private String mobile;
    private String captchaKey; // 图形验证码key
    private String captchaValue; // 图形验证码value

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }

    public String getCaptchaValue() {
        return captchaValue;
    }

    public void setCaptchaValue(String captchaValue) {
        this.captchaValue = captchaValue;
    }
}
