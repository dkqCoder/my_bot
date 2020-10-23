package com.tty.task.dao;

import com.tty.data.dto.JczqDataDTO;
import com.tty.task.ent.CompetitionEnt;

/**
 * @Author: dukeqiang
 * @Date: 2020/10/13 18:29
 */
public interface CrawlingDao {
    /**
     * 保存胜平负，让球胜平负赔率信息
     * @param traceId
     * @param jczqDataDTO
     */
    void saveWDLCompetitionInfo(String traceId, JczqDataDTO jczqDataDTO);

    /**
     * 保存比分赔率信息
     * @param traceId
     * @param jczqDataDTO
     */
    void saveCrsCompetitionInfo(String traceId, JczqDataDTO jczqDataDTO);

    /**
     * 保存总进球数赔率信息
     * @param traceId
     * @param jczqDataDTO
     */
    void saveGoalsCompetitionInfo(String traceId, JczqDataDTO jczqDataDTO);

    /**
     * 保存半全场胜平负赔率信息
     * @param traceId
     * @param jczqDataDTO
     */
    void saveHafuCompetitionInfo(String traceId,JczqDataDTO jczqDataDTO);
}
