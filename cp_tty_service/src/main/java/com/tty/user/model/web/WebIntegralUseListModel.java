package com.tty.user.model.web;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by jdd on 2017/1/22.
 */
public class WebIntegralUseListModel {
    private Long userId;//用户id
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;//创建时间
    private String description;//描述
    private Integer integral;//变动积分数
    private Integer type;//1任务 2商城


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
