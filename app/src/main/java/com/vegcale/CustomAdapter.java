package com.vegcale;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    List<String> itemData;
    int originalViewHeight = 0;


    public CustomAdapter(List<String> itemData) {
        this.itemData = itemData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewContainingImageOnTop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewContainingImageOnTop = itemView.findViewById(R.id.top_image_bottom_text_card);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewContainingImageOnTop.setText(itemData.get(position));
        holder.textViewContainingImageOnTop.setOnClickListener(changeHeight());
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
        return itemData.size();
    }

    private View.OnClickListener changeHeight() {
        return view -> {
            int viewHeight = view.getHeight();
            if (originalViewHeight == 0) {
                originalViewHeight = viewHeight;
            }

            ViewGroup.LayoutParams viewLayoutParams = view.getLayoutParams();
            if (viewLayoutParams.height > originalViewHeight) return;

            final int additionalHeight = 50;
            viewLayoutParams.height = viewHeight + additionalHeight;
            view.setLayoutParams(viewLayoutParams);
        };
    }
}
