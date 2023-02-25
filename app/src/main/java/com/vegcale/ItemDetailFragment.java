package com.vegcale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.vegcale.utility.fragment.FragmentUtility;

public class ItemDetailFragment extends Fragment {
    private VegetableListFragment mVegetableListFragment;
    private FragmentUtility mFragmentUtility;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentUtility = new FragmentUtility(getActivity());
        mVegetableListFragment = new VegetableListFragment();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mFragmentUtility.changeFragment(
                        mVegetableListFragment,
                        FragmentUtility.VegetableListFragmentTag,
                        FragmentUtility.SlideAnimation.RightToLeft
                );
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        TextView vegetableImageFrame = rootView.findViewById(R.id.image_frame);
        ImageView stickingOutImage = rootView.findViewById(R.id.sticking_out_image);

        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/vegcale-app.appspot.com/o/plant%2Fcherry_tomato.webp?alt=media&token=e8b314ef-a274-4f7f-9873-37f9111aad5d")
                .into(stickingOutImage);

        TextView vegetableName = rootView.findViewById(R.id.vegetable_name);
        vegetableName.setText("プチトマト");
        TextView seedingTimeMonth = rootView.findViewById(R.id.seeding_month);
        seedingTimeMonth.setText("1月");
        TextView harvestTimeMonth = rootView.findViewById(R.id.harvest_month);
        harvestTimeMonth.setText("3月");
        TextView growthDifficulty = rootView.findViewById(R.id.growth_difficulty);
        growthDifficulty.setText("ややむずかしい");
        TextView vegetableDetail = rootView.findViewById(R.id.vegetable_detail);
        vegetableDetail.setText("リコピン含有量が多くどんな料理にも合う名脇役");
        TextView daylightHour = rootView.findViewById(R.id.hours_of_light);
        daylightHour.setText("1日中");
        TextView wateringFrequency = rootView.findViewById(R.id.watering_frequency);
        wateringFrequency.setText("毎日");
        TextView wateringAmount = rootView.findViewById(R.id.watering_amount);
        wateringAmount.setText("土がひたひたになるまで");


        ImageView backButton = rootView.findViewById(R.id.back_button);
        backButton.setOnClickListener(view ->
                mFragmentUtility.changeFragment(
                        mVegetableListFragment,
                        FragmentUtility.VegetableListFragmentTag,
                        FragmentUtility.SlideAnimation.RightToLeft
                )
        );

        return rootView;
    }
}
