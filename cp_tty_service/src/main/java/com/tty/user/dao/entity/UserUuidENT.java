package com.tty.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author shenwei
 * @create 2017-12-25 14:20:52
 **/

@Entity
@Table(name = "user_uuid")
public class UserUuidENT implements Serializable {

    /**
     * user_id
     */
    private Long userId;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 渠道名
     */
    private String entranceCode;


    /**
     * 获取 user_id 的值
     *
     * @return Long
     */
    @Id
    @Column(name = "n_user_id")
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置user_id 的值
     *
     * @param userId user_id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取 uuid 的值
     *
     * @return String
     */
    @Column(name = "s_uuid")
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置uuid 的值
     *
     * @param uuid uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取 渠道名 的值
     *
     * @return String
     */
    @Column(name = "s_entrance_code")
    public String getEntranceCode() {
        return entranceCode;
    }

    /**
     * 设置渠道名 的值
     *
     * @param entranceCode 渠道名
     */
    public void setEntranceCode(String entranceCode) {
        this.entranceCode = entranceCode;
    }


}