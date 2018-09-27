package com.util;

import domain.PolicyProductPackage;

import java.util.Random;

public class enumUtil {

    private static Random RANDOM = new Random();

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = RANDOM.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static void main(String[] args) {
        String policyProductPackage = randomEnum(PolicyProductPackage.class).toString();
        System.out.println(policyProductPackage);
    }
}
