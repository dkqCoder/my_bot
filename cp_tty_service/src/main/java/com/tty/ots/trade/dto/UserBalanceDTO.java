package com.tty.ots.trade.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author wenxiaoqing
 * @Date 2017/5/10
 * @Description
 */
public class UserBalanceDTO implements Serializable {
    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 余额 */
    private BigDecimal balance;
    /** 不可提款余额 */
    private BigDecimal noDistillBalance;

    /** 可提款余额 */
    private BigDecimal distillBalance;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getNoDistillBalance() {
        return noDistillBalance;
    }

    public void setNoDistillBalance(BigDecimal noDistillBalance) {
        this.noDistillBalance = noDistillBalance;
    }

    public BigDecimal getDistillBalance() {
        return distillBalance;
    }

    public void setDistillBalance(BigDecimal distillBalance) {
        this.distillBalance = distillBalance;
    }
}
