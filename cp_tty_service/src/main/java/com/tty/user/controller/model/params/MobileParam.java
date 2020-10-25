package com.tty.user.controller.model.params;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zxh
 * @create 2017-03-15
 */
public class MobileParam {
    @JSONField(name = "Mobile")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
