package com.tty.appadmin.controller.model.result;

import java.util.List;

/**
 * @Author: sunyishun
 * @Date: 2020/10/26
 * @Description: 首页
 */
public class IndexPage {
    VersionInfo versionInfo; // 版本信息
    List<ShopInfo> shopInfoList; // 店铺列表
    List<IndexPicNotice> bannerList; // banner列表
    // 系统公告信息
    List<IndexTextNotice> sysNoticeList;
    // 投注彩种
    List<IndexLottery> lotteryList;
    // 滚动播报
    List<IndexTextNotice> rollNoticeList;
    // 强广1
    IndexPicNotice forceAd1;
    // 大神位信息
    List<IndexMaster> masterList;
    // 单关赛事
    List<IndexMatch> indexMatchList;
    // 强广位2
    IndexPicNotice forceAd2;

    public VersionInfo getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(VersionInfo versionInfo) {
        this.versionInfo = versionInfo;
    }

    public List<ShopInfo> getShopInfoList() {
        return shopInfoList;
    }

    public void setShopInfoList(List<ShopInfo> shopInfoList) {
        this.shopInfoList = shopInfoList;
    }

    public List<IndexPicNotice> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<IndexPicNotice> bannerList) {
        this.bannerList = bannerList;
    }

    public List<IndexTextNotice> getSysNoticeList() {
        return sysNoticeList;
    }

    public void setSysNoticeList(List<IndexTextNotice> sysNoticeList) {
        this.sysNoticeList = sysNoticeList;
    }

    public List<IndexTextNotice> getRollNoticeList() {
        return rollNoticeList;
    }

    public void setRollNoticeList(List<IndexTextNotice> rollNoticeList) {
        this.rollNoticeList = rollNoticeList;
    }

    public IndexPicNotice getForceAd1() {
        return forceAd1;
    }

    public void setForceAd1(IndexPicNotice forceAd1) {
        this.forceAd1 = forceAd1;
    }

    public List<IndexMaster> getMasterList() {
        return masterList;
    }

    public void setMasterList(List<IndexMaster> masterList) {
        this.masterList = masterList;
    }

    public List<IndexMatch> getIndexMatchList() {
        return indexMatchList;
    }

    public void setIndexMatchList(List<IndexMatch> indexMatchList) {
        this.indexMatchList = indexMatchList;
    }

    public IndexPicNotice getForceAd2() {
        return forceAd2;
    }

    public void setForceAd2(IndexPicNotice forceAd2) {
        this.forceAd2 = forceAd2;
    }

    public List<IndexLottery> getLotteryList() {
        return lotteryList;
    }

    public void setLotteryList(List<IndexLottery> lotteryList) {
        this.lotteryList = lotteryList;
    }
}
