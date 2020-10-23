package com.tty.ticket.dao;

import com.tty.ticket.dto.TicketDTO;
import com.tty.ticket.ent.TicketTicketENT;
import java.util.List;

/**
 * 电子票
 *
 * @author yys
 * @create 2017-07-04 17:26:13
 **/
public interface TicketTicketDao {

    void batchSaveTickets(Long schemeId, List<TicketDTO> list);

    void saveTicket(Long schemeId, TicketDTO ticketDTO);

    List<TicketTicketENT> findListByTickets(List<String> ticketNoList, String merchantId);

}