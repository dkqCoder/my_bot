package com.tty.ticket.biz;

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.ticket.dto.TicketDTO;
import com.tty.ticket.service.TicketConfigService;
import com.tty.ticket.service.TicketRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;

/**
 * Created by yys on 2017/9/26.
 * 分票业务逻辑
 */
@Component("agentRouteBiz")
public class AgentRouteBiz {

    @Autowired
    private TicketRouteService ticketRouteService;

    @Autowired
    private TicketConfigService ticketConfigService;

    private static final Logger logger = LoggerFactory.getLogger(AgentRouteBiz.class);

    /**
     * 分票路由逻辑
     * 0.豹赢，全部给 xuanyi
     * 1.竞猜足球，3串1,4串1,金额>=10元的票,按比例分给aoli wujiang,值0-100
     * 2.竞猜足球，345678串1，金额>=198元且金额<400元，按比例分给aoli wujiang,值0-100
     * 3.竞猜足球，单票金额等于198 ,按比例分给aoli wujiang,值0-100
     * 4.胜负彩/任选9，金额>=512 ,按比例分给aoli wujiang,值0-100
     * 5.竞猜足球，金额>=20 ,按比例分给aoli wujiang,值0-100
     * 6.竞猜足球，金额>=20 ,按比例分给funiu,值0-100
     * 6.基础分票策略
     *
     * @param ticketDTO
     * @return
     */
    public Integer getAgentId(TicketDTO ticketDTO) {
        return ticketRouteService.assignAgentId(ticketDTO.getLotteryId(), ticketDTO.getPrintEndTime());
    }

}
