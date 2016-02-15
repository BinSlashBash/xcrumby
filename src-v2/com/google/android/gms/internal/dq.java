/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.net.UrlQuerySanitizer
 *  android.net.UrlQuerySanitizer$ParameterValuePair
 *  android.net.UrlQuerySanitizer$ValueSanitizer
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 */
package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.android.gms.internal.ds;
import com.google.android.gms.internal.dt;
import com.google.android.gms.internal.du;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dw;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class dq {
    private static final Object px = new Object();
    private static boolean re = true;
    private static String rf;
    private static boolean rg;

    static {
        rg = false;
    }

    public static String a(Readable readable) throws IOException {
        int n2;
        StringBuilder stringBuilder = new StringBuilder();
        CharBuffer charBuffer = CharBuffer.allocate(2048);
        while ((n2 = readable.read(charBuffer)) != -1) {
            charBuffer.flip();
            stringBuilder.append(charBuffer, 0, n2);
        }
        return stringBuilder.toString();
    }

    private static JSONArray a(Collection<?> object) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        object = object.iterator();
        while (object.hasNext()) {
            dq.a(jSONArray, object.next());
        }
        return jSONArray;
    }

    static JSONArray a(Object[] arrobject) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        int n2 = arrobject.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            dq.a(jSONArray, arrobject[i2]);
        }
        return jSONArray;
    }

    private static JSONObject a(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String string2 : bundle.keySet()) {
            dq.a(jSONObject, string2, bundle.get(string2));
        }
        return jSONObject;
    }

    public static void a(Context context, String string2, WebSettings webSettings) {
        webSettings.setUserAgentString(dq.b(context, string2));
    }

    public static void a(Context context, String string2, List<String> object) {
        object = object.iterator();
        while (object.hasNext()) {
            new du(context, string2, (String)object.next()).start();
        }
    }

    public static void a(Context context, String string2, boolean bl2, HttpURLConnection httpURLConnection) {
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setInstanceFollowRedirects(bl2);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setRequestProperty("User-Agent", dq.b(context, string2));
        httpURLConnection.setUseCaches(false);
    }

    public static void a(WebView webView) {
        if (Build.VERSION.SDK_INT >= 11) {
            ds.a(webView);
        }
    }

    private static void a(JSONArray jSONArray, Object object) throws JSONException {
        if (object instanceof Bundle) {
            jSONArray.put(dq.a((Bundle)object));
            return;
        }
        if (object instanceof Map) {
            jSONArray.put(dq.p((Map)object));
            return;
        }
        if (object instanceof Collection) {
            jSONArray.put(dq.a((Collection)object));
            return;
        }
        if (object instanceof Object[]) {
            jSONArray.put(dq.a((Object[])object));
            return;
        }
        jSONArray.put(object);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void a(JSONObject jSONObject, String string2, Object object) throws JSONException {
        if (object instanceof Bundle) {
            jSONObject.put(string2, dq.a((Bundle)object));
            return;
        }
        if (object instanceof Map) {
            jSONObject.put(string2, dq.p((Map)object));
            return;
        }
        if (object instanceof Collection) {
            if (string2 == null) {
                string2 = "null";
            }
            jSONObject.put(string2, dq.a((Collection)object));
            return;
        }
        if (object instanceof Object[]) {
            jSONObject.put(string2, dq.a(Arrays.asList((Object[])object)));
            return;
        }
        jSONObject.put(string2, object);
    }

    public static boolean a(PackageManager packageManager, String string2, String string3) {
        if (packageManager.checkPermission(string3, string2) == 0) {
            return true;
        }
        return false;
    }

    public static boolean a(ClassLoader classLoader, Class<?> class_, String string2) {
        try {
            boolean bl2 = class_.isAssignableFrom(Class.forName(string2, false, classLoader));
            return bl2;
        }
        catch (Throwable var0_1) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String b(Context object, String string2) {
        Object object2 = px;
        synchronized (object2) {
            if (rf != null) {
                return rf;
            }
            if (Build.VERSION.SDK_INT >= 17) {
                rf = dt.getDefaultUserAgent((Context)object);
                return dq.rf = rf + " (Mobile; " + string2 + ")";
            }
            if (dv.bD()) {
                rf = dq.j((Context)object);
                return dq.rf = rf + " (Mobile; " + string2 + ")";
            }
            dv.rp.post(new Runnable((Context)object){
                final /* synthetic */ Context pB;

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    Object object = px;
                    synchronized (object) {
                        rf = dq.j(this.pB);
                        px.notifyAll();
                        return;
                    }
                }
            });
            while ((object = rf) == null) {
                try {
                    px.wait();
                    continue;
                }
                catch (InterruptedException var0_1) {}
                return rf;
            }
            return dq.rf = rf + " (Mobile; " + string2 + ")";
        }
    }

    public static Map<String, String> b(Uri object) {
        if (object == null) {
            return null;
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        UrlQuerySanitizer urlQuerySanitizer2 = new UrlQuerySanitizer();
        urlQuerySanitizer2.setAllowUnregisteredParamaters(true);
        urlQuerySanitizer2.setUnregisteredParameterValueSanitizer(UrlQuerySanitizer.getAllButNulLegal());
        urlQuerySanitizer2.parseUrl(object.toString());
        for (UrlQuerySanitizer urlQuerySanitizer2 : urlQuerySanitizer2.getParameterList()) {
            hashMap.put(urlQuerySanitizer2.mParameter, urlQuerySanitizer2.mValue);
        }
        return hashMap;
    }

    public static void b(WebView webView) {
        if (Build.VERSION.SDK_INT >= 11) {
            ds.b(webView);
        }
    }

    public static int bA() {
        if (Build.VERSION.SDK_INT >= 9) {
            return 7;
        }
        return 1;
    }

    public static boolean by() {
        return re;
    }

    public static int bz() {
        if (Build.VERSION.SDK_INT >= 9) {
            return 6;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean h(Context context) {
        boolean bl2;
        Intent intent = new Intent();
        intent.setClassName(context, "com.google.android.gms.ads.AdActivity");
        context = context.getPackageManager().resolveActivity(intent, 65536);
        if (context == null || context.activityInfo == null) {
            dw.z("Could not find com.google.android.gms.ads.AdActivity, please make sure it is declared in AndroidManifest.xml.");
            return false;
        }
        if ((context.activityInfo.configChanges & 16) == 0) {
            dw.z(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "keyboard"));
            bl2 = false;
        } else {
            bl2 = true;
        }
        if ((context.activityInfo.configChanges & 32) == 0) {
            dw.z(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "keyboardHidden"));
            bl2 = false;
        }
        if ((context.activityInfo.configChanges & 128) == 0) {
            dw.z(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "orientation"));
            bl2 = false;
        }
        if ((context.activityInfo.configChanges & 256) == 0) {
            dw.z(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "screenLayout"));
            bl2 = false;
        }
        if ((context.activityInfo.configChanges & 512) == 0) {
            dw.z(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "uiMode"));
            bl2 = false;
        }
        if ((context.activityInfo.configChanges & 1024) == 0) {
            dw.z(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "screenSize"));
            bl2 = false;
        }
        if ((context.activityInfo.configChanges & 2048) == 0) {
            dw.z(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "smallestScreenSize"));
            return false;
        }
        return bl2;
    }

    public static void i(Context context) {
        if (rg) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.getApplicationContext().registerReceiver((BroadcastReceiver)new a(), intentFilter);
        rg = true;
    }

    private static String j(Context context) {
        return new WebView(context).getSettings().getUserAgentString();
    }

    public static JSONObject p(Map<String, ?> map) throws JSONException {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            for (String string2 : map.keySet()) {
                dq.a(jSONObject, string2, map.get(string2));
            }
        }
        catch (ClassCastException var0_1) {
            throw new JSONException("Could not convert map to JSON: " + var0_1.getMessage());
        }
        return jSONObject;
    }

    public static String r(String string2) {
        return Uri.parse((String)string2).buildUpon().query(null).build().toString();
    }

    private static final class a
    extends BroadcastReceiver {
        private a() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                re = true;
                return;
            } else {
                if (!"android.intent.action.SCREEN_OFF".equals(intent.getAction())) return;
                {
                    re = false;
                    return;
                }
            }
        }
    }

}

