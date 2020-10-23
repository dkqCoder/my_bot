package com.tty.ticket.service;

import com.tty.ticket.dao.entity.TicketConfigENT;

/**
 * 出票配置
 **/
public interface TicketConfigService {
    Integer getSysSendType();

    TicketConfigENT getCurrentTicketConfigValueByCode(String code);

    boolean getSendSwitch();


}
