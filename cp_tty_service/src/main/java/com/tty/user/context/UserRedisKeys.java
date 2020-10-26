package com.tty.user.context;

/**
 * @author zhudonghai
 * @Date 2016-12-18
 * @Description 用户缓存相关常量
 */
public class UserRedisKeys {

    public static final int WEEK = 60 * 60 * 24 * 7;

    public static final int HOUR = 60 * 60;

    public static final int DAY = 60 * 60 * 24;

    public static final int THREE_DAY = 60 * 60 * 24 * 3;


    /**
     * @Author shenwei
     * @Date 2017/3/16 13:43
     * @Description hash key -> userId
     * value -> com.tty.user.dao.entity.UserInfoENT
     */

    public static final String USER_INFO_KEY_ID = "user:info:userid:%s";

    /**
     * hash key -> 用户手机号
     * value -> com.tty.user.dao.entity.UserInfoENT
     */

    public static final String USRE_INFO_MOBILE_LIST_NUMBER = "user:info:mobile:%s";

    /**
     * @Author zhuxinhai
     * 用户粉丝
     * $s -> userId
     * value -> set value 粉丝ID
     * score 序号
     */
    public final static String USER_RELATION_FANS_KEY = "user:relation:fans:%s";
    /**
     * @Author zhuxinhai
     * 用户关注
     * $s -> userId
     * value -> set valeu 关注人id
     * score 序号
     */
    public final static String USER_RELATION_ATTENTION_KEY = "user:relation:attention:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/16 13:43
     * @Description user base info hash
     * <p>
     * Hash :  key -> userId
     * value ->com.tty.user.dao.entity.UserBaseInfoENT
     */

    public static final String USER_BASE_INFO_KEY_ID = "user:baseinfo:userid:%s";

    /**
     * @Author shenwei
     * @Date 2016/12/19 11:12
     * @Description 用户维度相关 redis hash
     * <p>
     * %s -> 用户ID
     * hash -> key -> 属性名
     * value -> 属性值
     */
    public final static String USER_INFO_EXTENSION_KEY = "user:info:extension:userid:%s";


    /**
     * @Author shenwei
     * @Date 2016/12/19 12:53
     * @Description 用户首次充值满20 redis set
     * 已完成首次充值的用户ID
     */
    public final static String USER_SINGLEPAY_TWENTY_KEY = "user:singlepay:20:userids";

    /**
     * @Author shenwei
     * @Date 2016/12/19 12:55
     * @Description 用户首次跟单满足条件 redis set
     * 已完成首次跟单的用户ID
     */
    public final static String USER_FOLLOWSCHEME_KEY = "user:followscheme:userids";

    /**
     * @Author shenwei
     * @Date 2016/12/19 17:15
     * @Description 用户首次绑定手机号满足条件 redis set
     * 以完成首次绑定手机号的用户ID
     */
    public final static String USER_BINDPHONR_KEY = "user:bindphone:userids";

    /**
     * @Author shenwei
     * @Date 2016/12/21 18:59
     * @Description 用户首次绑定银行卡满足条件 redis set
     * 已完成首次绑定银行卡的用户
     */
    public final static String USER_BINDBANK_KEY = "user:bindbank:userids";

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:01
     * @Description 用户首次绑定身份证满足条件 redis set
     * 已完成首次绑定身份证的用户ID
     */
    public final static String USER_BINDCERT_KEY = "user:bindcert:userids";

    /**
     * @Author shenwei
     * @Date 2016/12/19 13:04
     * @Description 用户首次购买某彩种满足条件 redis set
     * 已完成首次购彩用户ID
     */
    public final static String USER_BUYSCHEME_KEY = "user:buyscheme:userids:lotteryid:%s";

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:39
     * @Description 用户每日晒单满足条件 redis set
     * 已完成每日晒单用户ID
     */
    public final static String USER_DAILY_SHOWSCHEME_KEY = "user:dailyshowscheme:userids";

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:41
     * @Description 用户每日分享满足条件 redis set
     * 已完成每日分享的用户ID
     */
    public final static String USER_DAILY_SHARE_KEY = "user:dailyshare:userids";

    /**
     * @Author shenwei
     * @Date 2016/12/21 19:41
     * @Description 用户每日评论满足条件 redis set
     * 已完成每日评论的用户ID
     */
    public final static String USER_DAILY_COMMENT_KEY = "user:dailycomment:userids";

