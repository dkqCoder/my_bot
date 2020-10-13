package com.tty.data.dto;


import java.io.Serializable;
import java.util.Date;


public class BasedataIssueDTO implements Serializable{

    /** 期号ID */
    private Integer issueId;

    /** 彩种ID */
    private Integer lotteryId;

    /** 彩种名称 */
    private String lotteryName;
    /** 开奖号码标准格式 */
    private String winNumberTemplate;

    /** 期号 */
    private String issueName;

    /** 开奖号码（除竞彩外） */
    private String winNumber;

    /** 本期销量:本期滚存 */
    private String openResult;

    /** 过关状态(0:未过关;1:过关中;2:已过关) */
    private Integer passStatus;

    /** 追号任务是否执行状态 */
    private Integer chaseExecuteStatus;

    /** 是否套餐 */
    private Integer packageStatus;

    /** 是否开奖 */
    private Integer openStatus;

    /** 状态(1:启用,0:禁用) */
    private Integer status;


    private Date endTime;


    private Date startTime;


    private Date updateTime;

    private Date currTime = new Date();

    public String getWinNumberTemplate() {
        return winNumberTemplate;
    }

    public void setWinNumberTemplate(String winNumberTemplate) {
        this.winNumberTemplate = winNumberTemplate;
    }

    public Integer  getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer  getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String  getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String  getWinNumber() {
        return winNumber;
    }

    public void setWinNumber(String winNumber) {
        this.winNumber = winNumber;
    }

    public String  getOpenResult() {
        return openResult;
    }

    public void setOpenResult(String openResult) {
        this.openResult = openResult;
    }

    public Integer  getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(Integer passStatus) {
        this.passStatus = passStatus;
    }

    public Integer  getChaseExecuteStatus() {
        return chaseExecuteStatus;
    }

    public void setChaseExecuteStatus(Integer chaseExecuteStatus) {
        this.chaseExecuteStatus = chaseExecuteStatus;
    }

    public Integer  getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(Integer packageStatus) {
        this.packageStatus = packageStatus;
    }

    public Integer  getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Integer openStatus) {
        this.openStatus = openStatus;
    }

    public Integer  getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date  getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date  getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date  getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public Date getCurrTime() {
        return currTime;
    }

    public void setCurrTime(Date currTime) {
        this.currTime = currTime;
    }

    @Override
    public String toString() {
        return "BasedataIssueDTO{"+"issueId="+issueId+",lotteryId="+lotteryId+",issueName="+issueName+",winNumber="+winNumber+",openResult="+openResult+",passStatus="+passStatus+",chaseExecuteStatus="+chaseExecuteStatus+",packageStatus="+packageStatus+",openStatus="+openStatus+",status="+status+",endTime="+endTime+",startTime="+startTime+",updateTime="+updateTime+"}";
    }
}