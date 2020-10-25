package com.tty.user.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.utils.StringUtils;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.common.utils.WhereUtils;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserInfoExtensionFields;
import com.tty.user.dao.WebUserBaseInfoDao;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.pojo.UserIntegralRecords;
import com.tty.user.model.web.WebIntegralUseListModel;
import com.tty.user.model.web.WebUserInfoModel;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LiuJin
 * @date 2017/1/13
 * @Descreption 用户基础信息持久层
 */
@Repository("webUserBaseInfoDao")
public class WebUserBaseInfoDaoImpl extends BaseDao<UserBaseInfoENT> implements WebUserBaseInfoDao {
    private static final Logger logger = LoggerFactory.getLogger(WebUserBaseInfoDaoImpl.class);

    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;


    public void saveUserBaseInfo(UserBaseInfoENT ent) {
        save(ent);
    }

    public void updateUserBaseInfo(UserBaseInfoENT ent) {
        update(ent);
    }

    public void deleteUserBaseInfo(UserBaseInfoENT ent) {
        delete(ent);
    }

    public void saveOrUpdateUserBaseInfo(UserBaseInfoENT ent) {
        saveOrUpdate(ent);
    }

    /**
     * @param start  当前页
     * @param limit  页面大小
     * @param search 查询条件
     * @return 满足条件的记录集合
     * @Description 按照条件查询用户等级的记录
     */
    @Override
    public List<Map<String, Object>> getUserBaseInfoListByCondition(Integer start, Integer limit, JSONObject search) {
        String sql = "select ubi.n_user_id as userId,ubi.s_name as `name`,ulg.n_user_growups as userGrowUps,ulg.n_user_level as userLevel," +
                "uai.n_user_integral as integral from user_base_info ubi left outer join user_level_growup ulg on ubi.n_user_id = ulg.n_user_id " +
                "left outer join user_acc_integral uai on ubi.n_user_id = uai.n_user_id where 1 = 1";

        WhereUtils where = WhereUtils.ins(sql)
                .contains("ubi.n_user_id", search.getString("userId"))
                .contains("ubi.s_name", search.getString("name"))
                .andEq("ulg.n_user_level", search.getInteger("userLevel"));

        SQLQuery query = getSQLQuery(where.getAllSql());
        List list = where.getParms();
        for (int i = 0; i < list.size(); i++) {
            query.setParameter(i, list.get(i));
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setFirstResult((start - 1) * limit).setMaxResults(limit);
        return query.list();
    }

    /**
     * @param search 查询条件
     * @return 满足条件的记录个数
     * @Description 按照条件查询用户等级记录总数
     */
    @Override
    public Long getUserBaseInfoListCountByCondition(JSONObject search) {
        String sql = "select count(1) from user_base_info ubi left outer join user_level_growup ulg on ubi.n_user_id = ulg.n_user_id " +
                "left outer join user_acc_integral uai on ubi.n_user_id = uai.n_user_id where 1 = 1";

        WhereUtils where = WhereUtils.ins(sql)
                .contains("ubi.n_user_id", search.getString("userId"))
                .contains("ubi.s_name", search.getString("name"))
                .andEq("ulg.n_user_level", search.getInteger("userLevel"));

        SQLQuery query = getSQLQuery(where.getAllSql());
        List list = where.getParms();
        for (int i = 0; i < list.size(); i++) {
            query.setParameter(i, list.get(i));
        }
        return Long.valueOf(query.uniqueResult().toString());
    }

    @Override
    public List<WebUserInfoModel> getUserInfoList(Integer start, Integer limit, JSONObject search) {
        List<WebUserInfoModel> userInfoListNew = new ArrayList<WebUserInfoModel>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.n_user_id AS userId,a.n_user_integral AS integral,IFNULL(b.n_user_growups,0) AS userGrowUps,IFNULL(b.n_user_level,0) AS userLevel ");
        sql.append("FROM user_acc_integral a LEFT JOIN user_level_growup b ON a.n_user_id=b.n_user_id ");
        WhereUtils where = null;
        //因为等级为0的表里没有，所以特殊处理
        if (StringUtils.isNotBlank(search.getString("userLevel")) && search.getString("userLevel").trim().equals("0")) {
            sql.append("WHERE b.n_user_level IS NULL ");
            where = WhereUtils.ins(sql.toString());
        } else {
            sql.append("WHERE 1=1 ");
            where = WhereUtils.ins(sql.toString());
            where.andEq("b.n_user_level", search.getString("userLevel"));
        }
        where.andEq("a.n_user_id", search.getString("userId"))
                .andGe("a.n_user_integral", search.getString("integralStart"))
                .andLe("a.n_user_integral", search.getString("integralEnd"))
                .addOrderBy(" ORDER BY a.n_user_id DESC");

        SQLQuery query = getSQLQuery(where.getAllSql());
        List list = where.getParms();
        for (int i = 0; i < list.size(); i++) {
            query.setParameter(i, list.get(i));
        }
        List<WebUserInfoModel> userInfoList = query.
                addScalar("userId", StandardBasicTypes.LONG).
                addScalar("integral", StandardBasicTypes.LONG).
                addScalar("userGrowUps", StandardBasicTypes.LONG).
                addScalar("userLevel", StandardBasicTypes.INTEGER).
                setResultTransformer(Transformers.aliasToBean(WebUserInfoModel.class)).
                setFirstResult((start - 1) * limit).setMaxResults(limit).list();
        if (userInfoList != null && userInfoList.size() > 0) {
            for (WebUserInfoModel userInfo : userInfoList) {
                WebUserInfoModel userInfoNew = new WebUserInfoModel();
                userInfoNew.setUserId(userInfo.getUserId());
                userInfoNew.setIntegral(userInfo.getIntegral());
                userInfoNew.setUserGrowUps(userInfo.getUserGrowUps());
                userInfoNew.setUserLevel(userInfo.getUserLevel());
                //从缓存读取打败多少彩友信息
                String strBeat = publicCommonRedisUtil.getUserInfoExtension(userInfo.getUserId(), UserInfoExtensionFields.USER_BEAT_FIELD);
                if (StringUtils.isNotBlank(strBeat)) {
                    userInfoNew.setBeatInfo(Double.parseDouble(strBeat));
                } else {
                    //如果获取不到，设最小值作为默认值
                    userInfoNew.setBeatInfo(UserContext.USER_BEATINFO_MIN);
                }
                //今日是否签到
                if (publicCommonRedisUtil.isInSignInTodaySet(userInfo.getUserId())) {
                    userInfoNew.setIsSignInToday(1);
                } else {
                    userInfoNew.setIsSignInToday(0);
                }

                userInfoListNew.add(userInfoNew);
            }
        }

        return userInfoListNew;
    }

    public Long getUserInfoCount(JSONObject search) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) num FROM user_acc_integral a LEFT JOIN user_level_growup b ON a.n_user_id=b.n_user_id ");
        WhereUtils where = null;
        //因为等级为0的表里没有，所以特殊处理
        if (StringUtils.isNotBlank(search.getString("userLevel")) && search.getString("userLevel").trim().equals("0")) {
            sql.append("WHERE b.n_user_level IS NULL ");
            where = WhereUtils.ins(sql.toString());
        } else {
            sql.append("WHERE 1=1 ");
            where = WhereUtils.ins(sql.toString());
            where.andEq("b.n_user_level", search.getString("userLevel"));
        }
        where.andEq("a.n_user_id", search.getString("userId"))
                .andGe("a.n_user_integral", search.getString("integralStart"))
                .andLe("a.n_user_integral", search.getString("integralEnd"));
        SQLQuery query = getSQLQuery(where.getAllSql());
        List list = where.getParms();
        for (int i = 0; i < list.size(); i++) {
            query.setParameter(i, list.get(i));
        }

