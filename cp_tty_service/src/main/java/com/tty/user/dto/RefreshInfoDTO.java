package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ln
 */
public class RefreshInfoDTO {
    @JSONField(name = "UserID")
    private Long userId;
    @JSONField(name = "RegisterTime")
    private String registerTime = "";
    @JSONField(name = "RegisterIP")
    private String registerIp = "";
    @JSONField(name = "LastLoginTime")
    private String lastLoginTime = "";
    @JSONField(name = "LastLoginIP")
    private String lastLoginIp = "";
    @JSONField(name = "RegisterFrom")
    private Integer registerFrom = 0;
    @JSONField(name = "NickName")
    private String nickName = "";
    @JSONField(name = "Sex")
    private String sex = "";
    @JSONField(name = "BirthDay")
    private String birthDay = "";
    @JSONField(name = "Address")
    private String address = "";
    @JSONField(name = "CityID")
    private Integer cityId = 0;
    @JSONField(name = "Email")
    private String email = "";
    @JSONField(name = "IsEmailValided")
    private Boolean isEmailValided = false;
    @JSONField(name = "QQ")
    private String qq = "";
    @JSONField(name = "IsQQValided")
    private Boolean isQQValided = false;
    @JSONField(name = "AlipayID")
    private String alipayID = "";
    @JSONField(name = "AlipayName")
    private String alipayName = "";
    @JSONField(name = "IsAlipayValided")
    private Boolean isAlipayValided = false;
    @JSONField(name = "Memo")
    private String memo = "";
    @JSONField(name = "PayPassword")
    private String payPassword = "";
    @JSONField(name = "SecurityQuestion")
    private String securityQuestion = "";
    @JSONField(name = "SecurityAnswer")
    private String securityAnswer = "";
    @JSONField(name = "IsSMSNotice")
    private Boolean isSMSNotice = true;
    @JSONField(name = "RecommendID")
    private Integer recommendID = 0;
    @JSONField(name = "RegisterType")
    private String registerType = "";
    @JSONField(name = "RegisterUrl")
    private String registerUrl = "";
    @JSONField(name = "IsBindBank")
    private Boolean isBindBank = false;
    @JSONField(name = "PayMoney")
    private Integer payMoney = 0;
    @JSONField(name = "BuyMoney")
    private Integer buyMoney = 0;
    @JSONField(name = "DtlMoney")
    private Integer dtlMoney = 0;
    @JSONField(name = "IsUmPay")
    private Boolean isUmPay = false;
    @JSONField(name = "MemberTypeID")
    private Integer memberTypeID = 0;
    @JSONField(name = "MemberStatus")
    private Integer memberStatus = 0;
    @JSONField(name = "UserFace")
    private String userFace = "";

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
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

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getRegisterFrom() {
        return registerFrom;
    }

    public void setRegisterFrom(Integer registerFrom) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Boolean getIsEmailValided() {
        return isEmailValided;
    }

    public void setIsEmailValided(Boolean emailValided) {
        isEmailValided = emailValided;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Boolean getIsQQValided() {
        return isQQValided;
    }

    public void setIsQQValided(Boolean QQValided) {
        isQQValided = QQValided;
    }

    public String getAlipayID() {
        return alipayID;
    }

    public void setAlipayID(String alipayID) {
        this.alipayID = alipayID;
    }

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }

    public Boolean getIsAlipayValided() {
        return isAlipayValided;
    }

    public void setIsAlipayValided(Boolean alipayValided) {
        isAlipayValided = alipayValided;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public Boolean getIsSMSNotice() {
        return isSMSNotice;
    }

    public void setIsSMSNotice(Boolean SMSNotice) {
        isSMSNotice = SMSNotice;
    }

    public Integer getRecommendID() {
        return recommendID;
    }

    public void setRecommendID(Integer recommendID) {
        this.recommendID = recommendID;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    public Boolean getIsBindBank() {
        return isBindBank;
    }

    public void setIsBindBank(Boolean bindBank) {
        isBindBank = bindBank;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(Integer buyMoney) {
        this.buyMoney = buyMoney;
    }

    public Integer getDtlMoney() {
        return dtlMoney;
    }

    public void setDtlMoney(Integer dtlMoney) {
        this.dtlMoney = dtlMoney;
    }

    public Boolean getIsUmPay() {
        return isUmPay;
    }

    public void setIsUmPay(Boolean umPay) {
        isUmPay = umPay;
    }

    public Integer getMemberTypeID() {
        return memberTypeID;
    }

    public void setMemberTypeID(Integer memberTypeID) {
        this.memberTypeID = memberTypeID;
    }

    public Integer getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }
}