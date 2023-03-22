package com.vegcale.adapters;

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
import com.vegcale.ArticleContentFragment;
import com.vegcale.R;
import com.vegcale.data.Article;
import com.vegcale.data.VegcaleDatabase;
import com.vegcale.utilities.FragmentUtility;

import java.util.ArrayList;
import java.util.List;

public class VegetableArticleListRecyclerView
        extends RecyclerView.Adapter<VegetableArticleListRecyclerView.ViewHolder>
        implements OnCompleteListener<DataSnapshot>, ValueEventListener {

    private enum ProgressBarVisibility {
        Visible,
        Invisible
    }

    private final FragmentActivity mFragmentActivity;
    private final List<Article> data = new ArrayList<>();
    private final SnackBarCallbackListener mSnackBarCallbackListener;
    private final VegcaleDatabase mVegcaleDatabase;
    private Integer itemCount;
    private int dataFetchCount = 0;
    private String latestDataKey;

    private void changeProgressCircleVisibility(@NonNull VegetableArticleListRecyclerView.ViewHolder holder, VegetableArticleListRecyclerView.ProgressBarVisibility visibility) {
        int infoGroupVisibility;
        int progressBarVisibility;

        if (visibility == VegetableArticleListRecyclerView.ProgressBarVisibility.Visible) {
            infoGroupVisibility = View.INVISIBLE;
            progressBarVisibility = View.VISIBLE;
        } else {
            infoGroupVisibility = View.VISIBLE;
            progressBarVisibility = View.INVISIBLE;
        }

        holder.infoGroup.setVisibility(infoGroupVisibility);
        holder.progressCircle.setVisibility(progressBarVisibility);
    }

    private void moveToArticleContentFragment(int position) {
        ArticleContentFragment mArticleContentFragment = new ArticleContentFragment();
        setArticleContentData(mArticleContentFragment, position);

        new FragmentUtility(mFragmentActivity).changeFragment(
                mArticleContentFragment,
                FragmentUtility.ArticleContentFragmentTag,
                FragmentUtility.SlideAnimation.LeftToRight
        );
    }

    private void setArticleContentData(@NonNull ArticleContentFragment mArticleContentFragment, int position) {
        Bundle mBundle = new Bundle();
        Article articleData = data.get(position);

        mBundle.putString("titleImage", articleData.getTitle_image());
        mBundle.putString("title", articleData.getTitle());
        mBundle.putStringArrayList("paragraphs", articleData.getParagraph());
        mBundle.putStringArrayList("paragraphImages", articleData.getParagraph_image_url());
        mBundle.putStringArrayList("tags", articleData.getTag());
        mArticleContentFragment.setArguments(mBundle);
    }

    private void setItemData(@NonNull VegetableArticleListRecyclerView.ViewHolder holder, int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Article articleData = data.get(position);
        String titleImageUrl = articleData.getTitle_image();
        StorageReference imageReference = storage.getReferenceFromUrl(titleImageUrl);

        Glide.with(holder.itemView)
                .load(imageReference)
                .into(holder.titleImage);

        holder.title.setText(articleData.getTitle());
        ArrayList<String> tags = articleData.getTag();
        setTags(tags.get(0), holder.tag1);
        setTags(tags.get(1), holder.tag2);
        setTags(tags.get(2), holder.tag3);
        setTags(tags.get(3), holder.tag4);
    }

    private void setTags(String tagData, TextView tagTextView) {
        if (tagData.equals("")) {
            tagTextView.setVisibility(View.GONE);
            tagTextView.setText("");
        } else {
            tagTextView.setVisibility(View.VISIBLE);
            tagTextView.setText(tagData);
        }
    }

    public VegetableArticleListRecyclerView(
            FragmentActivity mFragmentActivity,
            SnackBarCallbackListener mSnackBarCallbackListener
    ) {
        this.mFragmentActivity = mFragmentActivity;
        this.mSnackBarCallbackListener = mSnackBarCallbackListener;
        mVegcaleDatabase = new VegcaleDatabase();
        mVegcaleDatabase.getCount(mVegcaleDatabase.plantsArticleRootPath, this);
    }

    @Override
    public int getItemCount() {
        final int defaultDisplayNumber = 1;

        return data.isEmpty() ? defaultDisplayNumber : data.size();
    }

    public interface SnackBarCallbackListener {
        void showErrorSnackBar();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar progressCircle;
        private final Group infoGroup;
        private final ImageView titleImage;
        private final TextView title;
        private final TextView tag1;
        private final TextView tag2;
        private final TextView tag3;
        private final TextView tag4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            progressCircle = itemView.findViewById(R.id.progress_circle);
            infoGroup = itemView.findViewById(R.id.info_group);
            titleImage = itemView.findViewById(R.id.title_image);
            title = itemView.findViewById(R.id.title);
            tag1 = itemView.findViewById(R.id.tag1);
            tag2 = itemView.findViewById(R.id.tag2);
            tag3 = itemView.findViewById(R.id.tag3);
            tag4 = itemView.findViewById(R.id.tag4);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.box_item, parent, false);

        return new ViewHolder(cardItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int nextItemCount = 10;

        if (itemCount == null) return;

        if (data.isEmpty()) {
            changeProgressCircleVisibility(holder, ProgressBarVisibility.Visible);
            mVegcaleDatabase.fetchArticlesData(
                    nextItemCount,
                    true,
                    null,
                    this
            );
            dataFetchCount++;
            itemCount -= nextItemCount;

            return;
        }

        changeProgressCircleVisibility(holder, ProgressBarVisibility.Invisible);
        setItemData(holder, position);
        holder.itemView.setOnClickListener(view -> moveToArticleContentFragment(position));

        if (itemCount <= 0) return;

        int nextDataFetchPosition = nextItemCount * dataFetchCount - 1;
        if (position == nextDataFetchPosition) {
            mVegcaleDatabase.fetchArticlesData(
                    nextItemCount,
                    false,
                    latestDataKey,
                    this);
            dataFetchCount++;
            itemCount -= nextItemCount;
            if (itemCount < 0) {
                itemCount = 0;
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        mSnackBarCallbackListener.showErrorSnackBar();
    }

    @Override
    public void onComplete(@NonNull Task<DataSnapshot> task) {
        if (!task.isSuccessful()) {
            mSnackBarCallbackListener.showErrorSnackBar();
            return;
        }

        DataSnapshot resultDataSnapshot = task.getResult();
        itemCount = resultDataSnapshot.getValue(Integer.class);

        int firstItemIndex = 0;
        notifyItemChanged(firstItemIndex);

        if (itemCount == null) {
            mSnackBarCallbackListener.showErrorSnackBar();
        }
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
            data.add(children.getValue(Article.class));
            latestDataKey = children.getKey();
        }
    }
}
