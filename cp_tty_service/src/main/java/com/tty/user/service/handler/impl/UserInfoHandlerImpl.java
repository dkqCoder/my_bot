package com.tty.user.service.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.utils.Base64Util;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.PropertiesUtil;
import com.tty.common.utils.FileUploadManager;
import com.tty.common.utils.Result;
import com.tty.user.common.tupu.SignatureAndVerifyUtil;
import com.tty.user.common.tupu.TuPuModel;
import com.tty.user.common.utils.*;
import com.tty.user.context.LoginTypeEnum;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.context.VerifyCodeEnum;
import com.tty.user.controller.model.params.*;
import com.tty.user.controller.model.result.TokenLoginResult;
import com.tty.user.controller.model.result.UserNameIdResult;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.entity.UserIdcardResetENT;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.model.params.ResetPwdParam;
import com.tty.user.model.params.UserLevelParams;
import com.tty.user.service.*;
import com.tty.user.dto.UserIdCardResetInfoDTO;
import com.tty.user.dto.UserfaceDTO;
import com.tty.user.service.handler.UserInfoHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.net.URLDecoder;
import java.security.PrivateKey;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by donne on 2017/3/14.
 */
@Component("userInfoHandler")
public class UserInfoHandlerImpl implements UserInfoHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoHandlerImpl.class);
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserFansService userFansService;
    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private VerifyCodeCheckService verifyCodeCheckService;
    FileUploadManager fileUploadManager;
    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private UserMasterInfoService userMasterInfoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserSmsLoginService userSmsLoginService;

    /**
     * @Author shenwei
     * @Date 2017/3/15 11:25
     * @Description 103 ???????????????????????????
     */
    @Override
    public Result getBindMobileVerifyCode(String traceId, String userID, String params) {
        Result result = new Result();
        BindMobileVerifyParam bindMobileVerifyParam = GfJsonUtil.parseObject(params, BindMobileVerifyParam.class);
        if (bindMobileVerifyParam == null) {
            logger.error("?????????????????????????????????,????????????,userId:{},params:{},traceId:{}", userID, params, traceId);
            return getFailResult();
        }
        boolean checkFlag = verifyCodeService.checkCaptcha(bindMobileVerifyParam.getCaptchaKey(), bindMobileVerifyParam.getCaptchaValue());
        if (!checkFlag) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????????????????");
            return result;
        }
        if (StringUtils.isBlank(bindMobileVerifyParam.getMobile())) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????");
            return result;
        }
        if (!MobileUtil.isMobileNO(bindMobileVerifyParam.getMobile())) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????");
            return result;
        }
        String oldMobile = bindMobileVerifyParam.getOldMobile();
        String mobile = bindMobileVerifyParam.getMobile().trim();
        return verifyCodeService.getVerifyCode(userID, mobile, VerifyCodeEnum.BINDMOBILE, traceId, oldMobile);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/15 10:32
     * @Description 104 ????????????
     */
    @Override
    public Result bindUserMobile(String traceId, String userID, String params, ClientRequestHeader header) {
        Result result = new Result();
        BindMobileParams bindMobileParams = GfJsonUtil.parseObject(params, BindMobileParams.class);
        if (bindMobileParams == null) {
            logger.error("??????????????????,??????????????????,userId:{},params:{},traceId:{}", userID, params, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????");
            return result;
        }
        if (StringUtils.isBlank(bindMobileParams.getVerifyCode())) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????");
            return result;
        }
        if (StringUtils.isBlank(bindMobileParams.getMobile())) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????");
            return result;
        }
        if (!MobileUtil.isMobileNO(bindMobileParams.getMobile())) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????");
            return result;
        }
        return userInfoService.bindUserMobile(userID, bindMobileParams.getMobile(), bindMobileParams.getVerifyCode(), traceId, header);
    }

    /**
     * @Author shenwei
     * @Date 14:48
     * @Description 102 ????????????????????????????????????
     */
    @Override
    public Result bindUserRealityNameAndCertCard(String traceId, String userID, String params, ClientRequestHeader header) {
        RealityNameIDCardParams realityNameIDCardParams = GfJsonUtil.parseObject(params, RealityNameIDCardParams.class);
        if (realityNameIDCardParams == null) {
            return getFailResult();
        }
        String realityName = "";
        if (realityNameIDCardParams.getRealityName() != null) {
            realityName = realityNameIDCardParams.getRealityName().trim();
        }
        String idCard = realityNameIDCardParams.getIdCard();
        return userBaseInfoService.bindUserRealityNameAndCertCard(userID, realityName, idCard, traceId, header);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/14 14:24
     * @Description 106??????????????????
     */
    @Override
    public Result changeUserPassword(String traceId, String userID, String params) {
        Result result = new Result();
        PasswordParams passwordParams = GfJsonUtil.parseObject(params, PasswordParams.class);
        if (passwordParams == null) {
            logger.error("??????????????????,params:{} traceId:{}", params, traceId);
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            return result;
        }
        String oldPwd = passwordParams.getOldPw();
        String newPwd = passwordParams.getNewPw();
        if (StringUtils.isEmpty(newPwd)) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????");
            return result;
        }
        if (newPwd.length() < UserContext.passMinLength || newPwd.length() > UserContext.passMaxLength) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????6-????????????6-15???,???????????????");
            return result;
        }
        if (newPwd.equals("123456") || newPwd.equals("111111") || newPwd.equals("abcdef")) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????");
            return result;
        }
        if (newPwd.equals(oldPwd)) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????,????????????????????????");
            return result;
        }
        if (newPwd != newPwd.trim()) {
            result.setCode(Result.ERROR);
            result.setMsg("????????????????????????,?????????6-15??????????????????");
            return result;
        }
        return userInfoService.changeUserPassword(userID, oldPwd, newPwd, traceId);
    }

    /**
     * ??????????????????token?????????????????????????????? action = 1061
     * 1.??????id?????????id<=0 ????????????username??????id) ????????? ????????????
     * 2.????????????????????????????????????????????????
     * 3.??????????????????????????????????????????????????????????????????????????????????????????
     * 4.?????????????????????
     * 5.????????????
     *
     * @param traceId
     * @param params
     * @return
     */
    @Override
    public Result resetPassword(String traceId, String params, ClientRequestHeader header) {
        Result result = new Result();
        ResetPwdParam resetPwdParam = GfJsonUtil.parseObject(params, ResetPwdParam.class);
        if (null == resetPwdParam) {
            logger.error("??????????????????,params:{} traceId:{}", params, traceId);
            return getFailResult();
        }

        if (StringUtils.isBlank(resetPwdParam.getNewPw())) {
            logger.error("??????????????????,params:{} traceId:{}", params, traceId);
            result.setCode(Result.ERROR);
            result.setMsg("????????????????????????,???????????????");
            return result;
        }
        String realUserId = "";

        Result checkResult = verifyCodeCheckService.checkForgetLoginPass(resetPwdParam.getVerifyCode(), resetPwdParam.getMobile());
        if (checkResult.getCode() < 0) {
            return checkResult;
        }

        List<UserInfoENT> list = userInfoService.getUserInfoByMobile(resetPwdParam.getMobile());
        if (CollectionUtils.isEmpty(list)) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????????????????,???????????????");
            return result;
        }
        if (CollectionUtils.isNotEmpty(list) && list.size() > 1) {
            String defaultName = userInfoService.getUserDefaultName(resetPwdParam.getMobile(), traceId);
            if (StringUtils.isBlank(defaultName)) {
                result.setCode(Result.ERROR);
                result.setMsg("???????????????,????????????????????????");
                return result;
            }
            UserInfoENT userInfoENT = userInfoService.getUserInfoByLoginName(defaultName);
            if (userInfoENT == null) {
                logger.warn("1061 ??????????????????????????????????????? traceId:{},defaultName:{}", traceId, defaultName);
            }
            realUserId = String.valueOf(userInfoENT.getUserId());
        } else {
            realUserId = String.valueOf(list.get(0).getUserId());
        }
        return userInfoService.changeUserPassword(realUserId, resetPwdParam.getNewPw(), traceId);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/16 16:04
     * @Description 1030 ????????????????????????
     */
    @Override
    public Result getForgetPassVerifyCode(String traceId, String params) {
        Result result = new Result();
        try {
            ForgetPassParams forgetPassParams = GfJsonUtil.parseObject(params, ForgetPassParams.class);
            if (forgetPassParams == null) {
                logger.error("[????????????????????????] ???????????? params:{} traceId:{}", params, traceId);
                return getFailResult();
            }
            boolean checkFlag = verifyCodeService.checkCaptcha(forgetPassParams.getCaptchaKey(), forgetPassParams.getCaptchaValue());
            if (!checkFlag) {
                result.setCode(Result.ERROR);
                result.setMsg("?????????????????????????????????");
                return result;
            }
            String mobile = forgetPassParams.getMobile();
            if (!MobileUtil.isMobileNO(mobile)) {
                result.setCode(Result.ERROR);
                result.setMsg("?????????????????????,???????????????");
                return result;
            }
            List<UserInfoENT> userInfoENTS = userInfoService.refreshUserInfoByMobile(mobile);
            if (CollectionUtils.isEmpty(userInfoENTS)) {
                result.setCode(Result.ERROR);
                result.setMsg("???????????????????????????");
                return result;
            }
            //??????????????????
            userInfoService.userListByLoginTime(userInfoENTS, result);
            //?????????????????????????????????  ???????????????????????????
            if (userInfoENTS.size() == 1) {
                UserInfoENT ent = userInfoENTS.get(0);
                if (userSmsLoginService.isUserFreeze(ent.getUserId().toString(), result)) {
                    result.setCode(Result.ERROR);
                    result.setMsg("??????????????????????????????,???????????????????????????");
                    return result;
                }
                result = verifyCodeService.getVerifyCode(ent.getUserId().toString(), mobile, VerifyCodeEnum.FORGETLOGINPASS, traceId, null);
                if (result != null && result.getCode() >= 0) {
                    UserNameIdResult r = new UserNameIdResult();
                    result.setCode(Result.SUCCESS);
                    r.setId(ent.getUserId());
                    r.setName(ent.getLoginName());
                    result.setData(r);
                }
                return result;
            } else {
                List<UserNameIdResult> lu = new ArrayList<>(userInfoENTS.size());
                for (UserInfoENT ent : userInfoENTS) {
                    UserNameIdResult ur = new UserNameIdResult();
                    ur.setId(ent.getUserId());
                    ur.setName(ent.getLoginName());
                    if (ent.getLastLoginTime() != null) {
                        ur.setLastlogintime(DateUtil.format(ent.getLastLoginTime()));
                    } else {
                        ur.setLastlogintime(DateUtil.format(ent.getRegisterTime()));
                    }
                    lu.add(ur);
                }
                result.setCode(1);
                result.setData(lu);
                return result;
            }
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????");
            logger.error("[????????????????????????] ?????? params:{} traceId:{},{}", params, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * @Author wenxiaoqing
     * @Date 2017/3/29 17:04
     * @Description 1031 ?????????????????????-??????????????????
     */
    @Override
    public Result getLoginPassVerifyCode(String traceId, String params) {
        Result result = new Result();
        ForgetPwdParam forgetPwdParam = GfJsonUtil.parseObject(params, ForgetPwdParam.class);
        if (forgetPwdParam == null) {
            logger.error("[?????????????????????-??????????????????] ???????????? traceId:{}", traceId);
            return getFailResult();
        }
        if (!MobileUtil.isMobileNO(forgetPwdParam.getMobile())) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????????????????");
            return result;
        }
        String userId = forgetPwdParam.getId();
        if (StringUtils.isNotBlank(userId) && Integer.valueOf(userId) > 0) {
            UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId);
            return verifyCodeService.getVerifyCode(userInfoENT.getUserId().toString(), userInfoENT.getMobileNumber(), VerifyCodeEnum.FORGETLOGINPASS, traceId, null);
        }
        result.setCode(Result.ERROR);
        result.setMsg("???????????????????????????");
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/20 15:11
     * @Description 1032 ?????????????????????????????????
     */
    @Override
    public Result checkForgetPassVerifyCode(String traceId, String params) {
        Result result = new Result();
        CheckForgetPassParams checkForgetPassParams = GfJsonUtil.parseObject(params, CheckForgetPassParams.class);
        if (checkForgetPassParams == null) {
            logger.error("[?????????????????????????????????] ???????????? traceId:{}", traceId);
            return getFailResult();
        }
        UserInfoENT userInfoENT = null;
        if (StringUtils.isNotBlank(checkForgetPassParams.getUserId())) {
            userInfoENT = userInfoService.getCurrentUserInfo(checkForgetPassParams.getUserId());
        }
        if (userInfoENT == null && StringUtils.isNotBlank(checkForgetPassParams.getUserName())) {
            userInfoENT = userInfoService.getUserInfoByLoginName(checkForgetPassParams.getUserName());
        }
        if (userInfoENT == null) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????,???????????????");
            return result;
        }
        String verifyCode = checkForgetPassParams.getVerifyCode();
        return verifyCodeCheckService.checkForgetLoginPass(verifyCode, userInfoENT.getMobileNumber().toString());
    }

    /**
     * @Author shenwei
     * @Date 2017/3/20 13:49
     * @Description 1201 ?????????????????????????????????
     */
    @Override
    public Result getForgetPayPassVerifyCode(String traceId, String userID, String params) {
        Result result = new Result();
        UserInfoENT user = userInfoService.getCurrentUserInfo(userID);
        if (user == null) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????");
            return result;
        }
        ForgetPayPassParams forgetPayPassParams = GfJsonUtil.parseObject(params, ForgetPayPassParams.class);
        if (forgetPayPassParams == null) {
            return getFailResult();
        }
        if (StringUtils.isEmpty(forgetPayPassParams.getMobile())) {
            result.setCode(Result.ERROR);
            result.setData("??????????????????");
            return result;
        }
        if (user.getMobileNumber() == null || !user.getMobileNumber().equals(forgetPayPassParams.getMobile())) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????????????????????????????????????????????????????????????????");
            return result;
        }
        String password = forgetPayPassParams.getPassword();
        String encryptPwd = MD5Utils.encrypt(password, UserContext.MD5Key);
        if (StringUtils.isBlank(password) || !Objects.equals(encryptPwd, user.getPassword())) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????");
            return result;
        }
        String mobile = forgetPayPassParams.getMobile();
        return verifyCodeService.getVerifyCode(userID, mobile, VerifyCodeEnum.FORGETPAYPASS, traceId, null);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/23 11:05
     * @Description 1202 ???????????????????????????????????????
     */
    @Override
    public Result checkForgetPayPassVerifyCode(String traceId, String userID, String params) {
        Result result = new Result();
        CheckForgetPayPassParams checkForgetPassParams = GfJsonUtil.parseObject(params, CheckForgetPayPassParams.class);
        if (checkForgetPassParams == null) {
            logger.error("[?????????????????????????????????] ???????????? traceId:{}", traceId);
            return getFailResult();
        }
        if (userInfoService.getCurrentUserInfo(userID) == null) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????,???????????????");
            return result;
        }
        result = verifyCodeCheckService.checkForgetPayPass(checkForgetPassParams.getVerifyCode(), userID);
        if (result.getCode() == Result.SUCCESS) {
            result.setCode(1);//??????
        }
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/23 11:29
     * @Description 1033 ???????????????????????????
     */
    @Override
    public Result getQuickRegisterVerifyCode(String traceId, String params) {
        Result result = new Result();
        QuickRegisterGetCodeParams quickRegisterGetCodeParams = GfJsonUtil.parseObject(params, QuickRegisterGetCodeParams.class);
        if (quickRegisterGetCodeParams == null) {
            return getFailResult();
        }
        // ???????????????
        boolean checkFlag = verifyCodeService.checkCaptcha(quickRegisterGetCodeParams.getCaptchaKey(), quickRegisterGetCodeParams.getCaptchaValue());
        if (!checkFlag) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????????????????");
            return result;
        }
        String mobile = quickRegisterGetCodeParams.getMobile();
        if (!MobileUtil.isMobileNO(mobile)) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????,???????????????");
            return result;
        }
        // ???????????????????????????
        return verifyCodeService.getVerifyCode(null, mobile, VerifyCodeEnum.QUICKREGISTER, traceId, null);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/23 11:29
     * @Description 1034 ?????????????????????????????????
     */
    @Override
    public Result checkQuickRegisterVerifyCode(String traceId, ClientRequestHeader header, String params) {
        CheckQuickRegisterParams checkQuickRegisterParams = GfJsonUtil.parseObject(params, CheckQuickRegisterParams.class);
        Result result = new Result();
        if (checkQuickRegisterParams == null) {
            return getFailResult();
        }
        if (EntranceUtil.forbiddenEntrance(header.getCmdName(), traceId) || EntranceUtil.forbiddenUUID(header.getPlatformCode(), header.getUuid(), traceId)) {
            result.setCode(Result.ERROR);
            result.setMsg("????????????,???????????????,???????????????");
            logger.warn("[??????????????????]header:{}", GfJsonUtil.toJSONString(header));
            return result;
        }
        String mobile = checkQuickRegisterParams.getMobile();
        if (!MobileUtil.isMobileNO(mobile)) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????");
            return result;
        }
        result = verifyCodeCheckService.checkQuickRegister(params, header);
        return result;
    }

    @Override
    public Result checkPCQuickRegister(String traceId, ClientRequestHeader header, String params) {
        CheckQuickRegisterParams checkQuickRegisterParams = GfJsonUtil.parseObject(params, CheckQuickRegisterParams.class);
        Result result = new Result();
        if (checkQuickRegisterParams == null) {
            return getFailResult();
        }
        String verifyCode = checkQuickRegisterParams.getVerifyCode();
        String mobile = checkQuickRegisterParams.getMobile();
        if (!MobileUtil.isMobileNO(mobile)) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????");
            return result;
        }
        result = verifyCodeCheckService.checkPCQuickRegister(verifyCode, mobile, traceId, header);
        return result;
    }

    @Override
    public Result getUserInfoByUserName(String traceId, String userName) {
        Result result = new Result();
        UserInfoENT ent = userInfoService.getUserInfoByLoginName(userName);
        if (ent == null) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????");
            return result;
        }
        UserBaseInfoENT userBaseInfoENT = userInfoService.getCurrentUserBaseInfo(ent.getUserId().toString());
        result.setData(userBaseInfoENT);
        return result;
    }

    @Override
    public Result getUserInfoByNameOrMobile(String traceId, String mobile) {
        Result result = new Result();
        List<UserInfoENT> list = new ArrayList<UserInfoENT>();
        UserInfoENT ent = userInfoService.getUserInfoByLoginName(mobile);
        if (ent != null) {
            list.add(ent);
        } else {
            list = userInfoService.getUserInfoByMobile(mobile);
            if (list == null || list.size() == 0) {
                result.setCode(Result.ERROR);
                result.setMsg("?????????????????????");
                return result;
            }
        }
        if (list != null && list.size() > 0) {
            for (UserInfoENT userInfo : list) {
                userInfo.setPassword("");
                userInfo.setPayPassword("");
            }
        }
        result.setData(list);
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/23 18:09
     * @Description 1018 wap?????????????????????
     */
    @Override
    public Result getWapVerifyCode(String traceId, String params) {
        Result result = new Result();
        WapVerifyParams wapVerifyParams = GfJsonUtil.parseObject(params, WapVerifyParams.class);
        if (wapVerifyParams == null) {
            return getFailResult();
        }
        if (!MobileUtil.isMobileNO(wapVerifyParams.getMobile())) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????");
            return result;
        }
        String mobile = wapVerifyParams.getMobile();

        result = verifyCodeService.getVerifyCode(null, mobile, VerifyCodeEnum.WAPVERIFYCODE, traceId, null);
        return result;
    }

    @Override
    public Result addUserFacePic(String traceId, MultipartFile file, String bfile, String fileName, String userId) {
        Result result = new Result();
        logger.info("??????????????????,userId:{} traceId:{}", userId, traceId);
        try {
            if (StringUtils.isBlank(userId)) {
                result.setCode(-1);
                result.setMsg("???????????????!");
                return result;
            }
            //bfile?????????base64??????
            String imgPath = null;

            //????????????,???????????????????????????
            logger.info("====????????????????????????====");
            long timestamp = System.currentTimeMillis();
            int nonce = (int) (Math.random() * 900) + 100;
            String sign_string = SignatureAndVerifyUtil.SECRETID + "," + timestamp + "," + nonce;

            PrivateKey privateKey = SignatureAndVerifyUtil.readPrivateKey();
            String signature = SignatureAndVerifyUtil.Signature(privateKey, sign_string);

            Map map = new HashMap();
            map.put("timestamp", timestamp);
            map.put("nonce", nonce);
            map.put("signature", signature);

            if (StringUtils.isNotBlank(bfile)) {
                byte[] buffer;
                if (bfile.contains("%")) { //urlencode?????????
                    buffer = Base64Util.decodeImg(URLDecoder.decode(bfile, "utf8"));
                } else {
                    buffer = Base64Util.decodeImg(bfile);
                }
                map.put("image", new TuPuModel[]{new TuPuModel(new ByteArrayInputStream(buffer), fileName)});
                String tuPuResult = HttpUtils.upload(SignatureAndVerifyUtil.TUPUURL, map, "image", false, 2000, 2000);

                if (null != tuPuResult && resultTuPu(result, tuPuResult)) {
                    return result;
                }

                imgPath = fileUploadManager.upload(buffer, fileName);
            } else {
                if (file.getSize() > 500000) {
                    result.setCode(-3);
                    result.setMsg("??????????????????500k,???????????????");
                    return result;
                }
                if (!file.getOriginalFilename().toLowerCase().endsWith(".jpg") &&
                        !file.getOriginalFilename().toLowerCase().endsWith(".gif") &&
                        !file.getOriginalFilename().toLowerCase().endsWith(".png")) {
                    result.setCode(-4);
                    result.setMsg("??????????????????,?????????jpg???gif???png????????????!");
                    return result;
                }

                map.put("image", new MultipartFile[]{file});
                String tuPuResult = HttpUtils.upload(SignatureAndVerifyUtil.TUPUURL, map, "image", true, 2000, 2000);

                if (null != tuPuResult && resultTuPu(result, tuPuResult)) {
                    return result;
                }
                imgPath = fileUploadManager.upload(file);
            }
            UserBaseInfoENT userBaseInfo = userInfoService.getCurrentUserBaseInfo(userId);
            userBaseInfo.setFaceUrl(PropertiesUtil.get("fastdfs.access.url") + imgPath);
            userBaseInfo.setUpdateTime(new Date());
            UserfaceDTO userfaceDTO = new UserfaceDTO();
            userfaceDTO.setUserface(PropertiesUtil.get("fastdfs.access.url") + imgPath);
            result.setData(userfaceDTO);
            userBaseInfoService.updateUserBaseInfo(userBaseInfo);
        } catch (Exception e) {
            result.setMsg(Result.MSG_ERROR_DESC);
            result.setCode(Result.ERROR);
            logger.error("????????????????????????,userId:{} traceId:{},error:{}", userId, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    private boolean resultTuPu(Result result, String tuPuResult) {
        JSONObject jsonDetail = JSON.parseObject(JSON.parseObject(tuPuResult).getString("json"));
        if (!jsonDetail.getString("code").equals("0")) {
            result.setCode(-5);
            result.setMsg(SignatureAndVerifyUtil.TUPUEREQUESTRROR);
            return true;
        }
        JSONObject fileDetail = jsonDetail.getJSONObject(SignatureAndVerifyUtil.TASKID).getJSONArray("fileList").getJSONObject(0);
        String label = fileDetail.getString("label");
        double rate = fileDetail.getDoubleValue("rate");
        if (!label.equals("0")) {
            result.setCode(-6);
            if (label.equals("1") && rate > 0) {
                result.setMsg(SignatureAndVerifyUtil.TUPUQRCODE);
            }
            if ((label.equals("2") || label.equals("3")) && rate > 0) {
                result.setMsg(SignatureAndVerifyUtil.TUPUCHARACTER);
            }

            return true;
        }
        return false;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/24 13:50
     * @Description 1019 wap??????????????????????????????
     */
    @Override
    public Result checkWapVerifyCode(String traceId, ClientRequestHeader header, String params) {
        Result result = new Result();
        CheckWapVerifyParams checkWapVerifyParams = GfJsonUtil.parseObject(params, CheckWapVerifyParams.class);
        if (checkWapVerifyParams == null || StringUtils.isBlank(checkWapVerifyParams.getMobile()) || StringUtils.isBlank(checkWapVerifyParams.getVerifyCode())) {
            return getFailResult();
        }
        String mobile = checkWapVerifyParams.getMobile();
        String verifyCode = checkWapVerifyParams.getVerifyCode();
        if (!MobileUtil.isMobileNO(mobile)) {
            result.setMsg("???????????????????????????");
            return result;
        }
        return verifyCodeCheckService.checkWapVerifyCode(verifyCode, mobile, traceId, header);
    }

    @Override
    public Result getResetIdCardInfo(String traceId, String userId) {
        Result result = new Result();
        if (StringUtils.isBlank(userId)) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????");
            return result;
        }
        UserIdcardResetENT ent = userInfoService.getResetIdCardInfo(traceId, userId);
        UserIdCardResetInfoDTO ud = new UserIdCardResetInfoDTO();
        if (ent != null) {
            ud.setIdcardNumber(ent.getIdcardNumber());
            ud.setStatus(ent.getStatus());
            ud.setRealName(ent.getRealName());
        } else {
            ud.setStatus(-1);
        }
        result.setData(ud);
        return result;
    }

    @Override
    public Result resetIdCardNumber(String traceId, String userId, String params, MultipartFile[] file, String[] bfile, String[] fileName) {
        Result result = new Result();
        UserIdCardParams userIdCardParams = GfJsonUtil.parseObject(params, UserIdCardParams.class);
        String idCardNumber = userIdCardParams.getIdcardnumber();
        String name = userIdCardParams.getName();
        if (StringUtils.isBlank(userId)) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????");
            return result;
        }
        if (StringUtils.isBlank(idCardNumber)) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????");
            return result;
        }
        //?????????????????????
        if (!RegexUtils.isIDCard18(idCardNumber.toUpperCase())) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????????????????");
            return result;
        }
        //??????18?????????????????????
        String birthDay = idCardNumber.substring(6, 14);
        Date birthDate = DateUtil.parseSampleDate(birthDay);
        Calendar c = Calendar.getInstance();
        c.setTime(birthDate);
        c.add(Calendar.YEAR, 18);
        if (c.getTime().after(new Date())) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????18?????????????????????");
            return result;
        }
        if (StringUtils.isBlank(name)) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????");
            return result;
        }
        if (name.length() > 40) {
            result.setCode(Result.ERROR);
            result.setMsg("????????????????????????");
            return result;
        }
        name = name.trim();
        UserBaseInfoENT userBaseInfoENT = userInfoService.getCurrentUserBaseInfo(userId);
        if (userBaseInfoENT == null) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????");
            return result;
        }
        if (idCardNumber.equals(userBaseInfoENT.getIdcardNumber()) &&
                name.equals(userBaseInfoENT.getRealName())) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????????????????????????????????????????????????????????????????");
            return result;
        }
        try {
            if (bfile != null) {
                if (bfile.length < 2) {
                    result.setCode(Result.ERROR);
                    result.setMsg("??????????????????????????????????????????");
                    return result;
                }
                String[] idcardUrl = new String[bfile.length];
                for (int i = 0; i < bfile.length; i++) {
                    byte[] buffer = new BASE64Decoder().decodeBuffer(bfile[i]);
                    idcardUrl[i] = fileUploadManager.upload(buffer, fileName[i]);
                }
                //???????????????
                result = userInfoService.resetIdCardNumber(userId, idCardNumber,
                        name, idcardUrl[0], idcardUrl[1], traceId);
            } else {
                //???????????????????????????
                if (ArrayUtils.isEmpty(file) || file.length < 2) {
                    result.setCode(Result.ERROR);
                    result.setMsg("??????????????????????????????????????????");
                    return result;
                }
                String[] idcardUrl = new String[file.length];
                for (int i = 0; i < file.length; i++) {
                    idcardUrl[i] = fileUploadManager.upload(file[i]);
                }
                //???????????????
                result = userInfoService.resetIdCardNumber(userId, idCardNumber,
                        name, idcardUrl[0], idcardUrl[1], traceId);
            }
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????????????????");
            logger.error("?????????????????????,userId:{} traceId:{},error:{}", userId, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    @Override
    public Result getUserAttentionList(String traceId, String userId, String params) {
        Result result = new Result();
        FansParams fansParams = GfJsonUtil.parseObject(params, FansParams.class);
        if (fansParams == null) {
            return getFailResult();
        }
        result.setData(userFansService.getUserAttentionList(fansParams.getUserid(), Long.valueOf(userId), fansParams.getPageno(), fansParams.getPagesize(), traceId));
        return result;
    }

    @Override
    public Result getUserFansList(String traceId, String userId, String params) {
        FansParams fansParams = GfJsonUtil.parseObject(params, FansParams.class);
        if (fansParams == null) {
            return getFailResult();
        }
        Result result = new Result();
        Long uid = null;
        if (StringUtils.isNotBlank(userId)) {
            uid = Long.valueOf(userId);
        }
        result.setData(userFansService.getUserFansList(fansParams.getUserid(), uid, fansParams.getPageno(), fansParams.getPagesize(), traceId));
        return result;
    }

    @Override
    public Result setUserFansRelation(String traceId, String userId, String params) {
        AttentionOperationParams attentionOperationParams = GfJsonUtil.parseObject(params, AttentionOperationParams.class);
        if (attentionOperationParams == null) {
            return getFailResult();
        }
        return userFansService.setUserFansRelation(Long.valueOf(userId), attentionOperationParams.getAttentionuserid(), attentionOperationParams.getType(), traceId);
    }

    @Override
    public Result userLogout(String traceId, String userId, String token) {
        return userInfoService.userLoginOut(userId, token, traceId);
    }

    @Override
    public Result checkLoginByMobile(String traceId, String params) {
        Result result = new Result();
        result.setCode(Result.ERROR);
        CheckVerifyCodeParams cp = GfJsonUtil.parseObject(params, CheckVerifyCodeParams.class);
        if (cp == null) {
            return getFailResult();
        }
        if (StringUtils.isBlank(cp.getMobile())) {
            result.setMsg("????????????????????????");
            return result;
        }
        if (!MobileUtil.isMobileNO(cp.getMobile())) {
            result.setMsg("???????????????????????????");
            return result;
        }
        if (StringUtils.isBlank(cp.getVerifyCode())) {
            result.setMsg("?????????????????????");
            return result;
        }
        return verifyCodeCheckService.checkLoginByMobile(cp.getVerifyCode(), cp.getMobile());
    }

    @Override
    public Result searchMasterInfoByNickName(String traceId, String userId, String params) {
        SearchMasterParam searchMasterParam = GfJsonUtil.parseObject(params, SearchMasterParam.class);
        if (searchMasterParam == null) {
            return getFailResult();
        }
        Result result = new Result();
        if (StringUtils.isEmpty(searchMasterParam.getNikeName())) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????");
            return result;
        }
        if (!Pattern.matches("[\u4E00-\u9FA5]{1,}", searchMasterParam.getNikeName())) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????");
            return result;
        }
        return userMasterInfoService.searchMasterByNickName(searchMasterParam.getNikeName(), searchMasterParam.getPageNum(), searchMasterParam.getPageSize());
    }

    public Result getUserBaseInfo(String traceId, String params, String isGetUserLevel, String isGetUserInteral, String isGetUserSignInfo) {
        Long[] userIds = new Long[]{};
        Result result = new Result();
        UserLevelParams userLevelParams = GfJsonUtil.parseObject(params, UserLevelParams.class);
        if (StringUtils.isBlank(isGetUserLevel)) {
            isGetUserLevel = "1";
        }
        if (StringUtils.isBlank(isGetUserInteral)) {
            isGetUserInteral = "1";
        }
        if (StringUtils.isBlank(isGetUserSignInfo)) {
            isGetUserSignInfo = "1";
        }
        if (userLevelParams == null) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.warn("?????????????????????????????????userIds????????????!traceId={},params={}", traceId, params);
            return result;
        }
        try {
            userIds = userLevelParams.getUserIds();
            result.setCode(Result.SUCCESS);
            result.setData(userBaseInfoService.listUserBaseInfo(traceId, userIds,
                    "1".equals(isGetUserLevel) ? true : false,
                    "1".equals(isGetUserInteral) ? true : false,
                    "1".equals(isGetUserSignInfo) ? true : false));
            result.setMsg(Result.MSG_SUCCESS_DESC);
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("?????????????????????????????????traceId={};userIds={};StackTrace: {}", traceId, userIds, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    @Override
    public Result thdPartChangeUserPassword(String traceId, String userID, String params) {
        NewpwdParams newpwdParams = GfJsonUtil.parseObject(params, NewpwdParams.class);
        if (newpwdParams == null) {
            return getFailResult();
        }
        String newpwd = newpwdParams.getNewpwd();
        return userInfoService.thdPartChangeUserPassword(userID, newpwd, traceId);
    }

    /**
     * @Author shenwei
     * @Date 2017/3/27 11:36
     * @Description 1606 ?????????????????????????????????????????????
     */
    @Override
    public Result getThdPartBindUserMobileVerifyCode(String traceId, String userID, String params) {
        ThirdPartBindMobileParams thirdPartBindMobileParams = GfJsonUtil.parseObject(params, ThirdPartBindMobileParams.class);
        Result result = new Result();
        if (thirdPartBindMobileParams == null) {
            return getFailResult();
        }
        String mobile = thirdPartBindMobileParams.getMobile();
        if (!MobileUtil.isMobileNO(mobile)) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????");
            return result;
        }
        Integer type = thirdPartBindMobileParams.getType();
        if (!type.equals(UserContext.USER_REGISTER_TYPE_THIRD_MOBILE_BIND)) {
            result.setCode(Result.ERROR);
            result.setMsg("?????????????????????");
            return result;
        }
        UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userID);
        if (userInfoENT == null || StringUtils.isBlank(userInfoENT.getThdPartType())) {
            result.setCode(Result.ERROR);
            result.setMsg("???????????????????????????????????????????????????????????????");
            return result;
        }
        return verifyCodeService.getVerifyCode(userID, mobile, VerifyCodeEnum.THIRDBINDMOBILE, traceId, null);
    }

    /**
     * 1022 ??????????????????
     *
     * @param traceId
     * @param userId
     * @param params
     * @return
     */
    @Override
    public Result editNickName(String traceId, String userId, String params) {
        UserNicknameParam nicknameParam = GfJsonUtil.parseObject(params, UserNicknameParam.class);
        if (nicknameParam == null) {
            return getFailResult();
        }
        return userInfoService.editNickName(userId, nicknameParam.getNickname(), traceId);
    }

    @Override
    public Result changeUserPayPassword(String traceId, String userID, String params) {
        PayPassParams payPassParams = GfJsonUtil.parseObject(params, PayPassParams.class);
        if (payPassParams == null) {
            return getFailResult();
        }
        return userInfoService.changeUserPayPassword(userID, payPassParams.getNewPw(), payPassParams.getVerifyCode(), traceId);
    }

    @Override
    public Result bindUserPayPassword(String traceId, String userId, String params) {
        Result result = new Result();
        PayPassParams payPassParams = GfJsonUtil.parseObject(params, PayPassParams.class);
        if (payPassParams == null) {
            return getFailResult();
        }
        if (StringUtils.isBlank(payPassParams.getNewPw())) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????");
            return result;
        }
        if (payPassParams.getNewPw().length() < 6 || payPassParams.getNewPw().length() > 15) {
            result.setCode(Result.ERROR);
            result.setMsg("??????????????????6-15?????????");
            return result;
        }
        return userInfoService.bindUserPayPassword(traceId, "", payPassParams.getNewPw(), userId);
    }

    /**
     * @Author shenwei
     * @Date 2017/5/10 14:42
     * @Description 107????????????
     */
    @Override
    public Result getBasicUserInfo(String traceId, ClientRequestHeader clientRequestHeader, String params, String userId) {
        return userBaseInfoService.getBasicUserInfo(traceId, clientRequestHeader, userId, params);
    }


    private Result getFailResult() {
        Result result = new Result();
        result.setCode(Result.ERROR);
        result.setMsg(Result.MSG_ERROR_DESC);
        return result;
    }

}
