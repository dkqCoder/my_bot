package com.tty.data.service;

import com.tty.data.dao.entity.BasedataIssueENT;
import com.tty.data.dto.JclqDataDTO;
import com.tty.data.dto.JczqDataDTO;
import com.tty.data.dto.redis.RedisJclqMatchDTO;
import com.tty.data.dto.redis.RedisJclqMatchStopSaleDTO;
import com.tty.data.dto.redis.RedisJczqDggpMatchDTO;
import com.tty.data.dto.redis.RedisJczqMatchDTO;
import com.tty.data.dto.redis.RedisJczqMatchStopSaleDTO;
import com.tty.data.dao.entity.BasedataDgMatchENT;
import com.tty.data.dto.redis.RedisLotteryDTO;
import java.util.List;

/**
 * 操作SharedRedisManager
 */
public interface BasedataShardedRedisService {

    List<RedisJczqMatchDTO> getJczqMatchIssueList();

    //获取竞彩足球
    List<RedisJczqMatchStopSaleDTO> getJczqStopSale();

    //获取竞彩赛事
    List<BasedataDgMatchENT> getDgMatchList();

    //获取彩种信息
    RedisLotteryDTO getLotteryByLotteryId(Integer lottID);

    //根据彩种获取数据
    List<BasedataIssueENT> getIssuesByLotteryId(String lotteryId);

    //获取竞彩篮球赛事
    List<RedisJclqMatchDTO> getJcLqMatch();

    //竞彩篮球停售时间
    List<RedisJclqMatchStopSaleDTO> getJcLqMatchStopSale();

    //单关固配比赛
    List<RedisJczqDggpMatchDTO> getJczqDggpMatch();

    //竞彩足球赛果
    List<JczqDataDTO> getJczqMatchResultByIssueId(String issueId);

    //单场竞彩篮球赛果
    List<JclqDataDTO> getJclqMatchResultByIssueId(String issueId);


}
