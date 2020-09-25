package com.tty.ticket.dto;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/19.
 */
public class SendResponseDTO {

    private String ticketNo;

    private Long schemeId;

    /**
     * 1:投注成功
     * -1:投注失败
     */
    private Integer sendStatus;

    private String sendDesc;

    private Date sendTime;

    public SendResponseDTO() {
    }

    public SendResponseDTO(String ticketNo, Long schemeId, Integer sendStatus, String sendDesc, Date sendTime) {
        this.ticketNo = ticketNo;
        this.schemeId = schemeId;
        this.sendStatus = sendStatus;
        this.sendDesc = sendDesc;
        this.sendTime = sendTime;
    }

    public String getSendDesc() {
        return sendDesc;
    }

    public void setSendDesc(String sendDesc) {
        this.sendDesc = sendDesc;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }
}
