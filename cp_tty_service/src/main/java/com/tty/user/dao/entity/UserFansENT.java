package com.tty.user.dao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zxh
 * @create 2017-03-06 18:03:22
 **/

@Entity
@Table(name = "user_fans")
public class UserFansENT implements java.io.Serializable {

    /** n_id */
    private Long id;

    /** n_user_id */
    private Long userId;

    /** 被关注人id */
    private Long attentionUserId;

    /** 状态 */
    private Integer status = 1;

    /** d_update_time */
    private Date updateTime;

    /** d_create_time */
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
     * 获取 被关注人id 的值
     * @return Long 
     */
    @Column(name = "n_attention_user_id")
    public Long  getAttentionUserId() {
        return attentionUserId;
    }

    /**
     * 设置被关注人id 的值
     * @param attentionUserId 被关注人id
     */
    public void setAttentionUserId(Long attentionUserId) {
        this.attentionUserId = attentionUserId;
    }

    /**
     * 获取 状态 的值
     * @return Integer 
     */
    @Column(name = "n_status")
    public Integer  getStatus() {
        return status;
    }

    /**
     * 设置状态 的值
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 d_update_time 的值
     * @return Date 
     */
    @Column(name = "d_update_time")
    public Date  getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置d_update_time 的值
     * @param updateTime d_update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 d_create_time 的值
     * @return Date 
     */
    @Column(name = "d_create_time")
    public Date  getCreateTime() {
        return createTime;
    }

    /**
     * 设置d_create_time 的值
     * @param createTime d_create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}