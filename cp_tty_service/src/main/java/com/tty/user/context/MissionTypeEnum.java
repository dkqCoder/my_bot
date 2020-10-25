package com.tty.user.context;

/**
 * @author zhudonghai
 * @Date 2016-12-20
 * @Description 任务类型枚举
 */
public enum MissionTypeEnum {
    SYSTEM("系统奖励", 0), BINDIDCARD("绑定身份证奖励", 1), BINDBANKCARD("绑定银行卡奖励", 2),
    BINDMOBILE("绑定手机奖励", 3), RECHARGE("充值奖励", 4), DASHENMERCHANDISER("大神跟单奖励", 5),
    BUYLOTTERY("购彩奖励", 6), SIGNIN("签到奖励", 7), SHOWBILL("晒单奖励", 8), SHARE("分享奖励", 9),
    COMMENT("评论奖励", 10), FOREVERLOVE("朝夕相处", 11), FIRSTINTEGRALPAGE("首次进入我的积分页", 12);

    private String name;
    private int index;

    private MissionTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
