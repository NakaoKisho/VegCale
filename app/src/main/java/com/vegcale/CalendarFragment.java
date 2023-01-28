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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
        CustomAdapter mCustomAdapter = new CustomAdapter();
        mRecyclerView.setAdapter(mCustomAdapter);
        setRecyclerViewLayout(mRecyclerView);

        SpreadsheetTask mSpreadsheetTask = new SpreadsheetTask(getContext(), mCustomAdapter);
        mSpreadsheetTask.execute();

        return rootView;
    }

    private void setRecyclerViewLayout(RecyclerView mRecyclerView) {
        final int twoColumn = 2;
        StaggeredGridLayoutManager twoColumnStaggeredGridLayout =
                new StaggeredGridLayoutManager(twoColumn, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(twoColumnStaggeredGridLayout);
    }
}