package com.tty.user.model.params;/**
 * Created by shenwei on 2017/8/16.
 */

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Date;

/**
 * @author shenwei
 * @create 2017-08-16
 */
public class EntranceCountParams {
    private String cmdNames = "";
    private Date startDate;
    private Date endDate;

    @JSONField(name = "cmdNames")
    public String getCmdNames() {
        return cmdNames;
    }

    @JSONField(name = "cmdNames")
    public void setCmdNames(String cmdNames) {
        this.cmdNames = cmdNames;
    }

    @JSONField(name = "startCreateTime")
    public Date getStartDate() {
        return startDate;
    }

    @JSONField(name = "startCreateTime")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JSONField(name = "endCreateTime")
    public Date getEndDate() {
        return endDate;
    }

    @JSONField(name = "endCreateTime")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
