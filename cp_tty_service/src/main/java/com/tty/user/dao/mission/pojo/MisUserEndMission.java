package com.tty.user.dao.mission.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * @author shenwei
 * @date 2016-12-23
 * @version v1.0
 * 说明：表【mis_user_end_mission】实体类，由hibernateToolv1.0工具自动生成
 */
@Entity
@Table(name = "mis_user_end_mission")
public class MisUserEndMission implements java.io.Serializable {

    private Long id;
    private Long missionId;
    private Integer isEnd;
    private Date createDate;
    private Integer isAdd;
    private Integer count;
    private Long userid;
    private Integer integrals;

    @Id
    @Column(name = "n_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "n_mission_id")
    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    @Column(name = "n_is_end")
    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    @Column(name = "d_create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "n_is_add")
    public Integer getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Integer isAdd) {
        this.isAdd = isAdd;
    }

    @Column(name = "n_count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Column(name = "n_userid")
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Column(name = "n_integrals")
    public Integer getIntegrals() {
        return integrals;
    }

    public void setIntegrals(Integer integrals) {
        this.integrals = integrals;
    }
}
