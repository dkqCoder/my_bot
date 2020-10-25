package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.tty.user.dao.entity.UserFilterDetailENT;

import java.util.List;

/**
 * @Author chenpengfei {chenpengfei@jiangduoduo.com}
 * @Date 2017/10/30 17:41
 */
public interface UserFilterDetailService {

    void addUserFilterDetail(String code);

    List<UserFilterDetailENT> findUserFilterDetailList(UserFilterDetailENT userFilterDetailENT, List<Long> userFilterIds);


    //    =========================admin==================================
    /**
     * 获取防御过滤明细数据
     */
    ExtModel listUserFilterDetail(JSONObject jsonParam);

    /**
     * 新增防御过滤明细数据
     */
    ExtModel addUserFilterDetail(UserFilterDetailENT userFilter);

    /**
     * 更新防御过滤明细数据
     */
    ExtModel updateUserFilterDetail(UserFilterDetailENT userFilter);
}
