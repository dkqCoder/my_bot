package com.tty.data.context;

/**
 * @author yinyansheng
 * @date 2/14/2017
 * @Description
 */
public class BasedataRedisKeys {

    /**
     * 后台采种缓存
     */
    public final static String BASEDATA_ADMIN_LOTTERY_INFO = "basedata:admin:lottery:%s";
    /**
     * @Author yinyansheng
     * @Date 2017/2/14 14:28
     * @Description 期次数据
     */
    public final static String BASEDATA_ISSUES = "basedata:issues";

    /**
     * @Author yinyansheng
     * @Date 2017/2/14 14:28
     * @Description 竞彩足球对阵数据
     */
    public final static String BASEDATA_JCZQ_MATCHS = "basedata:jczq:matchs";

    /**
     * @Author yinyansheng
     * @Date 2017/2/14 14:28
     * @Description 竞彩足球销售状态数据
     */
    public final static String BASEDATA_JCZQ_SALESTATES = "basedata:jczq:salesates";

    /**
     * @Author yinyansheng
     * @Date 2017/2/14 14:28
     * @Description 竞彩篮球对阵数据
     */
    public final static String BASEDATA_JCLQ_MATCHS = "basedata:jclq:matchs";

    /**
     * @Author yinyansheng
     * @Date 2017/2/14 14:28
     * @Description 竞彩篮球销售状态数据
     */
    public final static String BASEDATA_JCLQ_SALESTATES = "basedata:jclq:salesates";

    /**
     * @Author yinyansheng
     * @Date 2017/2/14 14:28
     * @Description 北京单场对阵数据
     */
    public final static String BASEDATA_BJDC_MATCHS = "basedata:bjdc:matchs";

    /**
     * @Author yinyansheng
     * @Date 2017/2/14 14:28
     * @Description 北京单场销售状态数据
     */
    public final static String BASEDATA_BJDC_SALESTATES = "basedata:bjdc:salesates";


    /**
     * @Author yinyansheng
     * @Date 2017/2/14 14:28
     * @Description 传统足彩对阵数据
     */
    public final static String BASEDATA_CTZC_MATCHS = "basedata:ctzc:matchs";


    /**
     * 后台推送信息发送记录
     * zxh
     */
    public final static String BASEDATA_PUSH_RECORD_LOTTERY_ISSUE = "basedata:push:record:%s:%s";

    /**
     * 单关配置数据
     */
    public final static String BASEDATA_MATCH_CFG = "basedata:dgmatch:cfg";

    /**
     * 支付配置
     * key -> id
     * value -> BasedataPayTypeENT
     */
    public final static String BASEDATA_PAY_CFG = "basedata:paycfg";
    public final static String BASEDATA_ALLPAY_CFG = "basedata:allpaycfg";

    public final static String BASEDATA_PLAY_CFG = "basedata:playcfg";
    /**
     * 用户支付配置
     */
    public final static String BASEDATA_PAY_CUSTOMER_CFG = "basedata:paycustomercfg";

    /**
     * 用户支付配置延迟缓存
     */
    public final static String BASEDATA_PAY_CUSTOMER_CFG_DELAY = "basedata:paycustomercfgdelay";

    /**
     * 比赛开始时推送
     */
    public final static String BASEDATA_MATCH_START_PUSH = "basedata:match:start:push:%s:%s";

    /**
     * 赛事历史战绩
     */
    public final static String BASEDATA_MATCH_HIS_RECORDS = "basedata:match:hisRecords:%s";

}
