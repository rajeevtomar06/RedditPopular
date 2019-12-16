package com.abbaqus.reddit.favorite;


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
import com.abbaqus.reddit.popular.PopularItemMenuListener;
import com.abbaqus.reddit.popular.PopularRecyclerViewAdapter;
import com.abbaqus.reddit.popular.model.PopularModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class FavoriteRecyclerViewAdapter extends PopularRecyclerViewAdapter{

    public FavoriteRecyclerViewAdapter(PopularItemMenuListener clickListener) {
        super(clickListener);
    }

    @Override
    public void onBindViewHolder(PopularRecyclerViewAdapter.DataViewHolder holder, int position) {
        // change text favorite to Un-Favorite
        holder.binding.tvFavorite.setText(R.string.unfovorite);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void updateData(@Nullable List<PopularModel> data) {
        if( data == null)
            return;
        popularModelList.clear();
        popularModelList = data;
        notifyDataSetChanged();
    }
}
