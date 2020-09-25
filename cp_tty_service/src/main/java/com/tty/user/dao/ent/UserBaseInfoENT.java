package com.tty.user.dao.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author LiuJin
 * @version v1.0
 *          说明：表【user_base_info】实体类，由hibernateToolv1.0工具自动生成
 * @date 2017-01-13
 */
@Entity
@Table(name = "user_base_info")
public class UserBaseInfoENT implements java.io.Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    private String name;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName = "";

    /**
     * 身份证号码
     */
    private String idcardNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 头像地址
     */
    private String faceUrl;

    private Date updateTime;


    /**
     * 获取 用户ID 的值
     *
     * @return Long
     */
    @Id
    @Column(name = "n_user_id")
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID 的值
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "s_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 昵称 的值
     *
     * @return String
     */
    @Column(name = "s_nick_name")
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称 的值
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取 真实姓名 的值
     *
     * @return String
     */
    @Column(name = "s_real_name")
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名 的值
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取 身份证号码 的值
     *
     * @return String
     */
    @Column(name = "s_idcard_number")
    public String getIdcardNumber() {
        return idcardNumber;
    }

    /**
     * 设置身份证号码 的值
     *
     * @param idcardNumber 身份证号码
     */
    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }

    /**
     * 获取 备注 的值
     *
     * @return String
     */
    @Column(name = "s_remark")
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注 的值
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取 头像地址 的值
     *
     * @return String
     */
    @Column(name = "s_face_url")
    public String getFaceUrl() {
        return faceUrl;
    }

    /**
     * 设置头像地址 的值
     *
     * @param faceUrl 头像地址
     */
    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    @Column(name = "d_update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}
