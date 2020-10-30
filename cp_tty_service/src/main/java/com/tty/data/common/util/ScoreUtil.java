package com.tty.data.common.util;

import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.StringUtils;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yinyansheng
 * @date 2/20/2017
 * @Description
 */
public class ScoreUtil {

    public static String getCtzcMatchResult(int lotteryId, String matchNo, String halfScore, String fullScore) {

        if (!StringUtils.isNotBlank(halfScore) || !StringUtils.isNotBlank(fullScore) || halfScore.contains("*") || fullScore.contains("*")) {
            return "";
        }

        if (halfScore.contains("-1") || fullScore.contains("-1")) {
            return "";
        }

        String[] half = halfScore.split(":");
        String[] full = fullScore.split(":");

        if (half.length != 2 || full.length != 2) {
            return "";
        }

        int h_half_score = Integer.valueOf(half[0]);//主队半场比分
        int v_half_score = Integer.valueOf(half[1]);//客队半场比分
        int h_full_score = Integer.valueOf(full[0]);//主队全场比分
        int v_full_score = Integer.valueOf(full[1]);//客队全场比分
        int total_score = h_full_score + v_full_score;//进球总数

        if (h_half_score < 0 || v_half_score < 0 || h_full_score < 0 || v_full_score < 0) {
            return "";
        }

        switch (lotteryId) {
            case 1://胜负彩
            case 19://任选九
                return getMatchResult(h_full_score, v_full_score);
            case 2://四场进球彩
                return String.format("%s%s", h_full_score > 3 ? 3 : h_full_score, v_full_score > 3 ? 3 : v_full_score);
            case 15://六场半全场
                //偶数为全场，奇数为半场
                if (Integer.valueOf(matchNo) % 2 == 0) {
                    return getMatchResult(h_full_score, v_full_score);//全场
                } else {
                    return getMatchResult(h_half_score, v_half_score);//半场
                }
        }
        return "";
    }

    /**
     * 获取竞彩足球比赛结果
     *
     * @param rq        让球数
     * @param halfScore 半场比分
     * @param fullScore 全场比分
     * @return
     */
    public static String getJczqMatchResult(int rq, String halfScore, String fullScore) {

        if (!StringUtils.isNotBlank(halfScore) || !StringUtils.isNotBlank(fullScore) || halfScore.contains("*") || fullScore.contains("*")) {
            return "";
        }

        if (halfScore.contains("-1") || fullScore.contains("-1")) {
            return "-1,-1,-1,-1,-1";
        }

        String[] half = halfScore.split(":");
        String[] full = fullScore.split(":");

        if (half.length != 2 || full.length != 2) {
            return "";
        }

        int h_half_score = Integer.valueOf(half[0]);//主队半场比分
        int v_half_score = Integer.valueOf(half[1]);//客队半场比分
        int h_full_score = Integer.valueOf(full[0]);//主队全场比分
        int v_full_score = Integer.valueOf(full[1]);//客队全场比分
        int h_full_score_rq = h_full_score + rq;//加让球后主队比分
        int total_score = h_full_score + v_full_score;//进球总数

        if (h_half_score < 0 || v_half_score < 0 || h_full_score < 0 || v_full_score < 0) {
            return "";
        }

        String rqspfResult = getMatchResult(h_full_score_rq, v_full_score);//让球胜平负
        String jqsResult = String.valueOf(total_score > 7 ? 7 : total_score);//总进球
        String cbfResult = getJczqCbfResult(h_full_score, v_full_score);//猜比分
        String bqcResult = String.format("%s%s", getMatchResult(h_half_score, v_half_score), getMatchResult(h_full_score, v_full_score));//半全场
        String spfResult = getMatchResult(h_full_score, v_full_score); //胜平负

        return String.format("%s,%s,%s,%s,%s", rqspfResult, jqsResult, cbfResult, bqcResult, spfResult);
    }

