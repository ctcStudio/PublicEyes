package com.hiepkhach9x.base.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hiepkhach9x.publiceyes.R;

import co.core.dialog.NDialogFragment;

/**
 * Created by HungHN on 10/18/16.
 */

public class LoadingDialogFragment extends NDialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();

        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Dialog dialog = getDialog();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }
}
