package com.tty.user.dao.mission.impl;

import com.jdd.fm.core.db.BaseDao;
import com.tty.user.dao.mission.MisUserMissionDao;
import com.tty.user.dao.mission.pojo.MisUserMission;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author ln
 */
@Repository("misUserMissionDao")
public class MisUserMissionDaoImpl extends BaseDao<MisUserMission> implements MisUserMissionDao {

    public void saveMisUserMission(MisUserMission ent) {
        save(ent);
    }

    public void updateMisUserMission(MisUserMission ent) {
        update(ent);
    }

    public void deleteMisUserMission(MisUserMission ent) {
        delete(ent);
    }

    public void saveOrUpdateMisUserMission(MisUserMission ent) {
        saveOrUpdate(ent);
    }


    /**
     * @Author shenwei
     * @Date 2016/12/21 14:05
     * @Description 获取任务实体
     */
    @Override
    public MisUserMission getMisUserMissionById(Long missionId, Long userId) {
        StringBuffer sql = new StringBuffer();
        sql.append("select n_mission_id as missionId,s_mission_name as missionName,n_mission_times as missionTimes")
                .append(",n_mission_type as missionType,n_mission_category as missionCategory,n_mission_counts as missionCounts")
                .append(",n_mission_integral as missionIntegral,n_mission_priority as missionPriority,s_remark as remark")
                .append(",n_mission_add_type as missionAddType,d_create_date as createDate from mis_user_mission where n_mission_id=:missionId ");
        List<MisUserMission> items = getSQLQuery(sql.toString())
                .addScalar("missionId", StandardBasicTypes.LONG)
                .addScalar("missionName", StandardBasicTypes.STRING)
                .addScalar("missionTimes", StandardBasicTypes.INTEGER)
                .addScalar("missionType", StandardBasicTypes.INTEGER)
                .addScalar("missionCategory", StandardBasicTypes.INTEGER)
                .addScalar("missionCounts", StandardBasicTypes.INTEGER)
                .addScalar("missionIntegral", StandardBasicTypes.INTEGER)
                .addScalar("missionPriority", StandardBasicTypes.INTEGER)
                .addScalar("remark", StandardBasicTypes.STRING)
                .addScalar("missionAddType", StandardBasicTypes.INTEGER)
                .addScalar("createDate", StandardBasicTypes.DATE)
                .setLong("missionId", missionId)
                .setResultTransformer(Transformers.aliasToBean(MisUserMission.class))
                .list();
        if (items != null && items.size() > 0) {
            return items.get(0);
        }
        return null;
    }

    /**
     * @Author shenwei
     * @Date 2016/12/28 15:30
     * @Description 获取单条任务状态
     */
    @Override
    public Map<String, Object> getMissionStatus(Long userId, Long missionId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select b.s_mission_name as missName,n_is_add as isAdd,n_is_end as isEnd ,b.s_remark as remark,a.n_integrals as integralsToAdd,b.n_mission_times as missionTimes  from mis_user_end_mission a inner join mis_user_mission b on a.n_mission_id = b.n_mission_id ")
                .append(" where n_userid=:userId and a.n_mission_id=:missionId");
        List<Map<String, Object>> list = getSQLQuery(sql.toString())
                .setParameter("userId", userId)
                .setParameter("missionId", missionId)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .list();
        if (list != null && list.size() > 0) {
            return (Map<String, Object>) (list.get(0));
        }
        return new HashMap<String, Object>();
    }
}
