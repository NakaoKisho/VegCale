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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VegetableListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
        setVerticalLinearLayoutToRecyclerViewLayout(mRecyclerView);

        VegetableListRecyclerViewAdapter mVegetableListRecyclerViewAdapter =
                new VegetableListRecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(mVegetableListRecyclerViewAdapter);

        return rootView;
    }

    private void setVerticalLinearLayoutToRecyclerViewLayout(@NonNull RecyclerView mRecyclerView) {
        LinearLayoutManager verticalLinearLayoutManager =
                new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(verticalLinearLayoutManager);
    }
}