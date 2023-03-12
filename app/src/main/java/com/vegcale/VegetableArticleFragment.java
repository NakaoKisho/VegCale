package com.vegcale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class VegetableArticleFragment
        extends Fragment
        implements VegetableListRecyclerViewAdapter.SnackBarCallbackListener {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_vegetable_article, container, false);

//        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
//        setVerticalLinearLayoutToRecyclerViewLayout(mRecyclerView);
//
//        VegetableListRecyclerViewAdapter mVegetableListRecyclerViewAdapter =
//                new VegetableListRecyclerViewAdapter(getActivity(), this);
//        mRecyclerView.setAdapter(mVegetableListRecyclerViewAdapter);

        return rootView;
    }

    private void setVerticalLinearLayoutToRecyclerViewLayout(@NonNull RecyclerView mRecyclerView) {
        LinearLayoutManager verticalLinearLayoutManager =
                new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(verticalLinearLayoutManager);
    }

    public void showErrorSnackBar() {
        Snackbar.make(rootView, "error", Snackbar.LENGTH_SHORT)
                .show();
    }
}