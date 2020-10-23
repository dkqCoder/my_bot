package com.tty.ticket.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.ticket.biz.TicketBiz;
import com.tty.ticket.common.util.TicketUtil;
import com.tty.ticket.context.TicketContext;
import com.tty.ticket.context.TicketRedisKeyContext;
import com.tty.ticket.dao.TicketTicketDao;
import com.tty.ticket.dto.SendTicketsResultDTO;
import com.tty.ticket.dto.TicketDTO;
import com.tty.ticket.ent.TicketTicketENT;
import com.tty.ticket.service.TicketConfigService;
import com.tty.ticket.service.TicketTicketService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ticketTicketService")
public class TicketTicketServiceImpl implements TicketTicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketTicketService.class);

    @Autowired
    private TicketTicketDao ticketTicketDao;

    @Autowired
    private JedisClusterFactory jedisClusterFactory;

    @Autowired
    private TicketConfigService ticketConfigService;

    /**
     * 核心投注逻辑 2.入库 3.投注
     *
     * @param ticketDTOList 待投注电子票集合
     * @return 投注成功的票号集合
     */
    @Override
    public SendTicketsResultDTO saveAndSendTickets(List<TicketDTO> ticketDTOList) {
        return saveAndSendTickets(ticketDTOList, false);
    }

    /**
     *
     * @param ticketDTOList
     * @param isAsync
     * @return
     */
    private SendTicketsResultDTO saveAndSendTickets(List<TicketDTO> ticketDTOList, boolean isAsync) {
        logger.info("[出票系统]开始投注:{}", GfJsonUtil.toJSONString(ticketDTOList));

        /**
         * 去重
         */
        ticketDTOList = removeDuplicateTicket(ticketDTOList);

        /**
         * 入库
         */
        List<TicketDTO> successSaveTickets = saveTicketsByShard(ticketDTOList);

        /**
         * 缓存入库成功的电子票
         */
        successSaveTickets.forEach(this::cacheTicketDTO);

        /**
         * 投注
         */
        SendTicketsResultDTO sendTicketsResultDTO = new SendTicketsResultDTO();
        if (isAsync) {
            asyncSendTickets(successSaveTickets);
        } else {
            sendTicketsResultDTO = sendTickets(successSaveTickets);
        }
        return sendTicketsResultDTO;
    }

    private List<TicketDTO> removeDuplicateTicket(List<TicketDTO> ticketDTOList) {
        /**
         * 去除可能存在的重复票，票号一样
         */
        List<TicketDTO> distinctTicketDTOList = new ArrayList<>();
        Set<String> ticketNoSet = new HashSet<>();
        for (TicketDTO r : ticketDTOList) {
            if (ticketDTOList.contains(r.getTicketNo())) {
                continue;
            }
            distinctTicketDTOList.add(r);
            ticketNoSet.add(r.getTicketNo());
        }
        return distinctTicketDTOList;
    }

    /**
     * 按库位进行分组，并进行批量入库, 如果批量入库发生错误，就逐条进行入库 saveTicketOneByOne
     *
     * @return 入库成功的数据
     */
    private List<TicketDTO> saveTicketsByShard(List<TicketDTO> ticketDTOList) {
        logger.info("[出票系统]电子票入库开始:{}", GfJsonUtil.toJSONString(ticketDTOList));
        Map<String, List<TicketDTO>> map = ticketDTOList.stream()
            .collect(Collectors.groupingBy(r -> TicketUtil.getShardByOrderNo(r.getSchemeId())));

        List<TicketDTO> successTickets = new ArrayList<>();
        for (List<TicketDTO> group : map.values()) {
            try {
                Long schemeId = group.get(0).getSchemeId();
                ticketTicketDao.batchSaveTickets(schemeId, group);
                successTickets.addAll(group);
            } catch (Exception ex) {
                logger.error("[出票系统]电子票第一次入库失败:ex:{},data:{}", LogExceptionStackTrace.erroStackTrace(ex), GfJsonUtil.toJSONString(group));
                //逐条进行入库尝试
                List<TicketDTO> success = saveTicketOneByOne(group);
                if (CollectionUtils.isNotEmpty(success)) {
                    successTickets.addAll(success);
                }
            }
        }
        logger.info("[出票系统]电子票入库结束:{}", GfJsonUtil.toJSONString(ticketDTOList));

        return successTickets;
    }

    /**
     * 处理入库失败的电子票
     *
     * @param ticketDTOList 入库失败的电子票
     * @return 重试入库成功
     */
    private List<TicketDTO> saveTicketOneByOne(List<TicketDTO> ticketDTOList) {
        List<TicketDTO> success = new ArrayList<>();
        for (TicketDTO ticketDTO : ticketDTOList) {
            try {
                ticketTicketDao.saveTicket(ticketDTO.getSchemeId(), ticketDTO);
                success.add(ticketDTO);
            } catch (Exception ex) {
                //未入库的数据，进入缓存异常队列
                cacheExceptionTicketDTO(ticketDTO);
                logger.error("[出票系统]电子票第二次逐条入库失败:ex:{},data:{}", LogExceptionStackTrace.erroStackTrace(ex), GfJsonUtil.toJSONString(ticketDTO));
            }
        }
        return success;
    }

    public void cacheExceptionTicketDTO(TicketDTO ticketDTO) {
        jedisClusterFactory.hset(TicketRedisKeyContext.TICKET_TICKET_DTO_EXCEPTION, ticketDTO.getTicketNo(), GfJsonUtil.toJSONString(ticketDTO));
    }

    public void cacheTicketDTO(TicketDTO ticketDTO) {
        String redisKey = String.format(TicketRedisKeyContext.TICKET_TICKET_DTO, ticketDTO.getTicketNo());
        jedisClusterFactory.set(redisKey, GfJsonUtil.toJSONString(ticketDTO));
        jedisClusterFactory.expire(redisKey, TicketContext.DAY_SECONDS * 7);
    }

    /**
     * 向出票商进行投注，并更新数据库状态 说明：投注异常的票（投注发生异常，如网络原因等，明确错误编码的电子票不在此返回）会进行重试
     *
     * @param ticketDTOList 待投注的电子票
     * @return void
     */
    private void asyncSendTickets(List<TicketDTO> ticketDTOList) {
        if (!ticketConfigService.getSendSwitch()) {
            logger.error("[出票系统]投注开关关闭状态，不进行投注");
            return;
        }

        if (CollectionUtils.isEmpty(ticketDTOList)) {
            logger.info("[出票系统]待投注的列表为空");
            return;
        }

    }

    /**
     * 向出票商进行投注，并更新数据库状态 不进行重试
     *
     * @param ticketDTOList 待投注的电子票
     * @return 投注详情对象[说明：投注异常的票（投注发生异常，如网络原因等，明确错误编码的电子票不在此返回）]
     */
    public SendTicketsResultDTO sendTickets(List<TicketDTO> ticketDTOList) {
        SendTicketsResultDTO sendTicketsResultDTO = new SendTicketsResultDTO(ticketDTOList);

        if (!ticketConfigService.getSendSwitch()) {
            logger.error("[出票系统]投注开关关闭状态，不进行投注");
            return sendTicketsResultDTO;
        }

        if (CollectionUtils.isEmpty(ticketDTOList)) {
            logger.info("[出票系统]待投注的列表为空");
            return sendTicketsResultDTO;
        }

        return sendTicketsResultDTO;
    }

    @Transactional(readOnly = true)
    @DataSource(name = DataSource.DATA_SOURCE_READ_MYCAT)
    @Override
    public List<TicketTicketENT> findListByTickets(List<String> ticketNoList, String merchantId) {
        return ticketTicketDao.findListByTickets(ticketNoList, merchantId);
    }


}
