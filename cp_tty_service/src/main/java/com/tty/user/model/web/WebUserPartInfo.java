package com.tty.user.model.web;

/**
 * @author 用户信息
 * @date 2017/1/13
 * @Description .net缓存中拿到的用户部分信息
 */
public class WebUserPartInfo {
    private String mobile;
    private String realityName;
    private String idCardNumber;
    private String isMobileValided;

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

    public String getIsMobileValided() {
        return isMobileValided;
    }

    public void setIsMobileValided(String isMobileValided) {
        this.isMobileValided = isMobileValided;
    }
}
