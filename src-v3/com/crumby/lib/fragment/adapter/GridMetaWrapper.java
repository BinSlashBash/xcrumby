package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class GridMetaWrapper extends RelativeLayout {
    public GridMetaWrapper(Context context) {
        super(context);
    }

    public GridMetaWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridMetaWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        int i = getChildCount() - 1;
        while (i >= 0) {
            int i2 = i - 1;
            getChildAt(i).setAlpha(alpha);
            i = i2;
        }
    }
}
