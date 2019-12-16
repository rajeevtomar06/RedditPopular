package com.abbaqus.reddit.favorite.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.abbaqus.reddit.base.BaseViewModel;
import com.abbaqus.reddit.data.Constants;
import com.abbaqus.reddit.db.popular.PopularDataSource;
import com.abbaqus.reddit.popular.model.PopularModel;
import com.abbaqus.reddit.utils.exception.AppException;
import com.abbaqus.reddit.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Rajeev Tomar on 16,December,2019
 */
public class FavoriteViewModel extends BaseViewModel {

    private PopularDataSource popularDataSource;
    private MutableLiveData<List<PopularModel>> favoriteListLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> unFavoriteLiveData = new MutableLiveData<>();

    public FavoriteViewModel(SchedulerProvider schedulerProvider,
                             PopularDataSource popularDataSource) {
        super(schedulerProvider);
        this.popularDataSource = popularDataSource;
    }


    public MutableLiveData<Integer> getUnFavoriteLiveData() {
        return unFavoriteLiveData;
    }

    public MutableLiveData<List<PopularModel>> getFavoriteListLiveData() {
        return favoriteListLiveData;
    }

    public void fetchFavoriteReddits() {
        getCompositeDisposable().add(popularDataSource.getAll().
                observeOn(getSchedulerProvider().ui()).
                subscribeOn(getSchedulerProvider().io()).
                subscribe(popularModelResponseBaseResponse ->
                                favoriteListLiveData.setValue(popularModelResponseBaseResponse),
                        throwable -> getAppExceptionEvent().setValue(new AppException(throwable.getMessage()))));
    }



    public void deletePopularModelInDB(PopularModel popularModel) {
        setIsLoading(true);
        getCompositeDisposable().add(Observable.fromCallable(() -> {
            popularDataSource.deletePopular(popularModel);
            return 1;
        }).observeOn(getSchedulerProvider().ui()).
                subscribeOn(getSchedulerProvider().io()).
                subscribe(value -> {
                    setIsLoading(false);
                    unFavoriteLiveData.setValue(value);
                }, throwable -> {
                    setIsLoading(false);
                    getAppExceptionEvent().setValue(new AppException(throwable.getMessage()));
                }));

    }


}
