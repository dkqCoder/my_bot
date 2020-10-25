package com.tty.user.service.mission.impl;

import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.utils.DateUtil;
import com.tty.common.utils.Result;
import com.tty.user.common.utils.DateUtils;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.controller.model.result.UserSignInModel;
import com.tty.user.dao.UserSignInDao;
import com.tty.user.dao.pojo.UserSigninRecord;
import com.tty.user.model.result.UserGrowupModel;
import com.tty.user.model.result.UserInfoResult;
import com.tty.user.model.result.UserIntegralHomePageResult;
import com.tty.user.model.result.UserIntegralMissionResult;
import com.tty.user.service.UserGrowupService;
import com.tty.user.service.UserIntegralCoreService;
import com.tty.user.service.UserSignInService;
import com.tty.user.service.mission.MisUserEndMissionService;
import com.tty.user.service.mission.UserIntegralCommService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhudonghai
 * @date 2016-12-14
 */
@Service("userIntegralCommService")
public class UserIntegralCommServiceImpl implements UserIntegralCommService {
    private static final Logger logger = LoggerFactory.getLogger(UserIntegralCommServiceImpl.class);
    @Autowired
    private UserIntegralCoreService userIntegralCoreService;
    @Autowired
    private MisUserEndMissionService misUserEndMissionService;
    @Autowired
    private UserSignInService userSignInService;
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;
    @Autowired
    private UserSignInDao userSignInDao;
    @Autowired
    private UserGrowupService userGrowupService;

    @Override
    public UserIntegralHomePageResult getUserHomePageInfo(Long userId) {
        UserIntegralHomePageResult result = userIntegralCoreService.getUserHomePageInfo(userId);
        return result;
    }

    @Override
    public HashMap getMissionsCenterInfo(Long userId) {
        HashMap map = null;
        List<UserIntegralMissionResult> missions = null;
        try {
            missions = userIntegralCoreService.getMissionsCenterInfo(userId);
            map = new HashMap<String, List<UserIntegralMissionResult>>();
            map.put("missions", new ArrayList<>());
            if (missions != null) {
                map.put("missions", missions);
            }
        } catch (Exception e) {
            logger.error("获取用户任务信息异常 【UserId={};StackTrace: {}】", userId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return map;
    }

    @Override
    public HashMap getUserIntegralBalance(Long userId) {
        HashMap map = null;
        try {
            Long balance = userIntegralCoreService.getUserIntegralBalance(userId);
            map = new HashMap<String, Long>();
            map.put("integral", 0);
            if (balance != null) {
                map.put("integral", balance.longValue());
            }
        } catch (Exception e) {
            logger.error("获取用户积分余额信息 【UserId={};StackTrace: {}】", userId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return map;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/26 20:43
     * @Description
     */
    public void updateUserEndMisson(String traceId, Long userId, Long missionId) {
        misUserEndMissionService.updateUserEndMission(traceId, userId, missionId);
    }

    @Override
    public Result getIntegralAndSignInfo(Long userId) {
        Result result = new Result();
        Long balance = userIntegralCoreService.getUserIntegralBalance(userId);
        HashMap map = new HashMap<String, Long>();
        if (balance != null) {
            map.put("integral", balance.longValue());
        }
        Long signDays = publicCommonRedisUtil.getSignInDays(userId);
        Boolean isSign = publicCommonRedisUtil.isInSignInTodaySet(userId);
        if (isSign != null && isSign.booleanValue() == true) {
            map.put("isSignToday", 1);
            if (signDays == null) {
                signDays = userSignInService.getContiniousSigninDays(userId, new Date());
                publicCommonRedisUtil.setSignInDays(userId, signDays, DateUtils.getSecondsToTomorrow());
            }
        } else {
            map.put("isSignToday", 0);
            if (signDays == null) {
                signDays = userSignInService.getContiniousSigninDays(userId, DateUtil.preDate(1));
                publicCommonRedisUtil.setSignInDays(userId, signDays, DateUtils.getSecondsToTomorrow());
            }
        }
        if (signDays != null) {
            //明日奖励判断(第1天10；第2天15；第3天30，连签超过3天每天30)
            Integer signAward = new Integer(0);
            if (signDays.longValue() == 0) {
                signAward = 10;
            } else if (signDays.longValue() == 1) {
                signAward = 15;
            } else if (signDays.longValue() >= 2) {
                signAward = 30;
            }
            map.put("signAward", signAward);
            map.put("sign", signDays.longValue());
        }
        result.setData(map);
        result.setCode(Result.SUCCESS);
        result.setMsg(Result.MSG_SUCCESS_DESC);
        return result;
    }

    @Override
    public Result getUserInfo(String traceId, Long userId) {
        Result result = new Result();
        Long balance = userIntegralCoreService.getUserIntegralBalance(userId);
        UserInfoResult userInfo = new UserInfoResult();
        if (balance != null) {
            userInfo.setIntegral(balance.longValue());
        }
        Boolean isSign = publicCommonRedisUtil.isInSignInTodaySet(userId);
        if (isSign != null && isSign.booleanValue() == true) {
            userInfo.setIsSignToday(1);
        } else {
            userInfo.setIsSignToday(0);
        }
        UserGrowupModel growUp = userGrowupService.getUserLevelByUserId(traceId, userId);
        userInfo.setUserId(userId);
        if (growUp != null) {
            userInfo.setLevel(growUp.getLevel());
            userInfo.setLevelname(growUp.getLevelname());
        }
        result.setData(userInfo);
        result.setCode(Result.SUCCESS);
        result.setMsg(Result.MSG_SUCCESS_DESC);
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/29 21:14
     * @Description 用户完成任务
     */
    public Boolean completeMission(String traceId, Long userId, Long missionId, Integer integrals) {
        return misUserEndMissionService.completeMission(traceId, userId, missionId, integrals);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/30 16:22
     * @Description 用户任务资格丧失
     */
    public Boolean missionFailed(String traceId, Long userId, Long missionId) {
        return misUserEndMissionService.missionFailed(traceId, userId, missionId);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/27 14:52
     * @Description 用户签到返回信息model
     */
    @Override
    public UserSignInModel getUserSignInModel(Long userId) {
        Long balance = userIntegralCoreService.getUserIntegralBalance(userId);
        Long continiousDays = new Long(0);
        continiousDays = userSignInService.getContiniousSigninDays(userId, DateUtil.preDate(1));
        continiousDays = continiousDays + 1;
        Integer integralToAdd = 0;
        if (continiousDays == 0) {
            integralToAdd = 10; //明日再签增加积分
        } else if (continiousDays == 1) {
            integralToAdd = 15;
        } else {
            integralToAdd = 30;
        }
        UserSignInModel model = new UserSignInModel();
        model.setIntegrals(balance);
        model.setIntegralsToAdd(integralToAdd);
        model.setContiniousDays(Integer.parseInt(continiousDays.toString()));
        return model;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    public void asyncSignRecord(UserSigninRecord userSigninRecord, String traceId, Integer integralcount) {
        userSignInDao.saveUserSign(userSigninRecord);
        misUserEndMissionService.completeMission(traceId, userSigninRecord.getUserId(), new Long(7), integralcount);
        userIntegralCoreService.saveUserIntegral(traceId, userSigninRecord.getUserId(), integralcount, 1, new Long(7), "签到奖励");
        updateUserEndMisson(traceId, userSigninRecord.getUserId(), new Long(7));
    }
}
