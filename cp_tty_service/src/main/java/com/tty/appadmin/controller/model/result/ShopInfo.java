package com.tty.appadmin.controller.model.result;

import java.util.List;

/**
 * @Author: sunyishun
 * @Date: 2020/10/26
 * @Description:
 */
public class ShopInfo {
    int shopId;
    private String shopName;
    private List<Integer> saleLotteryIds;
    private int tcFlag; // 售卖体彩
    private int fcFlag; // 售卖福彩
    private int businessFlag;// 1:营业中 0:暂停营业
    private int myShop; // 1:我的店铺

    public ShopInfo() {
    }

    public ShopInfo(int shopId, String shopName, List<Integer> saleLotteryIds, int tcFlag, int fcFlag, int businessFlag, int myShop) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.saleLotteryIds = saleLotteryIds;
        this.tcFlag = tcFlag;
        this.fcFlag = fcFlag;
        this.businessFlag = businessFlag;
        this.myShop = myShop;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<Integer> getSaleLotteryIds() {
        return saleLotteryIds;
    }

    public void setSaleLotteryIds(List<Integer> saleLotteryIds) {
        this.saleLotteryIds = saleLotteryIds;
    }

    public int getTcFlag() {
        return tcFlag;
    }

    public void setTcFlag(int tcFlag) {
        this.tcFlag = tcFlag;
    }

    public int getFcFlag() {
        return fcFlag;
    }

    public void setFcFlag(int fcFlag) {
        this.fcFlag = fcFlag;
    }

    public int getBusinessFlag() {
        return businessFlag;
    }

    public void setBusinessFlag(int businessFlag) {
        this.businessFlag = businessFlag;
    }

    public int getMyShop() {
        return myShop;
    }

    public void setMyShop(int myShop) {
        this.myShop = myShop;
    }
}
