package com.tty.user.dto.mission;

/**
 * @author chenlongfei
 * @date 2016-12-14
 * 用户任务上报实体类
 */
public class UserMissionAddModel {

    private Long missionId;
    private Integer missionType;
    private Float money;
    private Integer lotteryId;
    private String dateTime;
    private Long userId;
    private String traceId = "";

    public Integer getMissionType() {
        return missionType;
    }

    public void setMissionType(Integer missionType) {
        this.missionType = missionType;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMissionId() { return missionId;}

    public void setMissionId(Long missionId) {this.missionId = missionId;}

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

}
