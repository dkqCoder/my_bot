package com.tty.data.context;


import com.tty.data.dto.BuyQuantityErrorWrapDTO;
import com.tty.data.dto.PredictedIssueDataDTO;
import com.tty.data.dto.PredictedMatchDTO;

/**
 * 基础数据自己的数据结构
 */
public class BasedataSharedRedisKeys {

    /**
     * 银行
     */
    public final static String BASEDATA_BANK = "basedata:client:bank";

    /** 城市 */
    public final static String BASEDATA_CITY = "basedata:client:city";
    /** 省 */
    public final static String BASEDATA_PROVINCE = "basedata:client:province";

    /**
     * 后台彩种缓存
     * key -> lotteryId
     * value -> RedisLotteryDTO
     */
    public final static String BASEDATA_LOTTERY = "basedata:client:lottery";

    public final static String BASEDATA_LOTTERY_RELATIONSHIP = "basedata:client:lottery:relationship";

    public final static String BASEDATA_WIN_ISSUIES = "basedata:client:winissue:%s";
    public final static String BASEDATA_WIN_ISSUIE_MATCHES = "basedata:client:winissue:matches:%s";

    /**
     * 可售彩种期次
     * %s -> lotteryId
     * key ->  issueName
     * value -> BasedataIssueENT
     *
     * 高频彩  所有可销售期次+500期已开奖期次
     * 北京单场 10期可销售期次 + 10期已开奖期次
     * 竞彩足球 100期可销售期次 + 10期已开奖期次
     * 竞彩篮球 100期可销售期次 + 10期已开奖期次
     * 其他竞猜足彩 10期可销售期次 + 10期已开奖期次
     *
     * 奖池彩 50期可销售+100期已开奖
     *
     * 其他彩种 50期可销售+500期已开奖
     *
     */
    public final static String BASEDATA_ISSUES_BY_LOTTERY = "basedata:client:issues:%s";

    /**
     * 可售彩种期次
     * %s -> lotteryId
     * key ->  issueId
     * value -> BasedataIssueENT
     */
    public final static String BASEDATA_ISSUESID_BY_LOTTERY = "basedata:client:issuesid:%s";
    /**
     * 限号彩种
     * key -> lotteryId_id
     * value -> BasedataLimitGpcENT
     */
    public final static String BASEDATA_ISSUES_LIMIT = "basedata:client:issues:limit";

    /**
     * 高频彩 限号玩法
     * type hast
     * key -> playTypeId
     * value -> List<String>
     */
    public final static String BASEDATA_ISSUES_GPC_LIMIT = "basedata:client:issues:gpc:limit";

    /**
     * 可售竞彩足球 key -> issueMatchName
     * value ->  RedisJczqMatchDTO
     */
    public final static String BASEDATA_JCZQ_MATCH = "basedata:client:match:jczq";

    /**
     * 竞彩足球预测一周内的数据
     * value ->  {@link PredictedIssueDataDTO , PredictedMatchDTO}
     */
    public final static String BASEDATA_JCZQ_MATCH_PREDICT = "basedata:client:match:predict";

    /**
     * 竞彩足球买量分布一周内的数据
     * value ->  {@link BuyQuantityErrorWrapDTO }
     */
    public final static String BASEDATA_JCZQ_MATCH_BUYQUANTITY = "basedata:client:match:BuyQuantityError";

    /**
     * 可售竞彩篮球 key -> issueMatchName
     * value -> RedisJclqMatchDTO
     */
    public final static String BASEDATA_JCLQ_MATCH = "basedata:client:match:jclq";
    /**
     * 可售北京单场 key -> issueMatchName
     * value -> RedisBjdcMatchDTO
     */
    public final static String BASEDATA_BJDC_MATCH = "basedata:client:match:bjdc";
    /**
     * 可售传统足彩
     * %s -> lotteryId [1,2,15,19]
     * key -> issueMatchName
     * value -> RedisCtzcMatchDTO
     */
    public final static String BASEDATA_CTZC_MATCH = "basedata:client:match:ctzc:%s";
    /**
     * 可售竞彩足球单关固陪
     * key -> issueMatchName
     * valeu -> RedisJczqDggpMatchDTO
     */
    public final static String BASEDATA_DGGP_MATCH = "basedata:client:match:dggp";


    /**
     * 彩种停售对阵
     * key -> issueMatchName
     * RedisJclqMatchStopSaleDT
     * RedisJczqMatchStopSaleDTO
     * RedisBjdcMatchStopSaleDTO
     */
    public final static String BASEDATA_JCLQ_MATCH_STOP_SALE = "basedata:client:stopsale:jclq";
    public final static String BASEDATA_JCZQ_MATCH_STOP_SALE = "basedata:client:stopsale:jczq";
    public final static String BASEDATA_BJDC_MATCH_STOP_SALE = "basedata:client:stopsale:bjdc";


    /**
     * 11选5遗漏值缓存
     */
    public final static String BASEDATA_MISSVALUE_LOTTERYID_ISSUE_TYPE = "basedata:client:missValue:%s:%s";



