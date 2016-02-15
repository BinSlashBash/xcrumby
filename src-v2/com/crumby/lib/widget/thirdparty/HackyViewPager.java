/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 */
package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.crumby.CrumbyApp;
import com.crumby.ViewIdGenerator;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.adapter.SwipePageAdapter;

public class HackyViewPager
extends ViewPager {
    public HackyViewPager(Context context) {
        super(context);
        this.setPageMargin((int)CrumbyApp.convertDpToPx(this.getResources(), 20.0f));
    }

    public HackyViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setPageMargin((int)CrumbyApp.convertDpToPx(this.getResources(), 20.0f));
    }

    private boolean currentFragmentAllowsPaging(MotionEvent motionEvent) {
        try {
            boolean bl2 = ((SwipePageAdapter)this.getAdapter()).getFragment(this.getCurrentItem()).willAllowPaging(motionEvent);
            return bl2;
        }
        catch (NullPointerException var1_2) {
            return false;
        }
    }

    public void initialize(SwipePageAdapter swipePageAdapter, ViewPager.OnPageChangeListener onPageChangeListener, int n2) {
        this.setId(ViewIdGenerator.INSTANCE.generateViewId());
        this.setAdapter(swipePageAdapter);
        this.setOnPageChangeListener(onPageChangeListener);
        this.setCurrentItem(n2);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        try {
            boolean bl2 = super.onInterceptTouchEvent(motionEvent);
            return bl2;
        }
        catch (IllegalArgumentException var1_2) {
            var1_2.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2 - this.getPageMargin(), n3, n4 - this.getPageMargin(), n5);
    }
}

