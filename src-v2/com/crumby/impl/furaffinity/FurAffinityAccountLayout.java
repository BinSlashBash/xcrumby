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
package com.crumby.impl.furaffinity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.lib.authentication.UserData;
import com.uservoice.uservoicesdk.UserVoice;

public class FurAffinityAccountLayout
extends LinearLayout
implements UserData {
    public FurAffinityAccountLayout(Context context) {
        super(context);
    }

    public FurAffinityAccountLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FurAffinityAccountLayout(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    @Override
    public void fillWith(Object object) {
        ((TextView)this.findViewById(2131492979)).setText((CharSequence)("Logged in as: " + (String)object));
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.findViewById(2131492980).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newNavigationEvent("uservoice", "furaffinity click");
                UserVoice.launchForum(FurAffinityAccountLayout.this.getContext());
            }
        });
    }

}

