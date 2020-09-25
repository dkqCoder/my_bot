package com.tty.ticket.ent;

import java.io.Serializable;


/**
 * 电子票
 * @author yys
 * @create 2017-07-04 17:26:13
 **/
public class TicketTicketPK implements Serializable {

    /** 方案id */
    private Long schemeId;

    /** 票号 */
    private String ticketNo;




    public Long  getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public String  getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }


}