package com.tty.ots.order.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.ResultBO;


public interface ApiSchemeService {
    ResultBO order(ClientRequestHeader header, Object body, Long userId);
    
    ResultBO getSchemeOptimize(ClientRequestHeader header, String params, Long userId);

    ResultBO getSchemeTicket(String userId, String params, String traceId);

    ResultBO remindScheme(ClientRequestHeader header, String params, Long userId);

    ResultBO getSchemes(ClientRequestHeader header, Object body, boolean unPaidSO, Long userId);

    ResultBO getCgjGyjSchemesNew(ClientRequestHeader header, Object body, Long userId);

    ResultBO getLsmSchemes(ClientRequestHeader header, Object body, Long userId);

    ResultBO recoverDelete(ClientRequestHeader header, Object body, Long userId);

    ResultBO getUserBuyMatches(ClientRequestHeader header, Object body);

    ResultBO getBetSummaryByMatchId(ClientRequestHeader header, Object body);

    ResultBO getSchemeView(ClientRequestHeader header, String s);

    ResultBO getLsmSchemeView(ClientRequestHeader header, String s);

    //根据方案id获取分享的方案详情
    ResultBO getSharedSchemeDetailBySchemaId(ClientRequestHeader header, String body);
    //设置分享的方案的share_status属性
    ResultBO setShareStatusBySchemeId(ClientRequestHeader header, Object body, Long userId);
}
