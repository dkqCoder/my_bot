package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/3/14.
 */

/**
 * @author shenwei
 * @create 2017-03-14
 */
public class PayPassParams {
    private String verifyCode;
    private String newPw;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getNewPw() {
        return newPw;
    }

    public void setNewPw(String newPw) {
        this.newPw = newPw;
    }
}
