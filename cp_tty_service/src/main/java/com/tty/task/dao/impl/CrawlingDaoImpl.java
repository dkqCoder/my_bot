package com.tty.task.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.tty.data.dto.JczqDataDTO;
import com.tty.task.context.LogContext;
import com.tty.task.ent.CompetitionEnt;
import com.tty.task.utils.Constants;
import com.tty.task.utils.JdbcUtil;
import com.tty.task.dao.CrawlingDao;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: dukeqiang
 * @Date: 2020/10/13 18:29
 */
@Service("crawlingDao")
public class CrawlingDaoImpl extends BaseDao implements CrawlingDao {
    protected final Logger logger = LoggerFactory.getLogger(CrawlingDaoImpl.class);
    private static JdbcUtil competitionJdbc = new JdbcUtil(Constants.jdbcProperties());

    @Override
    @Transactional
    @DataSource(name = "dataDataSourceWrite")
    public void saveWDLCompetitionInfo(String traceId, JczqDataDTO jczqDataDTO) {
        try {
            StringBuilder sb = new StringBuilder("insert into basedata_match_jczq " +
                    " (s_issue_name,s_issue_match_name,n_weekday,s_weekday_name,s_match_no,s_host_team,n_host_team_id,s_visit_team,n_visit_team_id,d_match_start_time,d_buy_end_time,n_rq,s_bg_color,s_euro_odds,s_full_score,s_half_score,s_spf_sp,s_rqspf_sp,s_jqs_sp,s_cbf_sp,s_bqc_sp,s_match_result,n_edit_status,n_audit_status,n_match_id,n_tournament_id,s_tournament_name) values " +
                    " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update " +
                    " s_spf_sp=?,s_rqspf_sp=? " +
                    "" );
            SQLQuery query = getSQLQuery(sb.toString());
            query.setParameter(0,jczqDataDTO.getIssueName());
            query.setParameter(1,jczqDataDTO.getIssueMatchName());
            query.setParameter(2,jczqDataDTO.getWeekday());
            query.setParameter(3,jczqDataDTO.getWeekdayName());
            query.setParameter(4,jczqDataDTO.getMatchNo());
            query.setParameter(5,jczqDataDTO.getHostTeam());
            query.setParameter(6,jczqDataDTO.getHostTeamId());
            query.setParameter(7,jczqDataDTO.getVisitTeam());
            query.setParameter(8,jczqDataDTO.getVisitTeamId());
            query.setParameter(9,jczqDataDTO.getMatchStartTime());
            query.setParameter(10,jczqDataDTO.getBuyEndTime());
            query.setParameter(11,jczqDataDTO.getRq());
            query.setParameter(12,jczqDataDTO.getBgColor());
            query.setParameter(13,jczqDataDTO.getEuroOdds());
            query.setParameter(14,jczqDataDTO.getFullScore());
            query.setParameter(15,jczqDataDTO.getHalfScore());
            query.setParameter(16,jczqDataDTO.getSpfSp());
            query.setParameter(17,jczqDataDTO.getRqspfSp());
            query.setParameter(18,jczqDataDTO.getJqsSp());
            query.setParameter(19,jczqDataDTO.getCbfSp());
            query.setParameter(20,jczqDataDTO.getBqcSp());
            query.setParameter(21,jczqDataDTO.getMatchResult());
            query.setParameter(22,jczqDataDTO.getEditStatus());
            query.setParameter(23,jczqDataDTO.getAuditStatus());
            query.setParameter(24,jczqDataDTO.getMatchId());
            query.setParameter(25,jczqDataDTO.getTournamentId());
            query.setParameter(26,jczqDataDTO.getTournamentName());


            query.setParameter(27,jczqDataDTO.getSpfSp());
            query.setParameter(28,jczqDataDTO.getRqspfSp());


            query.executeUpdate();
        } catch (Exception e) {
            logger.error("{}:竞彩足球胜平负让球胜平负赔率保存异常,e:{},traceId:{}", LogContext.CRAWLING_LOG_PRE,LogExceptionStackTrace.erroStackTrace(e),traceId);
        }
    }

