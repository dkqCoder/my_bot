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
@Table(name = "basedata_match_bjdc")
public class BasedataMatchBjdcENT implements java.io.Serializable {

    /** 主键ID */
    private Integer id;

    /** 期次比赛号 s_issue_name+n_match_no */
    private String issueMatchName;

    /** 期次名称   */
    private String issueName;

    /** 比赛编号 */
    private String matchNo;

    /** 周(1-7)-用于前台呈现 */
    private Integer weekday;

    /** s_weekday_name */
    private String weekdayName;

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

    /** 比赛时间 */
    private Date matchStartTime;

    /** 比赛结束时间 */
    private Date buyEndTime;

    /** 创建时间 */
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
     * 获取 期次比赛号 s_issue_name+n_match_no 的值
     * @return String 
     */
    @Column(name = "s_issue_match_name")
    public String  getIssueMatchName() {
        return issueMatchName;
    }

    /**
     * 设置期次比赛号 s_issue_name+n_match_no 的值
     * @param issueMatchName 期次比赛号 s_issue_name+n_match_no
     */
    public void setIssueMatchName(String issueMatchName) {
        this.issueMatchName = issueMatchName;
    }

    /**
     * 获取 期次名称   的值
     * @return String 
     */
    @Column(name = "s_issue_name")
    public String  getIssueName() {
        return issueName;
    }

    /**
     * 设置期次名称   的值
     * @param issueName 期次名称  
     */
    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    /**
     * 获取 比赛编号 的值
     * @return Integer 
     */
    @Column(name = "s_match_no")
    public String  getMatchNo() {
        return matchNo;
    }

    /**
     * 设置比赛编号 的值
     * @param matchNo 比赛编号
     */
    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    /**
     * 获取 周(1-7)-用于前台呈现 的值
     * @return Integer 
     */
    @Column(name = "n_weekday")
    public Integer  getWeekday() {
        return weekday;
    }

    /**
     * 设置周(1-7)-用于前台呈现 的值
     * @param weekday 周(1-7)-用于前台呈现
     */
    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    /**
     * 获取 s_weekday_name 的值
     * @return String 
     */
    @Column(name = "s_weekday_name")
    public String  getWeekdayName() {
        return weekdayName;
    }

    /**
     * 设置s_weekday_name 的值
     * @param weekdayName s_weekday_name
     */
    public void setWeekdayName(String weekdayName) {
        this.weekdayName = weekdayName;
    }

    /**
     * 获取 比赛ID 的值
     * @return Integer 
     */
    @Column(name = "n_match_id")
    public Integer  getMatchId() {
        return matchId;
    }

    /**
     * 设置比赛ID 的值
     * @param matchId 比赛ID
     */
    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    /**
     * 获取 主队名称 的值
     * @return String 
     */
    @Column(name = "s_host_team")
    public String  getHostTeam() {
        return hostTeam;
    }

