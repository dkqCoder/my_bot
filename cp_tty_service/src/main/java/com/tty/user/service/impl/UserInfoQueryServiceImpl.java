package com.tty.user.service.impl;

import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.model.ResultModel;
import com.tty.user.controller.model.params.UserListParam;
import com.tty.user.dao.UserInfoQueryDao;
import com.tty.user.dto.UserBaseInfoDTO;
import com.tty.user.service.UserInfoQueryService;
import com.tty.user.dto.UcUserListResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by linian on 2017/10/20.
 */
@Service("userInfoQueryService")
public class UserInfoQueryServiceImpl implements UserInfoQueryService {
    @Autowired
    private UserInfoQueryDao userInfoQueryDao;


    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ResultModel queryUserList(UserListParam userListParam) {
        ResultModel result = new ResultModel();
        List<UserBaseInfoDTO> userBaseInfoDTOList = userInfoQueryDao.listUserBaseInfo(userListParam, true);
        UcUserListResultDTO ucUserListResult = new UcUserListResultDTO();
        if (userBaseInfoDTOList != null && userBaseInfoDTOList.size() > 0) {
            Long userBaseInfoCount = userInfoQueryDao.listUserBaseInfoCount(userListParam, true);
            ucUserListResult.setCount(userBaseInfoCount);
            ucUserListResult.setUserdata(userBaseInfoDTOList);
            result.setData(ucUserListResult);
        }
        return result;
    }
}
