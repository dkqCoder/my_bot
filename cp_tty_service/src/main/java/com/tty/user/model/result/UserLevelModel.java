package com.tty.user.model.result;/**
 * Created by shenwei on 2017/1/11.
 */

/**
 * @author shenwei
 * @create 2017-01-11
 */
public class UserLevelModel {
    private Long userid;
    private Integer userlevel;
    private String  levelname;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(Integer userlevel) {
        this.userlevel = userlevel;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }
}
