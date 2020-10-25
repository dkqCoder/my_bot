package com.tty.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.db.ds.DataSourceContext;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.utils.StringUtils;
import com.tty.user.dao.UserConfigDao;
import com.tty.user.dao.entity.UserConfigENT;
import com.tty.user.service.UserConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by linian on 2017/8/24.
 */
@Service("userConfigService")
public class UserConfigServiceImpl implements UserConfigService {
    private static final Logger logger = LoggerFactory.getLogger(UserBusinessServiceImpl.class);

    @Autowired
    private UserConfigDao userConfigDao;

    @Override
    @Transactional(readOnly = false)
    @DataSource(name = DataSourceContext.DATA_SOURCE_READ)
    public ExtModel getUserConfigAll(JSONObject data) {
        ExtModel em = new ExtModel();
        Integer start = data.getInteger("page");
        Integer limit = data.getInteger("limit");
        em.setData(userConfigDao.listUserConfig(start, limit, data.getJSONObject("data")));
        em.setTotal(userConfigDao.listUserConfigCount(data.getJSONObject("data")));
        return em;
    }

    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional
    @Override
    public ExtModel saveUserConfig(JSONObject data) {
        ExtModel em = new ExtModel();
        UserConfigENT ent = new UserConfigENT();
        if (StringUtils.isNotBlank(data.getString("id"))) {
            ent.setId(Long.valueOf(data.getString("id")));
        } else {
            UserConfigENT userConfigENT = userConfigDao.getUserConfig(data.getString("key"));
            if (userConfigENT != null) {
                em.setSuccess(false);
                em.setData("已存在对应key的字典项");
                return em;
            }
        }
        ent.setType(data.getString("type"));
        ent.setKey(data.getString("key"));
        ent.setValue(data.getString("value"));
        ent.setDescription(data.getString("description"));
        if (StringUtils.isNotBlank(data.getString("createTime"))) {
            ent.setCreateTime(StrToDate(data.getString("createTime")));
        } else {
            ent.setCreateTime(new Date());
        }
        if (StringUtils.isNotBlank(data.getString("status"))) {
            ent.setStatus(Integer.valueOf(data.getString("status")));
        } else {
            em.setSuccess(false);
            em.setData("请选择状态");
            return em;
        }
        userConfigDao.saveOrUpdateUserConfig(ent);
        return em;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    private Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
