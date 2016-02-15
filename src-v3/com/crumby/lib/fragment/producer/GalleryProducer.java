package com.crumby.lib.fragment.producer;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.SilentUrlRedirectEvent;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Request.Builder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import oauth.signpost.OAuth;

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

    class DownloadGalleryImageTask extends AsyncTask<Integer, Void, ArrayList<GalleryImage>> {
        private boolean updateHostImageViewsOnEnd;

        DownloadGalleryImageTask() {
        }

        protected ArrayList<GalleryImage> doInBackground(Integer... params) {
            GalleryProducer.this.available = GalleryProducer.$assertionsDisabled;
            return GalleryProducer.this.produce();
        }

        public void flagUpdateHostImageViewsOnEnd() {
            this.updateHostImageViewsOnEnd = true;
        }

        protected void onPostExecute(ArrayList<GalleryImage> galleryImages) {
            GalleryProducer.this.onFinishedDownloading(galleryImages, this.updateHostImageViewsOnEnd);
        }

        public String getUrl() {
            return GalleryProducer.this.getHostUrl() == null ? UnsupportedUrlFragment.DISPLAY_NAME : GalleryProducer.this.getHostUrl();
        }
    }

    protected abstract ArrayList<GalleryImage> fetchGalleryImages(int i) throws Exception;

    static {
        boolean z;
        if (GalleryProducer.class.desiredAssertionStatus()) {
            z = $assertionsDisabled;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
        JSON_PARSER = new JsonParser();
        tasks = new ArrayDeque();
    }

    protected static String getParameterInUrl(String url, String argName) {
        Uri uri = Uri.parse(url);
        String parameter = getQueryParameter(uri, url, argName);
        if (parameter == null || parameter.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            return null;
        }
        return uri.getQueryParameter(argName);
    }

    public boolean haltDownload() {
        CrDb.m0d("producer", "Downloader halted");
        if (!tryToStopDownload()) {
            return $assertionsDisabled;
        }
        CrDb.m0d("producer", "Resetting download.");
        this.currentPage += DO_NOT_FOCUS;
        this.available = true;
        return true;
    }

    protected boolean tryToStopDownload() {
        return (this.downloader == null || !this.downloader.cancel(true)) ? $assertionsDisabled : true;
    }

    protected void setStartPage(int startPage) {
        if (this.currentPage == 0) {
            this.currentPage = startPage;
        }
    }

    protected int getCurrentPage() {
        return this.currentPage;
    }

    protected void setSilentRedirectUrl(String silentRedirectUrl) {
        this.silentRedirectUrl = silentRedirectUrl;
    }

    public void initialize(GalleryConsumer adapter, GalleryImage image, Bundle arguments, boolean startFetching) {
        initialize(adapter, image, arguments);
        if (startFetching) {
            requestStartFetch();
        }
    }

    protected void preInitialize() {
    }

    protected void postInitialize() {
    }

    public boolean initialize() {
        if (this.initialized) {
            return $assertionsDisabled;
        }
        this.images = new ArrayList();
        this.available = $assertionsDisabled;
        this.galleryConsumers = new HashSet();
        this.currentImageFocus = DO_NOT_FOCUS;
        this.initialized = true;
        return true;
    }

    public void initialize(GalleryConsumer adapter, GalleryImage image, Bundle arguments) {
        preInitialize();
        if (!initialize()) {
            for (GalleryConsumer handler : this.galleryConsumers) {
                if (!$assertionsDisabled && handler == adapter) {
                    throw new AssertionError();
                }
            }
            adapter.addImages(this.images);
            this.shareable = true;
        }
        this.hostImage = image;
        this.galleryConsumers.add(adapter);
        postInitialize();
    }

    protected void notifyHandlerDataSetsChanged() {
        for (GalleryConsumer handler : this.galleryConsumers) {
            handler.notifyDataSetChanged();
        }
    }

    protected boolean addImagesToConsumers(List<GalleryImage> galleryImages) {
        boolean fetchAgain = $assertionsDisabled;
        for (GalleryConsumer handler : this.galleryConsumers) {
            if (handler.addImages(galleryImages)) {
                fetchAgain = true;
            }
        }
        return fetchAgain;
    }

    public void addConsumer(GalleryConsumer galleryConsumer) {
        this.galleryConsumers.add(galleryConsumer);
    }

    public void removeConsumer(GalleryConsumer removeHandler) {
        if (this.galleryConsumers != null) {
            this.galleryConsumers.remove(removeHandler);
        }
    }

    public List<GalleryImage> getImages() {
        return this.images;
    }

    public void makeShareable() {
        this.shareable = true;
    }

    public void setCurrentImageFocus(int index) {
        if (this.shareable) {
            this.currentImageFocus = index;
        }
    }

    public int getCurrentImageFocus() {
        CrDb.m0d("producer", "get current image focus " + this.currentImageFocus);
        return this.currentImageFocus;
    }

    protected void addProducedImagesToCache(List<GalleryImage> galleryImages) {
        for (GalleryImage image : galleryImages) {
            image.setPosition(this.images.size());
            if (this.hostImage != null) {
                image.setPath(this.hostImage.getPath());
            }
            this.images.add(image);
        }
    }

    public void shareAndSetCurrentImageFocus(int position) {
        this.shareable = true;
        setCurrentImageFocus(position);
    }

    private void signalNoMoreLoading() {
        for (GalleryConsumer handler : this.galleryConsumers) {
            handler.finishLoading();
        }
    }

    private void signalError() {
        if (this.fetchingException != null) {
            for (GalleryConsumer handler : this.galleryConsumers) {
                handler.showError(this.fetchingException);
            }
        }
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void alterImageAtPosition(int index, String silentRedirectUrl) {
        if (index < this.images.size()) {
            ((GalleryImage) this.images.get(index)).setLinkUrl(silentRedirectUrl);
        }
    }

    protected void onFinishedDownloading(ArrayList<GalleryImage> galleryImages, boolean updateHostImageViews) {
        if (this.silentRedirectUrl != null) {
            BusProvider.BUS.get().post(new SilentUrlRedirectEvent(this.silentRedirectUrl, getHostImage()));
            this.silentRedirectUrl = null;
        }
        CrDb.m0d("producer", this.currentPage + " Size:" + (galleryImages == null ? 0 : galleryImages.size()));
        if (galleryImages == null || galleryImages.isEmpty()) {
            signalNoMoreLoading();
            if (galleryImages == null) {
                signalError();
                return;
            }
            return;
        }
        filterOutUndesiredImages(galleryImages);
        addProducedImagesToCache(galleryImages);
        if (this.downloader != null) {
            String url = UnsupportedUrlFragment.DISPLAY_NAME;
            if (getHostUrl() != null) {
                url = getHostUrl();
            }
            CrDb.m0d("gallery producer", "ASYNC MANAGER: removing async task, finished! " + url);
            tasks.remove(this.downloader);
        }
        if (addImagesToConsumers(galleryImages)) {
            CrDb.m0d("producer", "fetching again");
            this.downloader = null;
            fetch();
        } else {
            this.available = true;
        }
        if (updateHostImageViews) {
            this.hostImage.updateViews();
        }
        notifyHandlerDataSetsChanged();
    }

    protected void filterOutUndesiredImages(ArrayList<GalleryImage> arrayList) {
    }

    public void clearInitialized() {
        this.initialized = $assertionsDisabled;
    }

    public void destroy() {
        if (this.images != null) {
            for (GalleryImage image : this.images) {
                image.clearViews();
            }
            this.images.clear();
        }
        if (this.galleryConsumers != null) {
            this.galleryConsumers.clear();
        }
        haltDownload();
    }

    public boolean isAvailable() {
        return this.available;
    }

    protected ArrayList<GalleryImage> tryToFetchNextBatchOfImages() throws Exception {
        this.currentPage++;
        if (!this.metadataLoaded) {
            try {
                if (fetchMetadata()) {
                    threadSafeUpdateHostImageViews();
                }
                this.metadataLoaded = true;
            } catch (Exception e) {
                Analytics.INSTANCE.newException(e);
            }
        }
        this.fetchingException = null;
        return fetchGalleryImages(this.currentPage + DO_NOT_FOCUS);
    }

    protected ArrayList<GalleryImage> produce() {
        CrDb.m0d("producer", "Fetching gallery images for page " + (this.currentPage + 1));
        try {
            return tryToFetchNextBatchOfImages();
        } catch (Exception e) {
            return handleFetchingException(e, true);
        }
    }

    protected ArrayList<GalleryImage> handleFetchingException(Exception e, boolean decrementPageCounter) {
        if (decrementPageCounter) {
            this.currentPage += DO_NOT_FOCUS;
        }
        this.fetchingException = e;
        return null;
    }

    private boolean canStartFetching() {
        return (this.images.isEmpty() || this.fetchingException != null) ? true : $assertionsDisabled;
    }

    public boolean requestStartFetch() {
        if (!canStartFetching()) {
            return $assertionsDisabled;
        }
        this.available = true;
        requestFetch();
        return true;
    }

    public static String fetchUrl(String url, String authorization) throws IOException {
        return fetchUrl(url, authorization, null);
    }

    public static String fetchUrl(String url) throws IOException {
        return fetchUrl(url, null, null);
    }

    public static String legacyfetchUrl(String urlString) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(urlString).openConnection();
        urlConnection.connect();
        return readStreamIntoString(urlConnection.getInputStream());
    }

    public static String fetchUrl(String urlString, String authorization, String mashape) throws IOException {
        Builder request = new Builder().url(urlString);
        if (mashape != null) {
            request.addHeader("X-Mashape-Key", mashape);
        }
        if (authorization != null) {
            request.addHeader(OAuth.HTTP_AUTHORIZATION_HEADER, authorization);
        }
        return CrumbyApp.getHttpClient().newCall(request.build()).execute().body().string();
    }

    public static void sendRequest() {
    }

    public static String readStreamIntoString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        StringBuffer buffer = new StringBuffer();
        char[] chars = new char[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            int read = reader.read(chars);
            if (read == DO_NOT_FOCUS) {
                return buffer.toString();
            }
            buffer.append(chars, 0, read);
        }
    }

    protected boolean fetchMetadata() throws Exception {
        return $assertionsDisabled;
    }

    public void requestFetch() {
        String hostUrl = this.hostImage != null ? getHostUrl() : "null";
        CrDb.m0d("producer", hostUrl + ": trying to start download...");
        if (this.available) {
            CrDb.m0d("producer", hostUrl + ": download started.");
            this.available = $assertionsDisabled;
            fetch();
            return;
        }
        CrDb.m0d("producer", hostUrl + ": not available.");
    }

    protected void fetch() {
        if (this.downloader == null || this.downloader.getStatus() == Status.FINISHED) {
            DownloadGalleryImageTask task = new DownloadGalleryImageTask();
            CrDb.m0d("gallery producer", "fetching " + tasks.size());
            String url = UnsupportedUrlFragment.DISPLAY_NAME;
            if (getHostUrl() != null) {
                url = getHostUrl();
            }
            CrDb.m0d("gallery producer", "ASYNC MANAGER: starting async task, " + url);
            if (tasks.size() > 4) {
                CrDb.m0d("gallery producer", "ASYNC MANAGER: removing async task, taking too long! " + ((DownloadGalleryImageTask) tasks.peek()).getUrl());
                ((DownloadGalleryImageTask) tasks.remove()).cancel(true);
            }
            tasks.add(task);
            this.downloader = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Integer[0]);
        }
    }

    protected GalleryImage getHostImage() {
        return this.hostImage;
    }

    public String getHostUrl() {
        return this.hostImage.getLinkUrl();
    }

    protected boolean setGalleryMetadata(String title, String description) {
        if (title != null) {
            this.hostImage.setTitle(title);
        }
        if (description != null) {
            this.hostImage.setDescription(description);
        }
        if (title == null && description == null) {
            return $assertionsDisabled;
        }
        threadSafeUpdateHostImageViews();
        return true;
    }

    private void threadSafeUpdateHostImageViews() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.hostImage.updateViews();
        } else if (this.downloader != null && this.downloader.getStatus() != Status.FINISHED) {
            ((DownloadGalleryImageTask) this.downloader).flagUpdateHostImageViewsOnEnd();
        }
    }

    public static String getQueryParameter(Uri uri, String url, String key) {
        if (VERSION.SDK_INT >= 16) {
            return uri.getQueryParameter(key);
        }
        return Uri.parse(url.replace("+", "%20").toString()).getQueryParameter(key);
    }
}
