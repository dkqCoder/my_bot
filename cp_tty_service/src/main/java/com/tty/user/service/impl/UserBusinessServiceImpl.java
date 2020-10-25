package com.tty.user.service.impl;
/**
 * Created by shenwei on 2016/12/15.
 */

import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.db.ds.DataSourceContext;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.user.common.utils.DateUtils;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.context.*;
import com.tty.user.dao.UserAccIntegralDao;
import com.tty.user.dao.UserBusinessDao;
import com.tty.user.dao.UserGrowupDao;
import com.tty.user.dao.pojo.UserLevelAuthority;
import com.tty.user.model.result.UserIntegralMissionModel;
import com.tty.user.model.result.UserIntegralMissionResult;
import com.tty.user.service.UserBusinessService;
import com.tty.user.service.UserGrowupService;
import com.tty.user.service.UserIntegralCoreService;
import com.tty.user.service.UserSignInService;
import com.tty.user.dto.mission.UserMissionAddModel;
import com.tty.user.service.mission.UserIntegralCommService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author shenwei
 * @create 2016-12-15
 */

@Service("userBusinessService")
public class UserBusinessServiceImpl implements UserBusinessService {

    private static final Logger logger = LoggerFactory.getLogger(UserBusinessServiceImpl.class);

    @Autowired
    private UserSignInService userSignInService;
    @Autowired
    private UserBusinessDao userBusinessDao;
    @Autowired
    private UserIntegralCoreService userIntegralCoreService;
    @Autowired
    private UserIntegralCommService userIntegralCommService;
    @Autowired
    private UserAccIntegralDao userAccIntegralDao;
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;
    @Autowired
    private UserGrowupService userGrowupService;
    @Autowired
    private UserGrowupDao userGrowupDao;
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;


