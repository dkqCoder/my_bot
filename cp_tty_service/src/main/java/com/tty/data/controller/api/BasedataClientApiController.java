package com.tty.data.controller.api;

import static com.tty.data.common.util.CommonsUtils.getTraceId;

import com.jdd.fm.core.annotations.Action;
import com.jdd.fm.core.annotations.ActionController;
import com.jdd.fm.core.base.APIBaseController;
import com.jdd.fm.core.ehcache.EhcacheManager;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.web.WebActionDispatcher;
import com.tty.common.utils.Result;
import com.tty.data.service.ClientApiHandler;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app端接口调用业务处理
 */
@CrossOrigin
@RestController
@ActionController
@RequestMapping("basedata/public")
public class BasedataClientApiController extends APIBaseController {
    Logger logger = LoggerFactory.getLogger(BasedataClientApiController.class);

    @Autowired
    private EhcacheManager ehcacheManager;

    @Autowired
    private ClientApiHandler clientApiHandler;

    @RequestMapping(value = "/securityMobileHandler")
    public ResultModel router(HttpServletRequest request) {
        ResultModel rm = new ResultModel();
        try {
            rm = (ResultModel) WebActionDispatcher.invoke(request, ehcacheManager);
        } catch (Exception e) {
            logger.error("user接口异常,stackTrace={}", LogExceptionStackTrace.erroStackTrace(e));
            rm.setCode(Result.ERROR);
            rm.setMsg(Result.MSG_ERROR_DESC);
        }
        return rm;
    }

    /**
     * 竞彩足球赔率信息
     */
    @Action(value = "1701")
    public ResultModel getJczq(HttpServletRequest request, Object body) {
        ResultModel result = new ResultModel();
        try {
            ResultModel model = clientApiHandler.getJczq(request.getParameter("platformCode"), request.getParameter("appVersion"), String.valueOf(body));
            result.setCode(model.getCode());
            result.setData(GfJsonUtil.toJSONString(model.getData()));
            result.setMsg(model.getMsg());
        } catch (Exception e) {
            logger.error("获取竞彩足球数据异常：{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * 获取单关竞彩足球数据
     */
    @RequestMapping(params = "action=1702")
    public ResultModel getDgJczq(HttpServletRequest request, String params) {
        ResultModel result = new ResultModel();
        try {
            ResultModel model = clientApiHandler.getDgMatch(null, params);
            result.setCode(model.getCode());
            result.setData(model.getData());
            result.setMsg(model.getMsg());
        } catch (Exception e) {
            logger.error("获取单关竞彩足球数据：{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * 获取篮球对阵
     */
    @RequestMapping(params = "action=1703")
    public ResultModel getJclqMatchs(HttpServletRequest request, String params) {
        ResultModel result = new ResultModel();
        try {
            ResultModel model = clientApiHandler.getJclq(request.getParameter("platformCode"), request.getParameter("appVersion"), params);
            result.setCode(model.getCode());
            result.setData(model.getData());
            result.setMsg(model.getMsg());
        } catch (Exception e) {
            logger.error("获取篮球对阵:{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * 获取奖金优化
     */
    @RequestMapping(params = "action=1704")
    public Result getOptimizePrize(HttpServletRequest request, String params) {
        Result result = new Result();
        try {
            ResultModel model = clientApiHandler.bonusOptimize(getTraceId(request), request.getParameter("platformCode"), params);
            result.setCode(model.getCode());
            result.setData(GfJsonUtil.toJSONString(model.getData()));
            result.setMsg(model.getMsg());
        } catch (Exception e) {
            logger.error("获取奖金优化:{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * 获取单关固陪
     */
    @RequestMapping(params = "action=1705")
    public Result getDgGp(HttpServletRequest request, String params) {
        Result result = new Result();
        try {
            ResultModel model = clientApiHandler.getDggpMatch(params);
            result.setCode(model.getCode());
            result.setData(model.getData());
            result.setMsg(model.getMsg());
        } catch (Exception e) {
            logger.error("获取单关固陪:{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * 获取单关固陪加奖对阵
     */
    @RequestMapping(params = "action=1706")
    public Result getDgGpBonus(HttpServletRequest request, String params) {
        Result result = new Result();
        try {
            ResultModel model = clientApiHandler.getDggpAwardMatch(params);
            result.setCode(model.getCode());
            result.setData(model.getData());
            result.setMsg(model.getMsg());
        } catch (Exception e) {
            logger.error("获取单关固陪加奖对阵:{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * 根据期次ID获取竞彩足球赛果
     */
    @RequestMapping(params = "action=1707")
    public Result getJczqMatchResult(HttpServletRequest request, String params) {
        Result result = new Result();
        try {
            ResultModel model = clientApiHandler.getJczqMatchResult(params);
            result.setCode(model.getCode());
            result.setData(GfJsonUtil.toJSONString(model.getData()));
            result.setMsg(model.getMsg());
        } catch (Exception e) {
            logger.error("根据期次ID获取竞彩足球赛果:{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * 根据期次ID获取竞彩篮球赛果
     */
    @RequestMapping(params = "action=1708")
    public Result getJclqMatchResult(HttpServletRequest request, String params) {
        Result result = new Result();
        try {
            ResultModel model = clientApiHandler.getJclqMatchResult(params);
            result.setCode(model.getCode());
            result.setData(GfJsonUtil.toJSONString(model.getData()));
            result.setMsg(model.getMsg());
        } catch (Exception e) {
            logger.error("根据期次ID获取竞彩篮球赛果:{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }


}
