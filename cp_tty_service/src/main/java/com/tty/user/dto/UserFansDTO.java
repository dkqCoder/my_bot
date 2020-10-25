package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;


public class UserFansDTO {
    @JSONField(name = "IsAttention")
    private Boolean isAttention;
    @JSONField(name = "FansUserID")
    private Long fansUserID;
    @JSONField(name = "FansName")
    private String fansName;
    @JSONField(name = "UserFace")
    private String userFace;

    public String getFansName() {
        return fansName;
    }

    public void setFansName(String fansName) {
        this.fansName = fansName;
    }

    public Long getFansUserID() {
        return fansUserID;
    }

    public void setFansUserID(Long fansUserID) {
        this.fansUserID = fansUserID;
    }

    public Boolean getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(Boolean attention) {
        isAttention = attention;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }
}