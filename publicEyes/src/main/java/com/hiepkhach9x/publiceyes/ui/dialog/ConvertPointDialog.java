package com.hiepkhach9x.publiceyes.ui.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.hiepkhach9x.publiceyes.Config;
import com.hiepkhach9x.publiceyes.R;

import co.core.dialog.NDialogFragment;
import co.core.toolbox.NotifyUtil;

/**
 * Created by hungh on 3/5/2017.
 */

public class ConvertPointDialog extends NDialogFragment implements View.OnClickListener {

    public static final int ACTION_CLICK_CONVERT_POINT = 1001;
    public static final String EXTRA_PHONE = "extra.phone";

    public static ConvertPointDialog newInstance(int requestCode) {

        Bundle args = makeBundle(requestCode);

        ConvertPointDialog fragment = new ConvertPointDialog();
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
        return inflater.inflate(R.layout.dialog_confirm_point, container, false);
    }

    private EditText phone;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_convert).setOnClickListener(this);
        TextView content = (TextView) view.findViewById(R.id.text_content);
        phone = (EditText) view.findViewById(R.id.user_phone);
        String confirmStart = getString(R.string.convert_point_to_coin_start);
        String confirmLink = getString(R.string.convert_point_to_coin_link);
        String confirmEnd = getString(R.string.convert_point_to_coin_end);
        String confirmMobile = getString(R.string.convert_point_to_coin);

        String str = confirmStart + confirmLink + confirmEnd + confirmMobile;
        SpannableString spannableString = new SpannableString(str);
        int size = confirmLink.length();
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.GCOIN_LINK));
                startActivity(browserIntent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ContextCompat.getColor(getContext(),R.color.redColor));
                ds.setUnderlineText(false);
            }
        };
        spannableString.setSpan(clickableSpan, confirmStart.length(), confirmStart.length() + size,
                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), confirmStart.length(), confirmStart.length() + size, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        content.setText(spannableString);
        content.setMovementMethod(LinkMovementMethod.getInstance());
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
            case R.id.btn_convert:
                Intent intent = new Intent();
                String format = "+84%s";
                intent.putExtra(EXTRA_PHONE, String.format(format,phone.getText().toString().trim()));
                NotifyUtil.notifyAction(true, this, intent, mRequestCode, ACTION_CLICK_CONVERT_POINT);
                break;
        }
    }
}
