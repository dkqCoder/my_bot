package com.tty.user.model.result;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by jdd on 2016/12/23.
 */
public class UserIntegralMissionModel {
    private Long endMisId;//用户任务完成表主键
    private Long misId = new Long(0);
    private String misName;
    private String misDis;
    private Integer isEnd;
    private Integer isGet;
    private Integer misType;
    private Integer integralGet;
    private Integer misCategory;
    private Integer buttonStatus;
    private String buttonText;
    private Integer count;//任务完成阶段(针对周期性)
    private Date createDate;
    private Date firstDayWeek;//当前周第一天日期
    private Date lastDayWeek;//当前周最后一天日期
    private Long userId;
    private Integer weekDay;//周几(1是星期一、2是星期二、3是星期三、4是星期四、5是星期五、6是星期六、0是星期日)
    private Integer missCounts;//触发值(比如朝夕相处5天，存5)
    private Integer priority;//优先级
    private String traceId = "";//追踪id

    public Long getMisId() {
        return misId;
    }

    public void setMisId(Long misId) {
        this.misId = misId;
    }

    public String getMisName() {
        return misName;
    }

    public void setMisName(String misName) {
        this.misName = misName;
    }

    public String getMisDis() {
        return misDis;
    }

    public void setMisDis(String misDis) {
        this.misDis = misDis;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public Integer getIsGet() {
        return isGet;
    }

    public void setIsGet(Integer isGet) {
        this.isGet = isGet;
    }

    public Integer getMisType() {
        return misType;
    }

    public void setMisType(Integer misType) {
        this.misType = misType;
    }

    public Integer getIntegralGet() {
        return integralGet;
    }

    public void setIntegralGet(Integer integralGet) {
        this.integralGet = integralGet;
    }

    public Integer getMisCategory() {
        return misCategory;
    }

    public void setMisCategory(Integer misCategory) {
        this.misCategory = misCategory;
    }

    public Integer getButtonStatus() {
        return buttonStatus;
    }

    public void setButtonStatus(Integer buttonStatus) {
        this.buttonStatus = buttonStatus;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getEndMisId() {
        return endMisId;
    }

    public void setEndMisId(Long endMisId) {
        this.endMisId = endMisId;
    }

    public Date getFirstDayWeek() {
        return firstDayWeek;
    }

    public void setFirstDayWeek(Date firstDayWeek) {
        this.firstDayWeek = firstDayWeek;
    }

    public Date getLastDayWeek() {
        return lastDayWeek;
    }

    public void setLastDayWeek(Date lastDayWeek) {
        this.lastDayWeek = lastDayWeek;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public Integer getMissCounts() {
        return missCounts;
    }

    public void setMissCounts(Integer missCounts) {
        this.missCounts = missCounts;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

}
