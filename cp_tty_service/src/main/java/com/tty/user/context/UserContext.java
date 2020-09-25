package com.tty.user.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhudonghai
 * @Date 2016-12-19
 * @Description 用户基础常量
 */
public class UserContext {
    public final static String DEFAULT_USER_NAME = "游客";
    //系统管理员id
    public final static Long INFO_SYSTEM_MANAGER_ID = -1L;
    //系统管理员名称
    public final static String INFO_SYSTEM_MANAGER_NAME = "系统通知";
    //积分兑换余额不足
    public final static String USER_DEDUCTION_INTEGRAL_LOW = "积分余额不足";
    public final static Integer USER_DEDUCTION_INTEGRAL_LOW_FLAG = 0;
    //积分兑换扣除正常
    public final static String USER_DEDUCTION_INTEGRAL_CORRECT = "积分变动正常";
    public final static Integer USER_DEDUCTION_INTEGRAL_CORRECT_FLAG = 1;
    //region 前端显示文字
    public final static String USER_BUTTON_TEXT_TODO = "去完成";
    public final static String USER_BUTTON_TEXT_CANGET = "可领取";
    public final static String USER_BUTTON_TEXT_DONE = "已领取";
    public final static String USER_BUTTON_TEXT_DONETODAY = "今日已领";
    public final static String USER_BUTTON_TEXT_DONEWEEK = "本周已领";
    public final static String USER_BUTTON_TEXT_NOTGET = "不可领取";
    public final static String USER_MISSION_FOREVERLOVE_NAME = "朝夕相处（%s/%s）";

    //用户状态
    public final static int USER_STATUS_STOP = 0;
    public final static int USER_STATUS_NORMAL = 1;

    //审核身份证状态 0 未审核 1 通过 2 拒绝
    public final static int USER_IDCARD_STATUS_UNCHECK = 0;
    public final static int USER_IDCARD_STATUS_PASS = 1;
    public final static int USER_IDCARD_STATUS_REFUSE = 2;

    public final static String REFUSE_MESSAGE = "您的身份证申诉失败，您可访问金山彩票客户端查看详情。如有疑问请您联系在线客服";
    public final static String ACCEPT_MESSAGE = "您的身份证申诉已通过，您可访问金山彩票客户端查看详情";

    //打败多少彩友最小值
    public final static Double USER_BEATINFO_MIN = 0.1;
    //首次进入我的积分页奖励的积分值
    public final static Integer USER_FIRSTVISITINTEGRALPAGE_INTEGRAL = 50;
    //一次性任务类型值
    public final static Integer USER_MISSION_TYPE_ONCE = 0;
    //每日任务类型值
    public final static Integer USER_MISSION_TYPE_DAY = 1;
    //每周任务类型值
    public final static Integer USER_MISSION_TYPE_WEEK = 7;
    //单日购彩奖励积分上限
    public final static Integer USER_DAILYBUY_INTEGRAL_UPPER = 5000;
    //每日登录成长值增加
    public final static Long USER_DAILY_LOGIN_GROWUP = new Long(5);
    //每日登录成长值变动描述
    public final static String USER_DAILY_LOGIN_GROWUP_DESC = "每日登录";
    //每日购彩成长值上限
    public final static Long USER_DAILYBUY_GROWUP_UPPER = new Long(5000);
    //每日购彩成长值变动描述
    public final static String USER_DAILYBUY_GROWUP_DESC = "购彩奖励";
    //用户头像域名
    public final static String USER_FACE_URL = "http://img.jdd.com/face";
    //.Net 接口域名
    public final static String CLIENT_API_URL = "http://client.jdd.com/action/NewMobileHandler.ashx";
    //等级权益按钮文字
    public final static String LEVEL_BUTTON_TEXT_TOSEE = "去看看";
    public final static String LEVEL_BUTTON_TEXT_DONE = "已领取";
    public final static String LEVEL_BUTTON_TEXT_CANGET = "领取";
    //等级权益按钮状态
    public final static Integer LEVEL_BUTTON_STATUS_DARK = 0; //置灰
    public final static Integer LEVEL_BUTTON_STATUS_HIGHLIGHT = 1; //高亮
    public final static Integer LEVEL_BUTTON_STATUS_JUMP = 2; //跳转
    //礼包类别
    public final static Integer LEVEL_UP_GIFT = 1; //升级礼包类别
    //用户等级名称映射
    public final static Map<Integer, String> USER_LEVEL_NAME_MAP = new HashMap<Integer, String>() {{
        put(0, "平民");
        put(1, "骑士");
        put(2, "子爵");
        put(3, "伯爵");
        put(4, "侯爵");
        put(5, "公爵");
        put(6, "亲王");
    }};
    public final static Integer PAGESIZE_MAX = 100;//pagesize最大值

