package com.tty.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author chenpengfei {chenpengfei@jiangduoduo.com}
 * @Date 2017/10/30 17:15
 */
@Entity
@Table(name = "user_filter_detail")
public class UserFilterDetailENT implements Serializable {

    private Long id;

    private Long userFilterId;

    private String context;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String operator;

    private String code;

    private String type;

    private String entranceCode;

    @Id
    @Column(name = "n_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "s_status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "d_create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "d_update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "s_operator")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "n_user_filter_id")
    public Long getUserFilterId() {
        return userFilterId;
    }

    public void setUserFilterId(Long userFilterId) {
        this.userFilterId = userFilterId;
    }

    @Column(name = "s_context")
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Column(name = "s_code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "s_type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "s_entrance_code")
    public String getEntranceCode() {
        return entranceCode;
    }

    public void setEntranceCode(String entranceCode) {
        this.entranceCode = entranceCode;
    }
}
