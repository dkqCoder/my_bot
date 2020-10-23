package com.tty.task.context;

/**
 * @Author: dukeqiang
 * @Date: 2020/10/12 18:55
 */
public class CrawlingUrlContext {
    /**
     * 竞彩官网胜平负/让球胜平负玩法 url
     */
    public final static String JCGW_WDL_URL = "https://i.sporttery.cn/odds_calculator/get_odds?i_format=json&poolcode[]=hhad&poolcode[]=had";

    /**
     * 竞彩官网比分玩法 url
     */
    public final static String JCGW_CRS_WDL_URL = "https://i.sporttery.cn/odds_calculator/get_odds?i_format=json&poolcode[]=crs";

    /**
     * 竞彩官网进球数玩法 url
     */
    public final static String JCGW_GOALS_WDL_URL = "https://i.sporttery.cn/odds_calculator/get_odds?i_format=json&poolcode[]=ttg";

    /**
     * 竞彩官网半全场胜平负玩法 url
     */
    public final static String JCGW_HAFU_WDL_URL = "https://i.sporttery.cn/odds_calculator/get_odds?i_format=json&poolcode[]=hafu";

    /**
     * 竞彩官网赛果信息 url
     */
    public final static String JCGW_POOL_RS_URL = "";
}
