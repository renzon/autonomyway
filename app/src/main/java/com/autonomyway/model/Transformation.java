package com.autonomyway.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Transformation {

    private static final long CENTS_MULTIPLIER = 100;

    public static String cashToCurrency(long cashInCents) {
        return transform(cashInCents, NumberFormat.getCurrencyInstance());
    }

    public static String cashToNumber(long cashInCents) {
        NumberFormat instance = NumberFormat.getInstance(Locale.US);
        instance.setGroupingUsed(false);
        return transform(cashInCents, instance);
    }

    public static long numberTocash(String number) throws ParseException {
        if (number == null || "".equals(number)) {
            return 0;
        }
        Number n = null;
        try {
            NumberFormat fmt = NumberFormat.getInstance();
            n = fmt.parse(number);
        } catch (ParseException e) {
//  workaround https://code.google.com/p/android/issues/detail?id=2626
            n = NumberFormat.getInstance(Locale.US).parse(number);
        }
        double value = n.doubleValue();
        value *= CENTS_MULTIPLIER;
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
