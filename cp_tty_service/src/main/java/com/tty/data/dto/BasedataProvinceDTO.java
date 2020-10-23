package com.tty.data.dto;


import java.io.Serializable;

public class BasedataProvinceDTO implements Serializable {

    /** 主键 */
    private Integer id;

    /** 省份名称 */
    private String name;

    /** 行政区域代码 */
    private String regionCode;




    public Integer  getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String  getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }



    @Override
    public String toString() {
        return "BasedataProvinceDTO{"+"id="+id+",name="+name+",regionCode="+regionCode+"}";
    }
}