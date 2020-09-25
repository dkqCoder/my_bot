package com.tty.ticket.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.DateUtil;
import com.tty.common.utils.LotteryUtil;
import com.tty.ticket.api.vo.MerchantSendDataItemVO;
import com.tty.ticket.api.vo.MerchantSendDataVO;
import com.tty.ticket.api.vo.MerchantTicketVO;
import com.tty.ticket.context.TicketRedisKeyContext;
import com.tty.ticket.dto.TicketDTO;
import com.tty.ticket.dto.TicketPrintSpDTO;
import com.tty.ticket.ent.TicketTicketENT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by yys on 2017/7/6.
 */
@Component("ticketBiz")
public class TicketBiz {

    @Autowired
    private JedisClusterFactory jedisClusterFactory;

    @Autowired
    private AgentRouteBiz agentRouteBiz;

    private static final Logger logger = LoggerFactory.getLogger(TicketBiz.class);

    /**
     * 按出票商，彩种,和期次进行分组
     * 竞彩足球和竞彩篮球 不按期次进行分组
     *
     * @param ticketDTOList
     * @return
     */
    public Map<String, List<TicketDTO>> groupByAgentAndLotteryAndIssueName(List<TicketDTO> ticketDTOList) {
        return ticketDTOList.stream().collect(Collectors.groupingBy((TicketDTO r) -> {
            String issueNameFlag = r.getIssueName();
            if (90 == r.getLotteryId()) {
                issueNameFlag = "JCZQ90";
            } else if (91 == r.getLotteryId()) {
                issueNameFlag = "JCLQ91";
            }
            return String.format("%s_%s_%s", r.getAgentId(), r.getLotteryId(), issueNameFlag);
        }));
    }

    public long generatePrintSpId(Long schemeId) {
        Long serial = jedisClusterFactory.incr(TicketRedisKeyContext.TICKET_PRINT_SP_SERIAL);
        return Long.valueOf(String.format("%s%s", serial, new DecimalFormat("000").format(schemeId % 1000)));
    }

    /**
     * 合众出票商报文ID后8位
     *
     * @return
     */
    public String generateHeZhongIdSerial() {
        String redisKey = String.format(TicketRedisKeyContext.TICKET_HEZHONG_ID_SERIAL, DateUtil.format(new Date(), "yyyyMMdd"));
        Long serial = jedisClusterFactory.incr(redisKey);
        jedisClusterFactory.expire(redisKey, DateUtil.SECONDS_OF_DAY);
        return new DecimalFormat("00000000").format(serial);
    }

    /**
     * 170416304=4_2S1.750F227.5,170416303=4_2S1.750F204.5,170416302=3_02S3.750/03S3.850,170416301=4_2S1.700F209.5
     * 返回出票SP 给商户
     *
     * @param ticketPrintSpDTOList
     * @return
     */
    public String generatePrintSpContent(List<TicketPrintSpDTO> ticketPrintSpDTOList) {
        //按场次分组
        Map<String, List<TicketPrintSpDTO>> map = new TreeMap<>();

        if (CollectionUtils.isEmpty(ticketPrintSpDTOList)) {
            return "";
        }

        for (TicketPrintSpDTO spDTO : ticketPrintSpDTOList) {
            if (map.containsKey(spDTO.getIssueMatchName())) {
                map.get(spDTO.getIssueMatchName()).add(spDTO);
            } else {
                List<TicketPrintSpDTO> tmp = new ArrayList<>();
                tmp.add(spDTO);
                map.put(spDTO.getIssueMatchName(), tmp);
            }
        }

        return map.values().stream().map(singleMatch -> String.format("%s=%s_%s%s",
                singleMatch.get(0).getIssueMatchName(),
                singleMatch.get(0).getPlaytypeId() % 10,
                singleMatch.stream().sorted(Comparator.comparing(TicketPrintSpDTO::getOption)).map(r -> String.format("%sS%s", r.getOption(), r.getSp())).collect(Collectors.joining("/")),
                singleMatch.stream().findFirst().map(r -> {
                    String score = "";
                    if (null != r.getOptionScore() && r.getOptionScore().compareTo(BigDecimal.ZERO) != 0) {
                        score = String.format("F%s", r.getOptionScore());
                    }
                    return score;
                }).get()
        )).collect(Collectors.joining(","));
    }


    public List<TicketDTO> wrapTicketDTOList(String merchantId, MerchantSendDataVO merchantSendDataVO) {
        List<TicketDTO> ticketDTOList = new ArrayList<>();
        for (MerchantSendDataItemVO dataItem : merchantSendDataVO.getData()) {
            String redisKey = String.format(TicketRedisKeyContext.TICKET_TICKET_DTO, dataItem.getTicketNo());

            if (jedisClusterFactory.exists(redisKey)) {
                logger.warn("[出票]票号已存在,ticketNo:{}", dataItem.getTicketNo());
                continue;
            }

            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setMoney(dataItem.getMoney());
            ticketDTO.setLotteryId(dataItem.getLotteryId());
            ticketDTO.setMultiple(dataItem.getMultiple());
            ticketDTO.setSchemeId(dataItem.getSchemeId());
            ticketDTO.setNumber(dataItem.getContent());
            ticketDTO.setPlayTypeId(dataItem.getPlayType());
            ticketDTO.setTicketNo(dataItem.getTicketNo());
            ticketDTO.setIssueName(dataItem.getIssueName());
            ticketDTO.setMerchantId(Integer.valueOf(merchantId));
            ticketDTO.setUnitCount(LotteryUtil.getUnitCount(dataItem.getPlayType(), dataItem.getMoney(), dataItem.getMultiple()));
            ticketDTO.setPrintEndTime(DateUtil.format(dataItem.getPrintEndTime()));
            ticketDTO.setAgentId(agentRouteBiz.getAgentId(ticketDTO));
            ticketDTOList.add(ticketDTO);
        }
        return ticketDTOList;
    }

    public List<MerchantTicketVO> wrapMerchantTicketVOList(List<TicketTicketENT> list) {
        List<MerchantTicketVO> voList = Lists.newArrayList();
        list.forEach(ent -> {
            MerchantTicketVO merchantTicketVO = new MerchantTicketVO();
            merchantTicketVO.setPrintStatus(ent.getPrintStatus());
            merchantTicketVO.setPrintTicketNo(ent.getPrintTicketNo());
            merchantTicketVO.setPrintTime(ent.getPrintTime());
            merchantTicketVO.setTicketNo(ent.getTicketNo());
            voList.add(merchantTicketVO);
        });
        return voList;
    }
}
