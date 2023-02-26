package com.vegcale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.vegcale.utility.Conversion;
import com.vegcale.utility.fragment.FragmentUtility;

import java.util.ArrayList;
import java.util.List;

public class VegetableListRecyclerViewAdapter
        extends RecyclerView.Adapter<VegetableListRecyclerViewAdapter.ViewHolder>
        implements ValueEventListener {
    private enum ProgressBarVisibility {
        Visible,
        Invisible
    }

    private static final List<Plant> data = new ArrayList<>();
    private static FragmentActivity mFragmentActivity = null;
    private final VegcaleDatabase mVegcaleDatabase;

    public VegetableListRecyclerViewAdapter(FragmentActivity mFragmentActivity) {
        VegetableListRecyclerViewAdapter.mFragmentActivity = mFragmentActivity;
        mVegcaleDatabase = new VegcaleDatabase(this);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ItemDetailFragment mItemDetailFragment = new ItemDetailFragment();
            setItemDetailData(mItemDetailFragment);

            new FragmentUtility(mFragmentActivity).changeFragment(
                    mItemDetailFragment,
                    FragmentUtility.ItemDetailFragmentTag,
                    FragmentUtility.SlideAnimation.LeftToRight
            );
        }

        private void setItemDetailData(ItemDetailFragment mItemDetailFragment) {
            Bundle mBundle = new Bundle();
            Plant vegetableData = data.get(getLayoutPosition());
            mBundle.putString("detail", vegetableData.getDetail());
            mBundle.putInt("growthDifficulty", vegetableData.getGrowth_Difficulty());
            mBundle.putInt("harvestMonthFrom", vegetableData.getHarvest_Month().getFrom());
            mBundle.putInt("harvestMonthTo", vegetableData.getHarvest_Month().getTo());
            mBundle.putString("hoursOfLight", vegetableData.getHours_Of_Light());
            mBundle.putString("imageUrl", vegetableData.getImage_Url());
            mBundle.putString("name", vegetableData.getName().getJapanese());
            mBundle.putInt("seedingMonthFrom", vegetableData.getSeeding_Month().getFrom());
            mBundle.putInt("seedingMonthTo", vegetableData.getSeeding_Month().getTo());
            mBundle.putString("wateringAmount", vegetableData.getWatering_Amount());
            mBundle.putString("wateringFrequency", vegetableData.getWatering_Frequency());
            mItemDetailFragment.setArguments(mBundle);
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

        if (position == 9) {
            mVegcaleDatabase.fetchPlantsData();
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

        int harvestMonthFrom = vegetableData.getHarvest_Month().getFrom();
        int harvestMonthTo = vegetableData.getHarvest_Month().getTo();
        Conversion mConversion = new Conversion();
        String harvestMonthText = mConversion.convertMonthRangeIntToText(harvestMonthFrom, harvestMonthTo);
        holder.harvestMonth.setText(harvestMonthText);

        int seedingMonthFrom = vegetableData.getSeeding_Month().getFrom();
        int seedingMonthTo = vegetableData.getSeeding_Month().getTo();
        String seedingMonthText = mConversion.convertMonthRangeIntToText(seedingMonthFrom, seedingMonthTo);
        holder.seedingMonth.setText(seedingMonthText);
    }
}