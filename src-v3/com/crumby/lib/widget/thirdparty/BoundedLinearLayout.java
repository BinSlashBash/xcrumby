package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import com.crumby.C0065R;

public class BoundedLinearLayout extends LinearLayout {
    private final int mBoundedHeight;
    private final int mBoundedWidth;

    public BoundedLinearLayout(Context context) {
        super(context);
        this.mBoundedWidth = 0;
        this.mBoundedHeight = 0;
    }

    public BoundedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, C0065R.styleable.BoundedView);
        this.mBoundedWidth = a.getDimensionPixelSize(0, 0);
        this.mBoundedHeight = a.getDimensionPixelSize(1, 0);
        a.recycle();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (this.mBoundedWidth > 0 && this.mBoundedWidth < measuredWidth) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(this.mBoundedWidth, MeasureSpec.getMode(widthMeasureSpec));
        }
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (this.mBoundedHeight > 0 && this.mBoundedHeight < measuredHeight) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(this.mBoundedHeight, MeasureSpec.getMode(heightMeasureSpec));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
