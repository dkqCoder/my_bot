package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/7/28.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-07-28
 */
public class FilterEntranceParams {
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
