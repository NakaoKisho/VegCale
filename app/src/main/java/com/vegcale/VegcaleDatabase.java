/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VegcaleDatabase {
    private FirebaseDatabase vegcaleDatabase;
    private DatabaseReference vegcaleDatabaseReference;

    public VegcaleDatabase(@NonNull String databasePath) {
        final String vegcaleDatabaseUrl =
                "https://vegcale-app-default-rtdb.asia-southeast1.firebasedatabase.app/";

        vegcaleDatabase = FirebaseDatabase.getInstance(vegcaleDatabaseUrl);
        vegcaleDatabaseReference = vegcaleDatabase.getReference(databasePath);
    }

    public void getOrangeValue() {
        vegcaleDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Failed to read value." + error.toException());
            }

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                System.out.println("Database value: " + value);
            }
        });
    }
}