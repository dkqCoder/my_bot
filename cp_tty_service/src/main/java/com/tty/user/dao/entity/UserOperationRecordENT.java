package com.tty.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;

/**
 * 用户操作记录
 * @author zxh
 * @create 2017-06-28 17:39:47
 **/

@Entity
@Table(name = "user_operation_record")
public class UserOperationRecordENT implements Serializable {

    /** n_id */
    private Long id;

    /** n_user_id */
    private Long userId;

    /** 分组(用户:1，订单:2) */
    private Integer group;

    /** 类型，（登录1，签到2，**） */
    private Integer type;

    /** s_trace_id */
    private String traceId;

    /** 内容 */
    private String content;

    /** 注册时间 */
    private Date createTime;




    /**
     * 获取 n_id 的值
     * @return Long 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    public Long  getId() {
        return id;
    }

    /**
     * 设置n_id 的值
     * @param id n_id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 n_user_id 的值
     * @return Long 
     */
    @Column(name = "n_user_id")
    public Long  getUserId() {
        return userId;
    }

    /**
     * 设置n_user_id 的值
     * @param userId n_user_id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取 分组(用户:1，订单:2) 的值
     * @return Integer 
     */
    @Column(name = "n_group")
    public Integer  getGroup() {
        return group;
    }

    /**
     * 设置分组(用户:1，订单:2) 的值
     * @param group 分组(用户:1，订单:2)
     */
    public void setGroup(Integer group) {
        this.group = group;
    }

    /**
     * 获取 类型，（登录1，签到2，**） 的值
     * @return Integer 
     */
    @Column(name = "n_type")
    public Integer  getType() {
        return type;
    }

    /**
     * 设置类型，（登录1，签到2，**） 的值
     * @param type 类型，（登录1，签到2，**）
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取 s_trace_id 的值
     * @return String 
     */
    @Column(name = "s_trace_id")
    public String  getTraceId() {
        return traceId;
    }

    /**
     * 设置s_trace_id 的值
     * @param traceId s_trace_id
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     * 获取 内容 的值
     * @return String 
     */
    @Column(name = "s_content")
    public String  getContent() {
        return content;
    }

    /**
     * 设置内容 的值
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取 注册时间 的值
     * @return Date 
     */
    @Column(name = "d_create_time")
    public Date  getCreateTime() {
        return createTime;
    }

    /**
     * 设置注册时间 的值
     * @param createTime 注册时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}