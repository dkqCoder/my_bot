package com.tty.ticket.dao.impl;

import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.utils.DateUtil;
import com.tty.ticket.context.TicketStatusContext;
import com.tty.ticket.dao.TicketTicketDao;
import com.tty.ticket.dto.TicketDTO;
import com.tty.ticket.ent.TicketTicketENT;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuxinhai
 */
@Repository("ticketTicketDao")
public class TicketTicketDaoImpl extends BaseDao<TicketTicketENT> implements TicketTicketDao {


    @DataSource
    @Transactional
    @Override
    public void batchSaveTickets(Long schemeId, List<TicketDTO> list) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("INSERT INTO ticket_ticket (" +
            " s_ticket_no," +
            " n_lottery_id," +
            " n_money," +
            " n_playtype_id," +
            " n_unit_count," +
            " n_multiple," +
            " s_content," +
            " n_quash_status," +
            " n_feedback_status," +
            " n_print_status," +
            " n_agent_id," +
            " s_issue_name," +
            " n_merchant_id," +
            " n_scheme_id," +
            " d_update_time," +
            " d_create_time," +
            " s_print_desc," +
            " n_send_count," +
            " d_print_end_time" +
            ") VALUES ");

        String values = list.stream().map(r -> String.format("('%s',%s,%s,%s,%s,%s,'%s',0,0,%s,%s,'%s',%s,%s,now(),now(),'',0,'%s')"
            , r.getTicketNo()
            , r.getLotteryId()
            , r.getMoney()
            , r.getPlayTypeId()
            , r.getUnitCount()
            , r.getMultiple()
            , r.getNumber()
            , TicketStatusContext.TICKET_UN_SEND
            , r.getAgentId()
            , r.getIssueName()
            , r.getMerchantId()
            , r.getSchemeId()
            , DateUtil.format(r.getPrintEndTime(), "yyyy-MM-dd HH:mm:ss")
        )).collect(Collectors.joining(","));

        sbSql.append(values);

        SQLQuery sqlQuery = getSQLQuery(sbSql.toString());
        sqlQuery.executeUpdate();
    }

    @DataSource
    @Transactional
    @Override
    public void saveTicket(Long schemeId, TicketDTO ticketDTO) {
        String sql = String.format("INSERT INTO ticket_ticket (" +
                " s_ticket_no," +
                " n_lottery_id," +
                " n_money," +
                " n_playtype_id," +
                " n_unit_count," +
                " n_multiple," +
                " s_content," +
                " n_quash_status," +
                " n_feedback_status," +
                " n_print_status," +
                " n_agent_id," +
                " s_issue_name," +
                " n_merchant_id," +
                " n_scheme_id," +
                " d_update_time," +
                " d_create_time," +
                " s_print_desc," +
                " n_send_count," +
                " d_print_end_time" +
                ") VALUES ('%s',%s,%s,%s,%s,%s,'%s',0,0,%s,%s,'%s',%s,%s,now(),now(),'',0,'%s')"
            , ticketDTO.getTicketNo()
            , ticketDTO.getLotteryId()
            , ticketDTO.getMoney()
            , ticketDTO.getPlayTypeId()
            , ticketDTO.getUnitCount()
            , ticketDTO.getMultiple()
            , ticketDTO.getNumber()
            , TicketStatusContext.TICKET_UN_SEND
            , ticketDTO.getAgentId()
            , ticketDTO.getIssueName()
            , ticketDTO.getMerchantId()
            , ticketDTO.getSchemeId()
            , DateUtil.format(ticketDTO.getPrintEndTime(), "yyyy-MM-dd HH:mm:ss")
        );

        SQLQuery sqlQuery = getSQLQuery(sql);
        sqlQuery.executeUpdate();
    }

    @Override
    public List<TicketTicketENT> findListByTickets(List<String> ticketNoList, String merchantId) {
        String hql = "FROM TicketTicketENT WHERE merchantId=:merchantId AND ticketNo in (:tickets)";
        return getQuery(hql).setInteger("merchantId", Integer.parseInt(merchantId))
            .setParameterList("tickets", ticketNoList).list();
    }


}
