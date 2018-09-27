package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IDCardUtil {

    public static String birthday(String cart_no) {
        if (cart_no.length() == 18) {
            StringBuilder str = new StringBuilder();
            cart_no = cart_no.substring(6, 14);
            str.append(cart_no);
            str.insert(4, "-").insert(7, "-");
            str.toString();
            if (str.toString() != "2018-08-10") {
                return str.toString();
            }
        }
        return "0";
    }

    public static String sex(String cart_no) {
        if (cart_no.length() == 18) {
            int s = Integer.parseInt(cart_no.substring(16, 17));

            if (s % 2 == 0) {
                return "1";
            }
            if (s % 2 != 0) {
                return "0";
            }
        }
        return "";
    }

    public static String insuredAppntRelation(String cart_no) {


        return null;
    }

    //出生日期字符串转化成Date对象
    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }

    //由出生日期获得年龄
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static void main(String[] args) throws Exception {
        String cart_no = "513433195806117692";
        int age = getAge(parse(birthday(cart_no)));
        System.out.println(age);

    }
}
