package com.vegcale;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class DateUtils {

    public final int spanCount = 7;

    private final Calendar currentCalendar = Calendar.getInstance();

    private final Calendar calendarForCalculation = (Calendar) currentCalendar.clone();

    private int dayDifference;

    public int getSpanCount() {
        return spanCount;
    }

    public Calendar getCurrentCalendar() {
        return currentCalendar;
    }

    public Calendar getCalendarForCalculation() {
        return calendarForCalculation;
    }

    public void calculateDayDifference(Calendar start, Calendar current) {
        Date startDate = start.getTime();
        Date currentDate = current.getTime();

        long difference = currentDate.getTime() - startDate.getTime();
        setDayDifference((int)(TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)));
    }

    public int getDayDifferenceToFirstDayOfMonth() {
        return getDayDifference() - currentCalendar.get(Calendar.DATE);
    }

    public int getDayDifference() {
        return this.dayDifference;
    }

    public void setDayDifference(int dayDifference) {
        this.dayDifference = dayDifference;
    }
}
