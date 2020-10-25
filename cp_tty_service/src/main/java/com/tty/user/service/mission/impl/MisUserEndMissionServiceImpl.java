package com.tty.user.service.mission.impl;/**
 * Created by shenwei on 2016/12/20.
 */


import com.jdd.fm.core.db.ds.DataSource;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.dao.mission.MisUserEndMissionDao;
import com.tty.user.dao.mission.pojo.MisUserEndMission;
import com.tty.user.dto.mission.UserEndMissionModel;
import com.tty.user.dto.mission.UserMissionModel;
import com.tty.user.service.mission.MisUserEndMissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author shenwei
 * @create 2016-12-20
 */
@Service("misUserEndMissionService")
public class MisUserEndMissionServiceImpl implements MisUserEndMissionService {

    private static final Logger logger = LoggerFactory.getLogger(MisUserEndMissionServiceImpl.class);

    @Autowired
    private MisUserEndMissionDao misUserEndMissionDao;
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;


    /**
     * @Author shenwei
     * @Date 2016/12/22 11:42
     * @Description
     */
    @Override
    @Transactional(readOnly = true)
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    public UserEndMissionModel getUserEndMissionModel(Long userId, Long missionId) {
        return misUserEndMissionDao.getUserEndMissionModel(userId, missionId);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/26 20:37
     * @Description
     */
    public void updateUserEndMission(String traceId, Long userId, Long missionId) {
        misUserEndMissionDao.updateUserEndMission(traceId, userId, missionId);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/27 10:19
     * @Description
     */
    @Override
    @Transactional(readOnly = true)
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    public UserMissionModel getUserMissionModel(Long userId, Long missionId) {
        return misUserEndMissionDao.getUserMissionModel(userId, missionId);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/28 8:42
     * @Description 任务完成动态表中删除任务
     */
//    public void delUserEndMission(String traceId, Long userId,Long missionId){
//          misUserEndMissionDao.deleteMisUserEndMission(traceId, userId,missionId);
//    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 14:23
     * @Description 用户完成任务
     */
    public boolean completeMission(String traceId, Long userId, Long missionId, Integer integrals) {
        misUserEndMissionDao.deleteMisUserEndMission(traceId, userId, missionId);
        MisUserEndMission misUserEndMission = new MisUserEndMission();
        misUserEndMission.setMissionId(missionId);
        misUserEndMission.setCreateDate(new Date());
        misUserEndMission.setIsAdd(0);
        misUserEndMission.setUserid(userId);
        misUserEndMission.setIntegrals(integrals);
        misUserEndMission.setIsEnd(1);
        misUserEndMission.setCount(0);  //非周期性任务，完成阶段设置为0
        misUserEndMissionDao.saveMisUserEndMission(misUserEndMission);
        //清除用户任务列表相关缓存
        publicCommonRedisUtil.clearUserMissionInfo(misUserEndMission);
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/30 16:25
     * @Description 任务资格丧失
     */
    public boolean missionFailed(String traceId, Long userId, Long missionId) {
        misUserEndMissionDao.deleteMisUserEndMission(traceId, userId, missionId);
        MisUserEndMission misUserEndMission = new MisUserEndMission();
        misUserEndMission.setMissionId(missionId);
        misUserEndMission.setCreateDate(new Date());
        misUserEndMission.setIsAdd(0);
        misUserEndMission.setUserid(userId);
        misUserEndMission.setIntegrals(0);
        misUserEndMission.setIsEnd(3);
        misUserEndMission.setCount(0);
        misUserEndMissionDao.saveMisUserEndMission(misUserEndMission);
        publicCommonRedisUtil.clearUserMissionInfo(misUserEndMission);
        return true;
    }
}
