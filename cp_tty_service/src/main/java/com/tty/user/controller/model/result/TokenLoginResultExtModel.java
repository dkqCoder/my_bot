package com.tty.user.controller.model.result;

/**
 * Created by jdd on 2017/7/7.
 */
public class TokenLoginResultExtModel extends TokenLoginResult {
    private String number;
    private String issueNumber;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }
}
