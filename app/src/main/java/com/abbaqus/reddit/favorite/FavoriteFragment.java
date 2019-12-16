package com.abbaqus.reddit.favorite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abbaqus.reddit.R;
import com.abbaqus.reddit.RedditApplication;
import com.abbaqus.reddit.base.BaseFragment;
import com.abbaqus.reddit.databinding.FragmentFavoriteBinding;
import com.abbaqus.reddit.databinding.FragmentPopularBinding;
import com.abbaqus.reddit.favorite.viewmodel.FavoriteViewModel;
import com.abbaqus.reddit.popular.PopularItemMenuListener;
import com.abbaqus.reddit.popular.model.PopularModel;

import javax.inject.Inject;

/**
 * Created by Rajeev Tomar on 16,December,2019
 */
public class FavoriteFragment extends BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>
        implements PopularItemMenuListener {

    FavoriteRecyclerViewAdapter mFavoriteRecyclerViewAdapter;
    @Inject
    FavoriteViewModel favoriteViewModel;




    public static FavoriteFragment getInstance() {
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        return favoriteFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // init DI
        RedditApplication.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
        initLiveData();
        loadSavedPopularEvents();
    }

    @Override
    public FavoriteViewModel getViewModel() {
        return favoriteViewModel;
    }

    @Override
    public int getBindingVariable() {
        return com.abbaqus.reddit.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorite;
    }


    //----------------------------------------------------------------------------------------------
    // Private methods
    //----------------------------------------------------------------------------------------------
    private void initAdapter() {
        RecyclerView recyclerView = getViewDataBinding().rvPopularList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFavoriteRecyclerViewAdapter = new FavoriteRecyclerViewAdapter(this);
        recyclerView.setAdapter(mFavoriteRecyclerViewAdapter);
    }


    private void initLiveData() {
        // filter models
        favoriteViewModel.getFavoriteListLiveData().observe(this,
                popularModels -> {
                    if (popularModels != null && popularModels.size() > 0) {
                        // stop shimme animation
                        getViewDataBinding().shimmerViewContainer.stopShimmer();
                        // hide shimmer View
                        getViewDataBinding().shimmerViewContainer.setVisibility(View.GONE);
                        // show recyclerview
                        getViewDataBinding().rvPopularList.setVisibility(View.VISIBLE);
                        // load adapter
                        mFavoriteRecyclerViewAdapter.updateData(popularModels);
                    }
                    else
                    {
                        getViewDataBinding().shimmerViewContainer.stopShimmer();
                        // hide shimmer View
                        getViewDataBinding().shimmerViewContainer.setVisibility(View.GONE);
                        getViewDataBinding().rvPopularList.setVisibility(View.GONE);
                        showSnackBar(getResources().getString(R.string.favorite_not_found),false);
                    }

                });
        // favorite callback
        favoriteViewModel.getUnFavoriteLiveData().observe(this,(result)->{
            showSnackBar(getResources().getString(R.string.favorite_confirm),true);
            loadSavedPopularEvents();
        });
    }


    private void loadSavedPopularEvents() {
        getViewDataBinding().shimmerViewContainer.startShimmer();
        favoriteViewModel.fetchFavoriteReddits();
    }


    private void showPopularConfirmPopup(PopularModel popularModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.delete_favorite_header);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                dialog.dismiss();
                deleteFavorite(popularModel);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteFavorite(PopularModel popularModel)
    {
        favoriteViewModel.deletePopularModelInDB(popularModel);
    }



    //----------------------------------------------------------------------------------------------
    // PopularMenuItemListener implemented methods
    //----------------------------------------------------------------------------------------------
    @Override
    public void onTapFavourite(PopularModel popularModel) {
        showPopularConfirmPopup(popularModel);
    }

    @Override
    public void onTapShare(PopularModel popularModel) {
        if (popularModel != null) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, popularModel.getUrl());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, getResources().getString(R.string.app_name));
            startActivity(shareIntent);
        }
    }

}
