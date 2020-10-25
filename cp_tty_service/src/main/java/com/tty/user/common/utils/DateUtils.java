package com.tty.user.common.utils;/**
 * Created by shenwei on 2016/12/21.
 */

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author shenwei
 * @create 2016-12-21
 */
public class DateUtils {

    //获取当前时间到当天23:59:59，剩余的秒数
    public static Long getSecondsToTomorrow() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        Long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
        if (seconds != null) {
            return seconds.longValue();
        }
        else {
            return new Long(0);
        }
    }

    //获取当前时间到本周日(23:59:59)，剩余的秒数
    public static Integer getSecondsByLastDayWeek(Date lastDay) {
        Date time1 = new Date();
        Date time2 = lastDay;
        Integer s = null;
        try {
            Long sec = (time2.getTime() - time1.getTime()) / (1000);
            s = sec.intValue();
        } catch (Exception e) {

        }
        return s;
    }

}
