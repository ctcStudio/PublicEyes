package co.core.fragments;

import android.content.Intent;

/**
 * Created by HungHN on 10/26/16.
 */
public interface OnFragmentResultListener {

    void onFragmentResult(int requestCode, int action, Intent extraData);

}
