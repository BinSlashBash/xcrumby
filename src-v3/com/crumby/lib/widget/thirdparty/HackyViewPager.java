package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.crumby.CrumbyApp;
import com.crumby.ViewIdGenerator;
import com.crumby.lib.fragment.adapter.SwipePageAdapter;

public class HackyViewPager extends ViewPager {
    public HackyViewPager(Context context) {
        super(context);
        setPageMargin((int) CrumbyApp.convertDpToPx(getResources(), 20.0f));
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageMargin((int) CrumbyApp.convertDpToPx(getResources(), 20.0f));
    }

    private boolean currentFragmentAllowsPaging(MotionEvent ev) {
        try {
            return ((SwipePageAdapter) getAdapter()).getFragment(getCurrentItem()).willAllowPaging(ev);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void initialize(SwipePageAdapter adapter, OnPageChangeListener pageChange, int currentImageFocus) {
        setId(ViewIdGenerator.INSTANCE.generateViewId());
        setAdapter(adapter);
        setOnPageChangeListener(pageChange);
        setCurrentItem(currentImageFocus);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w - getPageMargin(), h, oldw - getPageMargin(), oldh);
    }
}
