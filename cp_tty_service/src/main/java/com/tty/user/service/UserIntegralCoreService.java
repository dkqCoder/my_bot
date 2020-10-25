package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;
import com.tty.user.common.ExtModel;
import com.tty.user.controller.model.result.MissionModel;
import com.tty.user.dao.pojo.UserAccIntegral;
import com.tty.user.model.params.UserIntegralChangeParam;
import com.tty.user.model.result.UserIntegralHomePageResult;
import com.tty.user.model.result.UserIntegralMissionResult;

import java.util.List;

/**
 * @author zhudonghai
 * @date 2016-12-14
 * @Description 用户积分业务逻辑接口
 */
public interface UserIntegralCoreService {
    public List<UserAccIntegral> listUserAccIntegral();

    UserIntegralHomePageResult getUserHomePageInfo(Long userId);

    List<UserIntegralMissionResult> getMissionsCenterInfo(Long userId);

    Long getUserIntegralBalance(Long userId);

    Boolean userGetIntegral(String traceId, Long userId, Integer integral,Integer type,Long sourceId,String description);

    Boolean updateUserIntegral(String traceId, Long userId, Integer integral,Integer type,Long sourceId,String description);

    Boolean saveUserIntegral(String traceId, Long userId, Integer integral, Integer type, Long sourceId, String description);

    Boolean enterIntegralCenter(String traceId, Long userId);

    MissionModel getMissionModel(Long userId, Long missionId);

    List<UserIntegralMissionResult> getNewMissionInfoList(List<UserIntegralMissionResult> missList);

    Object doIntegralChange(UserIntegralChangeParam um);

    Boolean saveIntegralChangeRecord(UserIntegralChangeParam um);

    ExtModel integralBatchChange(String traceId,String[] userIdArray, JSONObject um);
}
