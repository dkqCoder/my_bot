package com.tty.data.common.util;

import com.jdd.fm.core.context.FormatConfig;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by rxu on 2017/11/23.
 * extension of {@link com.jdd.fm.core.utils.DateUtil}
 */
public class DateUtil {


    public static ZoneId zone = ZoneId.systemDefault();
    /**
     * 根据当前时间获取一周时间，往前推一天，往后推五天
     * @return
     */
    public static List<String>  getTimeInterval() {
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = com.jdd.fm.core.utils.DateUtil.getDateFormat();
        for(int i= -5;i<=1;i++){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            String date = sdf.format(calendar.getTime());
            dateList.add(date);
        }
        return dateList;
    }

    public static String getCurrentDateByFormat(SimpleDateFormat format){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        return format.format(calendar.getTime());
    }

//    public static void main(String[] args) {
//        System.out.println(getTimeInterval());
//    }

    public static boolean compareEqualDate(Date d1, Date d2){
        return LocalDateTime.ofInstant(d1.toInstant(), zone).format(DateTimeFormatter.ofPattern(FormatConfig.DATE))
                .equals(LocalDateTime.ofInstant(d2.toInstant(), zone).format(DateTimeFormatter.ofPattern(FormatConfig.DATE)));
    }

    public static Date getDateByStringPattern(String str,String pattern){

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return formatter.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("Exception throwing during parse date.");
        }

       /* DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
        return Date.from(LocalDateTime.parse(str,f).atZone(zone).toInstant());*/
    }

    public static String getWeekOfDate(Date dt,boolean isCHN) {
        String[] weekDays_chn = {"周日", "周一","周二", "周三", "周四", "周五", "周六"};
//        String[] weekDays_eng = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays_chn[w];
    }
}
