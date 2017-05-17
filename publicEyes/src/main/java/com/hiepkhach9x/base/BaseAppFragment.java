package com.hiepkhach9x.base;

/**
 * @author TUNGDX
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hiepkhach9x.base.api.Api;
import com.hiepkhach9x.base.dialog.LoadingDialogFragment;
import com.hiepkhach9x.publiceyes.App;
import com.hiepkhach9x.publiceyes.R;

import co.core.actionbar.CustomActionbar;
import co.core.fragments.NFragment;

/**
 * This is base fragment. <br>
 * It contains some default attributes: Context, Api, ImageLoader,
 * NavigationManager, Actionbar <br>
 */
public abstract class BaseAppFragment extends NFragment {

    protected CustomActionbar mActionbar;

    public static final String DIALOG_TAG = "dialog_tag";
    public static final String LOADING_TAG = "loading_tag";

    private Handler mHandler;
    protected Api mApi;
    private ProgressDialog apiDialog;

    public BaseAppFragment() {
        setArguments(new Bundle());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = App.get().getApi();
    }

    @Override
    @CallSuper
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mActionbar != null) {
            mActionbar.initialize(mNavigationManager, this);
        }
    }

    @Override
    protected boolean isHasActionbar() {
        return true;
    }

    @LayoutRes
    protected int getLayoutRes() {
        return 0;
    }

    /**
     * Shouldn't override this function...Use {@link #getLayoutRes()}
     */
    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (!isHasActionbar()) {
            int layoutRes = getLayoutRes();
            if (layoutRes != 0) {
                return inflater.inflate(layoutRes, container, false);
            } else return null;
        } else {
            LinearLayout frame = (LinearLayout) inflater.inflate(R.layout.fragment_frame_has_actionbar, container, false);

            int layoutRes = getLayoutRes();
            if (layoutRes != 0) {
                View contentView = inflater.inflate(layoutRes, container, false);
                frame.addView(contentView);
            }

            mActionbar = (CustomActionbar) frame.findViewById(R.id.actionbar);

            return frame;
        }
    }

    protected void showApiLoading() {
        if (apiDialog == null) {
            apiDialog = new ProgressDialog(getActivity());
            apiDialog.setMessage("Loading..");
        }

        if (!apiDialog.isShowing()) {
            apiDialog.show();
        }
    }

    protected void dismissApiLoading() {
        if (apiDialog != null && apiDialog.isShowing()) {
            apiDialog.dismiss();
        }
    }


    protected void showLoading() {
        Fragment loading = getChildFragmentManager().findFragmentByTag(LOADING_TAG);
        if (loading != null) return;

        new LoadingDialogFragment().show(getChildFragmentManager(), LOADING_TAG);
    }

    protected void hideLoading() {

        if (mHandler == null) mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Fragment loading = getChildFragmentManager().findFragmentByTag(LOADING_TAG);
                if (loading == null) return;

                ((DialogFragment) loading).dismissAllowingStateLoss();
            }
        }, 500);
    }

    protected final void showDialog(DialogFragment dialog) {
        if (dialog == null) return;

        dialog.show(getChildFragmentManager(), DIALOG_TAG);
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}
