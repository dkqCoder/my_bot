package com.tty.data.dto;

import java.io.Serializable;
import java.util.Date;


public class BasedataMatchBjdcDTO implements Serializable{

    /** 主键ID */
    private Integer id;

    /** s_issue_match_name */
    private String issueMatchName;

    /** 期次名称 */
    private String issueName;

    /** 比赛编号 */
    private String matchNo;

    /** 周(1-7)-用于前台呈现 */
    private Integer weekday;

    /** 比赛ID */
    private Integer matchId;

    /** 主队名称 */
    private String hostTeam;

    /** 主队ID */
    private Integer hostTeamId;

    /** 客队名称 */
    private String visitTeam;

    /** 客队ID */
    private Integer visitTeamId;

    private String teamVs;

    /** 让球,-1客队让1球,0不让,+0.5主队让半球 */
    private Integer rq;

    /** 背景色-用于前台呈现 */
    private String bgColor;

    /** 欧赔 */
    private String euroOdds;

    /** 半场比分 */
    private String halfScore;

    /** 全场比分 */
    private String fullScore;

    /** 让球胜平负 */
    private String spfSp;

    /** 进球数 */
    private String jqsSp;

    /** 猜比分 */
    private String cbfSp;

    /** 上下盘 */
    private String sxpSp;

    /** 半全场 */
    private String bqcSp;

    /** 最终SP */
    private String finalSp;

    /** 赛果 */
    private String matchResult;

    /** 是否手工修改过 0-没有 1-有  （如果手工修改过记录，将不再接受数据组推送数据） */
    private Integer editStatus;

    /** 是否审核过 0-未审核过 1-有 (后台) */
    private Integer auditStatus;



    /** 联赛名称(联赛杯赛)ID */
    private Integer tournamentId;

    /** 联赛名称(联赛杯赛) */
    private String tournamentName;

    /** 创建时间 */
    private Date createTime;

    /** 比赛时间 */
    private Date matchStartTime;

    /** 销售结束时间 */
    private Date buyEndTime;

    private Integer status;


    public Integer  getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String  getIssueMatchName() {
        return issueMatchName;
    }

    public void setIssueMatchName(String issueMatchName) {
        this.issueMatchName = issueMatchName;
    }

    public String  getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String  getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public Integer  getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Integer  getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String  getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }

    public Integer  getHostTeamId() {
        return hostTeamId;
    }

    public void setHostTeamId(Integer hostTeamId) {
        this.hostTeamId = hostTeamId;
    }

    public String  getVisitTeam() {
        return visitTeam;
    }

    public void setVisitTeam(String visitTeam) {
        this.visitTeam = visitTeam;
    }

    public Integer  getVisitTeamId() {
        return visitTeamId;
    }

    public void setVisitTeamId(Integer visitTeamId) {
        this.visitTeamId = visitTeamId;
    }

    public String getTeamVs() {
        return teamVs;
    }

    public void setTeamVs(String teamVs) {
        this.teamVs = teamVs;
    }

    public Integer  getRq() {
        return rq;
    }

    public void setRq(Integer rq) {
        this.rq = rq;
    }

    public String  getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String  getEuroOdds() {
        return euroOdds;
    }

    public void setEuroOdds(String euroOdds) {
        this.euroOdds = euroOdds;
    }

    public String  getHalfScore() {
        return halfScore;
    }

    public void setHalfScore(String halfScore) {
        this.halfScore = halfScore;
    }

    public String  getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    public String  getSpfSp() {
        return spfSp;
    }

    public void setSpfSp(String spfSp) {
        this.spfSp = spfSp;
    }

    public String  getJqsSp() {
        return jqsSp;
    }

    public void setJqsSp(String jqsSp) {
        this.jqsSp = jqsSp;
    }

    public String  getCbfSp() {
        return cbfSp;
    }

    public void setCbfSp(String cbfSp) {
        this.cbfSp = cbfSp;
    }

    public String  getSxpSp() {
        return sxpSp;
    }

    public void setSxpSp(String sxpSp) {
        this.sxpSp = sxpSp;
    }

    public String  getBqcSp() {
        return bqcSp;
    }

    public void setBqcSp(String bqcSp) {
        this.bqcSp = bqcSp;
    }

    public String  getFinalSp() {
        return finalSp;
    }

    public void setFinalSp(String finalSp) {
        this.finalSp = finalSp;
    }

    public String  getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public Integer  getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public Integer  getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer  getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String  getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Date  getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date  getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(Date matchStartTime) {
        this.matchStartTime = matchStartTime;
    }

    public Date getBuyEndTime() {
        return buyEndTime;
    }

    public void setBuyEndTime(Date buyEndTime) {
        this.buyEndTime = buyEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BasedataMatchBjdcDTO{"+"id="+id+",issueMatchName="+issueMatchName+",issueName="+issueName+",matchNo="+matchNo+",weekday="+weekday+",matchId="+matchId+",hostTeam="+hostTeam+",hostTeamId="+hostTeamId+",visitTeam="+visitTeam+",visitTeamId="+visitTeamId+",rq="+rq+",bgColor="+bgColor+",euroOdds="+euroOdds+",halfScore="+halfScore+",fullScore="+fullScore+",spfSp="+spfSp+",jqsSp="+jqsSp+",cbfSp="+cbfSp+",sxpSp="+sxpSp+",bqcSp="+bqcSp+",finalSp="+finalSp+",matchResult="+matchResult+",editStatus="+editStatus+",auditStatus="+auditStatus+",tournamentId="+tournamentId+",tournamentName="+tournamentName+",createTime="+createTime+",matchStartTime="+matchStartTime+",buyEndTime="+buyEndTime+"}";
    }
}