package com.tty.task.utils;

import com.jdd.fm.core.utils.DateUtil;
import com.tty.common.utils.DateUtils;

import java.util.Date;


/**
 * @Author: dukeqiang
 * @Date: 2020/10/16 15:04
 */
public class AnalysisUtils {
    /**
     * 解析 match no
     * @param machNum
     * @return
     */
    public static String analysisJCGWMatchNo(String machNum) {
        String number = "";
        if (machNum.contains("周一")) {
            String num = machNum.replace("周一","");
            number = num;
        } else if (machNum.contains("周二")) {
            String num = machNum.replace("周二","");
            number = num;
        } else if (machNum.contains("周三")) {
            String num = machNum.replace("周三","");
            number = num;
        } else if (machNum.contains("周四")) {
            String num = machNum.replace("周四","");
            number = num;
        } else if (machNum.contains("周五")) {
            String num = machNum.replace("周五","");
            number = num;
        } else if (machNum.contains("周六")) {
            String num = machNum.replace("周六","");
            number = num;
        } else if (machNum.contains("周日")) {
            String num = machNum.replace("周日","");
            number = num;
        }
        return number;
    }

    /**
     * 解析比赛周(1-7)
     * @param machNum
     * @return
     */
    public static Integer analysisJCGWWeekDay(String machNum){
        Integer weekDay = 0;
        if (machNum.contains("周一")) {
            String num = machNum.replace("周一","");
            weekDay = 1;
        } else if (machNum.contains("周二")) {
            String num = machNum.replace("周二","");
            weekDay = 2;
        } else if (machNum.contains("周三")) {
            String num = machNum.replace("周三","");
            weekDay = 3;
        } else if (machNum.contains("周四")) {
            String num = machNum.replace("周四","");
            weekDay = 4;
        } else if (machNum.contains("周五")) {
            String num = machNum.replace("周五","");
            weekDay = 5;
        } else if (machNum.contains("周六")) {
            String num = machNum.replace("周六","");
            weekDay = 6;
        } else if (machNum.contains("周日")) {
            String num = machNum.replace("周日","");
            weekDay = 7;
        }
        return weekDay;
    }

    /**
     * 解析比赛周名称（周一-周日）
     * @param machNum
     * @return
     */
    public static String analysisJCGWWeekName(String machNum){
        String weekName = "";
        if (machNum.contains("周一")) {
            String num = machNum.replace("周一","");
            weekName = "周一";
        } else if (machNum.contains("周二")) {
            String num = machNum.replace("周二","");
            weekName = "周二";
        } else if (machNum.contains("周三")) {
            String num = machNum.replace("周三","");
            weekName = "周三";
        } else if (machNum.contains("周四")) {
            String num = machNum.replace("周四","");
            weekName = "周四";
        } else if (machNum.contains("周五")) {
            String num = machNum.replace("周五","");
            weekName = "周五";
        } else if (machNum.contains("周六")) {
            String num = machNum.replace("周六","");
            weekName = "周六";
        } else if (machNum.contains("周日")) {
            String num = machNum.replace("周日","");
            weekName = "周日";
        }
        return weekName;
    }

    /**
     * 解析比赛开赛时间
     * @param date
     * @param time
     * @return
     */
    public static Date analysisJCGWMatchStartTime(String date, String time) {
        String startTime = date + " " + time;
        Date start = DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss");
        return start;
    }

    public static void main(String[] args) {
        System.out.println(analysisJCGWMatchStartTime("2020-10-16","12:00:00"));
    }
}
