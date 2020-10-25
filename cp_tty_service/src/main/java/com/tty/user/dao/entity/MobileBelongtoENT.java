package com.tty.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author zxh
 * @create 2017-07-28 10:23:53
 **/

@Entity
@Table(name = "mobile_belongto")
public class MobileBelongtoENT implements Serializable {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 手机号码段
     */
    private String mobileSegment ;

    /**
     * 省份
     */
    private String provinceName;

    /**
     * 城市名
     */
    private String cityName ;

    /**
     * utf8
     */
    private String type;


    /**
     * 获取 主键ID 的值
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
     * 设置主键ID 的值
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 手机号码段 的值
     *
     * @return String
     */
    @Column(name = "s_mobile_segment")
    public String getMobileSegment() {
        return mobileSegment;
    }

    /**
     * 设置手机号码段 的值
     *
     * @param mobileSegment 手机号码段
     */
    public void setMobileSegment(String mobileSegment) {
        this.mobileSegment = mobileSegment;
    }

    /**
     * 获取 省份 的值
     *
     * @return String
     */
    @Column(name = "s_province_name")
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * 设置省份 的值
     *
     * @param provinceName 省份
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * 获取 城市名 的值
     *
     * @return String
     */
    @Column(name = "s_city_name")
    public String getCityName() {
        return cityName;
    }

    /**
     * 设置城市名 的值
     *
     * @param cityName 城市名
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * 获取 utf8 的值
     *
     * @return String
     */
    @Column(name = "s_type")
    public String getType() {
        return type;
    }

    /**
     * 设置utf8 的值
     *
     * @param type utf8
     */
    public void setType(String type) {
        this.type = type;
    }


}