    /**
     * @Author shenwei
     * @Date 2016/12/19 16:43
     * @Description 首次绑定手机
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean bindPhoneFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 3 || um.getMissionId() != 3 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isFirstTimeBindPhone(um.getUserId());
        logger.debug("用户是否首次绑定手机验证 traceId:{},userId:{},isfirstTimeBindPhone:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            Integer integrals = 100;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("用户完成任务失败：traceId:{},userId:{} missionId:{},addModel:{}",
                        um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
                return false;
            }
            publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_BINDPHONR_KEY, um.getUserId());
            //福利任务小红点
            userRedis.hset(String.format(UserRedisKeys.USER_RED_DOT_KEY, um.getUserId()), UserInfoExtensionFields.USER_RED_DOT_WELFARE_KEY, "1");
        }
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 18:50
     * @Description 首次绑定银行卡
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean bindBankCardFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 2 || um.getMissionId() != 2 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isFirstTimeBindBankCard(um.getUserId());
        logger.debug("用户是否首次绑定银行卡验证 traceId:{},userId:{},isFirstTimeBindBankCard:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            Integer integrals = 100;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("用户完成任务失败：traceId:{},userId:{} missionId:{},addModel:{}",
                        um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
                return false;
            }
            publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_BINDBANK_KEY, um.getUserId());
            //福利任务小红点
            userRedis.hset(String.format(UserRedisKeys.USER_RED_DOT_KEY, um.getUserId()), UserInfoExtensionFields.USER_RED_DOT_WELFARE_KEY, "1");
        }
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:20
     * @Description 首次绑定身份证
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean bindCertCardFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 1 || um.getMissionId() != 1 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isFirstTimeBindCertCard(um.getUserId());
        logger.debug("用户是否首次绑定身份证 traceId:{},userId:{} isFirstTimeBindCertCard:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            Integer integrals = 100;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("用户完成任务失败：traceId:{},userId:{} missionId:{},addModel:{}",
                        um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
                return false;
            }
            publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_BINDCERT_KEY, um.getUserId());
        }
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 17:08
     * @Description 首次充值满20
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean payFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 4 || um.getMissionId() != 4 || um.getUserId() == null || um.getMoney() == null) {
            return false;
        }
        boolean flag = userBusinessDao.isFirstTimePay(um.getUserId(), um.getMoney());
        logger.debug("用户是否首次充值满20 traceId:{},userId:{} money:{} isFirstTimePay20 :{},addModel:{}",
                um.getTraceId(), um.getUserId(), um.getMoney(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            if (um.getMoney() < 20) {
                //任务资格丧失
                userIntegralCommService.missionFailed(um.getTraceId(), um.getUserId(), um.getMissionId());
            } else {
                Integer integrals = 100;
                if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                    logger.error("用户完成任务失败：traceId:{},userId:{} missionId:{},addModel:{}",
                            um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
                    return false;
                }
            }
            publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_SINGLEPAY_TWENTY_KEY, um.getUserId());
            //福利任务小红点
            userRedis.hset(String.format(UserRedisKeys.USER_RED_DOT_KEY, um.getUserId()), UserInfoExtensionFields.USER_RED_DOT_WELFARE_KEY, "1");
        }
        return false;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 16:43
     * @Description 首次大神跟单积分奖励
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean followSchemeFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 5 || um.getMissionId() != 5 || um.getUserId() == null) {
            return false;
        }
        boolean flag = userBusinessDao.isFirstTimeFollowScheme(um.getUserId());
        logger.debug("用户是否首次大神跟单 traceId:{},userId:{} isFirstFollowScheme:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            logger.debug("收到跟单某彩种请求：traceId:{},userId{},missionId{},addModel:{}",
                    um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
            int integrals = 100;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("用户完成任务失败：traceId:{},userId:{} missionId:{},addModel:{}",
                        um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
                return false;
            }
            publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_FOLLOWSCHEME_KEY, um.getUserId());
        }
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 16:43
     * @Description 首次购买某彩种积分奖励
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean buySchemeFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 6 || um.getUserId() == null || um.getLotteryId() == null) {
            return false;
        }
        boolean flag = userBusinessDao.isFirstTimeBuyScheme(um.getUserId(), um.getLotteryId());
        logger.debug("用户是否首次购买某彩种 traceId:{},userId:{}, lotteryId:{} isFirstTimeBuyScheme:{},addModel:{}",
                um.getTraceId(), um.getUserId(), um.getLotteryId(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            int integrals = 200;
            //首次购买竞彩足球
            if (um.getLotteryId() == 90) {
                if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), new Long(13), integrals)) {
                    logger.error("用户完成首次购买竞彩足球任务失败：traceId:{},userId:{} lotteryId:{},addModel:{}",
                            um.getTraceId(), um.getUserId(), um.getLotteryId(), GfJsonUtil.toJSONString(um));
                    return false;
                }
                String cacheKey = String.format(UserRedisKeys.USER_BUYSCHEME_KEY, um.getLotteryId());
                publicCommonRedisUtil.insertIntoRedisSet(cacheKey, um.getUserId());
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 16:44
     * @Description 签到相关积分奖励
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean signInFilter(UserMissionAddModel um) throws Exception {
        if (um.getMissionType() != MissionTypeEnum.SIGNIN.getIndex() ||
                um.getMissionId() != MissionTypeEnum.SIGNIN.getIndex() ||
                um.getUserId() == null || um.getDateTime() == null) {
            return false;
        }
        Integer integrals = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long continiousSignDays = new Long(0);
        continiousSignDays = userSignInService.getContiniousSigninDays(um.getUserId(), format.parse(um.getDateTime()));
        if (continiousSignDays == 0) {
            //第一天 10积分
            integrals = 10;
        }
        if (continiousSignDays == 1) {
            //第二天 15积分
            integrals = 15;
        }
        if (continiousSignDays >= 2) {
            //超过三天每天30积分
            integrals = 30;
        }
        if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
            logger.error("用户完成任务失败：traceId:{},userId:{} missionId:{},addModel:{}",
                    um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
            return false;
        }
        String description = "签到奖励";
        if (publicCommonRedisUtil.isInSignInTodaySet(um.getUserId())) {
            return false;
        }
        return userIntegralCoreService.updateUserIntegral(um.getTraceId(), um.getUserId(), integrals, 1, um.getMissionId(), description);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 17:15
     * @Description 用户每日购彩相关积分奖励
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional(readOnly = false)
    public boolean dailyBuyScheme(UserMissionAddModel um) {
        if (um.getMissionId() != 6 || um.getUserId() == null || um.getMoney() == null) {
            return false;
        }
        //单日购彩最高奖励积分
        Integer integralBuyMax = UserContext.USER_DAILYBUY_INTEGRAL_UPPER;
        //根据用户等级获取积分倍速
        double speed = findIntegralSpeed(um.getUserId());
        Integer integralToAdd = 0;
        Integer integralToday = publicCommonRedisUtil.getUserDailyIntegral(um.getUserId());
        logger.debug("用户今日购彩已奖励积分:traceId:{},userId:{} integralToday:{},addModel:{}",
                um.getTraceId(), um.getUserId(), integralToday, GfJsonUtil.toJSONString(um));
        if (integralToday >= integralBuyMax) {
            return true;
        }
        integralToAdd = (int) (Math.floor(um.getMoney().intValue() * speed));
        if ((integralToAdd + integralToday) > integralBuyMax) {
            integralToAdd = integralBuyMax - integralToday;
        }
        if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integralToAdd)) {
            logger.error("用户完成任务失败：traceId:{},userId:{} missionId:{},addModel:{}",
                    um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
            return false;
        }
        String description = "每日购彩奖励";
        userIntegralCoreService.updateUserIntegral(um.getTraceId(), um.getUserId(), integralToAdd, 1, um.getMissionId(), description);
        Long expireSeconds = DateUtils.getSecondsToTomorrow();
        publicCommonRedisUtil.setUserDailyBuyIntegral(um.getUserId(), Long.parseLong(integralToAdd.toString()), Integer.parseInt(expireSeconds.toString()));
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/20 18:12
     * @Description
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional()
    public boolean dailyBuySchemeGrowup(UserMissionAddModel um) {
        if (um.getMissionId() != 6 || um.getUserId() == null || um.getMoney() == null) {
            return false;
        }
        //单日购彩最高奖励成长值
        Long growupMax = UserContext.USER_DAILYBUY_GROWUP_UPPER;
        Long growupToAdd = new Long(0);
        Long growupToday = publicCommonRedisUtil.getUserDailyBuyGrowup(um.getUserId());
        logger.debug("用户每日购彩已奖励成长值:userId:{} growupToday:{}", um.getUserId(), growupToday);
        if (growupToday >= growupMax) {
            return true;
        }
        growupToAdd = um.getMoney().longValue();
        if ((growupToAdd + growupToday) > growupMax) {
            growupToAdd = growupMax - growupToday;
        }
        Long expireSeconds = DateUtils.getSecondsToTomorrow();
        userGrowupService.changeUserGrowup(um.getTraceId(), um.getUserId(), growupToAdd, UserContext.USER_DAILYBUY_GROWUP_DESC);
        publicCommonRedisUtil.setUserDailyBuyGrowup(um.getUserId(), growupToAdd, Integer.parseInt(expireSeconds.toString()));
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/20 13:39
     * @Description 根据用户Id取得积分倍速
     */
    private double findIntegralSpeed(Long userId) {
        double speed = 1;
        String level = publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_LEVEL);
        {
            if (level != null) {
                List<UserLevelAuthority> list = userGrowupDao.getLevelAuthority(Integer.parseInt(level));
                if (list != null && list.size() > 0) {
                    for (UserLevelAuthority item : list) {
                        if (item.getCategory().equals(AuthorityEnum.SPEEDINTEGRAL.getValue())) {
                            speed = item.getAuthorityValue().doubleValue();
                            break;
                        }
                    }
                }
            }
        }
        return speed;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:12
     * @Description 每日晒单任务达成
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean dailyShowScheme(UserMissionAddModel um) {
        if (um.getMissionType() != 8 || um.getMissionId() != 8 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isShowSchemeToday(um.getUserId());
        logger.debug("用户今日是否晒单 traceId:{},userId:{},isShowSchemeToday:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (!flag) {
            Integer integrals = 50;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("用户完成任务失败：traceId:{},userId:{} missionId:{},addModel:{}",
                        um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
                return false;
            }
            Long expireSeconds = DateUtils.getSecondsToTomorrow();
            publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_DAILY_SHOWSCHEME_KEY, um.getUserId(), Integer.parseInt(expireSeconds.toString()));
        }
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:13
     * @Description 每日分享任务达成
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean dailyShare(UserMissionAddModel um) {
        if (um.getMissionType() != 9 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isSharedToday(um.getUserId());
        logger.debug("用户今日是否已分享 traceId:{},userId:{} isShareToday:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (!flag) {
            Integer integrals = 50;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("用户完成任务失败：traceId:{},userId:{} missionId:{},addModel:{}",
                        um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
                return false;
            }
            Long expireSeconds = DateUtils.getSecondsToTomorrow();
            publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_DAILY_SHARE_KEY, um.getUserId(), Integer.parseInt(expireSeconds.toString()));
        }
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:13
     * @Description 每日评论任务达成
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean dailyComment(UserMissionAddModel um) {
        if (um.getMissionType() != 10 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isCommentToday(um.getUserId());
        logger.debug("用户今日是否评论：traceId:{},userId:{} isCommentToday:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (!flag) {
            Integer integrals = 50;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("用户完成任务失败：traceId:{},userId:{} missionId:{},addModel:{}",
                        um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
                return false;
            }
            Long expireSeconds = DateUtils.getSecondsToTomorrow();
            publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_DAILY_COMMENT_KEY, um.getUserId(), Integer.parseInt(expireSeconds.toString()));
        }
        return true;
    }

    /**
     * @param um
     * @author zhudonghai
     * @Date 2016-12-22
     * @Description 朝夕相处(自然周购彩天数>=5天)
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional(readOnly = false)
    public void checkForeverLoveDaysByWeek(UserMissionAddModel um) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(um.getDateTime());
        if (date != null && um.getUserId() != null && um.getUserId().longValue() > 0) {
            Date today = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            //Java里weekday=1，当天是周日；weekday=2，当天是周一；...;weekday=7，当天是周六。
            //现在统一下(1是星期一、2是星期二、3是星期三、4是星期四、5是星期五、6是星期六、0是星期日)
            Integer weekday = c.get(Calendar.DAY_OF_WEEK) - 1;
            Set<String> days = publicCommonRedisUtil.smembersForvevrdaysByWeek(um.getUserId());
            //如果当天已经完成，则不需走下去
            if (!days.contains(weekday.toString())) {
                List<UserIntegralMissionResult> missList = userAccIntegralDao.getMissionsInfoForeverDaysByUserId(um.getUserId());
                if (missList != null && missList.size() > 0) {
                    UserIntegralMissionResult userMiss = missList.get(0);
                    if (userMiss.getMisId() != null && userMiss.getMisId().intValue() == MissionTypeEnum.FOREVERLOVE.getIndex()) {
                        if (userMiss.getCount() < userMiss.getMissCounts() && weekday != null && !days.contains(weekday.toString())) {
                            if (userMiss.getCreateDate() == null || userMiss.getEndMisId() == null || userMiss.getEndMisId().longValue() == 0) {
                                //如果朝夕相处在当周没有记录则插入一条记录
                                UserIntegralMissionModel missM = new UserIntegralMissionModel();
                                missM.setMisId(userMiss.getMisId().longValue());
                                missM.setIsEnd(0);
                                missM.setIsGet(0);
                                missM.setCount(userMiss.getCount() + 1);
                                missM.setCreateDate(format.parse(um.getDateTime()));
                                missM.setMisType(MissionTypeEnum.FOREVERLOVE.getIndex());
                                missM.setLastDayWeek(userMiss.getLastDayWeek());
                                missM.setWeekDay(weekday);
                                missM.setIntegralGet(200);
                                missM.setUserId(um.getUserId());
                                missM.setFirstDayWeek(userMiss.getFirstDayWeek());
                                missM.setTraceId(um.getTraceId());
                                logger.debug("朝夕相处insert：traceId:{},userId:{} mission:{}", um.getTraceId(), um.getUserId(), GfJsonUtil.toJSONString(missM));
                                userAccIntegralDao.addUserEndMission(missM);
                            } else {
                                //如果存在则表示要更新count的值
                                UserIntegralMissionModel missM = new UserIntegralMissionModel();
                                missM.setEndMisId(userMiss.getEndMisId());
                                missM.setCount(userMiss.getCount() + 1);
                                missM.setMisType(userMiss.getMisType());
                                missM.setLastDayWeek(userMiss.getLastDayWeek());
                                missM.setWeekDay(weekday);
                                missM.setUserId(um.getUserId());
                                missM.setMisId(userMiss.getMisId().longValue());
                                missM.setTraceId(um.getTraceId());
                                logger.debug("朝夕相处update：traceId:{},userId:{} mission:{}", um.getTraceId(), um.getUserId(), GfJsonUtil.toJSONString(missM));
                                userAccIntegralDao.updateUserEndMissionCount(missM);
                            }
                        }
                        //再次获取缓存
                        days = publicCommonRedisUtil.smembersForvevrdaysByWeek(um.getUserId());
                        if (days.size() >= userMiss.getMissCounts()) {
                            if (userMiss.getIsEnd() != 1) {
                                //如果数量大于等于5了，则任务结束
                                UserIntegralMissionModel missM = new UserIntegralMissionModel();
                                missM.setEndMisId(userMiss.getEndMisId());
                                missM.setIsEnd(1);
                                missM.setMisType(userMiss.getMisType());
                                missM.setLastDayWeek(userMiss.getLastDayWeek());
                                missM.setUserId(um.getUserId());
                                missM.setWeekDay(weekday);
                                missM.setMisId(userMiss.getMisId().longValue());
                                missM.setMissCounts(userMiss.getMissCounts());
                                missM.setTraceId(um.getTraceId());
                                logger.debug("朝夕相处update：traceId:{},userId:{} mission:{}", um.getTraceId(), um.getUserId(), GfJsonUtil.toJSONString(missM));
                                userAccIntegralDao.updateUserEndMissionIsEnd(missM);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/1/6 17:34
     * @Description 用户每日登录行为消费增加成长值
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    public boolean dailyLogin(UserMissionAddModel um) {
        if (um.getMissionType() != 30 || um.getMissionId() != 30 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isLoginToday(um.getUserId());
        logger.debug("用户今日是否登录：traceId:{} userId:{} isLoginToday:{}", um.getTraceId(), um.getUserId(), flag);
        if (!flag && IsBuyedUser(um.getUserId())) {
            if (userGrowupService.changeUserGrowup(um.getTraceId(), um.getUserId(), UserContext.USER_DAILY_LOGIN_GROWUP, UserContext.USER_DAILY_LOGIN_GROWUP_DESC)) {
                Long expireSeconds = DateUtils.getSecondsToTomorrow();
                publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_DAILY_LOGIN_KEY, um.getUserId(), Integer.parseInt(expireSeconds.toString()));
                return true;
            }
        }
        return false;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/16 15:36
     * @Description 是否购彩用户
     */
    private Boolean IsBuyedUser(Long userId) {
        logger.debug("是否购彩用户 userId {}", userId);
        if (publicCommonRedisUtil.isInBuyedUserSet(userId)) {
            return true;
        }
        if (userBusinessDao.isBuyedUser(userId)) {
            publicCommonRedisUtil.insertIntoUserBuySet(userId);
            return true;
        } else {
            return false;
        }
    }
}
