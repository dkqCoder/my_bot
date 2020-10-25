package com.tty.user.dto;

import java.util.Date;


public class UserLoginRecordDTO{

    /** n_id */
    private Long id;

    /** n_user_id */
    private Long userId;

    /** 登录时间 */
    private Date loginTime;

    /** 渠道号 */
    private String cmdName;

    /** 手机类型 */
    private String phoneType;

    /** 系统版本 */
    private String osVersion;

    /** 客户端版本 */
    private String appVersion;

    /** 机型 */
    private String phoneModel;




    public Long  getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long  getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date  getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String  getCmdName() {
        return cmdName;
    }

    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    public String  getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String  getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String  getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String  getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }



    @Override
    public String toString() {
        return "UserLoginRecordDTO{"+"id="+id+",userId="+userId+",loginTime="+loginTime+",cmdName="+cmdName+",phoneType="+phoneType+",osVersion="+osVersion+",appVersion="+appVersion+",phoneModel="+phoneModel+"}";
    }
}