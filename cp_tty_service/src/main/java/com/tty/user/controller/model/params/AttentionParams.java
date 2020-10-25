package com.tty.user.controller.model.params;


/**
 * @author zxh
 * 关注/取消关注
 */
public class AttentionParams {
    //查看人ID
    private Long userId;
    //页码
    private Integer pageno;
    //每页数量
    private Integer pagesize;
    //被查看人ID
    private Long queryUserId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPageno() {
        return pageno;
    }

    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Long getQueryUserId() {
        return queryUserId;
    }

    public void setQueryUserId(Long queryUserId) {
        this.queryUserId = queryUserId;
    }
}
