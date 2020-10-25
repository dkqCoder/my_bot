package com.tty.user.model.result;


import java.util.Date;

/**
 * @author zhudonghai
 * @date 2016-12-14
 * @Description 我的积分首页任务列表输出类
 */
public class UserIntegralMissionResult {
    //@JSONField(serialize = false)
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
    //@JSONField(serialize = false)
    private Integer count;//任务完成阶段(针对周期性)
    //@JSONField(serialize = false)
    private Date createDate;
    //@JSONField(serialize = false)
    private Date firstDayWeek;//当前周第一天日期
    //@JSONField(serialize = false)
    private Date lastDayWeek;//当前周最后一天日期
    //@JSONField(serialize = false)
    private Integer missCounts;//触发值(比如朝夕相处5天，存5)
    //@JSONField(serialize = false)
    private Integer priority;//优先级
    private Integer misPeriod;//周期(0一次性,1每日,7每周)
    private Integer statusSort;//根据isend,isadd字段设置排序值;0,1,2

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

    public Integer getMisPeriod() {
        return misPeriod;
    }

    public void setMisPeriod(Integer misPeriod) {
        this.misPeriod = misPeriod;
    }

    public Integer getStatusSort() {
        return statusSort;
    }

    public void setStatusSort(Integer statusSort) {
        this.statusSort = statusSort;
    }
}
