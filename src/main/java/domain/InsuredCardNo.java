package domain;

import com.IdCardGenerator;
import com.util.IDCardUtil;

public class InsuredCardNo {
    public static String insuredCardNo() {
        //投保人年龄
        String insured_card_no = null;
        try {
            while (true) {
                IdCardGenerator g = new IdCardGenerator();
                insured_card_no = g.generate();
                int age = IDCardUtil.getAge(IDCardUtil.parse(IDCardUtil.birthday(insured_card_no)));
                if(age<18){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insured_card_no;
    }

    public static void main(String[] args) {
        System.out.println(insuredCardNo());
    }
}
