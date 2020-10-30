package com.tty.data.common.util;

import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.PropertiesUtil;
import com.jdddata.bdpush.bo.bdsp.BdMatchFinalSpBo;
import com.jdddata.bdpush.bo.common.CommonSpBo;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @author yinyansheng
 * @date 2/13/2017
 * @Description
 */
public class IssueMatchUtil {

    /**
     * 计算竞彩足球购买截止时间
     *
     * @param endBuyBeforeSecond
     * @param matchStartTime
     * matchStartTime=周日/周一,且01:00<=matchStartTime<=week7And1EndTime(09:30)时 matchEndTime=01:00-endBuyBeforeSecond;其他时间段matchEndTime=matchStartTime-endBuyBeforeSecond
     * matchStartTime<>周日/周一/,且00:00<=matchStartTime<=weekOtherEndTime(09:30)时 matchEndTime=00:00-endBuyBeforeSecond;其他时间段matchEndTime=matchStartTime-endBuyBeforeSecond
     * @return
     */
    public static Date getJczqMatchEndTime(int endBuyBeforeSecond, Date matchStartTime) {
        String week7And1EndTime = PropertiesUtil.get("week7And1.endTime");
        String weekOtherEndTime = PropertiesUtil.get("weekOther.endTime");



        //提前截期分钟
        int endBuyBeforeMinute = endBuyBeforeSecond / 60;

        //比赛开始时间日历
        Calendar matchStartTimeCalendar = Calendar.getInstance();
        matchStartTimeCalendar.setTime(matchStartTime);
        int week = matchStartTimeCalendar.get(Calendar.DAY_OF_WEEK);

        //出去时分秒的比赛开始时间日历
        Date tempDate = DateUtil.format(DateUtil.format(matchStartTime, "yyyy-MM-dd"), "yyyy-MM-dd");
        Calendar tempDateCalendar = Calendar.getInstance();
        tempDateCalendar.setTime(tempDate);

        Calendar endTime00 = Calendar.getInstance();
        endTime00.setTime(DateUtil.format(DateUtil.format(tempDate, "yyyy-MM-dd")+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));

        Calendar endTime01 = Calendar.getInstance();
        endTime01.setTime(DateUtil.format(DateUtil.format(tempDate, "yyyy-MM-dd")+" 01:00:00", "yyyy-MM-dd HH:mm:ss"));


        Calendar endTimeWeek7And1 = Calendar.getInstance();
        endTimeWeek7And1.setTime(DateUtil.format(DateUtil.format(tempDate, "yyyy-MM-dd")+" "+week7And1EndTime, "yyyy-MM-dd HH:mm:ss"));

        Calendar endTimeOther = Calendar.getInstance();
        endTimeOther.setTime(DateUtil.format(DateUtil.format(tempDate, "yyyy-MM-dd")+" "+weekOtherEndTime, "yyyy-MM-dd HH:mm:ss"));


        //周日 & 周一
        if (1 == week || 2 == week) {
            //[1点-week7And1EndTime]
            if (endTime01.getTime().getTime()<=matchStartTime.getTime()&&
                    matchStartTime.getTime()<=endTimeWeek7And1.getTime().getTime()) {
                return DateUtils.addMinutes(endTime01.getTime(),-1*endBuyBeforeMinute);
            }
        } else {
            //[0点-9点30]
            if (endTime00.getTime().getTime()<=matchStartTime.getTime()&&
                    matchStartTime.getTime()<=endTimeOther.getTime().getTime()) {
                return DateUtils.addMinutes(endTime00.getTime(),-1*endBuyBeforeMinute);
            }
        }
        return DateUtils.addMinutes(matchStartTime,-1*endBuyBeforeMinute);
    }


