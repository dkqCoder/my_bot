package com.tty.data.dto;

import java.io.Serializable;

/**
 * 高频彩可追号期次
 * Created by jdd on 2017/6/28.
 */
public class BasedataChaseDTO implements Serializable{

    /** 期次ID */
    private Integer issueId;

    /** 期次名称 */
    private String issueName = "";

    private int days;

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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
