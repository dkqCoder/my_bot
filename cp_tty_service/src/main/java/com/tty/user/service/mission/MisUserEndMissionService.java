package com.tty.user.service.mission;


import com.tty.user.dto.mission.UserEndMissionModel;
import com.tty.user.dto.mission.UserMissionModel;

/**
 * @author  shenwei on 2016/12/20.
 */
public interface MisUserEndMissionService {
    //void saveMisUserEndMission(MisUserEndMission ent);
    UserEndMissionModel getUserEndMissionModel(Long userId, Long missionId);
    void updateUserEndMission(String traceId, Long userId, Long missionId);
    UserMissionModel getUserMissionModel(Long userId, Long missionId);
    //void delUserEndMission(String traceId, Long userId,Long missionId);
    boolean completeMission(String traceId, Long userId, Long missionId, Integer integrals);
    boolean missionFailed(String traceId, Long userId, Long missionId);
}
