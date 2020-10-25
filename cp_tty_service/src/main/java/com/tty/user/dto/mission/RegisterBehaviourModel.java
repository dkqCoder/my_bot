package com.tty.user.dto.mission;/**
 * Created by shenwei on 2017/3/27.
 */

/**
 * @author shenwei
 * @create 2017-03-27
 */
public class RegisterBehaviourModel {
    private String userId;
    private String userName;
    private String mobile;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
