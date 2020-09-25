package com.tty.ots.order.service.impl;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.ResultBO;
import com.tty.ots.order.service.ApiSchemeService;
import org.springframework.stereotype.Service;


@Service("apiSchemeService")
public class ApiSchemeServiceImpl implements ApiSchemeService {

    @Override
    public ResultBO order(ClientRequestHeader header, Object body, Long userId) {
        return null;
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
