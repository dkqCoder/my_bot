package com.tty.data.service;

import java.util.List;

/**
 * Created by rxu on 2017/11/1.
 */
public interface BaseDataRetrieveAddiMatchService {

     List<String> getSeparateMatchResultByIssueId(String lotteryId, String issueId);

}
