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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.vegcale.data.VegetableTipContract;

/**
 * A simple {@link Fragment} subclass.
 * This handles tips for growing vegetables
 */
public class TipFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    /** VegetableTipCursorAdapter field */
    private VegetableTipCursorAdapter mVegetableTipCursorAdapter;

    /** Int field to tell that it shows all tables in the database */
    private static final int VEGETABLE_TIPS = 200;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
//        View rootView = inflater.inflate(R.layout.fragment_tip, container, false);

//        // Find the adView
//        AdView mAdView = rootView.findViewById(R.id.adView);
//
//        // Hold runtime information
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        // Load an ad
//        mAdView.loadAd(adRequest);
//
//        // Find the ListView
//        ListView lvDataDisplay = rootView.findViewById(R.id.list_view);
//
//        // Find the empty view
//        View emptyView = rootView.findViewById(R.id.empty_view);
//
//        // Set the empty view onto the ListView
//        lvDataDisplay.setEmptyView(emptyView);
//
//        // Initiate the cursor adapter
//        mVegetableTipCursorAdapter = new VegetableTipCursorAdapter(getActivity(), null);
//
//        // Set the adapter onto the ListView
//        lvDataDisplay.setAdapter(mVegetableTipCursorAdapter);
//
//        // Prepare the loader.  Either re-connect with an existing one,
//        // or start a new one.
//        LoaderManager.getInstance(this).initLoader(VEGETABLE_TIPS, null, this);
//
//        // Listen to touches and change the height
//        lvDataDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                TextView tipDescription = view.findViewById(R.id.tip_article);
//
//                // Get Layout
//                ViewGroup.LayoutParams params = tipDescription.getLayoutParams();
//                if (ViewGroup.LayoutParams.WRAP_CONTENT == params.height) {
//
//                    // Change it's height
//                    params.height = 0;
//                } else {
//
//                    // Change it's height
//                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                }
//
//                // Apply it
//                tipDescription.setLayoutParams(params);
//            }
//        });

        return rootView;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] vegetableTipsProjection = {
                VegetableTipContract.VegetableTipEntry._ID,
                VegetableTipContract.VegetableTipEntry.COLUMN_TITLE,
                VegetableTipContract.VegetableTipEntry.COLUMN_DESCRIPTION,
                VegetableTipContract.VegetableTipEntry.COLUMN_IMAGE,
        };

        // Perform a query on the provider using the ContentResolver.
        // Use the {@link ScheduleEntry#CONTENT_URI} to access the pet data.
        return new CursorLoader(getActivity(),
                VegetableTipContract.VegetableTipEntry.CONTENT_URI,
                vegetableTipsProjection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mVegetableTipCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mVegetableTipCursorAdapter.swapCursor(null);
    }
}