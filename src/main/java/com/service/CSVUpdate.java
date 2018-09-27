package com.service;

import com.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CSVUpdate {
    /**
     * 更新保单号
     */
    public static void UpdatePolicyNo() {
        //获取数据库连接
        Connection conn = JDBCUtil.getConn();
        PreparedStatement ps = null;
        StringBuffer policyNo = CSVQuery.policyNo;
        //更新sql语句
        String sql = "update Sp_policy set policy_output='1' where policy_no in (" + policyNo + "0)";
        try {
            if (conn != null) {
                ps = conn.prepareStatement(sql);
            }
            if (ps != null) {
                int i = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, ps, null);
        }
    }
}
