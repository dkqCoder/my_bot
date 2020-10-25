package com.tty.user.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.redis.JedisClusterFactory;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.UserFansDao;
import com.tty.user.dao.entity.UserFansENT;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author zhuxinhai
 */
@Repository("userFansDao")
public class UserFansDaoImpl extends BaseDao<UserFansENT> implements UserFansDao {
    @Autowired
     @Qualifier("userRedis")
     private JedisClusterFactory userRedis;

    public void saveUserFans(UserFansENT ent) {
        save(ent);
//        emptyCache(ent.getUserId(), ent.getAttentionUserId(), ent.getStatus());
    }

    public void updateUserFans(UserFansENT ent) {
        update(ent);
//        emptyCache(ent.getUserId(), ent.getAttentionUserId(), ent.getStatus());
    }

    public void deleteUserFans(UserFansENT ent) {
        delete(ent);
//        emptyCache(ent.getUserId(), ent.getAttentionUserId(), ent.getStatus());
    }

    public void saveOrUpdateUserFans(UserFansENT ent) {
        saveOrUpdate(ent);
//        emptyCache(ent.getUserId(), ent.getAttentionUserId(), ent.getStatus());
    }


    //    =========================admin==================================
    @Override
    public List<UserFansENT> listUserFans(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from UserFansENT where 1=1 ")
                .andEq("name", data.getString("name"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listUserFansCount(JSONObject data) {
        String hql = "select count(id) from UserFansENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("name", data.getString("name"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

    @Override
    public UserFansENT getUserFansRelation(Long userId, Long attentionUserId) {
        Query query = getQuery("from UserFansENT where userId=:userId and attentionUserId=:attentionUserId ");
        query.setLong("userId", userId);
        query.setLong("attentionUserId", attentionUserId);
        return (UserFansENT) query.uniqueResult();
    }

    /**
     * 获取用户粉丝id
     *
     * @param userId
     */
    @Override
    public List<String> getUserFans(Long userId) {
        List<String> list = null;
        String key = String.format(UserRedisKeys.USER_RELATION_FANS_KEY, userId);
        Set<String> set = userRedis.zrevrange(key, 0L, -1L);
        if (CollectionUtils.isEmpty(set)) {
            Query query = getQuery(" from UserFansENT ent where ent.attentionUserId=:attentionUserId and ent.status = 1");
            query.setLong("attentionUserId", userId);
            List<UserFansENT> fanslist = query.list();
            list = new ArrayList<>();
            if (fanslist != null && fanslist.size() > 0) {
                Map<String, Double> map = new HashMap<>();
                for (UserFansENT ent : fanslist) {
                    if (ent.getUserId() != null) {
                        //添加返回结果
                        list.add(String.valueOf(ent.getUserId()));
                        // 关注关系 以修改时候 排序  没有以创建时间
                        if (ent.getUpdateTime() == null) {
                            if (ent.getUpdateTime() == null) {
                                map.put(String.valueOf(ent.getUserId()), Double.valueOf(1));
                            } else {
                                map.put(String.valueOf(ent.getUserId()), Double.valueOf(ent.getCreateTime().getTime()));
                            }
                        } else {
                            map.put(String.valueOf(ent.getUserId()), Double.valueOf(ent.getUpdateTime().getTime()));
                        }
                    }
                }
                userRedis.zadd(key, map);
            }
        } else {
            list = new ArrayList<>(set);
        }
        return list;
    }

    /**
     * 获取用户粉丝数量
     *
     * @param userId
     */
    @Override
    public Integer getUserFansCount(Long userId) {
        String key = String.format(UserRedisKeys.USER_RELATION_FANS_KEY, userId);
        Integer count = userRedis.zcard(key).intValue();
        if (count == 0) {
            count = getUserFans(userId).size();
        }
        return count;
    }

    /**
     * 获取用户粉丝id
     *
     * @param userId
     * @param page
     * @param pageSize
     */
    @Override
    public List<Long> getUserFans(Long userId, Integer page, Integer pageSize) {
        List<Long> list = null;
        String key = String.format(UserRedisKeys.USER_RELATION_FANS_KEY, userId);
        Set<String> set = userRedis.zrevrange(key, Long.valueOf(page * pageSize - pageSize), Long.valueOf(page * pageSize - 1L));
        if (CollectionUtils.isEmpty(set)) {
            Query query = getQuery("select ent.userId from UserFansENT ent where ent.attentionUserId=:attentionUserId and ent.status = 1");
            query.setLong("attentionUserId", userId);
            List<Long> fansIds = (List<Long>) query.setFirstResult(page * pageSize - pageSize).setMaxResults(pageSize).list();
            list = new ArrayList<>();
            for (Long fansId : fansIds) {
                list.add(fansId);
            }
        } else {
            list = new ArrayList<>();
            for (String s : set) {
                list.add(Long.valueOf(s));
            }
        }
        return list;
    }
    public Integer getUserFansCounts(Long userId) {
        String key = String.format(UserRedisKeys.USER_RELATION_FANS_KEY, userId);
        return userRedis.zcard(key).intValue();
    }
    /**
     * 获取用户关注id
     *
     * @param userId
     */
    @Override
    public List<Long> getUserAttention(Long userId) {
        List<Long> list = null;
        String key = String.format(UserRedisKeys.USER_RELATION_ATTENTION_KEY, userId);
        Set<String> set = userRedis.zrevrange(key, 0L, -1L);
        if (CollectionUtils.isEmpty(set)) {
            Query query = getQuery(" from UserFansENT ent where ent.userId=:userId and ent.status = 1");
            query.setLong("userId", userId);
            List<UserFansENT> attentionList = query.list();
            list = new ArrayList<>();
            if (attentionList != null && attentionList.size() > 0) {
                Map<String, Double> map = new HashMap<>();
                for (UserFansENT ent : attentionList) {
                    if (ent.getAttentionUserId() != null) {
                        list.add(ent.getUserId());
                        if (ent.getUpdateTime() == null) {
                            if (ent.getUpdateTime() == null) {
                                map.put(String.valueOf(ent.getAttentionUserId()), Double.valueOf(1));
                            } else {
                                map.put(String.valueOf(ent.getAttentionUserId()), Double.valueOf(ent.getCreateTime().getTime()));
                            }
                        } else {
                            map.put(String.valueOf(ent.getAttentionUserId()), Double.valueOf(ent.getUpdateTime().getTime()));
                        }
                    }
                }
                userRedis.zadd(key, map);
            }
        } else {
            list = new ArrayList<>();
            for (String s : set) {
                list.add(Long.valueOf(s));
            }
        }
        return list;
    }

    /**
     * 获取用户关注数量
     *
     * @param userId
     */
    @Override
    public Integer getUserAttentionCount(Long userId) {
        String key = String.format(UserRedisKeys.USER_RELATION_ATTENTION_KEY, userId);
        Integer count = userRedis.zcard(key).intValue();
        if (count == 0) {
            count = getUserAttention(userId).size();
        }
        return count;
    }

    /**
     * 获取用户关注id
     *
     * @param userId
     * @param page
     * @param pageSize
     */
    @Override
    public List<Long> getUserAttention(Long userId, Integer page, Integer pageSize) {
        List<Long> list = null;
        String key = String.format(UserRedisKeys.USER_RELATION_ATTENTION_KEY, userId);
        Set<String> set = userRedis.zrevrange(key, Long.valueOf(page * pageSize - pageSize), Long.valueOf(page * pageSize - 1));
        if (CollectionUtils.isEmpty(set)) {
            Query query = getQuery("select ent.attentionUserId from UserFansENT ent where ent.userId=:userId and ent.status = 1");
            query.setLong("userId", userId)
            ;
            List<Long> attentionIds = (List<Long>) query.setFirstResult(page * pageSize - pageSize)
                    .setMaxResults(pageSize).list();
            list = new ArrayList<>();
            for (Long fansId : attentionIds) {
                list.add(fansId);
            }
        } else {
            list = new ArrayList<>();
            for (String s : set) {
                list.add(Long.valueOf(s));
            }
        }
        return list;
    }

    @Override
    public void setRedisUserAttentionstatus(Long userId, Long attentionUserId, Integer status) {
        if (UserContext.USER_FANS_STATUS_ATTENTION == status) {//关注
            long time = System.currentTimeMillis();
            //被关注人 粉丝列表添加
            String fansKey = String.format(UserRedisKeys.USER_RELATION_FANS_KEY, attentionUserId);
            Map<String, Double> fansMap = new HashMap<>();
            fansMap.put(String.valueOf(userId), Double.valueOf(time));
            userRedis.zadd(fansKey, fansMap);

            //添加关注列表
            String attentionKey = String.format(UserRedisKeys.USER_RELATION_ATTENTION_KEY, userId);
            Map<String, Double> attentionMap = new HashMap<>();
            attentionMap.put(String.valueOf(attentionUserId), Double.valueOf(time));
            userRedis.zadd(attentionKey, attentionMap);

        } else if (UserContext.USER_FANS_STATUS_CANCEL_ATTENTION == status) {//取消关注
            //删除被关注人 粉丝列表
            String fansKey = String.format(UserRedisKeys.USER_RELATION_FANS_KEY, attentionUserId);
            userRedis.zrem(fansKey, String.valueOf(userId));
            //删除关注列表
            String attentionKey = String.format(UserRedisKeys.USER_RELATION_ATTENTION_KEY, userId);
            userRedis.zrem(attentionKey, String.valueOf(attentionUserId));
        } else {//其它情况清空列表
            String fansKey = String.format(UserRedisKeys.USER_RELATION_FANS_KEY, attentionUserId);
            userRedis.del(fansKey);
            String attentionKey = String.format(UserRedisKeys.USER_RELATION_ATTENTION_KEY, userId);
            userRedis.del(attentionKey);
        }
    }


    private void emptyCache(Long userId, Long attentionUserId, Integer status) {


        String fansKey = String.format(UserRedisKeys.USER_RELATION_FANS_KEY, attentionUserId);
        userRedis.del(fansKey);
        String attentionKey = String.format(UserRedisKeys.USER_RELATION_ATTENTION_KEY, userId);
        userRedis.del(attentionKey);
    }


}
