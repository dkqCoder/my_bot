package com.tty.task.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.tty.common.utils.HttpUtils;
import com.tty.data.dto.JczqDataDTO;
import com.tty.task.context.CrawlingSourceContext;
import com.tty.task.context.CrawlingUrlContext;
import com.tty.task.context.LogContext;
import com.tty.task.dao.CrawlingDao;
import com.tty.task.dao.impl.CrawlingDaoImpl;
import com.tty.task.ent.CompetitionEnt;
import com.tty.task.ent.crawling.jcgw.WdlEnt;
import com.tty.task.service.CompetitionCrawlingService;
import com.tty.task.utils.AnalysisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Int;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author: dukeqiang
 * @Date: 2020/10/12 17:57
 */
@Service("competitionCrawlingService")
public class CompetitionCrawlingServiceImpl implements CompetitionCrawlingService {
    protected final Logger logger = LoggerFactory.getLogger(CompetitionCrawlingServiceImpl.class);

    @Autowired
    private CrawlingDao crawlingDao;

    @Override
    public void crawlingWDLInfo(String traceId,String source) {
        try {
            String wdlResult = "";
            Long time = new Date().getTime();
            if (source.equals(CrawlingSourceContext.JCGW_SOURCE)) {
                List<WdlEnt> wdlEntList = new ArrayList<WdlEnt>();
                wdlResult = HttpUtils.sendGet(CrawlingUrlContext.JCGW_WDL_URL,"",3000,3000);

                JSONObject data = (JSONObject) JSONObject.parseObject(wdlResult).get("data");
                Set<String> keys = data.keySet();
                keys.stream().forEach(kes->{
                    WdlEnt wdlEnt = JSON.parseObject(JSONObject.toJSONString(data.get(kes)),WdlEnt.class);
                    wdlEntList.add(wdlEnt);
                });

                wdlEntList.stream().forEach(wdlEnt -> {
                    JczqDataDTO jczqDataDTO = new JczqDataDTO();
                    jczqDataDTO.setIssueName(wdlEnt.getB_date().replace("-",""));
                    jczqDataDTO.setIssueMatchName(jczqDataDTO.getIssueName() + AnalysisUtils.analysisJCGWMatchNo(wdlEnt.getNum()));
                    jczqDataDTO.setWeekday(AnalysisUtils.analysisJCGWWeekDay(wdlEnt.getNum()));
                    jczqDataDTO.setWeekdayName(AnalysisUtils.analysisJCGWWeekName(wdlEnt.getNum()));
                    jczqDataDTO.setMatchNo(AnalysisUtils.analysisJCGWMatchNo(wdlEnt.getNum()));
                    jczqDataDTO.setHostTeam(wdlEnt.getH_cn());
                    jczqDataDTO.setHostTeamId(Integer.valueOf(wdlEnt.getH_id()));
                    jczqDataDTO.setVisitTeam(wdlEnt.getA_cn());
                    jczqDataDTO.setVisitTeamId(Integer.valueOf(wdlEnt.getA_id()));
                    jczqDataDTO.setMatchStartTime(AnalysisUtils.analysisJCGWMatchStartTime(wdlEnt.getDate(),wdlEnt.getTime()));
//                    jczqDataDTO.setBuyEndTime(); //todo:比赛销售截止日期
                    jczqDataDTO.setRq(Integer.valueOf(wdlEnt.getHhad().getFixedodds()));
//                    jczqDataDTO.setEuroOdds();
                    jczqDataDTO.setSpfSp(wdlEnt.getHad().getA() + "," + wdlEnt.getHad().getD() + "," + wdlEnt.getHad().getH());
                    jczqDataDTO.setRqspfSp(wdlEnt.getHhad().getA() + "," + wdlEnt.getHhad().getD() + "," + wdlEnt.getHhad().getH());
                    jczqDataDTO.setMatchId(Integer.valueOf(wdlEnt.getId()));
                    jczqDataDTO.setTournamentId(Integer.valueOf(wdlEnt.getL_id()));
                    jczqDataDTO.setTournamentName(wdlEnt.getL_cn_abbr());


                    crawlingDao.saveCompetitionInfo("123",jczqDataDTO);
                });
            } else if (source.equals(CrawlingSourceContext.JCGW_SOURCE)) {

            } else if (source.equals(CrawlingSourceContext.JCGW_SOURCE)) {

            } else {

            }
        } catch (Exception e) {
            logger.error("{}:胜平负玩法信息异常,e:{},traceId:{}", LogContext.CRAWLING_LOG_PRE, LogExceptionStackTrace.erroStackTrace(e),traceId);
        }
    }

    @Override
    public void crawlingLetBallWDLInfo(String traceId, String source) {

    }

    @Override
    public void crawlingCrsWDLInfo(String traceId, String source) {

    }

    @Override
    public void crawlingGoalsWDLInfo(String traceId, String source) {

    }

    @Override
    public void crawlingHafuWDLInfo(String traceId, String source) {

    }


    public static void main(String[] args) {
        new CompetitionCrawlingServiceImpl().crawlingWDLInfo("123",CrawlingSourceContext.JCGW_SOURCE);
    }
}


