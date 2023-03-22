package com.vegcale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.vegcale.adapters.VegetableArticleListRecyclerView;

public class VegetableArticleFragment
        extends Fragment
        implements VegetableArticleListRecyclerView.SnackBarCallbackListener {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_vegetable_article, container, false);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
        setGridLayoutToRecyclerViewLayout(mRecyclerView);

        VegetableArticleListRecyclerView mVegetableArticleListRecyclerView =
                new VegetableArticleListRecyclerView(getActivity(), this);
        mRecyclerView.setAdapter(mVegetableArticleListRecyclerView);

        return rootView;
    }

    private void setGridLayoutToRecyclerViewLayout(@NonNull RecyclerView mRecyclerView) {
        final int columnCount = 2;
        GridLayoutManager mGridLayoutManager =
                new GridLayoutManager(getContext(), columnCount);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    public void showErrorSnackBar() {
        Snackbar.make(rootView, "error", Snackbar.LENGTH_SHORT)
                .show();
    }
}