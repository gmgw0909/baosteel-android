package com.meetutech.baosteel.utils;

import java.math.BigDecimal;

public class NumberUtils {
    public static String getTwoN(double d) {
        BigDecimal b = new BigDecimal(d);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }
}
