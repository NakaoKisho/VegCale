/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass
 * This handles the calendar and the vegetables data
 */
public class CalendarFragment extends Fragment {//implements LoaderManager.LoaderCallbacks<Cursor> {

    private Calendar calendarForCalculation;

    private Calendar currentCalendar;

//    /** Int field to get a year which a user currently picks */
//    private int mYear;
//
//    /** Int field to get a month which a user currently picks */
//    private int mMonth;
//
//    /** Int field to get a day which a user currently picks */
//    private int mDay;

    /** Int field to tell that it shows all tables in the database */
    private static final int VEGETABLE = 1;

    /** Int field to tell that it shows tables associated with specified month in the database */
    private static final int VEGETABLE_MONTH = 2;

    /** VegetableCursorAdapter field */
//    private VegetableCursorAdapter mVegetableCursorAdapter;

    private CalendarAdapter mCalendarAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DateUtils mDateUtils = new DateUtils();
//        mCalendar = Calendar.getInstance();
//        currentCalendar = (Calendar) mCalendar.clone();
//        mCalendar.set(2000, 0, 2);
//        mDateUtils.setDayDifference(mCalendar, currentCalendar);

        calendarForCalculation = mDateUtils.getCalendarForCalculation();
        currentCalendar = mDateUtils.getCurrentCalendar();
        calendarForCalculation.set(2000, 0, 2);
        mDateUtils.calculateDayDifference(calendarForCalculation, currentCalendar);

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Find the adView
        AdView mAdView = rootView.findViewById(R.id.adView);

        // Hold runtime information
        AdRequest adRequest = new AdRequest.Builder().build();

        // Load an ad
        mAdView.loadAd(adRequest);

        TextView displayYear = rootView.findViewById(R.id.display_year);

        TextView displayMonth = rootView.findViewById(R.id.display_month);

//        // Initiate calendar class
//        Calendar cal = Calendar.getInstance();
//
//        // get current year
//        mYear = cal.get(Calendar.YEAR);
//
//        // get current month
//        mMonth = cal.get(Calendar.MONTH) + 1;
//
//        // get current day
//        mDay = cal.get(Calendar.DAY_OF_MONTH);
//
//        // Find GridView
//        GridView gvDataDisplay = rootView.findViewById(R.id.recycler_view);
//
//        // Initiate the cursor adapter
//        mVegetableCursorAdapter = new VegetableCursorAdapter(getActivity(), null);
//
//        // Set the adapter onto the GridView
//        gvDataDisplay.setAdapter(mVegetableCursorAdapter);

        // Find RecyclerView
        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);

        // Set GridView has 7 columns to RecyclerView
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mDateUtils.getSpanCount()));
        mRecyclerView.getLayoutManager().scrollToPosition(mDateUtils.getDayDifferenceToFirstDayOfMonth());

        // Initiate the cursor adapter
        mCalendarAdapter = new CalendarAdapter(displayYear, displayMonth);

        // Set the adapter onto the RecyclerView
        mRecyclerView.setAdapter(mCalendarAdapter);

//        // Prepare the loader.  Either re-connect with an existing one,
//        // or start a new one.
//        LoaderManager.getInstance(this).initLoader(VEGETABLE_MONTH, null, this);

        return rootView;
    }

//    @NonNull
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
//        switch (id) {
//            case VEGETABLE:
//                // Define a projection that specifies which columns from the database
//                // you will actually use after this query
//                String[] vegetableProjection = {
//                        VegetableContract.VegetableEntry._ID,
//                        VegetableContract.VegetableEntry.COLUMN_NAME,
//                        VegetableContract.VegetableEntry.COLUMN_DESCRIPTION1,
//                        VegetableContract.VegetableEntry.COLUMN_DESCRIPTION2,
//                        VegetableContract.VegetableEntry.COLUMN_MONTH,
//                        VegetableContract.VegetableEntry.COLUMN_WEEK_OF_MONTH,
//                        VegetableContract.VegetableEntry.COLUMN_IMAGE,
//                };
//
//                // Perform a query on the provider using the ContentResolver
//                // Use the {@link ScheduleEntry#CONTENT_URI} to access the pet data
//                return new CursorLoader(getActivity(),
//                        VegetableContract.VegetableEntry.CONTENT_URI,
//                        vegetableProjection,
//                        null,
//                        null,
//                        null);
//            case VEGETABLE_MONTH:
//                String[] vegetableProjectionMonth = {
//                        VegetableContract.VegetableEntry._ID,
//                        VegetableContract.VegetableEntry.COLUMN_NAME,
//                        VegetableContract.VegetableEntry.COLUMN_DESCRIPTION1,
//                        VegetableContract.VegetableEntry.COLUMN_DESCRIPTION2,
//                        VegetableContract.VegetableEntry.COLUMN_MONTH,
//                        VegetableContract.VegetableEntry.COLUMN_WEEK_OF_MONTH,
//                        VegetableContract.VegetableEntry.COLUMN_IMAGE,
//                };
//
//                return new CursorLoader(getActivity(),
//                        Uri.withAppendedPath(VegetableContract.VegetableEntry.CONTENT_URI, String.valueOf(mMonth)),
//                        vegetableProjectionMonth,
//                        null,
//                        null,
//                        null);
//            default:
//                throw new IllegalArgumentException(id + " doesn't match any cases");
//        }
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
//        switch(loader.getId()) {
//            case VEGETABLE:
//            case VEGETABLE_MONTH:
//                mVegetableCursorAdapter.swapCursor(data);
//                break;
//            default:
//                throw new IllegalArgumentException(loader.getId() + " doesn't match any cases");
//        }
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
//        switch(loader.getId()) {
//            case VEGETABLE:
//            case VEGETABLE_MONTH:
//                mVegetableCursorAdapter.swapCursor(null);
//                break;
//            default:
//                throw new IllegalArgumentException(loader.getId() + " doesn't match any cases");
//        }
//    }
}