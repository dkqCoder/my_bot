package com.tty.sms.dto;

import java.util.Date;

/**
 * @Author: sunyishun
 * @Date: 2020/10/23
 * @Description:
 */
public class MessageDTO {
    String mobileList;
    String body;
    int SMSType;
    String systemCode;
    Date createTime;

    public MessageDTO() {
    }

    public MessageDTO(String mobileList, String body, int SMSType, String systemCode) {
        this.mobileList = mobileList;
        this.body = body;
        this.SMSType = SMSType;
        this.systemCode = systemCode;
        this.createTime = new Date();
    }

    public String getMobileList() {
        return mobileList;
    }

    public void setMobileList(String mobileList) {
        this.mobileList = mobileList;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getSMSType() {
        return SMSType;
    }

    public void setSMSType(int SMSType) {
        this.SMSType = SMSType;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
