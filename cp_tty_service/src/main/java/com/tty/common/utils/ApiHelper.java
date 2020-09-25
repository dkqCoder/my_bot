package com.tty.common.utils;

import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.StringUtils;

public class ApiHelper {
    public static ResultModel mapToResultModel(ResultBO resultBO) {
        ResultModel rm = new ResultModel();
        BeanUtils.copy(resultBO, rm);
        return rm;
    }

    public static ResultBO getFailResult() {
        ResultBO resultBO = new ResultBO();
        resultBO.setCode(ResultBO.ERROR);
        resultBO.setMsg(ResultBO.MSG_ERROR_DESC);
        return resultBO;
    }

    public static ResultBO getSuccessResult() {
        ResultBO resultBO = new ResultBO();
        resultBO.setCode(ResultBO.SUCCESS);
        resultBO.setMsg(ResultBO.MSG_SUCCESS_DESC);
        return resultBO;
    }

    public static ResultBO getSuccessResult(Object data) {
        ResultBO resultBO = new ResultBO();
        resultBO.setCode(ResultBO.SUCCESS);
        resultBO.setMsg(ResultBO.MSG_SUCCESS_DESC);
        resultBO.setData(data);
        return resultBO;
    }

    public static ResultBO getFailResult(String msg) {
        if (!StringUtils.isNotBlank(msg)) {
            return getFailResult();
        }
        ResultBO resultBO = new ResultBO();
        resultBO.setCode(ResultBO.ERROR);
        resultBO.setMsg(msg);
        if (ResultBO.MSG_PURCHASE_PROHIBITION_DESC.equals(msg)) {
            resultBO.setCode(ResultBO.PURCHASE_PROHIBITION);
        }
        return resultBO;
    }

    public static ResultBO getFailResult(String msg, Object data) {
        if (!StringUtils.isNotBlank(msg)) {
            return getFailResult();
        }
        ResultBO resultBO = new ResultBO();
        resultBO.setCode(ResultBO.ERROR);
        resultBO.setMsg(msg);
        resultBO.setData(data);
        if (ResultBO.MSG_PURCHASE_PROHIBITION_DESC.equals(msg)) {
            resultBO.setCode(ResultBO.PURCHASE_PROHIBITION);
        }
        return resultBO;
    }
}
