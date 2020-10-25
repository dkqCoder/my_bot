package com.tty.ticket.api;

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.ticket.api.vo.MerchantResultVO;
import com.tty.ticket.api.vo.MerchantSendDataVO;
import com.tty.ticket.api.vo.MerchantTicketVO;
import com.tty.ticket.api.vo.MerchantVO;
import com.tty.ticket.biz.MerchantBiz;
import com.tty.ticket.biz.TicketBiz;
import com.tty.ticket.context.TicketQueueContext;
import com.tty.ticket.dto.TicketDTO;
import com.tty.ticket.ent.TicketTicketENT;
import com.tty.ticket.service.TicketConfigService;
import com.tty.ticket.service.TicketTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yys on 2017/7/4.
 */
@RestController
@CrossOrigin
@RequestMapping("/ticket/public/merchant")
public class MerchantController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @Autowired
    private MerchantBiz merchantBiz;

    @Autowired
    private TicketBiz ticketBiz;

    @Autowired
    private TicketTicketService ticketTicketService;

    @Autowired
    private TicketConfigService ticketConfigService;

    @Autowired
    @Qualifier("jedisClusterFactory")
    private JedisClusterFactory jedisClusterFactory;

    /**
     * 接受商户投注
     */
    @RequestMapping(value = "/sendTickets", method = RequestMethod.POST)
    public ResultModel sendTickets(@RequestBody MerchantVO merchantVO) {
        logger.info("[出票系统][投注请求]接收投注信息:{}", GfJsonUtil.toJSONString(merchantVO));
        try {

            /** 数据合法性验证 */
            MerchantResultVO merchantResultVO = merchantBiz.checkSign(merchantVO);
            logger.info("[出票系统][投注请求]接收投注信息,明文信息:{}", GfJsonUtil.toJSONString(merchantResultVO));

            // TODO: 2020/10/12 待统一加密方式
            /*if (!merchantResultVO.isSuccess()) {
                return getErrorResult(merchantResultVO.getMsg());
            }*/

            /**
             * 1.过滤已经投注的电子票
             * 2.转换为标准投注格式
             * 3.分配出票商
             */

            //1.过滤已经投注的电子票
            MerchantSendDataVO merchantSendDataVO = GfJsonUtil.parseObject(merchantResultVO.getData(), MerchantSendDataVO.class);
            List<TicketDTO> ticketDTOList = ticketBiz.wrapTicketDTOList(merchantVO.getMerchantId(), merchantSendDataVO);

            //获取系统投注方式
            Integer sysSendType = ticketConfigService.getSysSendType();

            /**
             * 2: 异步投注(Redis)
             * 0：同步投注
             */
            if (sysSendType > 0) {//异步投注处理
                /**
                 * 入队列
                 */
                logger.info("[出票系统][投注请求]入队列,data:{}", GfJsonUtil.toJSONString(ticketDTOList));

                ticketDTOList.forEach(r -> {
                    String queue = String.format("%s_%s", TicketQueueContext.TICKET_TICKET_QUEUE, r.getLotteryId());
                    jedisClusterFactory.lpush(queue, GfJsonUtil.toJSONString(r));
                });
            } else {//同步处理
                /**
                 * 入库并且投注
                 */
                ticketTicketService.saveAndSendTickets(ticketDTOList);
            }
        } catch (Exception ex) {
            logger.error("[出票系统][投注请求]处理异常; params:{},errorStackTrace:{}", GfJsonUtil.toJSONString(merchantVO), LogExceptionStackTrace.erroStackTrace(ex));
            return getErrorResult();
        }
        return getSuccessResult();
    }

    /**
     * 电子票查询接口
     */
    @RequestMapping(value = "/queryTickets", method = RequestMethod.POST)
    public ResultModel queryTickets(@RequestBody MerchantVO merchantVO) {
        List<MerchantTicketVO> voList = null;
        try {

            //校验签名，解密电子票号
            MerchantResultVO merchantResultVO = merchantBiz.checkSign(merchantVO);
            if (!merchantResultVO.isSuccess()) {
                return getErrorResult(merchantResultVO.getMsg());
            }

            //根据参数解密获取ticket_no
            String[] ticketNos = merchantResultVO.getData().split(",");
            List<String> ticketNoList = Arrays.asList(ticketNos);
            if (ticketNos.length > 50) {
                ticketNoList = Arrays.stream(ticketNos).limit(50).collect(Collectors.toList());
            }

            //根据商户号和ticket_no从ticket_ticket中获取电子票信息
            List<TicketTicketENT> list = ticketTicketService.findListByTickets(ticketNoList, merchantVO.getMerchantId());

            //将电子票信息转成输出格式
            voList = ticketBiz.wrapMerchantTicketVOList(list);
            return getSuccessResult(voList);

        } catch (Exception e) {
            logger.error("[出票系统][查询电子票接口]处理异常json={},stackTrace={}", GfJsonUtil.toJSONString(merchantVO), LogExceptionStackTrace.erroStackTrace(e));
            return getErrorResult();
        }
    }

    private ResultModel getErrorResult() {
        ResultModel rm = new ResultModel();
        rm.setCode(ResultModel.ERROR);
        rm.setMsg(ResultModel.MSG_ERROR_DESC);
        return rm;
    }

    private ResultModel getSuccessResult() {
        ResultModel rm = new ResultModel();
        rm.setCode(ResultModel.SUCCESS);
        rm.setMsg(ResultModel.MSG_SUCCESS_DESC);
        return rm;
    }

    private ResultModel getErrorResult(String errMsg) {
        ResultModel rm = new ResultModel();
        rm.setCode(ResultModel.ERROR);
        rm.setMsg(errMsg);
        return rm;
    }

    private ResultModel getSuccessResult(Object data) {
        ResultModel rm = new ResultModel();
        rm.setCode(ResultModel.SUCCESS);
        rm.setMsg(ResultModel.MSG_SUCCESS_DESC);
        rm.setData(data);
        return rm;
    }

}
