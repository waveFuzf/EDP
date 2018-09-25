package com.zust.EDP.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Pattern;

@Component
public class CheckNumUtil {
    private static String [] cityCode={"11","12","13","14","15","21","22","23","31","32","33","34","35","36","37","41",
            "42","43","44","45","46","50","51","52","53","54","61","62","63","64","65","71","81","82","91"};

    private static int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
            8, 4, 2 };

    private static String formatnum = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

    private static String formatname ="^(([\\u4e00-\\u9fa5]{2,6})|([a-zA-Z]{2,16}))$";

    public boolean CheckNumName(String cardnum,String name){

        if (Pattern.compile(formatnum).matcher(cardnum).matches()
                &&Arrays.binarySearch(cityCode, cardnum.substring(0, 2))>=0
                &&Pattern.compile(formatname).matcher(name).matches()) {
            return cardnum.substring(17, 18).equals(getCheckCodeBySum(getPowerSum(cardnum.substring(0, 17).toCharArray())));
        }else {
            return false;
        }
    }

    private static int getPowerSum(char[] c) {
        int sum = 0;
        for (int i=0;i<c.length;i++){
            sum=sum + (c[i]- '0')*power[i];
        }
        return sum;
    }

    private static String getCheckCodeBySum(int sum17) {
        String checkCode = null;
        switch (sum17 % 11) {
            case 10:
                checkCode = "2";
                break;
            case 9:
                checkCode = "3";
                break;
            case 8:
                checkCode = "4";
                break;
            case 7:
                checkCode = "5";
                break;
            case 6:
                checkCode = "6";
                break;
            case 5:
                checkCode = "7";
                break;
            case 4:
                checkCode = "8";
                break;
            case 3:
                checkCode = "9";
                break;
            case 2:
                checkCode = "x";
                break;
            case 1:
                checkCode = "0";
                break;
            case 0:
                checkCode = "1";
                break;
        }
        return checkCode;
    }


}
