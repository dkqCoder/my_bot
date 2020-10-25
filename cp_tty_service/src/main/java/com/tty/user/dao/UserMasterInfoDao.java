package com.tty.user.dao;

import com.tty.user.dao.entity.UserMasterInfoENT;
import com.alibaba.fastjson.JSONObject;
import com.tty.user.dto.UserMasterInfoDTO;

import java.util.List;

/**
 * @author zxh
 * @create 2017-03-06 18:02:01
 **/
public interface UserMasterInfoDao {

    public void saveUserMasterInfo(UserMasterInfoENT ent);

    public void updateUserMasterInfo(UserMasterInfoENT ent);

    public void deleteUserMasterInfo(UserMasterInfoENT ent);

    public void saveOrUpdateUserMasterInfo(UserMasterInfoENT ent);


//    =========================admin==================================

    List<UserMasterInfoENT> listUserMasterInfo(Integer page, Integer limit, JSONObject data);

    Long listUserMasterInfoCount(JSONObject data);

    List<UserMasterInfoDTO> userMasterInfoByNickName(String nickName, Integer pageNum, Integer pageSize);

    Long userMasterInfoCountByNickName(String nickName);
}