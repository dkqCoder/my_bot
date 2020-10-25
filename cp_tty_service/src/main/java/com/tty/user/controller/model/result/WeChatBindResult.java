package com.tty.user.controller.model.result;/**
 * Created by shenwei on 2017/6/29.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-06-29
 */
public class WeChatBindResult {
    private Integer phoneBindWx;

    @JSONField(name = "PhoneBindWx")
    public Integer getPhoneBindWx() {
        return phoneBindWx;
    }

    @JSONField(name = "PhoneBindWx")
    public void setPhoneBindWx(Integer phoneBindWx) {
        this.phoneBindWx = phoneBindWx;
    }
}
