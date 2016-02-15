/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.download;

import com.crumby.Analytics;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.download.ImageDownloadListener;
import com.crumby.lib.download.ImageDownloadManager;
import com.crumby.lib.widget.firstparty.DisplayError;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
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

    public ImageDownload(GalleryImage galleryImage, Set<ImageDownloadListener> object, String string2, boolean bl2) {
        this.image = galleryImage;
        this.listeners = new ArrayList<ImageDownloadListener>((Collection<ImageDownloadListener>)object);
        if (galleryImage == null || galleryImage.getImageUrl() == null) {
            this.progress = -3;
            this.listeners.clear();
            this.downloadUri = null;
            return;
        }
        object = string2;
        if (bl2) {
            object = string2 + galleryImage.buildPath();
        }
        object = object.replaceAll("\\s+", "_").replace(":", "_");
        new File((String)object).mkdirs();
        this.downloadUri = (String)object + galleryImage.getRequestedFilename();
    }

    public boolean canBeRestarted() {
        if (this.hasStopped() || this.hasError()) {
            return true;
        }
        return false;
    }

    public void deferAddListener(ImageDownloadListener imageDownloadListener) {
        if (!this.iterating) {
            this.listeners.add(0, imageDownloadListener);
        }
        if (this.deferAdd == null) {
            this.deferAdd = new HashSet<ImageDownloadListener>();
        }
        this.deferAdd.add(imageDownloadListener);
    }

    public void deferRemoveListener(ImageDownloadListener imageDownloadListener) {
        if (!this.iterating) {
            this.listeners.remove(imageDownloadListener);
        }
        if (this.deferRemove == null) {
            this.deferRemove = new HashSet<ImageDownloadListener>();
        }
        this.deferRemove.add(imageDownloadListener);
    }

    public boolean equals(Object object) {
        if (!(object instanceof ImageDownload) || this.downloadUri == null) {
            return false;
        }
        return this.downloadUri.equals(((ImageDownload)object).downloadUri);
    }

    public String getDownloadUri() {
        return this.downloadUri;
    }

    public GalleryImage getImage() {
        return this.image;
    }

    public int getProgress() {
        return this.progress;
    }

    public boolean hasError() {
        if (this.progress == -1) {
            return true;
        }
        return false;
    }

    public boolean hasStopped() {
        if (this.progress == -2) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.downloadUri == null) {
            return 0;
        }
        return this.downloadUri.hashCode();
    }

    public void indicateAlreadySaved() {
        this.progress = 100;
        this.listeners.clear();
    }

    public void indicateQueued() {
        if (!this.isValid()) {
            return;
        }
        this.progress = 0;
    }

    public boolean isDone() {
        if (this.progress == 100) {
            return true;
        }
        return false;
    }

    public boolean isDownloading() {
        if (this.downloadUri != null && this.progress >= 0 && this.progress < 100) {
            return true;
        }
        return false;
    }

    public boolean isValid() {
        if (this.progress != -3 && this.downloadUri != null) {
            return true;
        }
        return false;
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void start() {
        if (this.downloadUri == null) {
            return;
        }
        var6_1 = null;
        var2_2 = System.currentTimeMillis();
        var5_3 = new FileOutputStream(this.downloadUri);
        try {
            var6_1 = new URL(this.image.getImageUrl());
            var4_4 = new byte[1024];
            var6_1 = var6_1.openConnection();
            var6_1.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            var6_1 = var6_1.getInputStream();
            while ((var1_9 = var6_1.read(var4_4)) != -1) {
                var5_3.write(var4_4, 0, var1_9);
            }
            this.update(100);
            Analytics.INSTANCE.newImageDownloadEvent(this, "finished", var2_2);
            ImageDownloadManager.INSTANCE.broadcast(this);
            return;
        }
        catch (IOException var4_5) {}
        ** GOTO lbl-1000
        catch (IOException var4_8) {
            var5_3 = var6_1;
        }
lbl-1000: // 2 sources:
        {
            Analytics.INSTANCE.newError(DisplayError.COULD_NOT_DOWNLOAD_IMAGE, this.downloadUri + " " + var4_6.toString());
            this.future.cancel(true);
            if (var5_3 != null) {
                try {
                    var5_3.close();
                }
                catch (IOException var4_7) {
                    var4_7.printStackTrace();
                }
            }
            new File(this.downloadUri).delete();
            this.update(-1);
            return;
        }
    }

    public boolean stop() {
        this.update(-2);
        Analytics.INSTANCE.newImageDownloadEvent(this, "stop");
        return this.future.cancel(true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void update(int n2) {
        synchronized (this) {
            CrDb.d("image download", "updating " + this.downloadUri + " " + n2);
            this.progress = n2;
            this.iterating = true;
            Iterator<ImageDownloadListener> iterator = this.listeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().update(this);
            }
            this.iterating = false;
            if (this.deferRemove != null) {
                this.listeners.removeAll(this.deferRemove);
            }
            if (this.deferAdd != null) {
                this.listeners.addAll(0, this.deferAdd);
            }
            this.deferRemove = null;
            this.deferAdd = null;
            if (n2 == 100) {
                this.listeners.clear();
            }
            return;
        }
    }
}

