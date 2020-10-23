package com.tty.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ${zhangdi} on 2017/7/24.
 */
public class BaseDataIssueWinDetailDTO implements Serializable{
    private Long lotteryId;
    private String issueName;
    private Date endTime;
    private Integer issueId;
    private Integer wintypeId;
    private String wintypeName;
    private Integer wintypeOrder;
    private Integer wintypeStatus;
    /**
     * 默认奖金
     */
    private BigDecimal defaultWinmoney;

    /**
     * 默认税后奖金
     */
    private BigDecimal defaultWinmoneyNotax;

    /**
     * 获奖注数
     */
    private Integer winCount;
    public Long getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Long lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getWintypeId() {
        return wintypeId;
    }

    public void setWintypeId(Integer wintypeId) {
        this.wintypeId = wintypeId;
    }

    public String getWintypeName() {
        return wintypeName;
    }

    public void setWintypeName(String wintypeName) {
        this.wintypeName = wintypeName;
    }

    public Integer getWintypeOrder() {
        return wintypeOrder;
    }

    public void setWintypeOrder(Integer wintypeOrder) {
        this.wintypeOrder = wintypeOrder;
    }

    public Integer getWintypeStatus() {
        return wintypeStatus;
    }

    public void setWintypeStatus(Integer wintypeStatus) {
        this.wintypeStatus = wintypeStatus;
    }

    public BigDecimal getDefaultWinmoney() {
        return defaultWinmoney;
    }

    public void setDefaultWinmoney(BigDecimal defaultWinmoney) {
        this.defaultWinmoney = defaultWinmoney;
    }

    public BigDecimal getDefaultWinmoneyNotax() {
        return defaultWinmoneyNotax;
    }

    public void setDefaultWinmoneyNotax(BigDecimal defaultWinmoneyNotax) {
        this.defaultWinmoneyNotax = defaultWinmoneyNotax;
    }

    public Integer getWinCount() {
        return winCount;
    }

    public void setWinCount(Integer winCount) {
        this.winCount = winCount;
    }
}
