package com.tty.user.service.mission.impl;/**
 * Created by shenwei on 2016/12/21.
 */

import com.jdd.fm.core.db.ds.DataSource;
import com.tty.user.dao.mission.MisUserMissionDao;
import com.tty.user.dao.mission.pojo.MisUserMission;
import com.tty.user.service.mission.MisUserMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shenwei
 * @create 2016-12-21
 */

@Service("misUserMissionService")
public class MisUserMissionServiceImpl implements MisUserMissionService {

    @Autowired
    private MisUserMissionDao misUserMissionDao;

    /**
     * @Author shenwei
     * @Date 2016/12/21 14:05
     * @Description 获取任务实体
     */
    @Override
    @Transactional(readOnly = true)
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    public MisUserMission getMisUserMissionById(Long missionId, Long userId) {
        return misUserMissionDao.getMisUserMissionById(missionId, userId);
    }
}
