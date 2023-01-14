/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vegcale.data.VegetableContract;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass
 * This handles the calendar and the vegetables data
 */
public class CalendarFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    /** Int field to get a year which a user currently picks */
    private int mYear;

    /** Int field to get a month which a user currently picks */
    private int mMonth;

    /** Int field to get a day which a user currently picks */
    private int mDay;

    /** Int field to tell that it shows all tables in the database */
    private static final int VEGETABLE = 1;

    /** Int field to tell that it shows tables associated with specified month in the database */
    private static final int VEGETABLE_MONTH = 2;

    /** VegetableCursorAdapter field */
    private VegetableCursorAdapter mVegetableCursorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
        CustomAdapter mCustomAdapter = new CustomAdapter();
        mRecyclerView.setAdapter(mCustomAdapter);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);


//        // Find calendarView
//        CalendarView calendarView = rootView.findViewById(R.id.calendar_view);
//
//        // If API level is lower than 19
//        if ((Build.VERSION.SDK_INT) <= 19) {
//
//            // Get Layout
//            ViewGroup.LayoutParams params = calendarView.getLayoutParams();
//
//            // Get the screen's density scale
//            final float scale = getActivity().getResources().getDisplayMetrics().density;
//
//            // Convert the dps to pixels, based on density scale and change it's height
//            params.height = (int) (550 * scale + 0.5f);
//
//            // Apply it
//            calendarView.setLayoutParams(params);
//        }
//
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
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                if(mYear != year || mMonth != month + 1 || mDay != dayOfMonth) {
//                    mYear = year;
//                    mMonth = month + 1;
//                    mDay = dayOfMonth;
//                }
//                restartLoader();
//            }
//        });
//
//        // Find GridView
//        GridView gvDataDisplay = rootView.findViewById(R.id.grid_view);
//
//        // Find the empty view
//        View emptyView = rootView.findViewById(R.id.empty_view);
//
//        // Set the empty view onto the GridView
//        gvDataDisplay.setEmptyView(emptyView);
//
//        // Initiate the cursor adapter
//        mVegetableCursorAdapter = new VegetableCursorAdapter(getActivity(), null);
//
//        // Set the adapter onto the GridView
//        gvDataDisplay.setAdapter(mVegetableCursorAdapter);
//
//        // Prepare the loader.  Either re-connect with an existing one,
//        // or start a new one.
//        LoaderManager.getInstance(this).initLoader(VEGETABLE_MONTH, null, this);
        
        return rootView;
    }

    public void setRecyclerViewLayoutManager() {

    }

    public void restartLoader() {
        LoaderManager.getInstance(this).restartLoader(VEGETABLE_MONTH, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case VEGETABLE:
                // Define a projection that specifies which columns from the database
                // you will actually use after this query
                String[] vegetableProjection = {
                        VegetableContract.VegetableEntry._ID,
                        VegetableContract.VegetableEntry.COLUMN_NAME,
                        VegetableContract.VegetableEntry.COLUMN_DESCRIPTION1,
                        VegetableContract.VegetableEntry.COLUMN_DESCRIPTION2,
                        VegetableContract.VegetableEntry.COLUMN_MONTH,
                        VegetableContract.VegetableEntry.COLUMN_WEEK_OF_MONTH,
                        VegetableContract.VegetableEntry.COLUMN_IMAGE,
                };

                // Perform a query on the provider using the ContentResolver
                // Use the {@link ScheduleEntry#CONTENT_URI} to access the pet data
                return new CursorLoader(getActivity(),
                        VegetableContract.VegetableEntry.CONTENT_URI,
                        vegetableProjection,
                        null,
                        null,
                        null);
            case VEGETABLE_MONTH:
                String[] vegetableProjectionMonth = {
                        VegetableContract.VegetableEntry._ID,
                        VegetableContract.VegetableEntry.COLUMN_NAME,
                        VegetableContract.VegetableEntry.COLUMN_DESCRIPTION1,
                        VegetableContract.VegetableEntry.COLUMN_DESCRIPTION2,
                        VegetableContract.VegetableEntry.COLUMN_MONTH,
                        VegetableContract.VegetableEntry.COLUMN_WEEK_OF_MONTH,
                        VegetableContract.VegetableEntry.COLUMN_IMAGE,
                };

                return new CursorLoader(getActivity(),
                        Uri.withAppendedPath(VegetableContract.VegetableEntry.CONTENT_URI, String.valueOf(mMonth)),
                        vegetableProjectionMonth,
                        null,
                        null,
                        null);
            default:
                throw new IllegalArgumentException(id + " doesn't match any cases");
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        switch(loader.getId()) {
            case VEGETABLE:
            case VEGETABLE_MONTH:
                mVegetableCursorAdapter.swapCursor(data);
                break;
            default:
                throw new IllegalArgumentException(loader.getId() + " doesn't match any cases");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        switch(loader.getId()) {
            case VEGETABLE:
            case VEGETABLE_MONTH:
                mVegetableCursorAdapter.swapCursor(null);
                break;
            default:
                throw new IllegalArgumentException(loader.getId() + " doesn't match any cases");
        }
    }
}