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
     * @Description ??????????????????????????????
     */
    public Result getUserIntegralHomePageInfo(String traceId, String userID) {
        Result result = new Result();
        Integer firstVisitMark = 0;// 1????????????  0???????????????
        try {
            if (!StringUtils.isNotBlank(userID)) {
                result.setCode(Result.NOTLOGIN);
                result.setMsg(Result.MSG_NOTLOGIN_DESC);
                return result;
            }
            Long longuserID = Long.valueOf(userID);
            //????????????????????????????????????????????????????????????????????????????????????????????????
            if (publicCommonRedisUtil.isInEnterIntegralSet(longuserID)) {
                firstVisitMark = 0;
            } else {
                firstVisitMark = 1;
            }
            try {
                userIntegralCoreService.enterIntegralCenter(traceId, longuserID);
            } catch (Exception e) {
                logger.error("??????????????????????????????????????? ?????????traceId={};userID={};StackTrace: {}???", traceId, userID, LogExceptionStackTrace.erroStackTrace(e));
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
            logger.error("?????????????????????????????? ?????????traceId={};userID={};StackTrace: {}???", traceId, userID, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]???????????????{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @param userID
     * @return
     * @author zhudonghai
     * @date 2016-12-15
     * @Description ??????????????????????????????
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
            logger.error("???????????????????????? ?????????traceId={};userID={};StackTrace: {}???", traceId, userID, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]???????????????{}", traceId, GfJsonUtil.toJSONString(result));
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
            logger.error("??????????????????????????????????????????traceId={};params={};StackTrace: {}???", traceId, params, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/14 17:54
     * @Description ????????????
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
                logger.error("????????????id??????");
                return result;
            }
            Long id = Long.valueOf(userID);
            result = userSignInService.SignIn(traceId, id, model);
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("??????????????????traceId={};userID={};params={};StackTrace: {}???", traceId, userID, params, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/14 20:04
     * @Description ??????????????????
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
                result.setMsg("????????????!");
            } else {
                result.setCode(Result.ERROR);
                result.setMsg("????????????????????????????????????");
            }
        } catch (Exception e) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("??????????????????????????????????????????traceId={};userID={};params={};StackTrace: {}???", traceId, userID, params, LogExceptionStackTrace.erroStackTrace(e));
            return result;
        }
        logger.info("traceId=[{}]???????????????{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @param userID
     * @return
     * @author zhudonghai
     * @date 2016-12-16
     * @Description ??????????????????????????????
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
            logger.error("?????????????????????????????? ?????????traceId={};userID={};StackTrace: {}???", traceId, userID, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]???????????????{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/6 14:16
     * @Description ?????????????????? ????????? ?????????????????????
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
            logger.error("????????????????????????????????????userID={};TraceId={};StackTrace: {}???", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]???????????????{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/4 18:37
     * @Description ???????????????????????????
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
            //??????pagesize??????
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
            logger.error("??????????????????????????????userID={};TraceId:{};StackTrace: {}???", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]???????????????{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/6 14:30
     * @Description ??????????????????????????????
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
            logger.error("?????????????????????????????? ???userID={};TraceId:{};StackTrace: {}???", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]???????????????{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/18 16:44
     * @Description ??????????????????
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
            logger.error("???????????????????????? ???userID={};TraceId:{};StackTrace:{}???", userID, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.info("traceId=[{}]???????????????{}", traceId, GfJsonUtil.toJSONString(result));
        return result;
    }

}
