package com.tty.appadmin.controller.api;

import com.jdd.fm.core.annotations.Action;
import com.jdd.fm.core.annotations.ActionController;
import com.jdd.fm.core.base.APIBaseController;
import com.jdd.fm.core.ehcache.EhcacheManager;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.web.WebActionDispatcher;
import com.tty.appadmin.context.Constants;
import com.tty.appadmin.controller.model.result.IndexPage;
import com.tty.appadmin.controller.model.result.StartPage;
import com.tty.appadmin.controller.service.IndexNavService;
import com.tty.common.utils.BeanUtils;
import com.tty.common.utils.Result;
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
@RequestMapping(value = "/app/public", method = RequestMethod.POST)
public class AppAdminSecurityController extends APIBaseController {
    Logger logger = LoggerFactory.getLogger(AppAdminSecurityController.class);

    @Autowired
    private EhcacheManager ehcacheManager;
    @Autowired
    private IndexNavService indexNavService;

    @RequestMapping(value = "/router")
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
     * 打开APP，获取启动信息
     */
    @Action(value = "10001")
    public ResultModel getStartPage(ClientRequestHeader header, Object body) {
        ResultModel resultModel = new ResultModel();
        StartPage startPage = new StartPage();
        startPage.setPicUrl("https://alipic.lanhuapp.com/ps6169a4ba57738fa4-f224-4796-a98d-822df0c312e7");
        startPage.setActionBusiness(Constants.ActionBusiness.WEB_VIEW);
        startPage.setActionParam("{\"url\":\"http://wwww.baidu.com\"}");
        resultModel.setData(startPage);
        return resultModel;
    }

    /**
     * 获取首页聚合信息
     */
    @Action(value = "10002")
    public ResultModel getIndexPage(ClientRequestHeader header, Object body) {
        ResultModel resultModel = new ResultModel();
        IndexPage indexPage = indexNavService.getIndexPage();
        resultModel.setData(indexPage);
        return resultModel;
    }

    /**
     * 店铺列表
     */
    @Action(value = "10003")
    public ResultModel getShopList(ClientRequestHeader header, Object body) {
        ResultModel resultModel = new ResultModel();
        return resultModel;
    }

    /**
     * 店铺切换
     */
    @Action(value = "10004")
    public ResultModel changeShop(ClientRequestHeader header, Object body) {
        ResultModel resultModel = new ResultModel();
        return resultModel;
    }

    /**
     * 检测更新
     */
    @Action(value = "10005")
    public ResultModel checkAppVersion(ClientRequestHeader header, Object body) {
        ResultModel resultModel = new ResultModel();
        return resultModel;
    }

    /**
     * 意见反馈
     */
    @Action(value = "10006")
    public ResultModel useFeedback(ClientRequestHeader header, Object body) {
        ResultModel resultModel = new ResultModel();
        return resultModel;
    }


    private ResultModel resultToResultModel(Result result) {
        ResultModel rm = new ResultModel();
        BeanUtils.copy(result, rm);
        return rm;
    }

    private ResultModel getFailResult(String msg) {
        ResultModel result = new ResultModel();
        result.setCode(Result.ERROR);
        result.setMsg(msg);
        return result;
    }

    private ResultModel getFailResult() {
        ResultModel result = new ResultModel();
        result.setCode(Result.ERROR);
        result.setMsg(Result.MSG_ERROR_DESC);
        return result;
    }

}
