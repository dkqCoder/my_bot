package com.tty.ticket.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yys on 2017/8/28.
 */
public class SendTicketsResultDTO {

    public SendTicketsResultDTO() {
    }

    public SendTicketsResultDTO(List<TicketDTO> sendList) {
        this.sendList = sendList;
    }

    /**
     * 投注内容
     */
    private List<TicketDTO> sendList = new ArrayList<>();

    /**
     * 投注成功
     */
    private List<TicketDTO> sendSuccessList = new ArrayList<>();

    /**
     * 投注失败
     */
    private List<TicketDTO> sendFailList = new ArrayList<>();

    /**
     * 投注异常
     */
    private List<TicketDTO> sendExceptionList = new ArrayList<>();

    /**
     * 投注返回内容
     */
    private List<SendResponseDTO> sendResponseDTOList = new ArrayList<>();

    public List<TicketDTO> getSendList() {
        return sendList;
    }

    public void setSendList(List<TicketDTO> sendList) {
        this.sendList = sendList;
    }

    public List<TicketDTO> getSendSuccessList() {
        return sendSuccessList;
    }

    public void setSendSuccessList(List<TicketDTO> sendSuccessList) {
        this.sendSuccessList = sendSuccessList;
    }

    public List<TicketDTO> getSendFailList() {
        return sendFailList;
    }

    public void setSendFailList(List<TicketDTO> sendFailList) {
        this.sendFailList = sendFailList;
    }

    public List<TicketDTO> getSendExceptionList() {
        return sendExceptionList;
    }

    public void setSendExceptionList(List<TicketDTO> sendExceptionList) {
        this.sendExceptionList = sendExceptionList;
    }

    public List<SendResponseDTO> getSendResponseDTOList() {
        return sendResponseDTOList;
    }

    public void setSendResponseDTOList(List<SendResponseDTO> sendResponseDTOList) {
        this.sendResponseDTOList = sendResponseDTOList;
    }

    @Override
    public String toString() {
        return String.format("【投注详情,共%s张，投注成功:%s,投注失败:%s,投注异常:%s】", sendList.size(), sendSuccessList.size(), sendFailList.size(), sendExceptionList.size());
    }
}
