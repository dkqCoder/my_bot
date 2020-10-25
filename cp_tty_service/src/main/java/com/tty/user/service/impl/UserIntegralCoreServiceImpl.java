package com.tty.user.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.StringUtils;
import com.tty.common.utils.Result;
import com.tty.user.common.ExtModel;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserInfoExtensionFields;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.controller.model.result.MissionModel;
import com.tty.user.dao.UserAccIntegralDao;
import com.tty.user.dao.UserBusinessDao;
import com.tty.user.dao.UserIntegralRecordsDao;
import com.tty.user.dao.UserSignInDao;
import com.tty.user.dao.pojo.UserAccIntegral;
import com.tty.user.dao.pojo.UserIntegralRecords;
import com.tty.user.model.params.UserIntegralChangeParam;
import com.tty.user.model.result.UserIntegralHomePageResult;
import com.tty.user.model.result.UserIntegralMissionResult;
import com.tty.user.service.UserIntegralCoreService;
import com.tty.user.service.mission.MissionService;
import com.tty.user.service.mission.UserIntegralCommService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zhudonghai
 * @date 2016-12-13
 * @Description 用户积分业务逻辑类
 */
@Service("userIntegralCoreService")
public class UserIntegralCoreServiceImpl implements UserIntegralCoreService {

    private static final Logger logger = LoggerFactory.getLogger(UserIntegralCoreServiceImpl.class);
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private UserAccIntegralDao userAccIntegralDao;
    @Autowired
    private UserSignInDao userSignInDao;
    @Autowired
    private UserIntegralRecordsDao userIntegralRecordsDao;
    @Autowired
    private UserIntegralCommService userIntegralCommService;
    @Autowired
    private UserBusinessDao userBusinessDao;
    @Autowired
    private MissionService missionService;
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public List<UserAccIntegral> listUserAccIntegral() {
        return userAccIntegralDao.listUserAccIntegral();
    }

    @Override
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public UserIntegralHomePageResult getUserHomePageInfo(Long userId) {
        List<UserIntegralMissionResult> listMissions = new ArrayList<>();
        UserIntegralHomePageResult userInfo = new UserIntegralHomePageResult();
        String integralField = UserInfoExtensionFields.USER_INTEGRAL_BALANCE_FIELD;
        userInfo.setUserName(publicCommonRedisUtil.getFullUserName(userId));

        // 打败多少彩友
        userInfo.setBeat(getUserBeatInfo(userId).toString());

        //region 签到相关
        Long signDays = null;
        if (publicCommonRedisUtil.isInSignInTodaySet(userId)) {
            userInfo.setIsSignToday(1);
            signDays = userSignInDao.getContiniousSigninDays(userId, new Date());
        } else {
            userInfo.setIsSignToday(0);
            signDays = userSignInDao.getContiniousSigninDays(userId, DateUtil.preDate(1));
        }
        if (signDays != null) {
            userInfo.setSign(signDays.longValue());
            //明日奖励判断(第1天10；第2天15；第3天30，连签超过3天每天30)
            Integer signAward = new Integer(0);
            if (signDays.longValue() == 0) {
                signAward = 10;
            } else if (signDays.longValue() == 1) {
                signAward = 15;
            } else if (signDays.longValue() == 2) {
                signAward = 30;
            } else if (signDays.longValue() >= 3) {
                signAward = 30;
            }
            userInfo.setSignAward(signAward);
        }
        //endregion

        //region 用户积分余额
        Long balance = new Long(0);
        //先读缓存
        String strBalance = publicCommonRedisUtil.getUserInfoExtension(userId, integralField);
        if (StringUtils.isNotBlank(strBalance)) {
            balance = Long.parseLong(strBalance);
        } else {
            balance = getUserIntegralBalance(userId);
        }
        userInfo.setIntegral(balance);
        //endregion

        //我的积分页推荐任务传1，显示优先级为1的任务,如果不足5条显示优先级为2的
        listMissions = userAccIntegralDao.getMissionsInfo(userId, 1);
        userInfo.setMissions(listMissions);

        //是否首次进入我的积分页面
        if (userBusinessDao.isFirstEnterIntegral(userId)) {
            userInfo.setIsFirstVisit(1);
        } else {
            userInfo.setIsFirstVisit(0);
        }
        userInfo.setFirstVisitIntegral(UserContext.USER_FIRSTVISITINTEGRALPAGE_INTEGRAL);

        return userInfo;
    }

