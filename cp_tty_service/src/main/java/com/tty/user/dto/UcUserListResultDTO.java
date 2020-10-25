package com.tty.user.dto;

import java.util.List;

/**
 * Created by jdd on 2017/10/23.
 */
public class UcUserListResultDTO {
    private Long count;
    private List<UserBaseInfoDTO> userdata;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<UserBaseInfoDTO> getUserdata() {
        return userdata;
    }

    public void setUserdata(List<UserBaseInfoDTO> userdata) {
        this.userdata = userdata;
    }
}
