package com.tty.user.dto.mission;

import com.jdd.fm.core.model.ClientRequestHeader;

/**
 * @author zxh
 * @date 2017-5-3
 * 活动任务上报实体类
 */
public class ActivityMissionAddModel {
    private Long userId;
    private String traceId = "";
    /**绑定手机号 --81，
    绑定银行卡--82，
    新人注册--86
    */
    private Integer typeId;
    /**
     * 渠道id
     */
    private String entranceId;
    /**
     * 平台
     */
    private String platformId;
    private String dateTime;

    private String mobilPhone;
    /**uuid*/
    private String uuid;

    private ClientRequestHeader header;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getEntranceId() {
        return entranceId;
    }

    public void setEntranceId(String entranceId) {
        this.entranceId = entranceId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMobilPhone() {
        return mobilPhone;
    }

    public void setMobilPhone(String mobilPhone) {
        this.mobilPhone = mobilPhone;
    }

    public ClientRequestHeader getHeader() {
        return header;
    }

    public void setHeader(ClientRequestHeader header) {
        this.header = header;
    }
}
