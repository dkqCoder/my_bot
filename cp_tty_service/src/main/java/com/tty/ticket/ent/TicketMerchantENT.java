package com.tty.ticket.ent;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 商户
 * @author 
 * @create 2017-07-19 08:50:57
 **/

@Entity
@Table(name = "ticket_merchant")
public class TicketMerchantENT implements Serializable {

    /** 商户code */
    private Integer merchantId;

    /** 商户名 */
    private String merchantName;

    /** 通知URL */
    private String noticeUrl;

    private String noticeUrl2;

    /** s_password */
    private String password;

    /** n_status */
    private Integer status;

    /** s_comment */
    private String comment;

    /** d_update_time */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** d_create_time */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;




    /**
     * 获取 商户code 的值
     * @return Integer 
     */
    @Id
    @Column(name = "n_merchant_id")
    public Integer  getMerchantId() {
        return merchantId;
    }

    /**
     * 设置商户code 的值
     * @param merchantId 商户code
     */
    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * 获取 商户名 的值
     * @return String 
     */
    @Column(name = "s_merchant_name")
    public String  getMerchantName() {
        return merchantName;
    }

    /**
     * 设置商户名 的值
     * @param merchantName 商户名
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * 获取 通知URL 的值
     * @return String 
     */
    @Column(name = "s_notice_url")
    public String  getNoticeUrl() {
        return noticeUrl;
    }

    /**
     * 设置通知URL 的值
     * @param noticeUrl 通知URL
     */
    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }

    @Column(name = "s_notice_url2")
    public String getNoticeUrl2() {
        return noticeUrl2;
    }

    public void setNoticeUrl2(String noticeUrl2) {
        this.noticeUrl2 = noticeUrl2;
    }

    /**
     * 获取 s_password 的值
     * @return String 
     */
    @Column(name = "s_password")
    public String  getPassword() {
        return password;
    }

    /**
     * 设置s_password 的值
     * @param password s_password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取 n_status 的值
     * @return Integer 
     */
    @Column(name = "n_status")
    public Integer  getStatus() {
        return status;
    }

    /**
     * 设置n_status 的值
     * @param status n_status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 s_comment 的值
     * @return String 
     */
    @Column(name = "s_comment")
    public String  getComment() {
        return comment;
    }

    /**
     * 设置s_comment 的值
     * @param comment s_comment
     */
    public void setComment(String comment) {
        this.comment = comment;
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