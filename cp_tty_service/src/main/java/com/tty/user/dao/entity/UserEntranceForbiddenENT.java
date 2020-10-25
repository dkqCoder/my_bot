package com.tty.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author zxh
 * @create 2017-07-28 10:23:53
 **/

@Entity
@Table(name = "user_entrance_forbidden")
public class UserEntranceForbiddenENT implements Serializable {

    /**
     * n_id
     */
    private Long id;

    /**
     * 渠道名称
     */
    private String cmdName;

    /**
     * s_province_name
     */
    private String provinceName;

    private String cityName;


    /**
     * 获取 n_id 的值
     *
     * @return Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    public Long getId() {
        return id;
    }

    /**
     * 设置n_id 的值
     *
     * @param id n_id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 渠道名称 的值
     *
     * @return String
     */
    @Column(name = "s_cmd_name")
    public String getCmdName() {
        return cmdName;
    }

    /**
     * 设置渠道名称 的值
     *
     * @param cmdName 渠道名称
     */
    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    /**
     * 获取 s_province_name 的值
     *
     * @return String
     */
    @Column(name = "s_province_name")
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * 设置s_province_name 的值
     *
     * @param provinceName s_province_name
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Column(name = "s_city_name")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}