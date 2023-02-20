package com.vegcale;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VegetableListRecyclerViewAdapter
        extends RecyclerView.Adapter<VegetableListRecyclerViewAdapter.ViewHolder>
        implements ValueEventListener {
    private final List<Plant> data = new ArrayList<>();
    private final View.OnClickListener onClickListenerOnParent;
    private final VegcaleDatabase mVegcaleDatabase;

    public VegetableListRecyclerViewAdapter(View.OnClickListener onClickListenerOnParent) {
        this.onClickListenerOnParent = onClickListenerOnParent;
        mVegcaleDatabase = new VegcaleDatabase(this);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar progressCircle;
        private final ViewStub vegetable_image;
        private final TextView vegetable_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            progressCircle = itemView.findViewById(R.id.progress_circle);
            vegetable_image = itemView.findViewById(R.id.vegetable_image);
            vegetable_name = itemView.findViewById(R.id.vegetable_name);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.isEmpty()) {
            holder.progressCircle.setVisibility(View.VISIBLE);
            holder.vegetable_image.setVisibility(View.INVISIBLE);
            mVegcaleDatabase.fetchPlantsData();
            return;
        }

        holder.progressCircle.setVisibility(View.INVISIBLE);
        holder.vegetable_image.setVisibility(View.VISIBLE);
        holder.itemView.setOnClickListener(onClickListenerOnParent);

        holder.vegetable_name.setText(data.get(0).getHours_Of_Light());
        if (position == 9) {
            mVegcaleDatabase.fetchPlantsData();
        }
//        String currentMonth =
//                Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
//        assert currentMonth != null : "currentMonth must not be null.";
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

        return data.isEmpty() ? defaultDisplayNumber : data.size();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (data.isEmpty()) {
            int firstItemIndex = 0;

            notifyItemChanged(firstItemIndex);
        } else {
            notifyItemInserted(data.size());
        }

        data.add(snapshot.getValue(Plant.class));
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
//        エラーが起きました。インターネット接続を確認してください。
    }
}
