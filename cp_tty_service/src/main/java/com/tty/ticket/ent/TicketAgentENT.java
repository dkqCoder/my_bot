package com.tty.ticket.ent;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 出票商
 * @author yys
 * @create 2017-07-04 17:24:56
 **/

@Entity
@Table(name = "ticket_agent")
public class TicketAgentENT implements Serializable {

    /** 出票商Id */
    private Integer agentId;

    /** 出票商名称 */
    private String agentName;

    /** 接口用户名 */
    private String printUsername;

    /** 接口密码 */
    private String printPassword;

    /** 接口URL */
    private String printUrl;

    /** 状态(0:不可用,1:可用) */
    private Integer status;

   /* * 出票商账户
    private BigDecimal balance;*/

    /** 备注 */
    private String comment;

    /** 后台URL */
    private String backendUrl;

    /** 后台用户名 */
    private String backendUsername;

    /** 后台密码 */
    private String backendPassword;

    /** 银行名称 */
    private String bankName;

    /** 银行Code */
    private String bankCode;

    /** 银行卡号 */
    private String bankCardNo;

    /** 银行卡城市id */
    private Integer bankCardCityId;

    private Integer bankCardProvinceId;

    /** 银行卡城市code */
    private String bankCardCityCode;

    /** 银行卡地址 */
    private String bankCardAddress;

    /** 银行卡用户名 */
    private String bankCardUsername;

    /** 更新时间 */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建时间 */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

   /* *//** 资金类型code  ext**//*
    private String fundTypeCode;

    *//** 变更的金额 ext**//*
    private BigDecimal money;

    *//** 外部业务主键id ext**//*
    private String sourceId;

    *//** 彩种编码 ext**//*
    private Integer lotteryId;
    *//** 商户过关派奖时间 **//*
    private String passTime;

    *//** 操作人 **//*
    private String operator;*/

    /**
     * 获取 出票商Id 的值
     * @return Integer 
     */
    @Id
    @Column(name = "n_agent_id")
    public Integer  getAgentId() {
        return agentId;
    }

    /**
     * 设置出票商Id 的值
     * @param agentId 出票商Id
     */
    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    /**
     * 获取 出票商名称 的值
     * @return String 
     */
    @Column(name = "s_agent_name")
    public String  getAgentName() {
        return agentName;
    }

    /**
     * 设置出票商名称 的值
     * @param agentName 出票商名称
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * 获取 接口用户名 的值
     * @return String 
     */
    @Column(name = "s_print_username")
    public String  getPrintUsername() {
        return printUsername;
    }

    /**
     * 设置接口用户名 的值
     * @param printUsername 接口用户名
     */
    public void setPrintUsername(String printUsername) {
        this.printUsername = printUsername;
    }

    /**
     * 获取 接口密码 的值
     * @return String 
     */
    @Column(name = "s_print_password")
    public String  getPrintPassword() {
        return printPassword;
    }

    /**
     * 设置接口密码 的值
     * @param printPassword 接口密码
     */
    public void setPrintPassword(String printPassword) {
        this.printPassword = printPassword;
    }

    /**
     * 获取 接口URL 的值
     * @return String 
     */
    @Column(name = "s_print_url")
    public String  getPrintUrl() {
        return printUrl;
    }

    /**
     * 设置接口URL 的值
     * @param printUrl 接口URL
     */
    public void setPrintUrl(String printUrl) {
        this.printUrl = printUrl;
    }

    /**
     * 获取 状态(0:不可用,1:可用) 的值
     * @return Integer 
     */
    @Column(name = "n_status")
    public Integer  getStatus() {
        return status;
    }

    /**
     * 设置状态(0:不可用,1:可用) 的值
     * @param status 状态(0:不可用,1:可用)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

  /*  *//**
     * 获取 出票商账户 的值
     * @return BigDecimal 
     *//*
    @Column(name = "n_balance")
    public BigDecimal  getBalance() {
        return balance;
    }

    *//**
     * 设置出票商账户 的值
     * @param balance 出票商账户
     *//*
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }*/

