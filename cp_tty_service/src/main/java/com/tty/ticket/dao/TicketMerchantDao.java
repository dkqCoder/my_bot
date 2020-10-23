package com.tty.ticket.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.ticket.ent.TicketMerchantENT;
import java.util.List;


/**
 * 商户
 *
 * @author yys
 * @create 2017-07-04 17:24:57
 **/
public interface TicketMerchantDao {


    List<TicketMerchantENT> listTicketMerchant(Integer page, Integer limit, JSONObject data);


    List<TicketMerchantENT> listTicketMerchant();
}