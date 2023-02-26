package com.vegcale.utility;

import android.icu.text.DateFormatSymbols;

import androidx.annotation.IntRange;

public class Conversion {
    public String convertMonthIntToText(@IntRange(from = 0, to = 11) int monthNumber) {
        DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();
        return mDateFormatSymbols.getMonths()[monthNumber];
    }

    public String convertMonthRangeIntToText(
            @IntRange(from = 0, to = 11) int from,
            @IntRange(from = 0, to = 11) int to
    ) {
        if (from >= to) {
            throw new Error("to should be higher than from.");
        }

        String monthFrom = convertMonthIntToText(from);
        String monthTo = convertMonthIntToText(to);

        return monthFrom + "から" + monthTo;
    }
}