    /**
     * @param userId
     * @return
     * @author zhudonghai
     * @Description 获取任务列表
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public List<UserIntegralMissionResult> getMissionsCenterInfo(Long userId) {
        List<UserIntegralMissionResult> listMissions = new ArrayList<>();
        //传0显示所有任务
        listMissions = userAccIntegralDao.getMissionsInfo(userId, 0);
        return listMissions;
    }

    public Double getUserBeatInfo(Long userId) {
        Double beat = null;
        String beatField = UserInfoExtensionFields.USER_BEAT_FIELD;
        String beatStr = publicCommonRedisUtil.getUserInfoExtension(userId, beatField);
        if (StringUtils.isNotBlank(beatStr)) {
            beat = Double.parseDouble(beatStr);
        }
        if (beat != null && beat.doubleValue() > 0) {
            return beat;
        }
        beat = userAccIntegralDao.getUserBeatInfo(userId);
        if (beat != null && beat.doubleValue() > 0) {
            publicCommonRedisUtil.setUserInfoExtension(userId, beatField, String.valueOf(beat));
        }
        return beat;
    }

    /**
     * 积分变动后重新缓存一下用户打败多少彩友的信息
     *
     * @param userId
     */
    public void refreshUserBeatInfo(Long userId) {
        String beatField = UserInfoExtensionFields.USER_BEAT_FIELD;
        Double beat = new Double(0);
        publicCommonRedisUtil.setUserInfoExtension(userId, beatField, String.valueOf(beat));
    }

