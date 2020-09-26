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

public class VegetableTipCursorAdapter extends CursorAdapter {

    /** Context */
    private Context mContext;

    /**
     * Constructs a new {@link VegetableCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public VegetableTipCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        mContext = context;
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
        TextView tipDescription = view.findViewById(R.id.tip_article);

        // If these databases column are not null
        if(cursor.getColumnIndex(VegetableTipEntry.COLUMN_TITLE) > 0
                && cursor.getColumnIndex(VegetableTipEntry.COLUMN_DESCRIPTION) > 0
                && cursor.getColumnIndex(VegetableTipEntry.COLUMN_IMAGE) > 0) {

            // Get the resource ID column index
            int imageColumnIndex = cursor.getColumnIndex(VegetableTipEntry.COLUMN_IMAGE);

            // Get the title column index
            int titleColumnIndex = cursor.getColumnIndex(VegetableTipEntry.COLUMN_TITLE);

            // Get the description1 column index
            int descriptionColumnIndex = cursor.getColumnIndex(VegetableTipEntry.COLUMN_DESCRIPTION);

            // Extract resource ID from the image cursor
            String resId = cursor.getString(imageColumnIndex);

            // Extract properties from the title cursor
            String title = cursor.getString(titleColumnIndex);

            // Extract properties from the description cursor
            String description = cursor.getString(descriptionColumnIndex);

            // Set the image to the imageView
            imageView.setImageResource(mContext.getResources().getIdentifier(resId, "drawable", mContext.getPackageName()));

            // Set text to the tipTitle
            tipTitle.setText(title);

            // Set text to the tipDescription
            tipDescription.setText(description);

            // If these databases column are null
        } else {
            String wrong = "Something is wrong";
            tipTitle.setText(wrong);
            tipDescription.setText(wrong);
        }
    }
}
