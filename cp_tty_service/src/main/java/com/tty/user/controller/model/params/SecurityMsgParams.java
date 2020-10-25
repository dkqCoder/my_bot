package com.tty.user.controller.model.params;

/**
 * @author sunyishun
 * @create 2020-10-23
 * 安全验证码
 */
public class SecurityMsgParams {
    private int msgType;
    private String userId;
    private String mobile;
    private String oldMobile;
    private String captchaKey; // 图形验证码key
    private String captchaValue; // 图形验证码value

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

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
