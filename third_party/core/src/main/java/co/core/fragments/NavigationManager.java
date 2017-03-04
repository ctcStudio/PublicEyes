package co.core.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by HungHN on 5/27/16.
 */
public interface NavigationManager {
    /**
     * Pop fragment in stack if stack isn't empty.
     *
     * @return true if success, false otherwise. (maybe: stack is empty,
     * activity is in onSaveInstance())
     */
    boolean goBack();

    Fragment getActivePage();

    void finishActivity();

    void showPage(Fragment fragment);

    void swapPage(Fragment fragment);

    void replaceAll(Fragment fragment);

    void replaceAll(Fragment fragment, boolean hasAnimation);

    void showPage(Fragment fragment, boolean hasAnimation, boolean isAddBackStack);
}
