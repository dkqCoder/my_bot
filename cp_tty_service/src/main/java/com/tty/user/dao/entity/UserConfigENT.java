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
@Table(name = "user_config")
public class UserConfigENT implements Serializable {

    /** 主键ID */
    private Long id;

    /** 类型 */
    private String type;

    /** Key */
    private String key;

    /** 值 */
    private String value;

    /** 描述 */
    private String description = "NULL";

    /** 状态 0、无效，1、有效 */
    private Integer status = 0;

    /** 创建时间 */
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Date createTime;




    /**
     * 获取 主键ID 的值
     * @return Long 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    public Long  getId() {
        return id;
    }

    /**
     * 设置主键ID 的值
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 类型 的值
     * @return String 
     */
    @Column(name = "s_type")
    public String  getType() {
        return type;
    }

    /**
     * 设置类型 的值
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取 Key 的值
     * @return String 
     */
    @Column(name = "s_key")
    public String  getKey() {
        return key;
    }

    /**
     * 设置Key 的值
     * @param key Key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取 值 的值
     * @return String 
     */
    @Column(name = "s_value")
    public String  getValue() {
        return value;
    }

    /**
     * 设置值 的值
     * @param value 值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取 描述 的值
     * @return String 
     */
    @Column(name = "s_description")
    public String  getDescription() {
        return description;
    }

    /**
     * 设置描述 的值
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 状态 0、无效，1、有效 的值
     * @return Integer 
     */
    @Column(name = "n_status")
    public Integer  getStatus() {
        return status;
    }

    /**
     * 设置状态 0、无效，1、有效 的值
     * @param status 状态 0、无效，1、有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 创建时间 的值
     * @return Date 
     */
    @Column(name = "d_create_time")
    public Date  getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间 的值
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}