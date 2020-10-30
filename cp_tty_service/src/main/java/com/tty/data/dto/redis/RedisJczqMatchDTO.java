package com.tty.data.dto.redis;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * shareRedis中竞彩足球对阵dto
 */
public class RedisJczqMatchDTO {

    private Integer id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime;
    private Integer issueId;
    private String issueName;
    private String issueMatchName;
    private String weekName;
    private Integer week;
    private String matchNo;
    private String matchName = "";
    private String hostTeam = "";
    private Integer hostTeamId;
    private String visitTeam = "";
    private Integer visitTeamId;
    private Integer rq;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date matchTime;
    private String bgColor;
    private String odds;
    private Integer hd; //开售状态
    private String rz; // 全场比分
    private String hrz; //半场比分
    private String spspf;
    private String sprqs;
    private String spjqs;
    private String spcbf;
    private String spbqc;
    private String rzResult;
    private String spResult;
    private Boolean edit;
    private Boolean audit;
    private Boolean pass;
    private String spdgspf;
    private String spdgrqs;
    private String spdgjqs;
    private String spdgbqc;
    private String spdgResult;
    private Boolean dgAudit;
    private Boolean dgPass;
    private Integer matchId;
    private String psStatus;
    private Boolean teamReverse;
    private Integer dgspfStatus;
    private Integer dgrqspfStatus;
    private Integer dgzjqStatus;
    private Integer dgbqcStatus;
    private Integer tournamentId;
    private Integer hostRank = 0;
    private Integer visitRank = 0;
    private String homeFieldRank;//主队实力
    private String awayFieldRank;//客队实力

    private String fightHistory = "";
    private String hostRecentHistory = "";
    private String visitRecentHistory = "";
    private String averageSp = "";

    private boolean hot;

    private RedisMainBetOfferDTO europeanBetOffer = new RedisMainBetOfferDTO();
    private RedisMainBetOfferDTO asiaBetOffer = new RedisMainBetOfferDTO();
    private RedisBettingRateDTO spfOptionRate = new RedisBettingRateDTO();
    private RedisBettingRateDTO rqspfOptionRate = new RedisBettingRateDTO();

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

    public Integer getRq() {
        return rq;
    }

