package com.tty.ticket.dto;

import java.math.BigDecimal;

/**
 * Created by yys on 2017/9/4.
 */
public class WinMoneyMqDTO {
    private Long schemeId;
    private String ticketNo;
    private BigDecimal winMoneyNoTax;
    private Integer lotteryId;
    private Integer passStatus;
    private Integer agentId;
    private Integer merchantId;
    /**
     * "yyyy-MM-dd HH:mm:ss"
     */
    private String passTime;

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public BigDecimal getWinMoneyNoTax() {
        return winMoneyNoTax;
    }

    public void setWinMoneyNoTax(BigDecimal winMoneyNoTax) {
        this.winMoneyNoTax = winMoneyNoTax;
    }

    public Integer getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(Integer passStatus) {
        this.passStatus = passStatus;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
}
