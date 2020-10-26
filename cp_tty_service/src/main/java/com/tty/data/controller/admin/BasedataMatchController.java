package com.tty.data.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.tty.data.common.bean.BaseController;
import com.tty.data.service.BasedataMatchJczqService;
import com.tty.user.common.ExtModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxh
 * @create 2017-02-16 11:14:00
 **/
@RestController
@RequestMapping("/basedata/private/basedataMatchController")
public class BasedataMatchController extends BaseController {

    @Autowired
    private BasedataMatchJczqService basedataMatchJczqService;
//    @Autowired
//    private BasedataMatchJclqService basedataMatchJclqService;
//    @Autowired
//    private BasedataSalestateJczqService basedataSalestateJczqService;

    /**
     * 获取竞彩足球比赛表
     */
    @RequestMapping("listBasedataMatchJczqByHostTeamAndVisitTeam")
    public Object listBasedataMatchJczq(@RequestBody JSONObject jsonParm) {
        ExtModel result = new ExtModel();
        basedataMatchJczqService.listBasedataMatchJczqByHostTeamAndVisitTeam(jsonParm, result);
        return result;
    }

    /**
     * ext 接口
     *//*
    @RequestMapping(value = "findBasedataMatchJczq")
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
    *//**
     * ext 接口
     *//*
    @RequestMapping(value = "findBasedataMatchJczqByIssueMatchName")
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


    *//**
     * ext 接口 列表
     *//*
    @RequestMapping("listBasedataMatchJclqByHostTeamAndVisitTeam")
    public Object listBasedataMatchJclq(@RequestBody JSONObject jsonParm) {
        ExtModel result = new ExtModel();
        basedataMatchJclqService.listBasedataMatchJclqByHostTeamAndVisitTeam(jsonParm, result);
        return result;
    }

    *//**
     * ext 接口
     *//*
    @RequestMapping(value = "findBasedataMatchJclq")
    public Object findBasedataMatchJclqENT(Integer matchId) {
        ExtModel result = new ExtModel();
        basedataMatchJclqService.findBasedataMatchJclq(matchId, result);
        return result;
    }

    *//**
     * ext 接口
     *//*
    @RequestMapping(value = "findBasedataSalestateJczq")
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
    }*/
}
