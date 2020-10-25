package com.tty.user.service.mission.impl;/**
 * Created by shenwei on 2016/12/23.
 */

import com.tty.user.service.mission.MissionService;
import com.tty.user.service.mission.UserMissionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shenwei
 * @create 2016-12-23
 */

@Service("userMissionReportService")
public class UserMissionReportServiceImpl implements UserMissionReportService {

    @Autowired
    private MissionService missionService;


    /**
     * @Author shenwei
     * @Date 2016/12/28 16:01
     * @Description 获取用户任务状态
     */
    @Override
    public Object getMissionStatus(Long userId, Long missionId) {
        return null;
    }
}
