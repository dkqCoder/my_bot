package com.tty.user.dto;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by donne on 17/03/07.
 */
public class UserBaseInfoDTO {

    /**
     * n_user_id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 绑定手机号
     */

    private String mobileNumber;

    /**
     * 第三方用户名
     */
    private String thdPartName;

    /**
     * 第三方类别
     */
    private String thdPartType;

    /**
     * 注册时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 渠道代码
     */
    private String entranceCode;

    /**
     * 是否可用
     */
    private Integer status;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    private String remark;

    /**
     * 身份证号码
     */
    private String idcardNumber;

    //用户积分
    private Integer userIntegral;
    //成长值
    private Long userGrowUps;
    //用户等级
    private Integer userLevel;
    //打败多少彩友
    private Double beatInfo;
    //今日是否签到
    private Integer isSignInToday;


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

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getEntranceCode() {
        return entranceCode;
    }

    public void setEntranceCode(String entranceCode) {
        this.entranceCode = entranceCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }

    public Integer getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(Integer userIntegral) {
        this.userIntegral = userIntegral;
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

    public Double getBeatInfo() {
        return beatInfo;
    }

    public void setBeatInfo(Double beatInfo) {
        this.beatInfo = beatInfo;
    }

    public Integer getIsSignInToday() {
        return isSignInToday;
    }

    public void setIsSignInToday(Integer isSignInToday) {
        this.isSignInToday = isSignInToday;
    }
}
