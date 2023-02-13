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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
        CustomAdapter mCustomAdapter = new CustomAdapter(this);
        mRecyclerView.setAdapter(mCustomAdapter);
        setRecyclerViewLayout(mRecyclerView);

        SpreadsheetTask mSpreadsheetTask = new SpreadsheetTask(getContext(), mCustomAdapter);
        mSpreadsheetTask.execute();

        return rootView;
    }

    private void changeToItemDetailFragment() {
        if (getActivity() == null) return;

        Fragment itemDetailFragment = new ItemDetailFragment();
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out
                )
                .setReorderingAllowed(true)
                .replace(R.id.navigation_container, itemDetailFragment)
                .commit();
    }

    @Override
    public void onClick(View view) {
        changeToItemDetailFragment();
    }

    private void setRecyclerViewLayout(RecyclerView mRecyclerView) {
        LinearLayoutManager verticalLinearLayoutManager =
                new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(verticalLinearLayoutManager);
    }
}