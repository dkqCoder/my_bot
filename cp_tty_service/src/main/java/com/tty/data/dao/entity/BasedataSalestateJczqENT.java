package com.tty.data.dao.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @create 2017-02-28 10:42:48
 **/

@Entity
@Table(name = "basedata_salestate_jczq")
public class BasedataSalestateJczqENT implements java.io.Serializable {

    /** 主键 */
    private Integer id;

    /** 期次比赛表 */
    private String issueMatchName;

    /** 停售原因 */
    private String stopSaleReason;

    /** n_bqc_status */
    private Integer bqcStatus;

    /** n_cbf_status */
    private Integer cbfStatus;

    /** n_jqs_status */
    private Integer jqsStatus;

    /** n_rqspf_status */
    private Integer rqspfStatus;

    /** n_spf_status */
    private Integer spfStatus;

    /** 状态(1:启用,0:禁用) */
    private Integer status;

    /** 单关半全场 */
    private Integer dgBqcStatus;

    /** 单关猜比分 */
    private Integer dgCbfStatus;

    /** 单关进球数 */
    private Integer dgJqsStatus;

    /** 单关让球胜平负 */
    private Integer dgRqspfStatus;

    /** 单关胜平负 */
    private Integer dgSpfStatus;

    /** 玩法选项详细状态 */
    private String detailState;

    /** 创建时间 */
    private Date createTime;




    /**
     * 获取 主键 的值
     * @return Integer 
     */
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    public Integer  getId() {
        return id;
    }

    /**
     * 设置主键 的值
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取 期次比赛表 的值
     * @return String 
     */
    @Column(name = "s_issue_match_name")
    public String  getIssueMatchName() {
        return issueMatchName;
    }

    /**
     * 设置期次比赛表 的值
     * @param issueMatchName 期次比赛表
     */
    public void setIssueMatchName(String issueMatchName) {
        this.issueMatchName = issueMatchName;
    }

    /**
     * 获取 停售原因 的值
     * @return String 
     */
    @Column(name = "s_stop_sale_reason")
    public String  getStopSaleReason() {
        return stopSaleReason;
    }

    /**
     * 设置停售原因 的值
     * @param stopSaleReason 停售原因
     */
    public void setStopSaleReason(String stopSaleReason) {
        this.stopSaleReason = stopSaleReason;
    }

    /**
     * 获取 n_bqc_status 的值
     * @return Integer 
     */
    @Column(name = "n_bqc_status")
    public Integer  getBqcStatus() {
        return bqcStatus;
    }

    /**
     * 设置n_bqc_status 的值
     * @param bqcStatus n_bqc_status
     */
    public void setBqcStatus(Integer bqcStatus) {
        this.bqcStatus = bqcStatus;
    }

    /**
     * 获取 n_cbf_status 的值
     * @return Integer 
     */
    @Column(name = "n_cbf_status")
    public Integer  getCbfStatus() {
        return cbfStatus;
    }

    /**
     * 设置n_cbf_status 的值
     * @param cbfStatus n_cbf_status
     */
    public void setCbfStatus(Integer cbfStatus) {
        this.cbfStatus = cbfStatus;
    }

    /**
     * 获取 n_jqs_status 的值
     * @return Integer 
     */
    @Column(name = "n_jqs_status")
    public Integer  getJqsStatus() {
        return jqsStatus;
    }

    /**
     * 设置n_jqs_status 的值
     * @param jqsStatus n_jqs_status
     */
    public void setJqsStatus(Integer jqsStatus) {
        this.jqsStatus = jqsStatus;
    }

    /**
     * 获取 n_rqspf_status 的值
     * @return Integer 
     */
    @Column(name = "n_rqspf_status")
    public Integer  getRqspfStatus() {
        return rqspfStatus;
    }

    /**
     * 设置n_rqspf_status 的值
     * @param rqspfStatus n_rqspf_status
     */
    public void setRqspfStatus(Integer rqspfStatus) {
        this.rqspfStatus = rqspfStatus;
    }

    /**
     * 获取 n_spf_status 的值
     * @return Integer 
     */
    @Column(name = "n_spf_status")
    public Integer  getSpfStatus() {
        return spfStatus;
    }

    /**
     * 设置n_spf_status 的值
     * @param spfStatus n_spf_status
     */
    public void setSpfStatus(Integer spfStatus) {
        this.spfStatus = spfStatus;
    }

    /**
     * 获取 状态(1:启用,0:禁用) 的值
     * @return Integer 
     */
    @Column(name = "n_status")
    public Integer  getStatus() {
        return status;
    }

    /**
     * 设置状态(1:启用,0:禁用) 的值
     * @param status 状态(1:启用,0:禁用)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 单关半全场 的值
     * @return Integer 
     */
    @Column(name = "n_dg_bqc_status")
    public Integer  getDgBqcStatus() {
        return dgBqcStatus;
    }

    /**
     * 设置单关半全场 的值
     * @param dgBqcStatus 单关半全场
     */
    public void setDgBqcStatus(Integer dgBqcStatus) {
        this.dgBqcStatus = dgBqcStatus;
    }

    /**
     * 获取 单关猜比分 的值
     * @return Integer 
     */
    @Column(name = "n_dg_cbf_status")
    public Integer  getDgCbfStatus() {
        return dgCbfStatus;
    }

    /**
     * 设置单关猜比分 的值
     * @param dgCbfStatus 单关猜比分
     */
    public void setDgCbfStatus(Integer dgCbfStatus) {
        this.dgCbfStatus = dgCbfStatus;
    }

    /**
     * 获取 单关进球数 的值
     * @return Integer 
     */
    @Column(name = "n_dg_jqs_status")
    public Integer  getDgJqsStatus() {
        return dgJqsStatus;
    }

    /**
     * 设置单关进球数 的值
     * @param dgJqsStatus 单关进球数
     */
    public void setDgJqsStatus(Integer dgJqsStatus) {
        this.dgJqsStatus = dgJqsStatus;
    }

    /**
     * 获取 单关让球胜平负 的值
     * @return Integer 
     */
    @Column(name = "n_dg_rqspf_status")
    public Integer  getDgRqspfStatus() {
        return dgRqspfStatus;
    }

    /**
     * 设置单关让球胜平负 的值
     * @param dgRqspfStatus 单关让球胜平负
     */
    public void setDgRqspfStatus(Integer dgRqspfStatus) {
        this.dgRqspfStatus = dgRqspfStatus;
    }

    /**
     * 获取 单关胜平负 的值
     * @return Integer 
     */
    @Column(name = "n_dg_spf_status")
    public Integer  getDgSpfStatus() {
        return dgSpfStatus;
    }

    /**
     * 设置单关胜平负 的值
     * @param dgSpfStatus 单关胜平负
     */
    public void setDgSpfStatus(Integer dgSpfStatus) {
        this.dgSpfStatus = dgSpfStatus;
    }

    /**
     * 获取 玩法选项详细状态 的值
     * @return String 
     */
    @Column(name = "s_detail_state")
    public String  getDetailState() {
        return detailState;
    }

    /**
     * 设置玩法选项详细状态 的值
     * @param detailState 玩法选项详细状态
     */
    public void setDetailState(String detailState) {
        this.detailState = detailState;
    }

    /**
     * 获取 创建时间 的值
     * @return Date 
     */
    @Column(name = "d_create_time")
    public Date  getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间 的值
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}