    /**
     * 玩法sp值 9001 让球胜平负，9002 总进球，9003 猜比分，9004 半全场，9006 胜平负
     *
     * @param sp
     * @param playId
     * @return
     */
    public static  String getJczqSp(String sp, int playId, String separator) {
        if (sp == null) {
            sp = "";
        }
        StringBuffer sps = new StringBuffer();
        String[] spl = sp.split(",");
        switch (playId) {
            case 9001:
            case 9006:
                if (spl.length < 3) {
                    sps.append(String.format("0.00%s0.00%s0.00", separator, separator));
                } else {
                    sps.append(spl[2]);
                    sps.append(separator);
                    sps.append(spl[1]);
                    sps.append(separator);
                    sps.append(spl[0]);
                }
                break;
            case 9002:
                if (spl.length < 8) {
                    sps.append(String.format("0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00",
                            separator, separator, separator, separator, separator, separator, separator));
                } else {
                    sps.append(spl[0]);
                    sps.append(separator);
                    sps.append(spl[1]);
                    sps.append(separator);
                    sps.append(spl[2]);
                    sps.append(separator);
                    sps.append(spl[3]);
                    sps.append(separator);
                    sps.append(spl[4]);
                    sps.append(separator);
                    sps.append(spl[5]);
                    sps.append(separator);
                    sps.append(spl[6]);
                    sps.append(separator);
                    sps.append(spl[7]);
                }
                break;
            case 9003:
                if (spl.length < 31) {
                    sps.append(String.format("0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s",
                            separator, separator, separator, separator, separator, separator, separator, separator, separator, separator));
                    sps.append(String.format("0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s",
                            separator, separator, separator, separator, separator, separator, separator, separator, separator, separator));
                    sps.append(String.format("0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00",
                            separator, separator, separator, separator, separator, separator, separator, separator, separator, separator));
                } else {

                    sps.append(spl[9]);
                    sps.append(separator);
                    sps.append(spl[15]);
                    sps.append(separator);
                    sps.append(spl[16]);
                    sps.append(separator);
                    sps.append(spl[21]);
                    sps.append(separator);
                    sps.append(spl[22]);
                    sps.append(separator);
                    sps.append(spl[23]);
                    sps.append(separator);
                    sps.append(spl[25]);
                    sps.append(separator);
                    sps.append(spl[26]);
                    sps.append(separator);
                    sps.append(spl[27]);
                    sps.append(separator);


                    sps.append(spl[28]);
                    sps.append(separator);
                    sps.append(spl[29]);
                    sps.append(separator);
                    sps.append(spl[30]);
                    sps.append(separator);
                    sps.append(spl[2]);
                    sps.append(separator);


                    sps.append(spl[3]);
                    sps.append(separator);
                    sps.append(spl[10]);
                    sps.append(separator);
                    sps.append(spl[17]);
                    sps.append(separator);
                    sps.append(spl[24]);
                    sps.append(separator);
                    sps.append(spl[1]);
                    sps.append(separator);


                    sps.append(spl[4]);
                    sps.append(separator);
                    sps.append(spl[5]);
                    sps.append(separator);
                    sps.append(spl[11]);
                    sps.append(separator);
                    sps.append(spl[6]);
                    sps.append(separator);
                    sps.append(spl[12]);
                    sps.append(separator);
                    sps.append(spl[18]);
                    sps.append(separator);
                    sps.append(spl[7]);
                    sps.append(separator);
                    sps.append(spl[13]);
                    sps.append(separator);
                    sps.append(spl[19]);
                    sps.append(separator);

                    sps.append(spl[8]);
                    sps.append(separator);
                    sps.append(spl[14]);
                    sps.append(separator);
                    sps.append(spl[20]);
                    sps.append(separator);
                    sps.append(spl[0]);
                }
                break;
            case 9004:
                if (spl.length < 9) {
                    sps.append(String.format("0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00%s0.00",
                            separator, separator, separator, separator, separator, separator, separator, separator));
                } else {
                    sps.append(spl[8]);
                    sps.append(separator);
                    sps.append(spl[7]);
                    sps.append(separator);
                    sps.append(spl[6]);
                    sps.append(separator);
                    sps.append(spl[5]);
                    sps.append(separator);
                    sps.append(spl[4]);
                    sps.append(separator);
                    sps.append(spl[3]);
                    sps.append(separator);
                    sps.append(spl[2]);
                    sps.append(separator);
                    sps.append(spl[1]);
                    sps.append(separator);
                    sps.append(spl[0]);
                }
                break;
            default:
                sps.append("");
        }
        return sps.toString();
    }

