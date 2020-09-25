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
            logger.error("trade接口异常,stackTrace={}", LogExceptionStackTrace.erroStackTrace(e));
            rm.setCode(ResultModel.ERROR);
            rm.setMsg(ResultModel.MSG_ERROR_DESC);
        }
        return rm;
    }

    /**
     * Action=108，充值、即买即付
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
     * Action=1084获取充值到账状态
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
     * Action=109银行提现申请
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
     * Action=1092银行提款详情
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
     * Action=1052绑定银行卡
     *
     * @param header
     * @param body
     * @param user
     * @return
     */
    @Action(value = "1052", tokenValidated = true)
    public ResultModel bindUserBankcard(ClientRequestHeader header, Object body, UserVO user) {
        JSONObject params = JSONObject.parseObject(String.valueOf(body));
        String realName = params.getString("RealityName"); //真实姓名
        String cityId = params.getString("CityID");         //城市id
        Integer bankId = params.getInteger("BankTypeID");  //银行id
        String bankName = params.getString("BankName");    //银行名称
        String cardNumber = params.getString("BankCardNumber");//银行卡号
        Long successBindId = params.getLong("SuccBindID"); //id
        Long failedBindId = params.getLong("FailedBindID");//id

        Long userId = user.getUserid();

        Integer nCityId = null;
        if (NumberUtils.isNumber(cityId)) {
            nCityId = Integer.parseInt(cityId);
        }

        //1. 修改银行卡信息, 维护开户地区
        if (successBindId != null && successBindId.intValue() > 0) {
            ResultModel rm = tradeUserBankCardService.bankEdit(header.getTraceID(), userId, bankId, successBindId, nCityId, bankName);
            return rm;
        }

        //2. 若银行卡绑定失败重新绑定，将原有失败银行卡删除
        if (failedBindId != null && failedBindId.intValue() > 0) {
            tradeUserBankCardService.updateUserBankcardStatus(userId, failedBindId, 0, "重新绑定,删除此卡");
        }

        //3. 绑定银行卡
        return tradeUserBankCardService.bindCard(header.getTraceID(), userId, nCityId, realName, cardNumber);
    }

    /**
     * 用户支付 是否 需要验证 支付密码
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
     * 获取支付密码相关配置
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
     * 开启关闭支付密码，开启关闭小额免密支付，设置免密金额
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
     * 验证用户输入的支付密码是否正确
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
     * Action=1071，获取用户余额
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
     * Action=1072，获取用户绑定银行卡（成功和审核中的）
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
     * Action=1073，获取用户绑定成功的银行卡
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
     * Action=10720，获取用户绑定银行卡（成功和审核中和审核失败的）
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
     * Action=1053，设置默认提现银行
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
     * Action=1051，解除绑定银行卡
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
     * Action=10510，删除绑定银行卡
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
     * Action=1100，账户明细,区分正负符号
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
     * Action=1101，获取用户转账列表
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
     * Action=1102，获取转账交易号信息
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
