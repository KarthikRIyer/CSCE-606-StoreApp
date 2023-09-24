package com.example;

public class Util {

    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String maskCCNumber(String ccNumber) {
        return "XXXXXXXXXXXX" + ccNumber.substring(12, 16);
    }

}
