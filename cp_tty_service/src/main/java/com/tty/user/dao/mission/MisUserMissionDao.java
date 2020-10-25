package com.tty.user.dao.mission;


import com.tty.user.dao.mission.pojo.MisUserMission;

import java.util.Map;

/**
 *
 * @author chenlongfei
 *
 */
public interface MisUserMissionDao {

    public void saveMisUserMission(MisUserMission ent);

    public void updateMisUserMission(MisUserMission ent);

    public void deleteMisUserMission(MisUserMission ent);

    public void saveOrUpdateMisUserMission(MisUserMission ent);

    MisUserMission getMisUserMissionById(Long missionId, Long userId);

    //获取某条任务状态
    Map<String, Object> getMissionStatus(Long userId, Long missionId);


}
