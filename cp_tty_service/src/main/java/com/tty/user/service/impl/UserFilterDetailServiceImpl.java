package com.tty.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.tty.user.context.UserFilterContext;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.UserFilterDetailDao;
import com.tty.user.dao.UserInfoDao;
import com.tty.user.dao.entity.UserFilterDetailENT;
import com.tty.user.dao.entity.UserFilterENT;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.service.UserFilterDetailService;
import com.tty.user.service.UserFilterService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户禁登过滤业务类
 *
 * @Author chenpengfei {chenpengfei@jiangduoduo.com}
 * @Date 2017/10/30 17:41
 */
@Service("userFilterDetailService")
public class UserFilterDetailServiceImpl implements UserFilterDetailService {

    private final Logger logger = LoggerFactory.getLogger(UserFilterDetailServiceImpl.class);

    private final Integer QUERY_HOUR = 1;

    @Autowired
    private UserFilterDetailDao userFilterDetailDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserFilterService userFilterService;

    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;

    @Transactional(rollbackFor = RuntimeException.class)
    @DataSource
    @Override
    public void addUserFilterDetail(String code) {

        List<UserInfoENT> userList = userInfoDao.getUserInfoByDate(QUERY_HOUR);
        UserFilterENT queryUserFilterENT = new UserFilterENT();
        queryUserFilterENT.setCode(code);
        List<UserFilterENT> userFilterList = userFilterService.findUserFilterList(queryUserFilterENT);
        List<UserFilterDetailENT> rstList = filter(code, userList, userFilterList);
        List<UserFilterENT> blackList = userFilterList.stream().filter(user -> UserFilterContext.USER_FILTER_LIST_BLACK.equals(user.getType())).collect(Collectors.toList());
        rstList.forEach(userFilterDetailENT -> {
            userFilterDetailENT.setId(userRedis.incr(UserRedisKeys.USER_FILTER_DETAIL_INCR_KEY));
            userFilterDetailENT.setCreateTime(new Date());
            userFilterDetailENT.setUpdateTime(new Date());
            userFilterDetailENT.setStatus(UserFilterContext.USER_FILTER_STATUS_TRUE);
            userFilterDetailENT.setOperator(UserFilterContext.USER_FILTER_OPERATOR_SYS);
            userFilterDetailENT.setUserFilterId(blackList.get(0).getId());
            userFilterDetailENT.setCode(code);
            userFilterDetailENT.setType(UserFilterContext.USER_FILTER_LIST_BLACK);
            userFilterDetailDao.addUserFilterDetail(userFilterDetailENT);
        });


    }

    public List<UserFilterDetailENT> findUserFilterDetailList(UserFilterDetailENT userFilterDetailENT, List<Long> userFilterIds) {
        return userFilterDetailDao.findUserFilterDetailList(userFilterDetailENT, userFilterIds);
    }

