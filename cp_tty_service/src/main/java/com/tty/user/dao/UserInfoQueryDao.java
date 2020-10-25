package com.tty.user.dao;

import com.tty.user.controller.model.params.UserListParam;
import com.tty.user.dto.UserBaseInfoDTO;

import java.util.List;

/**
 * Created by linian on 2017/10/23.
 */
public interface UserInfoQueryDao {

    List<UserBaseInfoDTO> listUserBaseInfo(UserListParam userListParam, Boolean all);

    Long listUserBaseInfoCount(UserListParam userListParam, Boolean all);
}
