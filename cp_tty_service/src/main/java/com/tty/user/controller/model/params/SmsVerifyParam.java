package com.tty.user.controller.model.params;

/**
 * @author shenwei
 * @create 2017-03-16
 * 获取短信验证码登陆参数
 */
public class SmsVerifyParam {
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
