package com.tty.ots.trade.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户资金
 * @author wenxiaoqing
 * @create 2017-04-14 17:11:52
 **/

@Entity
@Table(name = "trade_fund")
public class TradeFundENT implements Serializable {

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 余额 */
    private BigDecimal balance;

    /** 冻结余额 */
    private BigDecimal freezeBalance;

    /** 不可提款余额 */
    private BigDecimal noDistillBalance;

    /** 可提款余额 */
    private BigDecimal distillBalance;

    /** 更改时间 */
    private Date updateTime;

    /** 创建时间 */
    private Date createTime;




    /**
     * 获取 用户ID 的值
     * @return Long 
     */
    @Id
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
     * 获取 余额 的值
     * @return BigDecimal 
     */
    @Column(name = "n_balance")
    public BigDecimal  getBalance() {
        return balance == null ? BigDecimal.ZERO:balance;
    }

    /**
     * 设置余额 的值
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 获取 冻结余额 的值
     * @return BigDecimal 
     */
    @Column(name = "n_freeze_balance")
    public BigDecimal  getFreezeBalance() {
        return freezeBalance == null ? BigDecimal.ZERO : freezeBalance;
    }

    /**
     * 设置冻结余额 的值
     * @param freezeBalance 冻结余额
     */
    public void setFreezeBalance(BigDecimal freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    /**
     * 获取 不可提款余额 的值
     * @return BigDecimal 
     */
    @Column(name = "n_no_distill_balance")
    public BigDecimal  getNoDistillBalance() {
        return noDistillBalance == null ? BigDecimal.ZERO : noDistillBalance;
    }

    /**
     * 设置不可提款余额 的值
     * @param noDistillBalance 不可提款余额
     */
    public void setNoDistillBalance(BigDecimal noDistillBalance) {
        this.noDistillBalance = noDistillBalance;
    }

    /**
     * 获取 可提款余额 的值
     * @return BigDecimal 
     */
    @Column(name = "n_distill_balance")
    public BigDecimal  getDistillBalance() {
        return distillBalance == null ? BigDecimal.ZERO : distillBalance;
    }

    /**
     * 设置可提款余额 的值
     * @param distillBalance 可提款余额
     */
    public void setDistillBalance(BigDecimal distillBalance) {
        this.distillBalance = distillBalance;
    }

    /**
     * 获取 更改时间 的值
     * @return Date 
     */
    @Column(name = "d_update_time")
    public Date  getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更改时间 的值
     * @param updateTime 更改时间
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