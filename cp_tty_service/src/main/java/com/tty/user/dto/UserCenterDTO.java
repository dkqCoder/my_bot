package com.tty.user.dto;/**
 * Created by shenwei on 2017/5/10.
 */

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenwei
 * @create 2017-05-10
 * 用户中心实体
 */
public class UserCenterDTO {
    //基本信息
    private Long userId;
    private String userName;
    private String realName;
    private Integer hasNickName;
    private String nickName;
    private String userFace;
    private String idCardNumber;
    private String mobile;
    private Boolean mobileValidated;
    private BigDecimal balance;
    private String securityQuestion;
    private Integer hasPayPwd;
    private String registerTime;
    //用户余额
    private BigDecimal noDistillBalance;
    private BigDecimal distillBalance;
    //用户银行卡
    private Integer status;
    private Integer cityId;
    private Integer bankTypeId;
    private Integer bankId;
    private String bankName;
    private String bankCardNumber;
    private Long usableCount;
    //积分等级
    private Long userIntegral;
    private String userLevel;
    private String levelName;
    //无用字段
    private Integer level = 1;
    //手机号是否绑定微信
    private Integer phoneBindWx = -1;
    //绑定微信号
    private String bindWxName = "";
    private Date lastLoginTime;




    /*是否设置登录密码*/
    private Integer hasLoginPwd;


    @JSONField(name = "Id")
    public Long getUserId() {
        return userId;
    }

    @JSONField(name = "Id")
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JSONField(name = "Name")
    public String getUserName() {
        return userName;
    }

    @JSONField(name = "Name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JSONField(name = "RealName")
    public String getRealName() {
        return realName;
    }

    @JSONField(name = "RealName")
    public void setRealName(String realName) {
        this.realName = realName;
    }

    @JSONField(name = "HasNickName")
    public Integer getHasNickName() {
        return hasNickName;
    }

    @JSONField(name = "HasNickName")
    public void setHasNickName(Integer hasNickName) {
        this.hasNickName = hasNickName;
    }

    @JSONField(name = "NickName")
    public String getNickName() {
        return nickName;
    }

    @JSONField(name = "NickName")
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @JSONField(name = "UserFace")
    public String getUserFace() {
        return userFace;
    }

    @JSONField(name = "UserFace")
    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    @JSONField(name = "IDCardNumber")
    public String getIdCardNumber() {
        return idCardNumber;
    }

    @JSONField(name = "IDCardNumber")
    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    @JSONField(name = "Mobile")
    public String getMobile() {
        return mobile;
    }

    @JSONField(name = "Mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JSONField(name = "IsMobileValied")
    public Boolean getMobileValidated() {
        return mobileValidated;
    }

    @JSONField(name = "IsMobileValied")
    public void setMobileValidated(Boolean mobileValidated) {
        this.mobileValidated = mobileValidated;
    }

    @JSONField(name = "Balance")
    public BigDecimal getBalance() {
        return balance;
    }

    @JSONField(name = "Balance")
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @JSONField(name = "SecurityQuestion")
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    @JSONField(name = "SecurityQuestion")
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    @JSONField(name = "IsPayPwd")
    public Integer getHasPayPwd() {
        return hasPayPwd;
    }

    @JSONField(name = "IsPayPwd")
    public void setHasPayPwd(Integer hasPayPwd) {
        this.hasPayPwd = hasPayPwd;
    }

    @JSONField(name = "RegisterTime")
    public String getRegisterTime() {
        return registerTime;
    }

    @JSONField(name = "RegisterTime")
    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    @JSONField(name = "NoDistillBalance")
    public BigDecimal getNoDistillBalance() {
        return noDistillBalance;
    }

    @JSONField(name = "NoDistillBalance")
    public void setNoDistillBalance(BigDecimal noDistillBalance) {
        this.noDistillBalance = noDistillBalance;
    }

    @JSONField(name = "DistillBalance")
    public BigDecimal getDistillBalance() {
        return distillBalance;
    }

    @JSONField(name = "DistillBalance")
    public void setDistillBalance(BigDecimal distillBalance) {
        this.distillBalance = distillBalance;
    }

    @JSONField(name = "Status")
    public Integer getStatus() {
        return status;
    }

    @JSONField(name = "Status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JSONField(name = "CityID")
    public Integer getCityId() {
        return cityId;
    }

    @JSONField(name = "CityID")
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @JSONField(name = "BankTypeID")
    public Integer getBankTypeId() {
        return bankTypeId;
    }

    @JSONField(name = "BankTypeID")
    public void setBankTypeId(Integer bankTypeId) {
        this.bankTypeId = bankTypeId;
    }

    @JSONField(name = "BankID")
    public Integer getBankId() {
        return bankId;
    }

    @JSONField(name = "BankID")
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    @JSONField(name = "BankName")
    public String getBankName() {
        return bankName;
    }

    @JSONField(name = "BankName")
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @JSONField(name = "BankCardNumber")
    public String getBankCardNumber() {
        return bankCardNumber;
    }

    @JSONField(name = "BankCardNumber")
    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    @JSONField(name = "UseableCount")
    public Long getUsableCount() {
        return usableCount;
    }

    @JSONField(name = "UseableCount")
    public void setUsableCount(Long usableCount) {
        this.usableCount = usableCount;
    }

    @JSONField(name = "UserExperience")
    public Long getUserIntegral() {
        return userIntegral;
    }

    @JSONField(name = "UserExperience")
    public void setUserIntegral(Long userIntegral) {
        this.userIntegral = userIntegral;
    }

    @JSONField(name = "Userlevel")
    public String getUserLevel() {
        return userLevel;
    }

    @JSONField(name = "Userlevel")
    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    @JSONField(name = "Levelname")
    public String getLevelName() {
        return levelName;
    }

    @JSONField(name = "Levelname")
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @JSONField(name = "Level")
    public Integer getLevel() {
        return level;
    }

    @JSONField(name = "Level")
    public void setLevel(Integer level) {
        this.level = level;
    }

    @JSONField(name = "PhoneBindWx")
    public Integer getPhoneBindWx() {
        return phoneBindWx;
    }

    @JSONField(name = "PhoneBindWx")
    public void setPhoneBindWx(Integer phoneBindWx) {
        this.phoneBindWx = phoneBindWx;
    }

    @JSONField(name = "BindWxName")
    public String getBindWxName() {
        return bindWxName;
    }

    @JSONField(name = "BindWxName")
    public void setBindWxName(String bindWxName) {
        this.bindWxName = bindWxName;
    }

    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /*是否设置登录密码*/
    @JSONField(name = "isLoginPwd")
    public Integer getHasLoginPwd() {
        return hasLoginPwd;
    }

    public void setHasLoginPwd(Integer hasLoginPwd) {
        this.hasLoginPwd = hasLoginPwd;
    }
}
