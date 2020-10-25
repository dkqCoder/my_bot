package com.tty.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.sms.dto.MessageDTO;
import com.tty.sms.outservice.SmsOutService;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.UserBaseInfoDao;
import com.tty.user.dao.UserIdcardResetDao;
import com.tty.user.dao.UserInfoDao;
import com.tty.user.dao.entity.UserIdcardResetENT;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.service.UserIdcardResetService;
import com.tty.user.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service("userIdcardResetService")
public class UserIdcardResetServiceImpl implements UserIdcardResetService {
    @Autowired
    private UserIdcardResetDao userIdcardResetDao;

    @Autowired
    private UserBaseInfoDao userBaseInfoDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    SmsOutService smsOutService;

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listUserIdcardReset(JSONObject jsonParm) {
        ExtModel result = new ExtModel();
        result.setData(userIdcardResetDao.listUserIdcardReset(jsonParm));
        result.setTotal(userIdcardResetDao.getUserIdcardResetCount(jsonParm));
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void saveUserIdcardReset(UserIdcardResetENT userIdcardReset) {
        userIdcardResetDao.saveUserIdcardReset(userIdcardReset);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void updateUserIdcardReset(UserIdcardResetENT userIdcardReset) {
        userIdcardResetDao.updateUserIdcardReset(userIdcardReset);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public ExtModel auditUserIdcardReset(Long id, Long userId, String idcardNumber, String realName, int status, String remark) {
        ExtModel em = new ExtModel();

        userIdcardResetDao.auditUserIdcardReset(id, status, remark);
        //待审核
        if (status == UserContext.USER_IDCARD_STATUS_UNCHECK) {
            return em;
        }
        //拒绝
        if (status == UserContext.USER_IDCARD_STATUS_REFUSE) {
            String key = String.format(UserRedisKeys.USER_RESET_IDCARDNUM_UNCHECK, userId.toString());
            String resetIdc = userRedis.get(key);
            if (StringUtils.isNotBlank(resetIdc)) {
                UserIdcardResetENT userIdcardResetENT = GfJsonUtil.parseObject(resetIdc, UserIdcardResetENT.class);
                userIdcardResetENT.setStatus(status);
                userRedis.set(key, GfJsonUtil.toJSONString(userIdcardResetENT));
            }


            doSendRefuseMsg(userId, UserContext.REFUSE_MESSAGE);

            em.setSuccess(true);
            return em;
        }

        //审核通过
        if (status == UserContext.USER_IDCARD_STATUS_PASS) {
            //同步到baseinfo
            userBaseInfoDao.updateIdcardnoAndRealName(userId, idcardNumber, realName);

            userInfoDao.emptyUserInfoRedisCache(userId);
            String key = String.format(UserRedisKeys.USER_RESET_IDCARDNUM_UNCHECK, userId.toString());
            String resetIdc = userRedis.get(key);
            if (StringUtils.isNotBlank(resetIdc)) {
                UserIdcardResetENT userIdcardResetENT = GfJsonUtil.parseObject(resetIdc, UserIdcardResetENT.class);
                userIdcardResetENT.setStatus(status);
                userIdcardResetENT.setIdcardNumber(idcardNumber);
                userIdcardResetENT.setUpdateTime(new Date());
                userIdcardResetENT.setRealName(realName);
                userRedis.set(key, GfJsonUtil.toJSONString(userIdcardResetENT));
            }

            doSendRefuseMsg(userId, UserContext.ACCEPT_MESSAGE);
        }
        return em;
    }

    private void doSendRefuseMsg(Long userId, String body) {
        UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(String.valueOf(userId));
        if (userInfoENT == null) {
            return;
        }
        if (StringUtils.isEmpty(userInfoENT.getMobileNumber())) {
            return;
        }
        MessageDTO messageDTO = new MessageDTO(userInfoENT.getMobileNumber(), body, 1, "tty_user");
        smsOutService.sendMsg(messageDTO);
    }
}
