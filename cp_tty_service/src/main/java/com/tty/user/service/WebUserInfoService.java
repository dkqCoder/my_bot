package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;

import java.util.List;
import java.util.Map;

/**
 * Created by jdd on 2017/1/19.
 */
public interface WebUserInfoService {
    void getUserInfoList(JSONObject jsonParm, ExtModel em);

    void getUserIntegralDetails(JSONObject jsonParam, ExtModel em);

    void getUserMissionList(JSONObject jsonParam, ExtModel em);

    void getIntegralUseList(JSONObject jsonParam, ExtModel em);

    List<Map<String, Object>> getUserLevelAndGrowup(Long userId);
}
