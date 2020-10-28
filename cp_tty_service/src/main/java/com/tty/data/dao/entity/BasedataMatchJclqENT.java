package com.tty.data.dao.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zxh
 * @create 2017-02-16 09:59:51
 **/

@Entity
@Table(name = "basedata_match_jclq")
public class BasedataMatchJclqENT implements java.io.Serializable {

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


    /** 比赛ID  */
    private Integer matchId;

    /** 赛事ID  */
    private Integer tournamentId;

    /** 比赛名称  */
    private String tournamentName;

    /** 新增时间  */
    private Date createTime;




    /**
     * 获取 主键ID 的值
     * @return Integer 
     */
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    public Integer  getId() {
        return id;
    }

    /**
     * 设置主键ID 的值
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取 期次名称  的值
     * @return String 
     */
    @Column(name = "s_issue_name")
    public String  getIssueName() {
        return issueName;
    }

    /**
     * 设置期次名称  的值
     * @param issueName 期次名称 
     */
    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    /**
     * 获取 期次比赛编号  的值
     * @return String 
     */
    @Column(name = "s_issue_match_name")
    public String  getIssueMatchName() {
        return issueMatchName;
    }

    /**
     * 设置期次比赛编号  的值
     * @param issueMatchName 期次比赛编号 
     */
    public void setIssueMatchName(String issueMatchName) {
        this.issueMatchName = issueMatchName;
    }

    /**
     * 获取 周名称(周一~周日)-用于前台呈现  的值
     * @return String 
     */
    @Column(name = "s_weekday_name")
    public String  getWeekdayName() {
        return weekdayName;
    }

    /**
     * 设置周名称(周一~周日)-用于前台呈现  的值
     * @param weekdayName 周名称(周一~周日)-用于前台呈现 
     */
    public void setWeekdayName(String weekdayName) {
        this.weekdayName = weekdayName;
    }

    /**
     * 获取 周(1-7)-用于前台呈现  的值
     * @return Integer 
     */
    @Column(name = "n_weekday")
    public Integer  getWeekday() {
        return weekday;
    }

    /**
     * 设置周(1-7)-用于前台呈现  的值
     * @param weekday 周(1-7)-用于前台呈现 
     */
    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    /**
     * 获取 比赛编号  的值
     * @return String 
     */
    @Column(name = "s_match_no")
    public String  getMatchNo() {
        return matchNo;
    }

    /**
     * 设置比赛编号  的值
     * @param matchNo 比赛编号 
     */
    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    /**
     * 获取 主队名称  的值
     * @return String 
     */
    @Column(name = "s_host_team")
    public String  getHostTeam() {
        return hostTeam;
    }

