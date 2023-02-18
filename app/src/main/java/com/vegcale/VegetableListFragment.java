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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vegcale.utility.fragment.FragmentUtility;

import java.util.ArrayList;
import java.util.List;

public class VegetableListFragment extends Fragment implements View.OnClickListener, ValueEventListener {
    private FragmentUtility mFragmentUtility;
    private ItemDetailFragment ItemDetailFragment;
    private VegetableListRecyclerViewAdapter mVegetableListRecyclerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        mFragmentUtility = new FragmentUtility(getActivity());
        ItemDetailFragment = new ItemDetailFragment();
        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
        setVerticalLinearLayoutToRecyclerViewLayout(mRecyclerView);

        mVegetableListRecyclerViewAdapter =
                new VegetableListRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mVegetableListRecyclerViewAdapter);

        new VegcaleDatabase("plants_info/alias/Japanese/プチトマト", this).getOrangeValue();

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

    private void setVerticalLinearLayoutToRecyclerViewLayout(RecyclerView mRecyclerView) {
        LinearLayoutManager verticalLinearLayoutManager =
                new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(verticalLinearLayoutManager);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        List<String> item = new ArrayList<>();
        item.add("1");
        item.add("2");
        item.add("3");
        item.add("4");
        item.add("5");
        mVegetableListRecyclerViewAdapter.setItem(item);
        mVegetableListRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}