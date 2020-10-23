package com.tty.task.main;

import com.jdd.fm.core.utils.SpringContextUtil;
import com.tty.task.context.CrawlingSourceContext;
import com.tty.task.service.CompetitionCrawlingService;
import java.util.UUID;

/**
 * @Author: dukeqiang
 * @Date: 2020/10/15 20:53
 */
public class CompetitionCrawlingTask {

    private CompetitionCrawlingService competitionCrawlingService = (CompetitionCrawlingService) SpringContextUtil.getBean("competitionCrawlingService");

    public void start(){
        String traceId = String.valueOf(UUID.randomUUID());
        competitionCrawlingService.crawlingWDLInfo(traceId,CrawlingSourceContext.JCGW_SOURCE);
    }
}
