package com.tty.ticket.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yys on 2017/7/6.
 */
public class TicketDTO {
    private String ticketNo;
    private Integer lotteryId;
    private BigDecimal money;
    private Integer playTypeId;
    private Integer unitCount;
    private Integer multiple;
    private String number;
    private Integer agentId;
    private String issueName;
    private Integer merchantId;
    private Date printEndTime;
    private Long schemeId;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getPlayTypeId() {
        return playTypeId;
    }

    public void setPlayTypeId(Integer playTypeId) {
        this.playTypeId = playTypeId;
    }

    public Integer getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Date getPrintEndTime() {
        return printEndTime;
    }

    public void setPrintEndTime(Date printEndTime) {
        this.printEndTime = printEndTime;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }
}
