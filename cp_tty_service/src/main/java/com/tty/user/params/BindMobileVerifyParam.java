package com.tty.user.params;/**
 * Created by shenwei on 2017/3/15.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-15
 * 绑定手机获取验证码参数
 */
public class BindMobileVerifyParam {
    @JSONField(name = "OldMobile")
    private String oldMobile;
    @JSONField(name = "Mobile")
    private String mobile;
    /*类型*/
    private Integer type;

    public String getOldMobile() {
        return oldMobile;
    }

    public void setOldMobile(String oldMobile) {
        this.oldMobile = oldMobile;
    }

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
