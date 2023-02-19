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

import com.vegcale.utility.fragment.FragmentUtility;

public class VegetableListFragment extends Fragment implements View.OnClickListener {
    private FragmentUtility mFragmentUtility;
    private ItemDetailFragment ItemDetailFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        mFragmentUtility = new FragmentUtility(getActivity());
        ItemDetailFragment = new ItemDetailFragment();
        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
        setVerticalLinearLayoutToRecyclerViewLayout(mRecyclerView);

        VegetableListRecyclerViewAdapter mVegetableListRecyclerViewAdapter =
                new VegetableListRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mVegetableListRecyclerViewAdapter);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        mFragmentUtility.changeFragment(
                ItemDetailFragment,
                FragmentUtility.ItemDetailFragmentTag,
                FragmentUtility.SlideAnimation.LeftToRight
        );
    }

    private void setVerticalLinearLayoutToRecyclerViewLayout(@NonNull RecyclerView mRecyclerView) {
        LinearLayoutManager verticalLinearLayoutManager =
                new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(verticalLinearLayoutManager);
    }
}