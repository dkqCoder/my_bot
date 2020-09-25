package com.tty.ots.trade.ent;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行卡验证（一分钱打款）
 * @author zxh
 * @create 2017-05-10 15:23:20
 **/

@Entity
@Table(name = "trade_distill_validate")
public class TradeDistillValidateENT implements Serializable {

    /** 主键 */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 提款金额 */
    private BigDecimal money;

    /** 用户银行卡绑定表ID */
    private Long bankBindId;

    /** 提交银行接口状态（0：未提交，1：已提交） */
    private Integer submitStatus;

    /** 打款状态（0：未确认，1：已成功，-1：打款失败） */
    private Integer distillStatus;

    /** 提交时间 */
    private Date submitTime;

    /** 打款时间 */
    private Date distillTime;

    /** 打款描述 */
    private String distillComment;

    /** 付款银行(1:招行,2:名生) */
    private Integer payBankType;

    /** jdd提款流水号 */
    private String jddSerialNo;

    /** 真实姓名 */
    private String realName;

    /** 银行卡id */
    private Integer bankId;

    /** 银行对应行号 */
    private String bankCode;

    /** 银行名 */
    private String bankName;

    /** 银行卡号 */
    private String bankcardNo;

    /** 银行卡城市code */
    private String cardCityCode;

    /** 修改时间 */
    private Date updateTime;

    /** 创建时间 */
    private Date createTime;




    /**
     * 获取 主键 的值
     * @return Long 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    public Long  getId() {
        return id;
    }

    /**
     * 设置主键 的值
     * @param id 主键
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

    /**
     * 获取 提款金额 的值
     * @return BigDecimal 
     */
    @Column(name = "n_money")
    public BigDecimal  getMoney() {
        return money;
    }

    /**
     * 设置提款金额 的值
     * @param money 提款金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取 用户银行卡绑定表ID 的值
     * @return Long 
     */
    @Column(name = "n_bank_bind_id")
    public Long  getBankBindId() {
        return bankBindId;
    }

    /**
     * 设置用户银行卡绑定表ID 的值
     * @param bankBindId 用户银行卡绑定表ID
     */
    public void setBankBindId(Long bankBindId) {
        this.bankBindId = bankBindId;
    }

    /**
     * 获取 提交银行接口状态（0：未提交，1：已提交） 的值
     * @return Integer 
     */
    @Column(name = "n_submit_status")
    public Integer  getSubmitStatus() {
        return submitStatus;
    }

    /**
     * 设置提交银行接口状态（0：未提交，1：已提交） 的值
     * @param submitStatus 提交银行接口状态（0：未提交，1：已提交）
     */
    public void setSubmitStatus(Integer submitStatus) {
        this.submitStatus = submitStatus;
    }

    /**
     * 获取 打款状态（0：未确认，1：已成功，-1：打款失败） 的值
     * @return Integer 
     */
    @Column(name = "n_distill_status")
    public Integer  getDistillStatus() {
        return distillStatus;
    }

    /**
     * 设置打款状态（0：未确认，1：已成功，-1：打款失败） 的值
     * @param distillStatus 打款状态（0：未确认，1：已成功，-1：打款失败）
     */
    public void setDistillStatus(Integer distillStatus) {
        this.distillStatus = distillStatus;
    }

    /**
     * 获取 提交时间 的值
     * @return Date 
     */
    @Column(name = "d_submit_time")
    public Date  getSubmitTime() {
        return submitTime;
    }

    /**
     * 设置提交时间 的值
     * @param submitTime 提交时间
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 获取 打款时间 的值
     * @return Date 
     */
    @Column(name = "d_distill_time")
    public Date  getDistillTime() {
        return distillTime;
    }

    /**
     * 设置打款时间 的值
     * @param distillTime 打款时间
     */
    public void setDistillTime(Date distillTime) {
        this.distillTime = distillTime;
    }

    /**
     * 获取 打款描述 的值
     * @return String 
     */
    @Column(name = "s_distill_comment")
    public String  getDistillComment() {
        return distillComment;
    }

    /**
     * 设置打款描述 的值
     * @param distillComment 打款描述
     */
    public void setDistillComment(String distillComment) {
        this.distillComment = distillComment;
    }

    /**
     * 获取 付款银行(1:招行,2:名生) 的值
     * @return Integer 
     */
    @Column(name = "n_pay_bank_type")
    public Integer  getPayBankType() {
        return payBankType;
    }

    /**
     * 设置付款银行(1:招行,2:名生) 的值
     * @param payBankType 付款银行(1:招行,2:名生)
     */
    public void setPayBankType(Integer payBankType) {
        this.payBankType = payBankType;
    }

    /**
     * 获取 jdd提款流水号 的值
     * @return String 
     */
    @Column(name = "s_jdd_serial_no")
    public String  getJddSerialNo() {
        return jddSerialNo;
    }

    /**
     * 设置jdd提款流水号 的值
     * @param jddSerialNo jdd提款流水号
     */
    public void setJddSerialNo(String jddSerialNo) {
        this.jddSerialNo = jddSerialNo;
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
     * 获取 银行卡id 的值
     * @return Integer 
     */
    @Column(name = "s_bank_id")
    public Integer  getBankId() {
        return bankId;
    }

    /**
     * 设置银行卡id 的值
     * @param bankId 银行卡id
     */
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
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
     * 获取 银行名 的值
     * @return String 
     */
    @Column(name = "s_bank_name")
    public String  getBankName() {
        return bankName;
    }

    /**
     * 设置银行名 的值
     * @param bankName 银行名
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 获取 银行卡号 的值
     * @return String 
     */
    @Column(name = "s_bankcard_no")
    public String  getBankcardNo() {
        return bankcardNo;
    }

    /**
     * 设置银行卡号 的值
     * @param bankcardNo 银行卡号
     */
    public void setBankcardNo(String bankcardNo) {
        this.bankcardNo = bankcardNo;
    }

    /**
     * 获取 银行卡城市code 的值
     * @return String 
     */
    @Column(name = "s_card_city_code")
    public String  getCardCityCode() {
        return cardCityCode;
    }

    /**
     * 设置银行卡城市code 的值
     * @param cardCityCode 银行卡城市code
     */
    public void setCardCityCode(String cardCityCode) {
        this.cardCityCode = cardCityCode;
    }

    /**
     * 获取 修改时间 的值
     * @return Date 
     */
    @Column(name = "d_update_time")
    public Date  getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间 的值
     * @param updateTime 修改时间
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



}