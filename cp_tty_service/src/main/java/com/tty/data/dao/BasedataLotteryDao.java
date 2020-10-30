package com.tty.data.dao;

import com.tty.data.dao.entity.BasedataLotteryENT;

/**
 * @author zxh
 * @create 2017-02-09 16:42:58
 **/
public interface BasedataLotteryDao {

     BasedataLotteryENT getBasedataLottery(Integer id);

    BasedataLotteryENT findById(Integer lotteryId);


}