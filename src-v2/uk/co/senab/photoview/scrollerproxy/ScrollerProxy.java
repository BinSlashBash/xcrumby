/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package uk.co.senab.photoview.scrollerproxy;

import android.content.Context;
import android.os.Build;
import uk.co.senab.photoview.scrollerproxy.GingerScroller;
import uk.co.senab.photoview.scrollerproxy.IcsScroller;
import uk.co.senab.photoview.scrollerproxy.PreGingerScroller;

public abstract class ScrollerProxy {
    public static ScrollerProxy getScroller(Context context) {
        if (Build.VERSION.SDK_INT < 9) {
            return new PreGingerScroller(context);
        }
        if (Build.VERSION.SDK_INT < 14) {
            return new GingerScroller(context);
        }
        return new IcsScroller(context);
    }

    public abstract boolean computeScrollOffset();

    public abstract void fling(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10);

    public abstract void forceFinished(boolean var1);

    public abstract int getCurrX();

    public abstract int getCurrY();

    public abstract boolean isFinished();
}

