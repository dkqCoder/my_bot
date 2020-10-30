package com.tty.data.service;

import com.tty.data.dao.entity.BasedataLotteryTagRelationshipENT;
import java.util.List;

/**
 * @author zxh
 * @create 2017-03-01 20:03:21
 **/
public interface BasedataLotteryTagRelationshipService {

    List<BasedataLotteryTagRelationshipENT> listAllRelationship();

    Boolean isGPC(Integer lotteryId);

    Boolean isJcOrZc(Integer lotteryId);

    Boolean isJcc(Integer lotteryId);

}
