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

import com.vegcale.data.VegetableContract.VegetableEntry;

import static com.vegcale.VegetableConstant.CHERRY_TOMATOES;
import static com.vegcale.VegetableConstant.NOT_DEFINED;
import static com.vegcale.VegetableConstant.RED_PEPPERS;
import static com.vegcale.VegetableConstant.STRAWBERRIES;

public class VegetableCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link VegetableCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public VegetableCursorAdapter(Context context, Cursor c) {
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
        return LayoutInflater.from(context).inflate(R.layout.vegetable_data, parent, false);
    }

    /**
     * This method binds the schedule data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current schedule can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find the image view
        ImageView imageView = view.findViewById(R.id.vegetable_image);

        // Find the vegetable name
        TextView vegetableName = view.findViewById(R.id.vegetable_name);

        // Find the description1 for a vegetable
        TextView vegetableDescription1 = view.findViewById(R.id.vegetable_description1);

        // Find the description2 for a vegetable
        TextView vegetableDescription2 = view.findViewById(R.id.vegetable_description2);

        // If these databases column are not null
        if(cursor.getColumnIndex(VegetableEntry.COLUMN_NAME) > 0
                && cursor.getColumnIndex(VegetableEntry.COLUMN_DESCRIPTION1) > 0
                && cursor.getColumnIndex(VegetableEntry.COLUMN_DESCRIPTION2) > 0) {

            // Get the name column index
            int nameColumnIndex = cursor.getColumnIndex(VegetableEntry.COLUMN_NAME);

            // Get the description1 column index
            int description1ColumnIndex = cursor.getColumnIndex(VegetableEntry.COLUMN_DESCRIPTION1);

            // Get the description2 column index
            int description2ColumnIndex = cursor.getColumnIndex(VegetableEntry.COLUMN_DESCRIPTION2);

            // Extract properties from the name cursor
            String name = cursor.getString(nameColumnIndex);

            switch (name) {
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
            // Extract properties from the description1 cursor
            String description1 = cursor.getString(description1ColumnIndex);

            // Extract properties from the description2 cursor
            String description2 = cursor.getString(description2ColumnIndex);

            // Set text to the vegetableName
            vegetableName.setText(name);

            // Set text to the vegetableDescription1
            vegetableDescription1.setText(description1);

            // If the description2 is NOT_DEFINED, set the TextView to invisible
            if(NOT_DEFINED.equals(description2)) {
                vegetableDescription2.setVisibility(View.GONE);
            } else {
                // Set text to the vegetableDescription2
                vegetableDescription2.setText(description2);
            }

        // If these databases column are null
        } else {
            String wrong = "Something is wrong";
            vegetableName.setText(wrong);
            vegetableDescription1.setText(wrong);
            vegetableDescription2.setText(wrong);
        }
    }
}
