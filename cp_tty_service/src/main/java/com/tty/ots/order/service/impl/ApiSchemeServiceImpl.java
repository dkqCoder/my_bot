package com.tty.ots.order.service.impl;

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.common.utils.ResultBO;
import com.tty.ots.order.bo.OrderResultBO;
import com.tty.ots.order.dto.OrderRequestDTO;
import com.tty.ots.order.helper.OrderParamsHelper;
import com.tty.ots.order.params.OrderParams;
import com.tty.ots.order.service.ApiSchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("apiSchemeService")
public class ApiSchemeServiceImpl implements ApiSchemeService {
    private Logger logger = LoggerFactory.getLogger(ApiSchemeService.class);

    @Override
    public ResultBO order(ClientRequestHeader header, Object body, Long userId) {
        String params = String.format("header:%s;body:%s", GfJsonUtil.toJSONString(header), GfJsonUtil.toJSONString(body));
        OrderResultBO orderCommonResult = new OrderResultBO();
        try {
            logger.info("[投注]开始处理请求,params:{}", params);

            //全局参数
            String traceId = header.getTraceID();

            /**
             * 预处理请求参数
             */
            OrderParams orderParams = GfJsonUtil.parseObject(OrderParamsHelper.prefixProcessRequestParams(String.valueOf(body)), OrderParams.class);
            int clientReqRecommType = orderParams.getRecommType();
            orderParams = OrderParamsHelper.prefixProcessOrderParams(orderParams);
            logger.info("[投注]参数解析：,orderParams:{}", GfJsonUtil.toJSONString(orderParams));

            /**
             * 封装通用投注参数
             */
            OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
            orderRequestDTO.setActivityCode(orderParams.getActivityCode());
            orderRequestDTO.setBetType(orderParams.getBetType());
            orderRequestDTO.setChooseType(orderParams.getChooseType());
            orderRequestDTO.setCmdName(header.getCmdName());
            orderRequestDTO.setCommissionRatio(orderParams.getCommissionRatio() == null ? new BigDecimal(0) : orderParams.getCommissionRatio());
            orderRequestDTO.setFromGame(orderParams.getFromGame());
            orderRequestDTO.setGameOrderNumber(orderParams.getGameOrderNumber());
            orderRequestDTO.setIssueId(orderParams.getIssueId());
            orderRequestDTO.setIssueName(orderParams.getIssueName());
            orderRequestDTO.setLotteryId(orderParams.getLotteryId());
            orderRequestDTO.setMasterUserId(orderParams.getMasterUserId());
            orderRequestDTO.setMoney(orderParams.getMoney());
            orderRequestDTO.setMultiple(orderParams.getMultiple());
            orderRequestDTO.setNumbers(orderBiz.mapToOrderNumberDTOList(orderParams.getNumbers()));
            orderRequestDTO.setRecommDescription(orderParams.getRecommDescription());
            orderRequestDTO.setRecommTitle(orderParams.getRecommTitle());
            orderRequestDTO.setRedPacketId(orderParams.getRedPacketId());
            orderRequestDTO.setSchemeType(orderParams.getSchemeType());
            orderRequestDTO.setRecommType(orderParams.getRecommType());
            orderRequestDTO.setUserId(userId);
            orderRequestDTO.setTraceId(traceId);
            orderRequestDTO.setSchemeShowStatus(orderParams.getPublicStatus());

            /**
             * 调用统一下单接口
             */
            OrderResponseDTO orderResponseDTO = orderBizService.order(orderRequestDTO);
            if (orderResponseDTO.getCode() < 0) {//订单生成失败
                return ApiHelper.getFailResult(orderResponseDTO.getMsg(), orderResponseDTO.getData());
            }

            /**
             * 如果是游戏订单：游戏的余额转金叶子，成功后直接返回
             */
            if (null != orderRequestDTO.getFromGame() && 1 == orderRequestDTO.getFromGame()) {
                return ApiHelper.getSuccessResult(orderResponseDTO.getData());
            }

            OrderSchemeENT orderSchemeENT = orderResponseDTO.getOrderSchemeENT();

            /**
             * 大神发单逻辑处理
             */
            if (90 == orderSchemeENT.getLotteryId() && 1 == orderSchemeENT.getRecommType()) {
                SchemeRecommMqDTO schemeRecommMqDTO = masterBiz.wrapSchemeRecommMqDTO(orderSchemeENT, orderRequestDTO, clientReqRecommType);
                fixedThreadPool.execute(() -> {
                    masterQueueSender.reportRecommInfo(schemeRecommMqDTO, traceId);
                });
            }

            orderCommonResult.setBalance(orderResponseDTO.getBalance());
            orderCommonResult.setOpenTime(orderResponseDTO.getOpenTime());
            orderCommonResult.setSchemeId(orderResponseDTO.getSchemeId());
        } catch (Exception e) {
            logger.error("[投注]下单失败,params:{},ex:{}", params, LogExceptionStackTrace.erroStackTrace(e));
            return ApiHelper.getFailResult();
        }

        return ApiHelper.getSuccessResult(orderCommonResult);
    }

    @Override
    public ResultBO getSchemeOptimize(ClientRequestHeader header, String params, Long userId) {
        return null;
    }

    @Override
    public ResultBO getSchemeTicket(String userId, String params, String traceId) {
        return null;
    }

    @Override
    public ResultBO remindScheme(ClientRequestHeader header, String params, Long userId) {
        return null;
    }

    @Override
    public ResultBO getSchemes(ClientRequestHeader header, Object body, boolean unPaidSO, Long userId) {
        return null;
    }

    @Override
    public ResultBO getCgjGyjSchemesNew(ClientRequestHeader header, Object body, Long userId) {
        return null;
    }

    @Override
    public ResultBO getLsmSchemes(ClientRequestHeader header, Object body, Long userId) {
        return null;
    }

    @Override
    public ResultBO recoverDelete(ClientRequestHeader header, Object body, Long userId) {
        return null;
    }

    @Override
    public ResultBO getUserBuyMatches(ClientRequestHeader header, Object body) {
        return null;
    }

    @Override
    public ResultBO getBetSummaryByMatchId(ClientRequestHeader header, Object body) {
        return null;
    }

    @Override
    public ResultBO getSchemeView(ClientRequestHeader header, String s) {
        return null;
    }

    @Override
    public ResultBO getLsmSchemeView(ClientRequestHeader header, String s) {
        return null;
    }

    @Override
    public ResultBO getSharedSchemeDetailBySchemaId(ClientRequestHeader header, String body) {
        return null;
    }

    @Override
    public ResultBO setShareStatusBySchemeId(ClientRequestHeader header, Object body, Long userId) {
        return null;
    }
}
