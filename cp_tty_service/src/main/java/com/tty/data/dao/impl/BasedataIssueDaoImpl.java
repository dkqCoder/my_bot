package com.tty.data.dao.impl;

import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.tty.data.context.BasedataLotteryTagEnum;
import com.tty.data.dao.BasedataIssueDao;
import com.tty.data.dao.entity.BasedataIssueENT;
import java.util.List;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuxinhai
 */
@Repository("basedataIssueDao")
public class BasedataIssueDaoImpl extends BaseDao<BasedataIssueENT> implements BasedataIssueDao {

    /**
     * @Author shenwei
     * @Date 2017/2/13 10:10
     * @Description 分彩种获取期次信息
     */
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional
    public List<BasedataIssueENT> getBasedataIssue(Integer lotteryId, BasedataLotteryTagEnum tagEnum) {
        String sql = "";
        //高频彩
        if (BasedataLotteryTagEnum.GPC.equals(tagEnum)) {
            sql = "(select n_issue_id as issueId,n_lottery_id as lotteryId,s_issue_name as issueName,IFNULL(s_win_number,'') as winNumber,IFNULL(s_open_result,'') as openResult,n_pass_status as passStatus," +
                "n_chase_execute_status as chaseExecuteStatus,n_package_status as packageStatus,n_open_status as openStatus,d_end_time as endTime,d_start_time as startTime,d_update_time as updateTime from basedata_issue" +
                " where n_lottery_id=:lotteryId and d_end_time>=date_format(DATE_ADD(NOW(),interval -3 day),'%Y-%m-%d') and d_end_time<=date_format(DATE_ADD(NOW(),interval 1 day),'%Y-%m-%d') order by s_issue_name)" +
                " union " +
                "(select n_issue_id as issueId,n_lottery_id as lotteryId,s_issue_name as issueName,IFNULL(s_win_number,'') as winNumber,IFNULL(s_open_result,'') as openResult,n_pass_status as passStatus," +
                "n_chase_execute_status as chaseExecuteStatus,n_package_status as packageStatus,n_open_status as openStatus,d_end_time as endTime,d_start_time as startTime,d_update_time as updateTime from basedata_issue" +
                " where n_lottery_id=:lotteryId and now() < d_end_time order by s_issue_name limit 500)";
        } else if (BasedataLotteryTagEnum.JCZC.equals(tagEnum)) {

            sql = "(select n_issue_id as issueId,n_lottery_id as lotteryId,s_issue_name as issueName,IFNULL(s_win_number,'') as winNumber,IFNULL(s_open_result,'') as openResult,n_pass_status as passStatus," +
                "n_chase_execute_status as chaseExecuteStatus,n_package_status as packageStatus,n_open_status as openStatus,d_end_time as endTime,d_start_time as startTime,d_update_time as updateTime from basedata_issue" +
                " where now() < d_end_time and n_lottery_id=:lotteryId order by s_issue_name limit 10)" +
                " union " +
                "(select n_issue_id as issueId,n_lottery_id as lotteryId,s_issue_name as issueName,IFNULL(s_win_number,'') as winNumber,IFNULL(s_open_result,'') as openResult,n_pass_status as passStatus," +
                "n_chase_execute_status as chaseExecuteStatus,n_package_status as packageStatus,n_open_status as openStatus,d_end_time as endTime,d_start_time as startTime,d_update_time as updateTime from basedata_issue" +
                " where now() > d_end_time and n_lottery_id=:lotteryId order by s_issue_name limit 10)";
        } else if (BasedataLotteryTagEnum.JCC.equals(tagEnum)) {
            sql = "(select n_issue_id as issueId,n_lottery_id as lotteryId,s_issue_name as issueName,IFNULL(s_win_number,'') as winNumber,IFNULL(s_open_result,'') as openResult,n_pass_status as passStatus," +
                "n_chase_execute_status as chaseExecuteStatus,n_package_status as packageStatus,n_open_status as openStatus,d_end_time as endTime,d_start_time as startTime,d_update_time as updateTime from basedata_issue" +
                " where now()>d_end_time and s_win_number is not null and  s_win_number <> '' and n_lottery_id=:lotteryId order by s_issue_name desc limit 100)" +
                " union " +
                "(select n_issue_id as issueId,n_lottery_id as lotteryId,s_issue_name as issueName,IFNULL(s_win_number,'') as winNumber,IFNULL(s_open_result,'') as openResult,n_pass_status as passStatus," +
                "n_chase_execute_status as chaseExecuteStatus,n_package_status as packageStatus,n_open_status as openStatus,d_end_time as endTime,d_start_time as startTime,d_update_time as updateTime from basedata_issue" +
                " where now()<d_end_time and n_lottery_id=:lotteryId order by s_issue_name limit 50)";
        } else {
            sql = "(select n_issue_id as issueId,n_lottery_id as lotteryId,s_issue_name as issueName,IFNULL(s_win_number,'') as winNumber,IFNULL(s_open_result,'') as openResult,n_pass_status as passStatus," +
                "n_chase_execute_status as chaseExecuteStatus,n_package_status as packageStatus,n_open_status as openStatus,d_end_time as endTime,d_start_time as startTime,d_update_time as updateTime from basedata_issue" +
                " where n_lottery_id=:lotteryId and now() > d_end_time order by s_issue_name desc limit 500)" +
                " union " +
                "(select n_issue_id as issueId,n_lottery_id as lotteryId,s_issue_name as issueName,IFNULL(s_win_number,'') as winNumber,IFNULL(s_open_result,'') as openResult,n_pass_status as passStatus," +
                "n_chase_execute_status as chaseExecuteStatus,n_package_status as packageStatus,n_open_status as openStatus,d_end_time as endTime,d_start_time as startTime,d_update_time as updateTime from basedata_issue" +
                " where now()<d_end_time and n_lottery_id=:lotteryId order by s_issue_name limit 50)";
        }
        //子查询后最终查询
        //        sql = " select n_issue_id as issueId,n_lottery_id as lotteryId,s_issue_name as issueName,IFNULL(s_win_number,'') as winNumber,IFNULL(s_open_result,'') as openResult,n_pass_status as passStatus," +
        //                "n_chase_execute_status as chaseExecuteStatus,n_package_status as packageStatus,d_end_time as endTime,d_start_time as startTime,d_update_time as updateTime from (" + sql + ") order by issueName desc";
        List<BasedataIssueENT> list = getSQLQuery(sql)
            .addScalar("issueId", StandardBasicTypes.INTEGER)
            .addScalar("lotteryId", StandardBasicTypes.INTEGER)
            .addScalar("issueName", StandardBasicTypes.STRING)
            .addScalar("winNumber", StandardBasicTypes.STRING)
            .addScalar("openResult", StandardBasicTypes.STRING)
            .addScalar("passStatus", StandardBasicTypes.INTEGER)
            .addScalar("chaseExecuteStatus", StandardBasicTypes.INTEGER)
            .addScalar("packageStatus", StandardBasicTypes.INTEGER)
            .addScalar("openStatus", StandardBasicTypes.INTEGER)
            .addScalar("endTime", StandardBasicTypes.TIMESTAMP)
            .addScalar("startTime", StandardBasicTypes.TIMESTAMP)
            .addScalar("updateTime", StandardBasicTypes.TIMESTAMP)
            .setParameter("lotteryId", lotteryId)
            .setResultTransformer(Transformers.aliasToBean(BasedataIssueENT.class))
            .list();
        return list;
    }
}
