package com.tty.data.dto;/**
 * Created by shenwei on 2017/2/14.
 */

import java.io.Serializable;
import java.util.Date;

/**
 * @author shenwei
 * @create 2017-02-14
 */
public class JclqDataDTO implements Serializable {
    /** 主键ID */
    private Integer id;

    /** 期次名称  */
    private String issueName;

    /** 期次比赛编号  */
    private String issueMatchName;

    /** 周名称(周一~周日)-用于前台呈现  */
    private String weekdayName;

    /** 周(1-7)-用于前台呈现  */
    private Integer weekday;

    /** 比赛编号  */
    private String matchNo;

    /** 主队名称  */
    private String hostTeam;

    /** 主队ID  */
    private Integer hostTeamId;

    /** 客队名称  */
    private String visitTeam;

    /** 客队ID  */
    private Integer visitTeamId;

    /** 截止时间  */
    private Date buyEndTime;

    /** 比赛时间  */
    private Date matchStartTime;

    /** 背景色-用于前台呈现  */
    private String bgColor;

    /** 欧赔  */
    private String euroOdds;

    /** 全场比分  */
    private String fullScore;

    /** 胜负  */
    private String sfSp;

    /** 让分胜负  */
    private String rfsfSp;

    /** 胜分差  */
    private String sfcSp;

    /** 大小分  */
    private String dxfSp;

    /** 赛果 */
    private String matchResult;

    /** 是否手工修改过 0-没有 1-有 (后台)  */
    private Integer editStatus;

    /** 是否审核过 0-未审核过 1-有 (后台)  */
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

    /** n_dxf_status */
    private Integer dxfStatus;

    /** n_sfc_status */
    private Integer sfcStatus;

    /** n_rfsf_status */
    private Integer rfsfStatus;

    /** n_sf_status */
    private Integer sfStatus;

    /** 状态(1:启用,0:禁用) */
    private Integer status;

    /** n_dg_dxf_status */
    private Integer dgDxfStatus;

    /** n_dg_sfc_status */
    private Integer dgSfcStatus;

    /** n_dg_rfsf_status */
    private Integer dgRfsfStatus;

    /** n_dg_sf_status */
    private Integer dgSfStatus;

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

    public String getWeekdayName() {
        return weekdayName;
    }

    public void setWeekdayName(String weekdayName) {
        this.weekdayName = weekdayName;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
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

    public Date getBuyEndTime() {
        return buyEndTime;
    }

    public void setBuyEndTime(Date buyEndTime) {
        this.buyEndTime = buyEndTime;
    }

    public Date getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(Date matchStartTime) {
        this.matchStartTime = matchStartTime;
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

    public String getSfSp() {
        return sfSp;
    }

    public void setSfSp(String sfSp) {
        this.sfSp = sfSp;
    }

    public String getRfsfSp() {
        return rfsfSp;
    }

    public void setRfsfSp(String rfsfSp) {
        this.rfsfSp = rfsfSp;
    }

    public String getSfcSp() {
        return sfcSp;
    }

    public void setSfcSp(String sfcSp) {
        this.sfcSp = sfcSp;
    }

    public String getDxfSp() {
        return dxfSp;
    }

    public void setDxfSp(String dxfSp) {
        this.dxfSp = dxfSp;
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

    public Integer getDxfStatus() {
        return dxfStatus;
    }

    public void setDxfStatus(Integer dxfStatus) {
        this.dxfStatus = dxfStatus;
    }

    public Integer getSfcStatus() {
        return sfcStatus;
    }

    public void setSfcStatus(Integer sfcStatus) {
        this.sfcStatus = sfcStatus;
    }

    public Integer getRfsfStatus() {
        return rfsfStatus;
    }

    public void setRfsfStatus(Integer rfsfStatus) {
        this.rfsfStatus = rfsfStatus;
    }

    public Integer getSfStatus() {
        return sfStatus;
    }

    public void setSfStatus(Integer sfStatus) {
        this.sfStatus = sfStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDgDxfStatus() {
        return dgDxfStatus;
    }

    public void setDgDxfStatus(Integer dgDxfStatus) {
        this.dgDxfStatus = dgDxfStatus;
    }

    public Integer getDgSfcStatus() {
        return dgSfcStatus;
    }

    public void setDgSfcStatus(Integer dgSfcStatus) {
        this.dgSfcStatus = dgSfcStatus;
    }

    public Integer getDgRfsfStatus() {
        return dgRfsfStatus;
    }

    public void setDgRfsfStatus(Integer dgRfsfStatus) {
        this.dgRfsfStatus = dgRfsfStatus;
    }

    public Integer getDgSfStatus() {
        return dgSfStatus;
    }

    public void setDgSfStatus(Integer dgSfStatus) {
        this.dgSfStatus = dgSfStatus;
    }

    public String getDetailState() {
        return detailState;
    }

    public void setDetailState(String detailState) {
        this.detailState = detailState;
    }
}
