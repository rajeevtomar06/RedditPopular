package com.abbaqus.reddit.di;

import com.abbaqus.reddit.favorite.FavoriteFragment;
import com.abbaqus.reddit.popular.PopularFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RetrofitModule.class,
        RemoteServiceModule.class, DBServiceModule.class,
        ViewModelModule.class})
public interface AppComponent {

    void inject(PopularFragment popularFragment);
    void inject(FavoriteFragment favoriteFragment);

}
