/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.crumby.impl.derpibooru;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.GalleryViewer;
import com.crumby.lib.authentication.UserData;
import com.crumby.lib.events.UrlChangeEvent;
import com.squareup.otto.Bus;

public class DerpibooruAccountLayout
extends LinearLayout
implements UserData {
    public DerpibooruAccountLayout(Context context) {
        super(context);
    }

    public DerpibooruAccountLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DerpibooruAccountLayout(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    @Override
    public void fillWith(Object object) {
        ((TextView)this.findViewById(2131492935)).setText((CharSequence)("Logged in as " + (String)object));
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.findViewById(2131492936).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", "https://derpibooru.org/images/favourites");
                BusProvider.BUS.get().post(new UrlChangeEvent("https://derpibooru.org/images/favourites", true));
            }
        });
        this.findViewById(2131492937).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", "https://derpibooru.org/images/watched");
                BusProvider.BUS.get().post(new UrlChangeEvent("https://derpibooru.org/images/watched", true));
            }
        });
        this.findViewById(2131492938).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", "https://derpibooru.org/images/upvoted");
                BusProvider.BUS.get().post(new UrlChangeEvent("https://derpibooru.org/images/upvoted", true));
            }
        });
        this.findViewById(2131492939).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", "https://derpibooru.org/images/uploaded");
                BusProvider.BUS.get().post(new UrlChangeEvent("https://derpibooru.org/images/uploaded", true));
            }
        });
        this.findViewById(2131492940).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "relogin", "derpibooru");
                ((GalleryViewer)DerpibooruAccountLayout.this.getContext()).autoLogin(true);
            }
        });
    }

}