    /**
     * 设置主队名称 的值
     * @param hostTeam 主队名称
     */
    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }

    /**
     * 获取 主队ID 的值
     * @return Integer 
     */
    @Column(name = "n_host_team_id")
    public Integer  getHostTeamId() {
        return hostTeamId;
    }

    /**
     * 设置主队ID 的值
     * @param hostTeamId 主队ID
     */
    public void setHostTeamId(Integer hostTeamId) {
        this.hostTeamId = hostTeamId;
    }

    /**
     * 获取 客队名称 的值
     * @return String 
     */
    @Column(name = "s_visit_team")
    public String  getVisitTeam() {
        return visitTeam;
    }

    /**
     * 设置客队名称 的值
     * @param visitTeam 客队名称
     */
    public void setVisitTeam(String visitTeam) {
        this.visitTeam = visitTeam;
    }

    /**
     * 获取 客队ID 的值
     * @return Integer 
     */
    @Column(name = "n_visit_team_id")
    public Integer  getVisitTeamId() {
        return visitTeamId;
    }

    /**
     * 设置客队ID 的值
     * @param visitTeamId 客队ID
     */
    public void setVisitTeamId(Integer visitTeamId) {
        this.visitTeamId = visitTeamId;
    }

    /**
     * 获取 让球,-1客队让1球,0不让,+0.5主队让半球 的值
     * @return Integer 
     */
    @Column(name = "n_rq")
    public Integer  getRq() {
        return rq;
    }

    /**
     * 设置让球,-1客队让1球,0不让,+0.5主队让半球 的值
     * @param rq 让球,-1客队让1球,0不让,+0.5主队让半球
     */
    public void setRq(Integer rq) {
        this.rq = rq;
    }

    /**
     * 获取 背景色-用于前台呈现 的值
     * @return String 
     */
    @Column(name = "s_bg_color")
    public String  getBgColor() {
        return bgColor;
    }

    /**
     * 设置背景色-用于前台呈现 的值
     * @param bgColor 背景色-用于前台呈现
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * 获取 欧赔 的值
     * @return String 
     */
    @Column(name = "s_euro_odds")
    public String  getEuroOdds() {
        return euroOdds;
    }

    /**
     * 设置欧赔 的值
     * @param euroOdds 欧赔
     */
    public void setEuroOdds(String euroOdds) {
        this.euroOdds = euroOdds;
    }

    /**
     * 获取 半场比分 的值
     * @return String 
     */
    @Column(name = "s_half_score")
    public String  getHalfScore() {
        return halfScore;
    }

    /**
     * 设置半场比分 的值
     * @param halfScore 半场比分
     */
    public void setHalfScore(String halfScore) {
        this.halfScore = halfScore;
    }

    /**
     * 获取 全场比分 的值
     * @return String 
     */
    @Column(name = "s_full_score")
    public String  getFullScore() {
        return fullScore;
    }

    /**
     * 设置全场比分 的值
     * @param fullScore 全场比分
     */
    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    /**
     * 获取 让球胜平负 的值
     * @return String 
     */
    @Column(name = "s_spf_sp")
    public String  getSpfSp() {
        return spfSp;
    }

    /**
     * 设置让球胜平负 的值
     * @param spfSp 让球胜平负
     */
    public void setSpfSp(String spfSp) {
        this.spfSp = spfSp;
    }

    /**
     * 获取 进球数 的值
     * @return String 
     */
    @Column(name = "s_jqs_sp")
    public String  getJqsSp() {
        return jqsSp;
    }

    /**
     * 设置进球数 的值
     * @param jqsSp 进球数
     */
    public void setJqsSp(String jqsSp) {
        this.jqsSp = jqsSp;
    }

    /**
     * 获取 猜比分 的值
     * @return String 
     */
    @Column(name = "s_cbf_sp")
    public String  getCbfSp() {
        return cbfSp;
    }

    /**
     * 设置猜比分 的值
     * @param cbfSp 猜比分
     */
    public void setCbfSp(String cbfSp) {
        this.cbfSp = cbfSp;
    }

    /**
     * 获取 上下盘 的值
     * @return String 
     */
    @Column(name = "s_sxp_sp")
    public String  getSxpSp() {
        return sxpSp;
    }

    /**
     * 设置上下盘 的值
     * @param sxpSp 上下盘
     */
    public void setSxpSp(String sxpSp) {
        this.sxpSp = sxpSp;
    }

    /**
     * 获取 半全场 的值
     * @return String 
     */
    @Column(name = "s_bqc_sp")
    public String  getBqcSp() {
        return bqcSp;
    }

    /**
     * 设置半全场 的值
     * @param bqcSp 半全场
     */
    public void setBqcSp(String bqcSp) {
        this.bqcSp = bqcSp;
    }

    /**
     * 获取 最终SP 的值
     * @return String 
     */
    @Column(name = "s_final_sp")
    public String  getFinalSp() {
        return finalSp;
    }

    /**
     * 设置最终SP 的值
     * @param finalSp 最终SP
     */
    public void setFinalSp(String finalSp) {
        this.finalSp = finalSp;
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
     * 获取 是否手工修改过 0-没有 1-有  （如果手工修改过记录，将不再接受数据组推送数据） 的值
     * @return Integer 
     */
    @Column(name = "n_edit_status")
    public Integer  getEditStatus() {
        return editStatus;
    }

    /**
     * 设置是否手工修改过 0-没有 1-有  （如果手工修改过记录，将不再接受数据组推送数据） 的值
     * @param editStatus 是否手工修改过 0-没有 1-有  （如果手工修改过记录，将不再接受数据组推送数据）
     */
    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    /**
     * 获取 是否审核过 0-未审核过 1-有 (后台) 的值
     * @return Integer 
     */
    @Column(name = "n_audit_status")
    public Integer  getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置是否审核过 0-未审核过 1-有 (后台) 的值
     * @param auditStatus 是否审核过 0-未审核过 1-有 (后台)
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 获取 联赛名称(联赛杯赛)ID 的值
     * @return Integer 
     */
    @Column(name = "n_tournament_id")
    public Integer  getTournamentId() {
        return tournamentId;
    }

    /**
     * 设置联赛名称(联赛杯赛)ID 的值
     * @param tournamentId 联赛名称(联赛杯赛)ID
     */
    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    /**
     * 获取 联赛名称(联赛杯赛) 的值
     * @return String 
     */
    @Column(name = "s_tournament_name")
    public String  getTournamentName() {
        return tournamentName;
    }

    /**
     * 设置联赛名称(联赛杯赛) 的值
     * @param tournamentName 联赛名称(联赛杯赛)
     */
    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    /**
     * 获取 比赛时间 的值
     * @return Date 
     */
    @Column(name = "d_match_start_time")
    public Date  getMatchStartTime() {
        return matchStartTime;
    }

    /**
     * 设置比赛时间 的值
     * @param matchStartTime 比赛时间
     */
    public void setMatchStartTime(Date matchStartTime) {
        this.matchStartTime = matchStartTime;
    }

    /**
     * 获取 比赛结束时间 的值
     * @return Date 
     */
    @Column(name = "d_buy_end_time")
    public Date getBuyEndTime() {
        return buyEndTime;
    }

    /**
     * 设置比赛结束时间 的值
     * @param buyEndTime 比赛结束时间
     */
    public void setBuyEndTime(Date buyEndTime) {
        this.buyEndTime = buyEndTime;
    }

    /**
     * 获取 创建时间 的值
     * @return Date 
     */
    @Column(name = "d_create_time")
    public Date  getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间 的值
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}