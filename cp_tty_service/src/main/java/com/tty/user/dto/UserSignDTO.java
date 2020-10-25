package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by linian on 2017/5/10.
 */
public class UserSignDTO {

    @JSONField(name = "TodayStatus")
    private Integer todayStatus;
    @JSONField(name = "SignDay")
    private Integer signDay;

    public Integer getTodayStatus() {
        return todayStatus;
    }

    public void setTodayStatus(Integer todayStatus) {
        this.todayStatus = todayStatus;
    }

    public Integer getSignDay() {
        return signDay;
    }

    public void setSignDay(Integer signDay) {
        this.signDay = signDay;
    }
}
