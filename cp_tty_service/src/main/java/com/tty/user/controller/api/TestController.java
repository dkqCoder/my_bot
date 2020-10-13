package com.tty.user.controller.api;

import com.jdd.fm.core.model.ResultModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/test", method = RequestMethod.POST)
public class TestController {

    @RequestMapping(value = "/testMethod")
    public ResultModel testMethod(HttpServletRequest request) {
        ResultModel rm = new ResultModel();
        return rm;
    }
}
