package com.tty.task.utils;

import java.sql.*;
import java.util.*;

public class JdbcUtil {

    protected String url, driverClass, username, password;

    public JdbcUtil() {
        initConfig();
    }

    public JdbcUtil(Properties JdbcConf) {
        initConfig(JdbcConf);
    }

    protected void initConfig() {
        this.username = (String) Constants.jdbcProperties().get("user");
        this.password = (String) Constants.jdbcProperties().get("password");
        this.url = Constants.COMPETITION_CONNECTION_URL;
        this.driverClass = (String) Constants.jdbcProperties().get("driver");
    }

    protected void initConfig(Properties JdbcConf) {
        this.username = (String) JdbcConf.get("user");
        this.password = (String) JdbcConf.get("password");
        this.url = (String) JdbcConf.get("url");
        this.driverClass = (String) JdbcConf.get("driver");
    }

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * @param sql an SQL statement that may contain one or more '?' IN
     * @param params the object list containing the input parameter values
     * @return an instance of {@code List<Map<String, Object>>} holding the column values
     */
    public List<Map<String, Object>> read(String sql, Object... params) {
        List<Map<String, Object>> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                List<String> fields = new ArrayList<>();
                for (int i = 0; i < meta.getColumnCount(); i++) {
                    fields.add(meta.getColumnLabel(i + 1));
                }
                while (rs.next()) {
                    Map<String, Object> item = new HashMap<>();
                    for (int i = 0; i < fields.size(); i++) {
                        String field = fields.get(i);
                        item.put(field, rs.getObject(i + 1));
                    }
                    result.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public Map<String, Object> readFirstRow(String sql, Object... params) {
        List<Map<String, Object>> list = read(sql, params);
        return list.size()>0? list.get(0) : null;
    }

    /**
     * @param type Class representing the Java data type to convert the first column to.
     * @param sql an SQL statement that may contain one or more '?' IN
     * @param params the object list containing the input parameter values
     * @param <T> the type of the class modeled by this Class object
     * @return an instance of {@code List<type>} holding the column values
     */
    public <T> List<T> readFirstColumn(Class<T> type, String sql, Object... params) {
        List<T> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getObject(1, type));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

}
