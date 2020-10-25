package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/6/28.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-06-28
 */
public class WeChatBindCheckParams {
    private String mobile;
    private String verifyCode;
    private String thdUserId;
    private String thdUserName;

    @JSONField(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    @JSONField(name = "mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JSONField(name = "verifyCode")
    public String getVerifyCode() {
        return verifyCode;
    }

    @JSONField(name = "verifyCode")
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @JSONField(name = "thdUserId")
    public String getThdUserId() {
        return thdUserId;
    }

    @JSONField(name = "thdUserId")
    public void setThdUserId(String thdUserId) {
        this.thdUserId = thdUserId;
    }

    @JSONField(name = "thdUserName")
    public String getThdUserName() {
        return thdUserName;
    }

    @JSONField(name = "thdUserName")
    public void setThdUserName(String thdUserName) {
        this.thdUserName = thdUserName;
    }
}
