package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/3/24.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-24
 */
public class MobileLoginParams {

    private String mobile;
    private String password;
    private String token;
    private String userId;
    private Integer userType;

    public String getMobile() {
        return mobile;
    }
    @JSONField(name = "mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }
    @JSONField(name = "pw")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }
    @JSONField(name = "userid")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }
    @JSONField(name = "usertype")
    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
