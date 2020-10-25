package com.tty.user.model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhudonghai
 * @date 2016-12-14
 * @Description 用户积分首页信息输出类
 */
public class UserIntegralHomePageResult {
    private String userName;
    private Long integral = new Long(0);
    private String beat;
    private Long sign = new Long(0);
    private Integer isSignToday = new Integer(0);
    private Integer signAward = new Integer(0);
    private Integer isFirstVisit;//是否首次进入我的积分页
    private Integer firstVisitIntegral;//首次进入我的积分页奖励的积分值
    private List<UserIntegralMissionResult> missions = new ArrayList<>();

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public String getBeat() {
        return beat;
    }

    public void setBeat(String beat) {
        this.beat = beat;
    }

    public Long getSign() {
        return sign;
    }

    public void setSign(Long sign) {
        this.sign = sign;
    }

    public Integer getSignAward() {
        return signAward;
    }

    public void setSignAward(Integer signAward) {
        this.signAward = signAward;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIsSignToday() {
        return isSignToday;
    }

    public void setIsSignToday(Integer isSignToday) {
        this.isSignToday = isSignToday;
    }

    public List<UserIntegralMissionResult> getMissions() {
        return missions;
    }

    public void setMissions(List<UserIntegralMissionResult> missions) {
        this.missions = missions;
    }

    public Integer getIsFirstVisit() {
        return isFirstVisit;
    }

    public void setIsFirstVisit(Integer isFirstVisit) {
        this.isFirstVisit = isFirstVisit;
    }

    public Integer getFirstVisitIntegral() {
        return firstVisitIntegral;
    }

    public void setFirstVisitIntegral(Integer firstVisitIntegral) {
        this.firstVisitIntegral = firstVisitIntegral;
    }
}
