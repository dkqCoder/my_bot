package com.tty.user.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.pojo.UserIntegralRecords;
import com.tty.user.model.web.WebIntegralUseListModel;
import com.tty.user.model.web.WebUserInfoModel;

import java.util.List;
import java.util.Map;

/**
 * @author LiuJin
 */
public interface WebUserBaseInfoDao {

    public void saveUserBaseInfo(UserBaseInfoENT ent);

    public void updateUserBaseInfo(UserBaseInfoENT ent);

    public void deleteUserBaseInfo(UserBaseInfoENT ent);

    public void saveOrUpdateUserBaseInfo(UserBaseInfoENT ent);

    public List<Map<String, Object>> getUserBaseInfoListByCondition(Integer start, Integer limit, JSONObject search);

    public Long getUserBaseInfoListCountByCondition(JSONObject search);

    List<WebUserInfoModel> getUserInfoList(Integer start, Integer limit, JSONObject search);

    Long getUserInfoCount(JSONObject search);

    List<UserIntegralRecords> getUserIntegralDetails(Integer start, Integer limit, JSONObject search);

    Long getUserIntegralDetailsCount(JSONObject search);

    List<WebIntegralUseListModel> getIntegralUseList(Integer start, Integer limit, JSONObject search);

    Long getIntegralUseListCount(JSONObject search);

    List<Map<String, Object>> getUserLevelAndGrowup(Long userId);

}
