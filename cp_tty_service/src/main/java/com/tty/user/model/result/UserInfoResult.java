package com.tty.user.model.result;

/**
 * @author zhudonghai
 * @Date 2017/2/14
 * @Description 用户信息，比如用户积分余额，用户等级等
 */
public class UserInfoResult {
    private Long userId;//用户id
    private Long integral;//用户积分
    private Integer isSignToday;//今日是否签到
    private Integer level;//用户等级
    private String levelname;//等级名称

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public Integer getIsSignToday() {
        return isSignToday;
    }

    public void setIsSignToday(Integer isSignToday) {
        this.isSignToday = isSignToday;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }
}