    @Override
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public Long getUserIntegralBalance(Long userId) {
        Long balance = null;
        String integralField = UserInfoExtensionFields.USER_INTEGRAL_BALANCE_FIELD;
        String strBalance = publicCommonRedisUtil.getUserInfoExtension(userId, integralField);
        if (StringUtils.isNotBlank(strBalance)) {
            balance = Long.parseLong(strBalance);
        }
        if (balance != null && balance.longValue() >= 0) {
            return balance;
        }
        balance = userAccIntegralDao.getUserIntegralBalance(userId);
        if (balance != null && balance.longValue() >= 0) {
            //缓存积分余额
            publicCommonRedisUtil.setUserInfoExtension(userId, integralField, String.valueOf(balance));
            return balance;
        }
        return balance;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/29 22:08
     * @Description 用户领取积分
     */
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional(readOnly = false)
    public Boolean userGetIntegral(String traceId, Long userId, Integer integral, Integer type, Long sourceId, String description) {
        if (!userAccIntegralDao.CanGetIntegral(userId, Integer.parseInt(sourceId.toString()))) {
            logger.debug("用户不可领取该任务积分,traceId:{} userId:{} MissionId:{}", traceId, userId, sourceId);
            return false;
        }
        updateUserIntegral(traceId, userId, integral, type, sourceId, description);
        userIntegralCommService.updateUserEndMisson(traceId, userId, sourceId);
        return true;
    }


    /**
     * @Author shenwei
     * @Date 2016/12/21 12:38
     * @Description 用户积分变动
     */
    public Boolean updateUserIntegral(String traceId, Long userId, Integer integral, Integer type, Long sourceId, String description) {
        Boolean flag = publicCommonRedisUtil.increaseUserIntegral(userId, UserInfoExtensionFields.USER_INTEGRAL_BALANCE_FIELD, integral);
        if (!flag) {
            return flag;
        }
        return saveUserIntegral(traceId, userId, integral, type, sourceId, description);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/28 8:31
     * @Description 用户积分变动入库
     */
    public Boolean saveUserIntegral(String traceId, Long userId, Integer integral, Integer type, Long sourceId, String description) {
        logger.debug("用户积分变动入库traceId:{},userid:{},integral:{},type:{},sourceid:{},description:{}",
                traceId, userId, integral, type, sourceId, description);
        UserIntegralRecords ent = new UserIntegralRecords();
        ent.setUserid(userId);
        ent.setCreateDate(new Date());
        ent.setType(type);
        ent.setCount(Integer.parseInt(integral.toString()));
        ent.setSourceid(sourceId);
        ent.setDescription(description);
        userIntegralRecordsDao.saveUserIntegralRecords(ent);
        //用户账户积分变动
        userAccIntegralDao.updateUserIntegral(traceId, userId, new Long(integral));
        //积分变动后重新缓存一下用户打败多少彩友的信息
        refreshUserBeatInfo(userId);
        //积分变动后清理积分收支明细缓存
        publicCommonRedisUtil.removeUserIntegralRecordsCache(userId);
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/28 13:19
     * @Description 领取积分后返回model
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    public MissionModel getMissionModel(Long userId, Long missionId) {
        logger.debug("获取任务状态 userId:{} missionId:{}", userId, missionId);
        Integer integrals = Integer.parseInt(getUserIntegralBalance(userId).toString());
        Integer isEnd = 0;
        Integer isAdd = 0;
        String remark = "";
        Integer integralsToAdd = 0;
        Integer missionTimes = 0;
        String missName = "";
        Map<String, Object> missionStatus = (Map<String, Object>) missionService.getMissionStatus(userId, missionId);
        if (missionStatus != null) {
            if (missionStatus.get("isEnd") != null) {
                isEnd = Integer.parseInt(missionStatus.get("isEnd").toString());
            }
            if (missionStatus.get("isAdd") != null) {
                isAdd = Integer.parseInt(missionStatus.get("isAdd").toString());
            }
            if (missionStatus.get("remark") != null) {
                remark = missionStatus.get("remark").toString();
            }
            if (missionStatus.get("integralsToAdd") != null) {
                integralsToAdd = Integer.parseInt(missionStatus.get("integralsToAdd").toString());
            }
            if (missionStatus.get("missionTimes") != null) {
                missionTimes = Integer.parseInt(missionStatus.get("missionTimes").toString());
            }
            if (missionStatus.get("missName") != null) {
                missName = missionStatus.get("missName").toString();
            }
        }
        MissionModel missionModel = new MissionModel();
        missionModel.setIntegrals(integrals);
        missionModel.setDescription(remark);
        missionModel.setIntegralsToAdd(integralsToAdd);
        missionModel.setMissName(missName);
        logger.info("用户领取积分：isadd{},isend{}", isAdd, isEnd);
        if (isAdd == 1) {
            missionModel.setButtonStatus(0);
            if (missionTimes == 1) {
                missionModel.setButtonText("今日已领");
            } else if (missionTimes == 7) {
                missionModel.setButtonText("本周已领");
            } else {
                missionModel.setButtonText("已领取");
            }
        } else if (isEnd == 1) {
            missionModel.setButtonStatus(1);
            missionModel.setButtonText("领取积分");
        } else {
            missionModel.setButtonStatus(1);
            missionModel.setButtonText("去完成");
        }
        return missionModel;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/28 8:48
     * @Description 用户首次进入积分中心
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional(readOnly = false)
    public Boolean enterIntegralCenter(String traceId, Long userId) {
        if (publicCommonRedisUtil.isInEnterIntegralSet(userId)) {
            return false;
        }
        Integer integral = UserContext.USER_FIRSTVISITINTEGRALPAGE_INTEGRAL;//50积分
        publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_ENTER_INTEGRAL_KEY, userId);
        logger.info("traceId={} 首次进入用户中心 用户积分redis 增加 userid {}", traceId, userId);
        publicCommonRedisUtil.increaseUserIntegral(userId, UserInfoExtensionFields.USER_INTEGRAL_BALANCE_FIELD, integral);
        userIntegralCommService.completeMission(traceId, userId, new Long(12), integral);
        saveUserIntegral(traceId, userId, integral, 1, new Long(12), "首次进入积分中心奖励");
        userIntegralCommService.updateUserEndMisson(traceId, userId, new Long(12));
        return true;
    }

    public List<UserIntegralMissionResult> getNewMissionInfoList(List<UserIntegralMissionResult> missList) {
        return userAccIntegralDao.getNewMissionInfoList(missList);
    }

    /**
     * 积分兑换，积分增减
     *
     * @param um
     * @return
     */
    public Object doIntegralChange(UserIntegralChangeParam um) {
        Result resStatus = new Result();
        resStatus.setCode(Result.ERROR);
        resStatus = publicCommonRedisUtil.increaseUserIntegralResult(um.getTraceId(), um.getUserId(),
                UserInfoExtensionFields.USER_INTEGRAL_BALANCE_FIELD, um.getIntegral());
        boolean checkMission = resStatus != null && (resStatus.getCode() == UserContext.USER_DEDUCTION_INTEGRAL_LOW_FLAG || resStatus.getCode() == Result.ERROR);
        if (checkMission) {
            //如果积分不足，或者错误，直接返回
            return resStatus;
        } else if (resStatus != null && resStatus.getCode() == UserContext.USER_DEDUCTION_INTEGRAL_CORRECT_FLAG) {
            //redis扣减正常则把变动的记录入库
            logger.info("用户积分兑换redis变动成功 traceId:{},userid:{},integral:{},paramModel:{}",
                    um.getTraceId(), um.getUserId(), um.getIntegral(), GfJsonUtil.toJSONString(um));

            threadPoolTaskExecutor.execute(() -> {
                //用户账户积分变动
                userAccIntegralDao.updateUserIntegral(um.getTraceId(), um.getUserId(), new Long(um.getIntegral()));
                //积分变动后重新缓存一下用户打败多少彩友的信息
                refreshUserBeatInfo(um.getUserId());
                logger.info("用户积分兑换入库成功 traceId:{},userid:{},integral:{},paramModel:{}",
                        um.getTraceId(), um.getUserId(), um.getIntegral(), GfJsonUtil.toJSONString(um));
            });
        }

        return resStatus;
    }

    /**
     * 积分变动记录入库
     *
     * @param um
     * @return
     */
    public Boolean saveIntegralChangeRecord(UserIntegralChangeParam um) {
        threadPoolTaskExecutor.execute(() -> {
            UserIntegralRecords ent = new UserIntegralRecords();
            ent.setUserid(um.getUserId());
            ent.setCreateDate(new Date());
            ent.setType(um.getType());
            ent.setCount(um.getIntegral());
            ent.setSourceid(um.getSourceId());
            ent.setDescription(um.getDescription());
            userIntegralRecordsDao.saveUserIntegralRecords(ent);
            //积分变动后清理积分收支明细缓存
            publicCommonRedisUtil.removeUserIntegralRecordsCache(um.getUserId());
            logger.info("用户积分变动记录入库成功 traceId:{},userid:{},integral:{},paramModel:{}",
                    um.getTraceId(), um.getUserId(), um.getIntegral(), GfJsonUtil.toJSONString(um));
        });
        return true;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public ExtModel integralBatchChange(String traceId, String[] userIdArray, JSONObject um) {
        ExtModel em = new ExtModel();
        try {
            HashMap<Long, String> msgList = new HashMap<Long, String>();
            for (String userIdStr : userIdArray) {
                Long userId = Long.parseLong(userIdStr);
                if (!msgList.containsKey(userId)) {
                    String user101 = publicCommonRedisUtil.getHideUserName(userId);
                    if (!StringUtils.isNotBlank(user101)) {
                        msgList.put(userId, "用户ID不存在");
                        continue;
                    }
                    if (this.updateUserIntegral(traceId, userId, um.getInteger("integral"), um.getInteger("type"), um.getLong("sourceId"), um.getString("description"))) {

                        msgList.put(userId, "积分变动成功");
                    } else {
                        msgList.put(userId, "积分变动失败");
                    }
                }
            }
            em.setSuccess(true);
            em.setData(msgList);
            return em;
        } catch (Exception e) {
            em.setSuccess(false);
            em.setData("用户积分批量变动失败");
            logger.error("用户积分批量变动异常! traceId={};stackTrace:{}", traceId, LogExceptionStackTrace.erroStackTrace(e));
            return em;
        }
    }


}
