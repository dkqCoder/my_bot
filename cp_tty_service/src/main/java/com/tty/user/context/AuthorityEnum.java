package com.tty.user.context;

/**
 * @author shenwei
 * Created by shenwei on 2017/1/19.
 * 礼包种类
 */
public enum AuthorityEnum {
    IDENTITY("身份勋章", 0), LEVELUP("升级礼包", 1), PARTICULAR("专享礼包", 2), LIMITEXCHANGE("等级限兑", 3), SPEEDINTEGRAL("积分倍速", 4), VIPPHONE("专享客服", 5), INTEGRALEXCHANGE("积分兑换", 6);

    private String name;
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    AuthorityEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

}
