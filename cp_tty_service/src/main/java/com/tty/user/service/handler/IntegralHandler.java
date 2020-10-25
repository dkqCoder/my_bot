package com.tty.user.service.handler;


import com.tty.common.utils.Result;

/**
 * @author ln
 */
public interface IntegralHandler {

    Result getUserIntegralHomePageInfo(String traceId, String userID);

    Result getMissionsCenterInfo(String traceId, String userID);

    Result getUserIntegralRecords(String traceId, String userID, String params);

    Result userSignIn(String traceId, String userID, String params);

    Result userGetMissionReward(String traceId, String userID, String params);

    Result getUserIntegralBalance(String traceId, String userID);

    Result getUserLevelGrowups(String traceId, String userID);

    Result getUserGrowupsRecord(String traceId, String userID, String params);

    Result getUserLevelCenter(String traceId, String userID);

    Result getLevelupGift(String traceId, String userID, String params);

}
