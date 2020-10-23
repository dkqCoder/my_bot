package com.tty.ticket.context;

/**
 * Created by Administrator on 2017/7/18.
 */
public class TicketStatusContext {

    /**
     * 未投注
     */
    public static final int TICKET_UN_SEND = 0;
    /**
     * 投注失败
     */
    public static final int TICKET_SEND_FAILED = -1;
    /**
     * 投注成功
     */
    public static final int TICKET_SEND_SUCCESS = 1;
    /**
     * 出票失败
     */
    public static final int TICKET_PRINT_FAILED = -2;
    /**
     * 出票成功
     */
    public static final int TICKET_PRINT_SUCCESS = 2;

}
