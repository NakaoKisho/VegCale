package com.vegcale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.vegcale.utilities.FragmentUtility;

import java.util.ArrayList;

public class ArticleContentFragment  extends Fragment {
    private enum ViewType {
        TextView,
        ImageView
    }
    private FragmentUtility mFragmentUtility;
    private VegetableArticleFragment mVegetableArticleFragment;
    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentUtility = new FragmentUtility(getActivity());
        mVegetableArticleFragment = new VegetableArticleFragment();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                backToVegetableArticleFragment();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_article_content, container, false);
        ImageView backButton = rootView.findViewById(R.id.back_button);
        backButton.setOnClickListener(item -> backToVegetableArticleFragment());

        if (getArguments() == null) return rootView;
        setData();

        return rootView;
    }

    private void backToVegetableArticleFragment() {
        mFragmentUtility.changeFragment(
                mVegetableArticleFragment,
                FragmentUtility.VegetableArticleFragmentTag,
                FragmentUtility.SlideAnimation.RightToLeft
        );
    }

    private ArrayList<View> generateViews(ViewType viewType, ConstraintLayout articleContentConstraintLayout, ArrayList<String> arrayListArguments) {
        assert getArguments() != null;
        ArrayList<View> views = new ArrayList<>();

        for (String argument : arrayListArguments) {
            View newView;

            if (viewType == ViewType.TextView) {
                newView = new TextView(getContext());
                ((TextView) newView).setText(argument);
            } else {
                newView = new ImageView(getContext());
                Glide.with(this)
                        .load(argument)
                        .into((ImageView) newView);
            }

            newView.setId(View.generateViewId());
            articleContentConstraintLayout.addView(newView);
            views.add(newView);
        }

        return views;
    }

    private void addViewsToConstraintLayout(
            ConstraintLayout articleContentConstraintLayout,
            ArrayList<View> textViews,
            ArrayList<View> imageViews
    ) {
        int maxArrayListSize = Math.max(textViews.size(), imageViews.size());
        ConstraintSet mConstraintSet = new ConstraintSet();
        mConstraintSet.clone(articleContentConstraintLayout);

        int bottomViewId = R.id.article_title_image;
        for (int i = 0; i < maxArrayListSize; i++) {
            if (i > textViews.size()) continue;

            int viewId = textViews.get(i).getId();
            setLeftRightAndTopConstraint(mConstraintSet, viewId, bottomViewId);
            bottomViewId = viewId;

            viewId = imageViews.get(i).getId();
            setLeftRightAndTopConstraint(mConstraintSet, viewId, bottomViewId);
            bottomViewId = viewId;
        }
        mConstraintSet.applyTo(articleContentConstraintLayout);
    }

    private void setLeftRightAndTopConstraint(ConstraintSet mConstraintSet, int viewId, int bottomViewId) {
        mConstraintSet.connect(viewId, ConstraintSet.LEFT, R.id.article_content, ConstraintSet.LEFT);
        mConstraintSet.connect(viewId, ConstraintSet.RIGHT, R.id.article_content, ConstraintSet.RIGHT);
        mConstraintSet.connect(viewId, ConstraintSet.TOP, bottomViewId, ConstraintSet.BOTTOM);
    }

    private void setData() {
        assert getArguments() != null;

        TextView articleTitle = rootView.findViewById(R.id.article_title);
        articleTitle.setText(getArguments().getString("title"));

        ImageView articleTitleImage = rootView.findViewById(R.id.article_title_image);
        Glide.with(this)
                .load(getArguments().getString("titleImage"))
                .into(articleTitleImage);

        ConstraintLayout articleContentConstraintLayout = rootView.findViewById(R.id.article_content);

        ArrayList<String> paragraphs = getArguments().getStringArrayList("paragraphs");
        ArrayList<View> textViews = generateViews(ViewType.TextView, articleContentConstraintLayout, paragraphs);

        ArrayList<String> paragraphImages = getArguments().getStringArrayList("paragraphImages");
        ArrayList<View> imageViews = generateViews(ViewType.ImageView, articleContentConstraintLayout, paragraphImages);

        addViewsToConstraintLayout(articleContentConstraintLayout, textViews, imageViews);
    }
}
