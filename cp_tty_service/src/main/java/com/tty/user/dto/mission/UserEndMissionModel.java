package com.tty.user.dto.mission;/**
 * Created by shenwei on 2016/12/21.
 */

import java.util.Date;

/**
 * @author shenwei
 * @create 2016-12-21
 */
public class UserEndMissionModel {
    private Long missionId;
    private String missionName;
    private Integer integrals;
    private Integer missionTimes;
    private String remark;
    private Long userId;
    private Integer isAdd;
    private Long id;
    private Integer isEnd;
    private Integer count;
    private Date createDate;

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public Integer getIntegrals() {
        return integrals;
    }

    public void setIntegrals(Integer integrals) {
        this.integrals = integrals;
    }

    public Integer getMissionTimes() {
        return missionTimes;
    }

    public void setMissionTimes(Integer missionTimes) {
        this.missionTimes = missionTimes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Integer isAdd) {
        this.isAdd = isAdd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
