package com.tty.ticket.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.StringUtils;
//import com.tty.ticket.common.context.TicketContext;
//import com.tty.ticket.common.context.TicketRedisKeyContext;
import com.tty.ticket.context.TicketContext;
import com.tty.ticket.context.TicketRedisKeyContext;
import com.tty.ticket.dao.TicketConfigDao;
//import com.tty.ticket.dao.entity.TicketConfigENT;
import com.tty.ticket.dao.entity.TicketConfigENT;
import com.tty.ticket.service.TicketConfigService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ticketConfigService")
public class TicketConfigServiceImpl implements TicketConfigService {
    private static final Logger logger = LoggerFactory.getLogger(TicketConfigServiceImpl.class);

    @Autowired
    private TicketConfigDao ticketConfigDao;

    @Autowired
    @Qualifier("jedisClusterFactory")
    private JedisClusterFactory jedisClusterFactory;

    /*@DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listTicketConfig(JSONObject jsonParm) {
        ExtModel result = new ExtModel();
        result.setData(ticketConfigDao.listTicketConfig(jsonParm.getInteger("page"), jsonParm.getInteger("limit"), jsonParm.getJSONObject("data")));
        result.setTotal(ticketConfigDao.listTicketConfigCount(jsonParm.getJSONObject("data")));
        return result;
    }*/

    /*@Transactional
    @DataSource
    @Override
    public void saveTicketConfig(TicketConfigENT ticketConfig) {
        ticketConfigDao.saveTicketConfig(ticketConfig);
    }

    @Transactional
    @DataSource
    @Override
    public void updateTicketConfig(TicketConfigENT ticketConfig) {
        ticketConfigDao.saveOrUpdateTicketConfig(ticketConfig);
        String key = String.format(TicketRedisKeyContext.TICKET_CONFIG, ticketConfig.getCode());
        jedisClusterFactory.del(key);
    }

    @Transactional
    @DataSource
    @Override
    public void deleteTicketConfig(String code) {
        ticketConfigDao.deleteTicketConfig(code);
        String key = String.format(TicketRedisKeyContext.TICKET_CONFIG, code);
        jedisClusterFactory.del(key);
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public void refreshTicketConfig() {
        List<TicketConfigENT> ticketConfigENTList = ticketConfigDao.listAllTicketConfig();
        for (TicketConfigENT ticketConfigENT : ticketConfigENTList) {
            String key = String.format(TicketRedisKeyContext.TICKET_CONFIG, ticketConfigENT.getCode());
            jedisClusterFactory.set(key, GfJsonUtil.toJSONString(ticketConfigENT));
        }
    }*/

    @Override
    public TicketConfigENT getCurrentTicketConfigValueByCode(String code) {

        // ???redis ??????????????????????????????
        String key = String.format(TicketRedisKeyContext.TICKET_CONFIG, code);
        String val = jedisClusterFactory.get(key);

        if (StringUtils.isNotBlank(val)) {
            return GfJsonUtil.parseObject(val, TicketConfigENT.class);
        }

        TicketConfigENT orderConfigENT = ticketConfigDao.getOrderConfigENTByCode(code);
        ticketConfigDao.getOrderConfigENTByCode(code);
        if (null == orderConfigENT) {
            return null;
        }
        jedisClusterFactory.set(key, GfJsonUtil.toJSONString(orderConfigENT));
        return orderConfigENT;
    }

    /**
     * ???????????????????????? 1???????????????(MQ) 2: ????????????(Redis) 0???????????????
     *
     * @return 1, 2, 0
     */
    @Override
    public Integer getSysSendType() {
        try {
            TicketConfigENT currentTicketConfigValueByCode = getCurrentTicketConfigValueByCode(TicketContext.TICKET_SYS_SEND_TYPE);
            String value = currentTicketConfigValueByCode.getValue();

            if ("1".equals(value)) {
                return 1;
            }

            if ("0".equals(value)) {
                return 0;
            }

            if ("2".equals(value)) {
                return 2;
            }
        } catch (Exception ex) {
            logger.error("[????????????]????????????????????????:ex:{}", LogExceptionStackTrace.erroStackTrace(ex));
        }
        return 1;
    }

    /**
     * ??????????????????
     */
    @Override
    public boolean getSendSwitch() {
        try {
            TicketConfigENT currentTicketConfigValueByCode = getCurrentTicketConfigValueByCode(TicketContext.TICKET_SYS_SEND_SWITCH);
            String value = currentTicketConfigValueByCode.getValue();

            if ("1".equals(value)) {
                return true;
            }

            if ("0".equals(value)) {
                return false;
            }
        } catch (Exception ex) {
            logger.error("[????????????]????????????????????????:ex:{}", LogExceptionStackTrace.erroStackTrace(ex));
        }
        return true;
    }

    /*@Override
    public boolean getSendSwitch() {
        try {
            TicketConfigENT currentTicketConfigValueByCode = getCurrentTicketConfigValueByCode(TicketContext.TICKET_SYS_SEND_SWITCH);
            String value = currentTicketConfigValueByCode.getValue();

            if ("1".equals(value)) {
                return true;
            }

            if ("0".equals(value)) {
                return false;
            }
        } catch (Exception ex) {
            logger.error("[????????????]????????????????????????:ex:{}", LogExceptionStackTrace.erroStackTrace(ex));
        }
        return true;
    }

    *//**
     * ??????server??????????????????
     *
     * @param serverNodeName ServerA/ServerB
     * @return ??????????????????????????????????????????
     *//*
    @Override
    public boolean getServerNodeEnableStatus(String serverNodeName) {
        try {
            TicketConfigENT currentTicketConfigValueByCode = null;
            if ("ServerA".equalsIgnoreCase(serverNodeName)) {
                currentTicketConfigValueByCode = getCurrentTicketConfigValueByCode(TicketContext.TICKET_CONFIG_SERVER_NODE_A_SWITCH);
            }

            if ("ServerB".equalsIgnoreCase(serverNodeName)) {
                currentTicketConfigValueByCode = getCurrentTicketConfigValueByCode(TicketContext.TICKET_CONFIG_SERVER_NODE_B_SWITCH);
            }

            if (null == currentTicketConfigValueByCode) {
                return true;
            }

            //??????0??????????????????
            if (currentTicketConfigValueByCode.getValue().trim().equals("0")) {
                return false;
            }
        } catch (Exception ex) {
            logger.error("[????????????]???????????????????????????????????????::serverNodeName{},ex:{}", serverNodeName, LogExceptionStackTrace.erroStackTrace(ex));
        }
        return true;
    }*/
}
