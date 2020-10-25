package com.tty.user.dto;

/**
 * Created by pdl on 2017/4/7.
 */
public class UserLevelAndIntegralDTO {
    //用户积分
    private Integer userIntegral;
    //成长值
    private Long userGrowUps;
    //用户等级
    private Integer userLevel;

    public Integer getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(Integer userIntegral) {
        this.userIntegral = userIntegral;
    }

    public Long getUserGrowUps() {
        return userGrowUps;
    }

    public void setUserGrowUps(Long userGrowUps) {
        this.userGrowUps = userGrowUps;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

}
