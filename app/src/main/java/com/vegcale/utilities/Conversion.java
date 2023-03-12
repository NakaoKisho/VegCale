package com.vegcale.utilities;

import android.icu.text.DateFormatSymbols;

import androidx.annotation.IntRange;

public class Conversion {
    private static String getMonthWord(@IntRange(from = 0, to = 11) int monthNumber) {
        DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();
        return mDateFormatSymbols.getMonths()[monthNumber];
    }

    public static String convertMonthNumberToWord(
            @IntRange(from = 0, to = 11) int from,
            @IntRange(from = 0, to = 11) int to
    ) {
        if (from == to) {
            return getMonthWord(from);
        }

        if (from > to) {
            throw new Error("A Parameter \"to\" should be higher than or equals to \"from\".");
        }

        String monthFrom = getMonthWord(from);
        String monthTo = getMonthWord(to);

        return monthFrom + "から" + monthTo;
    }
}
