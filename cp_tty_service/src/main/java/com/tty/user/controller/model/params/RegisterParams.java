package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/3/27.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-27
 */
public class RegisterParams {
    @JSONField(name = "Name")
    private String name;
    @JSONField(name = "Pw")
    private String passWord;
    @JSONField(name = "CmdID")
    private Integer cmdId;
    @JSONField(name = "Registype")
    private String registype;
    @JSONField(name = "code")
    private String code;

    @JSONField(name = "SmsType")
    private Integer smsType = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getCmdId() {
        return cmdId;
    }

    public void setCmdId(Integer cmdId) {
        this.cmdId = cmdId;
    }

    public String getRegistype() {
        return registype;
    }

    public void setRegistype(String registype) {
        this.registype = registype;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }
}
