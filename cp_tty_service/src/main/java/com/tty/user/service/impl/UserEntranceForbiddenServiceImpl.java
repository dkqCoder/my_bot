package com.tty.user.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.PropertiesUtil;
import com.tty.user.common.utils.MobileUtil;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.*;
import com.tty.user.dao.entity.*;
import com.tty.user.service.UserEntranceForbiddenService;
import com.tty.user.service.UserInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service("userEntranceForbiddenService")
public class UserEntranceForbiddenServiceImpl implements UserEntranceForbiddenService {
    private static final Logger logger = LoggerFactory.getLogger(UserEntranceForbiddenServiceImpl.class);

    @Autowired
    private UserEntranceForbiddenDao userEntranceForbiddenDao;
    @Autowired
    private MobileBelongtoDao mobileBelongtoDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserMobileWhitelistDao userMobileWhitelistDao;
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;
    @Autowired
    private UserEntranceDenyTimeDao userEntranceDenyTimeDao;
    @Autowired
    private UserConfigDao userConfigDao;


    @Override
    public Integer filterEntrance(String userId, String mobile, String cmdName, String traceId) {
        //开关
        String forbiddenEntranceStatus = Optional.ofNullable(userConfigDao.getUserConfig("forbidden.entrance.province")).map(x -> x.getValue()).orElse("0");
        if (StringUtils.isNotEmpty(forbiddenEntranceStatus) && forbiddenEntranceStatus.equals("1")) {
            UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(userId);
            String needValidateMaxId = PropertiesUtil.get("forbidden.entrance.maxUserId");
            if (userInfoENT != null && userInfoENT.getUserId() < Long.valueOf(needValidateMaxId)) {
                return 1;
            }
            if (!MobileUtil.isMobileNO(mobile)) {
                return 0;
            }
            if (isInWhiteList(mobile)) {
                return 1;
            }
            String mobileSeg = mobile.substring(0, 7);
            MobileBelongtoENT mobileBelongtoENT = mobileBelongtoDao.findMobileBelong(mobileSeg);
            if (null == mobileBelongtoENT) {
                return 1;
            }
            if (!userEntranceDenyTimeDao.isInEntranceDenyTimeRegion(cmdName)) {
                return 1;
            }
            UserEntranceForbiddenENT forbidden = userEntranceForbiddenDao.getUserEntranceForbidden(cmdName, mobileBelongtoENT.getProvinceName(), mobileBelongtoENT.getCityName());
            if (null != forbidden) {
                return 0;
            }
            return 1;
        }
        return 1;
    }

