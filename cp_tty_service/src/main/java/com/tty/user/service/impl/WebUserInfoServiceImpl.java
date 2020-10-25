package com.tty.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.utils.StringUtils;
import com.tty.user.dao.UserAccIntegralDao;
import com.tty.user.dao.WebUserBaseInfoDao;
import com.tty.user.model.result.UserIntegralMissionResult;
import com.tty.user.service.WebUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhudonghai
 * @Date 2017/1/19
 * @Description 用户后台使用, 用户信息
 */
@Service("webUserInfoService")
public class WebUserInfoServiceImpl implements WebUserInfoService {
    @Autowired
    private WebUserBaseInfoDao userBaseInfoDao;
    @Autowired
    private UserAccIntegralDao userAccIntegralDao;

    public void getUserInfoList(JSONObject jsonParam, ExtModel em) {
        em.setData(userBaseInfoDao.getUserInfoList(jsonParam.getInteger("page"), jsonParam.getInteger("limit"), jsonParam.getJSONObject("data")));
        em.setTotal(userBaseInfoDao.getUserInfoCount(jsonParam.getJSONObject("data")));
    }

    public void getUserIntegralDetails(JSONObject jsonParam, ExtModel em) {
        em.setData(userBaseInfoDao.getUserIntegralDetails(jsonParam.getInteger("page"), jsonParam.getInteger("limit"), jsonParam.getJSONObject("data")));
        em.setTotal(userBaseInfoDao.getUserIntegralDetailsCount(jsonParam.getJSONObject("data")));
    }

    public void getUserMissionList(JSONObject jsonParam, ExtModel em) {
        Long userIdLong = new Long(0);
        JSONObject json = jsonParam.getJSONObject("data");
        if (json != null && StringUtils.isNotBlank(json.getString("userId"))) {
            userIdLong = Long.parseLong(json.getString("userId"));
        }
        List<UserIntegralMissionResult> missList = userAccIntegralDao.getMissionsInfo(userIdLong, 0);
        em.setData(missList);
        em.setTotal(Long.valueOf(missList.size()));
    }

    public void getIntegralUseList(JSONObject jsonParam, ExtModel em) {
        em.setData(userBaseInfoDao.getIntegralUseList(jsonParam.getInteger("page"), jsonParam.getInteger("limit"), jsonParam.getJSONObject("data")));
        em.setTotal(userBaseInfoDao.getIntegralUseListCount(jsonParam.getJSONObject("data")));
    }

    public List<Map<String, Object>> getUserLevelAndGrowup(Long userId) {
        return userBaseInfoDao.getUserLevelAndGrowup(userId);
    }
}
