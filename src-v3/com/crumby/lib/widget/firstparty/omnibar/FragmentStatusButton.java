package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.lib.events.ReloadFragmentEvent;
import com.crumby.lib.events.StopLoadingFragmentEvent;

public class FragmentStatusButton extends ImageButton implements OnClickListener {
    private boolean hardRefresh;
    private final Drawable refreshDrawable;
    private boolean refreshMode;
    private final Drawable stopDrawable;

    public FragmentStatusButton(Context context) {
        super(context);
        this.refreshDrawable = getResources().getDrawable(C0065R.drawable.ic_action_refresh);
        this.stopDrawable = getResources().getDrawable(C0065R.drawable.ic_action_cancel);
        setOnClickListener(this);
    }

    public FragmentStatusButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.refreshDrawable = getResources().getDrawable(C0065R.drawable.ic_action_refresh);
        this.stopDrawable = getResources().getDrawable(C0065R.drawable.ic_action_cancel);
        setOnClickListener(this);
    }

    public FragmentStatusButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.refreshDrawable = getResources().getDrawable(C0065R.drawable.ic_action_refresh);
        this.stopDrawable = getResources().getDrawable(C0065R.drawable.ic_action_cancel);
        setOnClickListener(this);
    }

    public void refreshMode() {
        setImageDrawable(this.refreshDrawable);
        this.refreshMode = true;
    }

    public void stopLoadingMode() {
        setImageDrawable(this.stopDrawable);
        this.refreshMode = false;
    }

    public void onClick(View v) {
        if (this.refreshMode) {
            BusProvider.BUS.get().post(new ReloadFragmentEvent(false));
            Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "refresh", null);
            return;
        }
        BusProvider.BUS.get().post(new StopLoadingFragmentEvent());
        Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "stop", null);
        refreshMode();
    }
}
