package com.autonomyway.model;

import android.content.res.Resources;

import com.autonomyway.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
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

    public static String dateToString(Date date) {
        DateFormat fmt = DateFormat.getDateInstance(DateFormat.SHORT);
        return fmt.format(date);
    }

    public static long numberTocash(String number) throws ParseException {
        if (number == null || "".equals(number)) {
            return 0;
        }
        Number n = null;
        try {
            //  workaround https://code.google.com/p/android/issues/detail?id=2626
            n = NumberFormat.getInstance(Locale.US).parse(number);
        } catch (ParseException e) {

        }
        double value = n.doubleValue();
        value *= CENTS_MULTIPLIER;
        return Math.round(value);


    }

    private static String transform(long cashInCents, NumberFormat fmt) {
        double cash = cashInCents;
        cash /= CENTS_MULTIPLIER;
        fmt.setMaximumFractionDigits(2);
        fmt.setMinimumFractionDigits(2);
        return fmt.format(cash);
    }


    public static String durationToString(long duration) {
        Long hour = duration / 60;

        Long minutes = duration % 60;
        String minutesStr = minutes.toString();
        if (minutesStr.length() == 1) {
            minutesStr = "0" + minutesStr;
        }
        return hour + ":" + minutesStr;
    }

    public static String cashRate(long rate, Resources resources) {
        StringBuilder builder=new StringBuilder(cashToCurrency(rate));
        builder.append("/");
        builder.append(resources.getString(R.string.hour));
        return builder.toString();
    }

    public static String durationForHumans(long hoursToLive) {
        return hoursToLive+" hours";
    }
}
