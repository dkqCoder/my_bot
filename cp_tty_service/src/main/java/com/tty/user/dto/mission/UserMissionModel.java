package com.tty.user.dto.mission;/**
 * Created by shenwei on 2016/12/27.
 */

/**
 * @author shenwei
 * @create 2016-12-27
 */
public class UserMissionModel {
    private Long misId;
    private String misName;
    private String misDis;
    private Integer isEnd;
    private Integer isGet;
    private Integer misCategory;
    private Integer integralGet;
    private Integer buttonStatus;
    private String buttonText;
    private Integer misType;

    public Long getMisId() {
        return misId;
    }

    public void setMisId(Long misId) {
        this.misId = misId;
    }

    public String getMisName() {
        return misName;
    }

    public void setMisName(String misName) {
        this.misName = misName;
    }

    public String getMisDis() {
        return misDis;
    }

    public void setMisDis(String misDis) {
        this.misDis = misDis;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public Integer getIsGet() {
        return isGet;
    }

    public void setIsGet(Integer isGet) {
        this.isGet = isGet;
    }

    public Integer getMisCategory() {
        return misCategory;
    }

    public void setMisCategory(Integer misCategory) {
        this.misCategory = misCategory;
    }

    public Integer getIntegralGet() {
        return integralGet;
    }

    public void setIntegralGet(Integer integralGet) {
        this.integralGet = integralGet;
    }

    public Integer getButtonStatus() {
        return buttonStatus;
    }

    public void setButtonStatus(Integer buttonStatus) {
        this.buttonStatus = buttonStatus;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Integer getMisType() {
        return misType;
    }

    public void setMisType(Integer misType) {
        this.misType = misType;
    }
}
