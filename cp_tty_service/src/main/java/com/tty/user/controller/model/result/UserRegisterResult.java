package com.tty.user.controller.model.result;/**
 * Created by shenwei on 2017/3/30.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-30
 */
public class UserRegisterResult {
    @JSONField(name = "UserId")
    private String userId;
    @JSONField(name = "Password")
    private String passWord;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
