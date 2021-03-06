package com.tty.ots.trade.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jdd.fm.core.annotations.Action;
import com.jdd.fm.core.annotations.ActionController;
import com.jdd.fm.core.ehcache.EhcacheManager;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.token.UserVO;
import com.jdd.fm.core.utils.web.WebActionDispatcher;
import com.tty.common.utils.IpUtils;
import com.tty.ots.trade.context.RMUtil;
import com.tty.ots.trade.context.ErrorMsgEnum;
import com.tty.ots.trade.dto.TradeBankCardDTO;
import com.tty.ots.trade.ent.TradeFundENT;
import com.tty.ots.trade.service.ApiTradeService;
import com.tty.ots.trade.service.TradeInnerFundService;
import com.tty.ots.trade.service.TradeUserBankCardService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@ActionController
@RequestMapping(value = "trade/public", method = RequestMethod.POST)
public class TradeApiController {
    private static final Logger logger = LoggerFactory.getLogger(TradeApiController.class);

    @Autowired
    private EhcacheManager ehcacheManager;
    @Autowired
    private ApiTradeService apiTradeService;
    @Autowired
    private TradeUserBankCardService tradeUserBankCardService;
    @Autowired
    private TradeInnerFundService tradeInnerFundService;

    @RequestMapping(value = "/securityApiHandler")
    public ResultModel tradeApiHandlerDispatch(HttpServletRequest request) {
        ResultModel rm = new ResultModel();
        try {
            rm = (ResultModel) WebActionDispatcher.invoke(request, ehcacheManager);
        } catch (Exception e) {
            logger.error("trade????????????,stackTrace={}", LogExceptionStackTrace.erroStackTrace(e));
            rm.setCode(ResultModel.ERROR);
            rm.setMsg(ResultModel.MSG_ERROR_DESC);
        }
        return rm;
    }

    /**
     * Action=108????????????????????????
     *
     * @param header
     * @param body
     * @return
     */
    @Action(value = "108", tokenValidated = true, request = true)
    public ResultModel onlinePay(ClientRequestHeader header, Object body, HttpServletRequest request, UserVO user) {
        String ip = IpUtils.getIpAddr(request);
        return apiTradeService.payOnline(header, user.getUserid(), String.valueOf(body), ip);
    }