    /**
     * @Author shenwei
     * @Date 2017/3/7 14:11
     * @Description 验证码字符集
     */
    public final static String CHARSETS = "0123456789";
    /**
     * @Author shenwei
     * @Date 2017/3/7 14:12
     * @Description 验证码长度
     */
    public final static Integer CHARLENGTH = 6;

    /**
     * @Author shenwei
     * @Date 2017/3/8 17:02
     * @Description 冻结时间限制
     */
    public final static Integer FREEZEMINUTES = 30;

    /**
     * @Author shenwei
     * @Date 2017/3/8 17:28
     * @Description 登录密码错误最大次数
     */
    public final static Integer LOGIN_FAIL_MAX = 5;

    /**
     * @Author shenwei
     * @Date 2017/3/20 11:39
     * @Description 单个验证码错误此时上限
     */
    public final static Integer VERIFY_FAIL_PER_MAX = 5;

    /**
     * @Author shenwei
     * @Date 2017/3/13 13:56
     * @Description 验证码错误最大次数
     */
    public final static Integer VERIFY_FAIL_MAX = 7;

    /**
     * @Author shenwei
     * @Date 2017/3/13 16:20
     * @Description server token expire seconds
     */
    public final static Integer TOKEN_LOSE_SECONDS = 3600 * 24 * 7;

    /**
     * @Author shenwei
     * @Date 2017/3/13 20:45
     * @Description 用户登录 md5 加密key
     */
    public final static String MD5Key = "Q56GtyNkop97H334TtyturfgErvvv98r";

    /**
     * @Author shenwei
     * @Date 2017/3/14 15:08
     * @Description 用户密码最短长度
     */
    public final static Integer passMinLength = 6;

    /**
     * type:7第三方账号绑定手机密码。100：首次绑定验证是否有多账号
     */
    public final static Integer USER_REGISTER_TYPE_THIRD_MOBILE_BIND = 7;

    public final static Integer passMaxLength = 15;
    public final static String USER_LOGIN_PAST_MSG = "登录已过期！请重新登录";
    public final static String USER_LOGIN_ERROR_MSG = "身份认证失败！请重新登录";
    public final static String USER_LOGIN_WRAN_MSG = "您的账号于%s在%s设备上%s登录，如果不是您本人操作，请您确认是否密码已经泄漏。您可通过客服冻结账户或在登录页找回密码";
    /**
     * 用户关注状态  已关注
     */
    public final static int USER_FANS_STATUS_ATTENTION = 1;
    /**
     * 用户关注状态  取消关注
     */
    public final static int USER_FANS_STATUS_CANCEL_ATTENTION = 0;

    public final static Integer USER_RESETIDCARDNUM_LOSE_SECONDS = 365 * 24 * 60;

    /**
     * @Author shenwei
     * @Date 2017/4/3 15:21
     * @Description wap站下载地址
     */
    public final static String WAP_DOWNLOAD_URL = "r.jdd.com/d";

    /**
     * @Author shenwei
     * @Date 2017/4/28 11:18
     * @Description 禁止登录文案
     */
    public final static String USER_LOGIN_FORBIDDEN = "尊敬的用户，系统检测到您的账号存在违规操作，请联系在线客服提供身份信息进行恢复";

    // 访问来源code
    public final static String PLATFORM_CODE_ANDROID = "Android";
    public final static String PLATFORM_CODE_PC = "pc";
    public final static String PLATFORM_CODE_IPHONE = "IPHONE";
    public final static String PLATFORM_CODE_WAP = "h5mobile";

    // 用户uuid缓存存放的value值。
    public final static String USER_REGISTER_UUID_IS_EXISTED = "1";

    // 注册成功发送短信开关
    public final static String USER_REGISTER_SEND_SMS_KEY = "sms.register.send.key";
    // true注册成功发送成功短信
    public final static String USER_REGISTER_SEND_SMS_VALUE = "true";

    // 注册成功短信内容
    public final static String USER_REGISTER_APP_SMS_CONTENT = "感谢注册金山奖多多，新人礼包已到账，请登录APP至我的-福利任务领取。";
    public final static String USER_REGISTER_PC_SMS_CONTENT = "感谢注册金山奖多多，出票更安全，派奖更效率！下载金山彩票APP，中奖信息不错过: r.jdd.com/d ";

    /**
     * @Author shenwei
     * @Date 2017/11/7 11:45
     * @Description 短信地址
     */
    public final static String SMS_URL = "http://sms-api.jdd.com/jdd/sms/public/sendMsgMq.do";

    /**
     * @Author shenwei
     * @Date 2017/12/12 11:47
     * @Description 大数据灰色用户校验地址
     */
    public final static String GRAY_USER_URL = "http://portrait-api.jdd.com/bigdata/private/innerHandler/checkGrayUser.do";

}
