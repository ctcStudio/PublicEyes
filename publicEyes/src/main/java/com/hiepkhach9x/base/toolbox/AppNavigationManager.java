package com.hiepkhach9x.base.toolbox;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

import com.hiepkhach9x.publiceyes.R;

import java.util.ArrayList;
import java.util.Stack;

import co.core.activities.NActivity;
import co.core.fragments.NavigationManager;

public class AppNavigationManager implements NavigationManager {
    protected NActivity mActivity;
    protected final Stack mBackStack = new MainThreadStack();
    protected FragmentManager mFragmentManager;
    protected int mPlaceholder;

    public AppNavigationManager(NActivity activity, int frameId) {
        mActivity = activity;
        mPlaceholder = frameId;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    public void addOnBackStackChangedListener(
            OnBackStackChangedListener backStackChangedListener) {
        mFragmentManager
                .addOnBackStackChangedListener(backStackChangedListener);
    }

    public void removeOnBackStackChangedListener(
            OnBackStackChangedListener onbackstackchangedlistener) {
        mFragmentManager
                .removeOnBackStackChangedListener(onbackstackchangedlistener);
    }

    /**
     * Check whether can navigate or not.
     *
     * @return true if can navigate, false otherwise.
     */
    protected boolean canNavigate() {
        return mActivity != null && mActivity.canChangeFragmentManagerState();
    }

    @Override
    public boolean goBack() {
        if (mActivity == null || !mActivity.canChangeFragmentManagerState()
                || mBackStack.isEmpty() || mFragmentManager.getBackStackEntryCount() == 0)
            return false;
        mBackStack.pop();
        mFragmentManager.popBackStack();
        // Even popped back stack, the fragment which is added without addToBackStack would be showing.
        // So we need to remove it manually
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment currentFrag = mFragmentManager.findFragmentById(mPlaceholder);
        if (currentFrag != null) {
            transaction.remove(currentFrag);
            transaction.commit();
        }

        return true;
    }

    @Override
    public void finishActivity() {
        if (mActivity == null) {
            return;
        }
        mActivity.finish();
    }

    @Override
    public void showPage(Fragment fragment) {
        showPage(fragment, true, true);
    }

    @Override
    public void swapPage(Fragment fragment) {
        showPage(fragment, true, false);
    }

    @Override
    public void replaceAll(Fragment fragment) {
        replaceAll(fragment, true);
    }

    @Override
    public void replaceAll(Fragment fragment, boolean hasAnimation) {
        if (!canNavigate())
            return;

        // Clear all back stack.
        int backStackCount = mFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {

            // Get the back stack fragment id.
            int backStackId = mFragmentManager.getBackStackEntryAt(i).getId();

            mFragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        } /* end of for */
        mBackStack.clear();

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (hasAnimation)
            transaction.setCustomAnimations(R.anim.fragment_enter,
                    R.anim.fragment_exit, R.anim.fragment_pop_enter,
                    R.anim.fragment_pop_exit);
        transaction.replace(mPlaceholder, fragment);
        transaction.commit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void showPage(Fragment fragment, boolean hasAnimation, boolean isAddBackStack) {
        if (!canNavigate())
            return;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (hasAnimation)
            transaction.setCustomAnimations(R.anim.fragment_enter,
                    R.anim.fragment_exit, R.anim.fragment_pop_enter,
                    R.anim.fragment_pop_exit);
        transaction.replace(mPlaceholder, fragment);
        NavigationState navigationState = new NavigationState(mPlaceholder);
        if (isAddBackStack) {
            transaction.addToBackStack(navigationState.backStackName);
            mBackStack.push(navigationState);
        }
        transaction.commit();

    }

    /**
     * Terminate resource that keep by this class. Must call in
     * {@link Activity#onDestroy()}
     */
    public void terminate() {
        mFragmentManager = null;
        mActivity = null;
    }

    public int getCurrentPlaceholder() {
        return mPlaceholder;
    }

    public int getCurrentPlaceholderInBackstack() {
        return !mBackStack.isEmpty() ? ((NavigationState) mBackStack.peek()).placeholder
                : 0;
    }

    @Override
    public boolean isBackStackEmpty() {
        return mBackStack.isEmpty() || mFragmentManager.getBackStackEntryCount() == 0;
    }

    public boolean isPlaceHolderEmpty(int placeHolder) {
        if (!mBackStack.isEmpty()) {
            for (Object object : mBackStack) {
                if (object instanceof NavigationState) {
                    if (((NavigationState) object).placeholder == placeHolder) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getBackStackSize() {
        return mBackStack.size();
    }

    @Override
    public Fragment getActivePage() {
        return mFragmentManager.findFragmentById(mPlaceholder);
    }

    /**
     * Use for save state of Navigation Manager, called in
     * {@link Activity#onSaveInstanceState(Bundle)}
     *
     * @param bundle
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void serialize(Bundle bundle) {
        if (!mBackStack.isEmpty())
            bundle.putParcelableArrayList("nm_state", new ArrayList(mBackStack));
    }

    /**
     * Use for restore state that saved in {@link #serialize(Bundle)}, called in
     * {@link Activity#onCreate(Bundle)}
     *
     * @param bundle
     */
    @SuppressWarnings("unchecked")
    public void deserialize(Bundle bundle) {
        if (bundle == null) return;
        @SuppressWarnings("rawtypes")
        ArrayList arraylist = bundle.getParcelableArrayList("nm_state");
        if (arraylist != null && arraylist.size() != 0) {
            for (Object navigationState : arraylist) {
                mBackStack.push(navigationState);
            }
        }
    }
}
