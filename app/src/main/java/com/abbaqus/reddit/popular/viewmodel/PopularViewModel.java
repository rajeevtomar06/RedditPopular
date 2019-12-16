package com.abbaqus.reddit.popular.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.abbaqus.reddit.base.BaseViewModel;
import com.abbaqus.reddit.data.Constants;
import com.abbaqus.reddit.data.remote.RedditPopularService;
import com.abbaqus.reddit.db.popular.PopularDataSource;
import com.abbaqus.reddit.popular.model.PopularModel;
import com.abbaqus.reddit.popular.model.PopularModelResponse;
import com.abbaqus.reddit.server.BaseResponse;
import com.abbaqus.reddit.utils.exception.AppException;
import com.abbaqus.reddit.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class PopularViewModel extends BaseViewModel {

    private MutableLiveData<BaseResponse<PopularModelResponse>> responseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<PopularModel>> popularListLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> favoriteLiveData = new MutableLiveData<>();

    private RedditPopularService redditPopularService;
    private PopularDataSource popularDataSource;

    public PopularViewModel(SchedulerProvider schedulerProvider,
                            PopularDataSource popularDataSource,
                            RedditPopularService redditPopularService) {
        super(schedulerProvider);
        this.redditPopularService = redditPopularService;
        this.popularDataSource = popularDataSource;
    }


    public MutableLiveData<BaseResponse<PopularModelResponse>> getResponseMutableLiveData() {
        return responseMutableLiveData;
    }

    public MutableLiveData<List<PopularModel>> getPopularListLiveData() {
        return popularListLiveData;
    }

    public MutableLiveData<Integer> getFavoriteLiveData() {
        return favoriteLiveData;
    }

    public void fetchPopularReddits(String after) {
        getCompositeDisposable().add(redditPopularService.getPopularList(Constants.ITEM_COUNT_PER_PAGE,
                after).
                observeOn(getSchedulerProvider().ui()).
                subscribeOn(getSchedulerProvider().io()).
                subscribe(popularModelResponseBaseResponse ->
                                responseMutableLiveData.setValue(popularModelResponseBaseResponse),
                        throwable -> getAppExceptionEvent().setValue(new AppException(throwable.getMessage()))));
    }

    public void filterPopularModelList(List<PopularModelResponse> popularModelResponseList) {
        if (popularModelResponseList == null || popularModelResponseList.size() == 0)
            return;
        setIsLoading(true);
        getCompositeDisposable().add(Observable.
                fromIterable(popularModelResponseList).
                map(popularModelResponse ->
                        popularModelResponse.getPopularModel()).
                toList().
                subscribeOn(getSchedulerProvider().io()).
                observeOn(getSchedulerProvider().ui()).
                subscribe(popularModels -> {
                            setIsLoading(false);
                            popularListLiveData.setValue(popularModels);
                        }, throwable ->
                        {
                            setIsLoading(false);
                            getAppExceptionEvent().setValue(new AppException(throwable.getMessage()));
                        }
                ));
    }

    public void savePopularModelInDB(PopularModel popularModel) {
        setIsLoading(true);
        getCompositeDisposable().add(Observable.fromCallable(() -> {
            popularDataSource.insertPopular(popularModel);
            return 1;
        }).observeOn(getSchedulerProvider().ui()).
                subscribeOn(getSchedulerProvider().io()).
                subscribe(value -> {
                    setIsLoading(false);
                    favoriteLiveData.setValue(value);
                }, throwable -> {
                    setIsLoading(false);
                    getAppExceptionEvent().setValue(new AppException(throwable.getMessage()));
                }));

    }

}
