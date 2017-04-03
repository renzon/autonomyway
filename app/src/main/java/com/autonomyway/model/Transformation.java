package com.autonomyway.model;

import java.text.NumberFormat;
import java.text.ParseException;

public class Transformation {
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();
    private static final long CENTS_MULTIPLIER=100;

    public static String cashToCurrency(long cashInCents) {
        return transform(cashInCents, CURRENCY_FORMAT);
    }

    public static String cashToNumber(long cashInCents) {
        return transform(cashInCents, NUMBER_FORMAT);
    }

    public static long numberTocash(String number) {
        if (number==null || "".equals(number)){
            return 0;
        }
        Number n =null;
        try {
            n = NUMBER_FORMAT.parse(number);
        } catch (ParseException e) {

        }
        double value=n.doubleValue();
        value*=CENTS_MULTIPLIER;
        return (long) value;


    }

    private static String transform(long cashInCents, NumberFormat fmt) {
        double cash = cashInCents;
        cash /= CENTS_MULTIPLIER;
        fmt.setMaximumFractionDigits(2);
        fmt.setMinimumFractionDigits(2);
        return fmt.format(cash);
    }


}
