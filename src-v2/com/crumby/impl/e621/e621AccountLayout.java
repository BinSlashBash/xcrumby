/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.crumby.impl.e621;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.lib.authentication.UserData;
import com.crumby.lib.events.UrlChangeEvent;
import com.squareup.otto.Bus;

public class e621AccountLayout
extends LinearLayout
implements UserData {
    String user;

    public e621AccountLayout(Context context) {
        super(context);
    }

    public e621AccountLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public e621AccountLayout(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    @Override
    public void fillWith(Object object) {
        this.user = (String)object;
        ((TextView)this.findViewById(2131492957)).setText((CharSequence)("Logged in as: " + this.user));
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.findViewById(2131492958).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newNavigationEvent("account", "e621 favorites");
                BusProvider.BUS.get().post(new UrlChangeEvent("https://e621.net/post?tags=" + Uri.encode((String)new StringBuilder().append("fav:").append(e621AccountLayout.this.user).toString())));
            }
        });
    }

}

