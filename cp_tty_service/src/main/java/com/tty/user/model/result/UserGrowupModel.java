package com.tty.user.model.result;/**
 * Created by shenwei on 2017/1/4.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-01-04
 */
public class UserGrowupModel {
    @JSONField(name = "growups")
    private Long growups;
    @JSONField(name = "userlevel")
    private Integer level;
    @JSONField(name = "levelup")
    private Long levelup;
    @JSONField(name = "levelbeat")
    private String levelbeat;
    @JSONField(name = "levelname")
    private String levelname;

    public Long getGrowups() {
        return growups;
    }

    public void setGrowups(Long growups) {
        this.growups = growups;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }
}
