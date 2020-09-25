package com.tty.ticket.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.tty.ticket.dto.*;
import com.tty.ticket.ent.TicketAgentENT;
import com.tty.ticket.ent.TicketTicketENT;

import java.util.List;
import java.util.Map;

/**
 * 电子票
 *
 * @author yys
 * @create 2017-07-04 17:26:13
 **/
public interface TicketTicketService {

    ExtModel listTicketTicketBySchemeId(Long schemeId, JSONObject jsonParam);

    ExtModel listTicketTicketByTicketNo(String ticketNo, JSONObject jsonParam);

    void cacheTicketDTO(TicketDTO ticketDTO);

    TicketDTO getCurrentTicketDTO(String ticketNo);

    SendTicketsResultDTO saveAndSendTickets(List<TicketDTO> ticketDTOList);

    void asyncSaveAndSendTickets(List<TicketDTO> ticketDTOList);

    /**
     * 重新投注
     *
     * @param ticketNos
     * @param result
     */
    void resend(String ticketNos, ExtModel result);

    /**
     * 转投
     *
     * @param ticketNos
     * @param agentId
     * @param result
     */
    void redirect(String ticketNos, Integer agentId, String loginName, boolean isForce, ExtModel result);

    /**
     * 刷新
     * 0.查询出票状态
     * 1.反馈主站
     *
     * @param ticketNos
     * @param result
     */
    void refresh(String ticketNos, ExtModel result);

    /**
     * 反馈主站
     *
     * @param ticketNos
     * @param result
     */
    void feedback(String ticketNos, ExtModel result);

    /**
     * 系统撤单
     *
     * @param ticketNos
     * @param result
     */
    void quash(String ticketNos, String quashReason, Integer compensateStatus, ExtModel result);

    /**
     * 强制出票
     *
     * @param ticketNos
     * @param result
     */
    void forcePrint(String ticketNos, ExtModel result);

    boolean processAgentResponse(List<AgentResponseDTO> agentResponseDTOList);

    List<TicketTicketExceptionDTO> listExceptionTicket();

    List<Map<String, Object>> listSumMoneyGroupByPrintStatus(JSONObject search);

    Map<String, Object> listSumMoneyByQuashStatus(JSONObject search);

    /**
     * 查询主动查询的电子票
     *
     * @param lotteryIds
     * @param agentId
     * @param userId
     * @return
     */
    List<TicketTicketENT> findQueryList(List<Integer> lotteryIds, Integer agentId, int limits, String orderBy, Long userId);

    void cacheExceptionTicketDTO(TicketDTO ticketDTO);

    /**
     * 查询需要重投的电子票（未投注，投注失败）
     *
     * @param lotteryIds
     * @return
     */
    List<TicketTicketENT> findSendFailList(List<Integer> lotteryIds, long userId, boolean isGpc);

    /**
     * 投注
     *
     * @param ticketDTOList
     * @return 投注详情对象
     */
    SendTicketsResultDTO sendTickets(List<TicketDTO> ticketDTOList);

    List<Map<String, Object>> listTicketGroupByAgent(List<TicketAgentENT> agentList, JSONObject search);

    List<TicketTicketENT> findMerchantWinList(Long userId, int start, int limit);

    Long findMerchantWinCount(Long userId);

    void queryAndSaveAgentWinMoney(List<TicketDTO> ticketDTOList);

    int updatePassInfo(WinMoneyMqDTO winMoneyMqDTO, Long schemeId);

    List<TicketTicketENT> findListByTickets(List<String> ticketNoList, String merchantId);

    List<TicketDTO> saveAndSendTicketsOneByOne(List<TicketDTO> ticketDTOList);

}
