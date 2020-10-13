package com.tty.data.dto;

import java.io.Serializable;

/**
 * Created by jdd on 2017/4/20.
 */
public class BasedataPayTypeListDTO implements Serializable {
    /** 主键  id,FunctionCode,functionName,merchantNumber,md5key */
    private Integer id;

    /** 支付名称 */
    private String name;
    /** value=  id,FunctionCode,functionName,merchantNumber,md5key */
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
