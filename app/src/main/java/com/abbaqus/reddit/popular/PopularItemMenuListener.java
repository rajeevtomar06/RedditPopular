package com.abbaqus.reddit.popular;

import com.abbaqus.reddit.popular.model.PopularModel;

/**
 * Created by Rajeev Tomar on 16,December,2019
 */
public interface PopularItemMenuListener {

    void onTapFavourite(PopularModel popularModel);
    void onTapShare(PopularModel popularModel);
}
