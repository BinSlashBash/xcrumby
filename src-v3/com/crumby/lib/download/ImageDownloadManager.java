package com.crumby.lib.download;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.firstparty.main_menu.MainMenu;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum ImageDownloadManager {
    INSTANCE;
    
    private HashSet<ImageDownload> completedDownloads;
    private Set<ImageDownloadListener> defaultListeners;
    private Collection<GalleryImage> deferredImages;
    private String downloadPath;
    private ExecutorService executor;
    private MainMenu mainMenu;
    private HashSet<ImageDownload> recentDownloads;

    /* renamed from: com.crumby.lib.download.ImageDownloadManager.1 */
    class C00951 implements Runnable {
        final /* synthetic */ ImageDownload val$imageDownload;

        C00951(ImageDownload imageDownload) {
            this.val$imageDownload = imageDownload;
        }

        public void run() {
            this.val$imageDownload.start();
        }
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

    public int getDownloadingCount() {
        int count = 0;
        Iterator i$ = this.recentDownloads.iterator();
        while (i$.hasNext()) {
            if (((ImageDownload) i$.next()).isDownloading()) {
                count++;
            }
        }
        return count;
    }

    public void clearFinishedDownloads() {
        ArrayList<ImageDownload> remove = new ArrayList();
        Iterator i$ = this.recentDownloads.iterator();
        while (i$.hasNext()) {
            ImageDownload imageDownload = (ImageDownload) i$.next();
            if (imageDownload.isDone()) {
                remove.add(imageDownload);
            }
        }
        this.recentDownloads.removeAll(remove);
        this.completedDownloads.addAll(remove);
    }

    public void downloadOne(GalleryImage image) {
        ArrayList<GalleryImage> images = new ArrayList();
        images.add(image);
        downloadAll(images);
    }

    public void downloadAll(Collection<GalleryImage> images) {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "downloading images", UnsupportedUrlFragment.DISPLAY_NAME, (long) images.size());
        if (this.downloadPath == null) {
            if (Environment.getExternalStorageState().equals("mounted")) {
                this.downloadPath = Environment.getExternalStorageDirectory().getPath();
                PreferenceManager.getDefaultSharedPreferences(this.mainMenu.getContext()).edit().putString(GalleryViewer.CRUMBY_DOWNLOAD_DIRECTORY, this.downloadPath).commit();
            } else {
                Toast toast = Toast.makeText(this.mainMenu.getContext(), UnsupportedUrlFragment.DISPLAY_NAME, 1);
                toast.setGravity(5, 0, 0);
                TextView view = (TextView) View.inflate(this.mainMenu.getContext(), C0065R.layout.toast_alert_error, null);
                view.setText("Crumby could not access your download folder. Please check that your SD card is mounted!");
                toast.setView(view);
                toast.show();
                this.deferredImages = images;
                return;
            }
        }
        this.mainMenu.showDownloads();
        clearFinishedDownloads();
        ArrayList duplicate = new ArrayList();
        boolean useImagePath = PreferenceManager.getDefaultSharedPreferences(this.mainMenu.getContext()).getBoolean(MainMenu.USE_BREADCRUMB_PATH_FOR_DOWNLOAD, false);
        for (GalleryImage image : images) {
            duplicate.add(download(image, useImagePath));
        }
        this.recentDownloads.addAll(duplicate);
        for (ImageDownloadListener listener : this.defaultListeners) {
            listener.update(duplicate);
        }
    }

    private ImageDownload download(GalleryImage image, boolean useImagePath) {
        ImageDownload imageDownload = new ImageDownload(image, this.defaultListeners, getSaveDirectory(), useImagePath);
        if (imageDownload.isValid()) {
            boolean inRecent = this.recentDownloads.contains(imageDownload);
            boolean inCompleted = this.completedDownloads.contains(imageDownload);
            if (!inRecent && !inCompleted) {
                submitRequest(imageDownload);
            } else if (inRecent) {
                restart(imageDownload);
            } else {
                imageDownload.indicateAlreadySaved();
            }
        }
        return imageDownload;
    }

    private void submitRequest(ImageDownload imageDownload) {
        imageDownload.indicateQueued();
        if (this.executor == null) {
            this.executor = Executors.newFixedThreadPool(2);
        }
        imageDownload.setFuture(this.executor.submit(new C00951(imageDownload)));
    }

    public void addDefaultListener(ImageDownloadListener downloadListener) {
        if (this.defaultListeners == null) {
            this.defaultListeners = new HashSet();
        }
        this.defaultListeners.add(downloadListener);
    }

    public void restart(ImageDownload download) {
        Iterator i$ = this.recentDownloads.iterator();
        while (i$.hasNext()) {
            ImageDownload imageDownload = (ImageDownload) i$.next();
            if (download.equals(imageDownload) && imageDownload.canBeRestarted()) {
                submitRequest(imageDownload);
                for (ImageDownloadListener listener : this.defaultListeners) {
                    listener.update(imageDownload);
                }
                Analytics.INSTANCE.newImageDownloadEvent(imageDownload, "restart");
                return;
            }
        }
    }

    public void terminateDownloads() {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "terminate", (this.recentDownloads.size() + this.completedDownloads.size()) + UnsupportedUrlFragment.DISPLAY_NAME);
        if (this.executor != null) {
            this.executor.shutdownNow();
        }
        this.executor = null;
        this.recentDownloads.clear();
        this.completedDownloads.clear();
        for (ImageDownloadListener listener : this.defaultListeners) {
            listener.terminate();
        }
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
        if (this.deferredImages != null && !this.deferredImages.isEmpty()) {
            downloadAll(this.deferredImages);
            this.deferredImages = null;
        }
    }

    public boolean clearDeferredImageDownloadAndCheckIfDownloadPathIsNull() {
        if (this.deferredImages != null) {
            this.deferredImages.clear();
        }
        this.deferredImages = null;
        return this.downloadPath == null;
    }

    public String getDownloadPath() {
        return this.downloadPath;
    }

    public void broadcast(ImageDownload imageDownload) {
        this.mainMenu.getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(imageDownload.getDownloadUri()))));
    }
}
