package com.tty.ticket.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
public class AgentResponseDTO {
    
    private Integer agentId;

    private Integer merchantId;

    private Long schemeId;
    /**
     * 票号
     */
    private String ticketNo;

    private Integer lotteryId;
    /**
     * 出票商出票描述
     */
    private String printDesc;
    /**
     * 电子票状态
     */
    private Integer ticketStatus;
    /**
     * 出票商返回的时间
     */
    private Date printTime;

    /**
     * 落地票号
     */
    private String agentTicketNo;

    /**
     * 出票sp
     */
    private List<TicketPrintSpDTO> ticketPrintSpDTOList;

    /**
     * 出票商中奖金额
     */
    private BigDecimal agentWinMoney;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getPrintDesc() {
        return printDesc;
    }

    public void setPrintDesc(String printDesc) {
        this.printDesc = printDesc;
    }

    public Integer getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(Integer ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getAgentTicketNo() {
        return agentTicketNo;
    }

    public void setAgentTicketNo(String agentTicketNo) {
        this.agentTicketNo = agentTicketNo;
    }

    public List<TicketPrintSpDTO> getTicketPrintSpDTOList() {
        return ticketPrintSpDTOList;
    }

    public void setTicketPrintSpDTOList(List<TicketPrintSpDTO> ticketPrintSpDTOList) {
        this.ticketPrintSpDTOList = ticketPrintSpDTOList;
    }

    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getAgentWinMoney() {
        return agentWinMoney;
    }

    public void setAgentWinMoney(BigDecimal agentWinMoney) {
        this.agentWinMoney = agentWinMoney;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }
}
