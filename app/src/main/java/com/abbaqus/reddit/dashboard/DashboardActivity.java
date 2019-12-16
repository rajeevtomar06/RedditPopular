package com.abbaqus.reddit.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.abbaqus.reddit.R;
import com.abbaqus.reddit.favorite.FavoriteActivity;
import com.abbaqus.reddit.popular.PopularFragment;
import com.abbaqus.reddit.utils.ActivityUtils;

import java.lang.ref.WeakReference;

public class DashboardActivity extends AppCompatActivity {


    //----------------------------------------------------------------------------------------------
    // Static methods
    //----------------------------------------------------------------------------------------------
    public static Intent getNewIntent(WeakReference<Context> contextWeakReference) {
        Intent intent = new Intent(contextWeakReference.get(), DashboardActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addPopularFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_favorite:
                launchFavoriteActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //----------------------------------------------------------------------------------------------
    // Private methods
    //----------------------------------------------------------------------------------------------
    private void addPopularFragment() {
        Fragment popularFragment = obtainPopularFragment();
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), popularFragment,
                R.id.container_dashboard);
    }

    private Fragment obtainPopularFragment() {
        Fragment popularFragment = getSupportFragmentManager().
                findFragmentById(R.id.container_dashboard);
        if (popularFragment == null)
            popularFragment = PopularFragment.getInstance();
        return popularFragment;
    }

    private void launchFavoriteActivity(){
        Intent intent = FavoriteActivity.getNewIntent(new WeakReference<>(this));
        startActivity(intent);

    }
}