    /**
     * @Author shenwei
     * @Date 2016/12/21 20:55
     * @Description 用户进入我的积分名单页面 redis set
     * 已完成进入我的积分页面的用户ID
     */
    public final static String USER_ENTER_INTEGRAL_KEY = "user:enterintegral_userids";

    /**
     * @Author shenwei
     * @Date 2016/12/28 17:57
     * @Description 用户每日签到redis set
     * 已完成每日签到的用户ID
     */
    public final static String USER_DAILY_SIGNIN_KEY = "user:dailysignin:userids";

    /**
     * @Author shenwei
     * @Date 2016/12/19 14:40
     * @Description 用户每日购彩积分奖励 redis string
     * 每日购彩积分奖励
     * %s -> userId
     * value -> 积分
     */
    public final static String USER_DAILYBUY_KEY = "user:dailybuy:integral:userid:%s";

    /**
     * @Author shenwei
     * @Date 2017/1/9 10:32
     * @Description 用户每日购彩成长值奖励 redis string
     * 每日购彩成长值奖励
     * %s -> userId
     * value ->用户每日购彩奖励
     */
    public final static String USER_DAILYBUY_GROWUP_KEY = "user:dailybuy:growup:userid:%s";

    /**
     * @Author shenwei
     * @Date 2017/1/6 17:49
     * @Description 用户每日登录redis set
     * 每天登陆的用户ID SET
     */
    public final static String USER_DAILY_LOGIN_KEY = "user:dailylogin:userids";

    /**
     * @Author shenwei
     * @Date 2017/1/13 15:42
     * @Description 用户进入会员中心redis set
     * 每日进入会员中心的用户ID SET
     */
    public final static String USER_ENTER_LEVEL_CENTER_KEY = "user:enterlevelcenter:userids";

    /**
     * @Author shenwei
     * @Date 2017/1/16 15:47
     * @Description 消费用户redis set
     * 每日消费的用户ID SET
     */
    public final static String USER_BUYED_KEY = "user:buyed:userids";

    /**
     * @Author shenwei
     * @Date 2016/12/21 15:42
     * @Description 用户积分收支明细分页内容 redis string
     */
    public final static String USER_INTEGRAL_RECORDS_PAGES_KEY = "user:integral_record_userid:%s:page:%d";

    /**
     * @Author shenwei
     * @Date 2016/12/23 13:27
     * @Description 用户积分收支分页的 keys
     */
    public final static String USER_INTEGRAL_RECORDS_PAGESKEYS_KEY = "user:integrals_record_keys_userid:%s";

    /**
     * @Author shenwei
     * @Date 2017/1/12 15:29
     * @Description 用户等级击败比例 redis string
     */
    public final static String USER_LEVEL_BEAT_KEY = "user:level_beat:level:%d";

    /**
     * @Author shenwei
     * @Date 2017/1/16 16:32
     * @Description 用户中心redis string
     */
    public final static String USER_LEVEL_CENTER_KEY = "user:levelcenter:userid:%s";

    // 用户基础信息公共缓存key
    public final static String CACHE_USER_BASE_BY_USER_ID_KEY = "user_101:";

    public final static String CACHE_USER_INFO_BY_USER_ID_KEY = "user_102:";

    //朝夕相处 连续五日购买
    public final static String USER_MISSION_FOREVERLOVEDAYS_KEY = "user:mission:foreverlovedays:userid:%s";

    //缓存某个用户的任务信息 value -> list<UserIntegralMissionResult>
    public final static String USER_MISSION_INFO_KEY = "user:mission:info:userid:%s";

    //等级对应的奖励 $s -> 等级 value -> List<UserLevelAuthority>
    public final static String USER_LEVEL_AUTHORITY_KEY = "user:levelcenter:level:authority:%s";

    //token key %s -> userId
    public final static String USER_LOGIN_TOKEN = "user:login:token:%s";

    //用户签到 $s -> userId value-> UserSignInModel 积分 累计积分 连续签到天数
    public final static String USER_SIGN_INFO = "user:sign:info:%s";

    //-------------------------------------用户重构

    /**
     * @Author shenwei
     * @Date 2017/3/7 13:40
     * @Description 根据用户名获取用户 param:username
     * set key -> userId  value -> com.tty.user.dao.entity.UserInfoENT
     */

    public static final String USER_INFO_NAME_KEY_NAME = "user:info:name:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/7 15:18
     * @Description 用户注册密码 param:username   value:password
     */
    public final static String USER_REGISTER_PWD = "user:registerPwd:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/7 20:12
     * @Description 存放在redis里已使用到的最大的UserID
     */
    public final static String USER_NEW_ID = "user_new_id";