        return Long.valueOf(query.uniqueResult().toString());
    }


    public List<UserIntegralRecords> getUserIntegralDetails(Integer start, Integer limit, JSONObject search) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT n_id AS id,n_userid AS userid,d_create_date AS createDate,n_count AS count,");
        sql.append("s_description AS description FROM user_integral_records ");
        sql.append("WHERE n_userid=:userid ORDER BY n_id DESC");
        return getSQLQuery(sql.toString())
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("userid", StandardBasicTypes.LONG)
                .addScalar("createDate", StandardBasicTypes.TIMESTAMP)
                .addScalar("count", StandardBasicTypes.INTEGER)
                .addScalar("description", StandardBasicTypes.STRING)
                .setString("userid", search.getString("userId"))
                .setResultTransformer(Transformers.aliasToBean(UserIntegralRecords.class))
                .setFirstResult((start - 1) * limit).setMaxResults(limit)
                .list();
    }

    public Long getUserIntegralDetailsCount(JSONObject search) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) count FROM user_integral_records ");
        sql.append("WHERE n_userid=:userid ORDER BY n_id DESC");
        return Long.valueOf(getSQLQuery(sql.toString())
                .setString("userid", search.getString("userId")).uniqueResult().toString());
    }

    public List<WebIntegralUseListModel> getIntegralUseList(Integer start, Integer limit, JSONObject search) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT uir.n_userid AS userId, uir.d_create_date AS createDate,uir.n_count AS integral,");
        sql.append(" uir.s_description AS description,uir.n_type AS type,uir.n_sourceid AS sourceid FROM user_integral_records uir ");
        sql.append(" WHERE 1=1 ");

        WhereUtils where = WhereUtils.ins(sql.toString())
                .andEq("uir.n_userid", search.getString("userId"))
                .andEq("uir.n_sourceid", search.getString("sourceid"))
                .andEq("uir.n_type", search.getString("type"))
                .andGe("uir.d_create_date", search.getDate("startTime"))
                .andLe("uir.d_create_date", search.getDate("endTime"))
                //排序
                .addOrderBy(" order by uir.d_create_date desc");

        SQLQuery query = getSQLQuery(where.getAllSql());
        List list = where.getParms();
        for (int i = 0; i < list.size(); i++) {
            query.setParameter(i, list.get(i));
        }
        List<WebIntegralUseListModel> rows = query
                .addScalar("userId", StandardBasicTypes.LONG)
                .addScalar("createDate", StandardBasicTypes.TIMESTAMP)
                .addScalar("integral", StandardBasicTypes.INTEGER)
                .addScalar("description", StandardBasicTypes.STRING)
                .addScalar("type", StandardBasicTypes.INTEGER)
                .setResultTransformer(Transformers.aliasToBean(WebIntegralUseListModel.class))
                .setFirstResult((start - 1) * limit).setMaxResults(limit)
                .list();
        return rows;
    }

    public Long getIntegralUseListCount(JSONObject search) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) num FROM user_integral_records ");
        sql.append("WHERE 1=1 ");
        WhereUtils where = WhereUtils.ins(sql.toString())
                .andEq("n_userid", search.getString("userId"))
                .andEq("n_sourceid", search.getString("sourceid"))
                .andEq("n_type", search.getString("type"))
                .andGe("d_create_date", search.getDate("startTime"))
                .andLe("d_create_date", search.getDate("endTime"));
        SQLQuery query = getSQLQuery(where.getAllSql());
        List list = where.getParms();
        for (int i = 0; i < list.size(); i++) {
            query.setParameter(i, list.get(i));
        }

        return Long.valueOf(query.uniqueResult().toString());
    }

    public List<Map<String, Object>> getUserLevelAndGrowup(Long userId) {
        String sql = "SELECT IFNULL(n_user_growups,0) AS growups,IFNULL(n_user_level,0) AS level FROM user_level_growup WHERE n_user_id=:userId";
        List<Map<String, Object>> list = getSQLQuery(sql)
                .addScalar("growups", StandardBasicTypes.LONG)
                .addScalar("level", StandardBasicTypes.INTEGER)
                .setLong("userId", userId)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .list();
        return list;
    }

}
