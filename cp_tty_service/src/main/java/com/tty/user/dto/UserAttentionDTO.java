package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class UserAttentionDTO {
    @JSONField(name = "FansCount")
    private Integer fansCount;
    @JSONField(name = "IsAttention")
    private Boolean attention;
    @JSONField(name = "AttentionCount")
    private Integer attentionCount;
    @JSONField(name = "showfirst")
    private Integer showFirst;

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
    @JSONField(name = "IsAttention")
    public Boolean getIsAttention() {
        return attention;
    }
    @JSONField(name = "IsAttention")
    public void setIsAttention(Boolean attention) {
        this.attention = attention;
    }

    public Integer getShowFirst() {
        return showFirst;
    }

    public void setShowFirst(Integer showFirst) {
        this.showFirst = showFirst;
    }
}