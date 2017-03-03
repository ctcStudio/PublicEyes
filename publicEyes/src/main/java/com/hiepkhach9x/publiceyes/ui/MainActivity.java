package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hiepkhach9x.base.BaseSlidingActivity;
import com.hiepkhach9x.base.menu.CustomSlidingMenu;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.store.AppPref;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseSlidingActivity implements CustomSlidingMenu {

    SlidingMenu mSlidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.activity_left_menu);

        initSlidingMenu();

        if (mNavigationManager != null
                && mNavigationManager.getActivePage() == null) {
            if (AppPref.get().isFirstLogin()) {
                mNavigationManager.swapPage(new SliderFragment());
            } else {
                mNavigationManager.swapPage(new HomeFragment());
            }
        }
    }

    private void initSlidingMenu() {
        // customize the SlidingMenu
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setSlidingEnabled(false);
    }

    @Override
    public int getContentFrame() {
        return R.id.content_layout;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void show() {
        if (mSlidingMenu != null) {
            mSlidingMenu.showContent(true);
        }
    }

    @Override
    public void hide() {
        if (mSlidingMenu != null) {
            mSlidingMenu.showContent(false);
        }
    }

    @Override
    public void toggle() {
        if (mSlidingMenu != null) {
            mSlidingMenu.toggle();
        }
    }

    @Override
    public void setEnableSliding(boolean enable) {
        if (mSlidingMenu != null) {
            mSlidingMenu.setSlidingEnabled(enable);
        }
    }


}
