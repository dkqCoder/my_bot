package com.tty.common.controller;

import com.jdd.fm.core.context.Constants;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.TransferAesEncrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sc", method = RequestMethod.POST)
public class ScController {

    /**
     * 请求报文加密接口，返回加密后的报文，用于调用action接口，生产环境不可暴露该接口
     * 请求报文明文示例（body内容根据实际情况调整）
     * {
     *     "body": "{\"222\":232}",
     *     "header": {
     *         "action": "12001",
     *         "chooseType": 0,
     *         "cmdID": "app_zz",
     *         "cmdName": "app_zz",
     *         "ip": "10.22.3.5",
     *         "lat": 0,
     *         "lng": 0,
     *         "phoneName": "iphone",
     *         "platformCode": "android",
     *         "platformVersion": "1.0",
     *         "token": "22343",
     *         "uuid": "dsfssfds"
     *     }
     * }
     *
     * **/
    @RequestMapping(value = "/aesEncrypt")
    public ResultModel testMethod(@RequestBody String str) {
        String encryptStr = null;
        try {
            encryptStr = TransferAesEncrypt.aesEncrypt(str, Constants.API_REQUEST_AES_KEY, "utf-8");
        } catch (Exception e) {

        }
        ResultModel rm = new ResultModel();
        rm.setData(encryptStr);
        return rm;
    }
}
