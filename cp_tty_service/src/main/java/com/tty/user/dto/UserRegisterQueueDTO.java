package com.tty.user.dto;

/**
 * 用户注册上报
 *
 * @author zxh
 * @create 2017-03-29
 */
public class UserRegisterQueueDTO {
    private Long userId;
    private String mobile;
    private String platFormCode;
    private String cmdName = "app_zz";
    private String traceId;

    public UserRegisterQueueDTO() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public UserRegisterQueueDTO(Long userId, String mobile,String platFormCode, String cmdName, String traceId) {
        this.userId = userId;
        this.mobile = mobile;
        this.platFormCode=platFormCode;
        this.cmdName = cmdName;
        this.traceId = traceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPlatFormCode() {
        return platFormCode;
    }

    public void setPlatFormCode(String platFormCode) {
        this.platFormCode = platFormCode;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getCmdName() {
        return cmdName;
    }

    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }
}
