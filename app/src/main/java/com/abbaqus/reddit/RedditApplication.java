package com.abbaqus.reddit;

import android.app.Application;

import com.abbaqus.reddit.di.AppComponent;
import com.abbaqus.reddit.di.AppModule;
import com.abbaqus.reddit.di.DaggerAppComponent;

public class RedditApplication extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        // init appcomponent
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
