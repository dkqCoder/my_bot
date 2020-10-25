package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;


/**
 * @author zxh
 */
public class AttentionDTO {
    @JSONField(name = "Detail")
    private List<UserAttentionInfoDTO> detail;
    @JSONField(name = "Total")
    private Integer total;

    public List<UserAttentionInfoDTO> getDetail() {
        return detail;
    }

    public void setDetail(List<UserAttentionInfoDTO> detail) {
        this.detail = detail;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}