    /**
     * 竞彩篮球截止时间
     *
     * @param endBuyBeforeSecond
     * @param matchStartTime
     * matchStartTime=周日/周一,且01:00<=matchStartTime<=week7And1EndTime(09:30)时 matchEndTime=01:00-endBuyBeforeSecond;其他时间段matchEndTime=matchStartTime-endBuyBeforeSecond
     * matchStartTime=周三/周四,且00:00<=matchStartTime<=week3And4EndTime(08:00)时 matchEndTime=00:00-endBuyBeforeSecond;其他时间段matchEndTime=matchStartTime-endBuyBeforeSecond
     * matchStartTime<>周日/周一/周三/周四,且00:00<=matchStartTime<=weekOtherEndTime(09:30)时 matchEndTime=00:00-endBuyBeforeSecond;其他时间段matchEndTime=matchStartTime-endBuyBeforeSecond
     * @return
     */
    public static Date getJclqMatchEndTime(Integer endBuyBeforeSecond, Date matchStartTime) {
        String week7And1EndTime = PropertiesUtil.get("week7And1.endTime");
        String week3And4EndTime = PropertiesUtil.get("week3And4.endTime");
        String weekOtherEndTime = PropertiesUtil.get("weekOther.endTime");


        //提前截期分钟
        int endBuyBeforeMinute = endBuyBeforeSecond / 60;

        //比赛开始时间日历
        Calendar matchStartTimeCalendar = Calendar.getInstance();
        matchStartTimeCalendar.setTime(matchStartTime);
        int week = matchStartTimeCalendar.get(Calendar.DAY_OF_WEEK);

        //去掉时分秒的比赛开始时间日历
        Date tempDate = DateUtil.format(DateUtil.format(matchStartTime, "yyyy-MM-dd"), "yyyy-MM-dd");
        Calendar tempDateCalendar = Calendar.getInstance();
        tempDateCalendar.setTime(tempDate);

        Calendar endTime00 = Calendar.getInstance();
        endTime00.setTime(DateUtil.format(DateUtil.format(tempDate, "yyyy-MM-dd")+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));

        Calendar endTime01 = Calendar.getInstance();
        endTime01.setTime(DateUtil.format(DateUtil.format(tempDate, "yyyy-MM-dd")+" 01:00:00", "yyyy-MM-dd HH:mm:ss"));


        Calendar endTimeWeek7And1 = Calendar.getInstance();
        endTimeWeek7And1.setTime(DateUtil.format(DateUtil.format(tempDate, "yyyy-MM-dd")+" "+week7And1EndTime, "yyyy-MM-dd HH:mm:ss"));

        Calendar endTimeWeek3And4 = Calendar.getInstance();
        endTimeWeek3And4.setTime(DateUtil.format(DateUtil.format(tempDate, "yyyy-MM-dd")+" "+week3And4EndTime, "yyyy-MM-dd HH:mm:ss"));

        Calendar endTimeOther = Calendar.getInstance();
        endTimeOther.setTime(DateUtil.format(DateUtil.format(tempDate, "yyyy-MM-dd")+" "+weekOtherEndTime, "yyyy-MM-dd HH:mm:ss"));


        //周日 & 周一
        if (1 == week || 2 == week) {
            //[1点-week7And1EndTime]
            if (endTime01.getTime().getTime()<=matchStartTime.getTime()&&
                    matchStartTime.getTime()<=endTimeWeek7And1.getTime().getTime()) {
                return DateUtils.addMinutes(endTime01.getTime(),-1*endBuyBeforeMinute);
            }
        } else if (4 == week || 5 == week) {//周三&周四
            //[0点-week3And4EndTime]
            if (endTime00.getTime().getTime()<=matchStartTime.getTime()&&
                    matchStartTime.getTime()<=endTimeWeek3And4.getTime().getTime()) {
                return DateUtils.addMinutes(endTime00.getTime(),-1*endBuyBeforeMinute);
            }
        } else {
            //[0点-endTimeOther]
            if (endTime00.getTime().getTime()<=matchStartTime.getTime()&&
                    matchStartTime.getTime()<=endTimeOther.getTime().getTime()) {
                return DateUtils.addMinutes(endTime00.getTime(),-1*endBuyBeforeMinute);
            }
        }
        return DateUtils.addMinutes(matchStartTime,-1*endBuyBeforeMinute);

    }

    /**
     * 计算北京单场购买截止时间
     *
     * @param endBuyBeforeSecond
     * @param matchStartTime
     * @return
     */
    public static Date getBjdcMatchEndTime(int endBuyBeforeSecond, Date matchStartTime) {
        Calendar matchEndTimeCalendar = Calendar.getInstance();
        matchEndTimeCalendar.setTime(matchStartTime);
        matchEndTimeCalendar.add(Calendar.SECOND, -1 * endBuyBeforeSecond);//减去提前时间

        return matchEndTimeCalendar.getTime();
    }

