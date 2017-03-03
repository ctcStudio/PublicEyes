package com.hiepkhach9x.base.dialog;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.hiepkhach9x.publiceyes.R;

import co.core.dialog.NDialogFragment;
import co.core.toolbox.NotifyUtil;

/**
 * Created by HungHN on 7/1/16.
 */
public class AppDialogFragment extends NDialogFragment {

    private static final String EXTRA_BUILDER = "extra_builder";

    public static final int ACTION_CLICK_NEGATIVE = 1001;
    public static final int ACTION_CLICK_POSITIVE = 1002;

    public static class Builder implements Parcelable {
        private int requestCode;
        private String positiveText;
        private String negativeText;
        private String message;
        private String title;
        private boolean isCancelable;

        public Builder() {
        }

        public Builder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        /**
         * The positive button is used in case you want to create a dialog with one button.
         *
         * @param positiveText text of positive button
         */
        public Builder setPositiveText(String positiveText) {
            this.positiveText = positiveText;
            return this;
        }

        public Builder setNegativeText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public AppDialogFragment build() {
            return AppDialogFragment.newInstance(this);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.requestCode);
            dest.writeString(this.positiveText);
            dest.writeString(this.negativeText);
            dest.writeString(this.message);
            dest.writeString(this.title);
            dest.writeByte(this.isCancelable ? (byte) 1 : (byte) 0);
        }

        protected Builder(Parcel in) {
            this.requestCode = in.readInt();
            this.positiveText = in.readString();
            this.negativeText = in.readString();
            this.message = in.readString();
            this.title = in.readString();
            this.isCancelable = in.readByte() != 0;
        }

        public static final Creator<Builder> CREATOR = new Creator<Builder>() {
            @Override
            public Builder createFromParcel(Parcel source) {
                return new Builder(source);
            }

            @Override
            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };
    }

    private static AppDialogFragment newInstance(Builder builder) {
        AppDialogFragment fragmentV4 = new AppDialogFragment();
        Bundle bundle = makeBundle(builder.requestCode);
        bundle.putParcelable(EXTRA_BUILDER, builder);
        fragmentV4.setArguments(bundle);
        return fragmentV4;
    }

    private Builder mBuilder;

    private TextView mMessageView, mNegativeView, mPositiveView, mTitleView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            getDataFrom(savedInstanceState);
        else
            getDataFrom(getArguments());
    }

    protected void getDataFrom(@NonNull Bundle bundle) {
        super.getDataFrom(bundle);
        mBuilder = bundle.getParcelable(EXTRA_BUILDER);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Window window = getDialog().getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }

        int layoutRes;

        if (TextUtils.isEmpty(mBuilder.positiveText) || TextUtils.isEmpty(mBuilder.negativeText))
            layoutRes = R.layout.dialog_ok;
        else
            layoutRes = R.layout.dialog_ok_cancel;

        return inflater.inflate(layoutRes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessageView = (TextView) view.findViewById(R.id.content);
        mNegativeView = (TextView) view.findViewById(R.id.btn_cancel);
        mPositiveView = (TextView) view.findViewById(R.id.btn_ok);
        mTitleView = (TextView) view.findViewById(R.id.title);

        displayText(mMessageView, mBuilder.message);
        displayText(mNegativeView, mBuilder.negativeText);
        displayText(mPositiveView, mBuilder.positiveText);
        displayText(mTitleView, mBuilder.title);

        settingOnClick(mNegativeView);
        settingOnClick(mPositiveView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCancelable(mBuilder.isCancelable);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_cancel:
                    NotifyUtil.notifyAction(true, AppDialogFragment.this, null, mRequestCode, ACTION_CLICK_NEGATIVE);
                    break;

                case R.id.btn_ok:
                    NotifyUtil.notifyAction(true, AppDialogFragment.this, null, mRequestCode, ACTION_CLICK_POSITIVE);
                    break;
            }
        }
    };

    private void displayText(TextView view, String text) {
        if (view == null || TextUtils.isEmpty(text)) return;
        view.setText(text);
    }

    private void settingOnClick(View view) {
        if (view == null) return;
        view.setOnClickListener(mOnClickListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_BUILDER, mBuilder);
    }
}