    public static String getJclqMatchResult(String fullScore, String rfsfSp, String dxfSp) {
        if (!StringUtils.isNotBlank(fullScore) || !StringUtils.isNotBlank(rfsfSp) || !StringUtils.isNotBlank(dxfSp)) {
            return "";
        }

        if (fullScore.contains("-1")) {
            return "-1,-1,-1,-1";
        }

        String[] full = fullScore.split(":");
        String[] rfsf = rfsfSp.split(",");
        String[] dxf = dxfSp.split(",");

        if (full.length != 2 || rfsf.length != 3 || dxf.length != 3) {
            return "";
        }

        int v_score = Integer.valueOf(full[0]);//客队全场比分
        int h_score = Integer.valueOf(full[1]);//主队全场比分

        if (h_score < 0 || v_score < 0) {
            return "";
        }

        float rfsfScore = Float.valueOf(rfsf[0]);
        float dxfScore = Float.valueOf(dxf[0]);

        if (dxfScore < 0F) {
            return "";
        }

        DecimalFormat decimalFormat = new DecimalFormat(".00");

        //RFSF
        String rfsfResult;
        if (0F == rfsfScore) {
            rfsfResult = "-1";
        } else {
            rfsfResult = String.format("%s|%s", h_score + rfsfScore > v_score ? 1 : 2, decimalFormat.format(rfsfScore));
        }

        //SF
        String sfResult = h_score > v_score ? "1" : "2";

        //SFC
        String sfcResult = getJclqCbfResult(v_score, h_score);

        //DXF
        String dxfResult;
        if (0F == dxfScore) {
            dxfResult = "-1";
        } else {
            dxfResult = String.format("%s|%s", h_score + v_score > dxfScore ? 1 : 2, decimalFormat.format(dxfScore));
        }

        return String.format("%s,%s,%s,%s", rfsfResult, sfResult, sfcResult, dxfResult);
    }

    private static String getJclqCbfResult(int v_score, int h_score) {
        int v_win_score = v_score - h_score;//客队胜分值
        int h_win_score = h_score - v_score;//主队胜分值

        if (h_score > v_score) {
            if (h_win_score >= 1 && h_win_score <= 5) {
                return "01";
            } else if (h_win_score >= 6 && h_win_score <= 10) {
                return "02";
            } else if (h_win_score >= 11 && h_win_score <= 15) {
                return "03";
            } else if (h_win_score >= 15 && h_win_score <= 20) {
                return "04";
            } else if (h_win_score >= 21 && h_win_score <= 25) {
                return "05";
            } else if (h_win_score >= 26) {
                return "06";
            }
        } else {
            if (v_win_score >= 1 && v_win_score <= 5) {
                return "11";
            } else if (v_win_score >= 6 && v_win_score <= 10) {
                return "12";
            } else if (v_win_score >= 11 && v_win_score <= 15) {
                return "13";
            } else if (v_win_score >= 15 && v_win_score <= 20) {
                return "14";
            } else if (v_win_score >= 21 && v_win_score <= 25) {
                return "15";
            } else if (v_win_score >= 26) {
                return "16";
            }
        }
        return "";
    }

