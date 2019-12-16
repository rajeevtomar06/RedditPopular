package com.abbaqus.reddit.di;

import android.content.Context;

import com.abbaqus.reddit.data.local.PopularLocalDataSource;
import com.abbaqus.reddit.db.AppDatabase;
import com.abbaqus.reddit.db.popular.PopularDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rajeev Tomar on 16,December,2019
 */
@Module
public class DBServiceModule {

    @Provides
    @Singleton
    public AppDatabase provideDatabase(Context context)
    {
        return AppDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    public PopularDataSource providePopularDataSource(AppDatabase appDatabase)
    {
        return new PopularLocalDataSource(appDatabase.popularDao());
    }


}
