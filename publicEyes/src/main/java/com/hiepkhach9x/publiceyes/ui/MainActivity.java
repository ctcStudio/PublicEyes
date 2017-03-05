package com.hiepkhach9x.publiceyes.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hiepkhach9x.base.BaseSlidingActivity;
import com.hiepkhach9x.base.menu.CustomSlidingMenu;
import com.hiepkhach9x.base.toolbox.PermissionGrant;
import com.hiepkhach9x.publiceyes.Constants;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.store.AppPref;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import co.utilities.VideoUtils;

public class MainActivity extends BaseSlidingActivity implements CustomSlidingMenu, View.OnClickListener {

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

        PermissionGrant.verify(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_PERMISSION_LOCATION);
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

        findViewById(R.id.layout_complaints).setOnClickListener(this);
        findViewById(R.id.layout_faq).setOnClickListener(this);
        findViewById(R.id.layout_rate).setOnClickListener(this);
        findViewById(R.id.layout_how_to).setOnClickListener(this);
        findViewById(R.id.layout_report_bug).setOnClickListener(this);
        findViewById(R.id.layout_logout).setOnClickListener(this);
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


    @Override
    public void onClick(View view) {
        hide();
        switch (view.getId()) {
            case R.id.layout_complaints:
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(MyComplaintsFragment.newInstance());
                }
                break;
            case R.id.layout_faq:
                break;
            case R.id.layout_rate:
                break;
            case R.id.layout_how_to:
                break;
            case R.id.layout_report_bug:
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(ReportFragment.newInstance(null));
                }
                break;
            case R.id.layout_logout:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
        }
    }
}
