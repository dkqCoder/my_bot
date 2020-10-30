package com.tty.data.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * @author zhangdi
 * @date 17/4/13
 * @Description
 */
public class ClientJclqMatchVO implements java.io.Serializable {
    private String mId;//比赛编号
    private String matchName;//比赛名称
    private String tournamentId;//赛事Id
    private Date endTime;//投注截止时间
    private String hostTeam;//主队名称
    private String vistorTeam;//客队名称
    private String spRFSF;//让分胜负SP
    private String spSF;//胜负SP
    private String spSFC;//胜分差SP
    private String spDXF;//胜分差SP
    private Integer dgRFSFStatus;//是否开售单关让分胜负，0：关，1：开售
    private Integer dgSFStatus;//是否开售单关胜负，0：关，1：开售
    private Integer dgDXFStatus;//是否开售单关大小分，0：关，1：开售
    private Integer dgSFCStatus;//是否开售单关胜分差，0：关，1：开售
    private Integer status;//1：开售，0：整场停售
    private String reason;//停售原因
    //顺序：让分胜负: 1,1 + 胜负: 1,1 + 胜分差: 1,1,1,1,1,1,1,1,1,1,1,1 + 大小分: 1,1
    //四项加起来与PSDetailState长度相同
    private String playDetailState;//玩法开停售明细状态
    private String playState;//玩法开停售状态，1：开售，0：停售，让分胜负,胜负,胜分差,大小分
    private String hostRank;//主队排名
    private String hostAlias;//主队区域
    private String vistorRank;//客队排名
    private String vistorAlias;//客队区域
    private String haHistory;//历史交锋
    private String hostHistory; //主队历史战绩
    private String hostRFHistory; //主队让分胜负历史战绩
    private String hostDXFHistory; //主队大小分历史战绩
    private String vistorHistory; //客队历史战绩
    private String vistorRFHistory;//客队让分历史战绩
    private String vistorDXFHistory;//客队大小分历史战绩
    private String bgColor;
    private Integer hostTeamId;
    private Integer visitTeamId;
    private String matchId;

    @JSONField(name = "MID")
    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    @JSONField(name = "NMm")
    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    @JSONField(name = "ETime", format = "yyyy-MM-dd HH:mm:ss")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @JSONField(name = "HTeam")
    public String getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }

    @JSONField(name = "VTeam")
    public String getVistorTeam() {
        return vistorTeam;
    }

    public void setVistorTeam(String vistorTeam) {
        this.vistorTeam = vistorTeam;
    }

    @JSONField(name = "SpRFSF")
    public String getSpRFSF() {
        return spRFSF;
    }

    public void setSpRFSF(String spRFSF) {
        this.spRFSF = spRFSF;
    }

    @JSONField(name = "SpSF")
    public String getSpSF() {
        return spSF;
    }

    public void setSpSF(String spSF) {
        this.spSF = spSF;
    }

    @JSONField(name = "SpSFC")
    public String getSpSFC() {
        return spSFC;
    }

    public void setSpSFC(String spSFC) {
        this.spSFC = spSFC;
    }

    @JSONField(name = "SpDXF")
    public String getSpDXF() {
        return spDXF;
    }

    public void setSpDXF(String spDXF) {
        this.spDXF = spDXF;
    }

    @JSONField(name = "DGRFSFStatus")
    public Integer getDgRFSFStatus() {
        return dgRFSFStatus;
    }

    public void setDgRFSFStatus(Integer dgRFSFStatus) {
        this.dgRFSFStatus = dgRFSFStatus;
    }


    @JSONField(name = "DGSFStatus")
    public Integer getDgSFStatus() {
        return dgSFStatus;
    }

    public void setDgSFStatus(Integer dgSFStatus) {
        this.dgSFStatus = dgSFStatus;
    }

    @JSONField(name = "DGDXFStatus")
    public Integer getDgDXFStatus() {
        return dgDXFStatus;
    }

    public void setDgDXFStatus(Integer dgDXFStatus) {
        this.dgDXFStatus = dgDXFStatus;
    }

    @JSONField(name = "DGSFCStatus")
    public Integer getDgSFCStatus() {
        return dgSFCStatus;
    }

    public void setDgSFCStatus(Integer dgSFCStatus) {
        this.dgSFCStatus = dgSFCStatus;
    }

    @JSONField(name = "Hd")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JSONField(name = "Reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @JSONField(name = "PSDetailState")
    public String getPlayDetailState() {
        return playDetailState;
    }

    public void setPlayDetailState(String playDetailState) {
        this.playDetailState = playDetailState;
    }

    @JSONField(name = "PSState")
    public String getPlayState() {
        return playState;
    }

    public void setPlayState(String playState) {
        this.playState = playState;
    }

    @JSONField(name = "HomeRank")
    public String getHostRank() {
        return hostRank;
    }

    public void setHostRank(String hostRank) {
        this.hostRank = hostRank;
    }

    @JSONField(name = "HomeAlias")
    public String getHostAlias() {
        return hostAlias;
    }

    public void setHostAlias(String hostAlias) {
        this.hostAlias = hostAlias;
    }

    @JSONField(name = "AwayRank")
    public String getVistorRank() {
        return vistorRank;
    }

    public void setVistorRank(String vistorRank) {
        this.vistorRank = vistorRank;
    }

    @JSONField(name = "Awaylias")
    public String getVistorAlias() {
        return vistorAlias;
    }

    public void setVistorAlias(String vistorAlias) {
        this.vistorAlias = vistorAlias;
    }

    @JSONField(name = "HAHistory")
    public String getHaHistory() {
        return haHistory;
    }

    public void setHaHistory(String haHistory) {
        this.haHistory = haHistory;
    }

    @JSONField(name = "HomeHistory")
    public String getHostHistory() {
        return hostHistory;
    }

    public void setHostHistory(String hostHistory) {
        this.hostHistory = hostHistory;
    }

    @JSONField(name = "HomeRFHistory")
    public String getHostRFHistory() {
        return hostRFHistory;
    }

    public void setHostRFHistory(String hostRFHistory) {
        this.hostRFHistory = hostRFHistory;
    }

    @JSONField(name = "HomeDXFHistory")
    public String getHostDXFHistory() {
        return hostDXFHistory;
    }

    public void setHostDXFHistory(String hostDXFHistory) {
        this.hostDXFHistory = hostDXFHistory;
    }

    @JSONField(name = "AwayHistory")
    public String getVistorHistory() {
        return vistorHistory;
    }

    public void setVistorHistory(String vistorHistory) {
        this.vistorHistory = vistorHistory;
    }

    @JSONField(name = "AwayRFHistory")
    public String getVistorRFHistory() {
        return vistorRFHistory;
    }

    public void setVistorRFHistory(String vistorRFHistory) {
        this.vistorRFHistory = vistorRFHistory;
    }

    @JSONField(name = "AwayDXFHistory")
    public String getVistorDXFHistory() {
        return vistorDXFHistory;
    }

    public void setVistorDXFHistory(String vistorDXFHistory) {
        this.vistorDXFHistory = vistorDXFHistory;
    }

    @JSONField(name = "BgColor")
    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    @JSONField(name = "HTeamID")
    public Integer getHostTeamId() {
        return hostTeamId;
    }

    public void setHostTeamId(Integer hostTeamId) {
        this.hostTeamId = hostTeamId;
    }

    @JSONField(name = "VTeamID")
    public Integer getVisitTeamId() {
        return visitTeamId;
    }

    public void setVisitTeamId(Integer visitTeamId) {
        this.visitTeamId = visitTeamId;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }
    @JSONField(name = "MatchID")
    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
}
