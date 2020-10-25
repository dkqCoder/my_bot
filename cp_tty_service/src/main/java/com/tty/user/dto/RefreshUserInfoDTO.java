package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zxh
 * @create 2017-03-31
 */
public class RefreshUserInfoDTO {
    @JSONField(name = "UserID")
    private Long userId;
    @JSONField(name = "Mobile")
    private String mobile = "";
    @JSONField(name = "IsMobileValided")
    private Boolean isMobileValidated = true;
    @JSONField(name = "RealityName")
    private String realityName = "";
    @JSONField(name = "IDCardNumber")
    private String idCardNumber = "";

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

    public Boolean getIsMobileValidated() {
        return isMobileValidated;
    }

    public void setIsMobileValidated(Boolean mobileValidated) {
        isMobileValidated = mobileValidated;
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
}
