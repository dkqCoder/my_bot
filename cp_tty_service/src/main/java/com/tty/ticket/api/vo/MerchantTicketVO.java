package com.tty.ticket.api.vo;

import java.util.Date;

/**
 * Created by chenpengfei on 2017/8/11.
 */
public class MerchantTicketVO {

    /** 电子票号 **/
    private String ticketNo;
    /** 电子票状态 **/
    private Integer printStatus;
    /** 落地票号 **/
    private String printTicketNo;
    /** 出票时间 **/
    private Date printTime;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Integer getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(Integer printStatus) {
        this.printStatus = printStatus;
    }

    public String getPrintTicketNo() {
        return printTicketNo;
    }

    public void setPrintTicketNo(String printTicketNo) {
        this.printTicketNo = printTicketNo;
    }

    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }
}
