package co.core.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by freesky1102 on 5/27/16.
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

    void showPage(Fragment fragment, boolean hasAnimation, boolean isAddBackStack);
}
