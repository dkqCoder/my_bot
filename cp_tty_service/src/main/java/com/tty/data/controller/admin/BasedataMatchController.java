package com.tty.data.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.annotations.Action;
import com.jdd.fm.core.annotations.ActionController;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.web.WebActionDispatcher;
import com.tty.common.utils.Result;
import com.tty.data.common.bean.BaseController;
import com.tty.data.dao.entity.BasedataMatchJczqENT;
import com.tty.data.dao.entity.BasedataSalestateJczqENT;
import com.tty.data.service.BasedataMatchJclqService;
import com.tty.data.service.BasedataMatchJczqService;
import com.tty.data.service.BasedataSalestateJczqService;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * index 规则：170* 竞彩足球；171*：竞彩篮球
 **/
@RestController
@CrossOrigin
@ActionController
@RequestMapping("/basedata/private/basedataMatchController")
public class BasedataMatchController extends BaseController {

    @Autowired
    private BasedataMatchJczqService basedataMatchJczqService;

    @Autowired
    private BasedataMatchJclqService basedataMatchJclqService;

    @Autowired
    private BasedataSalestateJczqService basedataSalestateJczqService;

    @RequestMapping(value = "/baseDataMatch")
    public ResultModel router(HttpServletRequest request) {
        ResultModel rm = new ResultModel();
        try {
            rm = (ResultModel) WebActionDispatcher.invoke(request);
        } catch (Exception e) {
            logger.error("user接口异常,stackTrace={}", LogExceptionStackTrace.erroStackTrace(e));
            rm.setCode(Result.ERROR);
            rm.setMsg(Result.MSG_ERROR_DESC);
        }
        return rm;
    }

    /**
     * 获取竞彩足球对阵列表
     */
    @Action("17001")
    public Object listBasedataMatchJczq(@RequestBody JSONObject jsonParm) {
        ExtModel result = new ExtModel();
        basedataMatchJczqService.listBasedataMatchJczqByHostTeamAndVisitTeam(jsonParm, result);
        return result;
    }

    /**
     * 获取单场比赛的详情
     */
    @Action(value = "17002")
    public Object findBasedataMatchJczqENT(@RequestBody JSONObject jsonObject) {

        Integer matchId = jsonObject.getInteger("matchId");
        ExtModel result = new ExtModel();
        if (matchId == null) {
            result.setSuccess(false);
            result.setData("无法获取matchId");
            return result;
        }
        basedataMatchJczqService.findBasedataMatchJczq(matchId, result);
        return result;
    }

    /**
     * 根据比赛编号获取比赛详情
     */
    @Action(value = "17003")
    public Object findBasedataMatchJczqByIssueMatchName(@RequestBody JSONObject jsonObject) {

        String issueMatchName = jsonObject.getString("issueMatchName");
        ExtModel result = new ExtModel();
        if (issueMatchName == null) {
            result.setSuccess(false);
            result.setData("无法获取issueMatchName");
            return result;
        }
        BasedataMatchJczqENT ent = basedataMatchJczqService.getCurrentJczqMatch(null, issueMatchName);
        if (ent == null) {
            result.setSuccess(false);
            result.setData("根据比赛编号无法获取比赛");
            return result;
        }
        result.setData(ent);
        return result;
    }


    /**
     * 根据主客队获取篮球对阵列表
     */
    @Action("17101")
    public Object listBasedataMatchJclq(@RequestBody JSONObject jsonParm) {
        ExtModel result = new ExtModel();
        basedataMatchJclqService.listBasedataMatchJclqByHostTeamAndVisitTeam(jsonParm, result);
        return result;
    }

    /**
     * 根据竞彩篮球比赛ID获取比赛信息
     */
    @Action(value = "17102")
    public Object findBasedataMatchJclqENT(Integer matchId) {
        ExtModel result = new ExtModel();
        basedataMatchJclqService.findBasedataMatchJclq(matchId, result);
        return result;
    }

    /**
     * 根据期次比赛编号获取竞彩足球销售状态
     */
    @Action(value = "17103")
    public Object findBasedataSalestateJczq(@RequestBody JSONObject jsonObject) {
        String issueMatchName = jsonObject.getString("issueMatchName");
        ExtModel result = new ExtModel();
        if (issueMatchName == null) {
            result.setSuccess(false);
            result.setData("无法获取issueMatchName");
            return result;
        }
        BasedataSalestateJczqENT dto = basedataSalestateJczqService.getBasedataSalestateJczqByIssueMatchName(issueMatchName, UUID.randomUUID().toString());
        if (dto == null) {
            result.setSuccess(false);
            result.setData("根据比赛编号无法获取比赛");
            return result;
        }
        result.setData(dto);
        return result;
    }
}
