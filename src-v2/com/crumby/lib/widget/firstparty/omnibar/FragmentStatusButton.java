/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageButton
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.lib.events.ReloadFragmentEvent;
import com.crumby.lib.events.StopLoadingFragmentEvent;
import com.squareup.otto.Bus;

public class FragmentStatusButton
extends ImageButton
implements View.OnClickListener {
    private boolean hardRefresh;
    private final Drawable refreshDrawable;
    private boolean refreshMode;
    private final Drawable stopDrawable;

    public FragmentStatusButton(Context context) {
        super(context);
        this.refreshDrawable = this.getResources().getDrawable(2130837635);
        this.stopDrawable = this.getResources().getDrawable(2130837610);
        this.setOnClickListener((View.OnClickListener)this);
    }

    public FragmentStatusButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.refreshDrawable = this.getResources().getDrawable(2130837635);
        this.stopDrawable = this.getResources().getDrawable(2130837610);
        this.setOnClickListener((View.OnClickListener)this);
    }

    public FragmentStatusButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.refreshDrawable = this.getResources().getDrawable(2130837635);
        this.stopDrawable = this.getResources().getDrawable(2130837610);
        this.setOnClickListener((View.OnClickListener)this);
    }

    public void onClick(View view) {
        if (this.refreshMode) {
            BusProvider.BUS.get().post(new ReloadFragmentEvent(false));
            Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "refresh", null);
            return;
        }
        BusProvider.BUS.get().post(new StopLoadingFragmentEvent());
        Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "stop", null);
        this.refreshMode();
    }

    public void refreshMode() {
        this.setImageDrawable(this.refreshDrawable);
        this.refreshMode = true;
    }

    public void stopLoadingMode() {
        this.setImageDrawable(this.stopDrawable);
        this.refreshMode = false;
    }
}