    /**
     * 设置主队名称  的值
     * @param hostTeam 主队名称 
     */
    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }

    /**
     * 获取 主队ID  的值
     * @return Integer 
     */
    @Column(name = "n_host_team_id")
    public Integer  getHostTeamId() {
        return hostTeamId;
    }

    /**
     * 设置主队ID  的值
     * @param hostTeamId 主队ID 
     */
    public void setHostTeamId(Integer hostTeamId) {
        this.hostTeamId = hostTeamId;
    }

    /**
     * 获取 客队名称  的值
     * @return String 
     */
    @Column(name = "s_visit_team")
    public String  getVisitTeam() {
        return visitTeam;
    }

    /**
     * 设置客队名称  的值
     * @param visitTeam 客队名称 
     */
    public void setVisitTeam(String visitTeam) {
        this.visitTeam = visitTeam;
    }

    /**
     * 获取 客队ID  的值
     * @return Integer 
     */
    @Column(name = "n_visit_team_id")
    public Integer  getVisitTeamId() {
        return visitTeamId;
    }

    /**
     * 设置客队ID  的值
     * @param visitTeamId 客队ID 
     */
    public void setVisitTeamId(Integer visitTeamId) {
        this.visitTeamId = visitTeamId;
    }

    /**
     * 获取 截止时间  的值
     * @return Date 
     */
    @Column(name = "d_buy_end_time")
    public Date getBuyEndTime() {
        return buyEndTime;
    }

    /**
     * 设置截止时间  的值
     * @param buyEndTime 截止时间
     */
    public void setBuyEndTime(Date buyEndTime) {
        this.buyEndTime = buyEndTime;
    }

    /**
     * 获取 比赛时间  的值
     * @return Date 
     */
    @Column(name = "d_match_start_time")
    public Date  getMatchStartTime() {
        return matchStartTime;
    }

    /**
     * 设置比赛时间  的值
     * @param matchStartTime 比赛时间 
     */
    public void setMatchStartTime(Date matchStartTime) {
        this.matchStartTime = matchStartTime;
    }


    /**
     * 获取 背景色-用于前台呈现  的值
     * @return String 
     */
    @Column(name = "s_bg_color")
    public String  getBgColor() {
        return bgColor;
    }

    /**
     * 设置背景色-用于前台呈现  的值
     * @param bgColor 背景色-用于前台呈现 
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * 获取 欧赔  的值
     * @return String 
     */
    @Column(name = "s_euro_odds")
    public String  getEuroOdds() {
        return euroOdds;
    }

    /**
     * 设置欧赔  的值
     * @param euroOdds 欧赔 
     */
    public void setEuroOdds(String euroOdds) {
        this.euroOdds = euroOdds;
    }

    /**
     * 获取 全场比分  的值
     * @return String 
     */
    @Column(name = "s_full_score")
    public String  getFullScore() {
        return fullScore;
    }

    /**
     * 设置全场比分  的值
     * @param fullScore 全场比分 
     */
    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    /**
     * 获取 胜负  的值
     * @return String 
     */
    @Column(name = "s_sf_sp")
    public String  getSfSp() {
        return sfSp;
    }

    /**
     * 设置胜负  的值
     * @param sfSp 胜负 
     */
    public void setSfSp(String sfSp) {
        this.sfSp = sfSp;
    }

    /**
     * 获取 让分胜负  的值
     * @return String 
     */
    @Column(name = "s_rfsf_sp")
    public String  getRfsfSp() {
        return rfsfSp;
    }

    /**
     * 设置让分胜负  的值
     * @param rfsfSp 让分胜负 
     */
    public void setRfsfSp(String rfsfSp) {
        this.rfsfSp = rfsfSp;
    }

    /**
     * 获取 胜分差  的值
     * @return String 
     */
    @Column(name = "s_sfc_sp")
    public String  getSfcSp() {
        return sfcSp;
    }

    /**
     * 设置胜分差  的值
     * @param sfcSp 胜分差 
     */
    public void setSfcSp(String sfcSp) {
        this.sfcSp = sfcSp;
    }

    /**
     * 获取 大小分  的值
     * @return String 
     */
    @Column(name = "s_dxf_sp")
    public String  getDxfSp() {
        return dxfSp;
    }

    /**
     * 设置大小分  的值
     * @param dxfSp 大小分 
     */
    public void setDxfSp(String dxfSp) {
        this.dxfSp = dxfSp;
    }

    /**
     * 获取 赛果 的值
     * @return String 
     */
    @Column(name = "s_match_result")
    public String  getMatchResult() {
        return matchResult;
    }

    /**
     * 设置赛果 的值
     * @param matchResult 赛果
     */
    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    /**
     * 获取 是否手工修改过 0-没有 1-有 (后台)  的值
     * @return Integer 
     */
    @Column(name = "n_edit_status")
    public Integer  getEditStatus() {
        return editStatus;
    }

    /**
     * 设置是否手工修改过 0-没有 1-有 (后台)  的值
     * @param editStatus 是否手工修改过 0-没有 1-有 (后台) 
     */
    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    /**
     * 获取 是否审核过 0-未审核过 1-有 (后台)  的值
     * @return Integer 
     */
    @Column(name = "n_audit_status")
    public Integer  getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置是否审核过 0-未审核过 1-有 (后台)  的值
     * @param auditStatus 是否审核过 0-未审核过 1-有 (后台) 
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 获取 比赛ID  的值
     * @return Integer 
     */
    @Column(name = "n_match_id")
    public Integer  getMatchId() {
        return matchId;
    }

    /**
     * 设置比赛ID  的值
     * @param matchId 比赛ID 
     */
    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    /**
     * 获取 赛事ID  的值
     * @return Integer 
     */
    @Column(name = "n_tournament_id")
    public Integer  getTournamentId() {
        return tournamentId;
    }

    /**
     * 设置赛事ID  的值
     * @param tournamentId 赛事ID 
     */
    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    /**
     * 获取 比赛名称  的值
     * @return String 
     */
    @Column(name = "s_tournament_name")
    public String  getTournamentName() {
        return tournamentName;
    }

    /**
     * 设置比赛名称  的值
     * @param tournamentName 比赛名称 
     */
    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    /**
     * 获取 新增时间  的值
     * @return Date 
     */
    @Column(name = "d_create_time")
    public Date  getCreateTime() {
        return createTime;
    }

    /**
     * 设置新增时间  的值
     * @param createTime 新增时间 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}