    @Override
    @Transactional
    @DataSource(name = "dataDataSourceWrite")
    public void saveCrsCompetitionInfo(String traceId, JczqDataDTO jczqDataDTO) {
        try {
            StringBuilder sb = new StringBuilder("insert into basedata_match_jczq " +
                    " (s_issue_name,s_issue_match_name,n_weekday,s_weekday_name,s_match_no,s_host_team,n_host_team_id,s_visit_team,n_visit_team_id,d_match_start_time,d_buy_end_time,s_cbf_sp) values " +
                    " (?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update " +
                    " s_cbf_sp=? " +
                    "" );
            SQLQuery query = getSQLQuery(sb.toString());
            query.setParameter(0,jczqDataDTO.getIssueName());
            query.setParameter(1,jczqDataDTO.getIssueMatchName());
            query.setParameter(2,jczqDataDTO.getWeekday());
            query.setParameter(3,jczqDataDTO.getWeekdayName());
            query.setParameter(4,jczqDataDTO.getMatchNo());
            query.setParameter(5,jczqDataDTO.getHostTeam());
            query.setParameter(6,jczqDataDTO.getHostTeamId());
            query.setParameter(7,jczqDataDTO.getVisitTeam());
            query.setParameter(8,jczqDataDTO.getVisitTeamId());
            query.setParameter(9,jczqDataDTO.getMatchStartTime());
            query.setParameter(10,jczqDataDTO.getBuyEndTime());
            query.setParameter(11,jczqDataDTO.getCbfSp());

            query.setParameter(12,jczqDataDTO.getCbfSp());

            query.executeUpdate();
        } catch (Exception e) {
            logger.error("{}:竞彩足球比分赔率信息保存异常,e:{},traceId:{}", LogContext.CRAWLING_LOG_PRE,LogExceptionStackTrace.erroStackTrace(e),traceId);
        }
    }

    @Override
    @Transactional
    @DataSource(name = "dataDataSourceWrite")
    public void saveGoalsCompetitionInfo(String traceId, JczqDataDTO jczqDataDTO) {
        try {
            StringBuilder sb = new StringBuilder("insert into basedata_match_jczq " +
                    " (s_issue_name,s_issue_match_name,n_weekday,s_weekday_name,s_match_no,s_host_team,n_host_team_id,s_visit_team,n_visit_team_id,d_match_start_time,d_buy_end_time,s_jqs_sp) values " +
                    " (?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update " +
                    " s_jqs_sp=? " +
                    "" );
            SQLQuery query = getSQLQuery(sb.toString());
            query.setParameter(0,jczqDataDTO.getIssueName());
            query.setParameter(1,jczqDataDTO.getIssueMatchName());
            query.setParameter(2,jczqDataDTO.getWeekday());
            query.setParameter(3,jczqDataDTO.getWeekdayName());
            query.setParameter(4,jczqDataDTO.getMatchNo());
            query.setParameter(5,jczqDataDTO.getHostTeam());
            query.setParameter(6,jczqDataDTO.getHostTeamId());
            query.setParameter(7,jczqDataDTO.getVisitTeam());
            query.setParameter(8,jczqDataDTO.getVisitTeamId());
            query.setParameter(9,jczqDataDTO.getMatchStartTime());
            query.setParameter(10,jczqDataDTO.getBuyEndTime());
            query.setParameter(11,jczqDataDTO.getJqsSp());

            query.setParameter(12,jczqDataDTO.getJqsSp());

            query.executeUpdate();
        } catch (Exception e) {
            logger.error("{}:竞彩足球进球数赔率信息保存异常,e:{},traceId:{}", LogContext.CRAWLING_LOG_PRE,LogExceptionStackTrace.erroStackTrace(e),traceId);
        }
    }

    @Override
    @Transactional
    @DataSource(name = "dataDataSourceWrite")
    public void saveHafuCompetitionInfo(String traceId, JczqDataDTO jczqDataDTO) {
        try {
            StringBuilder sb = new StringBuilder("insert into basedata_match_jczq " +
                    " (s_issue_name,s_issue_match_name,n_weekday,s_weekday_name,s_match_no,s_host_team,n_host_team_id,s_visit_team,n_visit_team_id,d_match_start_time,d_buy_end_time,s_bqc_sp) values " +
                    " (?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update " +
                    " s_bqc_sp=? " +
                    "" );
            SQLQuery query = getSQLQuery(sb.toString());
            query.setParameter(0,jczqDataDTO.getIssueName());
            query.setParameter(1,jczqDataDTO.getIssueMatchName());
            query.setParameter(2,jczqDataDTO.getWeekday());
            query.setParameter(3,jczqDataDTO.getWeekdayName());
            query.setParameter(4,jczqDataDTO.getMatchNo());
            query.setParameter(5,jczqDataDTO.getHostTeam());
            query.setParameter(6,jczqDataDTO.getHostTeamId());
            query.setParameter(7,jczqDataDTO.getVisitTeam());
            query.setParameter(8,jczqDataDTO.getVisitTeamId());
            query.setParameter(9,jczqDataDTO.getMatchStartTime());
            query.setParameter(10,jczqDataDTO.getBuyEndTime());
            query.setParameter(11,jczqDataDTO.getBqcSp());

            query.setParameter(12,jczqDataDTO.getBqcSp());

            query.executeUpdate();
        } catch (Exception e) {
            logger.error("{}:竞彩足球半全场赔率信息保存异常,e:{},traceId:{}", LogContext.CRAWLING_LOG_PRE,LogExceptionStackTrace.erroStackTrace(e),traceId);
        }
    }
}
