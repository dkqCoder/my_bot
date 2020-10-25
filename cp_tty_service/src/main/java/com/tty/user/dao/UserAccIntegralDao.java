package com.tty.user.dao;

import com.tty.user.dao.pojo.UserAccIntegral;
import com.tty.user.model.result.UserIntegralMissionModel;
import com.tty.user.model.result.UserIntegralMissionResult;

import java.util.List;

/**
 * @author chenlongfei
 */
public interface UserAccIntegralDao {

    public List<UserAccIntegral> listUserAccIntegral();

    public void saveUserAccIntegral(UserAccIntegral ent);

    public void updateUserAccIntegral(UserAccIntegral ent);

    public void deleteUserAccIntegral(UserAccIntegral ent);

    public void saveOrUpdateUserAccIntegral(UserAccIntegral ent);

    Double getUserBeatInfo(Long userId);

    Long getUserIntegralBalance(Long userId);

    Boolean updateUserIntegral(String traceId, Long userId, Long integral);

    List<UserIntegralMissionResult> getMissionsInfo(Long userId, Integer priority);

    Boolean addUserEndMission(UserIntegralMissionModel miss) throws Exception;

    Boolean updateUserEndMissionCount(UserIntegralMissionModel miss) throws Exception;

    Boolean updateUserEndMissionIsEnd(UserIntegralMissionModel miss) throws Exception;

    List<UserIntegralMissionResult> getMissionsInfoForeverDaysByUserId(Long userId);

    List<UserIntegralMissionResult> getNewMissionInfoList(List<UserIntegralMissionResult> missList);

    Boolean CanGetIntegral(Long userId, Integer missionId);

}
