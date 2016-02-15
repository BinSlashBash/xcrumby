/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 */
package com.crumby.lib.universal;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.crumby.CrDb;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalInterpreter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public enum UniversalInterpreterManager {
    INSTANCE;
    
    public static String LIBRARY_JS;
    private int MAX_WEBVIEWS_ON_LOAD = 6;
    private Context context;
    private Queue<UniversalInterpreter> interpreters;

    private UniversalInterpreterManager() {
    }

    private void addNewInterpreter() {
        CrDb.d("universal producer", "Adding New interpreter");
        UniversalInterpreter universalInterpreter = new UniversalInterpreter(this.context);
        WebSettings webSettings = universalInterpreter.getSettings();
        if (Build.VERSION.SDK_INT >= 16) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            if (Build.VERSION.SDK_INT >= 19) {
                WebView.setWebContentsDebuggingEnabled((boolean)true);
            }
        }
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        this.interpreters.add(universalInterpreter);
    }

    public UniversalInterpreter getInterpreter() {
        UniversalInterpreter universalInterpreter = this.interpreters.remove();
        this.interpreters.add(universalInterpreter);
        universalInterpreter.forceInvalidateInterface();
        return universalInterpreter;
    }

    public String getLibraryJS() {
        return LIBRARY_JS;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void initialize(Context context) {
        this.context = context;
        try {
            LIBRARY_JS = GalleryProducer.readStreamIntoString(context.openFileInput("library.min.js"));
        }
        catch (IOException var1_2) {
            var1_2.printStackTrace();
        }
        this.interpreters = new ArrayDeque<UniversalInterpreter>();
        int n2 = 0;
        while (n2 < this.MAX_WEBVIEWS_ON_LOAD) {
            this.addNewInterpreter();
            ++n2;
        }
    }
}