    public static String getBjdcMatchResult(int rq, String halfScore, String fullScore) {

        if (!StringUtils.isNotBlank(halfScore) || !StringUtils.isNotBlank(fullScore) || halfScore.contains("*") || fullScore.contains("*")) {
            return "";
        }

        if (halfScore.contains("-1") || fullScore.contains("-1")) {
            return "-1,-1,-1,-1,-1";
        }

        String[] half = halfScore.split(":");
        String[] full = fullScore.split(":");

        if (half.length != 2 || full.length != 2) {
            return "";
        }

        int h_half_score = Integer.valueOf(half[0]);//主队半场比分
        int v_half_score = Integer.valueOf(half[1]);//客队半场比分
        int h_full_score = Integer.valueOf(full[0]);//主队全场比分
        int v_full_score = Integer.valueOf(full[1]);//客队全场比分
        int h_full_score_rq = h_full_score + rq;//加让球后主队比分
        int total_score = h_full_score + v_full_score;//进球总数

        if (h_half_score < 0 || v_half_score < 0 || h_full_score < 0 || v_full_score < 0) {
            return "";
        }

        String rqspfResult;//让球胜平负
        String jqsResult;//总进球
        String sxpResult;//上下盘
        String cbfResult;//猜比分
        String bqcResult;//半全场

        rqspfResult = getMatchResult(h_full_score_rq, v_full_score);
        jqsResult = String.valueOf(total_score > 7 ? 7 : total_score);

        if (total_score >= 3) {
            sxpResult = total_score % 2 == 0 ? "2" : "3";
        } else {
            sxpResult = total_score % 2 == 0 ? "0" : "1";
        }

        cbfResult = getBjdcCbfResult(h_full_score, v_full_score);
        bqcResult = String.format("%s%s", getMatchResult(h_half_score, v_half_score), getMatchResult(h_full_score, v_full_score));

        return String.format("%s,%s,%s,%s,%s", rqspfResult, jqsResult, sxpResult, cbfResult, bqcResult);
    }

    private static String getJczqCbfResult(int hostScore, int visitScore) {

        Set<String> winSet = new HashSet<>();
        winSet.add("10");
        winSet.add("20");
        winSet.add("21");
        winSet.add("30");
        winSet.add("31");
        winSet.add("32");
        winSet.add("40");
        winSet.add("41");
        winSet.add("42");
        winSet.add("50");
        winSet.add("51");
        winSet.add("52");

        Set<String> failSet = new HashSet<>();
        failSet.add("01");
        failSet.add("02");
        failSet.add("12");
        failSet.add("03");
        failSet.add("13");
        failSet.add("23");
        failSet.add("04");
        failSet.add("14");
        failSet.add("24");
        failSet.add("05");
        failSet.add("15");
        failSet.add("25");

        Set<String> tieSet = new HashSet<>();
        tieSet.add("00");
        tieSet.add("11");
        tieSet.add("22");
        tieSet.add("33");

        String strScore = String.format("%d%d", hostScore, visitScore);

        if (hostScore > visitScore) {
            if (winSet.contains(strScore)) {
                return strScore;
            }
            return "90";
        }

        if (hostScore == visitScore) {
            if (tieSet.contains(strScore)) {
                return strScore;
            }
            return "99";
        }

        if (hostScore < visitScore) {
            if (failSet.contains(strScore)) {
                return strScore;
            }
            return "09";
        }

        return "";
    }

    private static String getBjdcCbfResult(int hostScore, int visitScore) {

        Set<String> winSet = new HashSet<>();
        winSet.add("10");
        winSet.add("20");
        winSet.add("21");
        winSet.add("30");
        winSet.add("31");
        winSet.add("32");
        winSet.add("40");
        winSet.add("41");
        winSet.add("42");

        Set<String> failSet = new HashSet<>();
        failSet.add("01");
        failSet.add("02");
        failSet.add("12");
        failSet.add("03");
        failSet.add("13");
        failSet.add("23");
        failSet.add("04");
        failSet.add("14");
        failSet.add("24");

        Set<String> tieSet = new HashSet<>();
        tieSet.add("00");
        tieSet.add("11");
        tieSet.add("22");
        tieSet.add("33");

        String strScore = String.format("%d%d", hostScore, visitScore);

        if (hostScore > visitScore) {
            if (winSet.contains(strScore)) {
                return strScore;
            }
            return "90";
        }

        if (hostScore == visitScore) {
            if (tieSet.contains(strScore)) {
                return strScore;
            }
            return "99";
        }

        if (hostScore < visitScore) {
            if (failSet.contains(strScore)) {
                return strScore;
            }
            return "09";
        }

        return "";
    }

