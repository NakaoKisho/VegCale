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
    private enum ProgressBarVisibility {
        Visible,
        Invisible
    }
    private final List<Plant> data = new ArrayList<>();
    private final View.OnClickListener onClickListenerOnParent;
    private final VegcaleDatabase mVegcaleDatabase;

    public VegetableListRecyclerViewAdapter(View.OnClickListener onClickListenerOnParent) {
        this.onClickListenerOnParent = onClickListenerOnParent;
        mVegcaleDatabase = new VegcaleDatabase(this);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView growthDifficulty;
        private final TextView growthDifficultyLabel;
        private final TextView harvestMonth;
        private final TextView harvestMonthLabel;
        private final ProgressBar progressCircle;
        private final TextView seedingMonth;
        private final TextView seedingMonthLabel;
        private final ViewStub vegetableImage;
        private final TextView vegetableName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            growthDifficulty = itemView.findViewById(R.id.growth_difficulty);
            growthDifficultyLabel = itemView.findViewById(R.id.growth_difficulty_label);
            harvestMonth = itemView.findViewById(R.id.harvest_month);
            harvestMonthLabel = itemView.findViewById(R.id.harvest_month_label);
            progressCircle = itemView.findViewById(R.id.progress_circle);
            seedingMonth = itemView.findViewById(R.id.seeding_month);
            seedingMonthLabel = itemView.findViewById(R.id.seeding_month_label);
            vegetableImage = itemView.findViewById(R.id.vegetable_image);
            vegetableName = itemView.findViewById(R.id.vegetable_name);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.isEmpty()) {
            changeProgressCircleVisibility(holder, ProgressBarVisibility.Visible);
            mVegcaleDatabase.fetchPlantsData();
            return;
        }

        changeProgressCircleVisibility(holder, ProgressBarVisibility.Invisible);
        holder.itemView.setOnClickListener(onClickListenerOnParent);

        setItemData(holder, position);
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

        for (DataSnapshot children : snapshot.getChildren()) {
            data.add(children.getValue(Plant.class));
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
//        エラーが起きました。インターネット接続を確認してください。
    }

    private void changeProgressCircleVisibility(@NonNull ViewHolder holder, ProgressBarVisibility visibility) {
        int otherItemVisibility;
        int progressBarVisibility;

        if (visibility == ProgressBarVisibility.Visible) {
            otherItemVisibility = View.INVISIBLE;
            progressBarVisibility = View.VISIBLE;
        } else {
            otherItemVisibility = View.VISIBLE;
            progressBarVisibility = View.INVISIBLE;
        }

        holder.growthDifficulty.setVisibility(otherItemVisibility);
        holder.growthDifficultyLabel.setVisibility(otherItemVisibility);
        holder.harvestMonth.setVisibility(otherItemVisibility);
        holder.harvestMonthLabel.setVisibility(otherItemVisibility);
        holder.progressCircle.setVisibility(progressBarVisibility);
        holder.seedingMonth.setVisibility(otherItemVisibility);
        holder.seedingMonthLabel.setVisibility(otherItemVisibility);
        holder.vegetableImage.setVisibility(otherItemVisibility);
        holder.vegetableName.setVisibility(otherItemVisibility);
    }

    private void setItemData(@NonNull ViewHolder holder, int position) {
        Plant vegetableData = data.get(position);

        holder.vegetableName.setText(vegetableData.getName().getJapanese());
        holder.growthDifficulty.setText(String.valueOf(vegetableData.getGrowth_Difficulty()));
        holder.harvestMonth.setText(vegetableData.getHarvest_Month().getFrom());
        holder.seedingMonth.setText(vegetableData.getSeeding_Month().getFrom());
    }
}
