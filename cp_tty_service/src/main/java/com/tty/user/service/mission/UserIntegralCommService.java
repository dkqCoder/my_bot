package com.tty.user.service.mission;

import com.tty.common.utils.Result;
import com.tty.user.controller.model.result.UserSignInModel;
import com.tty.user.dao.pojo.UserSigninRecord;
import com.tty.user.model.result.UserIntegralHomePageResult;

import java.util.HashMap;

/**
 * @author zhudonghai
 * @date 2016-12-14
 * 用户积分相关
 */
public interface UserIntegralCommService {
    UserIntegralHomePageResult getUserHomePageInfo(Long userId);

    HashMap getMissionsCenterInfo(Long userId);

    HashMap getUserIntegralBalance(Long userId);

    void updateUserEndMisson(String traceId, Long userId, Long missionId);

    UserSignInModel getUserSignInModel(Long userId);

    Result getIntegralAndSignInfo(Long userId);

    Result getUserInfo(String traceId, Long userId);

    Boolean completeMission(String traceId, Long userId, Long missionId, Integer integrals);

    Boolean missionFailed(String traceId, Long userId, Long missionId);

    public void asyncSignRecord(UserSigninRecord userSigninRecord, String traceId, Integer integralcount);

}
