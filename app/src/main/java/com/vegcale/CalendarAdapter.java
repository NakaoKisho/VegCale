package com.vegcale;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

import static com.vegcale.DateUtils.SPAN_COUNT;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private static final int ONE = 1;

    private static final String TAG = "CalendarAdapter";

    DateUtils mDateUtils = new DateUtils();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView date;
        private final TextView note;
        private final ImageView stamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: " + getBindingAdapterPosition());
                }
            });
            date = itemView.findViewById(R.id.date);
            note = itemView.findViewById(R.id.note);
            stamp = itemView.findViewById(R.id.stamp);
        }
        public TextView getDate() {
            return date;
        }
        public TextView getNote() {
            return note;
        }
        public ImageView getStamp() {
            return stamp;
        }
    }

    @NonNull
    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vegetable_data, parent, false);

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        params.width = parent.getMeasuredWidth() / SPAN_COUNT;
        params.height = parent.getMeasuredHeight() / mDateUtils.getFULL_HEIGHT_COUNT();
        view.setLayoutParams(params);

//        Log.d(TAG,
//                "\n parent.getMeasuredHeight() = " + parent.getMeasuredHeight() +
//                "\n mDateUtils.getFULL_HEIGHT_COUNT() = " + mDateUtils.getFULL_HEIGHT_COUNT());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.ViewHolder holder, int position) {

//        holder.getDate().setVisibility(View.VISIBLE);
//        holder.getNote().setVisibility(View.VISIBLE);

        if(mDateUtils.getEmptyDaysToTheStart() < position + ONE
                && position + ONE <= mDateUtils.getEmptyDaysToTheStart() + mDateUtils.getDaysFromTheStartToTheEnd()) {

            holder.getDate().setText(String.valueOf((position + ONE)
                    - mDateUtils.getEmptyDaysToTheStart()));
            holder.getNote().setText("HI");
//            Log.d(TAG, "AAA");

        } else {

//            holder.getDate().setVisibility(View.GONE);
//            holder.getNote().setVisibility(View.GONE);
//            Log.d(TAG, "BBB");

        }
//        Log.d(TAG, "onBindViewHolder: "
//                + "\n position = " + position
//                + "\n mDateUtils.getEmptyDaysToTheStart() = " + mDateUtils.getEmptyDaysToTheStart()
//                + "\n position + ONE = " + (position + ONE)
//                + "\n mDateUtils.getDaysFromTheStartToTheEnd() = " + mDateUtils.getDaysFromTheStartToTheEnd());
    }

    @Override
    public int getItemCount() {
        return mDateUtils.getAllDisplayedDays();
    }
}
