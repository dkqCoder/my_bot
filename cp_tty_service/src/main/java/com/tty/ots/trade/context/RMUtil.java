package com.tty.ots.trade.context;

import com.jdd.fm.core.model.ResultModel;
/**
 * Created by liuzhenpeng on 2017/4/26.
 */
public class RMUtil {
    public static ResultModel result(int code, String msg, Object data){
        ResultModel rm = new ResultModel();
        rm.setCode(code);
        rm.setMsg(msg);
        rm.setData(data);
        return rm;
    }

    public static ResultModel error(String msg){
        return result(ResultModel.ERROR, msg, null);
    }

    public static ResultModel error(ErrorMsgEnum error){
        return result(ResultModel.ERROR, error.getErrorMsg(), null);
    }

    public static ResultModel error(String msg, Object data){
        return result(ResultModel.ERROR, msg, data);
    }

    public static ResultModel result(String msg){
        return result(ResultModel.SUCCESS, msg, null);
    }

    public static ResultModel result(String msg, Object data){
        return result(ResultModel.SUCCESS, msg, data);
    }

    public static ResultModel result(Object data){
        return result(ResultModel.SUCCESS, ResultModel.MSG_SUCCESS_DESC, data);
    }

    public static ResultModel result(){
        return result(ResultModel.SUCCESS, ResultModel.MSG_SUCCESS_DESC, null);
    }

    public static boolean isError(ResultModel rm){
        return rm.getCode() != ResultModel.SUCCESS;
    }
}
