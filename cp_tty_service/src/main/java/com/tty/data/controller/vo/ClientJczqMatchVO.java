package com.tty.data.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.tty.data.dto.redis.RedisBettingRateDTO;
import java.util.Date;

/**
 * @author zhangdi
 * @date 17/4/13
 * @Description
 */
public class ClientJczqMatchVO implements java.io.Serializable {
    private boolean hot;//是否热门
    private Integer matchId;//比赛ID
    private String mId;//比赛场次编号
    private String matchName;//比赛(联赛、杯赛)名称
    private Date endTime;//投注截止时间
    private String hostTeam;//主队名称
    private String vistorTeam;//客队名称
    private String rq;//让球数量
    private String spSPF;//胜平负SP
    private String spRQSPF;//让球胜平负SP
    private String spZJQ;//总进球SP
    private String spCBF;//猜比分SP
    private String spBQC;//猜比分SP
    private Integer dgSPFStatus;//是否开售单关胜平负，0：否，1：开
    private Integer dgRQSPFStatus;//是否开售单关让球胜平负，0：否，1：开
    private Integer dgZJQStatus;//是否开售大单关总进球，0：否，1：开
    private Integer dgCBFStatus;//是否开售单关猜比分，0：否，1：开
    private Integer dgBQCStatus;//是否开售单关半全场，0：否，1：开

    private Integer hostRank = 0;//主队排名
    private Integer vistorRank = 0;//客队排名

    private String homeFieldRank;
    private String awayFieldRank;
    private String haHistory = "";//历史交锋
    private String hostHistory = ""; //主队历史战绩
    private String vistorHistory = ""; //客队历史战绩
    private String avgSp = "";//平均SP
    private RedisBettingRateDTO optionSPF;//胜平负投注比例
    private RedisBettingRateDTO optionRQSPF;//胜平负投注比例
    private Integer status;//1：开售，0：整场停售
    private String reason = "";//停售原因
    //顺序参考：让球胜平负 1,1,1+总进球 1,1,1,1,1,1,1,1
    // 猜比分 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
    // 半全场 1,1,1,1,1,1,1,1,1
    // 胜平负 1,1,1
    //五项加起来与PSDetailState 相同
    private String playDetailState;//玩法开停售明细状态
    private String playState;//玩法开停售状态，1：开，0：关，顺序为： 让球胜平负，总进球，猜比分，半全场，胜平负
    private String bgColor;
    private Integer hostTeamId;
    private Integer visitTeamId;
    private Integer tournamentId;//联赛、杯赛名称
    private Date matchStartTime;//比赛开始时间
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
    public Integer getHostRank() {
        return hostRank;
    }

    public void setHostRank(Integer hostRank) {
        this.hostRank = hostRank;
    }
    @JSONField(name = "AwayRank")
    public Integer getVistorRank() {
        return vistorRank;
    }


    @JSONField(name = "HomeFieldRank")
    public String getHomeFieldRank() {
        return homeFieldRank;
    }

    public void setHomeFieldRank(String homeFieldRank) {
        this.homeFieldRank = homeFieldRank;
    }

    @JSONField(name = "AwayFieldRank")
    public String getAwayFieldRank() {
        return awayFieldRank;
    }

    public void setAwayFieldRank(String awayFieldRank) {
        this.awayFieldRank = awayFieldRank;
    }


    public void setVistorRank(Integer vistorRank) {
        this.vistorRank = vistorRank;
    }
    @JSONField(name = "AwayHistory")
    public String getVistorHistory() {
        return vistorHistory;
    }

    public void setVistorHistory(String vistorHistory) {
        this.vistorHistory = vistorHistory;
    }
    @JSONField(name = "IsHot")
    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }
    @JSONField(name = "MatchID")
    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }
    @JSONField(name = "Rq")
    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq;
    }
    @JSONField(name = "SpSPF")
    public String getSpSPF() {
        return spSPF;
    }

    public void setSpSPF(String spSPF) {
        this.spSPF = spSPF;
    }
    @JSONField(name = "SpRQSPF")
    public String getSpRQSPF() {
        return spRQSPF;
    }

    public void setSpRQSPF(String spRQSPF) {
        this.spRQSPF = spRQSPF;
    }
    @JSONField(name = "SpZJQ")
    public String getSpZJQ() {
        return spZJQ;
    }

    public void setSpZJQ(String spZJQ) {
        this.spZJQ = spZJQ;
    }
    @JSONField(name = "SpCBF")
    public String getSpCBF() {
        return spCBF;
    }

    public void setSpCBF(String spCBF) {
        this.spCBF = spCBF;
    }
    @JSONField(name = "SpBQC")
    public String getSpBQC() {
        return spBQC;
    }

    public void setSpBQC(String spBQC) {
        this.spBQC = spBQC;
    }
    @JSONField(name = "DGSPFStatus")
    public Integer getDgSPFStatus() {
        return dgSPFStatus;
    }

    public void setDgSPFStatus(Integer dgSPFStatus) {
        this.dgSPFStatus = dgSPFStatus;
    }
    @JSONField(name = "DGRQSPFStatus")
    public Integer getDgRQSPFStatus() {
        return dgRQSPFStatus;
    }

    public void setDgRQSPFStatus(Integer dgRQSPFStatus) {
        this.dgRQSPFStatus = dgRQSPFStatus;
    }
    @JSONField(name = "DGZJQStatus")
    public Integer getDgZJQStatus() {
        return dgZJQStatus;
    }

    public void setDgZJQStatus(Integer dgZJQStatus) {
        this.dgZJQStatus = dgZJQStatus;
    }
    @JSONField(name = "DGCBFStatus")
    public Integer getDgCBFStatus() {
        return dgCBFStatus;
    }

    public void setDgCBFStatus(Integer dgCBFStatus) {
        this.dgCBFStatus = dgCBFStatus;
    }
    @JSONField(name = "DGBQCStatus")
    public Integer getDgBQCStatus() {
        return dgBQCStatus;
    }

    public void setDgBQCStatus(Integer dgBQCStatus) {
        this.dgBQCStatus = dgBQCStatus;
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
    @JSONField(name = "AvgSP")
    public String getAvgSp() {
        return avgSp;
    }

    public void setAvgSp(String avgSp) {
        this.avgSp = avgSp;
    }
    @JSONField(name = "OptionSPF")
    public RedisBettingRateDTO getOptionSPF() {
        return optionSPF;
    }

    public void setOptionSPF(RedisBettingRateDTO optionSPF) {
        this.optionSPF = optionSPF;
    }
    @JSONField(name = "OptionRQSPF")
    public RedisBettingRateDTO getOptionRQSPF() {
        return optionRQSPF;
    }

    public void setOptionRQSPF(RedisBettingRateDTO optionRQSPF) {
        this.optionRQSPF = optionRQSPF;
    }
    @JSONField(name = "Hd")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(Date matchStartTime) {
        this.matchStartTime = matchStartTime;
    }
}