    /**
     * @Author shenwei
     * @Date 2017/8/23 10:59
     * @Description 是否白名单
     */
    private Boolean isInWhiteList(String mobile) {
        return userMobileWhitelistDao.getMobileWhiteList(mobile) == null ? false : true;
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listUserEntranceForbidden(JSONObject jsonParm) {
        ExtModel result = new ExtModel();
        result.setData(userEntranceForbiddenDao.listUserEntranceForbidden(jsonParm.getInteger("page"), jsonParm.getInteger("limit"), jsonParm.getJSONObject("data")));
        result.setTotal(userEntranceForbiddenDao.listUserEntranceForbiddenCount(jsonParm.getJSONObject("data")));
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void delete(Long id) {
        UserEntranceForbiddenENT ent = userEntranceForbiddenDao.getById(id);
        if (ent != null) {
            if (StringUtils.isNotEmpty(ent.getCityName())) {
                String key = String.format(UserRedisKeys.USER_ENTRANCE_FOBIDDEN_KEY, ent.getCmdName(), ent.getProvinceName(), ent.getCityName());
                userRedis.del(key);
            } else {
                List<Map<String, String>> mobileBelongByProvince = mobileBelongtoDao.findMobileBelongByProvince(ent.getProvinceName());
                if (CollectionUtils.isNotEmpty(mobileBelongByProvince)) {
                    for (Map<String, String> map : mobileBelongByProvince) {
                        String key = String.format(UserRedisKeys.USER_ENTRANCE_FOBIDDEN_KEY, ent.getCmdName(), map.get("provinceName"), map.get("cityName"));
                        userRedis.del(key);
                    }
                }
            }
            userEntranceForbiddenDao.delUserEntranceForbidden(id);
        }
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public int saveUserEntranceForbidden(UserEntranceForbiddenENT ent) {
        int reultCode = 0;
        String key = String.format(UserRedisKeys.USER_ENTRANCE_FOBIDDEN_KEY, ent.getCmdName(), ent.getProvinceName(), ent.getCityName());
        UserEntranceForbiddenENT userEntranceForbiddenENT = userEntranceForbiddenDao.getUserEntranceForbiddenByProvince(ent.getCmdName(), ent.getProvinceName());
        if (userEntranceForbiddenENT != null) {
            reultCode = -1;
            return reultCode;
        }
        if (StringUtils.isEmpty(ent.getCityName())) {
            ent.setCityName(null);
            if (!userEntranceForbiddenDao.isExitstEntranceForbiddenByProvince(ent.getCmdName(), ent.getProvinceName())) {
                userEntranceForbiddenDao.saveUserEntranceForbidden(ent);
                userRedis.del(key);
                return reultCode;
            }
        } else {
            userEntranceForbiddenENT = userEntranceForbiddenDao.getEntranceForbidden(ent.getCmdName(), ent.getProvinceName(), ent.getCityName());
            if (userEntranceForbiddenENT == null) {
                userEntranceForbiddenDao.saveUserEntranceForbidden(ent);
                userRedis.del(key);
                return reultCode;
            }
        }
        reultCode = -1;
        return reultCode;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void updateUserEntranceForbidden(UserEntranceForbiddenENT ent) {
        userEntranceForbiddenDao.updateUserEntranceForbidden(ent);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public List<Map<String, String>> listCmdName() {
        return userEntranceForbiddenDao.listCmdName();
    }

    //MobileWhitelist
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void deleteMobileWhitelist(String mobile) {
        userMobileWhitelistDao.deleteMobileWhitelist(mobile);
    }


    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void deleteMobileWhitelist(Long id) {
        userMobileWhitelistDao.deleteMobileWhitelist(id);
    }


    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void saveUserMobileWhitelist(UserMobileWhitelistENT ent) {
        userMobileWhitelistDao.saveUserMobileWhitelist(ent);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void batchSaveUserMobileWhitelist(String mobiles) {
        List<String> mobileArr = Arrays.asList(mobiles.split("\n"));
        if (CollectionUtils.isNotEmpty(mobileArr)) {
            for (String item : mobileArr) {
                UserMobileWhitelistENT ent = new UserMobileWhitelistENT();
                ent.setMobile(item);
                userMobileWhitelistDao.saveUserMobileWhitelist(ent);
            }
        }
    }


    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void batchDelUserMobileWhitelist(String ids) {
        List<String> mobileArr = Arrays.asList(ids.split("\n"));
        if (CollectionUtils.isNotEmpty(mobileArr)) {
            for (String item : mobileArr) {
                userMobileWhitelistDao.deleteMobileWhitelist(item);
            }
        }
    }


    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listMobileWhitelist(JSONObject jsonParm) {
        ExtModel result = new ExtModel();
        result.setData(userMobileWhitelistDao.listMobileWhitelist(jsonParm.getInteger("page"), jsonParm.getInteger("limit"), jsonParm.getJSONObject("data")));
        result.setTotal(userMobileWhitelistDao.listMobileWhitelistCount(jsonParm.getJSONObject("data")));
        return result;
    }

    //EntranceDenyTime

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void saveEntranceDenyTimelist(UserEntranceDenyTimeENT ent) {
        userEntranceDenyTimeDao.saveOrUpdateUserEntranceDenyTime(ent);
    }


    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listEntranceDenyTime(JSONObject jsonParm) {
        ExtModel result = new ExtModel();
        result.setData(userEntranceDenyTimeDao.listUserEntranceDenyTime(jsonParm.getInteger("page"), jsonParm.getInteger("limit"), jsonParm.getJSONObject("data")));
        result.setTotal(userEntranceDenyTimeDao.listUserEntranceDenyTimeCount(jsonParm.getJSONObject("data")));
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void deleteEntranceDenyTime(Long id) {
        userEntranceDenyTimeDao.deleteEntranceDenyTime(id);
    }

}
