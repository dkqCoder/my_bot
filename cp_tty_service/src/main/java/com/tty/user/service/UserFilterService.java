package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.tty.user.dao.entity.UserFilterENT;

import java.util.List;

/**
 * @Author chenpengfei {chenpengfei@jiangduoduo.com}
 * @Date 2017/10/30 16:19
 */
public interface UserFilterService {

    void saveUserFilter(UserFilterENT userFilterENT);

    List<UserFilterENT> findUserFilterList(UserFilterENT userFilterENT);

    void checkAndForbiddenUser(Integer queryHour);


    //    =========================admin==================================

    /**
     * 获取防御过滤配置数据
     */
    ExtModel listUserFilter(JSONObject jsonParam);

    /**
     * 新增防御过滤配置数据
     */
    ExtModel addUserFilter(UserFilterENT userFilter);

    /**
     * 更新防御过滤配置数据
     */
    ExtModel updateUserFilter(UserFilterENT userFilter);
}
