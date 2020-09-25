package com.tty.ticket.dto;

import java.math.BigDecimal;

public class TicketPrintSpDTO {

    /**
     * 期次比赛号
     */
    private String issueMatchName;

    /**
     * 玩法id
     */
    private Integer playtypeId;

    /**
     * 玩法code
     */
    private String playtypeCode;

    /**
     * 选项
     */
    private String option;

    /**
     * 选项比分（篮球让分，大小分）
     */
    private BigDecimal optionScore;

    /**
     * 赔率值
     */
    private BigDecimal sp;

    public String getIssueMatchName() {
        return issueMatchName;
    }

    public void setIssueMatchName(String issueMatchName) {
        this.issueMatchName = issueMatchName;
    }

    public Integer getPlaytypeId() {
        return playtypeId;
    }

    public void setPlaytypeId(Integer playtypeId) {
        this.playtypeId = playtypeId;

        switch (playtypeId) {
            case 9001:
                playtypeCode = "RSP";
                break;
            case 9002:
                playtypeCode = "JQS";
                break;
            case 9003:
                playtypeCode = "CBF";
                break;
            case 9004:
                playtypeCode = "BQC";
                break;
            case 9005:
                playtypeCode = "HHGG";
                break;
            case 9006:
                playtypeCode = "SPF";
                break;
            case 9101:
                playtypeCode = "RSF";
                break;
            case 9102:
                playtypeCode = "SFC";
                break;
            case 9103:
                playtypeCode = "SFD";
                break;
            case 9104:
                playtypeCode = "DXF";
                break;
            case 9105:
                playtypeCode = "HHGG";
                break;
            default:
                playtypeCode = "";
                break;
        }
    }

    public String getPlaytypeCode() {
        return playtypeCode;
    }

    public void setPlaytypeCode(String playtypeCode) {
        this.playtypeCode = playtypeCode;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public BigDecimal getOptionScore() {
        return optionScore;
    }

    public void setOptionScore(BigDecimal optionScore) {
        this.optionScore = optionScore;
    }

    public BigDecimal getSp() {
        return sp;
    }

    public void setSp(BigDecimal sp) {
        this.sp = sp;
    }

}