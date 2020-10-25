package com.tty.user.controller.model.params;

/** 根据条件筛选大神
 * @author zxh
 * @create 2017-03-29
 */
public class SearchMasterParam {
    private String nikeName;
    private Integer pageNum;
    private Integer pageSize;

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
