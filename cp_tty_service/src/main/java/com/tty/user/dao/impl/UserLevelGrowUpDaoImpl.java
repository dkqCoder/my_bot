package com.tty.user.dao.impl;

import com.jdd.fm.core.db.BaseDao;
import com.tty.user.dao.WebUserLevelGrowUpDao;
import com.tty.user.dao.pojo.UserLevelGrowUp;
import com.tty.user.dto.UserLevelAndIntegralDTO;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

/**
 * @author LiuJin
 * @date 2017/1/13
 * @Descreption 用户等级持久层
 */
@Repository("UserLevelGrowUpDao")
public class UserLevelGrowUpDaoImpl extends BaseDao<UserLevelGrowUp> implements WebUserLevelGrowUpDao {

    public void saveUserLevelGrowUp(UserLevelGrowUp ent) {
        save(ent);
    }

    public void updateUserLevelGrowUp(UserLevelGrowUp ent) {
        update(ent);
    }

    public void deleteUserLevelGrowUp(UserLevelGrowUp ent) {
        delete(ent);
    }

    public void saveOrUpdateUserLevelGrowUp(UserLevelGrowUp ent) {
        saveOrUpdate(ent);
    }

    @Override
    public UserLevelAndIntegralDTO getUserLevelByUserId(Long userId) {
        String sql = "SELECT uci.n_user_integral userIntegral,IFNULL(ulg.n_user_growups,0) AS userGrowUps,IFNULL(ulg.n_user_level,0) AS userLevel FROM user_acc_integral uci LEFT JOIN user_level_growup ulg ON uci.n_user_id = ulg.n_user_id WHERE uci.n_user_id =:userId";
        SQLQuery sqlQuery = getSQLQuery(sql);
        sqlQuery.addScalar("userIntegral", StandardBasicTypes.INTEGER)
                .addScalar("userGrowUps", StandardBasicTypes.LONG)
                .addScalar("userLevel", StandardBasicTypes.INTEGER);

        return (UserLevelAndIntegralDTO) sqlQuery.setLong("userId", userId).setResultTransformer(Transformers.aliasToBean(UserLevelAndIntegralDTO.class)).uniqueResult();
    }

}
