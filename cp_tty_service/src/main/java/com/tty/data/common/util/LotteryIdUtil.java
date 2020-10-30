package com.tty.data.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yinyansheng
 * @date 2/20/2017
 * @Description
 */
public class LotteryIdUtil {

    /**
     * 根据数据组提供的彩种和玩法 获取基础数据中的彩种
     *
     * @param dataTeamLotteryId
     * @param dataTeamPlayId
     * @return lotteryId
     */
    public static Set<Integer> getLotteryIdFromDataTeam(int dataTeamLotteryId, int dataTeamPlayId) {
        Set<Integer> tempResult = new HashSet<>();
        //胜负彩 & 任选九
        if (10 == dataTeamLotteryId && 1001 == dataTeamPlayId) {
            tempResult.add(1);//胜负彩
            tempResult.add(19);//任选九
            return tempResult;
        }

        if (10 == dataTeamLotteryId && 1003 == dataTeamPlayId) {
            tempResult.add(15);//六场半全场
            return tempResult;
        }

        if (10 == dataTeamLotteryId && 1004 == dataTeamPlayId) {
            tempResult.add(2);//四场进球彩
            return tempResult;
        }

        if (90 == dataTeamLotteryId) {
            tempResult.add(90);//竞彩足球
            return tempResult;
        }

        if (91 == dataTeamLotteryId) {
            tempResult.add(91);//竞彩篮球
            return tempResult;
        }

        if (45 == dataTeamLotteryId) {
            tempResult.add(45);//北京单场
            return tempResult;
        }

        return tempResult;
    }

    public static Boolean isCtzc(Integer lotteryId) {
        List<Integer> ctzcLotteryIds = new ArrayList<>();
        ctzcLotteryIds.add(1);
        ctzcLotteryIds.add(2);
        ctzcLotteryIds.add(15);
        ctzcLotteryIds.add(19);
        return ctzcLotteryIds.contains(lotteryId);
    }

}
