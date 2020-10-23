package com.tty.task.utils;

import java.util.Properties;

/**
 * @Author: dukeqiang
 * @Date: 2020/10/13 18:56
 */
public interface Constants {

    String M_DRIVER_NEW = "com.mysql.cj.jdbc.Driver";

    /**
     * 赛事信息库
     */
    String COMPETITION_CONNECTION_URL = "jdbc:mysql://localhost:3306/tty?useUnicode=true&characterEncoding=utf8&useSSL=false&autoReconnect=true&serverTimezone=CST&rewriteBatchedStatements=true";
    String COMPETITION_CONNECTION_USER = "root";
    String COMPETITION_CONNECTION_PASSWORD = "du123456";

    static Properties jdbcProperties() {
        Properties jdbcProperties = new Properties();
        jdbcProperties.setProperty("user", Constants.COMPETITION_CONNECTION_USER);
        jdbcProperties.setProperty("password", Constants.COMPETITION_CONNECTION_PASSWORD);
        jdbcProperties.setProperty("driver", Constants.M_DRIVER_NEW);
        jdbcProperties.setProperty("url", Constants.COMPETITION_CONNECTION_URL);
        return jdbcProperties;
    }
}
