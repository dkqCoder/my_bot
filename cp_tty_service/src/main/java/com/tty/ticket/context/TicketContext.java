package com.tty.ticket.context;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yys on 2017/7/4.
 */
public class TicketContext {

    public static final Integer ERROR = -1;
    public static final Integer SUCCESS = 1;

    /**
     * 每天总秒数
     */
    public final static int DAY_SECONDS = 24 * 60 * 60;

    /**
     * 资金类型code
     **/
    public static final String FUND_TYPE_PAIJIANG = "0002001";
    public static final String FUND_TYPE_TIKUAN = "0001001";
    public static final String FUND_TYPE_CHONGZHI = "1001001";
    public static final String FUND_TYPE_CHUPIAO = "1002001";
    public static final String FUND_TYPE_CHEXIAO = "1001003";

    /**
     * 提款状态
     **/
    public static final Integer DISTILL_STATUS_HANDLERING = 0;
    public static final Integer DISTILL_STATUS_SUCCESS = 1;
    public static final Integer DISTILL_STATUS_FAIL = -1;


    /**
     * 默认分票时间 999999999
     */
    public static final Long ROUTE_DEFAULT_SECONDS = 999999999L;

    /**
     * 需要主动查询票状态的出票商配置KEY
     */
    public static final String TICKET_CONFIG_QUERY_TASK_AGENT_IDS = "QUERY_TASK_AGENT_IDS";


    /**
     * 临时需要主动查询票状态的出票商配置KEY
     */
    public static final String TICKET_CONFIG_TEMPORARY_QUERY_TASK_AGENT_IDS = "TEMPORARY_QUERY_TASK_AGENT_IDS";

    /**
     * 代领奖人邮箱
     */
    public static final String TICKET_CONFIG_MERCHANT_MAIL = "MERCHANT_MAIL";

    /**
     * 代领奖人手机号
     */
    public static final String TICKET_CONFIG_MERCHANT_MOBILE = "MERCHANT_MOBILE";

    /**
     * 代领奖人身份证
     */
    public static final String TICKET_CONFIG_MERCHANT_CARDNUMBER = "MERCHANT_CARDNUMBER";

    /**
     * 代领奖人姓名
     */
    public static final String TICKET_CONFIG_MERCHANT_NAME = "MERCHANT_NAME";

    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    public static final String XML_HEADER_GBK = "<?xml version=\"1.0\" encoding=\"GBK\"?>";

    public static final String XML_CONTENT_TYPE = "text/xml;charset=utf-8";

    /**
     * 分库列表
     */
    public static final List<Long> matrixList = Arrays.asList(0L, 100L, 200L, 300L, 400L, 500L, 600L, 700L, 800L, 900L);

    /**
     * 系统投注方式
     */
    public static final String TICKET_SYS_SEND_TYPE = "SYS_SEND_TYPE";

    /**
     * USC开奖号码地址
     */
    public static final String TICKET_USC_NOTICE_WINNUMBER_PATH = "USC_NOTICE_WINNUMBER_PATH";

    /**
     * USC开奖号码地址
     */
    public static final String TICKET_USC_NOTICE_ISSUEINFO_PATH = "USC_NOTICE_ISSUEINFO_PATH";

    /**
     * 系统投注开关
     */
    public static final String TICKET_SYS_SEND_SWITCH = "SYS_SEND_SWITCH";

    /**
     * 是否启用投注task,A节点（1启用，0不启用）
     */
    public static final String TICKET_CONFIG_SERVER_NODE_A_SWITCH = "ServerNodeA_Switch";

    /**
     * 是否启用投注task,B节点（1启用，0不启用)
     */
    public static final String TICKET_CONFIG_SERVER_NODE_B_SWITCH = "ServerNodeB_Switch";

    /**
     * 高频彩开奖号码告警推送组ID
     */
    public static final Long GPC_WARNING_ALARM_PUSH_GROUP_ID = 1007L;

    /**
     * aoli 特殊规则出票比例
     */
    public static final String TICKET_CONFIG_AOLI_RATION = "AOLI_RATIO";

    /**
     * baoying 分票规则生效开关
     */
    public static final String TICKET_CONFIG_BAOYING_ROUTE_SWITCH = "BAOYING_ROUTE_SWITCH";

    /**
     * aoli 特殊规则出票比例2
     */
    public static final String TICKET_CONFIG_AOLI_RATION_2 = "AOLI_RATIO_2";

    /**
     * aoli 特殊规则出票比例3
     */
    public static final String TICKET_CONFIG_AOLI_RATION_3 = "AOLI_RATIO_3";

    /**
     * aoli 特殊规则出票比例4
     */
    public static final String TICKET_CONFIG_AOLI_RATION_4 = "AOLI_RATIO_4";

    /**
     * aoli 特殊规则出票比例5
     */
    public static final String TICKET_CONFIG_AOLI_RATION_5 = "AOLI_RATIO_5";

    /**
     * aoli 特殊规则出票比例5
     */
    public static final String TICKET_CONFIG_FUNIU_RATION = "FUNIU_RATIO";

    /**
     * 高频彩号码获取报警推送黑名单设置
     */
    public static final String TICKET_CONFIG_GPC_ALARM_BLACKLIST = "GPC_ALARM_BLACKLIST";

    /**
     * 电子票撤单状态
     */
    public static final int TICKET_QUASH_STATUS = 1;

    /**
     * 支付系统编号
     */
    public static final String TICKET_PAYMENT_BRAND_ID = "PAYMENT_BRAND_ID";

    /**
     * 支付系统请求URL
     */
    public static final String TICKET_PAYMENT_WITHDRAW_URL = "PAYMENT_WITHDRAW_URL";

    /**
     * 支付系统请求URL
     */
    public static final String TICKET_PAYMENT_WITHDRAW_QUERY_URL = "PAYMENT_WITHDRAW_QUERY_URL";

    /**
     * 支付系统密码
     */
    public static final String TICKET_PAYMENT_PWD = "PAYMENT_PWD";

}
