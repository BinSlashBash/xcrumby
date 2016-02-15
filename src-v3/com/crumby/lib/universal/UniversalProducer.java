package com.crumby.lib.universal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.wallet.WalletConstants;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class UniversalProducer extends GalleryProducer implements UniversalInterpreterInterface {
    private static final int COULD_NOT_PARSE = 0;
    private static final long ONE_DAY = 86400000;
    private static final int TIMEOUT = 1;
    private static Handler handler;
    protected static ObjectMapper mapper;
    private static Map<String, ScriptReference> scripts;
    private boolean finished;
    private UniversalInterpreter interpreter;

    /* renamed from: com.crumby.lib.universal.UniversalProducer.1 */
    static class C01111 extends Handler {
        C01111() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /* renamed from: com.crumby.lib.universal.UniversalProducer.2 */
    class C01122 implements Runnable {
        final /* synthetic */ int val$pageNumber;

        C01122(int i) {
            this.val$pageNumber = i;
        }

        public void run() {
            UniversalProducer.this.injectScriptIntoInterpreter(this.val$pageNumber);
        }
    }

    /* renamed from: com.crumby.lib.universal.UniversalProducer.3 */
    class C01133 implements Runnable {
        final /* synthetic */ IOException val$e1;

        C01133(IOException iOException) {
            this.val$e1 = iOException;
        }

        public void run() {
            UniversalProducer.this.webviewEncounteredAnException(this.val$e1);
        }
    }

    /* renamed from: com.crumby.lib.universal.UniversalProducer.5 */
    class C01155 implements Runnable {
        final /* synthetic */ String val$json;

        C01155(String str) {
            this.val$json = str;
        }

        public void run() {
            try {
                JsonNode node = UniversalProducer.mapper.readTree(this.val$json);
                UniversalProducer.this.checkPageNodeForError(node);
                UniversalProducer.this.onFinishedDownloading(UniversalProducer.this.getImagesFromJson(node), false);
            } catch (Exception e) {
                UniversalProducer.this.webviewEncounteredAnException(e);
            }
        }
    }

    /* renamed from: com.crumby.lib.universal.UniversalProducer.6 */
    class C01166 implements Runnable {
        final /* synthetic */ String val$description;
        final /* synthetic */ String val$failingUrl;

        C01166(String str, String str2) {
            this.val$description = str;
            this.val$failingUrl = str2;
        }

        public void run() {
            UniversalProducer.this.webviewEncounteredAnException(new Exception(this.val$description + "@" + this.val$failingUrl));
        }
    }

    class ScriptReference {
        long lastUpdated;
        String script;

        public ScriptReference(String script, long time) {
            this.script = script;
            this.lastUpdated = time;
        }
    }

    private class UniversalProducerException extends Exception {
        public UniversalProducerException(String exceptionMessage) {
            super(exceptionMessage);
        }

        public String toString() {
            return getMessage();
        }
    }

    /* renamed from: com.crumby.lib.universal.UniversalProducer.4 */
    class C07384 implements Callback {
        final /* synthetic */ int val$pageNumber;

        /* renamed from: com.crumby.lib.universal.UniversalProducer.4.1 */
        class C01141 implements Runnable {
            C01141() {
            }

            public void run() {
                UniversalProducer.this.injectScriptIntoInterpreter(C07384.this.val$pageNumber);
            }
        }

        C07384(int i) {
            this.val$pageNumber = i;
        }

        public void onFailure(Request request, IOException e) {
            UniversalProducer.this.failedToLoadRemoteScript(this.val$pageNumber);
        }

        public void onResponse(Response response) throws IOException {
            if (UniversalProducer.this.interpreter != null) {
                if (response.isSuccessful()) {
                    Analytics.INSTANCE.newEvent(AnalyticsCategories.UNIVERSAL, "phone home", UniversalProducer.this.getScriptFileName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + UniversalProducer.this.getHostUrl());
                    CrDb.m0d("universal producer", "loaded script from server! " + UniversalProducer.this.getScriptFilenameOnServer());
                    UniversalProducer.this.saveScript(UniversalProducer.this.interpreter.getContext(), response.body().string());
                    UniversalProducer.handler.post(new C01141());
                    return;
                }
                UniversalProducer.this.failedToLoadRemoteScript(this.val$pageNumber);
            }
        }
    }

    protected abstract String getBaseUrl();

    protected abstract String getRegexForMatchingId();

    protected abstract String getScriptName();

    static {
        scripts = new HashMap();
        mapper = new ObjectMapper();
        handler = new C01111();
    }

    protected String getExtraScript() {
        return UnsupportedUrlFragment.DISPLAY_NAME;
    }

    private void injectScriptIntoInterpreter(int pageNumber) {
        String script = ((ScriptReference) scripts.get(getScriptName())).script;
        if (script == null) {
            webviewEncounteredAnException(new Exception("Uh oh, some of Crumby's files got corrupted. Please restart your app!"));
        }
        if (this.interpreter != null) {
            String declareMatchedId = UnsupportedUrlFragment.DISPLAY_NAME;
            String matchedId = getMatchingId();
            if (matchedId != null) {
                matchedId = "'" + matchedId + "'";
            }
            this.interpreter.loadDataWithBaseURL(getBaseUrl(), "<html><body><iframe id='haxx'></iframe><div id='haxx1'></div>\n<script type='text/javascript'>" + UniversalInterpreterManager.INSTANCE.getLibraryJS() + "</script>" + "<script type='text/javascript'>" + "var pageNumber = " + pageNumber + "; " + "var hostUrl = '" + getHostUrl() + "'; " + ("var matchedId = " + matchedId + ";") + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getExtraScript() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + "</script>" + "<script type='text/javascript'>" + script + "</script>" + "</body>\n" + "</html>", "text/html", "utf-8", null);
        }
    }

    private String getScriptFileName() {
        return getScriptName() + ".min.js";
    }

    private String getScriptFilenameOnServer() {
        return "js/" + "45/" + getScriptFileName();
    }

    private void saveScript(Context context, String script) throws IOException {
        context.openFileOutput(getScriptFileName(), COULD_NOT_PARSE).write(script.getBytes());
        putScript(script);
    }

    private void putScript(String script) {
        scripts.put(getScriptName(), new ScriptReference(script, System.currentTimeMillis()));
    }

    private boolean hasLoadedMostCurrentScript() {
        ScriptReference scriptReference = (ScriptReference) scripts.get(getScriptName());
        if (scriptReference == null || System.currentTimeMillis() - scriptReference.lastUpdated > ONE_DAY) {
            return false;
        }
        return true;
    }

    private void failedToLoadRemoteScript(int pageNumber) {
        if (this.interpreter != null) {
            CrDb.m0d("universal producer", "could not load script (using assets): " + getScriptFilenameOnServer());
            try {
                putScript(GalleryProducer.readStreamIntoString(this.interpreter.getContext().openFileInput(getScriptFileName())));
                handler.post(new C01122(pageNumber));
            } catch (IOException e1) {
                Analytics.INSTANCE.newException(new Exception("Universal phone home failed: " + getScriptFileName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getHostUrl() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e1.getMessage(), e1));
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                    webviewEncounteredAnException(e1);
                } else {
                    handler.post(new C01133(e1));
                }
            }
        }
    }

    private void deferInjectScriptAndLoadRemote(int pageNumber) {
        CrumbyApp.getHttpClient().newCall(new Builder().url("http://i.getcrumby.com/crumby/" + getScriptFilenameOnServer()).build()).enqueue(new C07384(pageNumber));
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        this.interpreter = UniversalInterpreterManager.INSTANCE.getInterpreter();
        this.interpreter.setInterface(this);
        this.finished = false;
        if (hasLoadedMostCurrentScript()) {
            injectScriptIntoInterpreter(pageNumber);
        } else {
            deferInjectScriptAndLoadRemote(pageNumber);
        }
        return null;
    }

    public String getMatchingId() {
        if (getRegexForMatchingId() != null) {
            return GalleryViewerFragment.matchIdFromUrl(getRegexForMatchingId(), getHostUrl());
        }
        return null;
    }

    private UniversalInterpreterInterface getThis() {
        return this;
    }

    private boolean removeInterpreter() {
        if (this.interpreter == null) {
            return false;
        }
        this.interpreter.removeInterface(getThis());
        this.interpreter = null;
        return true;
    }

    private void webviewEncounteredAnException(Exception e) {
        removeInterpreter();
        handleFetchingException(e, true);
        Analytics.INSTANCE.newException(e);
        onFinishedDownloading(null, true);
    }

    private void checkPageNodeForError(JsonNode node) throws Exception {
        if (node.has("error")) {
            JsonNode errorNode = node.get("error");
            String exceptionMessage = "Crumby could not download and/or parse this page.";
            try {
                int status;
                if (errorNode.get("type").asText().equals("async")) {
                    try {
                        status = node.get("status").asInt(-1);
                        if (status == WalletConstants.ERROR_CODE_INVALID_PARAMETERS) {
                            exceptionMessage = "404, could not find the page you're looking for";
                        } else if (status == 503) {
                            exceptionMessage = "The website has restricted you from seeing this image.";
                        }
                    } catch (NullPointerException e) {
                        exceptionMessage = "Could not connect to website.";
                    }
                } else {
                    status = errorNode.get("status").asInt();
                    if (status == 0) {
                        exceptionMessage = "Crumby could not parse this page.";
                    } else if (status == TIMEOUT) {
                        exceptionMessage = "Crumby took way too long to get this image for you. Sorry :(";
                    }
                }
            } catch (NullPointerException e2) {
                String attemptedErrorMessage = errorNode.asText();
                if (!(attemptedErrorMessage == null || attemptedErrorMessage.equals(UnsupportedUrlFragment.DISPLAY_NAME))) {
                    exceptionMessage = attemptedErrorMessage;
                }
            }
            throw new UniversalProducerException(exceptionMessage);
        }
    }

    protected ArrayList<GalleryImage> getImagesFromJson(JsonNode node) throws Exception {
        GalleryImage hostImage = (GalleryImage) mapper.readValue(node.get("hostImage").toString(), GalleryImage.class);
        if (getHostImage() != null) {
            getHostImage().copy(hostImage);
            getHostImage().updateViews();
        }
        return convertJsonNodeToGalleryImages(node.get("images"));
    }

    @JavascriptInterface
    public void finish(String json) throws Exception {
        if (this.finished) {
            CrDb.m0d("universal producer", "finished! will not accept anymore json!");
            return;
        }
        this.finished = true;
        handler.post(new C01155(json));
    }

    protected void onFinishedDownloading(ArrayList<GalleryImage> galleryImages, boolean updateHostImageViews) {
        removeInterpreter();
        super.onFinishedDownloading(galleryImages, updateHostImageViews);
    }

    protected boolean tryToStopDownload() {
        return removeInterpreter();
    }

    protected ArrayList<GalleryImage> convertJsonNodeToGalleryImages(JsonNode node) throws Exception {
        ArrayList<GalleryImage> galleryImages = new ArrayList();
        int size = node.size();
        for (int i = COULD_NOT_PARSE; i < size; i += TIMEOUT) {
            GalleryImage image = (GalleryImage) mapper.readValue(node.get(i).toString(), GalleryImage.class);
            if (image != null) {
                if (image.getHeight() == 0) {
                    image.setHeight(150);
                }
                galleryImages.add(image);
            }
        }
        return galleryImages;
    }

    protected void fetch() {
        if (this.interpreter != null) {
            CrDb.m0d("crumby producer", getHostUrl() + " IS FETCHING! CANNOT FETCH AGAIN!");
        } else {
            produce();
        }
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            handler.post(new C01166(description, failingUrl));
        } else {
            webviewEncounteredAnException(new Exception(description + "@" + failingUrl));
        }
    }

    public void onInterfaceInvalidated() {
    }
}