    public static String getActualIssueName(int lotteryId, String issueFromDataTeam) {
        switch (lotteryId) {
            case 90:
            case 91:
                return DateUtil.format(DateUtil.format(issueFromDataTeam, "yyyy-MM-dd"), "yyyyMMdd");
            case 45:
                return "1".concat(issueFromDataTeam);
            case 1://胜负彩
            case 2://四场进球彩
            case 15://六场半全场
            case 19://任选九
                return "20".concat(issueFromDataTeam);
        }
        return "";
    }

    public static int getWeekDay(int lotteryId, String issueFromDataTeam) {
        switch (lotteryId) {
            case 90:
            case 91:
                return getWeekDay(DateUtil.format(issueFromDataTeam, "yyyy-MM-dd"));
            case 45:
                return getWeekDay(DateUtil.format("201".concat(issueFromDataTeam), "yyyyMMdd"));
        }
        return -1;
    }

    public static String getWeekDayName(int weekDay) {
        if (-1 == weekDay) {
            return "";
        }
        return getWeekInfos().get(weekDay);
    }

    public static String getActualIssueMatchName(int lotteryId, String issueName, String matchNo) {
        switch (lotteryId) {
            case 90:
            case 91:
                return issueName.concat(matchNo).substring(2);
            case 45://北单
            case 1://胜负彩
            case 2://四场进球彩
            case 15://六场半全场
            case 19://任选九
                return issueName.concat(matchNo);
        }
        return "";
    }

    private static Map<Integer, String> weekMaps = new HashMap();

    static {
        weekMaps.put(1, "周一");
        weekMaps.put(2, "周二");
        weekMaps.put(3, "周三");
        weekMaps.put(4, "周四");
        weekMaps.put(5, "周五");
        weekMaps.put(6, "周六");
        weekMaps.put(7, "周日");
    }

    private static Map<Integer, String> getWeekInfos() {
        return weekMaps;
    }

    private static int getWeekDay(Date issueDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        int tmp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return tmp == 0 ? 7 : tmp;
    }

    public static String getJczqActualSP(int playId, String sp) {
        if (StringUtils.isBlank(sp)) {
            return "";
        }
        switch (playId) {
            case 9001:
            case 9002:
            case 9003:
            case 9004:
                return sp.concat(",1");
            case 9005:
                return sp.concat(",1,1");
            default:
                return "";
        }
    }

    public static String getJclqActualSP(int playId, String sp,String score) {
        if (StringUtils.isBlank(sp)) {
            return "";
        }

        switch (playId) {
            case 9101:
            case 9104:
                return StringUtils.isBlank(score)?sp:score.concat(",").concat(sp);
            case 9103:
                return sp.concat(",1");
            default:
                return sp;
        }
    }

    public static String getBjdcActualSP(String sp) {
        if (null == sp) {
            return "";
        }
        return sp;
    }

    public static String getActualSP(List<CommonSpBo> commonSpBoList) {
        if (CollectionUtils.isEmpty(commonSpBoList)) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (CommonSpBo commonSpBo : commonSpBoList) {
            stringBuffer.append(commonSpBo.getOdds()).append(",");
        }
        String s = stringBuffer.toString();
        if (StringUtils.isBlank(s)) {
            return "";
        }
        return s.substring(0, s.length() - 1);
    }

    public static String getActualJclqSP(List<CommonSpBo> sp) {
        if (CollectionUtils.isEmpty(sp)) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (CommonSpBo jlSpBo : sp) {
            stringBuffer.append(jlSpBo.getOdds()).append(",");
        }
        String s = stringBuffer.toString();
        if (StringUtils.isBlank(s)) {
            return "";
        }
        return s.substring(0, s.length() - 1);
    }

    public static String getActualFinalSP(List<BdMatchFinalSpBo> lastSp) {
        if(CollectionUtils.isEmpty(lastSp)){
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (BdMatchFinalSpBo bdMatchFinalSpBo : lastSp) {
            stringBuffer.append(bdMatchFinalSpBo.getLastSp()).append(",");
        }
        String s = stringBuffer.toString();
        if (StringUtils.isBlank(s)) {
            return "";
        }
        return s.substring(0, s.length() - 1);
    }
}
