package com.autonomyway.model;

import java.text.NumberFormat;

public class Transformation {
    public static String toCashString(long cashInCents) {
        double cash = cashInCents;
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        cash /= Math.pow(10, fmt.getMaximumFractionDigits());
        return fmt.format(cash);
    }
}
