package domain;

import com.util.enumUtil;

public enum InsuredAppntRelation {
    SON("05"),
    Daughter("06");

    public String value;

     InsuredAppntRelation(String value){
        this.value = value;
    }

    public static void main(String[] args) {
        String insured_appnt_relation= enumUtil.randomEnum(InsuredAppntRelation.class).value.toString();
        System.out.println(insured_appnt_relation);
    }
}
