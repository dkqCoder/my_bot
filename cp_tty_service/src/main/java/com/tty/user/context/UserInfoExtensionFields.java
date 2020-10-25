package com.tty.user.context;

/**
 * @author zhudonghai
 * @Date 2016-12-19
 * @Description redis缓存用户信息用到的field
 */
public class UserInfoExtensionFields {
    /**
     * 用户积分余额
     */
    public final static String USER_INTEGRAL_BALANCE_FIELD = "integral";

    /**
     * @Author shenwei
     * @Date 2017/1/3 19:58
     * @Description 获取用户等级
     */
    public final static String USER_LEVEL = "level";

    /**
     * @Author shenwei
     * @Date 2017/1/4 13:04
     * @Description 用户成长值
     */
    public final static String USER_GROWUPS = "growups";

    /**
     * 积分打败多少彩友
     */
    public final static String USER_BEAT_FIELD = "beat";

    /**
     * 绑定银行卡，0-未做，1-已做，2-已领取
     */
    public final static String USER_RED_DOT_ISBINDBANK_KEY = "isbindbank";

    /**
     * 首次购彩，0-未做，1-已做，2-已领取
     */
    public final static String USER_RED_DOT_FIRSTBUY_KEY = "firstbuy";

    /**
     * 疯狂抢彩金，0-未做，1-已做（默认传0）
     */
    public final static String USER_RED_DOT_ISCRAZYROBCJCARD_KEY = "iscrazyrobcjcard";

    /**
     * 彩票中奖数
     */
    public final static String USER_RED_DOT_WINCOUNT_KEY = "wincount";

    /**
     * 追号中记录数
     */
    public final static String USRE_RED_DOT_CHASECOUNT_KEY = "chasecount";

    /**
     * 20元
     */
    public final static String USER_RED_DOT_BUYSTATUS20_KEY = "buystatus20";

    /**
     * 118元 :
     */
    public final static String USER_RED_DOT_BUYSTATUS118_KEY = "buystatus118";

    /**
     * 中奖两次 :
     */
    public final static String USER_RED_DOT_AWARDSTATUS_KEY = "awardstatus";

    //福利任务
    public final static String USER_RED_DOT_WELFARE_KEY = "welfare";
    
    /**
     * 抢彩金倒计时信息
     */
    public final static String USER_RED_DOT_FREEWELFAREINFO_KEY = "freewelfareinfo";
}
