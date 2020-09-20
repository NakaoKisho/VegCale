/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vegcale.data.VegetableTipContract.VegetableTipEntry;

import static com.vegcale.VegetableConstant.CHERRY_TOMATOES;
import static com.vegcale.VegetableConstant.NOT_DEFINED;
import static com.vegcale.VegetableConstant.RED_PEPPERS;
import static com.vegcale.VegetableConstant.STRAWBERRIES;

public class VegetableTipCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link VegetableCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public VegetableTipCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.tip_data, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find the image view
        ImageView imageView = view.findViewById(R.id.tip_image);

        // Find the tip title
        TextView tipTitle = view.findViewById(R.id.tip_title);

        // Find the tip's short description
        TextView tipDescription = view.findViewById(R.id.tip_description);

        // If these databases column are not null
        if(cursor.getColumnIndex(VegetableTipEntry.COLUMN_TITLE) > 0
                && cursor.getColumnIndex(VegetableTipEntry.COLUMN_DESCRIPTION1) > 0
                && cursor.getColumnIndex(VegetableTipEntry.COLUMN_DESCRIPTION2) > 0) {

            // Get the title column index
            int titleColumnIndex = cursor.getColumnIndex(VegetableTipEntry.COLUMN_TITLE);

            // Get the description1 column index
            int description1ColumnIndex = cursor.getColumnIndex(VegetableTipEntry.COLUMN_DESCRIPTION1);

            // Get the description2 column index
            int description2ColumnIndex = cursor.getColumnIndex(VegetableTipEntry.COLUMN_DESCRIPTION2);

            // Extract properties from the title cursor
            String title = cursor.getString(titleColumnIndex);

            // Extract properties from the description1 cursor
            String description1 = cursor.getString(description1ColumnIndex);

            // Extract properties from the description2 cursor
            String description2 = cursor.getString(description2ColumnIndex);

            switch (title) {
                // if name is cherry tomato
                case CHERRY_TOMATOES:
                    imageView.setImageResource(R.drawable.ic_cherry_tomato_circle);
                    break;

                // if name is strawberry
                case STRAWBERRIES:
                    imageView.setImageResource(R.drawable.ic_strawberry_circle);
                    break;

                // if name is red pepper
                case RED_PEPPERS:
                    imageView.setImageResource(R.drawable.ic_red_pepper_circle);
                    break;
            }

            // Set text to the tipTitle
            tipTitle.setText(title);

            // Set text to the tipDescription
            tipDescription.setText(description1);

            // If the description2 is NOT_DEFINED, set the TextView to invisible
            if(NOT_DEFINED.equals(description2)) {
                tipDescription.setVisibility(View.GONE);
            } else {
                tipDescription.setText(description2);
            }

            // If these databases column are null
        } else {
            String wrong = "Something is wrong";
            tipTitle.setText(wrong);
            tipDescription.setText(wrong);
        }
    }
}
