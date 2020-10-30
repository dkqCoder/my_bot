package com.tty.data.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ResultModel;

/**
 * app端接口调用业务处理
 */

public interface ClientApiHandler {

    //    ResultModel getBuyHallData(Object body, String traceID);

    ResultModel getJczq(String platformCode, String strAppVersion, String body);

    ResultModel getDgMatch(ClientRequestHeader header, Object body);

    ResultModel getJclq(String platformCode, String strAppVersion, Object body);

    //奖金优化
    ResultModel bonusOptimize(String traceId, String platformCode, Object body);

    //单关固配
    ResultModel getDggpMatch(Object body);

    //单关固配加奖对阵
    ResultModel getDggpAwardMatch(Object body);

    //根据期次ID获取竞彩足球赛果
    ResultModel getJczqMatchResult(Object body);

    //根据期次ID获取竞彩足球赛果
    ResultModel getJclqMatchResult(Object body);

    //    ResultModel getJczqBet(ClientRequestHeader header, Object body);

}
