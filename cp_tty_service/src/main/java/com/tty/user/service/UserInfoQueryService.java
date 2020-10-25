package com.tty.user.service;

import com.jdd.fm.core.model.ResultModel;
import com.tty.user.controller.model.params.UserListParam;

/**
 * Created by linian on 2017/10/20.
 */
public interface UserInfoQueryService {
    ResultModel queryUserList(UserListParam userListParam);

}
