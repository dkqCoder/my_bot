package com.tty.data.context;/**
 * Created by shenwei on 2017/2/16.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenwei
 * @create 2017-02-16
 */
public class BasedataContext {
    /**
     * @Author shenwei
     * @Date 2017/2/16 12:47
     * @Description 期次批量导入数据量
     */
    public final static Integer ISSUE_BATCH_NUM = 10000;

    /**
     * 传统足彩ID
     */
    //传统足彩 1胜负彩 2四场进球彩 15六场半全场 19任选九
    public final static List<Integer> CTZC_LOTTERY_IDS = new ArrayList<>();

    public final static List<Integer> AGAINST_LOTTERY_IDS = new ArrayList<>();
    static {
        CTZC_LOTTERY_IDS.add(1);
        CTZC_LOTTERY_IDS.add(2);
        CTZC_LOTTERY_IDS.add(15);
        CTZC_LOTTERY_IDS.add(19);

        AGAINST_LOTTERY_IDS.add(45);
        AGAINST_LOTTERY_IDS.add(90);
        AGAINST_LOTTERY_IDS.add(91);
    }

    //双色球
    public final static Integer LOTTERY_SSQ_ID = 5;
    public final static Integer LOTTERY_FC3D_ID = 6;
    public final static String LOTTERY_SSQ_WEEK = "240";
    //大乐透
    public final static Integer LOTTERY_CJDLT_ID = 39;
    public final static String LOTTERY_CJDLT_WEEK = "136";

    //胜负彩
    public final static Integer LOTTERY_SFC_ID = 1;
    //四场进球彩
    public final static Integer LOTTERY_JQC_ID = 2;
    // 七星彩
    public final static Integer LOTTERY_QXC_ID = 3;
    // 七星彩开始星期
    public final static String LOTTERY_QXC_WEEK = "250";

    //六场半全场
    public final static Integer LOTTERY_BQC_ID = 15;
    //七乐彩
    public final static Integer LOTTERY_QLC_ID = 13;
    //七乐彩开始星期
    public final static String LOTTERY_QLC_WEEK = "135";

    //任选九
    public final static Integer LOTTERY_RXJ_ID = 19;
    //北京单场
    public final static Integer LOTTERY_BJDC_ID = 45;
    //十一运夺金
    public final static Integer LOTTERY_SYYDJ_ID = 62;
    //江西快3
    public final static Integer LOTTERY_JXK3_ID = 67;
    //广西快3
    public final static Integer LOTTERY_GXK3_ID = 68;
    //吉林快3
    public final static Integer LOTTERY_JLK3_ID = 69;
    //11选5
    public final static Integer LOTTERY_SYX5_ID = 70;
    //广东11选5
    public final static Integer LOTTERY_GD11X5_ID = 72;
    //新疆11选5
    public final static Integer LOTTERY_XJ11X5_ID = 74;
    //湖北11选5
    public final static Integer LOTTERY_HB11X5_ID = 78;
    //江西11选5
    public final static Integer LOTTERY_JX11X5_ID = 75;
    //重庆快乐十分
    public final static Integer LOTTERY_CQKLSF_ID = 81;
    //吉林时时彩
    public final static Integer LOTTERY_JLSSC_ID = 29;
    //竞彩足球
    public final static Integer LOTTERY_JCZQ_ID = 90;
    //竞彩篮球
    public final static Integer LOTTERY_JCLQ_ID = 91;
    //猜冠军
    public final static Integer LOTTERY_JCGJ_ID = 95;

    //过关期次功能块应剔出胜负彩、任选九、6场半全场、4场进球彩、竞彩足球、竞彩篮球、猜冠军这几个彩种的开奖功能
    public final static Integer[] PASS_ISSUE_LIMIT = new Integer[]{LOTTERY_SFC_ID, LOTTERY_JQC_ID, LOTTERY_BQC_ID, LOTTERY_RXJ_ID, LOTTERY_JCZQ_ID, LOTTERY_JCLQ_ID, LOTTERY_JCGJ_ID};

    //玩法 胜平负
    public final static Integer PLAYTYPE_SPF_ID = 9006;
    //玩法 让球胜平负
    public final static Integer PLAYTYPE_RQSPF_ID = 9001;
    //让分胜负
    public final static int PLAYTYPE_RFSF_ID = 9101;
    //胜负
    public final static int PLATTYPE_SF_ID = 9102;
    //大小分
    public final static int PLAYTYPE_DXF_ID = 9104;

    //投注 胜
    public final static String BET_OPTION_WIN = "3";
    //投注平
    public final static String BET_OPTION_DRAW = "1";
    //投注负
    public final static String BET_OPTION_LOSE = "0";

    //推送接口配置KEY
    public final static String PUSH_API_URL_KEY = "basedata.push.api";

    public final static String BASEDATA_ORDER_PASS_QUEUE = "order.pass.queue";
    public final static String BASEDATA_ORDER_WIN_QUEUE = "order.pass.win.queue";

    //比赛结果主胜
    public final static Integer MATCH_RESULT_HOMEWIN = 1;
    //比赛结果平局
    public final static Integer MATCH_RESULT_DRAW = 2;
    //比赛结果客胜
    public final static Integer MATCH_RESULT_VISITWIN = 3;
    //无结果
    public final static Integer MATCH_RESULT_NORESULT = 0;

}
