package com.tty.user.service.mission;/**
 * Created by shenwei on 2016/12/21.
 */


import com.tty.user.dao.mission.pojo.MisUserMission;

/**
 * @author shenwei
 * @create 2016-12-21
 */
public interface MisUserMissionService {
     MisUserMission getMisUserMissionById(Long missionId, Long userId);
}
