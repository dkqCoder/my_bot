package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * @author  zxh
 */
public class NetRegisterDTO {

    @JSONField(name = "UserId")
    private Long userId;
    @JSONField(name = "EntranceCode")
    private String entranceCode="";
    @JSONField(name = "GroupID")
    private Integer groupID = 0;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEntranceCode() {
        return entranceCode;
    }

    public void setEntranceCode(String entranceCode) {
        this.entranceCode = entranceCode;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
}


