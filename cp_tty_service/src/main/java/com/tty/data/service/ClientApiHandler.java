package com.tty.data.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ResultModel;

/**
 * app端接口调用业务处理
 */

public interface ClientApiHandler {

    //获取竞彩足球赛事
    ResultModel getJczq(String platformCode, String strAppVersion, String body);

    //获取单关赛事
    ResultModel getDgMatch(ClientRequestHeader header, Object body);

    //获取竞彩篮球赛事
    ResultModel getJclq(String platformCode, String strAppVersion, Object body);

    //获取单关固配
    ResultModel getDggpMatch(Object body);

    //获取单关固配加奖对阵
    ResultModel getDggpAwardMatch(Object body);

    //根据期次ID获取竞彩足球赛果
    ResultModel getJczqMatchResult(Object body);

    //根据期次ID获取竞彩足球赛果
    ResultModel getJclqMatchResult(Object body);


}
