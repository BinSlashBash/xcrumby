/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.AsyncTask$Status
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Looper
 */
package com.crumby.lib.fragment.producer;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.SilentUrlRedirectEvent;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.otto.Bus;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;

public abstract class GalleryProducer {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int DO_NOT_FOCUS = -1;
    protected static final JsonParser JSON_PARSER;
    private static Queue<DownloadGalleryImageTask> tasks;
    private volatile boolean available;
    private int currentImageFocus;
    private int currentPage;
    private AsyncTask<Integer, Void, ArrayList<GalleryImage>> downloader;
    private Exception fetchingException;
    protected Set<GalleryConsumer> galleryConsumers;
    private GalleryImage hostImage;
    private List<GalleryImage> images;
    private boolean initialized;
    private boolean metadataLoaded;
    protected String pageArg;
    private boolean shareable;
    private String silentRedirectUrl;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !GalleryProducer.class.desiredAssertionStatus();
        $assertionsDisabled = bl2;
        JSON_PARSER = new JsonParser();
        tasks = new ArrayDeque<DownloadGalleryImageTask>();
    }

    private boolean canStartFetching() {
        if (this.images.isEmpty() || this.fetchingException != null) {
            return true;
        }
        return false;
    }

    public static String fetchUrl(String string2) throws IOException {
        return GalleryProducer.fetchUrl(string2, null, null);
    }

    public static String fetchUrl(String string2, String string3) throws IOException {
        return GalleryProducer.fetchUrl(string2, string3, null);
    }

    public static String fetchUrl(String object, String string2, String string3) throws IOException {
        object = new Request.Builder().url((String)object);
        if (string3 != null) {
            object.addHeader("X-Mashape-Key", string3);
        }
        if (string2 != null) {
            object.addHeader("Authorization", string2);
        }
        return CrumbyApp.getHttpClient().newCall(object.build()).execute().body().string();
    }

    protected static String getParameterInUrl(String string2, String string3) {
        Uri uri = Uri.parse((String)string2);
        if ((string2 = GalleryProducer.getQueryParameter(uri, string2, string3)) != null && !string2.equals("")) {
            return uri.getQueryParameter(string3);
        }
        return null;
    }

    public static String getQueryParameter(Uri uri, String string2, String string3) {
        if (Build.VERSION.SDK_INT >= 16) {
            return uri.getQueryParameter(string3);
        }
        return Uri.parse((String)string2.replace("+", "%20").toString()).getQueryParameter(string3);
    }

    public static String legacyfetchUrl(String object) throws IOException {
        object = (HttpURLConnection)new URL((String)object).openConnection();
        object.connect();
        return GalleryProducer.readStreamIntoString(object.getInputStream());
    }

    public static String readStreamIntoString(InputStream closeable) throws IOException {
        int n2;
        closeable = new BufferedReader(new InputStreamReader((InputStream)closeable, "utf-8"));
        StringBuffer stringBuffer = new StringBuffer();
        char[] arrc = new char[1024];
        while ((n2 = closeable.read(arrc)) != -1) {
            stringBuffer.append(arrc, 0, n2);
        }
        return stringBuffer.toString();
    }

    public static void sendRequest() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private void signalError() {
        if (this.fetchingException != null) {
            Iterator<GalleryConsumer> iterator = this.galleryConsumers.iterator();
            while (iterator.hasNext()) {
                iterator.next().showError(this.fetchingException);
            }
        }
    }

    private void signalNoMoreLoading() {
        Iterator<GalleryConsumer> iterator = this.galleryConsumers.iterator();
        while (iterator.hasNext()) {
            iterator.next().finishLoading();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void threadSafeUpdateHostImageViews() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.hostImage.updateViews();
            return;
        } else {
            if (this.downloader == null || this.downloader.getStatus() == AsyncTask.Status.FINISHED) return;
            {
                ((DownloadGalleryImageTask)this.downloader).flagUpdateHostImageViewsOnEnd();
                return;
            }
        }
    }

    public void addConsumer(GalleryConsumer galleryConsumer) {
        this.galleryConsumers.add(galleryConsumer);
    }

    protected boolean addImagesToConsumers(List<GalleryImage> list) {
        boolean bl2 = false;
        Iterator<GalleryConsumer> iterator = this.galleryConsumers.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().addImages(list)) continue;
            bl2 = true;
        }
        return bl2;
    }

    protected void addProducedImagesToCache(List<GalleryImage> object) {
        object = object.iterator();
        while (object.hasNext()) {
            GalleryImage galleryImage = (GalleryImage)object.next();
            galleryImage.setPosition(this.images.size());
            if (this.hostImage != null) {
                galleryImage.setPath(this.hostImage.getPath());
            }
            this.images.add(galleryImage);
        }
    }

    public void alterImageAtPosition(int n2, String string2) {
        if (n2 >= this.images.size()) {
            return;
        }
        this.images.get(n2).setLinkUrl(string2);
    }

    public void clearInitialized() {
        this.initialized = false;
    }

    public void destroy() {
        if (this.images != null) {
            Iterator<GalleryImage> iterator = this.images.iterator();
            while (iterator.hasNext()) {
                iterator.next().clearViews();
            }
            this.images.clear();
        }
        if (this.galleryConsumers != null) {
            this.galleryConsumers.clear();
        }
        this.haltDownload();
    }

    protected void fetch() {
        if (this.downloader != null && this.downloader.getStatus() != AsyncTask.Status.FINISHED) {
            return;
        }
        DownloadGalleryImageTask downloadGalleryImageTask = new DownloadGalleryImageTask();
        CrDb.d("gallery producer", "fetching " + tasks.size());
        String string2 = "";
        if (this.getHostUrl() != null) {
            string2 = this.getHostUrl();
        }
        CrDb.d("gallery producer", "ASYNC MANAGER: starting async task, " + string2);
        if (tasks.size() > 4) {
            CrDb.d("gallery producer", "ASYNC MANAGER: removing async task, taking too long! " + tasks.peek().getUrl());
            tasks.remove().cancel(true);
        }
        tasks.add(downloadGalleryImageTask);
        this.downloader = downloadGalleryImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Integer[0]);
    }

    protected abstract ArrayList<GalleryImage> fetchGalleryImages(int var1) throws Exception;

    protected boolean fetchMetadata() throws Exception {
        return false;
    }

    protected void filterOutUndesiredImages(ArrayList<GalleryImage> arrayList) {
    }

    public int getCurrentImageFocus() {
        CrDb.d("producer", "get current image focus " + this.currentImageFocus);
        return this.currentImageFocus;
    }

    protected int getCurrentPage() {
        return this.currentPage;
    }

    protected GalleryImage getHostImage() {
        return this.hostImage;
    }

    public String getHostUrl() {
        return this.hostImage.getLinkUrl();
    }

    public List<GalleryImage> getImages() {
        return this.images;
    }

    public boolean haltDownload() {
        CrDb.d("producer", "Downloader halted");
        if (this.tryToStopDownload()) {
            CrDb.d("producer", "Resetting download.");
            --this.currentPage;
            this.available = true;
            return true;
        }
        return false;
    }

    protected ArrayList<GalleryImage> handleFetchingException(Exception exception, boolean bl2) {
        if (bl2) {
            --this.currentPage;
        }
        this.fetchingException = exception;
        return null;
    }

    public void initialize(GalleryConsumer galleryConsumer, GalleryImage galleryImage, Bundle object) {
        this.preInitialize();
        if (!this.initialize()) {
            for (GalleryConsumer galleryConsumer2 : this.galleryConsumers) {
                if (!$assertionsDisabled && galleryConsumer2 == galleryConsumer) {
                    throw new AssertionError();
                }
            }
            galleryConsumer.addImages(this.images);
            this.shareable = true;
        }
        this.hostImage = galleryImage;
        this.galleryConsumers.add(galleryConsumer);
        this.postInitialize();
    }

    public void initialize(GalleryConsumer galleryConsumer, GalleryImage galleryImage, Bundle bundle, boolean bl2) {
        this.initialize(galleryConsumer, galleryImage, bundle);
        if (bl2) {
            this.requestStartFetch();
        }
    }

    public boolean initialize() {
        if (this.initialized) {
            return false;
        }
        this.images = new ArrayList<GalleryImage>();
        this.available = false;
        this.galleryConsumers = new HashSet<GalleryConsumer>();
        this.currentImageFocus = -1;
        this.initialized = true;
        return true;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void makeShareable() {
        this.shareable = true;
    }

    protected void notifyHandlerDataSetsChanged() {
        Iterator<GalleryConsumer> iterator = this.galleryConsumers.iterator();
        while (iterator.hasNext()) {
            iterator.next().notifyDataSetChanged();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onFinishedDownloading(ArrayList<GalleryImage> arrayList, boolean bl2) {
        if (this.silentRedirectUrl != null) {
            BusProvider.BUS.get().post(new SilentUrlRedirectEvent(this.silentRedirectUrl, this.getHostImage()));
            this.silentRedirectUrl = null;
        }
        int n2 = arrayList == null ? 0 : arrayList.size();
        CrDb.d("producer", "" + this.currentPage + " Size:" + n2);
        if (arrayList == null || arrayList.isEmpty()) {
            this.signalNoMoreLoading();
            if (arrayList == null) {
                this.signalError();
            }
            return;
        }
        this.filterOutUndesiredImages(arrayList);
        this.addProducedImagesToCache(arrayList);
        if (this.downloader != null) {
            String string2 = "";
            if (this.getHostUrl() != null) {
                string2 = this.getHostUrl();
            }
            CrDb.d("gallery producer", "ASYNC MANAGER: removing async task, finished! " + string2);
            tasks.remove(this.downloader);
        }
        if (this.addImagesToConsumers(arrayList)) {
            CrDb.d("producer", "fetching again");
            this.downloader = null;
            this.fetch();
        } else {
            this.available = true;
        }
        if (bl2) {
            this.hostImage.updateViews();
        }
        this.notifyHandlerDataSetsChanged();
    }

    protected void postInitialize() {
    }

    protected void preInitialize() {
    }

    protected ArrayList<GalleryImage> produce() {
        CrDb.d("producer", "Fetching gallery images for page " + (this.currentPage + 1));
        try {
            ArrayList<GalleryImage> arrayList = this.tryToFetchNextBatchOfImages();
            return arrayList;
        }
        catch (Exception var1_2) {
            return this.handleFetchingException(var1_2, true);
        }
    }

    public void removeConsumer(GalleryConsumer galleryConsumer) {
        if (this.galleryConsumers == null) {
            return;
        }
        this.galleryConsumers.remove(galleryConsumer);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void requestFetch() {
        String string2 = this.hostImage != null ? this.getHostUrl() : "null";
        CrDb.d("producer", string2 + ": trying to start download...");
        if (!this.available) {
            CrDb.d("producer", string2 + ": not available.");
            return;
        }
        CrDb.d("producer", string2 + ": download started.");
        this.available = false;
        this.fetch();
    }

    public boolean requestStartFetch() {
        if (!this.canStartFetching()) {
            return false;
        }
        this.available = true;
        this.requestFetch();
        return true;
    }

    public void setCurrentImageFocus(int n2) {
        if (this.shareable) {
            this.currentImageFocus = n2;
        }
    }

    protected boolean setGalleryMetadata(String string2, String string3) {
        if (string2 != null) {
            this.hostImage.setTitle(string2);
        }
        if (string3 != null) {
            this.hostImage.setDescription(string3);
        }
        if (string2 != null || string3 != null) {
            this.threadSafeUpdateHostImageViews();
            return true;
        }
        return false;
    }

    protected void setSilentRedirectUrl(String string2) {
        this.silentRedirectUrl = string2;
    }

    protected void setStartPage(int n2) {
        if (this.currentPage != 0) {
            return;
        }
        this.currentPage = n2;
    }

    public void shareAndSetCurrentImageFocus(int n2) {
        this.shareable = true;
        this.setCurrentImageFocus(n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected ArrayList<GalleryImage> tryToFetchNextBatchOfImages() throws Exception {
        ++this.currentPage;
        if (!this.metadataLoaded) {
            try {
                if (this.fetchMetadata()) {
                    this.threadSafeUpdateHostImageViews();
                }
                this.metadataLoaded = true;
            }
            catch (Exception var1_1) {
                Analytics.INSTANCE.newException(var1_1);
            }
        }
        this.fetchingException = null;
        return this.fetchGalleryImages(this.currentPage - 1);
    }

    protected boolean tryToStopDownload() {
        if (this.downloader != null && this.downloader.cancel(true)) {
            return true;
        }
        return false;
    }

    class DownloadGalleryImageTask
    extends AsyncTask<Integer, Void, ArrayList<GalleryImage>> {
        private boolean updateHostImageViewsOnEnd;

        DownloadGalleryImageTask() {
        }

        protected /* varargs */ ArrayList<GalleryImage> doInBackground(Integer ... arrinteger) {
            GalleryProducer.this.available = false;
            return GalleryProducer.this.produce();
        }

        public void flagUpdateHostImageViewsOnEnd() {
            this.updateHostImageViewsOnEnd = true;
        }

        public String getUrl() {
            if (GalleryProducer.this.getHostUrl() == null) {
                return "";
            }
            return GalleryProducer.this.getHostUrl();
        }

        protected void onPostExecute(ArrayList<GalleryImage> arrayList) {
            GalleryProducer.this.onFinishedDownloading(arrayList, this.updateHostImageViewsOnEnd);
        }
    }

}

