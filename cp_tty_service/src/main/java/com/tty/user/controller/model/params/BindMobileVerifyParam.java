package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/3/15.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-15
 * 绑定手机获取验证码参数
 */
public class BindMobileVerifyParam {
    private String oldMobile;
    private String mobile;
    /*类型*/
    private String captchaKey; // 图形验证码key
    private String captchaValue; // 图形验证码value

    public String getOldMobile() {
        return oldMobile;
    }

    public void setOldMobile(String oldMobile) {
        this.oldMobile = oldMobile;
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
