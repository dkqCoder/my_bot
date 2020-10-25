package com.tty.user.dto;


import java.util.List;

public class SearchMasterDTO {


    private Long totalNum;

    private Long totalPage;

    private List<UserMasterInfoDTO> list;


    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public List<UserMasterInfoDTO> getList() {
        return list;
    }

    public void setList(List<UserMasterInfoDTO> list) {
        this.list = list;
    }
}