package com.tty.user.model.result;/**
 * Created by shenwei on 2017/1/6.
 */

import java.util.List;

/**
 * @author shenwei
 * @create 2017-01-06
 * 用户中心VM
 */
public class UserLevelCenterModel {
    private Integer userlevel;
    private Long growups;
    private Long levelup;
    private String levelbeat;
    private String userface;
    private String growpercent;
    private Integer showdialog;
    private Integer dialoggiftid;

    private List<AuthorityModel> myauthority;
    private List<UpperAuthorityModel> upperauthority;

    public Integer getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(Integer userlevel) {
        this.userlevel = userlevel;
    }

    public Long getGrowups() {
        return growups;
    }

    public void setGrowups(Long growups) {
        this.growups = growups;
    }

    public Long getLevelup() {
        return levelup;
    }

    public void setLevelup(Long levelup) {
        this.levelup = levelup;
    }

    public String getLevelbeat() {
        return levelbeat;
    }

    public void setLevelbeat(String levelbeat) {
        this.levelbeat = levelbeat;
    }

    public List<AuthorityModel> getMyauthority() {
        return myauthority;
    }

    public void setMyauthority(List<AuthorityModel> myauthority) {
        this.myauthority = myauthority;
    }

    public List<UpperAuthorityModel> getUpperauthority() {
        return upperauthority;
    }

    public void setUpperauthority(List<UpperAuthorityModel> upperauthority) {
        this.upperauthority = upperauthority;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getGrowpercent() {
        return growpercent;
    }

    public void setGrowpercent(String growpercent) {
        this.growpercent = growpercent;
    }

    public Integer getShowdialog() {
        return showdialog;
    }

    public void setShowdialog(Integer showdialog) {
        this.showdialog = showdialog;
    }

    public Integer getDialoggiftid() {
        return dialoggiftid;
    }

    public void setDialoggiftid(Integer dialoggiftid) {
        this.dialoggiftid = dialoggiftid;
    }
}
