package com.tty.ticket.service;


import java.util.Date;

/**
 * 分票路由
 *
 * @author yys
 * @create 2017-07-04 17:24:57
 **/
public interface TicketRouteService {

    Integer assignAgentId(Integer lotteryId, Date printEndTime);

}
