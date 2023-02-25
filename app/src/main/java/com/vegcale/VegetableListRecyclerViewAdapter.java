package com.vegcale;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
        private final TextView harvestMonth;
        private final Group infoGroup;
        private final ProgressBar progressCircle;
        private final TextView seedingMonth;
        private final ImageView vegetableStickingOutImage;
        private final TextView vegetableName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            growthDifficulty = itemView.findViewById(R.id.growth_difficulty);
            harvestMonth = itemView.findViewById(R.id.harvest_month);
            infoGroup = itemView.findViewById(R.id.info_group);
            progressCircle = itemView.findViewById(R.id.progress_circle);
            seedingMonth = itemView.findViewById(R.id.seeding_month);
            vegetableStickingOutImage = itemView.findViewById(R.id.sticking_out_image);
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
        setItemData(holder, position);
        holder.itemView.setOnClickListener(onClickListenerOnParent);

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
        int infoGroupVisibility;
        int progressBarVisibility;

        if (visibility == ProgressBarVisibility.Visible) {
            infoGroupVisibility = View.INVISIBLE;
            progressBarVisibility = View.VISIBLE;
        } else {
            infoGroupVisibility = View.VISIBLE;
            progressBarVisibility = View.INVISIBLE;
        }

        holder.infoGroup.setVisibility(infoGroupVisibility);
        holder.progressCircle.setVisibility(progressBarVisibility);
    }

    private void setItemData(@NonNull ViewHolder holder, int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Plant vegetableData = data.get(position);
        String imageUrl = vegetableData.getImage_Url();
        StorageReference imageReference = storage.getReferenceFromUrl(imageUrl);

        Glide.with(holder.itemView)
                .load(imageReference)
                .into(holder.vegetableStickingOutImage);

        holder.vegetableName.setText(vegetableData.getName().getJapanese());
        holder.growthDifficulty.setText(String.valueOf(vegetableData.getGrowth_Difficulty()));
        holder.harvestMonth.setText(vegetableData.getHarvest_Month().getFrom());
        holder.seedingMonth.setText(vegetableData.getSeeding_Month().getFrom());
    }
}
