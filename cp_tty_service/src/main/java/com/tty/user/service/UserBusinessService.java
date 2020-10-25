package com.tty.user.service;


import com.tty.user.dto.mission.UserMissionAddModel;

/**
 * Created by shenwei on 2016/12/15.
 */
public interface UserBusinessService {

    //绑定手机相关任务判断
    boolean bindPhoneFilter(UserMissionAddModel um);

    //绑定银行卡相关任务判断
    boolean bindBankCardFilter(UserMissionAddModel um);

    //绑定身份证相关任务判断
    boolean bindCertCardFilter(UserMissionAddModel um);

    //充值相关任务判断
    boolean payFilter(UserMissionAddModel um);

    //跟单相关任务判断
    boolean followSchemeFilter(UserMissionAddModel um);

    //购彩相关任务判断
    boolean buySchemeFilter(UserMissionAddModel um);

    //签到相关任务判断
    boolean signInFilter(UserMissionAddModel um) throws Exception;

    //每日购彩相关判断
    boolean dailyBuyScheme(UserMissionAddModel um);

    //每日购彩成长值相关判断
    boolean dailyBuySchemeGrowup(UserMissionAddModel um);

    //每日晒单相关判断
    boolean dailyShowScheme(UserMissionAddModel um);

    //每日分享方案相关判断
    boolean dailyShare(UserMissionAddModel um);

    //每日评论相关判断
    boolean dailyComment(UserMissionAddModel um);

    //朝夕相处
    void checkForeverLoveDaysByWeek(UserMissionAddModel um) throws Exception;

    //每日登录成长值消费
    boolean dailyLogin(UserMissionAddModel um);

}
