package com.hiepkhach9x.base;

import android.os.Bundle;

import com.hiepkhach9x.base.toolbox.AppNavigationManager;
import com.hiepkhach9x.publiceyes.App;

import co.core.NFragmentHost;
import co.core.activities.NActivity;
import co.core.fragments.NavigationManager;
import co.core.imageloader.NImageLoader;

/**
 * Created by hungh on 3/3/2017.
 */

public abstract class BaseAppActivity extends NActivity implements NFragmentHost {

    protected NavigationManager mNavigationManager;
    protected NImageLoader mImageLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavigationManager = new AppNavigationManager(this, getContentFrame());
        mImageLoader = App.get().getImageLoader();
    }

    public abstract int getContentFrame();

    @Override
    public NImageLoader getImageLoader() {
        return mImageLoader;
    }

    @Override
    public NavigationManager getNavigationManager() {
        return mNavigationManager;
    }
}
