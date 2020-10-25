package com.tty.user.controller.model.params;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zxh
 * @create 2017-03-15
 */
public class UserNameAndMobileParam {
    private String mobile;
    @JSONField(name = "username")
    private String userName;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
