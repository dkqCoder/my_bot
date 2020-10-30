package com.tty.data.context;

/**
 * Created by shenwei on 2017/3/8.
 */
public enum BasedataLotteryTagEnum {
    GPC("GPC"), //高频彩
    JCC("JCC"),  //奖池彩
    JCZC("JCZC"),  // 竞彩   足彩
    NOTAG("NOTAG");
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    BasedataLotteryTagEnum(String name) {
        this.name = name;
    }
}
