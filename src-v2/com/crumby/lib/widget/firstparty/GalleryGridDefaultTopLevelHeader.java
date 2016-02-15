/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.crumby.BusProvider;
import com.crumby.lib.events.OmniformEvent;
import com.crumby.lib.widget.thirdparty.HorizontalFlowLayout;
import com.squareup.otto.Bus;

public class GalleryGridDefaultTopLevelHeader
extends HorizontalFlowLayout {
    private View openLogin;
    private View openSearch;

    public GalleryGridDefaultTopLevelHeader(Context context) {
        super(context);
    }

    public GalleryGridDefaultTopLevelHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GalleryGridDefaultTopLevelHeader(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.openLogin = this.findViewById(2131492934);
        this.openLogin.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                BusProvider.BUS.get().post(new OmniformEvent("Login"));
            }
        });
        this.openSearch = this.findViewById(2131492933);
        this.openSearch.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                BusProvider.BUS.get().post(new OmniformEvent("Search"));
            }
        });
    }

}

