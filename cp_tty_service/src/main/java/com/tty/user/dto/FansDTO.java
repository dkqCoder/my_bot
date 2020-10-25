package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author ln
 * */
public class FansDTO {
    @JSONField(name = "Detail")
    private List<UserFansDTO> detail;
    @JSONField(name = "Total")
    private Integer total;

    public List<UserFansDTO> getDetail() {
        return detail;
    }

    public void setDetail(List<UserFansDTO> detail) {
        this.detail = detail;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}


