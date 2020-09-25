package com.tty.ticket.context;

/**
 * Created by yys on 2017/7/4.
 */
public class TicketRedisKeyContext {

    /**
     * 彩种每天期次数数据
     * lotteryId
     * yyyyMMdd
     */
    public final static String TICKET_LOTTERY_DAILY_ISSUE = "ticket:lottery:daily:issue:%s:%s";

    /**
     * 电子票DTO缓存
     * ticketNo
     */
    public final static String TICKET_TICKET_DTO = "ticket:ticket:dto:%s";

    /**
     * 异常电子票DTO缓存
     * ticketNo
     * hash类型
     */
    public final static String TICKET_TICKET_DTO_EXCEPTION = "ticket:ticket:dto:exception";

    /**
     * 出票商缓存
     * hash 类型
     */
    public final static String TICKET_AGENTS = "ticket:agents";

    /**
     * 商户缓存
     * hash 类型
     */
    public final static String TICKET_MERCHANTS = "ticket:merchants";

    /**
     * 资金明细分布式序列缓存
     */
    public static final String TICKET_AGENT_FUND_DETAIL_INCR = "ticket:fundDetail:serialNo";

    /**
     * 分票路由缓存
     * lotteryId
     */
    public static final String TICKET_ROUTE = "ticket:route:%s";

    /**
     * 系统配置缓存前缀
     * code
     */
    public static final String TICKET_CONFIG = "ticket:config:%s";

    /**
     * 城市信息缓存
     * key cityId
     * value cityent
     */
    public final static String TICKET_CITY = "ticket:city";

    /**
     * 彩种信息缓存
     * agentId
     */
    public final static String TICKET_LOTTERY = "ticket:lottery";

    /**
     * 用于生产出票赔率表序列号
     */
    public final static String TICKET_PRINT_SP_SERIAL = "ticket:printSp:serial";

    /**
     * 电子票监控缓存
     */
    public final static String TICKET_MONITOR = "ticket:monitor:lottery";

    /**
     * 省份信息缓存
     * agentId
     */
    public final static String TICKET_PROVINCE = "ticket:province";

    /**
     * 高频彩开奖号码获取配置
     * lotteryId
     */
    public static final String TICKET_WINNUMBER_QUERY_SWITCH = "ticket:winnumber:query:switch";

    /**
     * 出票商的默认彩种
     */
    public static final String TICKET_QUERY_SWITCH = "ticket:query:switch";

    /**
     * 高频彩获取task的锁
     * lotteryId
     */
    public static final String TICKET_GPCNUMBER_TASK_LOCK = "ticket:gpcnumber:task:lock:%s:%s";

    /**
     * 高频彩期次信息获取task的锁
     * lotteryId
     */
    public static final String TICKET_GPCISSUEINFO_TASK_LOCK = "ticket:gpcissueinfo:task:lock:%s:%s";


    /**
     * 高频彩开奖号码告警次数
     */
    public static final String TICKET_GPCNUMBER_WARNING_TIMES = "ticket:gpcnumber:warning:%s:%s";

    /**
     * 用于生产hezhong序列号
     * yyyyMMdd
     */
    public final static String TICKET_HEZHONG_ID_SERIAL = "ticket:hezhong:id:%s";
    

}
