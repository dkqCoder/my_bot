package com.tty.user.model.result;/**
 * Created by shenwei on 2016/12/16.
 */

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shenwei
 * @create 2016-12-16
 */
public class UserIntegralRecordModel implements Serializable {

    @JSONField(name = "id")
    private Long id;
    @JSONField(name = "date")
    private Long date;
    @JSONField(name = "dis")
    private String discription;
    @JSONField(name = "integral")
    private String integralChange;


    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getIntegralChange() {
        return integralChange;
    }

    public void setIntegralChange(String integralChange) {
        this.integralChange = integralChange;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
