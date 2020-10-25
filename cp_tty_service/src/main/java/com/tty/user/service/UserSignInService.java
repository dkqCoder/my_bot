package com.tty.user.service;



import com.tty.common.utils.Result;
import com.tty.user.controller.model.params.UserSignInParam;
import com.tty.user.controller.model.result.UserSignInModel;

import java.util.Date;


/**
 * @Created by shenwei on 2016/12/14.
 * @Decription 用户签到接口
 */
public interface UserSignInService {
    Result SignIn(String traceId, Long userId, UserSignInParam model) throws Exception;

    Long getContiniousSigninDays(Long userId, Date signInDate);

    Integer getCurrentMonthSinginDays(Long userId);

    Boolean isAlreadySigned(Long userId);

    UserSignInModel getUserSignInfo(Long userId);
}