    /**
     * Action=1084????????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1084", tokenValidated = true)
    public ResultModel chkRecharge(ClientRequestHeader header, Object body, UserVO user) {
        return apiTradeService.chkRecharge(header, user.getUserid(), String.valueOf(body));
    }

    /**
     * Action=109??????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "109", tokenValidated = true)
    public ResultModel distillMoney(ClientRequestHeader header, Object body, UserVO user) {
        return apiTradeService.distillMoney(header, user.getUserid(), String.valueOf(body));
    }

    /**
     * Action=1092??????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1092", tokenValidated = true)
    public ResultModel distillDetails(ClientRequestHeader header, Object body, UserVO user) {
        return apiTradeService.distillDetails(header, user.getUserid(), String.valueOf(body));
    }

    /**
     * Action=1052???????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1052", tokenValidated = true)
    public ResultModel bindUserBankcard(ClientRequestHeader header, Object body, UserVO user) {
        JSONObject params = JSONObject.parseObject(String.valueOf(body));
        String realName = params.getString("RealityName"); //????????????
        String cityId = params.getString("CityID");         //??????id
        Integer bankId = params.getInteger("BankTypeID");  //??????id
        String bankName = params.getString("BankName");    //????????????
        String cardNumber = params.getString("BankCardNumber");//????????????
        Long successBindId = params.getLong("SuccBindID"); //id
        Long failedBindId = params.getLong("FailedBindID");//id

        Long userId = user.getUserid();

        Integer nCityId = null;
        if (NumberUtils.isNumber(cityId)) {
            nCityId = Integer.parseInt(cityId);
        }

        //1. ?????????????????????, ??????????????????
        if (successBindId != null && successBindId.intValue() > 0) {
            ResultModel rm = tradeUserBankCardService.bankEdit(header.getTraceID(), userId, bankId, successBindId, nCityId, bankName);
            return rm;
        }

        //2. ?????????????????????????????????????????????????????????????????????
        if (failedBindId != null && failedBindId.intValue() > 0) {
            tradeUserBankCardService.updateUserBankcardStatus(userId, failedBindId, 0, "????????????,????????????");
        }

        //3. ???????????????
        return tradeUserBankCardService.bindCard(header.getTraceID(), userId, nCityId, realName, cardNumber);
    }

    /**
     * ???????????? ?????? ???????????? ????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1625", tokenValidated = true)
    public ResultModel isNeedPwd(ClientRequestHeader header, Object body, UserVO user) {
        return apiTradeService.isNeedPwd(header, user.getUserid(), String.valueOf(body));
    }

    /**
     * ??????????????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1626", tokenValidated = true)
    public ResultModel getUserPayConfig(ClientRequestHeader header, Object body, UserVO user) {
        return apiTradeService.getUserPayConfig(header, user.getUserid());
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1627", tokenValidated = true)
    public ResultModel setUserPaypwdConfig(ClientRequestHeader header, Object body, UserVO user) {
        return apiTradeService.setUserPaypwdConfig(header, user.getUserid(), String.valueOf(body));
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1628", tokenValidated = true)
    public ResultModel validatePayPwd(ClientRequestHeader header, Object body, UserVO user) {
        return apiTradeService.validatePayPwd(header, user.getUserid(), String.valueOf(body));
    }

    /**
     * Action=1071?????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1071", tokenValidated = true)
    public ResultModel getUserBalance(ClientRequestHeader header, Object body, UserVO user) {
        return apiTradeService.getUserBalance(header, user.getUserid());
    }

    /**
     * Action=1072?????????????????????????????????????????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1072", tokenValidated = true)
    public ResultModel getUserBankCardList(ClientRequestHeader header, Object body, UserVO user) {
        return tradeUserBankCardService.getUserBankCardList(user.getUserid(), header.getTraceID());
    }

    /**
     * Action=1073???????????????????????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1073", tokenValidated = true)
    public ResultModel getUserDistillAndBankCardList(ClientRequestHeader header, Object body, UserVO user) {
        ResultModel rm = tradeUserBankCardService.getUserBankCardAllList(user.getUserid(), header.getTraceID());
        if (rm.getCode() < 0) {
            return rm;
        }
        JSONObject json = new JSONObject();
        List<TradeBankCardDTO> list = (List<TradeBankCardDTO>) rm.getData();
        json.put("cardsNumber",list.size());
        List<TradeBankCardDTO> reList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(list)) {
            reList = list.stream().filter(d -> d.getStatus() == 1).collect(Collectors.toList());
        }
        TradeFundENT tradeFundENT = tradeInnerFundService.getTradeFundENT(user.getUserid());

        if (tradeFundENT == null) {
            json.put("distillBalance", BigDecimal.ZERO);
        }else {
            json.put("distillBalance", tradeFundENT.getDistillBalance());
        }
        json.put("cards", reList);
        rm.setData(json);
        rm.setCode(ResultModel.SUCCESS);
        return rm;
    }

    /**
     * Action=10720????????????????????????????????????????????????????????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "10720", tokenValidated = true)
    public ResultModel getUserBankCardAllList(ClientRequestHeader header, Object body, UserVO user) {
        return tradeUserBankCardService.getUserBankCardAllList(user.getUserid(), header.getTraceID());
    }

    /**
     * Action=1053???????????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1053", tokenValidated = true)
    public ResultModel setDefaultbank(ClientRequestHeader header, Object body, UserVO user) {
        JSONObject param = JSONObject.parseObject(String.valueOf(body));
        Long bindId = param.getLong("BindID");
        if (bindId == null) {
            return RMUtil.error(ErrorMsgEnum.PARAMETER_ERROR);
        }
        return tradeUserBankCardService.setDefaultbank(user.getUserid(), bindId);
    }

    /**
     * Action=1051????????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1051", tokenValidated = true)
    public ResultModel unbind(ClientRequestHeader header, Object body, UserVO user) {
        JSONObject param = JSONObject.parseObject(String.valueOf(body));
        Long bindId = param.getLong("BindID");
        if (bindId == null) {
            return RMUtil.error(ErrorMsgEnum.PARAMETER_ERROR);
        }
        return tradeUserBankCardService.unbind(user.getUserid(), bindId);
    }

    /**
     * Action=10510????????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "10510", tokenValidated = true)
    public ResultModel removeBankCard(ClientRequestHeader header, Object body, UserVO user) {
        JSONObject param = JSONObject.parseObject(String.valueOf(body));
        Long bindId = param.getLong("BindID");
        if (bindId == null) {
            return RMUtil.error(ErrorMsgEnum.PARAMETER_ERROR);
        }
        return tradeUserBankCardService.removeBankCard(user.getUserid(), bindId);
    }

    /**
     * Action=1100???????????????,??????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1100", tokenValidated = true)
    public ResultModel getUserFundDetails(ClientRequestHeader header, Object body, UserVO user) {
        return apiTradeService.getUserFundDetails(header, user.getUserid(), String.valueOf(body));
    }


    /**
     * Action=1101???????????????????????????
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1101", tokenValidated = true)
    public ResultModel getUserTransferDetails(ClientRequestHeader header, Object body, UserVO user) {
        return apiTradeService.getUserTransferDetails(header, user.getUserid(), String.valueOf(body));
    }

    /**
     * Action=1102??????????????????????????????
     *
     * @param header
     * @param body
     * @return
     */
    @Action(value = "1102", tokenValidated = true, request = true)
    public ResultModel getTransferOnline(ClientRequestHeader header, Object body, HttpServletRequest request, UserVO user) {
        String ip = IpUtils.getIpAddr(request);
        return apiTradeService.getTransferOnline(header, user.getUserid(), String.valueOf(body), ip);
    }

}
