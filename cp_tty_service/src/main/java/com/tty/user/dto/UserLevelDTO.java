package com.tty.user.dto;

/**
 * Created by linian on 2018/1/16.
 */
public class UserLevelDTO {
    private Long userId;
    private Integer level;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
