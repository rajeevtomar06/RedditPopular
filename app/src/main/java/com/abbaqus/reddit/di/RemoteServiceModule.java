package com.abbaqus.reddit.di;

import com.abbaqus.reddit.data.remote.RedditPopularService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RemoteServiceModule {

    @Provides
    public RedditPopularService providePopularService(Retrofit retrofit)
    {
        return retrofit.create(RedditPopularService.class);
    }


}
