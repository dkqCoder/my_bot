package com.tty.user.dao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 
 * @create 2017-04-04 11:28:28
 **/

@Entity
@Table(name = "user_third_info")
public class UserThirdInfoENT implements java.io.Serializable {

    /** n_id */
    private Long id;

    /** n_user_id */
    private Long userId;

    /** 第三方用户id */
    private String thirdId;

    /** 第三方用户名 */
    private String thirdName;

    /** 第三方登录类型 */
    private String registerType;

    /** 是否平台用户 */
    private Integer isAppAccount;

    /** 注册时间 */
    private Date registerTime;




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
     * 获取 第三方用户id 的值
     * @return String 
     */
    @Column(name = "s_third_id")
    public String  getThirdId() {
        return thirdId;
    }

    /**
     * 设置第三方用户id 的值
     * @param thirdId 第三方用户id
     */
    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    /**
     * 获取 第三方用户名 的值
     * @return String 
     */
    @Column(name = "s_third_name")
    public String  getThirdName() {
        return thirdName;
    }

    /**
     * 设置第三方用户名 的值
     * @param thirdName 第三方用户名
     */
    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    /**
     * 获取 第三方登录类型 的值
     * @return String 
     */
    @Column(name = "s_register_type")
    public String  getRegisterType() {
        return registerType;
    }

    /**
     * 设置第三方登录类型 的值
     * @param registerType 第三方登录类型
     */
    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    /**
     * 获取 是否平台用户 的值
     * @return Integer 
     */
    @Column(name = "n_is_app_account")
    public Integer  getIsAppAccount() {
        return isAppAccount;
    }

    /**
     * 设置是否平台用户 的值
     * @param isAppAccount 是否平台用户
     */
    public void setIsAppAccount(Integer isAppAccount) {
        this.isAppAccount = isAppAccount;
    }

    /**
     * 获取 注册时间 的值
     * @return Date 
     */
    @Column(name = "d_register_time")
    public Date  getRegisterTime() {
        return registerTime;
    }

    /**
     * 设置注册时间 的值
     * @param registerTime 注册时间
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }



}