package com.tty.data.dto;


import java.io.Serializable;

public class BasedataCityDTO implements Serializable {

    /** 主键 */
    private Integer cityId;

    /** 省份ID */
    private Integer provinceId;

    /** 城市名称 */
    private String cityName;

    /** s_city_code */
    private String cityCode;

    /** 行政区域代码 */
    private String regionCode;




    public Integer  getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer  getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String  getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String  getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String  getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }



    @Override
    public String toString() {
        return "BasedataCityDTO{"+"cityId="+cityId+",provinceId="+provinceId+",cityName="+cityName+",cityCode="+cityCode+",regionCode="+regionCode+"}";
    }
}