package com.tty.user.controller.model.params;


/**
 * @author zxh
 * 关注/取消关注
 */
public class AttentionOperationParams {
    /**attentionuserid:关注的用户ID*/
    private Long attentionuserid;
    /**type:1-关注，0-取消关注*/
    private Integer type;

    public Long getAttentionuserid() {
        return attentionuserid;
    }

    public void setAttentionuserid(Long attentionuserid) {
        this.attentionuserid = attentionuserid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
