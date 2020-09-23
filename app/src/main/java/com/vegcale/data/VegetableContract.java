/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class VegetableContract {

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.vegcale.vegetable";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Path for planting schedules for vegetables
     */
    public static final String PATH_VEGETABLES = "vegetable";

    /** Contractor */
    public VegetableContract() { }

    /* Inner class that defines the table contents of the Vegetable table */
    public static final class VegetableEntry implements BaseColumns {

        /** The content URI to access the planting schedules for vegetables data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VEGETABLES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of schedules for vegetables.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEGETABLES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single schedule.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEGETABLES;

        // Table name
        public static final String TABLE_NAME = "vegetablePlantingSchedule";

        // Table headers
        public static final String _ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION1 = "description1";
        public static final String COLUMN_DESCRIPTION2 = "description2";
        public static final String COLUMN_MONTH = "month";
        public static final String COLUMN_WEEK_OF_MONTH = "weekOfMonth";
        public static final String COLUMN_IMAGE = "image";
    }
}
