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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        implements OnCompleteListener<DataSnapshot>, ValueEventListener {

    private enum ProgressBarVisibility {
        Visible,
        Invisible
    }

    private final List<Plant> data = new ArrayList<>();
    private final FragmentActivity mFragmentActivity;
    private final VegcaleDatabase mVegcaleDatabase;
    private Integer itemCount;
    private int dataFetchCount = 0;
    private String latestDataKey;

    public VegetableListRecyclerViewAdapter(FragmentActivity mFragmentActivity) {
        this.mFragmentActivity = mFragmentActivity;
        mVegcaleDatabase = new VegcaleDatabase(this);
        mVegcaleDatabase.getCount(this);
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
    public int getItemCount() {
        final int defaultDisplayNumber = 1;

        return data.isEmpty() ? defaultDisplayNumber : data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int nextItemCount = 6;

        if (itemCount == null) {
            return;
        }

        if (data.isEmpty()) {
            changeProgressCircleVisibility(holder, ProgressBarVisibility.Visible);
            mVegcaleDatabase.fetchPlantsData(nextItemCount, true, null);
            dataFetchCount++;
            itemCount -= nextItemCount;

            return;
        }

        changeProgressCircleVisibility(holder, ProgressBarVisibility.Invisible);
        setItemData(holder, position);
        holder.itemView.setOnClickListener(view -> moveToItemDetailFragment(position));

        if (itemCount <= 0) return;

        int nextDataFetchPosition = nextItemCount * dataFetchCount - 1;
        if (position == nextDataFetchPosition) {
            mVegcaleDatabase.fetchPlantsData(nextItemCount, false, latestDataKey);
            dataFetchCount++;
            itemCount -= nextItemCount;
            if (itemCount < 0) {
                itemCount = 0;
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
//        エラーが起きました。インターネット接続を確認してください。
    }

    @Override
    public void onComplete(@NonNull Task<DataSnapshot> task) {
        if (!task.isSuccessful()) {
//            エラーが起きました。インターネット接続を確認してください。
            return;
        }

        DataSnapshot resultDataSnapshot = task.getResult();
        itemCount = resultDataSnapshot.getValue(Integer.class);

        int firstItemIndex = 0;
        notifyItemChanged(firstItemIndex);

        if (itemCount == null) {
//            エラーが起きました。インターネット接続を確認してください。
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
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (data.isEmpty()) {
            int firstItemIndex = 0;

            notifyItemChanged(firstItemIndex);
        } else {
            notifyItemInserted(data.size());
        }

        for (DataSnapshot children : snapshot.getChildren()) {
            data.add(children.getValue(Plant.class));
            latestDataKey = children.getKey();
        }
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

    private void moveToItemDetailFragment(int position) {
        ItemDetailFragment mItemDetailFragment = new ItemDetailFragment();
        setDetailItemData(mItemDetailFragment, position);

        new FragmentUtility(mFragmentActivity).changeFragment(
                mItemDetailFragment,
                FragmentUtility.ItemDetailFragmentTag,
                FragmentUtility.SlideAnimation.LeftToRight
        );
    }

    private void setDetailItemData(@NonNull ItemDetailFragment mItemDetailFragment, int position) {
        Bundle mBundle = new Bundle();
        Plant vegetableData = data.get(position);

        mBundle.putString("detail", vegetableData.getDetail());
        mBundle.putString("growthDifficulty", vegetableData.getGrowth_Difficulty());
        mBundle.putInt("harvestMonthFrom", vegetableData.getHarvest_Month().getFrom());
        mBundle.putInt("harvestMonthTo", vegetableData.getHarvest_Month().getTo());
        mBundle.putString("hoursOfLight", vegetableData.getHours_Of_Light());
        mBundle.putString("imageUrl", vegetableData.getImage_Url());
        mBundle.putString("name", vegetableData.getName());
        mBundle.putInt("seedingMonthFrom", vegetableData.getSeeding_Month().getFrom());
        mBundle.putInt("seedingMonthTo", vegetableData.getSeeding_Month().getTo());
        mBundle.putString("wateringAmount", vegetableData.getWatering_Amount());
        mBundle.putString("wateringFrequency", vegetableData.getWatering_Frequency());
        mBundle.putInt("phFrom", vegetableData.getPh().getFrom());
        mBundle.putInt("phTo", vegetableData.getPh().getTo());
        mItemDetailFragment.setArguments(mBundle);
    }

    private void setItemData(@NonNull ViewHolder holder, int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Plant vegetableData = data.get(position);
        String imageUrl = vegetableData.getImage_Url();
        StorageReference imageReference = storage.getReferenceFromUrl(imageUrl);

        Glide.with(holder.itemView)
                .load(imageReference)
                .into(holder.vegetableStickingOutImage);

        holder.vegetableName.setText(vegetableData.getName());
        holder.growthDifficulty.setText(vegetableData.getGrowth_Difficulty());

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