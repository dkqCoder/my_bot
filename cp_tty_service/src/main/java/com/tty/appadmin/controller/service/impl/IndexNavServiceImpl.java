package com.tty.appadmin.controller.service.impl;

import com.tty.appadmin.context.Constants;
import com.tty.appadmin.controller.model.result.*;
import com.tty.appadmin.controller.service.IndexNavService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: sunyishun
 * @Date: 2020/10/27
 * @Description: 首页布局信息
 */
@Service("indexNavService")
public class IndexNavServiceImpl implements IndexNavService {

    public IndexPage getIndexPage() {
        IndexPage indexPage = new IndexPage();
        // 获取版本信息
        VersionInfo versionInfo = new VersionInfo("android", "1.0.0", 0, "http://www.baidu.com");
        indexPage.setVersionInfo(versionInfo);
        // 获取店铺列表
        List<ShopInfo> shopInfoList = new ArrayList<>();
        List<Integer> saleLotteryIds = new ArrayList<>();
        saleLotteryIds.add(90);
        shopInfoList.add(new ShopInfo(1, "广发彩票店", saleLotteryIds, 1, 0, 1, 1));
        shopInfoList.add(new ShopInfo(2, "诚发彩票店", saleLotteryIds, 1, 0, 1, 0));
        shopInfoList.add(new ShopInfo(3, "友成彩票店", saleLotteryIds, 1, 0, 1, 0));
        indexPage.setShopInfoList(shopInfoList);
        // banner列表
        List<IndexPicNotice> bannerList = new ArrayList<>();
        bannerList.add(new IndexPicNotice(1, 1, "https://alipic.lanhuapp.com/ps6169a4ba57738fa4-f224-4796-a98d-822df0c312e7", Constants.ActionBusiness.WEB_VIEW, "{\"url\":\"http://wwww.baidu.com\"}"));
        indexPage.setBannerList(bannerList);
        // 系统公告信息
        List<IndexTextNotice> sysNoticeList = new ArrayList<>();
        sysNoticeList.add(new IndexTextNotice(1, "平台系统公告", Constants.ActionBusiness.WEB_VIEW, "{\"url\":\"http://wwww.baidu.com\"}"));
        indexPage.setSysNoticeList(sysNoticeList);
        // 投注彩种
        List<IndexLottery> lotteryList = new ArrayList<>();
        IndexLottery indexLottery = new IndexLottery(90, "竞彩足球", "热门赛事竞猜", "XX场可投", "", Constants.ActionBusiness.LOTTERY, "{\"lottery\":\"90\"}");
        IndexLottery indexLottery2 = new IndexLottery(91, "竞彩篮球", "玩转NBA赛事", "XX场可投", "", Constants.ActionBusiness.LOTTERY, "{\"lottery\":\"91\"}");
        lotteryList.add(indexLottery);
        lotteryList.add(indexLottery2);
        indexPage.setLotteryList(lotteryList);
        // 滚动播报
        List<IndexTextNotice> rollNoticeList = new ArrayList<>();
        rollNoticeList.add(new IndexTextNotice(1, "大奖滚动播报", Constants.ActionBusiness.WEB_VIEW, "{\"url\":\"http://wwww.baidu.com\"}"));
        rollNoticeList.add(new IndexTextNotice(2, "大奖滚动播报2", Constants.ActionBusiness.WEB_VIEW, "{\"url\":\"http://wwww.baidu.com\"}"));
        indexPage.setRollNoticeList(rollNoticeList);
        // 强广1
        IndexPicNotice forceAd1 = new IndexPicNotice(1, 1, "https://alipic.lanhuapp.com/ps6169a4ba57738fa4-f224-4796-a98d-822df0c312e7", Constants.ActionBusiness.WEB_VIEW, "{\"url\":\"http://wwww.baidu.com\"}");
        indexPage.setForceAd1(forceAd1);
        // 大神位信息
        List<IndexMaster> masterList = new ArrayList<>();
        indexPage.setMasterList(masterList);
        // 单关赛事
        List<IndexMatch> indexMatchList = new ArrayList<>();
        indexPage.setIndexMatchList(indexMatchList);
        // 强广位2
        IndexPicNotice forceAd2 = new IndexPicNotice(1, 1, "https://alipic.lanhuapp.com/ps6169a4ba57738fa4-f224-4796-a98d-822df0c312e7", Constants.ActionBusiness.WEB_VIEW, "{\"url\":\"http://wwww.baidu.com\"}");
        indexPage.setForceAd2(forceAd2);
        return indexPage;
    }
}
