package com.tty.user.service.impl;/**
 * Created by shenwei on 2017/1/4.
 */

import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.tty.user.common.utils.DateUtils;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.context.AuthorityEnum;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserInfoExtensionFields;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.UserGrowupDao;
import com.tty.user.dao.pojo.UserGrowupRecord;
import com.tty.user.dao.pojo.UserLevelAuthority;
import com.tty.user.dao.pojo.UserLevelMapping;
import com.tty.user.model.result.*;
import com.tty.user.service.UserGrowupService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shenwei
 * @create 2017-01-04
 */
@Service("userGrowupService")
public class UserGrowupServiceImpl implements UserGrowupService {

    private static final Logger logger = LoggerFactory.getLogger(UserGrowupServiceImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private UserGrowupDao userGrowupDao;
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;

    /**
     * @Author shenwei
     * @Date 2017/1/4 15:02
     * @Description 获取用户等级成长值等
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    public UserGrowupModel getUserLevelAndGrowups(String traceId, Long userId) {
        logger.debug("获取用户等级成长值等信息 traceId {} userId {}", traceId, userId);
        String userLevel = publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_LEVEL);
        String userGrowups = publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_GROWUPS);
        String userLevelName = "";
        UserGrowupModel model = new UserGrowupModel();
        if (userLevel != null && userGrowups != null) {
            model.setLevel(Integer.parseInt(userLevel));
            model.setGrowups(Long.parseLong(userGrowups));
        } else {
            List<Map<String, Object>> list = userGrowupDao.getUserLevelAndGrowup(userId);
            if (list == null || list.size() == 0) {
                //无记录默认为0级，0成长值
                userLevel = "0";
                userGrowups = "0";
            } else {
                userLevel = list.get(0).get("level").toString();
                userGrowups = list.get(0).get("growups").toString();
            }
            model.setLevel(Integer.parseInt(userLevel));
            model.setGrowups(Long.parseLong(userGrowups));
            publicCommonRedisUtil.setUserInfoExtension(userId, UserInfoExtensionFields.USER_LEVEL, userLevel);
            publicCommonRedisUtil.setUserInfoExtension(userId, UserInfoExtensionFields.USER_GROWUPS, userGrowups);
        }
        for (Map.Entry<Integer, String> entry : UserContext.USER_LEVEL_NAME_MAP.entrySet()) {
            if (Integer.parseInt(userLevel) == entry.getKey()) {
                userLevelName = entry.getValue();
                break;
            }
        }
        List<UserLevelMapping> userLevelMappings = userGrowupDao.getUserLevelMapping();
        Long levelup = new Long(0);
        for (UserLevelMapping item : userLevelMappings) {
            if (item.getLevel() == Integer.parseInt(userLevel) + 1) {
                levelup = item.getGrowupMin() - Long.parseLong(userGrowups);
                break;
            }
        }
        model.setLevelbeat(getUserLevelBeat(Integer.parseInt(userLevel)));
        model.setLevelup(levelup);
        model.setLevelname(userLevelName);
        return model;
    }

    /**
     * 根据用户id获取用户的等级
     *
     * @param traceId
     * @param userId
     * @return
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    public UserGrowupModel getUserLevelByUserId(String traceId, Long userId) {
        logger.debug("获取用户等级信息 traceId {} userId {}", traceId, userId);
        String userLevel = publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_LEVEL);
        String userGrowups = "";
        String userLevelName = "";
        UserGrowupModel model = new UserGrowupModel();
        if (userLevel != null) {
            model.setLevel(Integer.parseInt(userLevel));
        } else {
            List<Map<String, Object>> list = userGrowupDao.getUserLevelAndGrowup(userId);
            if (list == null || list.size() == 0) {
                //无记录默认为0级，0成长值
                userLevel = "0";
                userGrowups = "0";
            } else {
                userLevel = list.get(0).get("level").toString();
                userGrowups = list.get(0).get("growups").toString();
            }
            model.setLevel(Integer.parseInt(userLevel));
            publicCommonRedisUtil.setUserInfoExtension(userId, UserInfoExtensionFields.USER_LEVEL, userLevel);
            publicCommonRedisUtil.setUserInfoExtension(userId, UserInfoExtensionFields.USER_GROWUPS, userGrowups);
        }

        userLevelName = UserContext.USER_LEVEL_NAME_MAP.get(Integer.valueOf(userLevel));
        model.setLevelname(userLevelName);
        return model;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/11 14:16
     * @Description 批量获取用户等级
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    public UserLevelsModel getUserLevels(String traceId, Long[] userIds) {
        logger.debug("批量获取用户等级 traceId {} userIds {}", traceId, userIds);
        List<UserLevelModel> list = new ArrayList<UserLevelModel>();
        if (userIds == null || userIds.length < 1) {
            UserLevelsModel um = new UserLevelsModel();
            um.setUserlevels(list);
            return um;
        }
        for (Long userId : userIds) {
            String userLevel = publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_LEVEL);
            if (userLevel == null) {
                userLevel = "0";
            }
            String levelName = UserContext.USER_LEVEL_NAME_MAP.get(Integer.valueOf(userLevel));
            UserLevelModel item = new UserLevelModel();
            item.setUserid(userId);
            item.setUserlevel(Integer.parseInt(userLevel));
            item.setLevelname(levelName);
            list.add(item);
        }
        UserLevelsModel um = new UserLevelsModel();
        um.setUserlevels(list);
        return um;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/4 13:19
     * @Description 用户成长值变动
     */
    public Boolean changeUserGrowup(String traceId, Long userId, Long growups, String description) {
        try {
            logger.info("用户成长值变动 traceId:{} userId:{} growups:{} description:{}", traceId, userId, growups, description);
            Integer levelBefore = null;
            UserGrowupModel userModel = getUserLevelAndGrowups(traceId, userId);
            if (userModel != null) {
                levelBefore = userModel.getLevel();
            }
            Long userGrowups = publicCommonRedisUtil.increaseUserGrowups(userId, growups);
            Integer level = findLevelByGrowup(userGrowups);
            boolean levelchanged = levelBefore != null && !level.equals(levelBefore);
            //如果等级发生变动,记录等级变动时间,同步用户Base信息用于后台统计
            if (levelchanged) {
                logger.debug("设定用户等级 traceId:{} userId:{} level:{}", traceId, userId, level);
                publicCommonRedisUtil.setUserInfoExtension(userId, UserInfoExtensionFields.USER_LEVEL, level.toString());
                //用于进入会员中心弹窗
                publicCommonRedisUtil.removeEnterLevelCenter(userId);
                syncUserBaseInfo(traceId, userId);
                // publicCommonActiveMqUtil.reportInfoUserLevel(userId, level, traceId);
            }
            logger.debug("成长值等级变动异步入库 traceId:{} userId:{} growups:{}", traceId, userId, growups);
            threadPoolTaskExecutor.execute(() -> {
                if (!userGrowupDao.changeUserGrowup(userId, growups, level, description, levelchanged)) {
                    logger.warn("用户成长值异步入库失败 traceId:{}, userId:{},growups:{}", traceId, userId, growups);
                }
            });
            return true;
        } catch (Exception e) {
            logger.error("用户成长值变动异常 traceId:{}, userId:{},growups:{}, stackTrace:{}", traceId, userId, growups, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/1/4 17:46
     * @Description 获取用户成长值变动明细
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    public List<UserGrowupRecordModel> getUserGrowupRecords(String traceId, Long userId, Integer pageIndex, Integer pageSize) {
        logger.debug("获取用户成长值变动明细 traceId {} userId {} pageIndex {} pageSize {}", traceId, userId, pageIndex, pageSize);
        List<UserGrowupRecord> records = userGrowupDao.getUserGrowupRecord(userId, pageIndex, pageSize);
        List<UserGrowupRecordModel> list = new ArrayList<>();
        if (records != null && records.size() > 0) {
            for (UserGrowupRecord ent : records) {
                UserGrowupRecordModel model = new UserGrowupRecordModel();
                model.setDate(ent.getCreateDate().getTime());
                model.setGrowups(ent.getGrowups().toString());
                model.setSource(ent.getDescription());
                list.add(model);
            }
        }
        return list;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/5 11:48
     * @Description 根据成长值定位等级
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    public Integer findLevelByGrowup(Long growups) {
        logger.debug("根据成长值定位等级 growups:{}", growups);
        Integer level = 0;
        List<UserLevelMapping> mappings = userGrowupDao.getUserLevelMapping();
        if (mappings == null || mappings.size() == 0) {
            return level;
        }
        for (UserLevelMapping mapping : mappings) {
            if (mapping.getGrowupMin() != null && mapping.getGrowupMax() != null) {
                if (mapping.getGrowupMin() <= growups && growups <= mapping.getGrowupMax()) {
                    level = mapping.getLevel();
                }
            } else {
                if (mapping.getGrowupMin() <= growups) {
                    level = mapping.getLevel();
                }
            }
        }
        return level;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/6 12:02
     * @Description 获取升级解锁特权
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    public List<UserLevelAuthority> getUnlockAuthority(Integer levelNow, Integer levelUpper) {
        return getUnlockAuthorityByLevel(levelNow, levelUpper);
    }

    private List<UserLevelAuthority> getUnlockAuthorityByLevel(Integer levelNow, Integer levelUpper) {
        logger.debug("获取升级解锁特权 levelNow:{} levelUpper:{}", levelNow, levelUpper);
        List<UserLevelAuthority> lowAuthorityList = userGrowupDao.getLevelAuthority(levelNow);
        List<UserLevelAuthority> highAuthorityList = userGrowupDao.getLevelAuthority(levelUpper);
        List<UserLevelAuthority> destAuthorityList = new ArrayList<>();
        if (lowAuthorityList != null && highAuthorityList != null) {
            for (UserLevelAuthority high : highAuthorityList) {
                Boolean flag = true;
                for (UserLevelAuthority low : lowAuthorityList) {
                    if (low.getAuthorityName().equals(high.getAuthorityName()) && low.getAuthorityValue().equals(high.getAuthorityValue())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    destAuthorityList.add(high);
                }
            }
        }
        return destAuthorityList;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/6 14:45
     * @Description 获取用户会员中心
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    public Object getUserLevelCenter(String traceId, Long userId) {
        UserLevelCenterModel model = new UserLevelCenterModel();
        UserGrowupModel growupModel = getUserLevelAndGrowups(traceId, userId);
        Integer level = growupModel.getLevel();
        Long growups = growupModel.getGrowups();
        Long levelup = growupModel.getLevelup();
        String levelbeat = growupModel.getLevelbeat();
        String growpercent = getGrowupPercent(growups, level);
        String userFace = publicCommonRedisUtil.getUserFace(userId);
        Integer dialogGiftId = 0;
        model.setUserlevel(level);
        model.setGrowups(growups);
        model.setLevelup(levelup);
        model.setLevelbeat(levelbeat);
        model.setGrowpercent(growpercent);
        model.setUserface(userFace);
        List<AuthorityModel> currentAuthorityModels = new ArrayList<AuthorityModel>();
        List<UpperAuthorityModel> upperAuthorityModels = new ArrayList<UpperAuthorityModel>();
        List<UserLevelAuthority> currentAuthority = userGrowupDao.getLevelAuthority(level);
        List<UserLevelAuthority> upperAuthority = getUnlockAuthorityByLevel(level, level + 1);
        for (UserLevelAuthority item : currentAuthority) {
            AuthorityModel authorityModel = new AuthorityModel();
            authorityModel.setGiftid(item.getId());
            authorityModel.setName(item.getAuthorityName());
            authorityModel.setDis(item.getAuthorityDescription());
            authorityModel.setCategory(item.getCategory());
            if (item.getCategory().equals(UserContext.LEVEL_UP_GIFT)) {
                dialogGiftId = item.getId();
            }
            //按钮文字显示 商品相关控制
            setAuthorityBtn(userId, item, authorityModel);
            currentAuthorityModels.add(authorityModel);
        }
        for (UserLevelAuthority item : upperAuthority) {
            UpperAuthorityModel upperAuthorityModel = new UpperAuthorityModel();
            upperAuthorityModel.setGiftid(item.getId());
            upperAuthorityModel.setName(item.getAuthorityName());
            upperAuthorityModel.setDis(item.getAuthorityDescription());
            upperAuthorityModel.setCategory(item.getCategory());
            upperAuthorityModels.add(upperAuthorityModel);
        }
        //0 1 级没有升级礼包 无弹窗
        if (publicCommonRedisUtil.isEnterLevelCenter(userId) || level == 0 || level == 1) {
            model.setShowdialog(0);
        } else {
            publicCommonRedisUtil.enterLevelCenter(userId);
            model.setShowdialog(1);
        }
        model.setDialoggiftid(dialogGiftId);
        model.setMyauthority(currentAuthorityModels);
        model.setUpperauthority(upperAuthorityModels);
        return model;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/12 18:50
     * @Description 领取礼包 需要发放彩金卡以及充值优化卡
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    public Boolean getLevelupGift(String traceId, Integer giftId, Long userId) {
        logger.info("用户领取礼包 traceId:{},giftId:{},userId:{}", traceId, giftId, userId);
        if (!userLevelUpFilter(traceId, userId, giftId)) {
            return false;
        }
        logger.debug("存入用户领取礼包记录 traceId:{} userId:{} giftId:{}", traceId, userId, giftId);
        userGrowupDao.saveUserGiftRecord(userId, giftId);
        int level = 0;
        //礼包id与等级匹配
        switch (giftId.intValue()) {
            case 4:
                level = 2;
                break;
            case 6:
                level = 3;
                break;
            case 10:
                level = 4;
                break;
            case 15:
                level = 5;
                break;
            case 20:
                level = 6;
                break;
            default:
                break;
        }
        try {
            boolean flag = false;
            //boolean flag = dubboActivityBaseInfoService.extendGradeActivityCard(traceId, userId, level);
            if (flag) {
                logger.info("RPC调用红包系统派发升级礼包成功, traceId={}, userId={}, level={}", traceId, userId, level);
            } else {
                logger.error("RPC调用红包系统派发升级礼包失败, traceId={}, userId={}, level={}", traceId, userId, level);
            }
            return flag;
        } catch (Exception e) {
            logger.error("RPC调用红包系统派发升级礼包失败, traceId={}, userId={}, level={} ", traceId, userId, level);
            return false;
        }
    }

    @Override
    public void removeGrowupRecords() {
        logger.info("清理成长值记录数据");
        userGrowupDao.removeGrowupRecords();
    }

    /**
     * @Author shenwei
     * @Date 2017/1/18 19:36
     * @Description 用户领取升级礼包过滤
     */
    private boolean userLevelUpFilter(String traceId, Long userId, Integer giftId) {
        if (userGrowupDao.IsAlreadyGetGift(userId, giftId)) {
            logger.debug("用户已领过该升级礼包,traceId:{} userId:{} giftId:{}", traceId, userId, giftId);
            return false;
        }
        //升级礼包所需等级是否匹配用户等级
        List<Map<String, Object>> list = userGrowupDao.getGiftMappingLevel(giftId);
        if (list == null || list.size() <= 0) {
            return false;
        }
        Integer mappingLevel = Integer.parseInt(list.get(0).get("level").toString());
        Integer category = Integer.parseInt(list.get(0).get("category").toString());
        if (!category.equals(UserContext.LEVEL_UP_GIFT) || publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_LEVEL) == null
                || Integer.parseInt(publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_LEVEL)) != mappingLevel) {
            return false;
        }
        return true;
    }

    /**
     * @Author shenwei
     * @Date 15:32
     * @Description 获取用户等级击败用户比例，每日更新
     */
    private String getUserLevelBeat(Integer level) {
        logger.debug("获取用户等级击败用户比例 level:{}", level);
        String levelBeat = publicCommonRedisUtil.getValue(String.format(UserRedisKeys.USER_LEVEL_BEAT_KEY, level));
        if (levelBeat == null || levelBeat.isEmpty()) {
            levelBeat = userGrowupDao.getUserLevelBeat(level);
            Integer expireSeconds = Integer.parseInt(DateUtils.getSecondsToTomorrow().toString());
            publicCommonRedisUtil.setValue(String.format(UserRedisKeys.USER_LEVEL_BEAT_KEY, level), levelBeat, expireSeconds);
        }
        return levelBeat;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/13 11:47
     * @Description 同步用户基础信息 [当用户成长值发生变动时]
     */
    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    void syncUserBaseInfo(String traceId, Long userId) {
        String redisKey = UserRedisKeys.CACHE_USER_INFO_BY_USER_ID_KEY + userId;
        String jsonString = publicCommonRedisUtil.getValue(redisKey);
        String userName = null;
        if (StringUtils.isNotBlank(jsonString)) {
            JSONObject userJson = JSONObject.fromObject(jsonString);
            userName = userJson.get("Name").toString();
            if (StringUtils.isNotBlank(userName)) {
                if (!userGrowupDao.syncUserBaseInfo(userId, userName)) {
                    logger.warn("同步用户基础信息失败 traceId {} userId {}", traceId, userId);
                }
            }
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/1/13 14:39
     * growups 当前成长值 level
     * @Description 获取成长值进度比例  计算：（当前成长值-本等级最低成长值）/(下一等级最低成长值-本等级最低成长值）
     */
    private String getGrowupPercent(Long growups, Integer level) {
        logger.debug("获取成长值进度比例 growups:{} level:{}", growups, level);
        List<UserLevelMapping> levelMappings = userGrowupDao.getUserLevelMapping();
        //当前等级最低成长值
        Long currentMin = new Long(0);
        Boolean foo = false;
        for (UserLevelMapping item : levelMappings) {
            if (item.getLevel().equals(level)) {
                foo = true;
                currentMin = item.getGrowupMin();
                break;
            }
        }
        //下一等级最低成长值
        Long growupMin = new Long(0);
        Boolean flag = false;
        for (UserLevelMapping item : levelMappings) {
            if (item.getLevel() == level + 1) {
                flag = true;
                growupMin = item.getGrowupMin();
                break;
            }
        }
        if (flag && foo) {
            double growPercent = (double) (growups - currentMin) / (double) (growupMin - currentMin);
            NumberFormat format = NumberFormat.getPercentInstance();
            format.setMaximumFractionDigits(0); //百分比不保留小数
            return format.format(growPercent);
        } else {
            return "0";
        }
    }

    /**
     * @Author shenwei
     * @Date 2017/1/18 11:16
     * @Description 等级权益按钮显示控制
     */
    private void setAuthorityBtn(Long userId, UserLevelAuthority item, AuthorityModel model) {
        Integer category = item.getCategory();
        if (category.equals(AuthorityEnum.IDENTITY.getValue()) || category.equals(AuthorityEnum.SPEEDINTEGRAL.getValue()) || category.equals(AuthorityEnum.VIPPHONE.getValue()) || category.equals(AuthorityEnum.INTEGRALEXCHANGE.getValue())) {
            model.setBtnStatus(UserContext.LEVEL_BUTTON_STATUS_DARK);
            model.setBtnText(UserContext.LEVEL_BUTTON_TEXT_DONE);
        } else if (category.equals(AuthorityEnum.PARTICULAR.getValue()) || category.equals(AuthorityEnum.LIMITEXCHANGE.getValue())) {
            model.setBtnStatus(UserContext.LEVEL_BUTTON_STATUS_JUMP);
            model.setBtnText(UserContext.LEVEL_BUTTON_TEXT_TOSEE);
        } else if (category.equals(AuthorityEnum.LEVELUP.getValue()) && !userGrowupDao.IsAlreadyGetGift(userId, model.getGiftid())) {
            model.setBtnStatus(UserContext.LEVEL_BUTTON_STATUS_HIGHLIGHT);
            model.setBtnText(UserContext.LEVEL_BUTTON_TEXT_CANGET);
        } else {
            model.setBtnStatus(UserContext.LEVEL_BUTTON_STATUS_DARK);
            model.setBtnText(UserContext.LEVEL_BUTTON_TEXT_DONE);
        }
    }
}
