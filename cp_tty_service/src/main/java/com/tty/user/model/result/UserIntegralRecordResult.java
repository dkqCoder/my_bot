package com.tty.user.model.result;/**
 * Created by shenwei on 2016/12/16.
 */

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author shenwei
 * @create 2016-12-16
 * 收支明细vm
 */
public class UserIntegralRecordResult {
    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public List<UserIntegralRecordModel> getDetails() {
        return details;
    }

    public void setDetails(List<UserIntegralRecordModel> details) {
        this.details = details;
    }

    public Long getSysdate() {
        return sysdate;
    }

    public void setSysdate(Long sysdate) {
        this.sysdate = sysdate;
    }

    @JSONField(name="integral")
    private Integer integral;
    @JSONField(name="details")
    private List<UserIntegralRecordModel> details;
    @JSONField(name="sysdate")
    private Long sysdate;
}
