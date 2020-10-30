package com.tty.data.dao.impl;

import com.jdd.fm.core.db.BaseDao;
import com.tty.data.dao.BasedataLotteryTagRelationshipDao;
import com.tty.data.dao.entity.BasedataLotteryTagRelationshipENT;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 */
@Repository("basedataLotteryTagRelationshipDao")
public class BasedataLotteryTagRelationshipDaoImpl extends BaseDao<BasedataLotteryTagRelationshipENT> implements BasedataLotteryTagRelationshipDao {

    @Override
    public List<BasedataLotteryTagRelationshipENT> listAllRelationship() {
        StringBuilder hql = new StringBuilder();
        hql.append("SELECT * FROM basedata_lottery_tag_relationship");
        return (List<BasedataLotteryTagRelationshipENT>) getQuery(hql.toString()).list();
    }

    @Override
    public List<BasedataLotteryTagRelationshipENT> listLotteryTagRelationByLotteryId(Integer lotteryId) {
        StringBuilder hql = new StringBuilder();
        hql.append("SELECT * FROM basedata_lottery_tag_relationship where lotteryId=:lotteryId");
        return (List<BasedataLotteryTagRelationshipENT>) getQuery(hql.toString()).setInteger("lotteryId", lotteryId).list();
    }

}
