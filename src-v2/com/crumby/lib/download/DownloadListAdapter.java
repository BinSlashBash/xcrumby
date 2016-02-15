/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.text.Html
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewPropertyAnimator
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.BaseAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 */
package com.crumby.lib.download;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.lib.download.ImageDownload;
import com.crumby.lib.download.ImageDownloadListener;
import com.crumby.lib.download.ScrollStateKeeper;
import com.crumby.lib.widget.firstparty.main_menu.DownloadMenuItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class DownloadListAdapter
extends BaseAdapter
implements ImageDownloadListener,
AbsListView.OnScrollListener,
ScrollStateKeeper {
    int currentScrollState;
    ArrayList<ImageDownload> downloads;
    int greatestViewPosition;
    private final TextView header;
    LayoutInflater inflater;
    private final ListView list;
    private final Runnable refresh;
    private final Handler refreshHandler;

    public DownloadListAdapter(ListView listView, LayoutInflater layoutInflater, TextView textView) {
        this.list = listView;
        this.inflater = layoutInflater;
        this.downloads = new ArrayList();
        this.refreshHandler = new Handler();
        this.refresh = new Runnable(){

            @Override
            public void run() {
                DownloadListAdapter.this.currentScrollState = 0;
                DownloadListAdapter.this.notifyDataSetChanged();
            }
        };
        this.header = textView;
        textView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "hide header", null);
                view.setVisibility(8);
            }
        });
        listView.setOnScrollListener((AbsListView.OnScrollListener)this);
    }

    @Override
    public boolean canShowListItems() {
        if (this.currentScrollState != 2) {
            return true;
        }
        return false;
    }

    public int getCount() {
        return this.downloads.size();
    }

    public Object getItem(int n2) {
        return this.downloads.get(n2);
    }

    public long getItemId(int n2) {
        return 0;
    }

    @Override
    public int getScrollState() {
        return this.currentScrollState;
    }

    public View getView(int n2, View object, ViewGroup viewGroup) {
        viewGroup = object;
        if (object == null) {
            CrDb.d("download list adapter", "creating new view for position " + n2);
            viewGroup = this.inflater.inflate(2130903100, null);
            ((DownloadMenuItem)viewGroup).setKeeper(this);
        }
        CrDb.d("download list adapter", "getting view for position " + n2);
        object = (DownloadMenuItem)viewGroup;
        object.update(this.downloads.get(n2), n2);
        return object;
    }

    public void onScroll(AbsListView absListView, int n2, int n3, int n4) {
    }

    public void onScrollStateChanged(AbsListView absListView, int n2) {
        this.refreshHandler.removeCallbacks(this.refresh);
        if (this.currentScrollState == 2 && n2 != 2) {
            int n3 = this.list.getLastVisiblePosition();
            if (this.greatestViewPosition < n3) {
                this.greatestViewPosition = n3;
                Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "scroll stop", "" + n3 + "");
            }
            for (n3 = 0; n3 < this.list.getChildCount(); ++n3) {
                ((DownloadMenuItem)this.list.getChildAt(n3)).invalidateDownloadChangeFlag();
            }
            this.notifyDataSetChanged();
        }
        this.currentScrollState = n2;
    }

    public void pause() {
        this.currentScrollState = 2;
    }

    public void refresh() {
        this.refreshHandler.removeCallbacks(this.refresh);
        this.refreshHandler.postDelayed(this.refresh, 100);
    }

    @Override
    public void terminate() {
        this.downloads.clear();
        this.header.setVisibility(8);
        this.notifyDataSetChanged();
    }

    public void unpause() {
        this.currentScrollState = 0;
        this.refresh();
    }

    @Override
    public void update(ImageDownload imageDownload) {
        CrDb.d("download list adapter", "got refresh request for " + imageDownload.getDownloadUri());
        if (this.currentScrollState == 2) {
            CrDb.d("download list adapter", "list view not ready for " + imageDownload.getDownloadUri());
            return;
        }
        int n2 = this.list.getLastVisiblePosition();
        for (int i2 = this.list.getFirstVisiblePosition(); i2 <= n2; ++i2) {
            if (imageDownload != this.getItem(i2)) continue;
            CrDb.d("download list adapter", "refreshing view at position" + i2 + " for " + imageDownload.getDownloadUri());
            this.refresh();
            return;
        }
        CrDb.d("download list adapter", "list view is not showing download at the moment " + imageDownload.getDownloadUri());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void update(ArrayList<ImageDownload> object) {
        Object object2;
        CrDb.d("download list adapter", "adding new downloads of size: " + object.size());
        object.size();
        object.removeAll(this.downloads);
        int n2 = 0;
        Collections.reverse(object);
        object = object.iterator();
        while (object.hasNext()) {
            object2 = (ImageDownload)object.next();
            if (object2.isValid()) {
                this.downloads.add(0, (ImageDownload)object2);
                continue;
            }
            ++n2;
        }
        if (n2 != 0) {
            if (this.header.getVisibility() != 0) {
                this.header.setVisibility(0);
                this.header.setScaleY(0.5f);
                this.header.setScaleX(0.5f);
                this.header.setAlpha(0.0f);
                this.header.animate().alpha(1.0f).scaleY(1.0f).scaleX(1.0f).setStartDelay(400);
            }
            object = object2 = "<b>" + (n2 + 0) + " images were ignored.</b><br/>";
            if (n2 > 0) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "invalid", "" + n2 + "");
                object = (String)object2 + "For this website, you can only download images from their page, not in the gallery.<br/>";
            }
            object2 = object;
            if (0 > 0) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "already saved", "" + 0 + "");
                object2 = (String)object + 0 + " images are already in your recent downloads.</i><br/>";
            }
            this.header.setText((CharSequence)Html.fromHtml((String)object2));
        } else {
            this.header.setVisibility(8);
        }
        this.notifyDataSetChanged();
    }

}

