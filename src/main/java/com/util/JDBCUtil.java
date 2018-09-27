package com.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {
    private static Properties p = new Properties();

    static {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
            p.load(inputStream);
            Class.forName(p.getProperty("db.driverClassName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getConn() {
        try {
            return DriverManager.getConnection(
                    p.getProperty("db.url"),
                    p.getProperty("db.username"),
                    p.getProperty("db.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            //关闭结果集
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭预编译语句
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    //关闭数据库连接
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
