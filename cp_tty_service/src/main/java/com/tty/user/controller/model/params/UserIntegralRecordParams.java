package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2016/12/14.
 */

/**
 * 用户积分明细请求参数
 * @author shenwei
 * @create 2016-12-14
 */
public class UserIntegralRecordParams {
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    private Integer pageSize;//每页数量
    private Integer pageIndex;//页码
    private Long lastId;
}
