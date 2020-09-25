package com.tty.ticket.dto;

import java.math.BigDecimal;

/**
 * Created by chenpengfei on 2017/7/19.
 */
public class TicketTicketExceptionDTO {

    /** 出票商code **/
    private Integer agentId;
    /** 期次名 **/
    private String issueName;
    /** 彩种id **/
    private Integer  lotteryId;
    /** 商户号 **/
    private Long merchantId;
    /** 金额 **/
    private BigDecimal money;
    /** 倍数**/
    private Integer multiple;
    private String number;
    /** 玩法id **/
    private Integer playTypeId;
    /** 出票截止日期 **/
    private Long printEndTime;
    /** 方案id **/
    private Long schemeId;
    /** 电子票id **/
    private String ticketNo;
    /** 注数 **/
    private Integer unitCount;

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public Integer getPlayTypeId() {
        return playTypeId;
    }

    public void setPlayTypeId(Integer playTypeId) {
        this.playTypeId = playTypeId;
    }

    public Long getPrintEndTime() {
        return printEndTime;
    }

    public void setPrintEndTime(Long printEndTime) {
        this.printEndTime = printEndTime;
    }

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

    public Integer getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }
}
