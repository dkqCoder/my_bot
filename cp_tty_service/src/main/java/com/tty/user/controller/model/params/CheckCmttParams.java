package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/4/2.
 */

/**
 * @author shenwei
 * @create 2017-04-02
 */
public class CheckCmttParams {
    private String mobile;
    private String verifycode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }
}
