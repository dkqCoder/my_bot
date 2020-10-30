package com.tty.data.dao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zxh
 * @create 2017-02-16 09:59:51
 **/

@Entity
@Table(name = "basedata_issue")
public class BasedataIssueENT implements java.io.Serializable {

    /** 期号ID */
    private Integer issueId;

    /** 彩种ID */
    private Integer lotteryId;

    /** 期号 */
    private String issueName = "";

    /** 开奖号码（除竞彩外） */
    private String winNumber = "";

    /** 本期销量:本期滚存 */
    private String openResult = "";

    /** 过关状态(0:未过关;1:过关中;2:已过关) */
    private Integer passStatus = 0;

    /** 追号任务是否执行状态 */
    private Integer chaseExecuteStatus = 0;

    /** 是否套餐 */
    private Integer packageStatus = 0;

    /** 是否开奖 */
    private Integer openStatus = 0;

    /** 审核状态(1:已审核,0:未审核) */
    private Integer auditStatus = 0;

    /** 截止时间 */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 开始投注时间 */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 更新时间 */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;




    /**
     * 获取 期号ID 的值
     * @return Integer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_issue_id")
    public Integer  getIssueId() {
        return issueId;
    }

    /**
     * 设置期号ID 的值
     * @param issueId 期号ID
     */
    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    /**
     * 获取 彩种ID 的值
     * @return Integer
     */
    @Column(name = "n_lottery_id")
    public Integer  getLotteryId() {
        return lotteryId;
    }

    /**
     * 设置彩种ID 的值
     * @param lotteryId 彩种ID
     */
    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    /**
     * 获取 期号 的值
     * @return String
     */
    @Column(name = "s_issue_name")
    public String  getIssueName() {
        return issueName;
    }

    /**
     * 设置期号 的值
     * @param issueName 期号
     */
    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    /**
     * 获取 开奖号码（除竞彩外） 的值
     * @return String
     */
    @Column(name = "s_win_number")
    public String  getWinNumber() {
        return winNumber;
    }

    /**
     * 设置开奖号码（除竞彩外） 的值
     * @param winNumber 开奖号码（除竞彩外）
     */
    public void setWinNumber(String winNumber) {
        this.winNumber = winNumber;
    }

    /**
     * 获取 本期销量:本期滚存 的值
     * @return String
     */
    @Column(name = "s_open_result")
    public String  getOpenResult() {
        return openResult;
    }

    /**
     * 设置本期销量:本期滚存 的值
     * @param openResult 本期销量:本期滚存
     */
    public void setOpenResult(String openResult) {
        this.openResult = openResult;
    }

    /**
     * 获取 过关状态(0:未过关;1:过关中;2:已过关) 的值
     * @return Integer
     */
    @Column(name = "n_pass_status")
    public Integer  getPassStatus() {
        return passStatus;
    }

    /**
     * 设置过关状态(0:未过关;1:过关中;2:已过关) 的值
     * @param passStatus 过关状态(0:未过关;1:过关中;2:已过关)
     */
    public void setPassStatus(Integer passStatus) {
        this.passStatus = passStatus;
    }

    /**
     * 获取 追号任务是否执行状态 的值
     * @return Integer
     */
    @Column(name = "n_chase_execute_status")
    public Integer  getChaseExecuteStatus() {
        return chaseExecuteStatus;
    }

    /**
     * 设置追号任务是否执行状态 的值
     * @param chaseExecuteStatus 追号任务是否执行状态
     */
    public void setChaseExecuteStatus(Integer chaseExecuteStatus) {
        this.chaseExecuteStatus = chaseExecuteStatus;
    }

    /**
     * 获取 是否套餐 的值
     * @return Integer
     */
    @Column(name = "n_package_status")
    public Integer  getPackageStatus() {
        return packageStatus;
    }

    /**
     * 设置是否套餐 的值
     * @param packageStatus 是否套餐
     */
    public void setPackageStatus(Integer packageStatus) {
        this.packageStatus = packageStatus;
    }

    /**
     * 获取 是否开奖 的值
     * @return Integer
     */
    @Column(name = "n_open_status")
    public Integer  getOpenStatus() {
        return openStatus;
    }

    /**
     * 设置是否开奖 的值
     * @param openStatus 是否开奖
     */
    public void setOpenStatus(Integer openStatus) {
        this.openStatus = openStatus;
    }

    /**
     * 获取 审核状态(1:已审核,0:未审核) 的值
     * @return Integer
     */
    @Column(name = "n_audit_status")
    public Integer  getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置审核状态(1:已审核,0:未审核) 的值
     * @param auditStatus 审核状态(1:已审核,0:未审核)
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 获取 截止时间 的值
     * @return Date
     */
    @Column(name = "d_end_time")
    public Date  getEndTime() {
        return endTime;
    }

    /**
     * 设置截止时间 的值
     * @param endTime 截止时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 开始投注时间 的值
     * @return Date
     */
    @Column(name = "d_start_time")
    public Date  getStartTime() {
        return startTime;
    }

    /**
     * 设置开始投注时间 的值
     * @param startTime 开始投注时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取 更新时间 的值
     * @return Date
     */
    @Column(name = "d_update_time")
    public Date  getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间 的值
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }




}