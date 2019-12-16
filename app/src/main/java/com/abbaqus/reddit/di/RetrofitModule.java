package com.abbaqus.reddit.di;

import com.abbaqus.reddit.server.RestManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RetrofitModule {

    // Create separate module as in future can add multiple interceptor here like - logging interceptor

    @Provides
    @Singleton
    public Retrofit provideRetrofit()
    {
        return RestManager.getRetrofitClient();
    }



}
