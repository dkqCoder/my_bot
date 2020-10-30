package com.tty.data.dao;

import com.tty.data.context.BasedataLotteryTagEnum;
import com.tty.data.dao.entity.BasedataIssueENT;
import java.util.List;

/**
 **/
public interface BasedataIssueDao {

    List<BasedataIssueENT> getBasedataIssue(Integer lotteryId, BasedataLotteryTagEnum tagEnum);

}