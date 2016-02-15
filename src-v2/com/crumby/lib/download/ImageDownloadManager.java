/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.net.Uri
 *  android.os.Environment
 *  android.preference.PreferenceManager
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 *  android.widget.Toast
 */
package com.crumby.lib.download;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.download.ImageDownload;
import com.crumby.lib.download.ImageDownloadListener;
import com.crumby.lib.widget.firstparty.main_menu.MainMenu;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public enum ImageDownloadManager {
    INSTANCE;
    
    private HashSet<ImageDownload> completedDownloads = new HashSet();
    private Set<ImageDownloadListener> defaultListeners;
    private Collection<GalleryImage> deferredImages;
    private String downloadPath;
    private ExecutorService executor;
    private MainMenu mainMenu;
    private HashSet<ImageDownload> recentDownloads = new HashSet();

    private ImageDownloadManager() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ImageDownload download(GalleryImage object, boolean bl2) {
        if (!(object = new ImageDownload((GalleryImage)object, this.defaultListeners, this.getSaveDirectory(), bl2)).isValid()) return object;
        bl2 = this.recentDownloads.contains(object);
        boolean bl3 = this.completedDownloads.contains(object);
        if (!bl2 && !bl3) {
            this.submitRequest((ImageDownload)object);
            return object;
        }
        if (bl2) {
            this.restart((ImageDownload)object);
            return object;
        }
        object.indicateAlreadySaved();
        return object;
    }

    private void submitRequest(final ImageDownload imageDownload) {
        imageDownload.indicateQueued();
        if (this.executor == null) {
            this.executor = Executors.newFixedThreadPool(2);
        }
        imageDownload.setFuture(this.executor.submit(new Runnable(){

            @Override
            public void run() {
                imageDownload.start();
            }
        }));
    }

    public void addDefaultListener(ImageDownloadListener imageDownloadListener) {
        if (this.defaultListeners == null) {
            this.defaultListeners = new HashSet<ImageDownloadListener>();
        }
        this.defaultListeners.add(imageDownloadListener);
    }

    public void broadcast(ImageDownload imageDownload) {
        this.mainMenu.getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile((File)new File(imageDownload.getDownloadUri()))));
    }

    public boolean clearDeferredImageDownloadAndCheckIfDownloadPathIsNull() {
        if (this.deferredImages != null) {
            this.deferredImages.clear();
        }
        this.deferredImages = null;
        if (this.downloadPath == null) {
            return true;
        }
        return false;
    }

    public void clearFinishedDownloads() {
        ArrayList<ImageDownload> arrayList = new ArrayList<ImageDownload>();
        for (ImageDownload imageDownload : this.recentDownloads) {
            if (!imageDownload.isDone()) continue;
            arrayList.add(imageDownload);
        }
        this.recentDownloads.removeAll(arrayList);
        this.completedDownloads.addAll(arrayList);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void downloadAll(Collection<GalleryImage> iterator) {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "downloading images", "", iterator.size());
        if (this.downloadPath == null) {
            if (!Environment.getExternalStorageState().equals("mounted")) {
                Toast toast = Toast.makeText((Context)this.mainMenu.getContext(), (CharSequence)"", (int)1);
                toast.setGravity(5, 0, 0);
                TextView textView = (TextView)View.inflate((Context)this.mainMenu.getContext(), (int)2130903118, (ViewGroup)null);
                textView.setText((CharSequence)"Crumby could not access your download folder. Please check that your SD card is mounted!");
                toast.setView((View)textView);
                toast.show();
                this.deferredImages = iterator;
                return;
            }
            this.downloadPath = Environment.getExternalStorageDirectory().getPath();
            PreferenceManager.getDefaultSharedPreferences((Context)this.mainMenu.getContext()).edit().putString("crumbyDownloadDirectory", this.downloadPath).commit();
        }
        this.mainMenu.showDownloads();
        this.clearFinishedDownloads();
        ArrayList<ImageDownload> arrayList = new ArrayList<ImageDownload>();
        boolean bl2 = PreferenceManager.getDefaultSharedPreferences((Context)this.mainMenu.getContext()).getBoolean("crumbyUseBreadcrumbPathForDownload", false);
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            arrayList.add(this.download((GalleryImage)((Object)iterator.next()), bl2));
        }
        this.recentDownloads.addAll(arrayList);
        iterator = this.defaultListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().update(arrayList);
        }
    }

    public void downloadOne(GalleryImage galleryImage) {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        arrayList.add(galleryImage);
        this.downloadAll(arrayList);
    }

    public String getDownloadPath() {
        return this.downloadPath;
    }

    public int getDownloadingCount() {
        int n2 = 0;
        Iterator<ImageDownload> iterator = this.recentDownloads.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isDownloading()) continue;
            ++n2;
        }
        return n2;
    }

    public String getSaveDirectory() {
        if (this.downloadPath == null) {
            this.downloadPath = Environment.getExternalStorageDirectory().getPath();
        }
        return this.downloadPath + "/crumby/";
    }

    public void initialize(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void restart(ImageDownload object) {
        for (ImageDownload imageDownload : this.recentDownloads) {
            if (!object.equals(imageDownload) || !imageDownload.canBeRestarted()) continue;
            this.submitRequest(imageDownload);
            object = this.defaultListeners.iterator();
            while (object.hasNext()) {
                ((ImageDownloadListener)object.next()).update(imageDownload);
            }
            Analytics.INSTANCE.newImageDownloadEvent(imageDownload, "restart");
            break;
        }
    }

    public void setDownloadPath(String string2) {
        this.downloadPath = string2;
        if (this.deferredImages == null || this.deferredImages.isEmpty()) {
            return;
        }
        this.downloadAll(this.deferredImages);
        this.deferredImages = null;
    }

    public void terminateDownloads() {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "terminate", "" + (this.recentDownloads.size() + this.completedDownloads.size()) + "");
        if (this.executor != null) {
            this.executor.shutdownNow();
        }
        this.executor = null;
        this.recentDownloads.clear();
        this.completedDownloads.clear();
        Iterator<ImageDownloadListener> iterator = this.defaultListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().terminate();
        }
    }

}

