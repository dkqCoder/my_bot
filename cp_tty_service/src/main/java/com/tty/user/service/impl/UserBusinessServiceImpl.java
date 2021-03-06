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
     * @Description ??????????????????
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean bindPhoneFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 3 || um.getMissionId() != 3 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isFirstTimeBindPhone(um.getUserId());
        logger.debug("???????????????????????????????????? traceId:{},userId:{},isfirstTimeBindPhone:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            Integer integrals = 100;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("???????????????????????????traceId:{},userId:{} missionId:{},addModel:{}",
                        um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
                return false;
            }
            publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_BINDPHONR_KEY, um.getUserId());
            //?????????????????????
            userRedis.hset(String.format(UserRedisKeys.USER_RED_DOT_KEY, um.getUserId()), UserInfoExtensionFields.USER_RED_DOT_WELFARE_KEY, "1");
        }
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 18:50
     * @Description ?????????????????????
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean bindBankCardFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 2 || um.getMissionId() != 2 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isFirstTimeBindBankCard(um.getUserId());
        logger.debug("??????????????????????????????????????? traceId:{},userId:{},isFirstTimeBindBankCard:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            Integer integrals = 100;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("???????????????????????????traceId:{},userId:{} missionId:{},addModel:{}",
                        um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
                return false;
            }
            publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_BINDBANK_KEY, um.getUserId());
            //?????????????????????
            userRedis.hset(String.format(UserRedisKeys.USER_RED_DOT_KEY, um.getUserId()), UserInfoExtensionFields.USER_RED_DOT_WELFARE_KEY, "1");
        }
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:20
     * @Description ?????????????????????
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean bindCertCardFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 1 || um.getMissionId() != 1 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isFirstTimeBindCertCard(um.getUserId());
        logger.debug("????????????????????????????????? traceId:{},userId:{} isFirstTimeBindCertCard:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            Integer integrals = 100;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("???????????????????????????traceId:{},userId:{} missionId:{},addModel:{}",
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
     * @Description ???????????????20
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean payFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 4 || um.getMissionId() != 4 || um.getUserId() == null || um.getMoney() == null) {
            return false;
        }
        boolean flag = userBusinessDao.isFirstTimePay(um.getUserId(), um.getMoney());
        logger.debug("???????????????????????????20 traceId:{},userId:{} money:{} isFirstTimePay20 :{},addModel:{}",
                um.getTraceId(), um.getUserId(), um.getMoney(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            if (um.getMoney() < 20) {
                //??????????????????
                userIntegralCommService.missionFailed(um.getTraceId(), um.getUserId(), um.getMissionId());
            } else {
                Integer integrals = 100;
                if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                    logger.error("???????????????????????????traceId:{},userId:{} missionId:{},addModel:{}",
                            um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
                    return false;
                }
            }
            publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_SINGLEPAY_TWENTY_KEY, um.getUserId());
            //?????????????????????
            userRedis.hset(String.format(UserRedisKeys.USER_RED_DOT_KEY, um.getUserId()), UserInfoExtensionFields.USER_RED_DOT_WELFARE_KEY, "1");
        }
        return false;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 16:43
     * @Description ??????????????????????????????
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean followSchemeFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 5 || um.getMissionId() != 5 || um.getUserId() == null) {
            return false;
        }
        boolean flag = userBusinessDao.isFirstTimeFollowScheme(um.getUserId());
        logger.debug("?????????????????????????????? traceId:{},userId:{} isFirstFollowScheme:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            logger.debug("??????????????????????????????traceId:{},userId{},missionId{},addModel:{}",
                    um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
            int integrals = 100;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("???????????????????????????traceId:{},userId:{} missionId:{},addModel:{}",
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
     * @Description ?????????????????????????????????
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean buySchemeFilter(UserMissionAddModel um) {
        if (um.getMissionType() != 6 || um.getUserId() == null || um.getLotteryId() == null) {
            return false;
        }
        boolean flag = userBusinessDao.isFirstTimeBuyScheme(um.getUserId(), um.getLotteryId());
        logger.debug("????????????????????????????????? traceId:{},userId:{}, lotteryId:{} isFirstTimeBuyScheme:{},addModel:{}",
                um.getTraceId(), um.getUserId(), um.getLotteryId(), flag, GfJsonUtil.toJSONString(um));
        if (flag) {
            int integrals = 200;
            //????????????????????????
            if (um.getLotteryId() == 90) {
                if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), new Long(13), integrals)) {
                    logger.error("???????????????????????????????????????????????????traceId:{},userId:{} lotteryId:{},addModel:{}",
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
     * @Description ????????????????????????
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
            //????????? 10??????
            integrals = 10;
        }
        if (continiousSignDays == 1) {
            //????????? 15??????
            integrals = 15;
        }
        if (continiousSignDays >= 2) {
            //??????????????????30??????
            integrals = 30;
        }
        if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
            logger.error("???????????????????????????traceId:{},userId:{} missionId:{},addModel:{}",
                    um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
            return false;
        }
        String description = "????????????";
        if (publicCommonRedisUtil.isInSignInTodaySet(um.getUserId())) {
            return false;
        }
        return userIntegralCoreService.updateUserIntegral(um.getTraceId(), um.getUserId(), integrals, 1, um.getMissionId(), description);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 17:15
     * @Description ????????????????????????????????????
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional(readOnly = false)
    public boolean dailyBuyScheme(UserMissionAddModel um) {
        if (um.getMissionId() != 6 || um.getUserId() == null || um.getMoney() == null) {
            return false;
        }
        //??????????????????????????????
        Integer integralBuyMax = UserContext.USER_DAILYBUY_INTEGRAL_UPPER;
        //????????????????????????????????????
        double speed = findIntegralSpeed(um.getUserId());
        Integer integralToAdd = 0;
        Integer integralToday = publicCommonRedisUtil.getUserDailyIntegral(um.getUserId());
        logger.debug("?????????????????????????????????:traceId:{},userId:{} integralToday:{},addModel:{}",
                um.getTraceId(), um.getUserId(), integralToday, GfJsonUtil.toJSONString(um));
        if (integralToday >= integralBuyMax) {
            return true;
        }
        integralToAdd = (int) (Math.floor(um.getMoney().intValue() * speed));
        if ((integralToAdd + integralToday) > integralBuyMax) {
            integralToAdd = integralBuyMax - integralToday;
        }
        if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integralToAdd)) {
            logger.error("???????????????????????????traceId:{},userId:{} missionId:{},addModel:{}",
                    um.getTraceId(), um.getUserId(), um.getMissionId(), GfJsonUtil.toJSONString(um));
            return false;
        }
        String description = "??????????????????";
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
        //?????????????????????????????????
        Long growupMax = UserContext.USER_DAILYBUY_GROWUP_UPPER;
        Long growupToAdd = new Long(0);
        Long growupToday = publicCommonRedisUtil.getUserDailyBuyGrowup(um.getUserId());
        logger.debug("????????????????????????????????????:userId:{} growupToday:{}", um.getUserId(), growupToday);
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
     * @Description ????????????Id??????????????????
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
     * @Description ????????????????????????
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean dailyShowScheme(UserMissionAddModel um) {
        if (um.getMissionType() != 8 || um.getMissionId() != 8 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isShowSchemeToday(um.getUserId());
        logger.debug("???????????????????????? traceId:{},userId:{},isShowSchemeToday:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (!flag) {
            Integer integrals = 50;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("???????????????????????????traceId:{},userId:{} missionId:{},addModel:{}",
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
     * @Description ????????????????????????
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean dailyShare(UserMissionAddModel um) {
        if (um.getMissionType() != 9 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isSharedToday(um.getUserId());
        logger.debug("??????????????????????????? traceId:{},userId:{} isShareToday:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (!flag) {
            Integer integrals = 50;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("???????????????????????????traceId:{},userId:{} missionId:{},addModel:{}",
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
     * @Description ????????????????????????
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_WRITE)
    public boolean dailyComment(UserMissionAddModel um) {
        if (um.getMissionType() != 10 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isCommentToday(um.getUserId());
        logger.debug("???????????????????????????traceId:{},userId:{} isCommentToday:{},addModel:{}",
                um.getTraceId(), um.getUserId(), flag, GfJsonUtil.toJSONString(um));
        if (!flag) {
            Integer integrals = 50;
            if (!userIntegralCommService.completeMission(um.getTraceId(), um.getUserId(), um.getMissionId(), integrals)) {
                logger.error("???????????????????????????traceId:{},userId:{} missionId:{},addModel:{}",
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
     * @Description ????????????(?????????????????????>=5???)
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
            //Java???weekday=1?????????????????????weekday=2?????????????????????...;weekday=7?????????????????????
            //???????????????(1???????????????2???????????????3???????????????4???????????????5???????????????6???????????????0????????????)
            Integer weekday = c.get(Calendar.DAY_OF_WEEK) - 1;
            Set<String> days = publicCommonRedisUtil.smembersForvevrdaysByWeek(um.getUserId());
            //?????????????????????????????????????????????
            if (!days.contains(weekday.toString())) {
                List<UserIntegralMissionResult> missList = userAccIntegralDao.getMissionsInfoForeverDaysByUserId(um.getUserId());
                if (missList != null && missList.size() > 0) {
                    UserIntegralMissionResult userMiss = missList.get(0);
                    if (userMiss.getMisId() != null && userMiss.getMisId().intValue() == MissionTypeEnum.FOREVERLOVE.getIndex()) {
                        if (userMiss.getCount() < userMiss.getMissCounts() && weekday != null && !days.contains(weekday.toString())) {
                            if (userMiss.getCreateDate() == null || userMiss.getEndMisId() == null || userMiss.getEndMisId().longValue() == 0) {
                                //????????????????????????????????????????????????????????????
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
                                logger.debug("????????????insert???traceId:{},userId:{} mission:{}", um.getTraceId(), um.getUserId(), GfJsonUtil.toJSONString(missM));
                                userAccIntegralDao.addUserEndMission(missM);
                            } else {
                                //??????????????????????????????count??????
                                UserIntegralMissionModel missM = new UserIntegralMissionModel();
                                missM.setEndMisId(userMiss.getEndMisId());
                                missM.setCount(userMiss.getCount() + 1);
                                missM.setMisType(userMiss.getMisType());
                                missM.setLastDayWeek(userMiss.getLastDayWeek());
                                missM.setWeekDay(weekday);
                                missM.setUserId(um.getUserId());
                                missM.setMisId(userMiss.getMisId().longValue());
                                missM.setTraceId(um.getTraceId());
                                logger.debug("????????????update???traceId:{},userId:{} mission:{}", um.getTraceId(), um.getUserId(), GfJsonUtil.toJSONString(missM));
                                userAccIntegralDao.updateUserEndMissionCount(missM);
                            }
                        }
                        //??????????????????
                        days = publicCommonRedisUtil.smembersForvevrdaysByWeek(um.getUserId());
                        if (days.size() >= userMiss.getMissCounts()) {
                            if (userMiss.getIsEnd() != 1) {
                                //????????????????????????5?????????????????????
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
                                logger.debug("????????????update???traceId:{},userId:{} mission:{}", um.getTraceId(), um.getUserId(), GfJsonUtil.toJSONString(missM));
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
     * @Description ?????????????????????????????????????????????
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    public boolean dailyLogin(UserMissionAddModel um) {
        if (um.getMissionType() != 30 || um.getMissionId() != 30 || um.getUserId() == null) {
            return false;
        }
        Boolean flag = userBusinessDao.isLoginToday(um.getUserId());
        logger.debug("???????????????????????????traceId:{} userId:{} isLoginToday:{}", um.getTraceId(), um.getUserId(), flag);
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
     * @Description ??????????????????
     */
    private Boolean IsBuyedUser(Long userId) {
        logger.debug("?????????????????? userId {}", userId);
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
