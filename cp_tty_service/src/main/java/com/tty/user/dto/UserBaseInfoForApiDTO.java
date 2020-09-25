package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author zhangdi
 * @date 17/3/23
 * @Description
 */
public class UserBaseInfoForApiDTO {
    private long userId;
    private String userName;//用户名
    private String realName;//真实姓名
    private int hasNickName;//是否有昵称
    private String nickName;//昵称
    private String userFace;//头像
    private String iDCardNumber; //身份证
    private String mobile;//手机号
    private boolean isMobileValied;//手机是否验证
    private int isPayPwd;//是否已设置支付密码，1：是，0：否
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;//注册时间
    private long userExperience;//用户积分
    private String userLevel;//用户等级
    private String levelName;//等级名称
    private Integer isSignToday;//今日是否签到
    private Long sign = new Long(0);
    private Integer signAward;//下一次签到获取的积分
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    private String thdPartId;//第三方用户ID
    private String thdPartName;//第三方用户名
    private String thdPartType;//第三方类别
    private int isLoginPwd;//是否设置了登录密码，1：是，0：否。
    /** 访问来源 */
    private String platformCode;    // 2017-05-11 luo yinghua 添加访问来源

    public String getThdPartId() {
        return thdPartId;
    }

    public void setThdPartId(String thdPartId) {
        this.thdPartId = thdPartId;
    }

    public String getThdPartName() {
        return thdPartName;
    }

    public void setThdPartName(String thdPartName) {
        this.thdPartName = thdPartName;
    }

    public String getThdPartType() {
        return thdPartType;
    }

    public void setThdPartType(String thdPartType) {
        this.thdPartType = thdPartType;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getSignAward() {
        return signAward;
    }

    public void setSignAward(Integer signAward) {
        this.signAward = signAward;
    }

    public Long getSign() {
        return sign;
    }

    public void setSign(Long sign) {
        this.sign = sign;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getHasNickName() {
        return hasNickName;
    }

    public void setHasNickName(int hasNickName) {
        this.hasNickName = hasNickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getiDCardNumber() {
        return iDCardNumber;
    }

    public void setiDCardNumber(String iDCardNumber) {
        this.iDCardNumber = iDCardNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isMobileValied() {
        return isMobileValied;
    }

    public void setMobileValied(boolean mobileValied) {
        isMobileValied = mobileValied;
    }

    public int getIsPayPwd() {
        return isPayPwd;
    }

    public void setIsPayPwd(int isPayPwd) {
        this.isPayPwd = isPayPwd;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public long getUserExperience() {
        return userExperience;
    }

    public void setUserExperience(long userExperience) {
        this.userExperience = userExperience;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getIsSignToday() {
        return isSignToday;
    }

    public void setIsSignToday(Integer isSignToday) {
        this.isSignToday = isSignToday;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public int getIsLoginPwd() {
        return isLoginPwd;
    }

    public void setIsLoginPwd(int isLoginPwd) {
        this.isLoginPwd = isLoginPwd;
    }
}
