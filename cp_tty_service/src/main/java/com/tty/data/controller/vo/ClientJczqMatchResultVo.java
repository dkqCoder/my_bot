package com.tty.data.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * pdl
 * 竞彩足球赛果 接口action=310
 */
public class ClientJczqMatchResultVo implements java.io.Serializable {
    /**
     * 周(1-7)-用于前台呈现
     */
    private Integer weekday;
    /**
     * 编号
     */
    private String matchNo;
    /**
     * 比赛名称
     */
    private String tournamentName;
    /**
     * 销售截止时间
     */
    private Date buyEndTime;
    /**
     * 主队名称
     */
    private String hostTeam;
    /**
     * 客队名称
     */
    private String visitTeam;
    /**
     * 让球,-1客队让1球,0不让
     */
    private Integer rq;
    /**
     * 全场比分
     */
    private String fullScore;
    /**
     * 半场比分
     */
    private String halfScore;
    /**
     * "{RZ:\"0,1,01,10,0\",SP:\"2.00,6,15,9,3.80\",DG:\"3.3,0.0,15.08,11.4,8.26\"}"
     * RZ 赛果：让球胜平负，总进球，比分，半全场，胜平负；
     * SP让球胜平负，总进球，比分，半全场，胜平负 前台没用 默认空字符串；
     * DG 单关对应的sp：让球胜平负，比分，总进球，半全场，胜平负 前台没用 默认空字符串；
     */
    private String cg;

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

    @JSONField(name = "NMm")
    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
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

    @JSONField(name = "Rq")
    public Integer getRq() {
        return rq;
    }

    public void setRq(Integer rq) {
        this.rq = rq;
    }

    @JSONField(name = "Score")
    public String getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    @JSONField(name = "Hrz")
    public String getHalfScore() {
        return halfScore;
    }

    public void setHalfScore(String halfScore) {
        this.halfScore = halfScore;
    }

    @JSONField(name = "Cg")
    public String getCg() {
        return cg;
    }

    public void setCg(String cg) {
        this.cg = cg;
    }

}
