package com.tty.user.model.web;

/**
 * Created by jdd on 2017/1/19.
 */
public class WebUserInfoModel {
    private Long userId;//用户id
    private String mobile;//手机号
    private String realityName;//真实姓名
    private String idCardNumber;//身份id
    private Long integral;//积分余额
    private Integer isSignInToday;//今日是否签到
    private Double beatInfo;//打败多少彩友
    private Long userGrowUps;//成长值
    private Integer userLevel;//等级
    private String name;//用户名

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public Integer getIsSignInToday() {
        return isSignInToday;
    }

    public void setIsSignInToday(Integer isSignInToday) {
        this.isSignInToday = isSignInToday;
    }

    public Double getBeatInfo() {
        return beatInfo;
    }

    public void setBeatInfo(Double beatInfo) {
        this.beatInfo = beatInfo;
    }

    public Long getUserGrowUps() {
        return userGrowUps;
    }

    public void setUserGrowUps(Long userGrowUps) {
        this.userGrowUps = userGrowUps;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
