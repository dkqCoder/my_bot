package com.tty.user.controller.model.params;

/**
 * @author wenxiaoqing
 * @Date 2017/3/29
 * @Description
 */
public class ForgetPwdParam {

    private String id;
    private String mobile;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
