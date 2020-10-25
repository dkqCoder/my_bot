package com.tty.user.model.web;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author 用户信息
 * @date 2017/1/13
 * @Description .net缓存中拿到的用户信息
 */
public class WebUserInfo {
    private String userId;
    private String mobile;
    private String isMobileValided;
    private String realityName;
    private String idCardNumber;
    @JSONField(serialize=false)
    private String registerTime;
    private Date registerTimeDate;
    private String registerIp;
    @JSONField(serialize=false)
    private String lastLoginTime;
    private Date lastLoginTimeDate;
    private String LastLoginIp;
    private Long registerFrom;
    private String nickName;
    private String sex;
    @JSONField(serialize=false)
    private String birthDay;
    private Date birthDayDate;
    private String Address;
    private Integer cityId;
    private String email;
    private String isEmailValided;
    private String qq;
    private String isQqValided;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsMobileValided() {
        return isMobileValided;
    }

    public void setIsMobileValided(String isMobileValided) {
        this.isMobileValided = isMobileValided;
    }

    public String getRealityName() {
        return realityName;
    }

    public void setRealityName(String realityName) {
        this.realityName = realityName;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    @JSONField(name="registerTime",format = "yyyy-MM-dd HH:mm:ss")
    public Date getRegisterTimeDate() {
        return registerTimeDate;
    }

    public void setRegisterTimeDate(Date registerTimeDate) {
        this.registerTimeDate = registerTimeDate;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @JSONField(name="lastLoginTime",format = "yyyy-MM-dd HH:mm:ss")
    public Date getLastLoginTimeDate() {
        return lastLoginTimeDate;
    }

    public void setLastLoginTimeDate(Date lastLoginTimeDate) {
        this.lastLoginTimeDate = lastLoginTimeDate;
    }

    public String getLastLoginIp() {
        return LastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        LastLoginIp = lastLoginIp;
    }

    public Long getRegisterFrom() {
        return registerFrom;
    }

    public void setRegisterFrom(Long registerFrom) {
        this.registerFrom = registerFrom;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    @JSONField(name="birthDay",format = "yyyy-MM-dd")
    public Date getBirthDayDate() {
        return birthDayDate;
    }

    public void setBirthDayDate(Date birthDayDate) {
        this.birthDayDate = birthDayDate;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsEmailValided() {
        return isEmailValided;
    }

    public void setIsEmailValided(String isEmailValided) {
        this.isEmailValided = isEmailValided;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getIsQqValided() {
        return isQqValided;
    }

    public void setIsQqValided(String isQqValided) {
        this.isQqValided = isQqValided;
    }
}
