/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class VegcaleDatabase {
    private final FirebaseDatabase vegcaleDatabase;
    public final String plantsInfoRootPath = "plants_info/";
    public final String plantsArticleRootPath = "plants_article/";

    public VegcaleDatabase() {
        final String vegcaleDatabaseUrl =
                "https://vegcale-app-default-rtdb.asia-southeast1.firebasedatabase.app/";

        vegcaleDatabase = FirebaseDatabase.getInstance(vegcaleDatabaseUrl);
    }

    public void fetchArticlesData(
            int nextItemCount,
            boolean isFirstDataFetch,
            @Nullable String startAfterValue,
            @NonNull ValueEventListener mValueEventListener
    ) {
        if (isFirstDataFetch && startAfterValue != null) {
            throw new Error("If isFirstDataFetch is true, startAfterValue should not be null,");
        }
        if (!isFirstDataFetch && startAfterValue == null) {
            throw new Error("If isFirstDataFetch is false, startAfterValue should be null,");
        }

        String plantsPath = plantsArticleRootPath + "articles/";

        DatabaseReference plantsReference =
                vegcaleDatabase.getReference(plantsPath);
        Query dataFetchQuery = plantsReference.orderByKey();

        dataFetchQuery = dataFetchQuery.limitToFirst(nextItemCount);

        if (!isFirstDataFetch) {
            dataFetchQuery = dataFetchQuery.startAfter(startAfterValue);
        }

        dataFetchQuery.addListenerForSingleValueEvent(mValueEventListener);
    }

    public void fetchPlantsData(
            int nextItemCount,
            boolean isFirstDataFetch,
            @Nullable String startAfterValue,
            @NonNull ValueEventListener mValueEventListener
    ) {
        if (isFirstDataFetch && startAfterValue != null) {
            throw new Error("If isFirstDataFetch is true, startAfterValue should not be null,");
        }
        if (!isFirstDataFetch && startAfterValue == null) {
            throw new Error("If isFirstDataFetch is false, startAfterValue should be null,");
        }

        String plantsPath = plantsInfoRootPath + "plants/";

        DatabaseReference plantsReference =
                vegcaleDatabase.getReference(plantsPath);
        Query dataFetchQuery = plantsReference.orderByKey();

        dataFetchQuery = dataFetchQuery.limitToFirst(nextItemCount);

        if (!isFirstDataFetch) {
            dataFetchQuery = dataFetchQuery.startAfter(startAfterValue);
        }

        dataFetchQuery.addListenerForSingleValueEvent(mValueEventListener);
    }

    public void getCount(String rootPath, OnCompleteListener<DataSnapshot> mOnCompleteListener) {
        String itemCountPath = rootPath + "item_count";

        DatabaseReference plantsReference =
                vegcaleDatabase.getReference(itemCountPath);
        plantsReference.get().addOnCompleteListener(mOnCompleteListener);
    }
}
