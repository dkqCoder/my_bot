package com.tty.user.service.mission.impl;/**
 * Created by shenwei on 2016/12/23.
 */

import com.jdd.fm.core.db.ds.DataSource;
import com.tty.user.dao.mission.MisUserMissionDao;
import com.tty.user.service.mission.MissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shenwei
 * @create 2016-12-23
 */

@Service("missionService")
public class MissionServiceImpl implements MissionService {
    private static final Logger logger = LoggerFactory.getLogger(MissionServiceImpl.class);
    @Autowired
    private MisUserMissionDao misUserMissionDao;

    /**
     * @Author shenwei
     * @Date 2016/12/28 15:10
     * @Description 获取单条任务状态
    */
    @Transactional(readOnly = true)
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    public Object getMissionStatus(Long userId, Long missionId) {
        return misUserMissionDao.getMissionStatus(userId,missionId);
    }
}
