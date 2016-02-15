package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;
import java.util.LinkedList;
import java.util.Queue;

public class HorizontalFlowLayout extends RelativeLayout {
    private Queue<View> lastMeasured;

    public HorizontalFlowLayout(Context context) {
        super(context);
        this.lastMeasured = new LinkedList();
    }

    public HorizontalFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.lastMeasured = new LinkedList();
    }

    public HorizontalFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.lastMeasured = new LinkedList();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();
        int line_height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                int childMarginLeft;
                int childMarginRight;
                int childMarginTop;
                int childMarginBottom;
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                if (child.getLayoutParams() == null || !(child.getLayoutParams() instanceof MarginLayoutParams)) {
                    childMarginLeft = 0;
                    childMarginRight = 0;
                    childMarginTop = 0;
                    childMarginBottom = 0;
                } else {
                    MarginLayoutParams childMarginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
                    childMarginLeft = childMarginLayoutParams.leftMargin;
                    childMarginRight = childMarginLayoutParams.rightMargin;
                    childMarginTop = childMarginLayoutParams.topMargin;
                    childMarginBottom = childMarginLayoutParams.bottomMargin;
                }
                if ((((xpos + childMarginLeft) + childWidth) + childMarginRight) + getPaddingRight() > width) {
                    xpos = getPaddingLeft();
                    ypos += line_height;
                    line_height = (childMarginTop + childHeight) + childMarginBottom;
                } else {
                    line_height = Math.max(line_height, (childMarginTop + childHeight) + childMarginBottom);
                }
                xpos += (childMarginLeft + childWidth) + childMarginRight;
            }
        }
        ypos += getPaddingBottom() + line_height;
        if (MeasureSpec.getMode(heightMeasureSpec) == 0) {
            height = ypos;
        } else if (MeasureSpec.getMode(heightMeasureSpec) == Integer.MIN_VALUE && ypos < height) {
            height = ypos;
        }
        setMeasuredDimension(width, height);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();
        int line_height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                int childMarginLeft;
                int childMarginRight;
                int childMarginTop;
                int childMarginBottom;
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                if (child.getLayoutParams() == null || !(child.getLayoutParams() instanceof MarginLayoutParams)) {
                    childMarginLeft = 0;
                    childMarginRight = 0;
                    childMarginTop = 0;
                    childMarginBottom = 0;
                } else {
                    MarginLayoutParams childMarginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
                    childMarginLeft = childMarginLayoutParams.leftMargin;
                    childMarginRight = childMarginLayoutParams.rightMargin;
                    childMarginTop = childMarginLayoutParams.topMargin;
                    childMarginBottom = childMarginLayoutParams.bottomMargin;
                }
                if ((((xpos + childMarginLeft) + childWidth) + childMarginRight) + getPaddingRight() > r - l) {
                    xpos = getPaddingLeft();
                    ypos += line_height;
                    line_height = (childHeight + childMarginTop) + childMarginBottom;
                } else {
                    line_height = Math.max(line_height, (childMarginTop + childHeight) + childMarginBottom);
                }
                child.layout(xpos + childMarginLeft, ypos + childMarginTop, (xpos + childMarginLeft) + childWidth, (ypos + childMarginTop) + childHeight);
                xpos += (childMarginLeft + childWidth) + childMarginRight;
            }
        }
    }
}
