package com.tty.user.dao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zxh
 * @create 2017-03-06 18:03:00
 **/

@Entity
@Table(name = "user_idcard_reset")
public class UserIdcardResetENT implements java.io.Serializable {

    /** n_id */
    private Long id;

    /** userid */
    private Long userId;

    /** 身份证号码 */
    private String idcardNumber;

    /** 真实姓名 */
    private String realName;

    /** 身份证正面照地址 */
    private String frontUrl;

    /** 身份证背面照地址 */
    private String backUrl;

    /** 待审0，成功1，失败2 */
    private Integer status;

    /** d_create_time */
    private Date createTime;

    /** d_update_time */
    private Date updateTime;

    /** s_remark */
    private String remark;




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
     * 获取 userid 的值
     * @return Long 
     */
    @Column(name = "n_user_id")
    public Long  getUserId() {
        return userId;
    }

    /**
     * 设置userid 的值
     * @param userId userid
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取 身份证号码 的值
     * @return String 
     */
    @Column(name = "s_idcard_number")
    public String  getIdcardNumber() {
        return idcardNumber;
    }

    /**
     * 设置身份证号码 的值
     * @param idcardNumber 身份证号码
     */
    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }

    /**
     * 获取 真实姓名 的值
     * @return String 
     */
    @Column(name = "s_real_name")
    public String  getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名 的值
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取 身份证正面照地址 的值
     * @return String 
     */
    @Column(name = "s_front_url")
    public String  getFrontUrl() {
        return frontUrl;
    }

    /**
     * 设置身份证正面照地址 的值
     * @param frontUrl 身份证正面照地址
     */
    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    /**
     * 获取 身份证背面照地址 的值
     * @return String 
     */
    @Column(name = "s_back_url")
    public String  getBackUrl() {
        return backUrl;
    }

    /**
     * 设置身份证背面照地址 的值
     * @param backUrl 身份证背面照地址
     */
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    /**
     * 获取 待审0，成功1，失败2 的值
     * @return Integer 
     */
    @Column(name = "n_status")
    public Integer  getStatus() {
        return status;
    }

    /**
     * 设置待审0，成功1，失败2 的值
     * @param status 待审0，成功1，失败2
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 获取 s_remark 的值
     * @return String 
     */
    @Column(name = "s_remark")
    public String  getRemark() {
        return remark;
    }

    /**
     * 设置s_remark 的值
     * @param remark s_remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }



}