    public void setRq(Integer rq) {
        this.rq = rq;
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

    public String getHrz() {
        return hrz;
    }

    public void setHrz(String hrz) {
        this.hrz = hrz;
    }

    public String getSpspf() {
        return spspf;
    }

    public void setSpspf(String spspf) {
        this.spspf = spspf;
    }

    public String getSprqs() {
        return sprqs;
    }

    public void setSprqs(String sprqs) {
        this.sprqs = sprqs;
    }

    public String getSpjqs() {
        return spjqs;
    }

    public void setSpjqs(String spjqs) {
        this.spjqs = spjqs;
    }

    public String getSpcbf() {
        return spcbf;
    }

    public void setSpcbf(String spcbf) {
        this.spcbf = spcbf;
    }

    public String getSpbqc() {
        return spbqc;
    }

    public void setSpbqc(String spbqc) {
        this.spbqc = spbqc;
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

    public String getSpdgspf() {
        return spdgspf;
    }

    public void setSpdgspf(String spdgspf) {
        this.spdgspf = spdgspf;
    }

    public String getSpdgrqs() {
        return spdgrqs;
    }

    public void setSpdgrqs(String spdgrqs) {
        this.spdgrqs = spdgrqs;
    }

    public String getSpdgjqs() {
        return spdgjqs;
    }

    public void setSpdgjqs(String spdgjqs) {
        this.spdgjqs = spdgjqs;
    }

    public String getSpdgbqc() {
        return spdgbqc;
    }

    public void setSpdgbqc(String spdgbqc) {
        this.spdgbqc = spdgbqc;
    }

    public String getSpdgResult() {
        return spdgResult;
    }

    public void setSpdgResult(String spdgResult) {
        this.spdgResult = spdgResult;
    }

    public Boolean getDgAudit() {
        return dgAudit;
    }

    public void setDgAudit(Boolean dgAudit) {
        this.dgAudit = dgAudit;
    }

    public Boolean getDgPass() {
        return dgPass;
    }

    public void setDgPass(Boolean dgPass) {
        this.dgPass = dgPass;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    //过关玩法销售状态(1,1,1,1,1->表示5个玩法都在售) 让球胜平负+总进球+猜比分+半全场+胜平负
    public String getPsStatus() {
        return psStatus;
    }

    public void setPsStatus(String psStatus) {
        this.psStatus = psStatus;
    }

    public Boolean getTeamReverse() {
        return teamReverse;
    }

    public void setTeamReverse(Boolean teamReverse) {
        this.teamReverse = teamReverse;
    }

    public Integer getDgspfStatus() {
        return dgspfStatus;
    }

    public void setDgspfStatus(Integer dgspfStatus) {
        this.dgspfStatus = dgspfStatus;
    }

    public Integer getDgrqspfStatus() {
        return dgrqspfStatus;
    }

    public void setDgrqspfStatus(Integer dgrqspfStatus) {
        this.dgrqspfStatus = dgrqspfStatus;
    }

    public Integer getDgzjqStatus() {
        return dgzjqStatus;
    }

    public void setDgzjqStatus(Integer dgzjqStatus) {
        this.dgzjqStatus = dgzjqStatus;
    }

    public Integer getDgbqcStatus() {
        return dgbqcStatus;
    }

    public void setDgbqcStatus(Integer dgbqcStatus) {
        this.dgbqcStatus = dgbqcStatus;
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

    public Integer getVisitRank() {
        return visitRank;
    }

    public void setVisitRank(Integer visitRank) {
        this.visitRank = visitRank;
    }

    public String getFightHistory() {
        return fightHistory;
    }

    public void setFightHistory(String fightHistory) {
        this.fightHistory = fightHistory;
    }

    public String getHostRecentHistory() {
        return hostRecentHistory;
    }

    public void setHostRecentHistory(String hostRecentHistory) {
        this.hostRecentHistory = hostRecentHistory;
    }

    public String getVisitRecentHistory() {
        return visitRecentHistory;
    }

    public void setVisitRecentHistory(String visitRecentHistory) {
        this.visitRecentHistory = visitRecentHistory;
    }

    public String getAverageSp() {
        return averageSp;
    }

    public void setAverageSp(String averageSp) {
        this.averageSp = averageSp;
    }

    public RedisMainBetOfferDTO getEuropeanBetOffer() {
        return europeanBetOffer;
    }

    public void setEuropeanBetOffer(RedisMainBetOfferDTO europeanBetOffer) {
        this.europeanBetOffer = europeanBetOffer;
    }

    public RedisMainBetOfferDTO getAsiaBetOffer() {
        return asiaBetOffer;
    }

    public void setAsiaBetOffer(RedisMainBetOfferDTO asiaBetOffer) {
        this.asiaBetOffer = asiaBetOffer;
    }

    public RedisBettingRateDTO getSpfOptionRate() {
        return spfOptionRate;
    }

    public void setSpfOptionRate(RedisBettingRateDTO spfOptionRate) {
        this.spfOptionRate = spfOptionRate;
    }

    public RedisBettingRateDTO getRqspfOptionRate() {
        return rqspfOptionRate;
    }

    public void setRqspfOptionRate(RedisBettingRateDTO rqspfOptionRate) {
        this.rqspfOptionRate = rqspfOptionRate;
    }

    public String getIssueMatchName() {
        return issueMatchName;
    }

    public void setIssueMatchName(String issueMatchName) {
        this.issueMatchName = issueMatchName;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public String getHomeFieldRank() {
        return homeFieldRank;
    }

    public void setHomeFieldRank(String homeFieldRank) {
        this.homeFieldRank = homeFieldRank;
    }

    public String getAwayFieldRank() {
        return awayFieldRank;
    }

    public void setAwayFieldRank(String awayFieldRank) {
        this.awayFieldRank = awayFieldRank;
    }
}
