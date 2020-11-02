package com.tty.data.common.util;

import com.jdd.fm.core.utils.DateUtil;
import com.tty.data.dao.entity.BasedataMatchBjdcENT;
import com.tty.data.dao.entity.BasedataMatchJclqENT;
import com.tty.data.dao.entity.BasedataMatchJczqENT;

/**
 * @author yinyansheng
 * @date 2/21/2017
 * @Description
 */
public class SpUtil {

    /**
     * 根据玩法获取数据库相应字段名
     * @param playId
     * @return
     */
    public static String getPlayTypeColumnName(int playId){
        switch (playId){
            case 9001:
                return "s_spf_sp";
            case 9002:
                return "s_rqspf_sp";
            case 9003:
                return "s_jqs_sp";
            case 9004:
                return "s_bqc_sp";
            case 9005:
                return "s_cbf_sp";
            case 9101:
                return "s_rfsf_sp";
            case 9102:
                return "s_sf_sp";
            case 9103:
                return "s_sfc_sp";
            case 9104:
                return "s_dxf_sp";
            case 4501:
                return "s_spf_sp";
            case 4502:
                return "s_jqs_sp";
            case 4503:
                return "s_sxp_sp";
            case 4504:
                return "s_cbf_sp";
            case 4505:
                return "s_bqc_sp";
            case 1001:
                return "s_euro_odds";
            case 1002:
                return "s_euro_odds";
            case 1003:
                return "s_euro_odds";
            case 1004:
                return "s_euro_odds";

        }
        return "";
    }

    /**
     * 根据玩法获取数据库相应字段名
     * @param playId
     * @return
     */
    public static int getSpLength(int playId){
        switch (playId){
            case 9001:
                return 3;
            case 9002:
                return 3;
            case 9003:
                return 8;
            case 9004:
                return 9;
            case 9005:
                return 31;
            case 9101:
                return 3;
            case 9102:
                return 2;
            case 9103:
                return 12;
            case 9104:
                return 3;
            case 4501:
                return 3;
            case 4502:
                return 8;
            case 4503:
                return 4;
            case 4504:
                return 25;
            case 4505:
                return 9;
        }
        return 0;
    }

    public static String getJczqSpByPlayId(BasedataMatchJczqENT match, int playId){
        switch (playId){
            case 9001:
                return match.getSpfSp();
            case 9002:
                return match.getRqspfSp();
            case 9003:
                return match.getJqsSp();
            case 9004:
                return match.getBqcSp();
            case 9005:
                return match.getCbfSp();
        }
        return "";
    }

    public static String getJclqSpByPlayId(BasedataMatchJclqENT match, int playId){
        switch (playId){

            case 9101:
                return match.getRfsfSp();
            case 9102:
                return match.getSfSp();
            case 9103:
                return match.getSfcSp();
            case 9104:
                return match.getDxfSp();
        }
        return "";
    }

    public static String getBjdcSpByPlayId(BasedataMatchBjdcENT match, int playId){
        switch (playId){
            case 4501:
                return match.getSpfSp();
            case 4502:
                return match.getJqsSp();
            case 4503:
                return match.getSxpSp();
            case 4504:
                return match.getCbfSp();
            case 4505:
                return match.getBqcSp();
        }
        return "";
    }


    public static String getActualIssueName(int lotteryId,String issueFromDataTeam){
        switch (lotteryId){
            case 90:
            case 91:
                return DateUtil.format(DateUtil.format(issueFromDataTeam,"yyyy-MM-dd"),"yyyyMMdd");
            case 45:
                return "1".concat(issueFromDataTeam);
        }
        return "";
    }
}
