package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class UserRelationDTO {
    @JSONField(name = "FansCount")
    private Integer fansCount;
    @JSONField(name = "IsAttention")
    private Boolean isAttention;
    @JSONField(name = "AttentionCount")
    private Integer attentionCount;
    @JSONField(name = "RewardCount")
    private Integer  rewardCount;
    @JSONField(name = "attuseface")
    private String attuseFace;
    @JSONField(name = "isself")
    private Integer isSelf;
    @JSONField(name = "Userlevel")
    private String userLevel;
    @JSONField(name = "Levelname")
    private String levelName;

    public Integer getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(Integer attentionCount) {
        this.attentionCount = attentionCount;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Boolean getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(Boolean attention) {
        isAttention = attention;
    }


    public Integer getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(Integer rewardCount) {
        this.rewardCount = rewardCount;
    }

    public String getAttuseFace() {
        return attuseFace;
    }

    public void setAttuseFace(String attuseFace) {
        this.attuseFace = attuseFace;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
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