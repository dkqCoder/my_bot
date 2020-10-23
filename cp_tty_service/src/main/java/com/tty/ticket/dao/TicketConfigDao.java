package com.tty.ticket.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.ticket.dao.entity.TicketConfigENT;
import java.util.List;

/**
 * 出票配置
 * @author yys
 * @create 2017-07-06 18:52:52
 **/
public interface TicketConfigDao {

    void saveTicketConfig(TicketConfigENT ent);

    void updateTicketConfig(TicketConfigENT ent);

    void saveOrUpdateTicketConfig(TicketConfigENT ent);

    void deleteTicketConfig(String code);

    TicketConfigENT getOrderConfigENTByCode(String code);

    List<TicketConfigENT> listTicketConfig(Integer page, Integer limit, JSONObject data);

    Long listTicketConfigCount(JSONObject data);

    List<TicketConfigENT> listAllTicketConfig();
}