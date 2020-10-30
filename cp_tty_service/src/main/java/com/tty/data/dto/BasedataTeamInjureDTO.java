package com.tty.data.dto;/**
 * Created by shenwei on 2017/3/8.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-08
 */
public class BasedataTeamInjureDTO {
    @JSONField(name = "ID")
    private Integer id;
    @JSONField(name = "IssueNo")
    private String issueMatchName;
    @JSONField(name = "IsHTeam")
    private Boolean isHostTeam;
    @JSONField(name = "PlayerName")
    private String playerName;
    @JSONField(name = "IsMain")
    private Boolean main;
    @JSONField(name = "Reason")
    private String reason;
    @JSONField(name = "Status")
    private Boolean status;
    @JSONField(name = "PlayerNameEN")
    private String playerNameEn;
    @JSONField(name = "ReasonEN")
    private String reasonEn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssueMatchName() {
        return issueMatchName;
    }

    public void setIssueMatchName(String issueMatchName) {
        this.issueMatchName = issueMatchName;
    }

    public Boolean getHostTeam() {
        return isHostTeam;
    }

    public void setHostTeam(Boolean hostTeam) {
        isHostTeam = hostTeam;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPlayerNameEn() {
        return playerNameEn;
    }

    public void setPlayerNameEn(String playerNameEn) {
        this.playerNameEn = playerNameEn;
    }

    public String getReasonEn() {
        return reasonEn;
    }

    public void setReasonEn(String reasonEn) {
        this.reasonEn = reasonEn;
    }
}
