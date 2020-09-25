package com.tty.user.params;/**
 * Created by shenwei on 2017/3/14.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-14
 */
public class LoginParams {
    private String token;
    private String name;
    private String password;
    private String userId;
    private Integer userType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    @JSONField(name = "pw")
    public void setPassword(String password) {
        this.password = password;
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
