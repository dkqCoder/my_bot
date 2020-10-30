package com.tty.data.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * pdl
 * 竞彩篮球赛果 接口action=311
 */
public class ClientJclqMatchResultVo implements java.io.Serializable {
    /**
     * 周(1-7)-用于前台呈现
     */
    private Integer weekday;

    /**
     * 比赛编号
     */
    private String matchNo;

    /**
     * 销售截止时间
     */
    private Date buyEndTime;
    /**
     * 比赛名称
     */
    private String tournamentName;

    /**
     * 主队名称
     */
    private String hostTeam;

    /**
     * 客队名称
     */
    private String visitTeam;

    /**
     * 全场比分
     */
    private String fullScore;

    private String hrz = "";

    private String cg;

    @JSONField(name = "NMm")
    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    @JSONField(name = "Wk")
    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    @JSONField(name = "MNo")
    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    @JSONField(name = "ETime", format = "yyyy-MM-dd HH:mm:ss")
    public Date getBuyEndTime() {
        return buyEndTime;
    }

    public void setBuyEndTime(Date buyEndTime) {
        this.buyEndTime = buyEndTime;
    }

    @JSONField(name = "HTeam")
    public String getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }

    @JSONField(name = "VTeam")
    public String getVisitTeam() {
        return visitTeam;
    }

    public void setVisitTeam(String visitTeam) {
        this.visitTeam = visitTeam;
    }

    @JSONField(name = "Score")
    public String getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    @JSONField(name = "Hrz")
    public String getHrz() {
        return hrz;
    }

    public void setHrz(String hrz) {
        this.hrz = hrz;
    }

    //"{ RZ:\"\", SP:\"\",DG:\"\" }",
    //RZ 赛果：主负|让分，主胜，主胜6-10，大分|预设分155.5 SP 主负sp,,主胜6-10sp，大分sp DG 单关对应的sp：
    @JSONField(name = "Cg")
    public String getCg() {
        return cg;
    }

    public void setCg(String cg) {
        this.cg = cg;
    }

}
