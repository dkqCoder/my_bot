package com.tty.ots.trade.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuzp on 2017/5/10.
 */
public class UserBankCardDTO implements Serializable{
    /** n_id */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 银行ID */
    private Integer bankId;

    /**银行名称 */
    private String bankName;

    /** 银行卡号 */
    private String cardNumber;

    /** 持卡人真实姓名 */
    private String cardUserName;

    /** 卡状态（1有效，0 无效） */
    private Integer status;

    /** 默认提款卡状态 （0 不是 1 是） */
    private Integer defaultStatus;

    private Integer cardCityId;

    /** 开卡城市code */
    private String cardCityCode;

    /** 描述 */
    private String comment;

    /** 是否已经提过款 */
    private Integer distillStatus;

    /** 绑定状态 */
    private Integer bindStatus;

    /** 更新时间 */
    private Date updateTime;

    /** 创建时间 */
    private Date createTime;

    /** 用户名 */
    private String userName;

    public Integer getCardCityId() {
        return cardCityId;
    }

    public void setCardCityId(Integer cardCityId) {
        this.cardCityId = cardCityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardUserName() {
        return cardUserName;
    }

    public void setCardUserName(String cardUserName) {
        this.cardUserName = cardUserName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getCardCityCode() {
        return cardCityCode;
    }

    public void setCardCityCode(String cardCityCode) {
        this.cardCityCode = cardCityCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getDistillStatus() {
        return distillStatus;
    }

    public void setDistillStatus(Integer distillStatus) {
        this.distillStatus = distillStatus;
    }

    public Integer getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
