package com.tty.ots.trade.service.impl;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ResultModel;
import com.tty.ots.trade.service.ApiTradeService;
import org.springframework.stereotype.Service;

/**
 * @Author: sunyishun
 * @Date: 2020/10/25
 * @Description:
 */
@Service("apiTradeService")
public class ApiTradeServiceImpl implements ApiTradeService {
    @Override
    public ResultModel payOnline(ClientRequestHeader header, Long userId, String body, String ip) {
        return null;
    }

    @Override
    public ResultModel chkRecharge(ClientRequestHeader header, Long userId, String body) {
        return null;
    }

    @Override
    public ResultModel distillMoney(ClientRequestHeader header, Long userId, String body) {
        return null;
    }

    @Override
    public ResultModel distillDetails(ClientRequestHeader header, Long userId, String body) {
        return null;
    }

    @Override
    public ResultModel isNeedPwd(ClientRequestHeader header, Long userId, String body) {
        return null;
    }

    @Override
    public ResultModel getUserPayConfig(ClientRequestHeader header, Long userId) {
        return null;
    }

    @Override
    public ResultModel setUserPaypwdConfig(ClientRequestHeader header, Long userId, String body) {
        return null;
    }

    @Override
    public ResultModel validatePayPwd(ClientRequestHeader header, Long userId, String body) {
        return null;
    }

    @Override
    public ResultModel getUserBalance(ClientRequestHeader header, Long userId) {
        return null;
    }

    @Override
    public ResultModel getUserFundDetails(ClientRequestHeader header, Long userId, String body) {
        return null;
    }

    @Override
    public ResultModel getUserTransferDetails(ClientRequestHeader header, Long userId, String body) {
        return null;
    }

    @Override
    public ResultModel getTransferOnline(ClientRequestHeader header, Long userId, String body, String ip) {
        return null;
    }
}
