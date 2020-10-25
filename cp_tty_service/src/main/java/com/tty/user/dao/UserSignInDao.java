package com.tty.user.dao;

import com.tty.user.dao.pojo.UserSigninRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by shenwei on 2016/12/14.
 */
public interface UserSignInDao {
    void saveUserSign(UserSigninRecord ent);

    Long getContiniousSigninDays(Long userId, Date signInDate);

    Integer getCurrentMonthSinginDays(Long userId);

    Boolean IsAlreadySigned(Long userId);
    
    List<Long> getUserIdListForSignInPush(Integer page, Integer pageSize);
}
