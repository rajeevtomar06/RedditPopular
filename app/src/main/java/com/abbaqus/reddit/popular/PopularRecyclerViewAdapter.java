package com.abbaqus.reddit.popular;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.abbaqus.reddit.R;
import com.abbaqus.reddit.databinding.ViewPopularListItemBinding;
import com.abbaqus.reddit.popular.model.PopularModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class PopularRecyclerViewAdapter extends RecyclerView.Adapter<PopularRecyclerViewAdapter.DataViewHolder> {

    protected List<PopularModel> popularModelList;
    private PopularItemMenuListener popularItemMenuListener;

    public PopularRecyclerViewAdapter(PopularItemMenuListener clickListener) {
        this.popularModelList = new ArrayList<>();
        this.popularItemMenuListener = clickListener;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_popular_list_item,
                new FrameLayout(parent.getContext()), false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        PopularModel dataModel = popularModelList.get(position);
        holder.setViewModel(dataModel);
        // menu click listener
        holder.binding.tvFavorite.setOnClickListener((v -> popularItemMenuListener.onTapFavourite(dataModel)));
        holder.binding.tvShare.setOnClickListener(v -> popularItemMenuListener.onTapShare(dataModel));
    }

    @Override
    public void onViewAttachedToWindow(DataViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(DataViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    protected void updateData(@Nullable List<PopularModel> data) {
        if (data == null)
            return;
        popularModelList.addAll(data);
        notifyItemInserted(popularModelList.size());
    }


    public void insert(int position, PopularModel data) {
        popularModelList.add(position, data);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        if (popularModelList == null)
            return 0;
        return popularModelList.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public  ViewPopularListItemBinding binding;

        DataViewHolder(View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (binding != null) {
                binding.unbind(); // Don't forget to unbind
            }
        }

        void setViewModel(PopularModel viewModel) {
            if (binding != null) {
                binding.setItemData(viewModel);
                // load imageView
                loadImage(binding, viewModel);
            }

        }

        private void loadImage(ViewPopularListItemBinding binding, PopularModel viewModel) {
            if (viewModel != null) {
                int imageWidth = viewModel.getThumbnailWidth();
                if (imageWidth == 0) {
                    binding.ivPopularList.setVisibility(View.GONE);
                } else {
                    binding.ivPopularList.setVisibility(View.VISIBLE);
                    String thumbnailUrl = viewModel.getThumbnail();
                    if (!TextUtils.isEmpty(thumbnailUrl)) {
                        Glide.with(binding.ivPopularList).load(thumbnailUrl).into(binding.ivPopularList);
                    }
                }
            }
        }
    }

}
