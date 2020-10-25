package com.tty.user.dao.impl;

import com.tty.user.dto.UserMasterInfoDTO;
import org.springframework.stereotype.Repository;
import com.jdd.fm.core.db.BaseDao;
import com.tty.user.dao.entity.UserMasterInfoENT;
import com.tty.user.dao.UserMasterInfoDao;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.utils.WhereUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuxinhai
 */
@Repository("userMasterInfoDao")
public class UserMasterInfoDaoImpl extends BaseDao<UserMasterInfoENT> implements UserMasterInfoDao {

    public void saveUserMasterInfo(UserMasterInfoENT ent) {
        save(ent);
    }

    public void updateUserMasterInfo(UserMasterInfoENT ent) {
        update(ent);
    }

    public void deleteUserMasterInfo(UserMasterInfoENT ent) {
        delete(ent);
    }

    public void saveOrUpdateUserMasterInfo(UserMasterInfoENT ent) {
        saveOrUpdate(ent);
    }


    //    =========================admin==================================
    @Override
    public List<UserMasterInfoENT> listUserMasterInfo(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserMasterInfoENT where 1=1 ")
                .andEq("name", data.getString("name"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listUserMasterInfoCount(JSONObject data) {
        String hql = "select count(id) from UserMasterInfoENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("name", data.getString("name"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

    @Override
    public List<UserMasterInfoDTO> userMasterInfoByNickName(String nickName, Integer pageNum, Integer pageSize) {
        WhereUtils where = WhereUtils.ins("from UserMasterInfoENT where 1=1 ")
                .contains("nickName", nickName);
        List<UserMasterInfoENT> list = findPageByListParam(where.getAllSql(), pageNum, pageSize, where.getParms());
        List<UserMasterInfoDTO> voList = new ArrayList<>();
        UserMasterInfoDTO vo;
        for (UserMasterInfoENT ent : list) {
            vo = new UserMasterInfoDTO();
            vo.setNickName(ent.getNickName());
            vo.setUserId(ent.getUserId());
            vo.setUserFace(ent.getUserFace());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public Long userMasterInfoCountByNickName(String nickName) {
        String hql = "select count(id) from UserMasterInfoENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .contains("nickName", nickName);
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

}
