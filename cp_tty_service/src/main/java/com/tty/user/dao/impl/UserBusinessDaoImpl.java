package com.tty.user.dao.impl;

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.UserBusinessDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @author shenwei
 * @create 2016-12-15
 */

@Repository("UserBusinessDao")
public class UserBusinessDaoImpl implements UserBusinessDao {

    private final Logger logger = LoggerFactory.getLogger(UserBusinessDaoImpl.class);
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;

    /**
     * @Author shenwei
     * @Date 2016/12/21 20:52
     * @Description 用户是否首次进入积分中心
     */
    @Override
    public boolean isFirstEnterIntegral(Long userId) {
        return !publicCommonRedisUtil.isInEnterIntegralSet(userId);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 16:18
     * @Description 用户是否首次下单某彩种
     */
    @Override
    public boolean isFirstTimeBuyScheme(Long userId, Integer lotteryId) {
        if (publicCommonRedisUtil.isInFirstBuySchemeSet(userId, lotteryId)) {
            return false;
        }
        String redisKey = String.format(UserRedisKeys.USER_BUYSCHEME_KEY, lotteryId);
        publicCommonRedisUtil.insertIntoRedisSet(redisKey, userId);
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 16:35
     * @Description 用户是否首次大神跟单
     */
    @Override
    public boolean isFirstTimeFollowScheme(Long userId) {
        if (publicCommonRedisUtil.isInFirstFollowSchemeSet(userId)) {
            return false;
        }
        publicCommonRedisUtil.insertIntoRedisSet(UserRedisKeys.USER_FOLLOWSCHEME_KEY, userId);
        return true;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/18 16:47
     * @Description 用户是否首次充值满20
     */
    @Override
    public boolean isFirstTimePay(Long userId, Float money) {
        return !publicCommonRedisUtil.isInFirstSinglePaySet(userId);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/19 16:46
     * @Description 用户是否首次绑定手机
     */
    @Override
    public boolean isFirstTimeBindPhone(Long userId) {
        return !publicCommonRedisUtil.isInFirstBindPhoneSet(userId);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 18:54
     * @Description 用户是否首次绑定银行卡
     */
    @Override
    public boolean isFirstTimeBindBankCard(Long userId) {
        return !publicCommonRedisUtil.isInFirstTimeBindBankCardSet(userId);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 18:55
     * @Description 用户是否首次绑定身份证
     */
    @Override
    public boolean isFirstTimeBindCertCard(Long userId) {
        return !publicCommonRedisUtil.isInFirstTimeBindCertCardSet(userId);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:27
     * @Description 今日是否晒单
     */
    @Override
    public boolean isShowSchemeToday(Long userId) {
        return publicCommonRedisUtil.isInShowSchemeTodaySet(userId);
    }

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:28
     * @Description 今日是否分享
     */
    @Override
    public boolean isSharedToday(Long userId) {
        return publicCommonRedisUtil.isInShareTodaySet(userId);
    }


    /**
     * @Author shenwei
     * @Date 2016/12/21 21:00
     * @Description 今日是否评论
     */
    @Override
    public boolean isCommentToday(Long userId) {
        return publicCommonRedisUtil.isInCommentTodaySet(userId);
    }

    /**
     * @Author shenwei
     * @Date 2017/1/6 17:46
     * @Description 今日是否登录
     */
    @Override
    public boolean isLoginToday(Long userId) {
        return publicCommonRedisUtil.isInLoginTodaySet(userId);
    }

    /**
     * @Author shenwei
     * @Date 2017/1/16 15:43
     * @Description 是否购彩用户
     */
    @Override
    public boolean isBuyedUser(Long userId) {
        try {
            return false;
            // return schemeService.isBuyedUser(userId, "");
        } catch (Exception e) {
            logger.error("调用订单rpc获取是否购彩用户异常,userId:{} stackTrace:{}", userId, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }
}