    /**
     * @Author shenwei
     * @Date 2017/3/8 16:15
     * @Description 用户登录密码错误冻结时间
     * $s -> userId value->冻结时刻
     */
    public final static String USER_LOGIN_ERROR_FREEZE_USERID = "user:password:errorFreezeTime:userId:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/8 16:17
     * @Description 用户登录密码错误次数
     */
    public final static String USER_LOGIN_ERROR_COUNT_USERID = "user:password:errorCount:userId:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/17 14:47
     * @Description 用户绑定手机号验证码获取次数
     */
    public final static String USER_BIND_MOBILE_GET_COUNT_USERID = "user:bindMobile:getCount:userId:%s_mobile:%s";
    /**
     * @Author linian
     * @Date 2017/8/02 13:13
     * @Description 用户原手机号验证码获取次数
     */
    public final static String USER_OLD_MOBILE_GET_COUNT_USERID = "user:oldMobile:getCount:userId:%s_mobile:%s";
    /**
     * @Author shenwei
     * @Date 2017/3/13 11:53
     * @Description 用户绑定手机号验证码错误次数
     */
    public final static String USER_BIND_MOBILE_ERROR_COUNT_USERID = "user:bindMobile:errorCount:userId:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/13 11:53
     * @Description 用户原手机号验证码错误次数
     */
    public final static String USER_OLD_MOBILE_ERROR_COUNT_USERID = "user:oldMobile:errorCount:userId:%s";
    /**
     * @Author shenwei
     * @Date 2017/3/13 11:56
     * @Description 用户绑定手机号验证码
     */
    public final static String USER_BIND_MOBILE_VERIFY_CODE_USERID = "user:bindMobile:verifyCode:userId:%s_mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/27 14:05
     * @Description 根据userId 以及验证码获取用户绑定手机号
     */
    public final static String USER_BIND_MOBILE_VERIFY_CODE_MOBILE = "user:bindMobile:verifyCode:userId:%s_verifyCode:%s";

    /**
     * @Author linian
     * @Date 2017/8/2 11:56
     * @Description 用户原手机号验证码
     */
    public final static String USER_OLD_MOBILE_VERIFY_CODE_USERID = "user:oldMobile:verifyCode:userId:%s";

