package com.tty.task.dao;

import com.tty.data.dto.JczqDataDTO;
import com.tty.task.ent.CompetitionEnt;

/**
 * @Author: dukeqiang
 * @Date: 2020/10/13 18:29
 */
public interface CrawlingDao {
    void saveCompetitionInfo(String traceId, JczqDataDTO jczqDataDTO);
}
