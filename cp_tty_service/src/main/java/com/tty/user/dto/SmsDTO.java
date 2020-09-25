package com.tty.user.dto;/**
 * Created by shenwei on 2017/11/7.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-11-07
 */
public class SmsDTO {
    private String mobileList;
    private String body;
    private Integer SMSType;
    private String systemFrom;

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

    @JSONField(name = "SMSType")
    public Integer getSMSType() {
        return SMSType;
    }

    @JSONField(name = "SMSType")
    public void setSMSType(Integer SMSType) {
        this.SMSType = SMSType;
    }

    public String getSystemFrom() {
        return systemFrom;
    }

    public void setSystemFrom(String systemFrom) {
        this.systemFrom = systemFrom;
    }
}
