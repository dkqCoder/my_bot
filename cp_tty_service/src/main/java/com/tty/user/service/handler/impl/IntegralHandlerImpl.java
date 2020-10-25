package com.tty.user.service.handler.impl;

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.common.utils.Result;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.context.UserContext;
import com.tty.user.controller.model.params.MissionParams;
import com.tty.user.controller.model.params.PageParams;
import com.tty.user.controller.model.params.UserIntegralRecordParams;
import com.tty.user.controller.model.params.UserSignInParam;
import com.tty.user.controller.model.result.MissionModel;
import com.tty.user.model.params.GiftParam;
import com.tty.user.model.result.UserGrowupRecordModel;
import com.tty.user.model.result.UserIntegralHomePageResult;
import com.tty.user.service.UserGrowupService;
import com.tty.user.service.UserIntegralRecordService;
import com.tty.user.service.UserIntegralCoreService;
import com.tty.user.service.UserSignInService;
import com.tty.user.service.handler.IntegralHandler;
import com.tty.user.service.mission.UserIntegralCommService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
/**
 * @author ln
 */
@Component("integralHandler")
public class IntegralHandlerImpl implements IntegralHandler {
    private static final Logger logger = LoggerFactory.getLogger(IntegralHandler.class);
    @Autowired
    private UserIntegralRecordService userIntegralRecordService;
    @Autowired
    private UserSignInService userSignInService;
    @Autowired
    private UserIntegralCommService userIntegralCommService;
    @Autowired
    private UserIntegralCoreService userIntegralCoreService;
    @Autowired
    private UserGrowupService userGrowupService;
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;

