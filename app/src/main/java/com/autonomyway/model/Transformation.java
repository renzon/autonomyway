package com.autonomyway.model;

import android.content.res.Resources;

import com.autonomyway.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class Transformation {
    private static final int HOURS_IN_DAY = 24;
    private static final int HOURS_IN_MONTH = HOURS_IN_DAY * 30;
    private static final int HOURS_IN_YEAR = HOURS_IN_MONTH * 30;
    private static final int CENTS_MULTIPLIER = 100;

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
        StringBuilder builder = new StringBuilder(cashToCurrency(rate));
        builder.append("/");
        builder.append(resources.getString(R.string.hour));
        return builder.toString();
    }

    public static String durationForHumans(long hoursToLive, Resources resources) {
        long remainingHours = Math.abs(hoursToLive);
        StringBuilder builder = new StringBuilder("");
        long years = remainingHours / HOURS_IN_YEAR;
        String single = resources.getString(R.string.year);
        String plural = resources.getString(R.string.year_plural);
        appendUnit(builder, years, single, plural);
        if (years > 0) {
            remainingHours = remainingHours % HOURS_IN_YEAR;
        }
        long months = remainingHours / HOURS_IN_MONTH;
        single = resources.getString(R.string.month);
        plural = resources.getString(R.string.month_plural);
        appendUnit(builder, months, single, plural);
        if (months > 0) {
            remainingHours = remainingHours % HOURS_IN_MONTH;
        }
        single = resources.getString(R.string.hour);
        plural = resources.getString(R.string.hour_plural);
        appendUnit(builder, remainingHours, single, plural);
        if (hoursToLive < 0) {
            return "- " + builder.toString();
        }
        return builder.toString();
    }

    private static void appendUnit(StringBuilder builder, long unit, String single, String plural) {
        if (builder.length() > 0 && unit > 0) {
            builder.append(", ");
        }
        if (unit == 1) {
            builder.append(1);
            builder.append(" ");
            builder.append(single);
        } else if (unit > 1) {
            builder.append(unit);
            builder.append(" ");
            builder.append(plural);
        }
    }
}
