package com.tty.user.params;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author donne on 2017/3/15.
 */
public class ResetPwdParam {
    @JSONField(name = "username")
    private String loginName;
    @JSONField(name = "mobile")
    private String mobileNumber;
    @JSONField(name = "newpwd")
    private String password;
    @JSONField(name = "id")
    private Long userId;
    @JSONField(name = "type")
    private Integer type;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
