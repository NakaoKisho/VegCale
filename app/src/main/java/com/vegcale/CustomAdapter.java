package com.vegcale;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    List<String> data;
    View.OnClickListener onClickListenerOnParent;

    public CustomAdapter(View.OnClickListener onClickListener) {
        onClickListenerOnParent = onClickListener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar progressCircle;
        private final ViewStub vegetable_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            progressCircle = itemView.findViewById(R.id.progress_circle);
            vegetable_image = itemView.findViewById(R.id.vegetable_image);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data == null) {
            holder.progressCircle.setVisibility(View.VISIBLE);
            holder.vegetable_image.setVisibility(View.INVISIBLE);
        } else {
            holder.progressCircle.setVisibility(View.INVISIBLE);
            holder.vegetable_image.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(onClickListenerOnParent);
        }
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
        final int defaultDisplayNumber = 1;

        return data == null ? defaultDisplayNumber : data.size();
    }

    public void setItem(List<String> data) {
        this.data = data;
    }
}
