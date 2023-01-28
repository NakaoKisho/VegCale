package com.vegcale;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    List<String> data;
    int originalViewHeight = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewContainingImageOnTop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewContainingImageOnTop = itemView.findViewById(R.id.top_image_bottom_text_card);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.textViewContainingImageOnTop.setText(data.get(position));
        holder.textViewContainingImageOnTop.setOnClickListener(this::changeViewHeight);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new ViewHolder(cardItem);
    }

    @Override
    public int getItemCount() {
        final int defaultDisplayNumber = 2;

        return data == null ? defaultDisplayNumber : data.size();
    }

    public void setItem(List<String> data) {
        this.data = data;
    }

    private void changeViewHeight(View view) {
        final int initialViewHeightValue = 0;
        int viewHeight = view.getHeight();
        if (originalViewHeight == initialViewHeightValue) {
            originalViewHeight = viewHeight;
        }

        ViewGroup.LayoutParams viewLayoutParams = view.getLayoutParams();
        if (viewLayoutParams.height > originalViewHeight) {
            viewLayoutParams.height = originalViewHeight;
        } else {
            final int additionalHeight = 50;
            viewLayoutParams.height = viewHeight + additionalHeight;
        }

        view.setLayoutParams(viewLayoutParams);
    }
}
