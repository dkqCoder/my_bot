package com.tty.user.controller.model.result;/**
 * Created by shenwei on 2016/12/28.
 */

/**
 * @author shenwei
 * @create 2016-12-28
 */
public class MissionModel {
    private Integer integrals;
    private Integer integralsToAdd;
    private String description;
    private Integer buttonStatus;
    private String  buttonText;
    private String missName;

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

    public Integer getIntegrals() {
        return integrals;
    }

    public void setIntegrals(Integer integrals) {
        this.integrals = integrals;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIntegralsToAdd() {
        return integralsToAdd;
    }

    public void setIntegralsToAdd(Integer integralsToAdd) {
        this.integralsToAdd = integralsToAdd;
    }

    public String getMissName() {
        return missName;
    }

    public void setMissName(String missName) {
        this.missName = missName;
    }
}
