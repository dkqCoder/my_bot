package com.tty.data.dao.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 **/

@Entity
@Table(name = "basedata_dg_match")
public class BasedataDgMatchENT implements java.io.Serializable {

    /** 主键 */
    private Integer id;

    /** 期次比赛编号（期次号+比赛编号） */
    private String issueMatchName;

    /** 主队名称 */
    private String hostTeam;

    /** 客队名称 */
    private String visitTeam;

    /** 玩法ID */
    private Integer playtypeId;

    /** 逗号隔开/1：让球胜平负，2：总进球，3：猜比分，4：半全场，6：胜平负 */
    private String ptypes;

    /** 推荐彩果 */
    private String recommResult;

    /** 支持率 */
    private String supportRate;

    /** 是否热门：1-是，0-否 */
    private Integer hotStatus;

    /** 推荐开始时间 */
    private Date recommBeginTime;

    /** 推荐结束时间 */
    private Date recommEndTime;

    /** 显示背景色 */
    private String bgImgUrl;

    /** 排序号 */
    private Integer order;

    /** 状态(1:启用,0:禁用) */
    private Integer status;

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
     * 获取 期次比赛编号（期次号+比赛编号） 的值
     * @return String 
     */
    @Column(name = "s_issue_match_name")
    public String  getIssueMatchName() {
        return issueMatchName;
    }

    /**
     * 设置期次比赛编号（期次号+比赛编号） 的值
     * @param issueMatchName 期次比赛编号（期次号+比赛编号）
     */
    public void setIssueMatchName(String issueMatchName) {
        this.issueMatchName = issueMatchName;
    }

    /**
     * 获取 主队名称 的值
     * @return String 
     */
    @Column(name = "s_host_team")
    public String  getHostTeam() {
        return hostTeam;
    }

    /**
     * 设置主队名称 的值
     * @param hostTeam 主队名称
     */
    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }

    /**
     * 获取 客队名称 的值
     * @return String 
     */
    @Column(name = "s_visit_team")
    public String  getVisitTeam() {
        return visitTeam;
    }

    /**
     * 设置客队名称 的值
     * @param visitTeam 客队名称
     */
    public void setVisitTeam(String visitTeam) {
        this.visitTeam = visitTeam;
    }

    /**
     * 获取 玩法ID 的值
     * @return Integer 
     */
    @Column(name = "n_playtype_id")
    public Integer  getPlaytypeId() {
        return playtypeId;
    }

    /**
     * 设置玩法ID 的值
     * @param playtypeId 玩法ID
     */
    public void setPlaytypeId(Integer playtypeId) {
        this.playtypeId = playtypeId;
    }

    /**
     * 获取 逗号隔开/1：让球胜平负，2：总进球，3：猜比分，4：半全场，6：胜平负 的值
     * @return String 
     */
    @Column(name = "s_ptypes")
    public String  getPtypes() {
        return ptypes;
    }

    /**
     * 设置逗号隔开/1：让球胜平负，2：总进球，3：猜比分，4：半全场，6：胜平负 的值
     * @param ptypes 逗号隔开/1：让球胜平负，2：总进球，3：猜比分，4：半全场，6：胜平负
     */
    public void setPtypes(String ptypes) {
        this.ptypes = ptypes;
    }

    /**
     * 获取 推荐彩果 的值
     * @return String 
     */
    @Column(name = "s_recomm_result")
    public String  getRecommResult() {
        return recommResult;
    }

    /**
     * 设置推荐彩果 的值
     * @param recommResult 推荐彩果
     */
    public void setRecommResult(String recommResult) {
        this.recommResult = recommResult;
    }

    /**
     * 获取 支持率 的值
     * @return String 
     */
    @Column(name = "s_support_rate")
    public String  getSupportRate() {
        return supportRate;
    }

    /**
     * 设置支持率 的值
     * @param supportRate 支持率
     */
    public void setSupportRate(String supportRate) {
        this.supportRate = supportRate;
    }

    /**
     * 获取 是否热门：1-是，0-否 的值
     * @return Integer 
     */
    @Column(name = "n_hot_status")
    public Integer  getHotStatus() {
        return hotStatus;
    }

    /**
     * 设置是否热门：1-是，0-否 的值
     * @param hotStatus 是否热门：1-是，0-否
     */
    public void setHotStatus(Integer hotStatus) {
        this.hotStatus = hotStatus;
    }

    /**
     * 获取 推荐开始时间 的值
     * @return Date 
     */
    @Column(name = "d_recomm_begin_time")
    public Date  getRecommBeginTime() {
        return recommBeginTime;
    }

    /**
     * 设置推荐开始时间 的值
     * @param recommBeginTime 推荐开始时间
     */
    public void setRecommBeginTime(Date recommBeginTime) {
        this.recommBeginTime = recommBeginTime;
    }

    /**
     * 获取 推荐结束时间 的值
     * @return Date 
     */
    @Column(name = "d_recomm_end_time")
    public Date  getRecommEndTime() {
        return recommEndTime;
    }

    /**
     * 设置推荐结束时间 的值
     * @param recommEndTime 推荐结束时间
     */
    public void setRecommEndTime(Date recommEndTime) {
        this.recommEndTime = recommEndTime;
    }

    /**
     * 获取 显示背景色 的值
     * @return String 
     */
    @Column(name = "s_bg_img_url")
    public String  getBgImgUrl() {
        return bgImgUrl;
    }

    /**
     * 设置显示背景色 的值
     * @param bgImgUrl 显示背景色
     */
    public void setBgImgUrl(String bgImgUrl) {
        this.bgImgUrl = bgImgUrl;
    }

    /**
     * 获取 排序号 的值
     * @return Integer 
     */
    @Column(name = "n_order")
    public Integer  getOrder() {
        return order;
    }

    /**
     * 设置排序号 的值
     * @param order 排序号
     */
    public void setOrder(Integer order) {
        this.order = order;
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