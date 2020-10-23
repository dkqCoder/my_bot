package com.tty.ticket.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.tty.ticket.ent.TicketMerchantENT;

import java.util.Map;

/**
 * 商户
 *
 * @author yys
 * @create 2017-07-04 17:24:57
 **/
public interface TicketMerchantService {

    TicketMerchantENT getCurrentMerchantENT(Integer merchantId);

    Map<Integer, TicketMerchantENT> getCurrentMerchantENTMap();

}
