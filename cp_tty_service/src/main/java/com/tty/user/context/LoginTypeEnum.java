package com.tty.user.context;

/**
 * @author shenwei on 2017/3/24.
 */
public enum LoginTypeEnum {
    APP("APP登录", 1);

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

    LoginTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
