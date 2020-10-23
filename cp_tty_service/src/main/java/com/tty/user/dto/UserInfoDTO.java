package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;


public class UserInfoDTO {
    @JSONField(name = "UserID")
    private String userId;
    @JSONField(name = "RegisterTime")
    private String registerTime;
    @JSONField(name = "RegisterIP")
    private String registerIp;
    @JSONField(name = "LastLoginTime")
    private String lastLoginTime;
    @JSONField(name = "LastLoginIP")
    private String lastLoginIp;
    @JSONField(name = "RegisterFrom")
    private String registerFrom;
    @JSONField(name = "NickName")
    private String nickName;
    @JSONField(name = "AlipayID")
    private String aliPayId;
    @JSONField(name = "AlipayName")
    private String aliPayName;
    @JSONField(name = "IsAlipayValided")
    private Boolean aliPayValidated;
    @JSONField(name = "Memo")
    private String remark;
    @JSONField(name = "PayPassword")
    private String payPass;
    @JSONField(name = "UserFace")
    private String userFace;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getRegisterFrom() {
        return registerFrom;
    }

    public void setRegisterFrom(String registerFrom) {
        this.registerFrom = registerFrom;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAliPayId() {
        return aliPayId;
    }

    public void setAliPayId(String aliPayId) {
        this.aliPayId = aliPayId;
    }

    public String getAliPayName() {
        return aliPayName;
    }

    public void setAliPayName(String aliPayName) {
        this.aliPayName = aliPayName;
    }

    public Boolean getAliPayValidated() {
        return aliPayValidated;
    }

    public void setAliPayValidated(Boolean aliPayValidated) {
        this.aliPayValidated = aliPayValidated;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayPass() {
        return payPass;
    }

    public void setPayPass(String payPass) {
        this.payPass = payPass;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }
}