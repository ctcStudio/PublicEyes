package com.hiepkhach9x.base.actionbar;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.ui.HomeFragment;
import com.hiepkhach9x.publiceyes.ui.ReportFragment;

import co.core.actionbar.CustomActionbar;
import co.core.fragments.NavigationManager;

/**
 * Created by NTQ on 12/25/2015.
 */
public class NativeActionbarView extends FrameLayout implements CustomActionbar {
    private NavigationManager mNavigationManager;
    private int mCurrentResId;
    private TextView mTitle;
    private ImageButton mLeftView;
    private View mRightView;

    public NativeActionbarView(Context context) {
        super(context);
    }

    public NativeActionbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NativeActionbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initialize(NavigationManager navigationManager, Fragment fragment) {
        mNavigationManager = navigationManager;
        syncActionBar(fragment);
    }

    @Override
    public void syncActionBar(Fragment activePage) {
        if (activePage == null) return;
        int newResId = findResourceIdForActionbar(activePage);
        if (newResId != mCurrentResId) {
            mCurrentResId = newResId;
            removeAllChildViews();
            inflate(getContext(), mCurrentResId, this);
            findChildViews();
            setupChildViews();
        }
        syncChildView(activePage);
    }

    private void setupChildViews() {
        setUpLeft();
        setUpRight();
    }

    private void findChildViews() {
        mLeftView = (ImageButton) findViewById(R.id.actionbar_left);
        mTitle = (TextView) findViewById(R.id.actionbar_title);
        mRightView = findViewById(R.id.actionbar_right);
    }

    protected int findResourceIdForActionbar(Fragment activePage) {
        int layout = R.layout.actionbar_default;
        if (activePage instanceof ReportFragment) {
            layout = R.layout.actionbar_upload;
        }
        return layout;
    }

    protected void syncChildView(Fragment activePage) {
        syncTitle(activePage);
    }

    private void syncTitle(Fragment activePage) {
        if (mTitle == null) return;

        if (activePage instanceof ActionbarInfo) {
            ActionbarInfo actionbarInfo = (ActionbarInfo) activePage;
            String title = actionbarInfo.getActionbarTitle();

            //TODO set title
            mTitle.setText(title);
        }
    }

    @Override
    public void hide() {
        setVisibility(View.GONE);
    }

    @Override
    public void show() {
        setVisibility(View.VISIBLE);
    }

    protected void removeAllChildViews() {
        removeAllViews();
    }

    private void setUpLeft() {
        if (mLeftView == null) return;
        final Fragment fragment = mNavigationManager.getActivePage();

        int imageRes = R.drawable.ic_back;
        if (fragment instanceof HomeFragment) {
            imageRes = R.drawable.ic_menu_white;
        }
        mLeftView.setImageResource(imageRes);

        mLeftView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Allow the current fragment controls action back
                if (fragment != null && fragment instanceof ActionbarHandler)
                    ((ActionbarHandler) fragment).onLeftHandled();
                    // Normal back
                else if (!mNavigationManager.goBack()) {
                    mNavigationManager.finishActivity();
                }
            }
        });
    }

    private void setUpRight() {
        if (mRightView == null) return;

        mRightView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = mNavigationManager.getActivePage();
                // Allow the current fragment controls action back
                if (fragment != null && fragment instanceof ActionbarHandler)
                    ((ActionbarHandler) fragment).onRightHandled();
            }
        });
    }
}
