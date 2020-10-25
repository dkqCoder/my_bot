package com.tty.user.dao.mission.impl;

import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.dao.mission.MisUserEndMissionDao;
import com.tty.user.dao.mission.pojo.MisUserEndMission;
import com.tty.user.dto.mission.UserEndMissionModel;
import com.tty.user.dto.mission.UserMissionModel;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author chenlongfei
 */
@Repository("misUserEndMissionDao")
public class MisUserEndMissionDaoImpl extends BaseDao<MisUserEndMission> implements MisUserEndMissionDao {
    private static final Logger logger = LoggerFactory.getLogger(MisUserEndMissionDaoImpl.class);
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;

    public void saveMisUserEndMission(MisUserEndMission ent) {
        save(ent);
    }

    public void updateMisUserEndMission(MisUserEndMission ent) {
        update(ent);
    }

    public void deleteMisUserEndMission(String traceId, Long userId, Long missionId) {
        String sql = "delete from mis_user_end_mission where n_mission_id =:missionId and n_userid=:userId";
        getSQLQuery(sql)
                .setLong("missionId", missionId)
                .setLong("userId", userId)
                .executeUpdate();
        logger.debug("删除已结束任务，traceId:{},userId:{};mission:{};", traceId, userId, missionId);
    }

    public void saveOrUpdateMisUserEndMission(MisUserEndMission ent) {
        saveOrUpdate(ent);
    }


    /**
     * @Author shenwei
     * @Date 2016/12/22 11:38
     * @Description 获取任务对应相关积分信息
     */
    @Override
    public UserEndMissionModel getUserEndMissionModel(Long userId, Long missionId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select a.n_id as id, a.n_mission_id as missionId,a.n_is_end as isEnd,a.n_userid as userId,a.d_create_date as createDate,a.n_count as count,b.s_remark as remark,a.n_integrals as integrals,a.n_is_add as isAdd");
        sql.append(" from mis_user_end_mission a inner join mis_user_mission b on a.n_mission_id=b.n_mission_id");
        sql.append(" where a.n_is_add=0 and  a.n_mission_id=:missionId and n_userid=:userId ;");

        List result = getSQLQuery(sql.toString())
                .addScalar("missionId", StandardBasicTypes.LONG)
                .addScalar("userId", StandardBasicTypes.LONG)
                .addScalar("remark", StandardBasicTypes.STRING)
                .addScalar("integrals", StandardBasicTypes.INTEGER)
                .addScalar("isAdd", StandardBasicTypes.INTEGER)
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("isEnd", StandardBasicTypes.INTEGER)
                .addScalar("count", StandardBasicTypes.INTEGER)
                .addScalar("createDate", StandardBasicTypes.DATE)
                .setLong("missionId", missionId)
                .setLong("userId", userId)
                .setResultTransformer(Transformers.aliasToBean(UserEndMissionModel.class))
                .list();

        if (result != null && result.size() > 0) {
            return (UserEndMissionModel) result.get(0);
        }
        return null;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/26 20:15
     * @Description 用户领取积分之后 更改该条记录积分领取状态
     */
    public Boolean updateUserEndMission(String traceId, Long userId, Long missionId) {
        String sql = "UPDATE mis_user_end_mission SET n_is_add = 1 WHERE n_userid=:userId AND n_mission_id=:missionId";
        Integer result = getSQLQuery(sql)
                .setLong("userId", userId)
                .setLong("missionId", missionId)
                .executeUpdate();
        if (result > 0) {
            MisUserEndMission misUserEndMission = new MisUserEndMission();
            misUserEndMission.setMissionId(missionId);
            misUserEndMission.setCreateDate(new Date());
            misUserEndMission.setIsAdd(1);
            misUserEndMission.setUserid(userId);
            misUserEndMission.setIsEnd(1);
            logger.debug("用户任务积分状态变更成功，traceId:{},userId:{};mission:{};", traceId, userId, GfJsonUtil.toJSONString(misUserEndMission));
            //清除用户任务信息缓存
            publicCommonRedisUtil.clearUserMissionInfo(misUserEndMission);
        }
        return result >= 0;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/27 9:42
     * @Description 获取单条任务状态(针对签到)
     */
    @Override
    public UserMissionModel getUserMissionModel(Long userId, Long missionId) {
        StringBuilder builder = new StringBuilder();
        builder.append("select a.n_mission_id as misId,a.s_mission_name as misName,a.s_remark as misDis,b.n_is_end as isEnd ")
                .append(" ,b.n_is_add as isGet,a.n_mission_category as misCategory,b.n_integrals as integralGet,a.n_mission_type as misType")
                .append(" from mis_user_mission a inner join mis_user_end_mission b on a.n_mission_id=b.n_mission_id  where b.n_mission_id=:missionId and b.n_userid=:userId and Date(now())=Date(b.d_create_date) ");

        UserMissionModel model = new UserMissionModel();
        List<UserMissionModel> items = getSQLQuery(builder.toString())
                .addScalar("misId", StandardBasicTypes.LONG)
                .addScalar("misName", StandardBasicTypes.STRING)
                .addScalar("misDis", StandardBasicTypes.STRING)
                .addScalar("isEnd", StandardBasicTypes.INTEGER)
                .addScalar("isGet", StandardBasicTypes.INTEGER)
                .addScalar("misCategory", StandardBasicTypes.INTEGER)
                .addScalar("integralGet", StandardBasicTypes.INTEGER)
                .addScalar("misType", StandardBasicTypes.INTEGER)
                .setLong("missionId", missionId)
                .setLong("userId", userId)
                .setResultTransformer(Transformers.aliasToBean(UserMissionModel.class))
                .list();

        if (items != null && items.size() > 0) {
            model = items.get(0);
            if (model.getIsGet() == 1) {
                model.setButtonStatus(0);
                model.setButtonText("今日已签");
            } else {
                model.setButtonStatus(0);
                model.setButtonText("完成任务");
            }
        }
        return model;
    }
}
