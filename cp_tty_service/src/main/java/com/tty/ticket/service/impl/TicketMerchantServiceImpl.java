package com.tty.ticket.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.StringUtils;
import com.tty.ticket.context.TicketContext;
import com.tty.ticket.context.TicketRedisKeyContext;
import com.tty.ticket.dao.TicketMerchantDao;
import com.tty.ticket.ent.TicketMerchantENT;
import com.tty.ticket.service.TicketMerchantService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ticketMerchantService")
public class TicketMerchantServiceImpl implements TicketMerchantService {
    private static final Logger logger = LoggerFactory.getLogger(TicketMerchantServiceImpl.class);

    @Autowired
    private TicketMerchantDao ticketMerchantDao;

    @Autowired
    private JedisClusterFactory jedisClusterFactory;

    @Override
    public TicketMerchantENT getCurrentMerchantENT(Integer merchantId) {
        String merchantValue = jedisClusterFactory.hget(TicketRedisKeyContext.TICKET_MERCHANTS, merchantId.toString());
        if (StringUtils.isNotBlank(merchantValue)) {
            return GfJsonUtil.parseObject(merchantValue, TicketMerchantENT.class);
        }

        Map<Integer, TicketMerchantENT> currentMerchantENTMap = getCurrentMerchantENTMap();
        return currentMerchantENTMap.get(merchantId);
    }

    @Override
    public Map<Integer, TicketMerchantENT> getCurrentMerchantENTMap() {
        Map<String, String> map = jedisClusterFactory.hgetAll(TicketRedisKeyContext.TICKET_MERCHANTS);
        Map<Integer, TicketMerchantENT> hashMap = new HashMap<>();

        if (0 != map.size()) {
            for (String value : map.values()) {
                TicketMerchantENT ticketMerchantENT = GfJsonUtil.parseObject(value, TicketMerchantENT.class);
                hashMap.put(ticketMerchantENT.getMerchantId(), ticketMerchantENT);
            }
            return hashMap;
        }

        logger.warn("[出票系统][获取商户]从缓存中获取出票商数据为空,开始从数据库中获取");
        List<TicketMerchantENT> ticketMerchantENTList = ticketMerchantDao.listTicketMerchant().stream().filter(r -> r.getStatus() == 1).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(ticketMerchantENTList)) {
            for (TicketMerchantENT ticketMerchantENT : ticketMerchantENTList) {
                jedisClusterFactory.hset(TicketRedisKeyContext.TICKET_MERCHANTS, ticketMerchantENT.getMerchantId().toString(), GfJsonUtil.toJSONString(ticketMerchantENT));
            }
            jedisClusterFactory.expire(TicketRedisKeyContext.TICKET_MERCHANTS, TicketContext.DAY_SECONDS * 7);

            for (TicketMerchantENT ticketMerchantENT : ticketMerchantENTList) {
                hashMap.put(ticketMerchantENT.getMerchantId(), ticketMerchantENT);
            }
            return hashMap;
        }

        logger.error("[出票系统][获取商户]获取失败");
        return hashMap;
    }

}
