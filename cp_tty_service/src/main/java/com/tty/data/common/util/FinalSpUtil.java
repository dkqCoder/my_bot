package com.tty.data.common.util;

/**
 * @author yinyansheng
 * @date 2/22/2017
 * @Description
 */
public class FinalSpUtil {

    public static int getPlayId(String bodyVOPlay) {
        if (null == bodyVOPlay)
            return 0;
        switch (bodyVOPlay.toUpperCase()) {
            case "DCWDLOFFER": //胜平负
                return 4501;
            case "DCGOALSOFFER": //总进球
                return 4502;
            case "DCOEOFFER": //上下盘
                return 4503;
            case "DCSCOREFTOFFER": //猜比分
                return 4504;
            case "DCHTFTOFFER": //半全场
                return 4505;
        }
        return 0;
    }

    public static boolean judgeFinalSp(String finalSp) {
        try {
            double sp = Double.valueOf(finalSp);
            if(sp<1){
                return  false;
            }
            return true;

        }catch (Exception e){
            return false;
        }
    }

    public static String getActualIssueName(int lotteryId,String issueFromDataTeam){
        switch (lotteryId){
            case 45://北京单场
                return "1".concat(issueFromDataTeam);
        }
        return "";
    }

}
