///*
// * Copyright 2020 Vegetable Calendar Project
// ********************************************
// *    Editor    *    Date    *    Reason    *
// *------------------------------------------*
// *    Kisho     * 2020/9/30  *    Launch    *
// *------------------------------------------*
// */
//
//package com.vegcale;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CursorAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class VegetableCursorAdapter extends CursorAdapter {
//
//    private List<Date> dateArray;
//
//    /** Context */
//    private Context mContext;
//    private DateUtils mDateManager;
//    private LayoutInflater mLayoutInflater;
//
//    //カスタムセルを拡張したらここでWidgetを定義
//    private static class ViewHolder {
//        public TextView date;
//        public TextView note;
//    }
//
//    /**
//     * Constructs a new {@link VegetableCursorAdapter}.
//     *
//     * @param context The context
//     * @param c       The cursor from which to get the data.
//     */
//    public VegetableCursorAdapter(Context context, Cursor c) {
//        super(context, c, 0);
//        mContext = context;
//        mLayoutInflater = LayoutInflater.from(mContext);
////        mDateManager = new DateUtils();
////        dateArray = mDateManager.getDays();
//    }
//
//    @Override
//    public int getCount() {
//        return dateArray.size();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            convertView = mLayoutInflater.inflate(R.layout.vegetable_data, null);
//            holder = new ViewHolder();
//            holder.date = convertView.findViewById(R.id.date);
//            holder.note = convertView.findViewById(R.id.note);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder)convertView.getTag();
//        }
//
////        //セルのサイズを指定
////        float dp = mContext.getResources().getDisplayMetrics().density;
////        AbsListView.LayoutParams params = new AbsListView.LayoutParams(parent.getWidth()/7 - (int)dp, (parent.getHeight() - (int)dp * mDateManager.getWeekOfCurrentMonth() ) / mDateManager.getWeekOfCurrentMonth());
////        convertView.setLayoutParams(params);
//
//        //日付のみ表示させる
//        SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.US);
////        holder.date.setText(dateFormat.format(dateArray.get(position)));
//
////        holder.note.setText("0123456789abcdefghijklmnopqrstuvwxyz");
//
////        //当月以外のセルをグレーアウト
////        if (mDateManager.isCurrentMonth(dateArray.get(position))){
////            convertView.setBackgroundColor(Color.WHITE);
////        } else {
////            convertView.setBackgroundColor(Color.LTGRAY);
////        }
//
//        //日曜日を赤、土曜日を青に
//        int colorId;
////        switch (mDateManager.getDayOfWeek(dateArray.get(position))){
////            case 1:
////                colorId = Color.RED;
////                break;
////            case 7:
////                colorId = Color.BLUE;
////                break;
////
////            default:
////                colorId = Color.BLACK;
////                break;
////        }
////        holder.date.setTextColor(colorId);
//
//        return convertView;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    //表示月を取得
//    public String getTitle(){
//        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.US);
////        return format.format(mDateManager.mCalendar.getTime());
//        return null;
//    }
//
////    //翌月表示
////    public void nextMonth(){
////        mDateManager.nextMonth();
////        dateArray = mDateManager.getDays();
////        this.notifyDataSetChanged();
////    }
////
////    //前月表示
////    public void prevMonth(){
////        mDateManager.prevMonth();
////        dateArray = mDateManager.getDays();
////        this.notifyDataSetChanged();
////    }
//
//    /**
//     * Makes a new blank list item view. No data is set (or bound) to the views yet.
//     *
//     * @param context app context
//     * @param cursor  The cursor from which to get the data. The cursor is already
//     *                moved to the correct position.
//     * @param parent  The parent to which the new view is attached to
//     * @return the newly created list item view.
//     */
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return LayoutInflater.from(context).inflate(R.layout.vegetable_data, parent, false);
//    }
//
//    /**
//     * This method binds the schedule data (in the current row pointed to by cursor) to the given
//     * list item layout. For example, the name for the current schedule can be set on the name TextView
//     * in the list item layout.
//     *
//     * @param view    Existing view, returned earlier by newView() method
//     * @param context app context
//     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
//     *                correct row.
//     */
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//
//        // Find TextView of date
//        TextView date = view.findViewById(R.id.date);
//
////        date.setText("8");
//
//        // Find TextView of note
//        TextView note = view.findViewById(R.id.note);
//
////        note.setText("0123456789abcdefghijklmnopqrstuvwxyz");
//
//        // Find the image view
//        ImageView stamp = view.findViewById(R.id.stamp);
//    }
//}