    /**
     * @Author linian
     * @Date 2017/8/2 11:50
     * @Description 根据userId 以及验证码获取用户绑定手机号
     */
    public final static String USER_OLD_MOBILE_VERIFY_CODE_MOBILE = "user:oldMobile:verifyCode:userId:%s_verifyCode:%s";
    /**
     * @Author shenwei
     * @Date 2017/3/17 14:48
     * @Description 用户短信验证码登录验证码获取次数
     */
    public final static String USER_LOGIN_SMS_GET_COUNT_MOBILE = "user:loginSms:getCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/15 20:27
     * @Description 用户短信验证码登录验证码错误次数
     */
    public final static String USER_LOGIN_SMS_ERROR_COUNT_MOBILE = "user:loginSms:errorCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/15 20:28
     * @Description 用户短信验证码登陆验证码
     */
    public final static String USER_LOGIN_SMS_VERIFY_CODE_MOBILE = "user:loginSms:verifyCode:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/27 12:05
     * @Description 用户手机号登录获取验证码
     */
    public final static String USER_LOGIN_MOBILE_VERIFY_CODE_MIBILE = "user:loginMobile:verifyCode:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/27 12:05
     * @Description 用户手机号登录获取验证码获取次数
     */
    public final static String USER_LOGIN_MOBILE_GET_COUNT_MIBILE = "user:loginMobile:getCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/27 12:05
     * @Description 用户手机号登录获取验证码错误次数
     */
    public final static String USER_LOGIN_MOBILE_ERROR_COUNT_MIBILE = "user:loginMobile:errorCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/17 14:51
     * @Description 用户忘记密码找回密码验证码获取次数
     */
    public final static String USER_FORGET_PASS_GET_COUNT = "user:forgetLoginPass:getCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/16 17:22
     * @Description 用户忘记密码找回密码验证码
     */
    public final static String USER_FORGET_PASS_VERIFY_CODE = "user:forgetLoginPass:verifyCode:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/16 17:25
     * @Description 用户忘记密码找回密码验证码错误次数
     */
    public final static String USER_FORGET_PASS_ERROR_COUNT = "user:forgetLoginPass:errorCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/16 17:35
     * @Description 用户忘记密码找回密码，验证码填写是否正确 存在且值为1 及正确. expire minutes 10
     */
    public final static String USER_FORGET_PASS_VERIFY_CODE_VALIDE = "user:forgetLoginPass:verifyCodeValid:mobile:%s";
    /**
     * 手机短信登录 验证状态
     */
    public final static String USER_SMS_LOGIN_VERIFY_CODE_VALIDE = "user:sms:login:verifyCodeValid:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/20 14:27
     * @Description 用户忘记支付密码找回密码验证码
     */
    public final static String USER_FORGET_PAYPASS_VERIFY_CODE = "user:forgetPayPass:verifyCode:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/20 14:31
     * @Description 用户忘记支付密码找回密码验证码获取次数
     */
    public final static String USER_FORGET_PAYPASS_GET_COUNT = "user:forgetPayPass:getCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/20 14:31
     * @Description 用户忘记支付密码找回密码验证码错误次数
     */
    public final static String USER_FORGET_PAYPASS_ERROR_COUNT = "user:forgetPayPass:errorCount:userId:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/20 14:48
     * @Description 用户快捷注册验证码
     */
    public final static String USER_QUICK_REGISTER_VERIFY_CODE = "user:quickRegister:verifyCode:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/20 14:49
     * @Description 用户快捷注册验证码获取次数
     */
    public final static String USER_QUICK_REGISTER_GET_COUNT = "user:quickRegister:getCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/20 14:49
     * @Description 用户快捷注册验证码错误次数
     */
    public final static String USER_QUICK_REGISTER_ERROR_COUNT = "user:quickRegister:errorCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/27 11:19
     * @Description 第三方用户绑定手机号获取验证码
     */
    public final static String USER_THIRD_BINDMOBILE_VERIFY_CODE = "user:thirdBindMobile:verifyCode:userId:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/27 11:19
     * @Description 第三方用户绑定手机号获取验证码获取次数
     */
    public final static String USER_THIRD_BINDMOBILE_GET_COUNT = "user:thirdBindMobile:getCount:userId:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/27 11:19
     * @Description 第三方用户绑定手机号获取验证码
     */
    public final static String USER_THIRD_BINDMOBILE_ERROR_COUNT = "user:thirdBindMobile:errorCount:userId:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/31 18:12
     * @Description 猎豹登录获取验证码
     */
    public final static String USER_CMTT_VERIFY_CODE = "user:cmtt:verifyCode:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/31 18:18
     * @Description 猎豹登录验证码获取次数
     */
    public final static String USER_CMTT_GET_COUNT = "user:cmtt:getCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/31 18:28
     * @Description 猎豹登录验证码错误次数
     */
    public final static String USER_CMTT_ERROR_COUNT = "user:cmtt:errorCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/23 17:15
     * @Description 用户wap端获取手机验证码
     */
    public final static String USER_WAP_VERIFY_CODE = "user:wap:verifyCode:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/27 11:19
     * @Description 用户wap端手机号获取验证码获取次数
     */
    public final static String USER_WAP_GET_COUNT = "user:wap:getCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/6/28 10:05
     * @Description 现有账号绑定微信验证码
     */
    public final static String USER_WECHAT_BIND_VERIFY_CODE = "user:weChat:verifyCode:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/6/28 10:06
     * @Description 现有账号绑定微信验证码获取次数
     */
    public final static String USER_WECHAT_BIND_GET_COUNT = "user:weChat:getCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/6/28 10:06
     * @Description 现有账号绑定微信验证码椒盐错误次数
     */
    public final static String USER_WECHAT_BIND_ERROR_COUNT = "user:weChat:errorCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/13 12:02
     * @Description 接收验证码的手机号和填入的手机号是否相同redis string
     */
    public final static String USER_BIND_MOBILE_VERIFY_CODE_ORIGINAL_USERID = "user:bindMobile:verifyCode:originalUserId:%s_mobile:%s";
    /**
     * 重置身份证信息中未审核
     * $s -> userId
     * value -> com.tty.user.dao.entity.UserIdcardResetENT
     */
    public final static String USER_RESET_IDCARDNUM_UNCHECK = "user:reset_idcardnum_uncheck:%s";

    /**
     * 多账户默认用户登录信息
     */
    public final static String USER_DEFAULT_REGISTER_NAME = "user:defaultRegisterName";

    public final static String USER_LAST_LOGIN_TIME = "user:last_login_time";

