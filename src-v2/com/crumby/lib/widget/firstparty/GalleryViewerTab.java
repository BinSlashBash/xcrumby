/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GalleryViewerTab
extends LinearLayout {
    int taskId;
    String url;

    public GalleryViewerTab(Context context, int n2, String string2) {
        super(context);
        this.setMinimumHeight(150);
        this.setMinimumWidth(100);
        context = new TextView(context);
        context.setText((CharSequence)string2);
        this.addView((View)context);
        this.setClickable(true);
        this.taskId = n2;
    }

    public int getTaskId() {
        return this.taskId;
    }
}

