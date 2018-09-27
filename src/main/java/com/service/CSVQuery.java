package com.service;

import com.util.JDBCUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class CSVQuery {
    private static final Logger logger = LogManager.getLogger(CSVQuery.class.getName());
    static StringBuffer policyNo =null;



    /**
     * 查询所有保单并放入List中
     *
     * @return list
     */
    public static List<Map<String, Object>> QueryAll(String previousTime, String latterTime, int currentRow, int pageSize) {
        List<Map<String, Object>> list = new ArrayList<>();
        policyNo = new StringBuffer();
        //获取数据库连接
        Connection conn = JDBCUtil.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //查询sql语句
        String sql = "select id," +
                "policy_no," +
                "policy_product_package," +
                "policy_sign_time," +
                "policy_effect_time," +
                "policy_charge_time," +
                "appnt_name," +
                "appnt_sex," +
                "appnt_birthday," +
                "appnt_card_type," +
                "appnt_cart_no," +
                "appnt_mobile," +
                "appnt_email," +
                "insured_appnt_relation," +
                "insured_name," +
                "insured_sex," +
                "insured_birthday," +
                "insured_card_type," +
                "insured_card_no," +
                "insured_shool_name" +
                " from Sp_policy where policy_pay_state='02' limit " + pageSize + "";
        try {
            if (conn != null) {
                ps = conn.prepareStatement(sql);
            }
            if (ps != null) {
                rs = ps.executeQuery();
            }
            if (rs != null) {
                while (rs.next()) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    if (rs.getString("policy_no") == null
                            || rs.getString("policy_product_package") == null
                            || rs.getDate("policy_sign_time") == null
                            || rs.getDate("policy_effect_time") == null
                            || rs.getDate("policy_charge_time") == null
                            || rs.getString("appnt_name") == null
                            || rs.getString("appnt_sex") == null
                            || rs.getString("appnt_birthday") == null
                            || rs.getString("appnt_card_type") == null
                            || rs.getString("appnt_cart_no") == null
                            || rs.getString("appnt_mobile") == null
                            || rs.getString("appnt_email") == null
                            || rs.getString("insured_appnt_relation") == null
                            || rs.getString("insured_name") == null
                            || rs.getString("insured_sex") == null
                            || rs.getString("insured_birthday") == null
                            || rs.getString("insured_card_type") == null
                            || rs.getString("insured_card_no") == null
                            || rs.getString("insured_shool_name") == null

                            || rs.getString("policy_no").isEmpty()
                            || rs.getString("appnt_name").isEmpty()
                            || rs.getString("appnt_sex").isEmpty()
                            || rs.getString("appnt_birthday").isEmpty()
                            || rs.getString("appnt_card_type").isEmpty()
                            || rs.getString("appnt_cart_no").isEmpty()
                            || rs.getString("appnt_mobile").isEmpty()
                            || rs.getString("appnt_email").isEmpty()
                            || rs.getString("insured_appnt_relation").isEmpty()
                            || rs.getString("insured_name").isEmpty()
                            || rs.getString("insured_sex").isEmpty()
                            || rs.getString("insured_card_type").isEmpty()
                            || rs.getString("insured_card_no").isEmpty()
                            || rs.getString("insured_shool_name").isEmpty()) {
                        logger.error("id:" + rs.getString("id") + "数据为空值或null");
                        continue;
                    }
                    map.put("1", rs.getLong("id"));
                    map.put("2", "S"+rs.getString("policy_no"));
                    map.put("3", rs.getString("policy_product_package"));
                    map.put("4", rs.getDate("policy_sign_time"));
                    map.put("5", rs.getDate("policy_effect_time"));
                    map.put("6", rs.getDate("policy_charge_time"));
                    map.put("7", rs.getString("appnt_name"));
                    map.put("8", rs.getString("appnt_sex"));
                    map.put("9", rs.getString("appnt_birthday"));
                    map.put("10", rs.getString("appnt_card_type"));
                    map.put("11", rs.getString("appnt_cart_no"));
                    map.put("12", rs.getString("appnt_mobile"));
                    map.put("13", rs.getString("appnt_email"));
                    map.put("14", rs.getString("insured_appnt_relation"));
                    map.put("15", rs.getString("insured_name"));
                    map.put("16", rs.getString("insured_sex"));
                    map.put("17", rs.getString("insured_birthday"));
                    map.put("18", rs.getString("insured_card_type"));
                    map.put("19", rs.getString("insured_card_no"));
                    map.put("20", rs.getString("insured_shool_name"));
                    list.add(map);
                    policyNo.append("'").append(rs.getString("policy_no")).append("'").append(",");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, ps, rs);
        }
        return list;
    }

    /**
     * 查询行数
     *
     * @return rowNum
     */
    public static int QueryRowNum(String previousTime, String latterTime) {
        int rowNum = 0;
        //获取连接
        Connection conn = JDBCUtil.getConn();

        ResultSet rs = null;

        String sql = "select count(*) from Sp_policy where policy_output='0' and policy_pay_state='02'";
        PreparedStatement ps = null;
        try {
            if (conn != null) {
                ps = conn.prepareStatement(sql);
            }
            if (ps != null) {
                rs = ps.executeQuery();
            }
            if (rs != null) {
                if (rs.next()) {
                    rowNum = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, ps, rs);
        }
        return rowNum;
    }


}
