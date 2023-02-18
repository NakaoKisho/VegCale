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

        ViewStub vegetableImageStub = rootView.findViewById(R.id.vegetable_image);
        View inflatedVegetableImage = vegetableImageStub.inflate();
        ImageView imageInVegetableImageViewStub = inflatedVegetableImage.findViewById(R.id.sticking_out_image);
        expandStickingOutImage(imageInVegetableImageViewStub);
        imageInVegetableImageViewStub.setBackgroundResource(R.drawable.ic_cherry_tomatoes_circle);

        TextView imageFrameInVegetableImageViewStub = inflatedVegetableImage.findViewById(R.id.image_frame);
        expandStickingOutImageFrame(imageFrameInVegetableImageViewStub);

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

    private void expandStickingOutImage(ImageView imageInVegetableImageViewStub) {
        ViewGroup.LayoutParams imageLayoutParams = imageInVegetableImageViewStub.getLayoutParams();
        imageLayoutParams.width = 550;
        imageLayoutParams.height = 688;
        imageInVegetableImageViewStub.setLayoutParams(imageLayoutParams);
    }

    private void expandStickingOutImageFrame(TextView imageFrameInVegetableImageViewStub) {
        ViewGroup.LayoutParams imageFrameLayoutParams = imageFrameInVegetableImageViewStub.getLayoutParams();
        imageFrameLayoutParams.width = 400;
        imageFrameLayoutParams.height = 500;
        imageFrameInVegetableImageViewStub.setLayoutParams(imageFrameLayoutParams);
    }
}
