package com.tty.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by yys on 2017/7/6.
 */
public class LotteryUtil {
    public static Integer getUnitMoney(Integer playTypeId) {
        if (playTypeId == 3903 || playTypeId == 3904 || playTypeId == 3908) {
            return 3;
        }
        return 2;
    }

    public static Integer getUnitCount(Integer playTypeId, BigDecimal money, Integer multiple) {
        return money.divide(new BigDecimal(getUnitMoney(playTypeId) * multiple), RoundingMode.HALF_UP).intValue();
    }
}
