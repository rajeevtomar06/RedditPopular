package com.abbaqus.reddit.favorite;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.abbaqus.reddit.R;
import com.abbaqus.reddit.base.BaseActivity;
import com.abbaqus.reddit.popular.PopularFragment;
import com.abbaqus.reddit.utils.ActivityUtils;

import java.lang.ref.WeakReference;

public class FavoriteActivity extends BaseActivity {

    public static Intent getNewIntent(WeakReference<Context> contextWeakReference) {
        Intent intent = new Intent(contextWeakReference.get(), FavoriteActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_favorite);
        addFavoriteFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    //----------------------------------------------------------------------------------------------
    // Private methods
    //----------------------------------------------------------------------------------------------
    private void addFavoriteFragment() {
        Fragment favoriteFragment = obtainFavoriteFragment();
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), favoriteFragment,
                R.id.container_favorite);
    }

    private Fragment obtainFavoriteFragment() {
        Fragment favoriteFragment = getSupportFragmentManager().
                findFragmentById(R.id.container_favorite);
        if (favoriteFragment == null)
            favoriteFragment = FavoriteFragment.getInstance();
        return favoriteFragment;
    }

}
