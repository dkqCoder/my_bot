package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/3/23.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-23
 */
public class CheckQuickRegisterParams {
    @JSONField(name = "verifycode")
    private String verifyCode;
    @JSONField(name = "mobile")
    private String mobile;
    /** 通付盾tokenId **/
    @JSONField(name = "tokenId")
    private String tokenId;

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

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
