package com.tty.user.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserFilterENT;

import java.util.List;

/**
 * @Author chenpengfei {chenpengfei@jiangduoduo.com}
 * @Date 2017/10/30 16:30
 */
public interface UserFilterDao {

    void saveUserFilter(UserFilterENT userFilterENT);

    void updateUserFilter(UserFilterENT userFilterENT);

    List<UserFilterENT> findUserFilterList(UserFilterENT userFilterENT);

    //    =========================admin==================================

    /**
     * 获取用户过滤配置分页数据
     */
    List<UserFilterENT> listUserFilter(Integer page, Integer limit, JSONObject data);
}
