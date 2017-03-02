package com.hiepkhach9x.publiceyes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by hungh on 3/2/2017.
 */

public class UnderLineEditText extends EditText {

    private boolean showUnderLine;
    private int underLineColor;

    public UnderLineEditText(Context context) {
        super(context);
    }

    public UnderLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public UnderLineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.UnderLine,
                0, 0);

        try {
            showUnderLine = a.getBoolean(R.styleable.UnderLine_showUnderLine, false);
            underLineColor = a.getColor(R.styleable.UnderLine_underLineColor, ContextCompat.getColor(context, R.color.yellowColor));
            if (showUnderLine) {
                getBackground().setColorFilter(underLineColor, PorterDuff.Mode.SRC_IN);
            } else {
                getBackground().clearColorFilter();
            }
        } finally {
            a.recycle();
        }
    }

    public void removeUnderLine() {
        getBackground().clearColorFilter();
    }

    public void setShowUnderLine(boolean showUnderLine) {
        this.showUnderLine = showUnderLine;
        if (showUnderLine) {
            getBackground().setColorFilter(this.underLineColor, PorterDuff.Mode.SRC_IN);
        } else {
            getBackground().clearColorFilter();
        }
    }

    public boolean isShowUnderLine() {
        return showUnderLine;
    }

    public int getUnderLineColor() {
        return underLineColor;
    }

    public void setUnderLineColor(int underLineColor) {
        this.underLineColor = underLineColor;

        if (showUnderLine) {
            getBackground().setColorFilter(this.underLineColor, PorterDuff.Mode.SRC_IN);
        }
    }
}
