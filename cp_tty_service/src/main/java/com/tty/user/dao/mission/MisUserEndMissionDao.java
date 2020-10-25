package com.tty.user.dao.mission;

import com.tty.user.dao.mission.pojo.MisUserEndMission;
import com.tty.user.dto.mission.UserEndMissionModel;
import com.tty.user.dto.mission.UserMissionModel;

/**
 *
 * @author chenlongfei
 *
 */
public interface MisUserEndMissionDao {

    public void saveMisUserEndMission(MisUserEndMission ent);

    public void updateMisUserEndMission(MisUserEndMission ent);

    public void deleteMisUserEndMission(String traceId, Long userId, Long missionId);

    public void saveOrUpdateMisUserEndMission(MisUserEndMission ent);

    UserEndMissionModel getUserEndMissionModel(Long userId, Long missionId);

    Boolean updateUserEndMission(String traceId, Long userId, Long missionId);

    UserMissionModel getUserMissionModel(Long userId, Long missionId);

}
