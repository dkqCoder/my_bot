package com.tty.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ExtModel;
import com.tty.common.utils.Result;
import com.tty.user.dao.UserMasterInfoDao;
import com.tty.user.dao.entity.UserMasterInfoENT;
import com.tty.user.dto.SearchMasterDTO;
import com.tty.user.dto.UserMasterInfoDTO;
import com.tty.user.service.UserMasterInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service("userMasterInfoService")
public class UserMasterInfoServiceImpl implements UserMasterInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserMasterInfoServiceImpl.class);

    @Autowired
    private UserMasterInfoDao userMasterInfoDao;

    @Override
    public Result searchMasterByNickName(String nickName, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        SearchMasterDTO dto = new SearchMasterDTO();
        List<UserMasterInfoDTO> masterInfoList = userMasterInfoDao.userMasterInfoByNickName(nickName, pageNum, pageSize);
        String traceId = UUID.randomUUID().toString();
        try {
            //过滤大神黑名单人员
            //List<String> masterBlackUserIds = masterDubboService.getMasterBlackUserIds(traceId);
            List<String> masterBlackUserIds = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(masterInfoList) && CollectionUtils.isNotEmpty(masterBlackUserIds)) {
                Iterator iterator = masterInfoList.iterator();
                while (iterator.hasNext()) {
                    UserMasterInfoDTO infoDTO = (UserMasterInfoDTO) iterator.next();
                    if (infoDTO != null && infoDTO.getUserId() != null
                            && masterBlackUserIds.contains(String.valueOf(infoDTO.getUserId()))) {
                        iterator.remove();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("搜索大神时过滤大神黑名单异常,traceId:{},errorStack:{}", traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        Long count = userMasterInfoDao.userMasterInfoCountByNickName(nickName);
        dto.setList(masterInfoList);
        dto.setTotalNum(count);
        dto.setTotalPage((count + pageSize - 1) / pageSize);
        result.setData(dto);
        result.setCode(Result.SUCCESS);
        if (masterInfoList.size() == 0) {
            result.setMsg("暂无此大神，请重新输入！");
        }
        return result;
    }

    //    =========================admin==================================
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listUserMasterInfo(JSONObject jsonParm, ExtModel result) {
        result.setData(userMasterInfoDao.listUserMasterInfo(jsonParm.getInteger("page"), jsonParm.getInteger("limit"), jsonParm.getJSONObject("data")));
        result.setTotal(userMasterInfoDao.listUserMasterInfoCount(jsonParm.getJSONObject("data")));
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void saveUserMasterInfo(UserMasterInfoENT userMasterInfo) {
        userMasterInfoDao.saveUserMasterInfo(userMasterInfo);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void updateUserMasterInfo(UserMasterInfoENT userMasterInfo) {
        userMasterInfoDao.updateUserMasterInfo(userMasterInfo);
    }


}