    /**
     * @param userID
     * @return
     * @author zhudonghai
     * @date 2016-12-14
     * @Description 获取我的积分首页信息
     */
    public Result getUserIntegralHomePageInfo(String traceId, String userID) {
        Result result = new Result();
        Integer firstVisitMark = 0;// 1首次进入  0非首次进入
        try {
            if (!StringUtils.isNotBlank(userID)) {
                result.setCode(Result.NOTLOGIN);
                result.setMsg(Result.MSG_NOTLOGIN_DESC);
                return result;
            }
            Long longuserID = Long.valueOf(userID);
            //注意：顺序不能乱，先读缓存判断是否首次进入积分页，再去是否加积分
            if (publicCommonRedisUtil.isInEnterIntegralSet(longuserID)) {
                firstVisitMark = 0;
            } else {
                firstVisitMark = 1;
            }
            try {
                userIntegralCoreService.enterIntegralCenter(traceId, longuserID);
            } catch (Exception e) {
                logger.error("上报首次进入我的积分页任务 失败【traceId={};userID={};StackTrace: {}】", traceId, userID, LogExceptionStackTrace.erroStackTrace(e));
            }
            UserIntegralHomePageResult userInte = userIntegralCommService.getUserHomePageInfo(longuserID);
            userInte.setIsFirstVisit(firstVisitMark);
            if (userInte != null) {
                result.setCode(Result.SUCCESS);
                result.setMsg(Result.MSG_SUCCESS_DESC);
                result.setData(userInte);
            } else {
                result.setCode(Result.ERROR);
                result.setMsg(Result.MSG_ERROR_DESC);
            }
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("获取我的积分首页信息 失败【traceId={};userID={};StackTrace: {}】", traceId, userID, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]；返回值：{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @param userID
     * @return
     * @author zhudonghai
     * @date 2016-12-15
     * @Description 获取用户任务中心信息
     */
    public Result getMissionsCenterInfo(String traceId, String userID) {
        Result result = new Result();
        try {
            if (!StringUtils.isNotBlank(userID)) {
                result.setCode(Result.NOTLOGIN);
                result.setMsg(Result.MSG_NOTLOGIN_DESC);
                return result;
            }
            Long longUserID = Long.valueOf(userID);
            HashMap map = userIntegralCommService.getMissionsCenterInfo(longUserID);
            if (map != null) {
                result.setCode(Result.SUCCESS);
                result.setMsg(Result.MSG_SUCCESS_DESC);
                result.setData(map);
            }
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("获取任务中心信息 失败【traceId={};userID={};StackTrace: {}】", traceId, userID, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]；返回值：{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }


    /**
     * @Author shenwei
     * @Date 2016/12/14 16:47
     * @Description
     */
    public Result getUserIntegralRecords(String traceId, String userID, String params) {
        Result result = new Result();
        try {
            if (!StringUtils.isNotBlank(userID)) {
                result.setCode(Result.NOTLOGIN);
                result.setMsg(Result.MSG_NOTLOGIN_DESC);
                return result;
            }
            UserIntegralRecordParams model = GfJsonUtil.parseObject(params, UserIntegralRecordParams.class);
            if (model == null) {
                result.setCode(Result.ERROR);
                result.setMsg(Result.MSG_ERROR_DESC);
                return result;
            }
            Long id = Long.valueOf(userID);
            if (model.getPageSize() >= UserContext.PAGESIZE_MAX) {
                model.setPageSize(UserContext.PAGESIZE_MAX);
            }
            result = userIntegralRecordService.getUserInteralRecords(id, model.getPageIndex(), model.getPageSize(), model.getLastId());

        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("获取用户积分收支明细失败！【traceId={};params={};StackTrace: {}】", traceId, params, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/14 17:54
     * @Description 用户签到
     */
    public Result userSignIn(String traceId, String userID, String params) {
        Result result = new Result();
        try {
            if (!StringUtils.isNotBlank(userID)) {
                result.setCode(Result.NOTLOGIN);
                result.setMsg(Result.MSG_NOTLOGIN_DESC);
                return result;
            }
            UserSignInParam model = GfJsonUtil.parseObject(params, UserSignInParam.class);
            if (model == null) {
                result.setCode(Result.ERROR);
                result.setMsg(Result.MSG_ERROR_DESC);
                logger.error("签到用户id为空");
                return result;
            }
            Long id = Long.valueOf(userID);
            result = userSignInService.SignIn(traceId, id, model);
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("签到失败！【traceId={};userID={};params={};StackTrace: {}】", traceId, userID, params, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/14 20:04
     * @Description 用户领取积分
     */
    public Result userGetMissionReward(String traceId, String userID, String params) {
        Result result = new Result();
        try {
            if (!StringUtils.isNotBlank(userID)) {
                result.setCode(Result.NOTLOGIN);
                result.setMsg(Result.MSG_LOGIN_DESC);
                return result;
            }
            MissionParams model = GfJsonUtil.parseObject(params, MissionParams.class);
            if (model == null) {
                result.setCode(Result.ERROR);
                result.setMsg(Result.MSG_ERROR_DESC);
                return result;
            }
            Long id = Long.valueOf(userID);
            Long missionId = model.getMisId();
            MissionModel data = userIntegralCoreService.getMissionModel(id, missionId);
            if (userIntegralCoreService.userGetIntegral(traceId, id, data.getIntegralsToAdd(), 1, missionId, data.getMissName())) {
                result.setCode(Result.SUCCESS);
                result.setData(userIntegralCoreService.getMissionModel(id, missionId));
                result.setMsg("领取成功!");
            } else {
                result.setCode(Result.ERROR);
                result.setMsg("用户领取任务积分奖励失败");
            }
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("用户领取任务积分奖励失败！【traceId={};userID={};params={};StackTrace: {}】", traceId, userID, params, LogExceptionStackTrace.erroStackTrace(e));
            return result;
        }
        logger.info("traceId=[{}]；返回值：{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @param userID
     * @return
     * @author zhudonghai
     * @date 2016-12-16
     * @Description 获取用户积分余额信息
     */
    public Result getUserIntegralBalance(String traceId, String userID) {
        Result result = new Result();
        if (!StringUtils.isNotBlank(userID)) {
            result.setCode(Result.NOTLOGIN);
            result.setMsg(Result.MSG_NOTLOGIN_DESC);
            return result;
        }
        try {
            Long longuserID = Long.valueOf(userID);
            HashMap user = userIntegralCommService.getUserIntegralBalance(longuserID);
            if (user != null) {
                result.setCode(Result.SUCCESS);
                result.setMsg(Result.MSG_SUCCESS_DESC);
                result.setData(user);
            } else {
                result.setCode(Result.ERROR);
                result.setMsg(Result.MSG_ERROR_DESC);
            }
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("获取用户积分余额信息 失败【traceId={};userID={};StackTrace: {}】", traceId, userID, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]；返回值：{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/6 14:16
     * @Description 获取用户等级 成长值 击败用户比例等
     */
    public Result getUserLevelGrowups(String traceId, String userID) {
        Result result = new Result();
        if (StringUtils.isBlank(userID)) {
            result.setCode(Result.NOTLOGIN);
            result.setMsg(Result.MSG_NOTLOGIN_DESC);
            return result;
        }
        try {
            Long longUserID = Long.valueOf(userID);
            result.setCode(Result.SUCCESS);
            result.setData(userGrowupService.getUserLevelAndGrowups(traceId, longUserID));
            result.setMsg(Result.MSG_SUCCESS_DESC);
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("获取用户等级成长值失败【userID={};TraceId={};StackTrace: {}】", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]；返回值：{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/4 18:37
     * @Description 获取用户成长值明细
     */
    public Result getUserGrowupsRecord(String traceId, String userID, String params) {
        Result result = new Result();
        if (StringUtils.isBlank(userID)) {
            result.setCode(Result.NOTLOGIN);
            result.setMsg(Result.MSG_NOTLOGIN_DESC);
            return result;
        }
        try {
            Long longUserID = Long.valueOf(userID);
            PageParams model = GfJsonUtil.parseObject(params, PageParams.class);
            if (model == null) {
                result.setCode(Result.ERROR);
                result.setMsg(Result.MSG_ERROR_DESC);
                return result;
            }
            //限制pagesize大小
            if (model.getPageSize() >= UserContext.PAGESIZE_MAX) {
                model.setPageSize(UserContext.PAGESIZE_MAX);
            }
            List<UserGrowupRecordModel> records = userGrowupService.getUserGrowupRecords(traceId, longUserID, model.getPageIndex(), model.getPageSize());
            result.setCode(Result.SUCCESS);
            result.setData(records);
            result.setMsg(Result.MSG_SUCCESS_DESC);
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("获取用户成长值失败【userID={};TraceId:{};StackTrace: {}】", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]；返回值：{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/6 14:30
     * @Description 获取用户会员中心首页
     */
    public Result getUserLevelCenter(String traceId, String userID) {
        Result result = new Result();
        if (StringUtils.isBlank(userID)) {
            result.setCode(Result.NOTLOGIN);
            result.setMsg(Result.MSG_NOTLOGIN_DESC);
            return result;
        }
        try {
            Long longUserID = Long.valueOf(userID);
            result.setCode(Result.SUCCESS);
            result.setData(userGrowupService.getUserLevelCenter(traceId, longUserID));
            result.setMsg(Result.MSG_SUCCESS_DESC);
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("获取用户会员中心失败 【userID={};TraceId:{};StackTrace: {}】", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]；返回值：{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/18 16:44
     * @Description 领取升级礼包
     */
    public Result getLevelupGift(String traceId, String userID, String params) {
        Result result = new Result();
        if (StringUtils.isBlank(userID)) {
            result.setCode(Result.NOTLOGIN);
            result.setMsg(Result.MSG_NOTLOGIN_DESC);
            return result;
        }
        GiftParam model = GfJsonUtil.parseObject(params, GiftParam.class);
        if (model == null) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            return result;
        }
        try {
            Long longUserID = Long.valueOf(userID);
            if (userGrowupService.getLevelupGift(traceId, model.getGiftId(), longUserID)) {
                result.setCode(Result.SUCCESS);
                result.setMsg(Result.MSG_SUCCESS);
            } else {
                result.setCode(Result.ERROR);
                result.setMsg(Result.MSG_FAILED);
            }
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("用户领取礼包失败 【userID={};TraceId:{};StackTrace:{}】", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]；返回值：{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

}
