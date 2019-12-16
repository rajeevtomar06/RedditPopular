package com.abbaqus.reddit.db.popular;

import com.abbaqus.reddit.popular.model.PopularModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Rajeev Tomar on 16,December,2019
 */
public interface PopularDataSource  {
    // getAll
    Observable<List<PopularModel>> getAll();

    // insert
    void insertPopular(PopularModel popularModel);

    // delete
    void deletePopular(PopularModel popularModel);

}
