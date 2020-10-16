package com.tty.task.dto;/**
 * Created by shenwei on 2017/2/14.
 */

import java.io.Serializable;
import java.util.Date;

/**
 * @author shenwei
 * @create 2017-02-14
 */
public class JczqDataDTO implements Serializable {
    /** 主键ID */
    private Integer id;

    /** 期次名称  */
    private String issueName;

    /** 期次比赛编号  */
    private String issueMatchName;

    /** 周(1-7)-用于前台呈现  */
    private Integer weekday;

    /** 周名称(周一~周日)-用于前台呈现  */
    private String weekdayName;

    /** 编号  */
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
    private Date matchStartTime;

    /** 截止时间  */
    private Date buyEndTime;

    /** 让球,-1客队让1球,0不让 */
    private Integer rq;

    /** 背景色-用于前台呈现  */
    private String bgColor;

    /** 欧赔  */
    private String euroOdds;

    /** 全场比分  */
    private String fullScore;

    /** 半场比分  */
    private String halfScore;

    /**  胜平负 SP */
    private String spfSp;

    /**  让球胜平负 SP */
    private String rqspfSp;

    /**  进球数 SP */
    private String jqsSp;

    /**  猜比分 SP */
    private String cbfSp;

    /** 半全场 SP */
    private String bqcSp;

    /** 赛果 */
    private String matchResult;

    /** 是否手工修改过 0-没有 1-有 (后台) */
    private Integer editStatus;

    /** 是否审核过 0-未审核过 1-有 (后台) */
    private Integer auditStatus;

    /** 是否过关开奖 0-false 1-true  */
    private Integer passStatus;

    /** 比赛ID  */
    private Integer matchId;

    /** 赛事ID  */
    private Integer tournamentId;

    /** 比赛名称  */
    private String tournamentName;

    /** 新增时间  */
    private Date createTime;

    /** 停售原因 */
    private String stopSaleReason;

    /** n_bqc_status */
    private Integer bqcStatus;

    /** n_cbf_status */
    private Integer cbfStatus;

    /** n_jqs_status */
    private Integer jqsStatus;

    /** n_rqspf_status */
    private Integer rqspfStatus;

    /** n_spf_status */
    private Integer spfStatus;

    /** 状态(1:启用,0:禁用) */
    private Integer status;

    /** n_dg_bqc_status */
    private Integer dgBqcStatus;

    /** n_dg_cbf_status */
    private Integer dgCbfStatus;

    /** n_dg_jqs_status */
    private Integer dgJqsStatus;

    /** n_dg_rqspf_status */
    private Integer dgRqspfStatus;

    /** n_dg_spf_status */
    private Integer dgSpfStatus;

    private String detailState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getIssueMatchName() {
        return issueMatchName;
    }

    public void setIssueMatchName(String issueMatchName) {
        this.issueMatchName = issueMatchName;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public String getWeekdayName() {
        return weekdayName;
    }

    public void setWeekdayName(String weekdayName) {
        this.weekdayName = weekdayName;
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public String getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }

    public Integer getHostTeamId() {
        return hostTeamId;
    }

    public void setHostTeamId(Integer hostTeamId) {
        this.hostTeamId = hostTeamId;
    }

    public String getVisitTeam() {
        return visitTeam;
    }

    public void setVisitTeam(String visitTeam) {
        this.visitTeam = visitTeam;
    }

    public Integer getVisitTeamId() {
        return visitTeamId;
    }

    public void setVisitTeamId(Integer visitTeamId) {
        this.visitTeamId = visitTeamId;
    }

    public Date getMatchStartTime() {
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

    public Integer getRq() {
        return rq;
    }

    public void setRq(Integer rq) {
        this.rq = rq;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getEuroOdds() {
        return euroOdds;
    }

    public void setEuroOdds(String euroOdds) {
        this.euroOdds = euroOdds;
    }

    public String getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    public String getHalfScore() {
        return halfScore;
    }

    public void setHalfScore(String halfScore) {
        this.halfScore = halfScore;
    }

    public String getSpfSp() {
        return spfSp;
    }

    public void setSpfSp(String spfSp) {
        this.spfSp = spfSp;
    }

    public String getRqspfSp() {
        return rqspfSp;
    }

    public void setRqspfSp(String rqspfSp) {
        this.rqspfSp = rqspfSp;
    }

    public String getJqsSp() {
        return jqsSp;
    }

    public void setJqsSp(String jqsSp) {
        this.jqsSp = jqsSp;
    }

    public String getCbfSp() {
        return cbfSp;
    }

    public void setCbfSp(String cbfSp) {
        this.cbfSp = cbfSp;
    }

    public String getBqcSp() {
        return bqcSp;
    }

    public void setBqcSp(String bqcSp) {
        this.bqcSp = bqcSp;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(Integer passStatus) {
        this.passStatus = passStatus;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStopSaleReason() {
        return stopSaleReason;
    }

    public void setStopSaleReason(String stopSaleReason) {
        this.stopSaleReason = stopSaleReason;
    }

    public Integer getBqcStatus() {
        return bqcStatus;
    }

    public void setBqcStatus(Integer bqcStatus) {
        this.bqcStatus = bqcStatus;
    }

    public Integer getCbfStatus() {
        return cbfStatus;
    }

    public void setCbfStatus(Integer cbfStatus) {
        this.cbfStatus = cbfStatus;
    }

    public Integer getJqsStatus() {
        return jqsStatus;
    }

    public void setJqsStatus(Integer jqsStatus) {
        this.jqsStatus = jqsStatus;
    }

    public Integer getRqspfStatus() {
        return rqspfStatus;
    }

    public void setRqspfStatus(Integer rqspfStatus) {
        this.rqspfStatus = rqspfStatus;
    }

    public Integer getSpfStatus() {
        return spfStatus;
    }

    public void setSpfStatus(Integer spfStatus) {
        this.spfStatus = spfStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDgBqcStatus() {
        return dgBqcStatus;
    }

    public void setDgBqcStatus(Integer dgBqcStatus) {
        this.dgBqcStatus = dgBqcStatus;
    }

    public Integer getDgCbfStatus() {
        return dgCbfStatus;
    }

    public void setDgCbfStatus(Integer dgCbfStatus) {
        this.dgCbfStatus = dgCbfStatus;
    }

    public Integer getDgJqsStatus() {
        return dgJqsStatus;
    }

    public void setDgJqsStatus(Integer dgJqsStatus) {
        this.dgJqsStatus = dgJqsStatus;
    }

    public Integer getDgRqspfStatus() {
        return dgRqspfStatus;
    }

    public void setDgRqspfStatus(Integer dgRqspfStatus) {
        this.dgRqspfStatus = dgRqspfStatus;
    }

    public Integer getDgSpfStatus() {
        return dgSpfStatus;
    }

    public void setDgSpfStatus(Integer dgSpfStatus) {
        this.dgSpfStatus = dgSpfStatus;
    }

    public String getDetailState() {
        return detailState;
    }

    public void setDetailState(String detailState) {
        this.detailState = detailState;
    }
}
