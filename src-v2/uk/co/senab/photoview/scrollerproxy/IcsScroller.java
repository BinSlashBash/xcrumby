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
import uk.co.senab.photoview.scrollerproxy.GingerScroller;

@TargetApi(value=14)
public class IcsScroller
extends GingerScroller {
    public IcsScroller(Context context) {
        super(context);
    }

    @Override
    public boolean computeScrollOffset() {
        return this.mScroller.computeScrollOffset();
    }
}

