package com.tty.data.dto.redis;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * @Description
 */
public class RedisJczqMatchStopSaleDTO {

    private String reason = "";

    private String playDetailState;

    private Integer matchID;

    private String issueMatchName;

    private String issueName;

    private String issueNo;

    private String wkName;

    private String wk;

    private String mNo;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date buyEndTime;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPlayDetailState() {
        return playDetailState;
    }

    public void setPlayDetailState(String playDetailState) {
        this.playDetailState = playDetailState;
    }

    public Integer getMatchID() {
        return matchID;
    }

    public void setMatchID(Integer matchID) {
        this.matchID = matchID;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public String getWkName() {
        return wkName;
    }

    public void setWkName(String wkName) {
        this.wkName = wkName;
    }

    public String getWk() {
        return wk;
    }

    public void setWk(String wk) {
        this.wk = wk;
    }

    public String getmNo() {
        return mNo;
    }

    public void setmNo(String mNo) {
        this.mNo = mNo;
    }

    public Date getBuyEndTime() {
        return buyEndTime;
    }

    public void setBuyEndTime(Date buyEndTime) {
        this.buyEndTime = buyEndTime;
    }

    public String getIssueMatchName() {
        return issueMatchName;
    }

    public void setIssueMatchName(String issueMatchName) {
        this.issueMatchName = issueMatchName;
    }
}
