package com.tty.data.controller.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

/**
 * @author zhangdi
 * @date 17/4/13
 * @Description
 */
public class ClientJczqDggpMatchVO {
    private Integer matchId;//赛事ID
    private Integer issueNo;//比赛编号
    private String issueName;
    private String dayOfWeek;//比赛名称
    private String dayNumber;
    private String matchTitle;
    private String matchName;
    private Long endTime;//投注截止时间
    private Long matchTime;
    private String rq;//让球数量
    private Integer playID;
    private Double winRate;//欧盘胜率
    private Double drawRate;//欧盘平率
    private Double loseRate;//欧盘负率
    private String des;//赛事描述
    private TeamInfo hostTeam;//主队名称
    private TeamInfo vistorTeam;//客队名称

    private SpVo spSPF;//胜平负SP
    private SpVo spRQSPF;//让球胜平负SP
    private SpVo spZJQ;//总进球SP
    private SpVo spCBF;//猜比分SP
    private SpVo spBQC;//猜比分SP

    private String img;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(Integer issueNo) {
        this.issueNo = issueNo;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Long matchTime) {
        this.matchTime = matchTime;
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq;
    }

    public Integer getPlayID() {
        return playID;
    }

    public void setPlayID(Integer playID) {
        this.playID = playID;
    }

    public Double getWinRate() {
        return winRate;
    }

    public void setWinRate(Double winRate) {
        this.winRate = winRate;
    }

    public Double getDrawRate() {
        return drawRate;
    }

    public void setDrawRate(Double drawRate) {
        this.drawRate = drawRate;
    }

    public Double getLoseRate() {
        return loseRate;
    }

    public void setLoseRate(Double loseRate) {
        this.loseRate = loseRate;
    }
    @JSONField(name="HisDescription")
    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
    @JSONField(name="homeTeam")
    public TeamInfo getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(TeamInfo hostTeam) {
        this.hostTeam = hostTeam;
    }
    @JSONField(name="awayTeam")
    public TeamInfo getVistorTeam() {
        return vistorTeam;
    }

    public void setVistorTeam(TeamInfo vistorTeam) {
        this.vistorTeam = vistorTeam;
    }
    @JSONField(name="SPSPF")
    public SpVo getSpSPF() {
        return spSPF;
    }

    public void setSpSPF(SpVo spSPF) {
        this.spSPF = spSPF;
    }
    @JSONField(name="SPRQS")
    public SpVo getSpRQSPF() {
        return spRQSPF;
    }

    public void setSpRQSPF(SpVo spRQSPF) {
        this.spRQSPF = spRQSPF;
    }
    @JSONField(name="SPJQS")
    public SpVo getSpZJQ() {
        return spZJQ;
    }

    public void setSpZJQ(SpVo spZJQ) {
        this.spZJQ = spZJQ;
    }
    @JSONField(name="SPCBF")
    public SpVo getSpCBF() {
        return spCBF;
    }

    public void setSpCBF(SpVo spCBF) {
        this.spCBF = spCBF;
    }
    @JSONField(name="SPBQC")
    public SpVo getSpBQC() {
        return spBQC;
    }

    public void setSpBQC(SpVo spBQC) {
        this.spBQC = spBQC;
    }
    @JSONField(name="Img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static class TeamInfo{
        private String name;
        private Double offer;
        private Integer rank;
        private String title;
        private Integer id;
        private HistoryZj history;
        private List<InjureInfoVO> injure;

        public List<InjureInfoVO> getInjure() {
            return injure;
        }

        public void setInjure(List<InjureInfoVO> injure) {
            this.injure = injure;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getOffer() {
            return offer;
        }

        public void setOffer(Double offer) {
            this.offer = offer;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public HistoryZj getHistory() {
            return history;
        }

        public void setHistory(HistoryZj history) {
            this.history = history;
        }
    }
    public static class HistoryZj{
        private Integer win;
        private Integer draw;
        private Integer lose;

        public Integer getWin() {
            return win;
        }

        public void setWin(Integer win) {
            this.win = win;
        }

        public Integer getDraw() {
            return draw;
        }

        public void setDraw(Integer draw) {
            this.draw = draw;
        }

        public Integer getLose() {
            return lose;
        }

        public void setLose(Integer lose) {
            this.lose = lose;
        }
    }
    public static class InjureInfoVO{
        private boolean main;//是否主力，true：是
        private String player;//球员名称
        private String reason;//伤停原因
        @JSONField(name="isMain")
        public boolean isMain() {
            return main;
        }

        public void setMain(boolean main) {
            this.main = main;
        }
        @JSONField(name="player")
        public String getPlayer() {
            return player;
        }

        public void setPlayer(String player) {
            this.player = player;
        }
        @JSONField(name="reason")
        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    public static class SpVo{
        private String odds;
        private Integer state;

        public String getOdds() {
            return odds;
        }

        public void setOdds(String odds) {
            this.odds = odds;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }
    }
}
