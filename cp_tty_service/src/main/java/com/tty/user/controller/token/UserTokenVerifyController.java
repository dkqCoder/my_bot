package com.tty.user.controller.token;


import com.jdd.fm.core.model.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenlongfei
 * @date 2017-03-13
 * @description 验证用户token
 * code=0 验证通过
 * code=-2 验证不通过
 */
@RestController
@RequestMapping("/user/protected/userToken")
public class UserTokenVerifyController {
    private final Logger logger = LoggerFactory.getLogger(UserTokenVerifyController.class);

    @Autowired
    private UserTokenVerifyService userTokenVerifyService;

    @RequestMapping(value = "/verifyToken", method = RequestMethod.POST)
    public ResultModel verifyToken(String token, String uuid) {
        return userTokenVerifyService.verifyToken(token, uuid);
    }


}
