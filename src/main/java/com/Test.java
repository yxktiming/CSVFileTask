package com;


import com.util.IDCardUtil;
import com.util.JDBCUtil;
import com.util.enumUtil;
import domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {


    public static void main(String[] args) {
        //获取连接
        Connection conn = JDBCUtil.getConn();
        //插入数据
        insert(conn);
    }

    //测试插入数据
    public static void insert(Connection conn) {
        // 开始时间
        Long begin = new Date().getTime();
        //身份证
        IdCardGenerator g = new IdCardGenerator();


        String prefix = "INSERT INTO `sp_policy` (`id`, `appnt_name`, `appnt_card_type`, `appnt_cart_no`, `appnt_sex`, `appnt_birthday`, `appnt_mobile`, `appnt_email`, `insured_appnt_relation`, `insured_name`, `insured_card_type`, `insured_card_no`, `insured_sex`, `insured_birthday`, `insured_shool_name`,`order_no`,`pay_prepay_id`, `policy_no`,`policy_product_code`,`policy_product_name`, `policy_product_package`, `policy_amount`, `policy_sign_time`, `policy_charge_time`, `policy_effect_time`, `policy_pay_state`,`policy_platform`, `policy_output`, `create_time`, `update_time`) VALUES ";
        PreparedStatement ps = null;

        try {
            // 保存sql后缀
            StringBuilder suffix = new StringBuilder();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // 比起st，pst会更好些
            ps = (PreparedStatement) conn.prepareStatement("");//准备执行语句
            // 外层循环，总提交事务次数
            long d = System.currentTimeMillis();

            long orderNo=300000000000L;
            long policyNo = 880500000011000L;
            for (int j = 1; j <= 2; j++) {
                for (int i = 1; i <= 5000; i++) {
                    Date date = new Date(d);
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String format = sd.format(date);
                    //投保人姓名
                    String appnt_name=enumUtil.randomEnum(AppntName.class).toString();
                    //投保人证件号
                    String appnt_cart_no = AppntCartNo.appntCartNo();
                    //投保人生日
                    String appnt_birthday = IDCardUtil.birthday(appnt_cart_no);
                    String pay_prepay_id="wx231449466337112ea787379e"+1200000000+i;
                    //投保人性别
                    String appnt_sex = IDCardUtil.sex(appnt_cart_no);
                    //被保人姓名
                    String insured_name=enumUtil.randomEnum(InsuredName.class).toString();
                    //被保人证件号
                    String insured_card_no = InsuredCardNo.insuredCardNo();
                    //被保人生日
                    String insured_birthday = IDCardUtil.birthday(insured_card_no);
                    //被保人性别
                    String insured_sex = IDCardUtil.sex(insured_card_no);
                    //与投保人关系
                    String insured_appnt_relation = null;
                    if (insured_sex == "0") {
                        insured_appnt_relation = "05";
                    } else if (insured_sex == "1") {
                        insured_appnt_relation = "06";
                    }


                    //套单编码
                    String policy_product_package = enumUtil.randomEnum(PolicyProductPackage.class).toString();
                    //套餐金额
                    float policyAmount=0;
                    if(policy_product_package.equals("XPX001")) {
                        policyAmount = 100;
                    }else if (policy_product_package.equals("XPX002")){
                        policyAmount=150;
                    }
                    long id = (j - 1) * 5000 + i;
                    System.out.println(id);
                    // 构建SQL后缀
                    suffix.append("('" + id + "', '"+appnt_name+"', '0', '" + appnt_cart_no + "', '" + appnt_sex + "', '" + appnt_birthday + "', '15229007135', '2283841136@qq.com', '" + insured_appnt_relation + "', '"+insured_name+"', '0', '" + insured_card_no + "', '" + insured_sex + "', '" + insured_birthday + "', '北京大学', '','wx231449466337112ea787379e1270190597','" + policyNo + "','119101', '海保人寿好生活学生平安意外险', '" + policy_product_package + "', '"+policyAmount+"', '2018-08-10', '2018-08-10', '2018-08-10', '02','02;20;00;1;"+ policyNo +";', '0','" + format + "','" + format + "'),");

                    policyNo++;
                    orderNo++;
                    d = d + 1000;

                }
                // 构建完整SQL
                String sql = prefix + suffix.substring(0, suffix.length() - 1);
                // 添加执行SQL
                ps.addBatch(sql);
                // 执行操作
                ps.executeBatch();
                // 提交事务
                conn.commit();
                // 清空上一次添加的数据
                suffix = new StringBuilder();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, ps, null);
        }
        System.out.println("插入完成");
    }

}
