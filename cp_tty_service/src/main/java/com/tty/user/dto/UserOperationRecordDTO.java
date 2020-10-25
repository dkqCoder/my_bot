package com.tty.user.dto;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class UserOperationRecordDTO {

    /**
     * n_id
     */
    private Long id;

    /**
     * n_user_id
     */
    private Long userId;

    /**
     * 分组(用户:1，订单:2)
     */
    private Integer group;
    private String traceId;

    private String groups;

    private String types;
    /**
     * 类型，（登录1，签到2，**）
     */
    private Integer type;

    /**
     * 内容
     */
    private String content;

    /**
     * 注册时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "UserOperationRecordDTO{" + "id=" + id + ",userId=" + userId + ",group=" + group + ",type=" + type + ",content=" + content + ",createTime=" + createTime + "}";
    }
}