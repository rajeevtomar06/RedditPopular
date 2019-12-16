
package com.abbaqus.reddit.base;


import com.abbaqus.reddit.utils.exception.AppException;
import com.abbaqus.reddit.utils.rx.SchedulerProvider;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends BaseObservable {

    private N mNavigator;
    private final SchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    private final MutableLiveData<AppException> appExceptionEvent = new MutableLiveData<>();
    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);



    public BaseViewModel(SchedulerProvider schedulerProvider) {
        this.mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
    }




    public void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }

    public N getNavigator() {
        return mNavigator;
    }


    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public MutableLiveData<AppException> getAppExceptionEvent()
    {
        return appExceptionEvent;
    }


    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    protected void onCleared() {
        mCompositeDisposable.dispose();
    }

    protected void handleError(Throwable error)
    {
        if(error == null)
            return;
        appExceptionEvent.setValue(new AppException(error.getMessage()));
    }
}
