package com.tty.user.dto;/**
 * Created by shenwei on 2017/3/15.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-15
 */
public class UserValidateDTO {
    @JSONField(name = "UserID")
    private Long userId;
    @JSONField(name = "Mobile")
    private String mobile;
    @JSONField(name = "IsMobileValided")
    private Boolean isMobileValidated;
    @JSONField(name = "RealityName")
    private String realityName;
    @JSONField(name = "IDCardNumber")
    private String idCardNumber;

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

    public Boolean getMobileValidated() {
        return isMobileValidated;
    }

    public void setMobileValidated(Boolean mobileValidated) {
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
