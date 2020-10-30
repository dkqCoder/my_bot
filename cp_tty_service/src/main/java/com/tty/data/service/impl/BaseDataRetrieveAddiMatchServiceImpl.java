package com.tty.data.service.impl;

import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.data.context.BasedataSharedRedisKeys;
import com.tty.data.service.BaseDataRetrieveAddiMatchService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 修改原来的开奖逻辑 为 .每当当前期一场比赛开奖出来，就显示当前期所有比赛开奖信息；例如：20171022期001开奖出来，开奖公告就显示20171022期全部当场开奖信息
 * Created by rxu on 2017/11/1.
 */
@Service("baseDataRetrieveAddiMatchService")
public class BaseDataRetrieveAddiMatchServiceImpl implements BaseDataRetrieveAddiMatchService {

    @Autowired
    @Qualifier("jedisClusterFactory")
    private JedisClusterFactory jedisClusterFactory;

    @Override
    public  List<String> getSeparateMatchResultByIssueId(String lotteryId, String issueId){
        final String issueId_issue_key = String.format(BasedataSharedRedisKeys.BASEDATA_WIN_ISSUIE_MATCHES, lotteryId);
        String issueCache = jedisClusterFactory.hget(issueId_issue_key, issueId);
        if(StringUtils.isNotEmpty(issueCache)){
            return  GfJsonUtil.parseArray(issueCache,String.class);
        }
        return new ArrayList<>();
    }
}