    /**
     * 可售冷热赛事和特征战绩竞彩足球 key -> issueMatchName
     * value ->  RedisJczqMatchDTO
     */
    public final static String BASEDATA_JCZQAnalysis_MATCH = "basedata:client:match:jczqAnalysis";

    /*-------------开奖信息--------------*/

    /**
     * key -> id
     * value -> BasedataWinTypeENT
     */
    public final static String BASEDATA_WINTYPE = "basedata:client:wintype:%s";

    /**
     * 开奖期次20
     * %s -> lotteryId
     * value -> BasedataIssueENT
     */
    public final static String BASEDATA_WIN_ISSUE = "basedata:client:windetail:issue:%s";


    /**
     * 开奖期次-获奖明细 by issueName
     * %s -> lotteryId
     * key -> issueName
     * value -> Map key->winType value ->IssueWinDetailDTO
     */
    public final static String BASEDATA_WINDETAIL_ISSUENAME = "basedata:client:windetail:issuename:%s";

    /**
     * 开奖期次-获开奖明细 by issueId
     * %s -> lotteryId
     * key -> issueId
     * value -> Map key->winType value ->IssueWinDetailDTO
     */
    public final static String BASEDATA_WINDETAIL_ISSUEID = "basedata:client:windetail:issueid:%s";

    /**
     * 开奖期次-对阵 [45北京单场， 90竞彩足球， 91竞彩篮球]
     * %s -> lotteryId [45 90 91]
     * key -> issueId
     * value -> List [JclqDataDTO, JczqDataDTO, BjdcDataDTO,BasedataMatchCtzcDTO]
     */
    public final static String BASEDATA_WINDETAIL_ISSUEID_MATCH = "basedata:client:windetail:agains:%s";

    /**
     * 开奖期次-对阵 [1胜负彩 2四场进球彩 15六场半全场 19任选九]
     * %s -> lotteryId [1,2,15,19]
     * key -> issueName
     * value -> List [BasedataMatchCtzcDTO]
     */
    public final static String BASEDATA_WINDETAIL_ISSUENAME_CTZC = "basedata:client:windetail:agains:ctzc:%s";


    /**
     * 遗漏数据
     */
    public final static String BASEDATA_MISSINGDATA = "basedata:client:missingdata:%s";

    /* ===============Rpc调用用到的缓存数据==================== */
    /**
     * 未开赛北京单场对阵信息
     * %s issueName
     * key -> issueMatchName
     * value -> com.jdd.basedata.service.dto.BjdcDataDTO
     */
    public final static String BASEDATA_RPC_BJDC_MATCH = "basedata:rpc:bjdc:%s";
    public final static String  BASEDATA_RPC_BJDC_ALL_MATCH = "basedata:rpc:bjdc:all:%s";

    /**
     * 未开赛竞彩足球对阵信息
     * %s -> issueName
     * key -> issueMatchName
     * value -> com.jdd.basedata.service.dto.JczqDataDTO
     */
    public final static String BASEDATA_RPC_JCZQ_MATCH = "basedata:rpc:jczq:%s";



    /**
     * %s -> issueName
     * key -> issueMatchName
     * value -> com.jdd.basedata.service.dto.JclqDataDTO
     */
    public final static String BASEDATA_RPC_JCLQ_MATCH = "basedata:rpc:jclq:%s";
    /**
     * %s_%s -> lotteryId_issueName
     * key -> issueMatchName
     * value -> com.jdd.basedata.service.dto.BjdcDataDTO
     */
    public final static String BASEDATA_RPC_CTZC_MATCH = "basedata:rpc:ctzc:%s_%s";
    /**
     * key->issueMatchName
     * value -> com.jdd.basedata.service.dto.JczqDataDTO
     */
    public final static String BASEDATA_RPC_JCZQ = "basedata:rpc:jczq";

    public final static String BASEDATA_RPC_JCLQ = "basedata:rpc:jclq";

    public final static String BASEDATA_RPC_BJDC = "basedata:rpc:bjdc";

    /**
     * key->issueName
     * value->List[com.jdd.basedata.service.redis.dto.RedisMatchAnalyzeDTO]
     */
    public final static String BASEDATA_FORECAST_ANALYZELIST = "basedata:forecast:analyzelist";

    /**
     * key->MatchId
     * value->Lcom.jdd.basedata.service.redis.dto.RedisMatchAnalyzeDTO
     */
    public final static String BASEDATA_FORECAST_ANALYZE = "basedata:forecast:analyze:%s";

    /**
     * key->MatchId
     * value->List<List<com.jdd.basedata.service.redis.dto.BunchedMatchCommendList>>
     *
     */
    public final static String BASEDATA_BUNCHEDMATCHCOMMENDLIST = "basedata:BunchedMatchCommendList";

    /**
     * 冷热数据分析(竞彩足球)
     */
    public final static String BASEDATA_ANALYSIS_JCZQ = "basedata:client:match:jczqAnalysis";

    /**
     * 竞彩足球 key -> matchId
     * value ->  RedisJczqMatchDTO
     */
    public final static String BASEDATA_JCZQ_MATCH_FOR_MATCHID = "basedata:client:matchId:jczq";
}
