package com.tty.user.controller.model.params;


/**
 * @author  ln
 * 关注/取消关注
 */
public class FansParams {
    //被查看用户id
    private Long userid;
    //页码
    private Integer pageno;
    //每页数量
    private Integer pagesize;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
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
}
