package com.crumby.lib.universal;

import android.content.Context;
import android.os.Build.VERSION;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.crumby.CrDb;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public enum UniversalInterpreterManager {
    INSTANCE;
    
    public static String LIBRARY_JS;
    private int MAX_WEBVIEWS_ON_LOAD;
    private Context context;
    private Queue<UniversalInterpreter> interpreters;

    public void initialize(Context context) {
        this.context = context;
        try {
            LIBRARY_JS = GalleryProducer.readStreamIntoString(context.openFileInput("library.min.js"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.interpreters = new ArrayDeque();
        for (int i = 0; i < this.MAX_WEBVIEWS_ON_LOAD; i++) {
            addNewInterpreter();
        }
    }

    public UniversalInterpreter getInterpreter() {
        UniversalInterpreter interpreter = (UniversalInterpreter) this.interpreters.remove();
        this.interpreters.add(interpreter);
        interpreter.forceInvalidateInterface();
        return interpreter;
    }

    private void addNewInterpreter() {
        CrDb.m0d("universal producer", "Adding New interpreter");
        UniversalInterpreter webView = new UniversalInterpreter(this.context);
        WebSettings webSettings = webView.getSettings();
        if (VERSION.SDK_INT >= 16) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            if (VERSION.SDK_INT >= 19) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        this.interpreters.add(webView);
    }

    public String getLibraryJS() {
        return LIBRARY_JS;
    }
}
