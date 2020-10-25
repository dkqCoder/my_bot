package com.tty.user.dto;/**
 * Created by shenwei on 2017/3/30.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-30  .net
 */
public class UserThirdInfoDTO {
    @JSONField(name = "UserID")
    private Long userId;
    @JSONField(name = "ThirdID")
    private String thirdId;
    @JSONField(name = "ThirdName")
    private String thirdName;
    @JSONField(name = "RegisterType")
    private String registerType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }
}
