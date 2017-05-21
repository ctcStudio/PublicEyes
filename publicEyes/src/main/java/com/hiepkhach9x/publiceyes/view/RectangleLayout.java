package com.hiepkhach9x.publiceyes.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by hungh on 3/4/2017.
 */

public class RectangleLayout extends RelativeLayout {

    public RectangleLayout(Context context) {
        super(context);
    }

    public RectangleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectangleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
