package com.tty.user.dao.entity;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;

/**
 * 
 * @author zxh
 * @create 2017-08-24 14:17:57
 **/

@Entity
@Table(name = "user_entrance_deny_time")
public class UserEntranceDenyTimeENT implements Serializable {

    /** n_id */
    private Long id;

    /** 渠道名称 */
    private String cmdName;

    /** 开始时间 */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** d_end_time */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;




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
     * 获取 渠道名称 的值
     * @return String 
     */
    @Column(name = "s_cmd_name")
    public String  getCmdName() {
        return cmdName;
    }

    /**
     * 设置渠道名称 的值
     * @param cmdName 渠道名称
     */
    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    /**
     * 获取 开始时间 的值
     * @return Date 
     */
    @Column(name = "d_start_time")
    public Date  getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间 的值
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取 d_end_time 的值
     * @return Date 
     */
    @Column(name = "d_end_time")
    public Date  getEndTime() {
        return endTime;
    }

    /**
     * 设置d_end_time 的值
     * @param endTime d_end_time
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }



}