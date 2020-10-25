package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/6/27.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-06-27
 */
public class WeChatLoginParams {
    private String thdUserId;
    private String thdUserName;

    @JSONField(name = "thdUserId")
    public String getThdUserId() {
        return thdUserId;
    }

    @JSONField(name = "thdUserId")
    public void setThdUserId(String thdUserId) {
        this.thdUserId = thdUserId;
    }

    @JSONField(name = "thdUserName")
    public String getThdUserName() {
        return thdUserName;
    }

    @JSONField(name = "thdUserName")
    public void setThdUserName(String thdUserName) {
        this.thdUserName = thdUserName;
    }
}