    private List<UserFilterDetailENT> filter(String code, List<UserInfoENT> userList, List<UserFilterENT> userFilterList) {
        Map<String, List<UserInfoENT>> map = Maps.newHashMap();
        List<UserFilterDetailENT> rstList = Lists.newLinkedList();
        switch (code) {
            case UserFilterContext.USER_FILTER_TYPE_IP:
                map = userList.stream().filter(userInfoENT -> StringUtils.isNotBlank(userInfoENT.getIpAddress())).collect(Collectors.groupingBy(UserInfoENT::getIpAddress));
                break;
            case UserFilterContext.USER_FILTER_TYPE_UUID:
                map = userList.stream().filter(userInfoENT -> StringUtils.isNotBlank(userInfoENT.getUuid())).collect(Collectors.groupingBy(UserInfoENT::getUuid));
                break;
            default:
                break;
        }
        /**==============1.白名单过滤 2.阈值过滤 START===============* */
        List<UserFilterENT> blackList = userFilterList.stream().filter(user -> UserFilterContext.USER_FILTER_LIST_BLACK.equals(user.getType())).collect(Collectors.toList());
        List<UserFilterENT> whiteList = userFilterList.stream().filter(user -> UserFilterContext.USER_FILTER_LIST_WHITE.equals(user.getType())).collect(Collectors.toList());
        List<UserFilterDetailENT> whiteDetailList = Lists.newArrayList();
        List<UserFilterDetailENT> blackDetailList = Lists.newArrayList();
        List<String> whilteStrList = Lists.newLinkedList();
        List<String> blackStrList = Lists.newLinkedList();
        if (whiteList != null && whiteList.size() > 0) {
            UserFilterDetailENT userFilterDetailENT = new UserFilterDetailENT();
            userFilterDetailENT.setUserFilterId(whiteList.get(0).getId());
            whiteDetailList = userFilterDetailDao.findUserFilterDetailList(userFilterDetailENT, null);
            /** 拼接有效的白名单 **/
            if (whiteDetailList != null && whiteDetailList.size() > 0) {
                whiteDetailList.forEach(ent -> {
                    if (UserFilterContext.USER_FILTER_STATUS_TRUE.equals(ent.getStatus())) {
                        whilteStrList.add(ent.getContext());
                    }
                });
            }
        }
        if (blackList != null && blackList.size() > 0) {
            UserFilterDetailENT userFilterDetailENT = new UserFilterDetailENT();
            userFilterDetailENT.setUserFilterId(blackList.get(0).getId());
            blackDetailList = userFilterDetailDao.findUserFilterDetailList(userFilterDetailENT, null);
            /** 拼接有效的黑名单 **/
            if (blackDetailList != null && blackDetailList.size() > 0) {
                blackDetailList.forEach(ent -> {
                    //if(StringUtils.isBlank(ent.getEntranceCode())) {
                        blackStrList.add(ent.getContext());
//                    }
//                    else{
//                        blackStrList.add(ent.getContext() + "|" + ent.getEntranceCode());
//                    }
                });
            }
        }
        map.forEach((k, v) -> {
            if (!"".equals(k)) {
                UserFilterENT blackUserFilterENT = blackList.get(0);
                int size = v.size();
                /**
                 * 添加条件：
                 * 1.不存在于白名单，或者白名单是无效的
                 * 2.不存在于黑名单
                 * 3.频次大于阈值
                 * **/
                if (!whilteStrList.contains(k) && !blackStrList.contains(k) && size > blackUserFilterENT.getRate().intValue()) {
                    UserFilterDetailENT ent = new UserFilterDetailENT();
                    ent.setContext(k);
//                    String [] array = k.split("|");
//                    ent.setContext(array[0]);
//                    if(array.length > 1) {
//                        ent.setEntranceCode(array[1]);
//                    }
                    rstList.add(ent);
                }
            }
        });
//        }
        /**==============1.白名单过滤 2.阈值过滤 END===============* */
        return rstList;
    }


    //    =========================admin==================================
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listUserFilterDetail(JSONObject jsonParam) {
        ExtModel result = new ExtModel();
        List<UserFilterDetailENT> userFilterDetailENTs = userFilterDetailDao.listUserFilterDetail(jsonParam.getInteger("page"),
                jsonParam.getInteger("limit"), jsonParam.getJSONObject("data"));
        Long allCount = userFilterDetailDao.getUserFilterDetailCount(jsonParam.getJSONObject("data"));

        result.setData(userFilterDetailENTs);
        result.setTotal(allCount);
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public ExtModel addUserFilterDetail(UserFilterDetailENT userFilterDetail) {
        ExtModel result = new ExtModel();
        //根据类型编码和黑白名单类型查询配置是否存在
        UserFilterENT queryUserFilterENT = new UserFilterENT();
        queryUserFilterENT.setId(userFilterDetail.getId());
        queryUserFilterENT.setStatus("1");
        List<UserFilterENT> userFilterList = userFilterService.findUserFilterList(queryUserFilterENT);
        if (CollectionUtils.isNotEmpty(userFilterList) && userFilterList.size() == 1){
            userFilterDetail.setId(userRedis.incr(UserRedisKeys.USER_FILTER_DETAIL_INCR_KEY));
            userFilterDetail.setCreateTime(new Date());
            userFilterDetail.setUpdateTime(new Date());
            UserFilterENT ent = userFilterList.get(0);
            userFilterDetail.setUserFilterId(ent.getId());
            userFilterDetail.setCode(ent.getCode());
            userFilterDetail.setType(ent.getType());
            userFilterDetail.setEntranceCode(ent.getEntranceCode());
            userFilterDetailDao.addUserFilterDetail(userFilterDetail);
        } else {
            result.setSuccess(Boolean.FALSE);
            result.setData("未查询到防御过滤配置");
        }
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public ExtModel updateUserFilterDetail(UserFilterDetailENT userFilter) {
        ExtModel result = new ExtModel();
        userFilter.setUpdateTime(new Date());
        userFilterDetailDao.updateUserFilter(userFilter);
        return result;
    }
}
