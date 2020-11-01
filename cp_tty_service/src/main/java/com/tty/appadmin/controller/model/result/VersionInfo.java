package com.tty.appadmin.controller.model.result;

/**
 * @Author: sunyishun
 * @Date: 2020/10/26
 * @Description: 平台版本信息
 */
public class VersionInfo {
    private String platform; // 平台
    private String newestVersion; // 最新版本
    private int forceUpdateFlag; // 是否强制更新
    private String downloadUrl; // 下载地址

    public VersionInfo() {
    }

    public VersionInfo(String platform, String newestVersion, int forceUpdateFlag, String downloadUrl) {
        this.platform = platform;
        this.newestVersion = newestVersion;
        this.forceUpdateFlag = forceUpdateFlag;
        this.downloadUrl = downloadUrl;
    }
}
