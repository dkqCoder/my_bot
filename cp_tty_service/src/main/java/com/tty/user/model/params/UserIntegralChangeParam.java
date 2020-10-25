package com.tty.user.model.params;/**
 * Created by shenwei on 2016/12/30.
 */

/**
 * @author shenwei
 * @create 2016-12-30
 */
public class UserIntegralChangeParam {
    private Long userId;
    private Long sourceId;
    private Integer integral;
    private Integer type;
    private String description;
    private String traceId = "";

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
