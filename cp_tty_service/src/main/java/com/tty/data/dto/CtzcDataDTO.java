package com.tty.data.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;


public class CtzcDataDTO implements Serializable {

    /** 主键ID */
    private Integer id;

    /** 彩种ID  */
    private Integer lotteryId;

    /** s_issue_match_name */
    private String issueMatchName;

    /** 期次名称  */
    private String issueName;

    /** 场次  */
    private String matchNo;

    /** 主队名称  */
    private String hostTeam;

    /** 主队ID  */
    private Integer hostTeamId;

    /** 客队名称  */
    private String visitTeam;

    /** 客队ID  */
    private Integer visitTeamId;

    /** 比赛时间  */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date matchStartTime;

    /** 背景色-用于前台呈现  */
    private String bgColor;

    /** 欧赔  */
    private String euroOdds;

    /** 全场比分  */
    private String fullScore;

    /** 赛果  */
    private String matchResult;

    /** 是否手工修改过 0-没有 1-有 (后台)  */
    private Integer editStatus;

    /** 是否审核过 0-未审核过 1-有 (后台)  */
    private Integer auditStatus;


    /** 比赛ID  */
    private Integer matchId;

    /** 赛事ID  */
    private Integer tournamentId;

    /** 联赛名称(联赛杯赛)  */
    private String tournamentName;

    /** 新增时间  */
    private Date createTime;




    public Integer  getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer  getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getIssueMatchName() {
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

    public Date  getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(Date matchStartTime) {
        this.matchStartTime = matchStartTime;
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

    public String  getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
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

    public Integer  getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
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



    @Override
    public String toString() {
        return "CtzcDataDTO{"+"id="+id+",lotteryId="+lotteryId+",issueName="+issueName+",matchNo="+matchNo+",hostTeam="+hostTeam+",hostTeamId="+hostTeamId+",visitTeam="+visitTeam+",visitTeamId="+visitTeamId+",matchStartTime="+matchStartTime+",bgColor="+bgColor+",euroOdds="+euroOdds+",fullScore="+fullScore+",matchResult="+matchResult+",editStatus="+editStatus+",auditStatus="+auditStatus+",matchId="+matchId+",tournamentId="+tournamentId+",tournamentName="+tournamentName+",createTime="+createTime+"}";
    }
}