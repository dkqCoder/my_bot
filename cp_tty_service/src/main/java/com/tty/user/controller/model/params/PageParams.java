package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/1/4.
 */

/**
 * @author shenwei
 * @create 2017-01-04
 * 分页查询参数
 */
public class PageParams {
    private int pageIndex;
    private int pageSize;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
