package com.tty.user.dao.ent;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_login_record")
public class UserLoginRecord  implements java.io.Serializable{

    /** n_id */
    private Long id;

    /** n_user_id */
    private Long userId;

    /** 登录时间 */
    private Date loginTime;

    /** 渠道号 */
    private String cmdName;

    /** 手机类型 */
    private String phoneType;

    /** 系统版本 */
    private String osVersion;

    /** 客户端版本 */
    private String appVersion;

    /** 机型 */
    private String phoneModel;




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
     * 获取 登录时间 的值
     * @return Date
     */
    @Column(name = "d_login_time")
    public Date  getLoginTime() {
        return loginTime;
    }

    /**
     * 设置登录时间 的值
     * @param loginTime 登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取 渠道号 的值
     * @return String
     */
    @Column(name = "s_cmd_name")
    public String  getCmdName() {
        return cmdName;
    }

    /**
     * 设置渠道号 的值
     * @param cmdName 渠道号
     */
    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    /**
     * 获取 手机类型 的值
     * @return String
     */
    @Column(name = "s_phone_type")
    public String  getPhoneType() {
        return phoneType;
    }

    /**
     * 设置手机类型 的值
     * @param phoneType 手机类型
     */
    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    /**
     * 获取 系统版本 的值
     * @return String
     */
    @Column(name = "s_os_version")
    public String  getOsVersion() {
        return osVersion;
    }

    /**
     * 设置系统版本 的值
     * @param osVersion 系统版本
     */
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    /**
     * 获取 客户端版本 的值
     * @return String
     */
    @Column(name = "s_app_version")
    public String  getAppVersion() {
        return appVersion;
    }

    /**
     * 设置客户端版本 的值
     * @param appVersion 客户端版本
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * 获取 机型 的值
     * @return String
     */
    @Column(name = "s_phone_model")
    public String  getPhoneModel() {
        return phoneModel;
    }

    /**
     * 设置机型 的值
     * @param phoneModel 机型
     */
    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }


}
