package com.tty.user.service.mission;


/**
 * @author  shenwei on 2016/12/23.
 */
public interface MissionService {
     //获取单条任务状态
     Object getMissionStatus(Long userId, Long missionId);
}
