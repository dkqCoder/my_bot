package com.tty.data.dto;

import java.math.BigDecimal;

/**
 * Created by rxu on 2017/12/1.
 */
public class BuyQuantityErrorDetailDTO {
    /**
     * 赛果
     */
    private String matchResult;
    /**
     * 赔率
     */
    private String sp;

    /**
     * 概率
     */
    private BigDecimal probability;

    /**
     * 买量分布 Buy quantity distribution
     */
    private BigDecimal bqd;
    /**
     * 误差
     */
    private BigDecimal deviation;

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public BigDecimal getBqd() {
        return bqd;
    }

    public void setBqd(BigDecimal bqd) {
        this.bqd = bqd;
    }

    public BigDecimal getDeviation() {
        return deviation;
    }

    public void setDeviation(BigDecimal deviation) {
        this.deviation = deviation;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }
}