    /**
     * 获取 备注 的值
     * @return String 
     */
    @Column(name = "s_comment")
    public String  getComment() {
        return comment;
    }

    /**
     * 设置备注 的值
     * @param comment 备注
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取 后台URL 的值
     * @return String 
     */
    @Column(name = "s_backend_url")
    public String  getBackendUrl() {
        return backendUrl;
    }

    /**
     * 设置后台URL 的值
     * @param backendUrl 后台URL
     */
    public void setBackendUrl(String backendUrl) {
        this.backendUrl = backendUrl;
    }

    /**
     * 获取 后台用户名 的值
     * @return String 
     */
    @Column(name = "s_backend_username")
    public String  getBackendUsername() {
        return backendUsername;
    }

    /**
     * 设置后台用户名 的值
     * @param backendUsername 后台用户名
     */
    public void setBackendUsername(String backendUsername) {
        this.backendUsername = backendUsername;
    }

    /**
     * 获取 后台密码 的值
     * @return String 
     */
    @Column(name = "s_backend_password")
    public String  getBackendPassword() {
        return backendPassword;
    }

    /**
     * 设置后台密码 的值
     * @param backendPassword 后台密码
     */
    public void setBackendPassword(String backendPassword) {
        this.backendPassword = backendPassword;
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
     * 获取 银行Code 的值
     * @return String
     */
    @Column(name = "s_bank_code")
    public String  getBankCode() {
        return bankCode;
    }

    /**
     * 设置银行Code 的值
     * @param bankCode 银行Code
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Column(name = "n_bank_card_province_id")
    public Integer getBankCardProvinceId() {
        return bankCardProvinceId;
    }

    public void setBankCardProvinceId(Integer bankCardProvinceId) {
        this.bankCardProvinceId = bankCardProvinceId;
    }

    @Column(name = "n_bank_card_city_id")
    public Integer getBankCardCityId() {
        return bankCardCityId;
    }

    public void setBankCardCityId(Integer bankCardCityId) {
        this.bankCardCityId = bankCardCityId;
    }

    @Column(name = "s_bank_card_city_code")
    public String getBankCardCityCode() {
        return bankCardCityCode;
    }

    public void setBankCardCityCode(String bankCardCityCode) {
        this.bankCardCityCode = bankCardCityCode;
    }

    /**
     * 获取 银行卡号 的值
     * @return String 
     */
    @Column(name = "s_bank_card_no")
    public String  getBankCardNo() {
        return bankCardNo;
    }

    /**
     * 设置银行卡号 的值
     * @param bankCardNo 银行卡号
     */
    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    /**
     * 获取 银行卡地址 的值
     * @return String 
     */
    @Column(name = "s_bank_card_address")
    public String  getBankCardAddress() {
        return bankCardAddress;
    }

    /**
     * 设置银行卡地址 的值
     * @param bankCardAddress 银行卡地址
     */
    public void setBankCardAddress(String bankCardAddress) {
        this.bankCardAddress = bankCardAddress;
    }

    /**
     * 获取 银行卡用户名 的值
     * @return String 
     */
    @Column(name = "s_bank_card_username")
    public String  getBankCardUsername() {
        return bankCardUsername;
    }

    /**
     * 设置银行卡用户名 的值
     * @param bankCardUsername 银行卡用户名
     */
    public void setBankCardUsername(String bankCardUsername) {
        this.bankCardUsername = bankCardUsername;
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
/*
    @Transient
    public String getFundTypeCode() {
        return fundTypeCode;
    }

    public void setFundTypeCode(String fundTypeCode) {
        this.fundTypeCode = fundTypeCode;
    }

    @Transient
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Transient
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Transient
    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    @Transient
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Transient
    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }*/
}