package com.tty.user.controller.model.params;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zxh
 * @create 2017-03-23
 */
public class CheckVerifyCodeParams {
    @JSONField(name = "verifycode")
    private String verifyCode;
    private String mobile;
    private String pwd;
    private Integer type;
    @JSONField(name = "usertype")
    private Integer userType;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
