package com.vegcale;


import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

import static com.vegcale.CalendarFragment.currentCalendar;
import static com.vegcale.CalendarFragment.mCalendar;

/**
 * Note
 * Calculate
 *      all days in the current month
 *      a first day of a week in the current month
 *      a end day of a month in the current month
 *
 * Set
 *      Numbers "Sunday" and "Saturday" to red
 *      empty days to grey color
 *      TextView's and ImageView's visibility to gone in the grey colored cells
 * Check if
 *      It is in the current date
 *
 * Method
 *      getDays()
 *      isCurrentMonth()
 *      getWeek()
 */
public class DateUtils {

    public static final int SPAN_COUNT = 7;

    private final int TO_THE_START = 0;

    private final int FROM_THE_END = 1;

    private final String TAG = "DateUtility.class";

//    /** Calendar field for setting*/
//    Calendar mCalendar;
//
//    /** Calendar field for saving current values*/
//    Calendar currentCalendar;

    /** Constructor */
    public DateUtils() {
        init();
//        mCalendar = Calendar.getInstance();
//        currentCalendar  = (Calendar) mCalendar.clone();
    }

    private void init() {
//        Log.d(TAG, "init(): Created");
    }

    /**
     *
     * @return Al displayed days in UI
     */
    public int getAllDisplayedDays() {
        return getEmptyDaysToTheStart() + getDaysFromTheStartToTheEnd() + getEmptyDaysFromTheEnd();
    }

    /**
     * 月の日付数を取得　→　最初の日付と最後の日付の曜日を取得　→　開始日までの空白と終了日からの空白文の数を計算
     * →　開始日までと終了日後のpositionをcontinueで抜け出す
     * @return The number of the day in a month
     */
    public int getDaysFromTheStartToTheEnd() {
        // Get the last day of the month
        return mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     *
     * @return The number of empty days until the start
     */
    public int getEmptyDaysToTheStart() {
        String dayOfTheStartWeek;
//        Log.d(TAG, "getEmptyDaysToTheStart: mCalendar.get(Calendar.MONTH) = " + mCalendar.get(Calendar.MONTH));
        // Set the calendar to the first day
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        // Change the number to corresponding string
        dayOfTheStartWeek = mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);

        if(isCurrentDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH))) {
            // Reset the Calendar
            resetCalendar();
        }

        return getEmptyDays(dayOfTheStartWeek, TO_THE_START);
    }

    /**
     *
     * @return The number of empty days until the end
     */
    public int getEmptyDaysFromTheEnd() {
        int dayOfTheMonth;
        String dayOfTheEndWeek;

        // Get the last day of the month
        dayOfTheMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        // Set the calendar to the last day
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfTheMonth);
        // Change the number to corresponding string
        dayOfTheEndWeek = mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);

        if(isCurrentDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH))) {
            // Reset the Calendar
            resetCalendar();
        }

        return getEmptyDays(dayOfTheEndWeek, FROM_THE_END);
    }

    /**
     *
     * @return The number of weeks in a month
     */
    public int getFULL_HEIGHT_COUNT() {
        return mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    /**
     * Reset the calendar
     */
    private void resetCalendar() {
        mCalendar = currentCalendar;
    }

    /**
     *
     * @param dayOfTheWeek String of DAY_OF_WEEK
     * @param direction Indicate to the start or from the end of the month
     * @return The number of empty days
     */
    public int getEmptyDays(String dayOfTheWeek, int direction) {

        int emptyDays = 0;

        if(dayOfTheWeek == null) {
            return emptyDays;
        }

        if(TO_THE_START == direction) {
            switch(dayOfTheWeek) {
                case "Mon":
                    return emptyDays + 1;
                case "Tue":
                    return emptyDays + 2;
                case "Wed":
                    return emptyDays + 3;
                case "Thu":
                    return emptyDays + 4;
                case "Fri":
                    return emptyDays + 5;
                case "Sat":
                    return emptyDays + 6;
                default:
                    return emptyDays;
            }
        } else if(FROM_THE_END == direction) {
            switch(dayOfTheWeek) {
                case "Sun":
                    return emptyDays + 6;
                case "Mon":
                    return emptyDays + 5;
                case "Tue":
                    return emptyDays + 4;
                case "Wed":
                    return emptyDays + 3;
                case "Thu":
                    return emptyDays + 2;
                case "Fri":
                    return emptyDays + 1;
                default:
                    return emptyDays;
            }
        } else {
            return emptyDays;
        }

    }

    public void setPreviousMonth() {
        mCalendar.add(Calendar.MONTH, -1);
//        Log.d(TAG, "setPreviousYear: mCalendar.add(Calendar.MONTH, -1) = " + mCalendar.get(Calendar.MONTH)); OK(9)
    }

    public void setNextMonth() {
        mCalendar.add(Calendar.MONTH, 1);
//        Log.d(TAG, "setNextYear: mCalendar.add(Calendar.MONTH, 1) = " + mCalendar.get(Calendar.MONTH)); OK
    }

    private boolean isCurrentDate(int year, int month) {
        return year == currentCalendar.get(Calendar.YEAR)
                && month == currentCalendar.get(Calendar.MONTH);
    }

//
//    public List<Date> getDays() {
//
//        //現在の状態を保持
//        Date currentDate = mCalendar.getTime();
//        //GridViewに表示するマスの合計を計算
//        int count = getWeekOfCurrentMonth() * 7 ;
//
//        //当月のカレンダーに表示される前月分の日数を計算
//        // Date is set to 1st
//        mCalendar.set(Calendar.DATE, 1); // Date == 1
//        // In the code above, Date is 1st so
//        // the day of a week (not a month) is 1
//        // 1 is subtracted from the day of a week so
//        // 0 is inserted to dayOfWeek
//        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK) - 1; // dayOfWeek == 0
//        // Adds or subtracts the specified amount of time to the given calendar field, based on the calendar's rules
//        mCalendar.add(Calendar.DATE, -dayOfWeek);
//
//        List<Date> days = new ArrayList<>();
//
//        for (int i = 0; i < count; i ++) {
//            // Returns a Date object representing
//            // this Calendar's time value in millisecond
//            days.add(mCalendar.getTime());
//            mCalendar.add(Calendar.DATE, 1);
//        }
//
//        //状態を復元
//        mCalendar.setTime(currentDate);
//
//        return days;
//    }
//
//    //当月かどうか確認
//    public boolean isCurrentMonth(Date date) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.US);
//        String currentMonth = format.format(mCalendar.getTime());
//        return currentMonth.equals(format.format(date));
//    }
//
//    // Get the number of weeks
//    public int getWeekOfCurrentMonth() {
//        // Returns the maximum number of the week which the current month has
//        return mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
//    }
//
//    //曜日を取得
//    public int getDayOfWeek(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return calendar.get(Calendar.DAY_OF_WEEK);
//    }
//
//    //翌月へ
//    public void nextMonth() {
//        mCalendar.add(Calendar.MONTH, 1);
//    }
//
//    //前月へ
//    public void prevMonth() {
//        mCalendar.add(Calendar.MONTH, -1);
//    }
}
