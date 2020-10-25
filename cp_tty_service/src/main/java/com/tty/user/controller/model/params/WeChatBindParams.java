package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/6/28.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-06-28
 */
public class WeChatBindParams {
    private String mobile;

    @JSONField(name = "mobile")
    public String getMobile() {
        return mobile;
    }
    
    @JSONField(name = "mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
