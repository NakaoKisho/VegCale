package com.vegcale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ItemDetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        ViewStub vegetable_image = rootView.findViewById(R.id.vegetable_image);
        View inflatedVegetableImage = vegetable_image.inflate();
        ImageView imageInVegetableImageViewStub = inflatedVegetableImage.findViewById(R.id.sticking_out_image);
        imageInVegetableImageViewStub.setBackgroundResource(R.drawable.ic_cherry_tomatoes_circle);

        TextView vegetableName = rootView.findViewById(R.id.vegetable_name);
        vegetableName.setText("プチトマト");
        TextView seedingTimeMonth = rootView.findViewById(R.id.seeding_time_month);
        seedingTimeMonth.setText("1月");
        TextView harvestTimeMonth = rootView.findViewById(R.id.harvest_time_month);
        harvestTimeMonth.setText("3月");
        TextView growthDifficulty = rootView.findViewById(R.id.growth_difficulty);
        growthDifficulty.setText("ややむずかしい");
        TextView vegetableDetail = rootView.findViewById(R.id.vegetable_detail);
        vegetableDetail.setText("リコピン含有量が多くどんな料理にも合う名脇役");
        TextView daylightHour = rootView.findViewById(R.id.daylight_hour);
        daylightHour.setText("1日中");
        TextView wateringFrequency = rootView.findViewById(R.id.watering_frequency);
        wateringFrequency.setText("毎日");
        TextView wateringAmount = rootView.findViewById(R.id.watering_amount);
        wateringAmount.setText("土がひたひたになるまで");

        return rootView;
    }
}
