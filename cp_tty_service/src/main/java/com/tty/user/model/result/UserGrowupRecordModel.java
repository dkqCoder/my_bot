package com.tty.user.model.result;/**
 * Created by shenwei on 2017/1/4.
 */

/**
 * @author shenwei
 * @create 2017-01-04
 */
public class UserGrowupRecordModel {
    private Long date;
    private String source;
    private String growups;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGrowups() {
        return growups;
    }

    public void setGrowups(String growups) {
        this.growups = growups;
    }
}
