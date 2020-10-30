package com.tty.data.common.util;

import com.jdd.basedata.commons.context.BasedataContext;
import com.jdd.fm.core.utils.HttpUtil;
import com.jdd.fm.core.utils.PropertiesUtil;

/**
 * Created by pdl on 2017/2/20.
 */
public class PushUtil {
    private static String pushUrl = PropertiesUtil.get(BasedataContext.PUSH_API_URL_KEY);

    public static boolean push(String params){
       return HttpUtil.httpClientPost(pushUrl, params);
    }

    public static class PushMsg {
        //彩种ID
        private int lotteryType;
        //开奖期次
        private String issue;
        //期号ID
        private int issueId;
        //开奖号码
        private String number;

        public int getLotteryType() {
            return lotteryType;
        }

        public void setLotteryType(int lotteryType) {
            this.lotteryType = lotteryType;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public int getIssueId() {
            return issueId;
        }

        public void setIssueId(int issueId) {
            this.issueId = issueId;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
