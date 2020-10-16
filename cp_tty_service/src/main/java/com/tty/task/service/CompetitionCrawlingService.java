package com.tty.task.service;

/**
 * @Author: dukeqiang
 * @Date: 2020/10/12 17:46
 */
public interface CompetitionCrawlingService {
    /**
     * 胜平负玩法信息
     */
    void crawlingWDLInfo(String traceId, String source);

    /**
     * 让球胜平负玩法信息
     */
    void crawlingLetBallWDLInfo(String traceId, String source);

    /**
     * 比分玩法信息
     */
    void crawlingCrsWDLInfo(String traceId, String source);

    /**
     * 进球数玩法信息
     */
    void crawlingGoalsWDLInfo(String traceId, String source);

    /**
     * 半全场玩法信息
     */
    void crawlingHafuWDLInfo(String traceId, String source);

}
