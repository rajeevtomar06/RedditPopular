package com.abbaqus.reddit.data.local;

import com.abbaqus.reddit.db.popular.PopularDao;
import com.abbaqus.reddit.db.popular.PopularDataSource;
import com.abbaqus.reddit.popular.model.PopularModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Rajeev Tomar on 16,December,2019
 */
public class PopularLocalDataSource implements PopularDataSource {

    private  final PopularDao popularDao;

    public PopularLocalDataSource(PopularDao popularDao) {
        this.popularDao = popularDao;
    }

    @Override
    public Observable<List<PopularModel>> getAll() {
        return popularDao.getAll();
    }

    @Override
    public void insertPopular(PopularModel popularModel) {
        popularDao.insert(popularModel);
    }

    @Override
    public void deletePopular(PopularModel popularModel) {
        popularDao.delete(popularModel);
    }
}
