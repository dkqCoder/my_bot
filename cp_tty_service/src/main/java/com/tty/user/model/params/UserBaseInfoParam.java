package com.tty.user.model.params;

import java.util.Date;

/**
 * Created by donne on 17/03/07.
 */
public class UserBaseInfoParam
{
    private Long userId;
    private String loginName;
    private String mobileNumber;
    private String realName;
    private String thdPartName;
    private String nickName;
    private Date startRegisterTime;
    private Date endRegisterTime;
    private Integer status;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getThdPartName() {
        return thdPartName;
    }

    public void setThdPartName(String thdPartName) {
        this.thdPartName = thdPartName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getStartRegisterTime() {
        return startRegisterTime;
    }

    public void setStartRegisterTime(Date startRegisterTime) {
        this.startRegisterTime = startRegisterTime;
    }

    public Date getEndRegisterTime() {
        return endRegisterTime;
    }

    public void setEndRegisterTime(Date endRegisterTime) {
        this.endRegisterTime = endRegisterTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
