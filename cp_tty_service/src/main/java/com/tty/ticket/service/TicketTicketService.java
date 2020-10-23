package com.tty.ticket.service;

import com.tty.ticket.dto.SendTicketsResultDTO;
import com.tty.ticket.dto.TicketDTO;
import com.tty.ticket.ent.TicketTicketENT;
import java.util.List;

/**
 * 电子票
 **/
public interface TicketTicketService {

    SendTicketsResultDTO saveAndSendTickets(List<TicketDTO> ticketDTOList);

    List<TicketTicketENT> findListByTickets(List<String> ticketNoList, String merchantId);

}