    //连续签到天数缓存 user:sign:days:userid
    public final static String USER_SIGN_DAYS_KEY = "user:signin:days:%s";

    /**
     * 用户三方登录信息
     */
    public final static String USER_USER_THIRD_INFO_KEY = "user:third:info:%s:%s";

    /**
     * 用户体育疯防换账号用户id绑定toKen
     */
    public final static String USER_USER_THIRD_UID_TOKEN_INFO_KEY = "user:third:info:uid:%s:%s";

    /**
     * @Author luo yinghua
     * @Date 2017/5/15
     * @Description 统一存放用户注册uuid的hash key
     */
    public final static String USER_REGISTER_UUID_KEY = "user:register.uuid";

    /**
     * @Author luo yinghua
     * @Date 2017/5/15
     * @Description hash中存放用户的uuid的key
     */
    public final static String USER_REGISTER_UUID_USER_KEY = "user:register.uuid.user:%s";

    /**
     * 用户小红点信息
     */
    public final static String USER_RED_DOT_KEY = "user:reddot:userId:%s";

    /**
     * 新增彩金卡数
     */
    public final static String USER_RED_DOT_CJCARDNEWCOUNT_KEY = "user:reddot:cjcardnewcount:userId:%s";

    /**
     * 最新到账的充值优惠卡张数
     */
    public final static String USER_RED_DOT_CZCARDNUM_KEY = "user:reddot:czcard:userId:%s";

    /**
     * @Author shenwei
     * @Date 2017/7/28 13:19
     * @Description 手机号归属省份
     */
    public final static String USER_MOBILE_BELONG_KEY = "user:mobile:belong:mobileSeg:%s";

    /**
     * @Author shenwei
     * @Date 2017/7/28 13:29
     * @Description 禁用渠道省份城市
     */
    public final static String USER_ENTRANCE_FOBIDDEN_KEY = "user:entrance:forbidden:cmdName:%s_province:%s_city:%s";

    /**
     * @Author shenwei
     * @Date 2017/8/23 10:02
     * @Description 手机号secboot校验
     */
    public final static String USER_SECBOOT_LEVEL_MOBILE_KEY = "user:secboot:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/17 14:48
     * @Description 华为活动验证码获取次数
     */
    public final static String USER_HUAWEI_GET_COUNT_MOBILE = "user:huawei:getCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/15 20:27
     * @Description 华为活动验证码错误次数
     */
    public final static String USER_HUAWEI_ERROR_COUNT_MOBILE = "user:huawei:errorCount:mobile:%s";

    /**
     * @Author shenwei
     * @Date 2017/3/15 20:28
     * @Description 华为活动验证码
     */
    public final static String USER_HUAWEI_VERIFY_CODE_MOBILE = "user:huawei:verifyCode:mobile:%s";

    /**
     * 用户禁登过滤表的序列
     */
    public static final String USER_FILTER_DETAIL_INCR_KEY = "user:filter:detail:key";

    /**
     * @Author shenwei
     * @Date 2017/12/1 10:02
     * @Description 禁止ip set
     */
    public static final String USER_FORBIDDEN_IP = "user:forbidden:ip";

    /**
     * @Author shenwei
     * @Date 2017/12/1 10:02
     * @Description 禁止uuid set
     */
    public static final String USER_FORBIDDEN_UUID = "user:forbidden:uuid";

    /**
     * @Author shenwei
     * @Date 2017/12/12 13:44
     * @Description 通付盾验证key  uuid，ip，userid，biztype
     */
    public static final String USER_TFD = "user:tfd_%s_%s_%s_%s";

    /**
     * @Author shenwei
     * @Date 2017/12/12 13:55
     * @Description 大数据灰色用户
     */
    public static final String GRAY_USER_CHECKED = "user:gray_userId:%s";

    /**
     * @Author shenwei
     * @Date 2017/12/22 10:26
     * @Description 大概用户总数 ,保存一天
     */
    public static final String USER_COUNT_TODAY = "user:count";
    /**
     * @Author shenwei
     * @Date 2017/12/25 13:35
     * @Description 汽车之家活动
     */
    public static final String USER_BY_CMD_UUID = "user:cmdName_%s_uuid_%s";

    /**
     * @Author
     * @Date 2017/12/25 14:29
     * @Description 汽车之家活动临时id
     */
    public final static String USER_TEMP_ID = "user_temp_id";

    /**
     * 图形验证码key
     */
    public static final String USER_CAPTCHA_KEY = "user:captcha:key:%s";

}
