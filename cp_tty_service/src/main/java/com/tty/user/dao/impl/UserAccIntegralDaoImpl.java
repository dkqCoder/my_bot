package com.tty.user.dao.impl;

import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.user.common.utils.DateUtils;
import com.tty.user.common.utils.ListSorter;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.context.MissionTypeEnum;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserInfoExtensionFields;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.model.result.UserIntegralMissionModel;
import com.tty.user.model.result.UserIntegralMissionResult;
import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;
import com.jdd.fm.core.db.BaseDao;
import com.tty.user.dao.pojo.UserAccIntegral;
import com.tty.user.dao.UserAccIntegralDao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenlongfei
 */
@Repository("userAccIntegralDao")
public class UserAccIntegralDaoImpl extends BaseDao<UserAccIntegral> implements UserAccIntegralDao {
    private static final Logger logger = LoggerFactory.getLogger(UserAccIntegralDaoImpl.class);
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;
    @Autowired
    private ListSorter listSorter;
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;


    public List<UserAccIntegral> listUserAccIntegral() {
        return getQuery("select a from UserAccIntegral a").setFirstResult(20).setMaxResults(10).list();
    }

    public List<Map> listUserAccIntegralSql(String userid) {
        return getSQLQuery("select n_user_id from  user_acc_integral where n_user_id=:userid").setString("userid", userid).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    public void saveUserAccIntegral(UserAccIntegral ent) {
        save(ent);
    }

    public void updateUserAccIntegral(UserAccIntegral ent) {
        update(ent);
    }

    public void deleteUserAccIntegral(UserAccIntegral ent) {
        delete(ent);
    }

    public void saveOrUpdateUserAccIntegral(UserAccIntegral ent) {
        saveOrUpdate(ent);
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public Double getUserBeatInfo(Long userId) {
        try {
            Double beat = 0.1;
            //计算总人数
            String totalCount = Optional.ofNullable(userRedis.get(UserRedisKeys.USER_COUNT_TODAY)).orElseGet(() -> {
                String sql1 = "select COUNT(1) as count from user_acc_integral";
                String result = getSQLQuery(sql1).uniqueResult().toString();
                userRedis.set(UserRedisKeys.USER_COUNT_TODAY, result);
                userRedis.expire(UserRedisKeys.USER_COUNT_TODAY, UserRedisKeys.DAY);
                return result;
            });
            //计算积分比他少的人数
            Integer userIntegral = Optional.ofNullable(publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_INTEGRAL_BALANCE_FIELD)).map(x -> Integer.valueOf(x)).orElse(0);
            String sql = "select COUNT(1) as count from user_acc_integral WHERE n_user_integral < " + userIntegral;
            Integer beatCount = Integer.valueOf(getSQLQuery(sql).uniqueResult().toString());
            //击败比例
            beat = new BigDecimal(100 * beatCount / Integer.valueOf(totalCount)).setScale(1).doubleValue();
            if (beat != null && beat.intValue() == 100) {
                beat = 99.9;
            }
            if (beat == null || beat.intValue() == 0) {
                beat = 0.1;
            }
            return beat;
        } catch (Exception e) {
            return 0.1;
        }
    }

    //该方法其他地方用到，不用写缓存
    public Long getUserIntegralBalance(Long userId) {
        Long balance = new Long(0);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT IFNULL((SELECT a.n_user_integral FROM user_acc_integral a WHERE a.n_user_id=:userid),0) AS integral;");
        try {
            balance = Long.parseLong(getSQLQuery(sql.toString()).
                    setLong("userid", userId).uniqueResult().toString());
        } catch (Exception e) {
            logger.error("查询用户积分余额异常 【UserId={};StackTrace: {}】", userId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return balance;
    }


    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    public Boolean updateUserIntegral(String traceId, Long userId, Long integral) {
        Boolean bool = new Boolean(false);
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE user_acc_integral SET n_user_integral=(:integral + n_user_integral) WHERE n_user_id=:userid");
        Integer result = getSQLQuery(sql.toString()).setLong("integral", integral).
                setLong("userid", userId).executeUpdate();
        if (result != null && result > 0) {
            bool = true;
        } else if (result != null && result.equals(0)) {
            sql = new StringBuilder();
            sql.append("INSERT INTO user_acc_integral(n_user_id, n_user_integral) VALUES (:userid, :integral)");
            result = getSQLQuery(sql.toString()).setLong("integral", integral).
                    setLong("userid", userId).executeUpdate();
            if (result > 0) {
                bool = true;
            } else {
                logger.error("用户积分变更失败：traceId:{},userId:{}, integral:{}", traceId, userId, integral);
            }
        } else {
            logger.error("用户积分变更失败：traceId:{},userId:{}, integral:{}", traceId, userId, integral);
        }
        return bool;
    }


    /**
     * @param userId
     * @param priority 优先级(传0显示所有任务)
     * @return
     * @author zhudonghai
     * @Date 2016-12-21
     * @Description 获取用户任务列表
     */
    public List<UserIntegralMissionResult> getMissionsInfo(Long userId, Integer priority) {
        List<UserIntegralMissionResult> misList = null;
        //先读缓存
        misList = publicCommonRedisUtil.getUserMissionInfo(userId);
        //缓存没有读库
        if (misList == null || misList.size() < 1) {
            misList = getMissionsBaseInfo(userId);
        }
        List<UserIntegralMissionResult> newMisList = null;
        if (priority.equals(0)) {
            return misList;
        } else if (priority.equals(1)) {
            //我的积分页推荐任务传1，显示优先级为1的任务,如果不足5条显示优先级为2的
            if (misList != null && misList.size() > 0) {
                newMisList = new ArrayList<UserIntegralMissionResult>();
                for (UserIntegralMissionResult mis : misList) {
                    newMisList.add(mis);
                    //sql已排序，只需取前5条
                    if (newMisList.size() == 5) {
                        break;
                    }
                }
            }
            return newMisList;
        } else {
            if (misList != null && misList.size() > 0) {
                newMisList = new ArrayList<UserIntegralMissionResult>();
                for (UserIntegralMissionResult mis : misList) {
                    try {
                        if (mis.getPriority() != null && mis.getPriority().equals(priority)) {
                            newMisList.add(mis);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return newMisList;
        }
    }

    public List<UserIntegralMissionResult> getMissionsBaseInfo(Long userId) {
        List<UserIntegralMissionResult> newMisList = new ArrayList<UserIntegralMissionResult>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT IFNULL(c.n_id,0) AS endMisId,a.n_mission_id AS misId,a.s_mission_name AS misName,a.n_mission_priority AS priority,");
        sql.append("a.s_remark AS misDis,IFNULL(c.n_is_add, 0) AS isGet,0 buttonStatus,'' buttonText,n_mission_counts AS missCounts,a.n_mission_times AS misPeriod,");
        sql.append("IFNULL(c.n_is_end, 0) AS isEnd,	a.n_mission_category AS misCategory,c.d_create_date AS createDate,");
        sql.append("IFNULL(a.n_mission_integral, 0) AS integralGet,a.n_mission_type AS misType,IFNULL(c.n_count,0) AS count, ");
        sql.append("DATE_SUB(CURDATE(),INTERVAL WEEKDAY(CURDATE()) DAY) AS firstDayWeek,");
        sql.append("DATE_SUB(CURDATE(),INTERVAL WEEKDAY(CURDATE()) - 6 DAY) AS lastDayWeek ");
        sql.append("FROM mis_user_mission a ");
        sql.append("LEFT JOIN mis_user_end_mission c ON a.n_mission_id = c.n_mission_id AND c.n_userid =:userid ");
        //每日任务，不等于当天则不显示
        sql.append("AND NOT EXISTS (  ");
        sql.append("SELECT b.n_mission_id FROM mis_user_end_mission b WHERE b.n_userid = :userid ");
        sql.append("AND a.n_mission_id = b.n_mission_id AND CURDATE() <> DATE(c.d_create_date) AND a.n_mission_times =1 ) ");
        //每周任务，当周显示，下一周重新开始
        sql.append("AND NOT EXISTS ( ");
        sql.append("SELECT b.n_mission_id FROM mis_user_end_mission b ");
        sql.append("WHERE b.n_userid = :userid AND a.n_mission_id = b.n_mission_id ");
        sql.append("AND c.d_create_date < date_sub(curdate(),INTERVAL WEEKDAY(curdate()) DAY) AND a.n_mission_times = 7) ");
        sql.append("WHERE a.n_mission_category = 2 ");
        //签到不显示在任务列表中
        sql.append("AND a.n_mission_id <>7 ");
        sql.append("ORDER BY CASE WHEN isGet = 0 AND isEnd = 1 THEN 0 WHEN isGet=0 AND isEnd =0 THEN 1 WHEN isGet=1 AND (isEnd = 1 OR isEnd=3) THEN 2 END ASC,a.n_mission_times ASC,a.n_mission_priority ASC ");
        try {
            List<UserIntegralMissionResult> result = getSQLQuery(sql.toString()).
                    addScalar("misId", StandardBasicTypes.LONG).
                    addScalar("misName", StandardBasicTypes.STRING).
                    addScalar("misDis", StandardBasicTypes.STRING).
                    addScalar("isGet", StandardBasicTypes.INTEGER).
                    addScalar("buttonStatus", StandardBasicTypes.INTEGER).
                    addScalar("buttonText", StandardBasicTypes.STRING).
                    addScalar("isEnd", StandardBasicTypes.INTEGER).
                    addScalar("misCategory", StandardBasicTypes.INTEGER).
                    addScalar("integralGet", StandardBasicTypes.INTEGER).
                    addScalar("misType", StandardBasicTypes.INTEGER).
                    addScalar("count", StandardBasicTypes.INTEGER).
                    addScalar("missCounts", StandardBasicTypes.INTEGER).
                    addScalar("endMisId", StandardBasicTypes.LONG).
                    addScalar("createDate", StandardBasicTypes.DATE).
                    addScalar("firstDayWeek", StandardBasicTypes.DATE).
                    addScalar("lastDayWeek", StandardBasicTypes.DATE).
                    addScalar("priority", StandardBasicTypes.INTEGER).
                    addScalar("misPeriod", StandardBasicTypes.INTEGER).
                    setLong("userid", userId).
                    setResultTransformer(Transformers.aliasToBean(UserIntegralMissionResult.class)).list();

            newMisList = getNewMissionInfoList(result);
            //更新缓存
            publicCommonRedisUtil.setUserMissionInfo(userId, GfJsonUtil.toJSONString(newMisList), DateUtils.getSecondsToTomorrow());


        } catch (Exception e) {
            logger.error("获取用户任务列表 【UserId={};StackTrace: {}】", userId, LogExceptionStackTrace.erroStackTrace(e));
        }

        return newMisList;
    }

    public List<UserIntegralMissionResult> getNewMissionInfoList(List<UserIntegralMissionResult> missList) {
        List<UserIntegralMissionResult> newMisList = new ArrayList<UserIntegralMissionResult>();
        if (missList != null && missList.size() > 0) {
            for (UserIntegralMissionResult mis : missList) {
                //注意过滤：如果是一次性任务完成了，积分领取了，当天显示，之后不显示
                //isget:0未领取，1已领取   isend:0未完成，1已完成，3不可领取(比如首次充值未满20元，则以后没该任务机会)
                Boolean once = mis.getMisPeriod().equals(UserContext.USER_MISSION_TYPE_ONCE) &&
                        //(mis.getIsEnd().intValue() == 1 || mis.getIsEnd().intValue() == 3) &&
                        (mis.getIsGet().intValue() == 1 || mis.getIsEnd().intValue() == 3) &&
                        !DateUtil.isSameDay(mis.getCreateDate(), DateUtil.getDate());
                if (!once) {
                    if (mis.getIsEnd().intValue() == 1 && mis.getIsGet().intValue() == 1) {
                        //0按钮灰色不可用，1按钮亮出来可用
                        mis.setButtonStatus(0);
                        mis.setButtonText(UserContext.USER_BUTTON_TEXT_DONE);
                        //针对周期性任务：每日任务 "今日已领"
                        if (mis.getMisPeriod() != null && mis.getMisPeriod().intValue() == UserContext.USER_MISSION_TYPE_DAY &&
                                mis.getCreateDate() != null && DateUtil.isSameDay(mis.getCreateDate(), DateUtil.getDate())) {
                            mis.setButtonText(UserContext.USER_BUTTON_TEXT_DONETODAY);
                        }
                        //针对周期性任务：每周任务 "本周已领"
                        if (mis.getMisPeriod() != null && mis.getMisPeriod().intValue() == UserContext.USER_MISSION_TYPE_WEEK &&
                                mis.getFirstDayWeek().getTime() <= DateUtil.getDate().getTime() &&
                                mis.getLastDayWeek().getTime() >= DateUtil.getDate().getTime()) {
                            mis.setButtonText(UserContext.USER_BUTTON_TEXT_DONEWEEK);
                        }
                        mis.setStatusSort(2);
                    }
                    if (mis.getIsEnd().intValue() == 0 && mis.getIsGet().intValue() == 0) {
                        mis.setButtonStatus(1);
                        mis.setButtonText(UserContext.USER_BUTTON_TEXT_TODO);
                        mis.setStatusSort(1);
                    }
                    if (mis.getIsEnd().intValue() == 1 && mis.getIsGet().intValue() == 0) {
                        mis.setButtonStatus(1);
                        mis.setButtonText(UserContext.USER_BUTTON_TEXT_CANGET);
                        mis.setStatusSort(0);
                    }
                    //朝夕相处
                    if (mis.getMisId() != null && mis.getMisId().intValue() == MissionTypeEnum.FOREVERLOVE.getIndex()) {
                        //格式化朝夕相处的任务名称
                        mis.setMisName(String.format(UserContext.USER_MISSION_FOREVERLOVE_NAME, mis.getCount() == null ? 0 : mis.getCount(), mis.getMissCounts()));
                    }
                    //如果isend=3,那么是"不可领取"状态
                    if (mis.getIsEnd().intValue() == 3) {
                        mis.setButtonStatus(0);
                        mis.setButtonText(UserContext.USER_BUTTON_TEXT_NOTGET);
                        mis.setStatusSort(2);
                    }

                    newMisList.add(mis);
                }
            }
            //List排序
            if (newMisList != null && newMisList.size() > 0) {
                listSorter.sort(newMisList, "statusSort", "misPeriod", "priority");
            }
        }
        return newMisList;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/23 14:59
     * @Description 是否可以领取该任务积分
     */
    public Boolean CanGetIntegral(Long userId, Integer missionId) {
        String sql = "select count(1) as num from mis_user_end_mission where n_is_add=0 and n_is_end=1 and  n_mission_id=:missionId and n_userid=:userId";
        Integer num = Integer.parseInt(getSQLQuery(sql).setInteger("missionId", missionId).setLong("userId", userId).uniqueResult().toString());
        return num > 0;
    }


    public List<UserIntegralMissionResult> getMissionsInfoForeverDaysByUserId(Long userId) {
        List<UserIntegralMissionResult> result = new ArrayList<UserIntegralMissionResult>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT IFNULL(c.n_id, 0) AS endMisId,a.n_mission_id AS misId,a.s_mission_name AS misName,a.n_mission_priority AS priority,");
        sql.append("a.s_remark AS misDis,IFNULL(c.n_is_add, 0) AS isGet,0 buttonStatus,'' buttonText,n_mission_counts AS missCounts,");
        sql.append("a.n_mission_times AS misPeriod,IFNULL(c.n_is_end, 0) AS isEnd,a.n_mission_category AS misCategory,");
        sql.append("c.d_create_date AS createDate,IFNULL(a.n_mission_integral, 0) AS integralGet,a.n_mission_type AS misType,");
        sql.append("IFNULL(c.n_count, 0) AS count,DATE_SUB(CURDATE(),INTERVAL WEEKDAY(CURDATE()) DAY) AS firstDayWeek,");
        sql.append("DATE_SUB(CURDATE(),INTERVAL WEEKDAY(CURDATE()) - 6 DAY) AS lastDayWeek ");
        sql.append("FROM mis_user_mission a LEFT JOIN mis_user_end_mission c ON a.n_mission_id = c.n_mission_id AND c.n_userid = :userid ");
        sql.append("AND date(c.d_create_date)>=DATE_SUB(CURDATE(),INTERVAL WEEKDAY(CURDATE()) DAY) ");
        sql.append("AND date(c.d_create_date)<=DATE_SUB(CURDATE(),INTERVAL WEEKDAY(CURDATE()) - 6 DAY) ");
        sql.append("WHERE a.n_mission_id = 11 LIMIT 1");

        try {
            result = getSQLQuery(sql.toString()).
                    addScalar("misId", StandardBasicTypes.LONG).
                    addScalar("misName", StandardBasicTypes.STRING).
                    addScalar("misDis", StandardBasicTypes.STRING).
                    addScalar("isGet", StandardBasicTypes.INTEGER).
                    addScalar("buttonStatus", StandardBasicTypes.INTEGER).
                    addScalar("buttonText", StandardBasicTypes.STRING).
                    addScalar("isEnd", StandardBasicTypes.INTEGER).
                    addScalar("misCategory", StandardBasicTypes.INTEGER).
                    addScalar("integralGet", StandardBasicTypes.INTEGER).
                    addScalar("misType", StandardBasicTypes.INTEGER).
                    addScalar("count", StandardBasicTypes.INTEGER).
                    addScalar("missCounts", StandardBasicTypes.INTEGER).
                    addScalar("endMisId", StandardBasicTypes.LONG).
                    addScalar("createDate", StandardBasicTypes.DATE).
                    addScalar("firstDayWeek", StandardBasicTypes.DATE).
                    addScalar("lastDayWeek", StandardBasicTypes.DATE).
                    addScalar("priority", StandardBasicTypes.INTEGER).
                    addScalar("misPeriod", StandardBasicTypes.INTEGER).
                    setLong("userid", userId).
                    setResultTransformer(Transformers.aliasToBean(UserIntegralMissionResult.class)).list();
        } catch (Exception e) {
            logger.error("获取朝夕相处任务信息失败 【UserId={};StackTrace: {}】", userId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }


    public Boolean addUserEndMission(UserIntegralMissionModel miss) throws Exception {
        Boolean bool = false;
        if (miss.getMisId() != null && MissionTypeEnum.FOREVERLOVE.getIndex() == miss.getMisId().intValue()) {
            SimpleDateFormat time2Format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat time1Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newLastDay = time1Format.parse((time2Format.format(miss.getLastDayWeek()) + " 23:59:59"));
            if (DateUtils.getSecondsByLastDayWeek(newLastDay) != null) {
                String sqlDel = "DELETE FROM mis_user_end_mission WHERE n_mission_id =:missionId AND n_userid=:userId AND date(d_create_date)<date(:firstdayweek)";
                Integer delResult = getSQLQuery(sqlDel)
                        .setLong("missionId", miss.getMisId())
                        .setLong("userId", miss.getUserId())
                        .setDate("firstdayweek", miss.getFirstDayWeek())
                        .executeUpdate();
                if (delResult >= 0) {
                    logger.info("删除过期朝夕相处任务成功：traceId:{}, userId:{}, missionId:{},missModel:{}",
                            miss.getTraceId(), miss.getUserId(), miss.getMisId(), GfJsonUtil.toJSONString(miss));
                    StringBuilder sql = new StringBuilder();
                    sql.append("INSERT INTO mis_user_end_mission(n_mission_id, n_is_end, d_create_date, n_is_add,n_count,n_userid,n_integrals) VALUES ");
                    sql.append("(:misid,:isend,:createdate,:isadd,:count,:userid,:integral);");
                    Integer result = getSQLQuery(sql.toString()).setLong("misid", miss.getMisId()).setInteger("isend", miss.getIsEnd()).
                            setString("createdate", time1Format.format(miss.getCreateDate())).setInteger("isadd", miss.getIsGet()).setInteger("count", miss.getCount()).
                            setLong("userid", miss.getUserId()).setLong("integral", miss.getIntegralGet()).executeUpdate();
                    if (result > 0) {
                        publicCommonRedisUtil.clearUserMissionInfo(miss.getUserId());
                        publicCommonRedisUtil.saddForvevrdaysByWeek(miss.getUserId(), miss.getWeekDay(), DateUtils.getSecondsByLastDayWeek(newLastDay));
                        bool = true;
                    }
                }
            }
        }

        return bool;
    }


    public Boolean updateUserEndMissionCount(UserIntegralMissionModel miss) throws Exception {
        Boolean bool = false;
        if (miss.getMisId() != null && MissionTypeEnum.FOREVERLOVE.getIndex() == miss.getMisId().intValue()) {
            SimpleDateFormat time2Format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat time1Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newLastDay = time1Format.parse((time2Format.format(miss.getLastDayWeek()) + " 23:59:59"));
            if (DateUtils.getSecondsByLastDayWeek(newLastDay) != null) {
                StringBuilder sql = new StringBuilder();
                sql.append("UPDATE mis_user_end_mission SET n_count=:count WHERE n_id=:id");
                Integer result = getSQLQuery(sql.toString()).setInteger("count", miss.getCount()).
                        setLong("id", miss.getEndMisId()).executeUpdate();
                if (result > 0) {
                    publicCommonRedisUtil.clearUserMissionInfo(miss.getUserId());
                    publicCommonRedisUtil.saddForvevrdaysByWeek(miss.getUserId(), miss.getWeekDay(), DateUtils.getSecondsByLastDayWeek(newLastDay));
                    bool = true;
                }
            }
        }
        return bool;
    }


    public Boolean updateUserEndMissionIsEnd(UserIntegralMissionModel miss) throws Exception {
        Boolean bool = false;
        if (miss.getMisId() != null && MissionTypeEnum.FOREVERLOVE.getIndex() == miss.getMisId().intValue()) {
            SimpleDateFormat time2Format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat time1Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newLastDay = time1Format.parse((time2Format.format(miss.getLastDayWeek()) + " 23:59:59"));
            if (DateUtils.getSecondsByLastDayWeek(newLastDay) != null) {
                StringBuilder sql = new StringBuilder();
                sql.append("UPDATE mis_user_end_mission SET n_is_end=:isend WHERE n_id=:id AND n_count>=:misscount");
                Integer result = getSQLQuery(sql.toString()).setInteger("isend", miss.getIsEnd()).
                        setLong("id", miss.getEndMisId()).setInteger("misscount", miss.getMissCounts()).executeUpdate();
                if (result > 0) {
                    publicCommonRedisUtil.clearUserMissionInfo(miss.getUserId());
                    publicCommonRedisUtil.saddForvevrdaysByWeek(miss.getUserId(), miss.getWeekDay(), DateUtils.getSecondsByLastDayWeek(newLastDay));
                    bool = true;
                }
            }
        }
        return bool;
    }


}
