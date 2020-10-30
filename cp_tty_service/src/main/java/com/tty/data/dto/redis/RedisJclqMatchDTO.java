package com.tty.data.dto.redis;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * @author zhangdi
 * @date 17/4/12
 * @Description
 */
public class RedisJclqMatchDTO {
    private Integer id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime;
    private Integer issueId;
    private String issueName;
    private String issueMatchName;
    private String weekName;
    private Integer week;
    private String matchNo;
    private String matchName="";
    private String hostTeam="";
    private Integer hostTeamId;
    private String visitTeam="";
    private Integer visitTeamId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date matchTime;
    private String bgColor;
    private String odds;
    private Integer hd;
    private String rz;
    private String spsf;
    private String sprfsf;
    private String spsfc;
    private String spdxf;
    private String rzResult;
    private String spResult;
    private String spdgsf;
    private String spdgrfsf;
    private String spdgdxf;
    private String spdgResult;
    private Boolean edit;
    private Boolean audit;
    private Boolean pass;
    private Integer matchId;
    private String psStatus;
    private Integer dgrfsfStatus;
    private Integer dgsfStatus;
    private Integer dgdxfStatus;
    private Integer tournamentId;
    private Integer hostRank = 0;
    private String hostArea = "";
    private Integer visitRank = 0;
    private String visitArea = "";
    private String hostAgainstVisitHistory;
    private String hostRecentHistory;
    private String hostRfHistory;
    private String hostDxfHistory;
    private String visitRecentHistory;
    private String visitRfHistory;
    private String visitDxfHistory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
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

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public Integer getHd() {
        return hd;
    }

    public void setHd(Integer hd) {
        this.hd = hd;
    }

    public String getRz() {
        return rz;
    }

    public void setRz(String rz) {
        this.rz = rz;
    }

    public String getSpsf() {
        return spsf;
    }

    public void setSpsf(String spsf) {
        this.spsf = spsf;
    }

    public String getSprfsf() {
        return sprfsf;
    }

    public void setSprfsf(String sprfsf) {
        this.sprfsf = sprfsf;
    }

    public String getSpsfc() {
        return spsfc;
    }

    public void setSpsfc(String spsfc) {
        this.spsfc = spsfc;
    }

    public String getSpdxf() {
        return spdxf;
    }

    public void setSpdxf(String spdxf) {
        this.spdxf = spdxf;
    }

    public String getRzResult() {
        return rzResult;
    }

    public void setRzResult(String rzResult) {
        this.rzResult = rzResult;
    }

    public String getSpResult() {
        return spResult;
    }

    public void setSpResult(String spResult) {
        this.spResult = spResult;
    }

    public String getSpdgsf() {
        return spdgsf;
    }

    public void setSpdgsf(String spdgsf) {
        this.spdgsf = spdgsf;
    }

    public String getSpdgrfsf() {
        return spdgrfsf;
    }

    public void setSpdgrfsf(String spdgrfsf) {
        this.spdgrfsf = spdgrfsf;
    }

    public String getSpdgdxf() {
        return spdgdxf;
    }

    public void setSpdgdxf(String spdgdxf) {
        this.spdgdxf = spdgdxf;
    }

    public String getSpdgResult() {
        return spdgResult;
    }

    public void setSpdgResult(String spdgResult) {
        this.spdgResult = spdgResult;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public Boolean getAudit() {
        return audit;
    }

    public void setAudit(Boolean audit) {
        this.audit = audit;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getPsStatus() {
        return psStatus;
    }

    public void setPsStatus(String psStatus) {
        this.psStatus = psStatus;
    }

    public Integer getDgrfsfStatus() {
        return dgrfsfStatus;
    }

    public void setDgrfsfStatus(Integer dgrfsfStatus) {
        this.dgrfsfStatus = dgrfsfStatus;
    }

    public Integer getDgsfStatus() {
        return dgsfStatus;
    }

    public void setDgsfStatus(Integer dgsfStatus) {
        this.dgsfStatus = dgsfStatus;
    }

    public Integer getDgdxfStatus() {
        return dgdxfStatus;
    }

    public void setDgdxfStatus(Integer dgdxfStatus) {
        this.dgdxfStatus = dgdxfStatus;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Integer getHostRank() {
        return hostRank;
    }

    public void setHostRank(Integer hostRank) {
        this.hostRank = hostRank;
    }

    public String getHostArea() {
        return hostArea;
    }

    public void setHostArea(String hostArea) {
        this.hostArea = hostArea;
    }

    public Integer getVisitRank() {
        return visitRank;
    }

    public void setVisitRank(Integer visitRank) {
        this.visitRank = visitRank;
    }

    public String getVisitArea() {
        return visitArea;
    }

    public void setVisitArea(String visitArea) {
        this.visitArea = visitArea;
    }

    public String getHostAgainstVisitHistory() {
        return hostAgainstVisitHistory;
    }

    public void setHostAgainstVisitHistory(String hostAgainstVisitHistory) {
        this.hostAgainstVisitHistory = hostAgainstVisitHistory;
    }

    public String getHostRecentHistory() {
        return hostRecentHistory;
    }

    public void setHostRecentHistory(String hostRecentHistory) {
        this.hostRecentHistory = hostRecentHistory;
    }

    public String getHostRfHistory() {
        return hostRfHistory;
    }

    public void setHostRfHistory(String hostRfHistory) {
        this.hostRfHistory = hostRfHistory;
    }

    public String getHostDxfHistory() {
        return hostDxfHistory;
    }

    public void setHostDxfHistory(String hostDxfHistory) {
        this.hostDxfHistory = hostDxfHistory;
    }

    public String getVisitRecentHistory() {
        return visitRecentHistory;
    }

    public void setVisitRecentHistory(String visitRecentHistory) {
        this.visitRecentHistory = visitRecentHistory;
    }

    public String getVisitRfHistory() {
        return visitRfHistory;
    }

    public void setVisitRfHistory(String visitRfHistory) {
        this.visitRfHistory = visitRfHistory;
    }

    public String getVisitDxfHistory() {
        return visitDxfHistory;
    }

    public void setVisitDxfHistory(String visitDxfHistory) {
        this.visitDxfHistory = visitDxfHistory;
    }
}