    private static String getMatchResult(int hostScore, int visitScore) {
        if (hostScore > visitScore)//胜
        {
            return "3";
        } else if (hostScore == visitScore)//平
        {
            return "1";
        } else//负
        {
            return "0";
        }
    }

    public static String getActualIssueName(int lotteryId, String issueFromDataTeam) {
        switch (lotteryId) {
            case 90:
            case 91:
                return DateUtil.format(DateUtil.format(issueFromDataTeam, "yyyy/M/d HH:mm:ss"), "yyyyMMdd");
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

    /**
     * 玩法sp值 9001 让球胜平负，9002 总进球，9003 猜比分，9004 半全场，9006 胜平负
     * @param sp
     * @param playId
     * @param key
     * @return
     */
    public static String generateJczqSp(String sp ,int playId,String key){
        try {
            String[] sps = sp.split(",");
            switch(playId){
                case 9001:
                    return key.equals("3")?sps[2]:(key.equals("1")?sps[1]:sps[0]);
                case 9002:
                    if(Integer.parseInt(key)>=7){
                        return sps[7];
                    }else{
                        return sps[Integer.parseInt(key)];
                    }
                case 9003:
                    return new HashMap<String,String>(){{
                        this.put("10",sps[9]); this.put("20",sps[15]); this.put("21",sps[16]);
                        this.put("30",sps[21]);this.put("31",sps[22]); this.put("32",sps[23]);
                        this.put("40",sps[25]);this.put("41",sps[26]); this.put("42",sps[27]);
                        this.put("50",sps[28]);this.put("51",sps[29]); this.put("52",sps[30]);
                        this.put("90",sps[2]); this.put("00",sps[3]);  this.put("11",sps[10]);
                        this.put("22",sps[17]);this.put("33",sps[24]); this.put("99",sps[1]);
                        this.put("01",sps[4]); this.put("02",sps[5]);  this.put("12",sps[11]);
                        this.put("03",sps[6]); this.put("13",sps[12]); this.put("23",sps[18]);
                        this.put("04",sps[7]); this.put("14",sps[13]); this.put("24",sps[19]);
                        this.put("05",sps[8]); this.put("15",sps[14]); this.put("25",sps[20]);
                        this.put("09",sps[0]);
                    }}.get(key);
                case 9004:
                    return new HashMap<String,String>(){{
                        this.put("00",sps[0]);this.put("01",sps[1]);this.put("03",sps[2]);
                        this.put("10",sps[3]);this.put("11",sps[4]);this.put("13",sps[5]);
                        this.put("30",sps[6]);this.put("31",sps[7]);this.put("33",sps[8]);
                    }}.get(key);
                case 9006:
                    return key.equals("3")?sps[2]:(key.equals("1")?sps[1]:sps[0]);
            }
        }catch (Exception e){
            return "";
        }
        return "";
    }

    /**
     * 玩法sp值 9101 让分胜负，9102 胜负，9103 胜分差，9104 大小分,由{@link #getJclqMatchResult}推演而来
     * @param sp
     * @param playId
     * @param key
     * @return
     */
    public static String getJclqSp(String sp ,int playId,String key){
        try {
            String[] sps = sp.split(",");
            switch(playId){
                case 9101:
                    return key.split("|")[0].equals("2")?sps[1]:sps[2];
                case 9102:
                    return key.equals("2")?sps[0]:sps[1];
                case 9103:
                    return new HashMap<String,String>(){{
                        this.put("01",sps[0]); this.put("02",sps[1]); this.put("03",sps[2]);
                        this.put("04",sps[3]);this.put("05",sps[4]); this.put("06",sps[5]);
                        this.put("11",sps[6]);this.put("12",sps[7]); this.put("13",sps[8]);
                        this.put("14",sps[9]);this.put("15",sps[10]); this.put("16",sps[11]);
                    }}.get(key);
                case 9104:
                    return key.split("|")[0].equals("2")?sps[1]:sps[2];
            }
        }catch (Exception e){
            return "";
        }
        return "";
    }

}
