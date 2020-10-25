package com.tty.user.service;

import com.tty.user.dao.entity.UserInfoENT;

/**
 * @Author wenxiaoqing
 * @Date 2017/4/4
 * @Description
 */
public interface ShardedRedisService {
    void delUserInfo(UserInfoENT userInfoENT);
}
