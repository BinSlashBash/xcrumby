package com.crumby.lib.download;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.widget.firstparty.main_menu.DownloadMenuItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DownloadListAdapter extends BaseAdapter implements ImageDownloadListener, OnScrollListener, ScrollStateKeeper {
    int currentScrollState;
    ArrayList<ImageDownload> downloads;
    int greatestViewPosition;
    private final TextView header;
    LayoutInflater inflater;
    private final ListView list;
    private final Runnable refresh;
    private final Handler refreshHandler;

    /* renamed from: com.crumby.lib.download.DownloadListAdapter.1 */
    class C00931 implements Runnable {
        C00931() {
        }

        public void run() {
            DownloadListAdapter.this.currentScrollState = 0;
            DownloadListAdapter.this.notifyDataSetChanged();
        }
    }

    /* renamed from: com.crumby.lib.download.DownloadListAdapter.2 */
    class C00942 implements OnClickListener {
        C00942() {
        }

        public void onClick(View view) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "hide header", null);
            view.setVisibility(8);
        }
    }

    public DownloadListAdapter(ListView downloadList, LayoutInflater inflater, TextView header) {
        this.list = downloadList;
        this.inflater = inflater;
        this.downloads = new ArrayList();
        this.refreshHandler = new Handler();
        this.refresh = new C00931();
        this.header = header;
        header.setOnClickListener(new C00942());
        downloadList.setOnScrollListener(this);
    }

    public int getCount() {
        return this.downloads.size();
    }

    public Object getItem(int position) {
        return this.downloads.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            CrDb.m0d("download list adapter", "creating new view for position " + position);
            convertView = this.inflater.inflate(C0065R.layout.main_menu_download, null);
            ((DownloadMenuItem) convertView).setKeeper(this);
        }
        CrDb.m0d("download list adapter", "getting view for position " + position);
        DownloadMenuItem downloadMenuItem = (DownloadMenuItem) convertView;
        downloadMenuItem.update((ImageDownload) this.downloads.get(position), position);
        return downloadMenuItem;
    }

    public void update(ImageDownload download) {
        CrDb.m0d("download list adapter", "got refresh request for " + download.getDownloadUri());
        if (this.currentScrollState == 2) {
            CrDb.m0d("download list adapter", "list view not ready for " + download.getDownloadUri());
            return;
        }
        int j = this.list.getLastVisiblePosition();
        for (int i = this.list.getFirstVisiblePosition(); i <= j; i++) {
            if (download == getItem(i)) {
                CrDb.m0d("download list adapter", "refreshing view at position" + i + " for " + download.getDownloadUri());
                refresh();
                return;
            }
        }
        CrDb.m0d("download list adapter", "list view is not showing download at the moment " + download.getDownloadUri());
    }

    public void refresh() {
        this.refreshHandler.removeCallbacks(this.refresh);
        this.refreshHandler.postDelayed(this.refresh, 100);
    }

    public void update(ArrayList<ImageDownload> downloads) {
        CrDb.m0d("download list adapter", "adding new downloads of size: " + downloads.size());
        int numberOfNewRecentDownloads = downloads.size();
        downloads.removeAll(this.downloads);
        int invalid = 0;
        Collections.reverse(downloads);
        Iterator i$ = downloads.iterator();
        while (i$.hasNext()) {
            ImageDownload imageDownload = (ImageDownload) i$.next();
            if (imageDownload.isValid()) {
                this.downloads.add(0, imageDownload);
            } else {
                invalid++;
            }
        }
        if (invalid != 0) {
            if (this.header.getVisibility() != 0) {
                this.header.setVisibility(0);
                this.header.setScaleY(0.5f);
                this.header.setScaleX(0.5f);
                this.header.setAlpha(0.0f);
                this.header.animate().alpha(GalleryViewer.PROGRESS_COMPLETED).scaleY(GalleryViewer.PROGRESS_COMPLETED).scaleX(GalleryViewer.PROGRESS_COMPLETED).setStartDelay(400);
            }
            String error = "<b>" + (invalid + 0) + " images were ignored.</b><br/>";
            if (invalid > 0) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "invalid", invalid + UnsupportedUrlFragment.DISPLAY_NAME);
                error = error + "For this website, you can only download images from their page, not in the gallery.<br/>";
            }
            if (null > null) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "already saved", 0 + UnsupportedUrlFragment.DISPLAY_NAME);
                error = error + 0 + " images are already in your recent downloads.</i><br/>";
            }
            this.header.setText(Html.fromHtml(error));
        } else {
            this.header.setVisibility(8);
        }
        notifyDataSetChanged();
    }

    public void terminate() {
        this.downloads.clear();
        this.header.setVisibility(8);
        notifyDataSetChanged();
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.refreshHandler.removeCallbacks(this.refresh);
        if (this.currentScrollState == 2 && scrollState != 2) {
            int lastVisiblePosition = this.list.getLastVisiblePosition();
            if (this.greatestViewPosition < lastVisiblePosition) {
                this.greatestViewPosition = lastVisiblePosition;
                Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "scroll stop", lastVisiblePosition + UnsupportedUrlFragment.DISPLAY_NAME);
            }
            for (int i = 0; i < this.list.getChildCount(); i++) {
                ((DownloadMenuItem) this.list.getChildAt(i)).invalidateDownloadChangeFlag();
            }
            notifyDataSetChanged();
        }
        this.currentScrollState = scrollState;
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public int getScrollState() {
        return this.currentScrollState;
    }

    public boolean canShowListItems() {
        return this.currentScrollState != 2;
    }

    public void unpause() {
        this.currentScrollState = 0;
        refresh();
    }

    public void pause() {
        this.currentScrollState = 2;
    }
}
