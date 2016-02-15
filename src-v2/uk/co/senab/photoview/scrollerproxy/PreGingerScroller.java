/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.Scroller
 */
package uk.co.senab.photoview.scrollerproxy;

import android.content.Context;
import android.widget.Scroller;
import uk.co.senab.photoview.scrollerproxy.ScrollerProxy;

public class PreGingerScroller
extends ScrollerProxy {
    private final Scroller mScroller;

    public PreGingerScroller(Context context) {
        this.mScroller = new Scroller(context);
    }

    @Override
    public boolean computeScrollOffset() {
        return this.mScroller.computeScrollOffset();
    }

    @Override
    public void fling(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11) {
        this.mScroller.fling(n2, n3, n4, n5, n6, n7, n8, n9);
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

