package com.tty.user.dto;


import com.alibaba.fastjson.annotation.JSONField;

public class UserMasterInfoDTO{



    @JSONField(name = "UserID")
    private Long userId;

    @JSONField(name = "NickName")
    private String nickName;

    @JSONField(name = "UserFace")
    private String userFace;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }
}