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
        holder.textViewContainingImageOnTop.setOnClickListener(getOnClickListener());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new ViewHolder(cardItem);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        holder.textViewContainingImageOnTop.setText("Detached");
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);

        holder.textViewContainingImageOnTop.setText("");
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    private View.OnClickListener getOnClickListener() {
        return view -> {
            final int additionalHeight = 50;
            int viewHeight = view.getHeight();
            ViewGroup.LayoutParams viewLayoutParams = view.getLayoutParams();
            viewLayoutParams.height = viewHeight + additionalHeight;
            view.setLayoutParams(viewLayoutParams);
        };
    }
}
