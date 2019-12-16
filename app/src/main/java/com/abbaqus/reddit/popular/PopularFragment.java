package com.abbaqus.reddit.popular;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abbaqus.reddit.BR;
import com.abbaqus.reddit.R;
import com.abbaqus.reddit.RedditApplication;
import com.abbaqus.reddit.base.BaseFragment;
import com.abbaqus.reddit.data.Constants;
import com.abbaqus.reddit.databinding.FragmentPopularBinding;
import com.abbaqus.reddit.popular.model.PopularModel;
import com.abbaqus.reddit.popular.model.PopularModelResponse;
import com.abbaqus.reddit.popular.viewmodel.PopularViewModel;
import com.abbaqus.reddit.server.BaseResponse;
import com.abbaqus.reddit.utils.RecyclerViewScrollListener;

import javax.inject.Inject;


public class PopularFragment extends BaseFragment<FragmentPopularBinding, PopularViewModel>
        implements PopularItemMenuListener {

    @Inject
    PopularViewModel popularViewModel;
    private String mAfterString;
    private PopularRecyclerViewAdapter mPopularRecyclerViewAdapter;
    private boolean isLastPage;


    public static PopularFragment getInstance() {
        PopularFragment popularFragment = new PopularFragment();
        return popularFragment;
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
        loadPopularRedditEvents();
    }


    @Override
    public PopularViewModel getViewModel() {
        return popularViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_popular;
    }


    //----------------------------------------------------------------------------------------------
    // Private methods
    //----------------------------------------------------------------------------------------------
    private void initAdapter() {
        RecyclerView recyclerView = getViewDataBinding().rvPopularList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPopularRecyclerViewAdapter = new PopularRecyclerViewAdapter(this);
        recyclerView.setAdapter(mPopularRecyclerViewAdapter);

        // set scroll listener
        getViewDataBinding().rvPopularList.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {
                // load more if didn't reached at last
                loadMorePopularReddits();

            }

            @Override
            public void onLoadMore() {

            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        });

    }


    private void initLiveData() {
        popularViewModel.getResponseMutableLiveData().observe(this,
                this::handlePopularDataResponse
        );
        // filter models
        popularViewModel.getPopularListLiveData().observe(this,
                popularModels -> {
                    if (popularModels != null && popularModels.size() > 0) {
                        // stop shimme animation
                        getViewDataBinding().shimmerViewContainer.stopShimmer();
                        // hide shimmer View
                        getViewDataBinding().shimmerViewContainer.setVisibility(View.GONE);
                        // show recyclerview
                        getViewDataBinding().rvPopularList.setVisibility(View.VISIBLE);
                        // check if item are less then requires count
                        if (popularModels.size() < Constants.ITEM_COUNT_PER_PAGE)
                            isLastPage = true;
                        // load adapter
                        mPopularRecyclerViewAdapter.updateData(popularModels);
                    }

                });
        // favorite callback
        popularViewModel.getFavoriteLiveData().observe(this,(result)->{
            showSnackBar(getResources().getString(R.string.favorite_confirm),true);
        });
    }


    private void loadPopularRedditEvents() {
        getViewDataBinding().shimmerViewContainer.startShimmer();
        popularViewModel.fetchPopularReddits(null);
    }

    private void loadMorePopularReddits() {
        // don't want to show shimmer again
        popularViewModel.fetchPopularReddits(mAfterString);
    }


    private void handlePopularDataResponse(BaseResponse<PopularModelResponse>
                                                   popularModelResponseBaseResponse) {
        // set after String
        if (popularModelResponseBaseResponse != null && popularModelResponseBaseResponse.getData() != null) {
            mAfterString = popularModelResponseBaseResponse.getData().getAfter();

            //filter list
            popularViewModel.filterPopularModelList(popularModelResponseBaseResponse.getData().
                    getResultList());
        } else {
            // stop shimme animation
            getViewDataBinding().shimmerViewContainer.stopShimmer();
            // hide shimmer View
            getViewDataBinding().popularShimmerView.setVisibility(View.GONE);
        }

    }

    private void showPopularConfirmPopup(PopularModel popularModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.favorite_confirm_header);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                dialog.dismiss();
                makeFavorite(popularModel);
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

    private void makeFavorite(PopularModel popularModel)
    {
        popularViewModel.savePopularModelInDB(popularModel);
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
