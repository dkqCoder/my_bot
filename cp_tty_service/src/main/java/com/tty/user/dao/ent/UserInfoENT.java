package com.tty.user.dao.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author
 * @create 2017-04-03 20:48:09
 **/

@Entity
@Table(name = "user_info")
public class UserInfoENT implements java.io.Serializable {

    /**
     * n_user_id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 绑定手机号
     */
    private String mobileNumber;

    /**
     * 第三方用户id
     */
    private String thdPartId;

    /**
     * 第三方用户名
     */
    private String thdPartName;

    /**
     * 第三方类别
     */
    private String thdPartType;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 渠道代码
     */
    private String entranceCode;

    /**
     * 密码
     */
    private String password;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 是否可用
     */
    private Integer status;

    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 手机操作系统版本
     */
    private String platformVersion;

    /**
     * 手机系统类型
     */
    private String platformCode;

    /**
     * s_cmd_id
     */
    private String cmdId;

    /**
     * 应用APP版本号
     */
    private String appVersion;

    /**
     * 用户坐标
     */
    private String ts;

    /**
     * 手机机型
     */
    private String phoneName;

    /**
     * app应用版本数字号
     */
    private Integer appVersionNum;
    /**
     * 用户设备id
     **/
    private String uuid;
    /** IP地址 */
    private String ipAddress;

    //更新时间
    private Date updateTime;

    @Column(name = "s_uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取 n_user_id 的值
     *
     * @return Long
     */
    @Id
    @Column(name = "n_user_id")
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置n_user_id 的值
     *
     * @param userId n_user_id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取 用户名 的值
     *
     * @return String
     */
    @Column(name = "s_login_name")
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置用户名 的值
     *
     * @param loginName 用户名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取 绑定手机号 的值
     *
     * @return String
     */
    @Column(name = "s_mobile_number")
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * 设置绑定手机号 的值
     *
     * @param mobileNumber 绑定手机号
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * 获取 第三方用户id 的值
     *
     * @return String
     */
    @Column(name = "s_thd_part_id")
    public String getThdPartId() {
        return thdPartId;
    }

    /**
     * 设置第三方用户id 的值
     *
     * @param thdPartId 第三方用户id
     */
    public void setThdPartId(String thdPartId) {
        this.thdPartId = thdPartId;
    }

    /**
     * 获取 第三方用户名 的值
     *
     * @return String
     */
    @Column(name = "s_thd_part_name")
    public String getThdPartName() {
        return thdPartName;
    }

    /**
     * 设置第三方用户名 的值
     *
     * @param thdPartName 第三方用户名
     */
    public void setThdPartName(String thdPartName) {
        this.thdPartName = thdPartName;
    }

    /**
     * 获取 第三方类别 的值
     *
     * @return String
     */
    @Column(name = "s_thd_part_type")
    public String getThdPartType() {
        return thdPartType;
    }

    /**
     * 设置第三方类别 的值
     *
     * @param thdPartType 第三方类别
     */
    public void setThdPartType(String thdPartType) {
        this.thdPartType = thdPartType;
    }

    /**
     * 获取 注册时间 的值
     *
     * @return Date
     */
    @Column(name = "d_register_time")
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * 设置注册时间 的值
     *
     * @param registerTime 注册时间
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * 获取 渠道代码 的值
     *
     * @return String
     */
    @Column(name = "s_entrance_code")
    public String getEntranceCode() {
        return entranceCode;
    }

    /**
     * 设置渠道代码 的值
     *
     * @param entranceCode 渠道代码
     */
    public void setEntranceCode(String entranceCode) {
        this.entranceCode = entranceCode;
    }

    /**
     * 获取 密码 的值
     *
     * @return String
     */
    @Column(name = "s_password")
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码 的值
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取 支付密码 的值
     *
     * @return String
     */
    @Column(name = "s_pay_password")
    public String getPayPassword() {
        return payPassword;
    }

    /**
     * 设置支付密码 的值
     *
     * @param payPassword 支付密码
     */
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    /**
     * 获取 是否可用 的值
     *
     * @return Integer
     */
    @Column(name = "n_status")
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否可用 的值
     *
     * @param status 是否可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 最后一次登录时间 的值
     *
     * @return Date
     */
    @Column(name = "d_last_login_time")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最后一次登录时间 的值
     *
     * @param lastLoginTime 最后一次登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取 手机操作系统版本 的值
     *
     * @return String
     */
    @Column(name = "s_platform_version")
    public String getPlatformVersion() {
        return platformVersion;
    }

    /**
     * 设置手机操作系统版本 的值
     *
     * @param platformVersion 手机操作系统版本
     */
    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    /**
     * 获取 手机系统类型 的值
     *
     * @return String
     */
    @Column(name = "s_platform_code")
    public String getPlatformCode() {
        return platformCode;
    }

    /**
     * 设置手机系统类型 的值
     *
     * @param platformCode 手机系统类型
     */
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    /**
     * 获取 s_cmd_id 的值
     *
     * @return String
     */
    @Column(name = "s_cmd_id")
    public String getCmdId() {
        return cmdId;
    }

    /**
     * 设置s_cmd_id 的值
     *
     * @param cmdId s_cmd_id
     */
    public void setCmdId(String cmdId) {
        this.cmdId = cmdId;
    }

    /**
     * 获取 应用APP版本号 的值
     *
     * @return String
     */
    @Column(name = "s_app_version")
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 设置应用APP版本号 的值
     *
     * @param appVersion 应用APP版本号
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * 获取 用户坐标 的值
     *
     * @return String
     */
    @Column(name = "s_ts")
    public String getTs() {
        return ts;
    }

    /**
     * 设置用户坐标 的值
     *
     * @param ts 用户坐标
     */
    public void setTs(String ts) {
        this.ts = ts;
    }

    /**
     * 获取 手机机型 的值
     *
     * @return String
     */
    @Column(name = "s_phone_name")
    public String getPhoneName() {
        return phoneName;
    }

    /**
     * 设置手机机型 的值
     *
     * @param phoneName 手机机型
     */
    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    /**
     * 获取 app应用版本数字号 的值
     *
     * @return Integer
     */
    @Column(name = "n_app_version_num")
    public Integer getAppVersionNum() {
        return appVersionNum;
    }

    /**
     * 设置app应用版本数字号 的值
     *
     * @param appVersionNum app应用版本数字号
     */
    public void setAppVersionNum(Integer appVersionNum) {
        this.appVersionNum = appVersionNum;
    }

    @Column(name = "d_update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /** IP地址 */
    @Column(name = "s_ipaddress")
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
}