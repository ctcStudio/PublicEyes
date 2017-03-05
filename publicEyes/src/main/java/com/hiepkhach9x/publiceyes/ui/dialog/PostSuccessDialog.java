package com.hiepkhach9x.publiceyes.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hiepkhach9x.publiceyes.R;

import co.core.dialog.NDialogFragment;
import co.core.toolbox.NotifyUtil;

/**
 * Created by hungh on 3/5/2017.
 */

public class PostSuccessDialog extends NDialogFragment implements View.OnClickListener {

    public static final int ACTION_CLICK_POST_OTHER = 1001;
    public static final int ACTION_CLICK_VIEW_IN = 1002;

    public static PostSuccessDialog newInstance(int requestCode) {

        Bundle args = makeBundle(requestCode);

        PostSuccessDialog fragment = new PostSuccessDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = getDialog().getWindow();
            if (window != null) {
                window.requestFeature(Window.FEATURE_NO_TITLE);
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
        return inflater.inflate(R.layout.dialog_post_success, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_post).setOnClickListener(this);
        view.findViewById(R.id.btn_view_on_ichangemycity).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();

        if (window == null) return;
        window.setBackgroundDrawableResource(android.R.color.transparent);

        //set window size programmatically
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        ViewGroup.LayoutParams params = window.getAttributes();

        int marginRL = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        params.width = displayMetrics.widthPixels - 2 * marginRL;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setLayout(params.width, params.height);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_post:
                NotifyUtil.notifyAction(true, this, null, mRequestCode, ACTION_CLICK_POST_OTHER);
                break;
            case R.id.btn_view_on_ichangemycity:
                this.dismiss();
                NotifyUtil.notifyAction(true, this, null, mRequestCode, ACTION_CLICK_VIEW_IN);
                break;
        }
    }
}
