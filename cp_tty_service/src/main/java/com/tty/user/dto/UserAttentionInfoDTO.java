package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;


public class UserAttentionInfoDTO {
    @JSONField(name = "IsAttention")
    private Boolean isAttention;
    @JSONField(name = "Level")
    private String level = "";
    @JSONField(name = "WinRate")
    private Integer winRate = 0;
    @JSONField(name = "EarningsRate")
    private Integer earningsRate = 0;
    @JSONField(name = "FollowWinMoney")
    private Integer followWinMoney = 0;
    @JSONField(name = "DateTime")
    private String dateTime = "";
    @JSONField(name = "IsWin")
    private Boolean isWin;
    @JSONField(name = "RecommCount")
    private Integer recommCount = 0;
    @JSONField(name = "UserID")
    private Long userID;
    @JSONField(name = "UserName")
    private String userName = "";
    @JSONField(name = "UserFace")
    private String userFace = "";
    @JSONField(name = "Userlevel")
    private String userLevel = "";
    @JSONField(name = "Levelname")
    private String levelName = "";

    public Boolean getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(Boolean attention) {
        isAttention = attention;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getWinRate() {
        return winRate;
    }

    public void setWinRate(Integer winRate) {
        this.winRate = winRate;
    }

    public Integer getEarningsRate() {
        return earningsRate;
    }

    public void setEarningsRate(Integer earningsRate) {
        this.earningsRate = earningsRate;
    }

    public Integer getFollowWinMoney() {
        return followWinMoney;
    }

    public void setFollowWinMoney(Integer followWinMoney) {
        this.followWinMoney = followWinMoney;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getWin() {
        return isWin;
    }

    public void setWin(Boolean win) {
        isWin = win;
    }

    public Integer getRecommCount() {
        return recommCount;
    }

    public void setRecommCount(Integer recommCount) {
        this.recommCount = recommCount;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}