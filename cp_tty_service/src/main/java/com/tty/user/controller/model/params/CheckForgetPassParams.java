package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/3/20.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-20
 */
public class CheckForgetPassParams {
    @JSONField(name = "verifycode")
    private String verifyCode;
    @JSONField(name = "username")
    private String userName;
    @JSONField(name = "id")
    private String userId;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
