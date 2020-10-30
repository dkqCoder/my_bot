package com.tty.data.dto;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 */
public class ClientDgMatchVO {
    private Integer matchId;//赛事ID
    private Integer mId;//比赛编号
    private String matchName;//比赛名称
    private Date endTime;//投注截止时间
    private String hostTeam;//主队名称
    private String vistorTeam;//客队名称
    private String rq;//让球值
    private String spSPF;//胜平负SP
    private String spRQSPF;//让球胜平负SP
    private String spZJQ;//总进球SP
    private String spCBF;//猜比分SP
    private String spBQC;//半全场SP
    private String imgUrl;//图片地址
    private boolean isUse;//是否可用
    @JSONField(name = "MatchID")
    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }
    @JSONField(name = "MID")
    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
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
    @JSONField(name = "ImgUrl")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    @JSONField(name = "IsUse")
    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        isUse = use;
    }
}
