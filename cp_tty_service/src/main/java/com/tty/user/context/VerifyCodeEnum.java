package com.tty.user.context;/**
 * Created by shenwei on 2017/3/16.
 */

/**
 * @author shenwei
 * @create 2017-03-16
 */
public enum VerifyCodeEnum {
    BINDMOBILE("绑定手机", 1),
    LOGINBYSMS("短信验证码登录", 2),
    FORGETLOGINPASS("忘记登录密码", 3),
    QUICKREGISTER("快捷注册获取验证码", 4),
    LOGINFAST("快速登录", 5),
    FORGETPAYPASS("忘记支付密码", 6),
    WAPVERIFYCODE("wap获取验证码", 7),
    THIRDBINDMOBILE("第三方用户绑定手机号密码", 8),
    WECHATBIND("微信绑定现有手机号获取验证码", 10),
    VALIDATEOLDMOBILE("校验原手机号", 11);

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

    VerifyCodeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
