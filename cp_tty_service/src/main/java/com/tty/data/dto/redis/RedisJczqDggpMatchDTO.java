package com.tty.data.dto.redis;

import com.alibaba.fastjson.annotation.JSONField;
import com.tty.data.dto.BasedataTeamInjureDTO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author pdl
 * @create 2017-4-13
 * 竞彩足球赛事推荐 单关固陪
 */
public class RedisJczqDggpMatchDTO {
    private Integer id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime;
    private Integer issueId;
    private String issueName;
    private String issueMatchName;
    private String issueNo;
    private String weekName;
    private Integer week;
    private String matchNo;
    private String matchName;
    private String hostTeam;
    private Integer hostTeamId;
    private String visitTeam;
    private Integer visitTeamId;
    private Integer rq;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date matchTime;
    private String bgColor;
    private String odds;
    private Integer hd;
    private String rz;
    private String hrz;
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
    private Integer hostRank;
    private Integer visitRank;
    private String fightHistory;
    private String hostRecentHistory;
    private String visitRecentHistory;
    private String averageSp;
    private RedisMainBetOfferDTO europeanBetOffer = new RedisMainBetOfferDTO();
    private RedisMainBetOfferDTO asiaBetOffer = new RedisMainBetOfferDTO();
    private RedisBettingRateDTO spfOptionRate = new RedisBettingRateDTO();
    private RedisBettingRateDTO rqspfOptionRate = new RedisBettingRateDTO();
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date recommendTime;
    private String recommendCG;
    private String support;
    private Integer playTypeId;
    private String playTypeName;
    private Integer status;
    private String childPlayTypes;
    private String context;
    private List<BasedataTeamInjureDTO> hostTeamInjuresList;
    private List<BasedataTeamInjureDTO> visitTeamInjureList;
    private Integer hostTeamLevel;
    private Integer visitTeamLevel;
    private Integer hostTeamRecentState;
    private Integer visitTeamRecentState;
    private Integer hostTeamSiteState;
    private Integer visitTeamSiteState;
    private Integer hostTeamEuroRate;
    private Integer visitTeamEuroRate;
    private Integer hostTeamInjure;
    private Integer visitTeamInjure;
    private BigDecimal hostTeamWinRate;
    private BigDecimal visitTeamWinRate;
    private Integer order;

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

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
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

    public Date getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(Date recommendTime) {
        this.recommendTime = recommendTime;
    }

    public String getRecommendCG() {
        return recommendCG;
    }

    public void setRecommendCG(String recommendCG) {
        this.recommendCG = recommendCG;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public Integer getPlayTypeId() {
        return playTypeId;
    }

    public void setPlayTypeId(Integer playTypeId) {
        this.playTypeId = playTypeId;
    }

    public String getPlayTypeName() {
        return playTypeName;
    }

    public void setPlayTypeName(String playTypeName) {
        this.playTypeName = playTypeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChildPlayTypes() {
        return childPlayTypes;
    }

    public void setChildPlayTypes(String childPlayTypes) {
        this.childPlayTypes = childPlayTypes;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<BasedataTeamInjureDTO> getHostTeamInjuresList() {
        return hostTeamInjuresList;
    }

    public void setHostTeamInjuresList(List<BasedataTeamInjureDTO> hostTeamInjuresList) {
        this.hostTeamInjuresList = hostTeamInjuresList;
    }

    public List<BasedataTeamInjureDTO> getVisitTeamInjureList() {
        return visitTeamInjureList;
    }

    public void setVisitTeamInjureList(List<BasedataTeamInjureDTO> visitTeamInjureList) {
        this.visitTeamInjureList = visitTeamInjureList;
    }

    public Integer getHostTeamLevel() {
        return hostTeamLevel;
    }

    public void setHostTeamLevel(Integer hostTeamLevel) {
        this.hostTeamLevel = hostTeamLevel;
    }

    public Integer getVisitTeamLevel() {
        return visitTeamLevel;
    }

    public void setVisitTeamLevel(Integer visitTeamLevel) {
        this.visitTeamLevel = visitTeamLevel;
    }

    public Integer getHostTeamRecentState() {
        return hostTeamRecentState;
    }

    public void setHostTeamRecentState(Integer hostTeamRecentState) {
        this.hostTeamRecentState = hostTeamRecentState;
    }

    public Integer getVisitTeamRecentState() {
        return visitTeamRecentState;
    }

    public void setVisitTeamRecentState(Integer visitTeamRecentState) {
        this.visitTeamRecentState = visitTeamRecentState;
    }

    public Integer getHostTeamSiteState() {
        return hostTeamSiteState;
    }

    public void setHostTeamSiteState(Integer hostTeamSiteState) {
        this.hostTeamSiteState = hostTeamSiteState;
    }

    public Integer getVisitTeamSiteState() {
        return visitTeamSiteState;
    }

    public void setVisitTeamSiteState(Integer visitTeamSiteState) {
        this.visitTeamSiteState = visitTeamSiteState;
    }

    public Integer getHostTeamEuroRate() {
        return hostTeamEuroRate;
    }

    public void setHostTeamEuroRate(Integer hostTeamEuroRate) {
        this.hostTeamEuroRate = hostTeamEuroRate;
    }

    public Integer getVisitTeamEuroRate() {
        return visitTeamEuroRate;
    }

    public void setVisitTeamEuroRate(Integer visitTeamEuroRate) {
        this.visitTeamEuroRate = visitTeamEuroRate;
    }

    public Integer getHostTeamInjure() {
        return hostTeamInjure;
    }

    public void setHostTeamInjure(Integer hostTeamInjure) {
        this.hostTeamInjure = hostTeamInjure;
    }

    public Integer getVisitTeamInjure() {
        return visitTeamInjure;
    }

    public void setVisitTeamInjure(Integer visitTeamInjure) {
        this.visitTeamInjure = visitTeamInjure;
    }

    public BigDecimal getHostTeamWinRate() {
        return hostTeamWinRate;
    }

    public void setHostTeamWinRate(BigDecimal hostTeamWinRate) {
        this.hostTeamWinRate = hostTeamWinRate;
    }

    public BigDecimal getVisitTeamWinRate() {
        return visitTeamWinRate;
    }

    public void setVisitTeamWinRate(BigDecimal visitTeamWinRate) {
        this.visitTeamWinRate = visitTeamWinRate;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
