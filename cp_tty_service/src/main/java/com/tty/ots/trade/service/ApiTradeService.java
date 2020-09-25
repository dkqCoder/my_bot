package com.tty.ots.trade.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ResultModel;


public interface ApiTradeService {
    ResultModel payOnline(ClientRequestHeader header, Long userId, String body, String ip);

    ResultModel chkRecharge(ClientRequestHeader header, Long userId, String body);

    ResultModel distillMoney(ClientRequestHeader header, Long userId, String body);

    ResultModel distillDetails(ClientRequestHeader header, Long userId, String body);

    ResultModel isNeedPwd(ClientRequestHeader header, Long userId, String body);

    ResultModel getUserPayConfig(ClientRequestHeader header, Long userId);

    ResultModel setUserPaypwdConfig(ClientRequestHeader header, Long userId, String body);

    ResultModel validatePayPwd(ClientRequestHeader header, Long userId, String body);

    ResultModel getUserBalance(ClientRequestHeader header, Long userId);

    ResultModel getUserFundDetails(ClientRequestHeader header, Long userId, String body);

    ResultModel getUserTransferDetails(ClientRequestHeader header, Long userId, String body);

    ResultModel getTransferOnline(ClientRequestHeader header, Long userId, String body, String ip);

}
