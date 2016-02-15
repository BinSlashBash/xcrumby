/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.Window
 */
package com.crumby;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.events.PagerEvent;
import com.crumby.lib.events.TerminateEvent;
import com.crumby.lib.fragment.adapter.SwipePageAdapter;
import com.crumby.lib.widget.thirdparty.ZoomOutPageTransformer;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ImageViewer
extends Activity {
    private SwipePageAdapter adapter;
    private ViewPager pager;

    public void onBackPressed() {
        Intent intent = new Intent((Context)this, (Class)GalleryViewer.class);
        intent.setFlags(131072);
        this.startActivity(intent);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.getWindow().requestFeature(9);
        this.setContentView(2130903091);
        this.pager = (ViewPager)this.findViewById(2131492993);
        this.pager.setPageTransformer(false, new ZoomOutPageTransformer());
        CrDb.d("image viewer", "created!");
        BusProvider.BUS.get().register((Object)this);
        bundle = this.getActionBar();
        bundle.setBackgroundDrawable(this.getResources().getDrawable(2131427504));
        bundle.setDisplayShowHomeEnabled(false);
        bundle.setIcon(null);
        this.onBackPressed();
    }

    protected void onDestroy() {
        super.onDestroy();
        CrDb.d("image viewer", "destroyed!");
        BusProvider.BUS.get().unregister((Object)this);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 16908332: 
        }
        this.onBackPressed();
        return true;
    }

    @Subscribe
    public void onPagerEvent(PagerEvent pagerEvent) {
    }

    @Subscribe
    public void onTerminateEvent(TerminateEvent terminateEvent) {
        this.finish();
    }
}

