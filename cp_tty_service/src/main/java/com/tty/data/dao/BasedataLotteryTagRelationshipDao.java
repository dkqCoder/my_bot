package com.tty.data.dao;

import com.tty.data.dao.entity.BasedataLotteryTagRelationshipENT;
import java.util.List;

/**
 **/
public interface BasedataLotteryTagRelationshipDao {

    List<BasedataLotteryTagRelationshipENT> listAllRelationship();

    List<BasedataLotteryTagRelationshipENT>  listLotteryTagRelationByLotteryId(Integer lotteryId);

}