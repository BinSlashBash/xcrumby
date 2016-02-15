package com.crumby.lib.download;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.crumby.Analytics;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Future;

public class ImageDownload {
    public static final int ERROR = -1;
    public static final int FINISHED = 100;
    public static final int INVALID = -3;
    public static final int STARTED = 0;
    public static final int STOP = -2;
    private Set<ImageDownloadListener> deferAdd;
    private Set<ImageDownloadListener> deferRemove;
    private final String downloadUri;
    private Future future;
    private final GalleryImage image;
    private boolean iterating;
    private final ArrayList<ImageDownloadListener> listeners;
    private int progress;

    public ImageDownload(GalleryImage image, Set<ImageDownloadListener> listeners, String rootDirectory, boolean useImagePath) {
        this.image = image;
        this.listeners = new ArrayList(listeners);
        if (image == null || image.getImageUrl() == null) {
            this.progress = INVALID;
            this.listeners.clear();
            this.downloadUri = null;
            return;
        }
        String directory = rootDirectory;
        if (useImagePath) {
            directory = directory + image.buildPath();
        }
        directory = directory.replaceAll("\\s+", "_").replace(":", "_");
        new File(directory).mkdirs();
        this.downloadUri = directory + image.getRequestedFilename();
    }

    public synchronized void update(int progress) {
        CrDb.m0d("image download", "updating " + this.downloadUri + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + progress);
        this.progress = progress;
        this.iterating = true;
        Iterator i$ = this.listeners.iterator();
        while (i$.hasNext()) {
            ((ImageDownloadListener) i$.next()).update(this);
        }
        this.iterating = false;
        if (this.deferRemove != null) {
            this.listeners.removeAll(this.deferRemove);
        }
        if (this.deferAdd != null) {
            this.listeners.addAll(STARTED, this.deferAdd);
        }
        this.deferRemove = null;
        this.deferAdd = null;
        if (progress == FINISHED) {
            this.listeners.clear();
        }
    }

    public GalleryImage getImage() {
        return this.image;
    }

    public String getDownloadUri() {
        return this.downloadUri;
    }

    public void deferAddListener(ImageDownloadListener listener) {
        if (!this.iterating) {
            this.listeners.add(STARTED, listener);
        }
        if (this.deferAdd == null) {
            this.deferAdd = new HashSet();
        }
        this.deferAdd.add(listener);
    }

    public void deferRemoveListener(ImageDownloadListener listener) {
        if (!this.iterating) {
            this.listeners.remove(listener);
        }
        if (this.deferRemove == null) {
            this.deferRemove = new HashSet();
        }
        this.deferRemove.add(listener);
    }

    public void start() {
        IOException e;
        if (this.downloadUri != null) {
            FileOutputStream out = null;
            try {
                long start = System.currentTimeMillis();
                FileOutputStream out2 = new FileOutputStream(this.downloadUri);
                try {
                    byte[] buffer = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
                    URLConnection connection = new URL(this.image.getImageUrl()).openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                    InputStream inputStream = connection.getInputStream();
                    while (true) {
                        int len = inputStream.read(buffer);
                        if (len != ERROR) {
                            out2.write(buffer, STARTED, len);
                        } else {
                            update(FINISHED);
                            Analytics.INSTANCE.newImageDownloadEvent(this, "finished", start);
                            ImageDownloadManager.INSTANCE.broadcast(this);
                            out = out2;
                            return;
                        }
                    }
                } catch (IOException e2) {
                    e = e2;
                    out = out2;
                    Analytics.INSTANCE.newError(DisplayError.COULD_NOT_DOWNLOAD_IMAGE, this.downloadUri + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e.toString());
                    this.future.cancel(true);
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    new File(this.downloadUri).delete();
                    update(ERROR);
                }
            } catch (IOException e3) {
                e = e3;
                Analytics.INSTANCE.newError(DisplayError.COULD_NOT_DOWNLOAD_IMAGE, this.downloadUri + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e.toString());
                this.future.cancel(true);
                if (out != null) {
                    out.close();
                }
                new File(this.downloadUri).delete();
                update(ERROR);
            }
        }
    }

    public int getProgress() {
        return this.progress;
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    public boolean stop() {
        update(STOP);
        Analytics.INSTANCE.newImageDownloadEvent(this, "stop");
        return this.future.cancel(true);
    }

    public boolean isValid() {
        return (this.progress == INVALID || this.downloadUri == null) ? false : true;
    }

    public boolean isDownloading() {
        return this.downloadUri != null && this.progress >= 0 && this.progress < FINISHED;
    }

    public boolean isDone() {
        return this.progress == FINISHED;
    }

    public boolean hasStopped() {
        return this.progress == STOP;
    }

    public boolean hasError() {
        return this.progress == ERROR;
    }

    public boolean canBeRestarted() {
        return hasStopped() || hasError();
    }

    public int hashCode() {
        if (this.downloadUri == null) {
            return STARTED;
        }
        return this.downloadUri.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof ImageDownload) || this.downloadUri == null) {
            return false;
        }
        return this.downloadUri.equals(((ImageDownload) o).downloadUri);
    }

    public void indicateAlreadySaved() {
        this.progress = FINISHED;
        this.listeners.clear();
    }

    public void indicateQueued() {
        if (isValid()) {
            this.progress = STARTED;
        }
    }
}
