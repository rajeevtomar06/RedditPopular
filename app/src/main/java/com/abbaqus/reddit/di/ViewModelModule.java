package com.abbaqus.reddit.di;

import com.abbaqus.reddit.data.remote.RedditPopularService;
import com.abbaqus.reddit.db.popular.PopularDataSource;
import com.abbaqus.reddit.favorite.viewmodel.FavoriteViewModel;
import com.abbaqus.reddit.popular.viewmodel.PopularViewModel;
import com.abbaqus.reddit.utils.rx.AppSchedulerProvider;
import com.abbaqus.reddit.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    @Singleton
    public  SchedulerProvider provideScheduler()
    {
        return new AppSchedulerProvider();
    }

    @Provides
    public PopularViewModel providePopularViewModel(SchedulerProvider schedulerProvider,
                                                    PopularDataSource popularDataSource,
                                                    RedditPopularService popularService)
    {
        return new PopularViewModel(schedulerProvider, popularDataSource,
                popularService);

    }

    @Provides
    public FavoriteViewModel provideFavoriteViewModel(SchedulerProvider schedulerProvider,
                                                     PopularDataSource popularDataSource
                                                     )
    {
        return new FavoriteViewModel(schedulerProvider, popularDataSource);

    }
}
