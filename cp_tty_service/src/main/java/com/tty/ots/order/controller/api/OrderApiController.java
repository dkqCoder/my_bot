package com.tty.ots.order.controller.api;

import com.jdd.fm.core.annotations.Action;
import com.jdd.fm.core.annotations.ActionController;
import com.jdd.fm.core.ehcache.EhcacheManager;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.token.UserVO;
import com.jdd.fm.core.utils.web.WebActionDispatcher;
import com.tty.common.utils.ApiHelper;
import com.tty.common.utils.Result;
import com.tty.common.utils.ResultBO;
import com.tty.ots.order.service.ApiSchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@ActionController
@RequestMapping(value = "/order/public", method = RequestMethod.POST)
public class OrderApiController {
    private Logger logger = LoggerFactory.getLogger(OrderApiController.class);

    @Autowired
    private EhcacheManager ehcacheManager;

    @Autowired
    private ApiSchemeService apiSchemeService;


    @RequestMapping(value = "/securityMobileHandler")
    public ResultModel mobileHandler(HttpServletRequest request) {
        ResultModel rm = new ResultModel();
        try {
            rm = (ResultModel) WebActionDispatcher.invoke(request, ehcacheManager);
        } catch (Exception e) {
            logger.error("方案接口异常,stackTrace={}", LogExceptionStackTrace.erroStackTrace(e));
            rm.setCode(Result.ERROR);
            rm.setMsg(Result.MSG_ERROR_DESC);
        }
        return rm;
    }

    /**
     * author:yys
     * create:2017-12-21
     * desc:下单接口
     *
     * @param header 请求头（公共参数）
     * @param body   请求体
     * @param userVO 用户信息
     * @return 下单处理结果
     */
    @Action(value = "207", tokenValidated = true)
    public ResultModel order(ClientRequestHeader header, Object body, UserVO userVO) {
        ResultBO result = apiSchemeService.order(header, body, userVO.getUserid());
        return ApiHelper.mapToResultModel(result);
    }

    /**
     * @Author shenwei
     * @Date 2017/5/11 19:08
     * @Description 2102 方案详情
     */
    @Action(value = "2102")
    public ResultModel getSchemeView(ClientRequestHeader header, Object body) {
        ResultBO result = apiSchemeService.getSchemeView(header, String.valueOf(body));
        return ApiHelper.mapToResultModel(result);
    }

    /**
     * @Author ruixu
     * @Date 2017/5/11 19:08
     * @Description 2102 方案详情
     */
    @Action(value = "2203")
    public ResultModel getLsmSchemeView(ClientRequestHeader header, Object body) {
        ResultBO result = apiSchemeService.getLsmSchemeView(header, String.valueOf(body));
        return ApiHelper.mapToResultModel(result);
    }

    /**
     * @Author shenwei
     * @Date 2017/5/11 19:08
     * @Description 2101 详情明细
     */
    @Action(value = "2101", tokenValidated = true)
    public ResultModel getSchemeOptimize(ClientRequestHeader header, Object body, UserVO userVO) {
        ResultBO result = apiSchemeService.getSchemeOptimize(header, String.valueOf(body), userVO.getUserid());
        return ApiHelper.mapToResultModel(result);
    }

    /**
     * @Author fuhuaidong
     * @Date 2017/7/11 19:08
     * @Description 2103 获取出票详情
     */
    @Action(value = "2103", tokenValidated = true)
    public ResultModel getSchemeTicket(ClientRequestHeader header, Object body, UserVO userVO) {
        ResultBO result = apiSchemeService.getSchemeTicket(userVO.getUserid().toString(), String.valueOf(body), header.getTraceID());
        return ApiHelper.mapToResultModel(result);
    }


    /**
     * @Author yys
     * @Description 111，获取购彩记录（不含未付款订单）
     */
    @Action(value = "111", tokenValidated = true)
    public ResultModel getSchemes(ClientRequestHeader header, Object body, UserVO userVO) {
        ResultBO result = apiSchemeService.getSchemes(header, body, false, userVO.getUserid());
        return ApiHelper.mapToResultModel(result);
    }

    /**
     * @Author yys
     * @Description 122，获取购彩记录（不含未付款订单）
     */
    @Action(value = "122", tokenValidated = true)
    public ResultModel getLsmSchemes(ClientRequestHeader header, Object body, UserVO userVO) {
        ResultBO result = apiSchemeService.getLsmSchemes(header, body, userVO.getUserid());
        return ApiHelper.mapToResultModel(result);
    }

    /**
     * 获取购彩记录（含未付款订单）
     *
     * @Author wzn
     * @Date 2017/8/25
     * @Description
     */
    @Action(value = "1121", tokenValidated = true)
    public ResultModel getSchemesNew(ClientRequestHeader header, Object body, UserVO userVO) {
        ResultBO result = apiSchemeService.getSchemes(header, body, false, userVO.getUserid());
        return ApiHelper.mapToResultModel(result);
    }
}
