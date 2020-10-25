package com.tty.user.dao;


/**
 * Created by shenwei on 2016/12/15.
 */
public interface UserBusinessDao {
    boolean isFirstEnterIntegral(Long userId);

    boolean isFirstTimeBuyScheme(Long userId, Integer lotteryId);

    boolean isFirstTimeFollowScheme(Long userId);

    boolean isFirstTimePay(Long userId, Float money);

    boolean isFirstTimeBindPhone(Long userId);

    boolean isFirstTimeBindBankCard(Long userId);

    boolean isFirstTimeBindCertCard(Long userId);

    boolean isShowSchemeToday(Long userId);

    boolean isSharedToday(Long userId);

    boolean isCommentToday(Long userId);

    //成长值
    boolean isLoginToday(Long userId);

    boolean isBuyedUser(Long userId);
}
