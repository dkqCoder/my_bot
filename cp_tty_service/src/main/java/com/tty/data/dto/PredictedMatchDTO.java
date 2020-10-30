package com.tty.data.dto;

import com.jdd.basedata.vo.ClientPlayOption;

/**
 * Created by rxu on 2017/11/24.
 */
public class PredictedMatchDTO {
    private Integer matchId;
    private String matchTime;
    private String homeTeamId;
    private String homeTeamName;
    private String awayTeamId;
    private String awayTeamName;
    private String uniqueTournamentName;
    private String winWdl;
    private String drawWdl;
    private String lossWdl;
    private String winPercent;
    private String drawPercent;
    private String lossPercent;
    private String winRecommend;
    private String drawRecommend;
    private String lossRecommend;
    private String recommendPercent;
    private String isHit;
    private String isFinish;
    private String matchResult;
    private String jzNum;
    private String score90;
    private String jzWeek;
    private String mid;
    private Integer rq;

    private boolean buyStatus = false;

    private ClientPlayOption playOption;

    public Integer getRq() {
        return rq;
    }

    public void setRq(Integer rq) {
        this.rq = rq;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(String homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(String awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getUniqueTournamentName() {
        return uniqueTournamentName;
    }

    public void setUniqueTournamentName(String uniqueTournamentName) {
        this.uniqueTournamentName = uniqueTournamentName;
    }

    public String getWinWdl() {
        return winWdl;
    }

    public void setWinWdl(String winWdl) {
        this.winWdl = winWdl;
    }

    public String getDrawWdl() {
        return drawWdl;
    }

    public void setDrawWdl(String drawWdl) {
        this.drawWdl = drawWdl;
    }

    public String getLossWdl() {
        return lossWdl;
    }

    public void setLossWdl(String lossWdl) {
        this.lossWdl = lossWdl;
    }

    public String getWinPercent() {
        return winPercent;
    }

    public void setWinPercent(String winPercent) {
        this.winPercent = winPercent;
    }

    public String getDrawPercent() {
        return drawPercent;
    }

    public void setDrawPercent(String drawPercent) {
        this.drawPercent = drawPercent;
    }

    public String getLossPercent() {
        return lossPercent;
    }

    public void setLossPercent(String lossPercent) {
        this.lossPercent = lossPercent;
    }

    public String getWinRecommend() {
        return winRecommend;
    }

    public void setWinRecommend(String winRecommend) {
        this.winRecommend = winRecommend;
    }

    public String getDrawRecommend() {
        return drawRecommend;
    }

    public void setDrawRecommend(String drawRecommend) {
        this.drawRecommend = drawRecommend;
    }

    public String getLossRecommend() {
        return lossRecommend;
    }

    public void setLossRecommend(String lossRecommend) {
        this.lossRecommend = lossRecommend;
    }

    public String getRecommendPercent() {
        return recommendPercent;
    }

    public void setRecommendPercent(String recommendPercent) {
        this.recommendPercent = recommendPercent;
    }

    public String getIsHit() {
        return isHit;
    }

    public void setIsHit(String isHit) {
        this.isHit = isHit;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public String getJzNum() {
        return jzNum;
    }

    public void setJzNum(String jzNum) {
        this.jzNum = jzNum;
    }

    public String getScore90() {
        return score90;
    }

    public void setScore90(String score90) {
        this.score90 = score90;
    }

    public String getJzWeek() {
        return jzWeek;
    }

    public void setJzWeek(String jzWeek) {
        this.jzWeek = jzWeek;
    }

    public ClientPlayOption getPlayOption() {
        return playOption;
    }

    public void setPlayOption(ClientPlayOption playOption) {
        this.playOption = playOption;
    }

    public boolean isBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(boolean buyStatus) {
        this.buyStatus = buyStatus;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
