/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.widget.OverScroller
 */
package uk.co.senab.photoview.scrollerproxy;

import android.annotation.TargetApi;
import android.content.Context;
import android.widget.OverScroller;
import uk.co.senab.photoview.scrollerproxy.ScrollerProxy;

@TargetApi(value=9)
public class GingerScroller
extends ScrollerProxy {
    private boolean mFirstScroll = false;
    protected final OverScroller mScroller;

    public GingerScroller(Context context) {
        this.mScroller = new OverScroller(context);
    }

    @Override
    public boolean computeScrollOffset() {
        if (this.mFirstScroll) {
            this.mScroller.computeScrollOffset();
            this.mFirstScroll = false;
        }
        return this.mScroller.computeScrollOffset();
    }

    @Override
    public void fling(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11) {
        this.mScroller.fling(n2, n3, n4, n5, n6, n7, n8, n9, n10, n11);
    }

    @Override
    public void forceFinished(boolean bl2) {
        this.mScroller.forceFinished(bl2);
    }

    @Override
    public int getCurrX() {
        return this.mScroller.getCurrX();
    }

    @Override
    public int getCurrY() {
        return this.mScroller.getCurrY();
    }

    @Override
    public boolean isFinished() {
        return this.mScroller.isFinished();
    }
}

