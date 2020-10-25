package com.tty.user.service.impl;

import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.common.utils.Result;
import com.tty.user.common.utils.DateUtils;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.context.UserInfoExtensionFields;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.controller.model.params.UserSignInParam;
import com.tty.user.controller.model.result.UserSignInModel;
import com.tty.user.dao.UserSignInDao;
import com.tty.user.dao.pojo.UserSigninRecord;
import com.tty.user.service.UserIntegralCoreService;
import com.tty.user.service.UserSignInService;
import com.tty.user.service.mission.UserIntegralCommService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by shenwei on 2016/12/14.
 * 用户签到相关实现
 */

@Service("userSignInService")
public class UserSignInServiceImpl extends BaseDao<UserSigninRecord> implements UserSignInService {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private UserSignInDao userSignInDao;
    @Autowired
    private UserIntegralCommService userIntegralCommService;
    @Autowired
    private UserIntegralCoreService userIntegralCoreService;
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;

    /**
     * @Author shenwei
     * @Date 2016/12/18 16:04
     * @Description 用户签到
     */
    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    public Result SignIn(String traceId, Long userId, UserSignInParam model) throws Exception {
        Result result = new Result();
        if (publicCommonRedisUtil.isInSignInTodaySet(userId)) {
            result.setCode(-3);
            result.setMsg("用户今日已签到!");
            return result;
        }
        Integer expireSeconds = DateUtils.getSecondsToTomorrow().intValue();
        publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_DAILY_SIGNIN_KEY, userId, expireSeconds);
        //获取昨天已连续签到次数(不跨月)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        Long continiousDays = userSignInDao.getContiniousSigninDays(userId, yesterday);
        Integer integralToAdd = 0;
        if (continiousDays == 0) {
            integralToAdd = 10;
        } else if (continiousDays == 1) {
            integralToAdd = 15;
        } else {
            integralToAdd = 30;
        }

        //更新redis
        publicCommonRedisUtil.increaseUserIntegral(userId, UserInfoExtensionFields.USER_INTEGRAL_BALANCE_FIELD, integralToAdd);
        UserSigninRecord userSigninRecord = new UserSigninRecord();
        userSigninRecord.setUserId(userId);
        userSigninRecord.setUserDate(new Date());
        userSigninRecord.setSignDays(continiousDays + 1);
        //删除连续签到缓存
        publicCommonRedisUtil.delSignInDays(userId);

        //异步保存到数据库
        /**
         * 1.更新签到set
         * 2.增加积分
         * 3.异步保存签到信息到数据，包含任务完成
         */
        final Integer integralcount = integralToAdd;
        threadPoolTaskExecutor.execute(() -> {
            userIntegralCommService.asyncSignRecord(userSigninRecord, traceId, integralcount);
        });
        UserSignInModel userSignInModel = userIntegralCommService.getUserSignInModel(userId);
        publicCommonRedisUtil.flushUserSignInfo(userSignInModel, String.valueOf(userId));
        result.setCode(Result.SUCCESS);
        result.setData(userSignInModel);
        result.setMsg(Result.MSG_SUCCESS_DESC);
        return result;
    }


    /**
     * @Author shenwei
     * @Date 2016/12/18 15:39
     * @Description 获取连续签到天数
     */
    public Long getContiniousSigninDays(Long userId, Date signInDate) {
        return userSignInDao.getContiniousSigninDays(userId, signInDate);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 15:40
     * @Description 获取当月已签到天数
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public Integer getCurrentMonthSinginDays(Long userId) {
        return userSignInDao.getCurrentMonthSinginDays(userId);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/19 16:37
     * @Description 当天是否已签到
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public Boolean isAlreadySigned(Long userId) {
        return userSignInDao.IsAlreadySigned(userId);
    }

    /**
     * 获取签到信息
     *
     * @param userId
     * @return
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public UserSignInModel getUserSignInfo(Long userId) {
        UserSignInModel sign = null;
        String signInfoStr = userRedis.get(String.format(UserRedisKeys.USER_SIGN_INFO, userId));
        if (StringUtils.isNotBlank(signInfoStr)) {
            sign = GfJsonUtil.parseObject(signInfoStr, UserSignInModel.class);
        } else {
            sign = new UserSignInModel();
            Long balance = userIntegralCoreService.getUserIntegralBalance(userId);
            Long continiousDays = new Long(0);
            Boolean isSign = publicCommonRedisUtil.isInSignInTodaySet(userId);
            if (isSign != null && isSign) {
                continiousDays = getContiniousSigninDays(userId, DateUtil.getDate());
            } else {
                continiousDays = getContiniousSigninDays(userId, DateUtil.preDate(1));
            }
            Integer integralToAdd = 0;
            if (continiousDays == 0) {
                integralToAdd = 10; //明日再签增加积分
            } else if (continiousDays == 1) {
                integralToAdd = 15;
            } else if (continiousDays >= 2) {
                integralToAdd = 30;
            }
            sign.setContiniousDays(continiousDays.intValue());
            sign.setIntegrals(balance);
            sign.setIntegralsToAdd(integralToAdd);
            publicCommonRedisUtil.flushUserSignInfo(sign, String.valueOf(userId));
        }
        return sign;
    }
}
