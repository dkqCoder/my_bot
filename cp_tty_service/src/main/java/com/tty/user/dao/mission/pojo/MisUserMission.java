package com.tty.user.dao.mission.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * @author shenwei
 * @date 2016-12-21
 * @version v1.0
 * 说明：表【mis_user_mission】实体类，由hibernateToolv1.0工具自动生成
 */
@Entity
@Table(name = "mis_user_mission")
public class MisUserMission implements java.io.Serializable {

    private Long missionId;
    private String missionName;
    private Integer missionTimes;
    private Integer missionType;
    private Integer missionCategory;
    private Integer missionCounts;
    private Integer missionIntegral;
    private Integer missionPriority;
    private String remark;
    private Integer missionAddType;
    private Date createDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_mission_id")
    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    @Column(name = "s_mission_name")
    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    @Column(name = "n_mission_times")
    public Integer getMissionTimes() {
        return missionTimes;
    }

    public void setMissionTimes(Integer missionTimes) {
        this.missionTimes = missionTimes;
    }

    @Column(name = "n_mission_type")
    public Integer getMissionType() {
        return missionType;
    }

    public void setMissionType(Integer missionType) {
        this.missionType = missionType;
    }

    @Column(name = "n_mission_category")
    public Integer getMissionCategory() {
        return missionCategory;
    }

    public void setMissionCategory(Integer missionCategory) {
        this.missionCategory = missionCategory;
    }

    @Column(name = "n_mission_counts")
    public Integer getMissionCounts() {
        return missionCounts;
    }

    public void setMissionCounts(Integer missionCounts) {
        this.missionCounts = missionCounts;
    }

    @Column(name = "n_mission_integral")
    public Integer getMissionIntegral() {
        return missionIntegral;
    }

    public void setMissionIntegral(Integer missionIntegral) {
        this.missionIntegral = missionIntegral;
    }

    @Column(name = "n_mission_priority")
    public Integer getMissionPriority() {
        return missionPriority;
    }

    public void setMissionPriority(Integer missionPriority) {
        this.missionPriority = missionPriority;
    }

    @Column(name = "s_remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "n_mission_add_type")
    public Integer getMissionAddType() {
        return missionAddType;
    }

    public void setMissionAddType(Integer missionAddType) {
        this.missionAddType = missionAddType;
    }

    @Column(name = "d_create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
