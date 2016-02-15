/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.webkit.JavascriptInterface
 *  android.webkit.WebView
 */
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
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalInterpreter;
import com.crumby.lib.universal.UniversalInterpreterInterface;
import com.crumby.lib.universal.UniversalInterpreterManager;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class UniversalProducer
extends GalleryProducer
implements UniversalInterpreterInterface {
    private static final int COULD_NOT_PARSE = 0;
    private static final long ONE_DAY = 86400000;
    private static final int TIMEOUT = 1;
    private static Handler handler;
    protected static ObjectMapper mapper;
    private static Map<String, ScriptReference> scripts;
    private boolean finished;
    private UniversalInterpreter interpreter;

    static {
        scripts = new HashMap<String, ScriptReference>();
        mapper = new ObjectMapper();
        handler = new Handler(){

            public void handleMessage(Message message) {
                super.handleMessage(message);
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void checkPageNodeForError(JsonNode object) throws Exception {
        Object object2;
        String string2;
        block8 : {
            if (!object.has("error")) {
                return;
            }
            object2 = object.get("error");
            string2 = "Crumby could not download and/or parse this page.";
            try {
                boolean bl2 = object2.get("type").asText().equals("async");
                if (!bl2) break block8;
            }
            catch (NullPointerException var1_3) {
                object2 = object2.asText();
                object = string2;
                if (object2 == null) throw new UniversalProducerException((String)object);
                object = string2;
                if (object2.equals("")) throw new UniversalProducerException((String)object);
                object = object2;
                throw new UniversalProducerException((String)object);
            }
            try {
                int n2 = object.get("status").asInt(-1);
                if (n2 == 404) {
                    object = "404, could not find the page you're looking for";
                    throw new UniversalProducerException((String)object);
                }
                object = string2;
                if (n2 != 503) throw new UniversalProducerException((String)object);
                object = "The website has restricted you from seeing this image.";
                throw new UniversalProducerException((String)object);
            }
            catch (NullPointerException var1_2) {
                object = "Could not connect to website.";
                throw new UniversalProducerException((String)object);
            }
        }
        int n3 = object2.get("status").asInt();
        if (n3 == 0) {
            object = "Crumby could not parse this page.";
            throw new UniversalProducerException((String)object);
        }
        object = string2;
        if (n3 != 1) throw new UniversalProducerException((String)object);
        object = "Crumby took way too long to get this image for you. Sorry :(";
        throw new UniversalProducerException((String)object);
    }

    private void deferInjectScriptAndLoadRemote(final int n2) {
        CrumbyApp.getHttpClient().newCall(new Request.Builder().url("http://i.getcrumby.com/crumby/" + this.getScriptFilenameOnServer()).build()).enqueue(new Callback(){

            @Override
            public void onFailure(Request request, IOException iOException) {
                UniversalProducer.this.failedToLoadRemoteScript(n2);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (UniversalProducer.this.interpreter == null) {
                    return;
                }
                if (!response.isSuccessful()) {
                    UniversalProducer.this.failedToLoadRemoteScript(n2);
                    return;
                }
                Analytics.INSTANCE.newEvent(AnalyticsCategories.UNIVERSAL, "phone home", UniversalProducer.this.getScriptFileName() + " " + UniversalProducer.this.getHostUrl());
                CrDb.d("universal producer", "loaded script from server! " + UniversalProducer.this.getScriptFilenameOnServer());
                UniversalProducer.this.saveScript(UniversalProducer.this.interpreter.getContext(), response.body().string());
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        UniversalProducer.this.injectScriptIntoInterpreter(n2);
                    }
                });
            }

        });
    }

    private void failedToLoadRemoteScript(final int n2) {
        if (this.interpreter == null) {
            return;
        }
        CrDb.d("universal producer", "could not load script (using assets): " + this.getScriptFilenameOnServer());
        try {
            this.putScript(GalleryProducer.readStreamIntoString(this.interpreter.getContext().openFileInput(this.getScriptFileName())));
            handler.post(new Runnable(){

                @Override
                public void run() {
                    UniversalProducer.this.injectScriptIntoInterpreter(n2);
                }
            });
            return;
        }
        catch (IOException var2_2) {
            Analytics.INSTANCE.newException(new Exception("Universal phone home failed: " + this.getScriptFileName() + " " + this.getHostUrl() + " " + var2_2.getMessage(), var2_2));
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                this.webviewEncounteredAnException(var2_2);
                return;
            }
            handler.post(new Runnable(){

                @Override
                public void run() {
                    UniversalProducer.this.webviewEncounteredAnException(var2_2);
                }
            });
            return;
        }
    }

    private String getScriptFileName() {
        return this.getScriptName() + ".min.js";
    }

    private String getScriptFilenameOnServer() {
        return "js/" + "45/" + this.getScriptFileName();
    }

    private UniversalInterpreterInterface getThis() {
        return this;
    }

    private boolean hasLoadedMostCurrentScript() {
        ScriptReference scriptReference = scripts.get(this.getScriptName());
        if (scriptReference == null || System.currentTimeMillis() - scriptReference.lastUpdated > 86400000) {
            return false;
        }
        return true;
    }

    private void injectScriptIntoInterpreter(int n2) {
        String string2;
        String string3 = UniversalProducer.scripts.get((Object)this.getScriptName()).script;
        if (string3 == null) {
            this.webviewEncounteredAnException(new Exception("Uh oh, some of Crumby's files got corrupted. Please restart your app!"));
        }
        if (this.interpreter == null) {
            return;
        }
        String string4 = string2 = this.getMatchingId();
        if (string2 != null) {
            string4 = "'" + string2 + "'";
        }
        string4 = "var matchedId = " + string4 + ";";
        this.interpreter.loadDataWithBaseURL(this.getBaseUrl(), "<html><body><iframe id='haxx'></iframe><div id='haxx1'></div>\n<script type='text/javascript'>" + UniversalInterpreterManager.INSTANCE.getLibraryJS() + "</script>" + "<script type='text/javascript'>" + "var pageNumber = " + n2 + "; " + "var hostUrl = '" + this.getHostUrl() + "'; " + string4 + " " + this.getExtraScript() + " " + "</script>" + "<script type='text/javascript'>" + string3 + "</script>" + "</body>\n" + "</html>", "text/html", "utf-8", null);
    }

    private void putScript(String string2) {
        scripts.put(this.getScriptName(), new ScriptReference(string2, System.currentTimeMillis()));
    }

    private boolean removeInterpreter() {
        if (this.interpreter != null) {
            this.interpreter.removeInterface(this.getThis());
            this.interpreter = null;
            return true;
        }
        return false;
    }

    private void saveScript(Context context, String string2) throws IOException {
        context.openFileOutput(this.getScriptFileName(), 0).write(string2.getBytes());
        this.putScript(string2);
    }

    private void webviewEncounteredAnException(Exception exception) {
        this.removeInterpreter();
        this.handleFetchingException(exception, true);
        Analytics.INSTANCE.newException(exception);
        this.onFinishedDownloading(null, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected ArrayList<GalleryImage> convertJsonNodeToGalleryImages(JsonNode jsonNode) throws Exception {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        int n2 = 0;
        int n3 = jsonNode.size();
        while (n2 < n3) {
            GalleryImage galleryImage = (GalleryImage)mapper.readValue(jsonNode.get(n2).toString(), GalleryImage.class);
            if (galleryImage != null) {
                if (galleryImage.getHeight() == 0) {
                    galleryImage.setHeight(150);
                }
                arrayList.add(galleryImage);
            }
            ++n2;
        }
        return arrayList;
    }

    @Override
    protected void fetch() {
        if (this.interpreter != null) {
            CrDb.d("crumby producer", this.getHostUrl() + " IS FETCHING! CANNOT FETCH AGAIN!");
            return;
        }
        this.produce();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        this.interpreter = UniversalInterpreterManager.INSTANCE.getInterpreter();
        this.interpreter.setInterface(this);
        this.finished = false;
        if (this.hasLoadedMostCurrentScript()) {
            this.injectScriptIntoInterpreter(n2);
            do {
                return null;
                break;
            } while (true);
        }
        this.deferInjectScriptAndLoadRemote(n2);
        return null;
    }

    @JavascriptInterface
    public void finish(final String string2) throws Exception {
        if (this.finished) {
            CrDb.d("universal producer", "finished! will not accept anymore json!");
            return;
        }
        this.finished = true;
        handler.post(new Runnable(){

            @Override
            public void run() {
                try {
                    Iterable iterable = UniversalProducer.mapper.readTree(string2);
                    UniversalProducer.this.checkPageNodeForError(iterable);
                    iterable = UniversalProducer.this.getImagesFromJson((JsonNode)iterable);
                    UniversalProducer.this.onFinishedDownloading((ArrayList<GalleryImage>)iterable, false);
                    return;
                }
                catch (Exception var1_2) {
                    UniversalProducer.this.webviewEncounteredAnException(var1_2);
                    return;
                }
            }
        });
    }

    protected abstract String getBaseUrl();

    protected String getExtraScript() {
        return "";
    }

    protected ArrayList<GalleryImage> getImagesFromJson(JsonNode jsonNode) throws Exception {
        GalleryImage galleryImage = (GalleryImage)mapper.readValue(jsonNode.get("hostImage").toString(), GalleryImage.class);
        if (this.getHostImage() != null) {
            this.getHostImage().copy(galleryImage);
            this.getHostImage().updateViews();
        }
        return this.convertJsonNodeToGalleryImages((JsonNode)jsonNode.get("images"));
    }

    public String getMatchingId() {
        String string2 = null;
        if (this.getRegexForMatchingId() != null) {
            string2 = GalleryViewerFragment.matchIdFromUrl(this.getRegexForMatchingId(), this.getHostUrl());
        }
        return string2;
    }

    protected abstract String getRegexForMatchingId();

    protected abstract String getScriptName();

    @Override
    protected void onFinishedDownloading(ArrayList<GalleryImage> arrayList, boolean bl2) {
        this.removeInterpreter();
        super.onFinishedDownloading(arrayList, bl2);
    }

    @Override
    public void onInterfaceInvalidated() {
    }

    @Override
    public void onReceivedError(WebView webView, int n2, final String string2, final String string3) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            handler.post(new Runnable(){

                @Override
                public void run() {
                    UniversalProducer.this.webviewEncounteredAnException(new Exception(string2 + "@" + string3));
                }
            });
            return;
        }
        this.webviewEncounteredAnException(new Exception(string2 + "@" + string3));
    }

    @Override
    protected boolean tryToStopDownload() {
        return this.removeInterpreter();
    }

    class ScriptReference {
        long lastUpdated;
        String script;

        public ScriptReference(String string2, long l2) {
            this.script = string2;
            this.lastUpdated = l2;
        }
    }

    private class UniversalProducerException
    extends Exception {
        public UniversalProducerException(String string2) {
            super(string2);
        }

        @Override
        public String toString() {
            return this.getMessage();
        }
    }

}

