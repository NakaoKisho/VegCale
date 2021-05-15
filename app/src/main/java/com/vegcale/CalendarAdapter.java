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
import java.util.Locale;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private static final String FOURTEENTH = "14";
    private static final String TWENTY_SECOND = "22";

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    private static final int TWO_THOUSAND = 2000;
    private static final int NUMBER_OF_DAYS_IN_ONE_HUNDRED_YEARS = 36500;

    private static final String TAG = "CalendarAdapter";

    private final TextView displayYear;

    private final TextView displayMonth;

    private final DateUtils mDateUtils = new DateUtils();

    private final Calendar calendarForCalculation = mDateUtils.getCalendarForCalculation();

    CalendarAdapter(TextView displayYear, TextView displayMonth) {
        this.displayYear = displayYear;
        this.displayMonth = displayMonth;
    }

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
        params.width = parent.getMeasuredWidth() / mDateUtils.getSpanCount();
        view.setLayoutParams(params);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.ViewHolder holder, int position) {
        calendarForCalculation.set(TWO_THOUSAND, ZERO, TWO);
        calendarForCalculation.add(Calendar.DATE, position);

        holder.getDate().setText(String.valueOf(calendarForCalculation.get(Calendar.DATE)));
        holder.getNote().setText(calendarForCalculation.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.JAPAN));
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        if(FOURTEENTH.contentEquals(holder.getDate().getText()) || TWENTY_SECOND.contentEquals(holder.getDate().getText())) {
            displayYear.setText(String.valueOf(calendarForCalculation.get(Calendar.YEAR)));
            displayMonth.setText(calendarForCalculation.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.JAPAN));
        }
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_DAYS_IN_ONE_HUNDRED_YEARS;
    }
}