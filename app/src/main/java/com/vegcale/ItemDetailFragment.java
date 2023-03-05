package com.vegcale;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.vegcale.utility.Conversion;
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
                backToVegetableListFragment();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        if (getArguments() == null) return rootView;

        setData(rootView);
        ImageView backButton = rootView.findViewById(R.id.back_button);
        backButton.setOnClickListener(view ->
                backToVegetableListFragment()
        );

        String hoursOfLight = getArguments().getString("hoursOfLight");
        if (hoursOfLight.equals(Plant.MORE_THAN_6_HOURS_OF_LIGHT)) {
            hideBottomCloud(rootView);
            hideTopCloud(rootView);
        } else if (hoursOfLight.equals(Plant.ONE_TO_TWO_HOURS_OF_LIGHT)){
            animateBottomCloud(rootView);
            animateTopCloud(rootView);
        } else {
            hideTopCloud(rootView);
            animateBottomCloud(rootView);
        }

        return rootView;
    }

    private void animateBottomCloud(View rootView) {
        Animator swayAnimator = AnimatorInflater.loadAnimator(getContext(), R.animator.bottom_sway);
        ImageView bottomCloud = rootView.findViewById(R.id.bottom_cloud);
        swayAnimator.setTarget(bottomCloud);
        swayAnimator.start();
    }

    private void animateTopCloud(View rootView) {
        Animator topSwayAnimator = AnimatorInflater.loadAnimator(getContext(), R.animator.top_sway);
        ImageView topCloud = rootView.findViewById(R.id.top_cloud);
        topSwayAnimator.setTarget(topCloud);
        topSwayAnimator.start();
    }

    private void backToVegetableListFragment() {
        mFragmentUtility.changeFragment(
                mVegetableListFragment,
                FragmentUtility.VegetableListFragmentTag,
                FragmentUtility.SlideAnimation.RightToLeft
        );
    }

    private void hideBottomCloud(View rootView) {
        ImageView bottomCloud = rootView.findViewById(R.id.bottom_cloud);
        bottomCloud.setVisibility(View.INVISIBLE);
    }

    private void hideTopCloud(View rootView) {
        ImageView topCloud = rootView.findViewById(R.id.top_cloud);
        topCloud.setVisibility(View.INVISIBLE);
    }

    private void setData(View rootView) {
        assert getArguments() != null;

        TextView detail = rootView.findViewById(R.id.vegetable_detail);
        detail.setText(getArguments().getString("detail"));

        TextView growthDifficulty = rootView.findViewById(R.id.growth_difficulty);
        growthDifficulty.setText(getArguments().getString("growthDifficulty"));

        TextView harvestMonth = rootView.findViewById(R.id.harvest_month);
        int harvestMonthFrom = getArguments().getInt("harvestMonthFrom");
        int harvestMonthTo = getArguments().getInt("harvestMonthTo");
        Conversion mConversion = new Conversion();
        harvestMonth.setText(mConversion.convertMonthRangeIntToText(harvestMonthFrom, harvestMonthTo));

        TextView hoursOfLight = rootView.findViewById(R.id.hours_of_light);
        hoursOfLight.setText(getArguments().getString("hoursOfLight"));

        ImageView stickingOutImage = rootView.findViewById(R.id.sticking_out_image);
        Glide.with(this)
                .load(getArguments().getString("imageUrl"))
                .into(stickingOutImage);

        TextView vegetableName = rootView.findViewById(R.id.vegetable_name);
        vegetableName.setText(getArguments().getString("name"));

        TextView seedingMonth = rootView.findViewById(R.id.seeding_month);
        int seedingMonthFrom = getArguments().getInt("seedingMonthFrom");
        int seedingMonthTo = getArguments().getInt("seedingMonthTo");
        seedingMonth.setText(mConversion.convertMonthRangeIntToText(seedingMonthFrom, seedingMonthTo));

        TextView wateringAmount = rootView.findViewById(R.id.watering_amount);
        wateringAmount.setText(getArguments().getString("wateringAmount"));

        TextView wateringFrequency = rootView.findViewById(R.id.watering_frequency);
        wateringFrequency.setText(getArguments().getString("wateringFrequency"));
    }
}
