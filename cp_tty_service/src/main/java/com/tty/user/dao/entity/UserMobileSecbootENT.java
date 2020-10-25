package com.tty.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;

/**
 * 
 * @author zxh
 * @create 2017-07-10 13:16:34
 **/

@Entity
@Table(name = "user_mobile_secboot")
public class UserMobileSecbootENT implements Serializable {

    /** 主键ID */
    private Long id;

    /** 手机号 */
    private String mobileNumber = "''";

    /** 是否成功 */
    private boolean isSuccess = false;

    /** 返回消息 */
    private String retMsg = "''";

    /** 数据来源 */
    private String source = "''";

    /** 手机号活跃时间 */
    private Date updateTime;

    /** 级别 -1未验证出来, 0可疑，1黑名单，2小号 */
    private Integer level = 0;

    /** 创建时间 */
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
     * 获取 手机号 的值
     * @return String 
     */
    @Column(name = "n_mobile_number")
    public String  getMobileNumber() {
        return mobileNumber;
    }

    /**
     * 设置手机号 的值
     * @param mobileNumber 手机号
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }



    /**
     * 获取 返回消息 的值
     * @return String 
     */
    @Column(name = "s_ret_msg")
    public String  getRetMsg() {
        return retMsg;
    }

    /**
     * 设置返回消息 的值
     * @param retMsg 返回消息
     */
    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    /**
     * 获取 数据来源 的值
     * @return String 
     */
    @Column(name = "s_source")
    public String  getSource() {
        return source;
    }

    /**
     * 设置数据来源 的值
     * @param source 数据来源
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取 手机号活跃时间 的值
     * @return Date 
     */
    @Column(name = "d_update_time")
    public Date  getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置手机号活跃时间 的值
     * @param updateTime 手机号活跃时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 级别 -1未验证出来, 0可疑，1黑名单，2小号 的值
     * @return Integer 
     */
    @Column(name = "d_level")
    public Integer  getLevel() {
        return level;
    }

    /**
     * 设置级别 -1未验证出来, 0可疑，1黑名单，2小号 的值
     * @param level 级别 -1未验证出来, 0可疑，1黑名单，2小号
     */
    public void setLevel(Integer level) {
        this.level = level;
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

    /**
     * 获取 是否成功 的值
     * @return String
     */
    @Column(name = "n_is_success")
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * 设置是否成功 的值
     * @param success 是否成功
     */
    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
