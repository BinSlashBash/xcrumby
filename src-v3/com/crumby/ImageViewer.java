package com.crumby;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.MenuItem;
import com.crumby.lib.events.PagerEvent;
import com.crumby.lib.events.TerminateEvent;
import com.crumby.lib.fragment.adapter.SwipePageAdapter;
import com.crumby.lib.widget.thirdparty.ZoomOutPageTransformer;
import com.squareup.otto.Subscribe;

public class ImageViewer extends Activity {
    private SwipePageAdapter adapter;
    private ViewPager pager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(9);
        setContentView(C0065R.layout.image_viewer);
        this.pager = (ViewPager) findViewById(C0065R.id.view_pager);
        this.pager.setPageTransformer(false, new ZoomOutPageTransformer());
        CrDb.m0d("image viewer", "created!");
        BusProvider.BUS.get().register(this);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(C0065R.color.TransparentBlack));
        bar.setDisplayShowHomeEnabled(false);
        bar.setIcon(null);
        onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe
    public void onTerminateEvent(TerminateEvent terminateEvent) {
        finish();
    }

    protected void onDestroy() {
        super.onDestroy();
        CrDb.m0d("image viewer", "destroyed!");
        BusProvider.BUS.get().unregister(this);
    }

    @Subscribe
    public void onPagerEvent(PagerEvent pagerEvent) {
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, GalleryViewer.class);
        intent.setFlags(AccessibilityNodeInfoCompat.ACTION_SET_SELECTION);
        startActivity(intent);
    }
}
