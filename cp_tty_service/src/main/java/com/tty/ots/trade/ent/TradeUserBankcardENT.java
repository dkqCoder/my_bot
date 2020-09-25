package com.tty.ots.trade.ent;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户绑定银行卡
 * @author
 * @create 2017-05-10 15:23:24
 **/

@Entity
@Table(name = "trade_user_bankcard")
public class TradeUserBankcardENT implements Serializable {

    /** n_id */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 银行ID */
    private Integer bankId;

    /** 银行名称 */
    private String bankName;

    /** 银行对应行号 */
    private String bankCode;

    /** 银行卡号 */
    private String cardNumber;

    /** 持卡人真实姓名 */
    private String cardUserName;

    /** 卡状态（1有效，0 无效,-1正在审核） */
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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 用户名 */
    private String userName;




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
     * 获取 用户ID 的值
     * @return Long 
     */
    @Column(name = "n_user_id")
    public Long  getUserId() {
        return userId;
    }

    /**
     * 设置用户ID 的值
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取 银行ID 的值
     * @return Integer 
     */
    @Column(name = "n_bank_id")
    public Integer  getBankId() {
        return bankId;
    }

    /**
     * 设置银行ID 的值
     * @param bankId 银行ID
     */
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    /**
     * 获取 银行名称 的值
     * @return String 
     */
    @Column(name = "s_bank_name")
    public String  getBankName() {
        return bankName;
    }

    /**
     * 设置银行名称 的值
     * @param bankName 银行名称
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 获取 银行对应行号 的值
     * @return String 
     */
    @Column(name = "s_bank_code")
    public String  getBankCode() {
        return bankCode;
    }

    /**
     * 设置银行对应行号 的值
     * @param bankCode 银行对应行号
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 获取 银行卡号 的值
     * @return String 
     */
    @Column(name = "s_card_number")
    public String  getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置银行卡号 的值
     * @param cardNumber 银行卡号
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * 获取 持卡人真实姓名 的值
     * @return String 
     */
    @Column(name = "s_card_user_name")
    public String  getCardUserName() {
        return cardUserName;
    }

    /**
     * 设置持卡人真实姓名 的值
     * @param cardUserName 持卡人真实姓名
     */
    public void setCardUserName(String cardUserName) {
        this.cardUserName = cardUserName;
    }

    /**
     * 获取 卡状态（1有效，0 无效,-1正在审核） 的值
     * @return Integer 
     */
    @Column(name = "n_status")
    public Integer  getStatus() {
        return status;
    }

    /**
     * 设置卡状态（1有效，0 无效,-1正在审核） 的值
     * @param status 卡状态（1有效，0 无效,-1正在审核）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 默认提款卡状态 （0 不是 1 是） 的值
     * @return Integer 
     */
    @Column(name = "n_default_status")
    public Integer  getDefaultStatus() {
        return defaultStatus;
    }

    /**
     * 设置默认提款卡状态 （0 不是 1 是） 的值
     * @param defaultStatus 默认提款卡状态 （0 不是 1 是）
     */
    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    /**
     * 获取 开卡城市code 的值
     * @return String 
     */
    @Column(name = "s_card_city_code")
    public String  getCardCityCode() {
        return cardCityCode;
    }

    /**
     * 设置开卡城市code 的值
     * @param cardCityCode 开卡城市code
     */
    public void setCardCityCode(String cardCityCode) {
        this.cardCityCode = cardCityCode;
    }

    @Column(name = "n_card_city_id")
    public Integer getCardCityId() {
        return cardCityId;
    }

    public void setCardCityId(Integer cardCityId) {
        this.cardCityId = cardCityId;
    }

    /**
     * 获取 描述 的值
     * @return String 
     */
    @Column(name = "s_comment")
    public String  getComment() {
        return comment;
    }

    /**
     * 设置描述 的值
     * @param comment 描述
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取 是否已经提过款 的值
     * @return Integer 
     */
    @Column(name = "n_distill_status")
    public Integer  getDistillStatus() {
        return distillStatus;
    }

    /**
     * 设置是否已经提过款 的值
     * @param distillStatus 是否已经提过款
     */
    public void setDistillStatus(Integer distillStatus) {
        this.distillStatus = distillStatus;
    }

    /**
     * 获取 绑定状态 的值
     * @return Integer 
     */
    @Column(name = "n_bind_status")
    public Integer  getBindStatus() {
        return bindStatus;
    }

    /**
     * 设置绑定状态 的值
     * @param bindStatus 绑定状态
     */
    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }

    /**
     * 获取 更新时间 的值
     * @return Date 
     */
    @Column(name = "d_update_time")
    public Date  getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间 的值
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * 获取 用户名 的值
     * @return String 
     */
    @Column(name = "s_user_name")
    public String  getUserName() {
        return userName;
    }

    /**
     * 设置用户名 的值
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }



}