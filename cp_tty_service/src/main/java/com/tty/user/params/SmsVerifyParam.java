package com.tty.user.params;/**
 * Created by shenwei on 2017/3/16.
 */

/**
 * @author shenwei
 * @create 2017-03-16
 * 获取短信验证码登陆参数
 */
public class SmsVerifyParam {
    private String mobile;
    private Integer type;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
