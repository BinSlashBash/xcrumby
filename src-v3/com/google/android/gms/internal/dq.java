package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.net.UrlQuerySanitizer.ParameterValuePair;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.android.gms.ads.AdActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.cast.Cast;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.zip.JSONzip;

public final class dq {
    private static final Object px;
    private static boolean re;
    private static String rf;
    private static boolean rg;

    /* renamed from: com.google.android.gms.internal.dq.1 */
    static class C03621 implements Runnable {
        final /* synthetic */ Context pB;

        C03621(Context context) {
            this.pB = context;
        }

        public void run() {
            synchronized (dq.px) {
                dq.rf = dq.m791j(this.pB);
                dq.px.notifyAll();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.dq.a */
    private static final class C0363a extends BroadcastReceiver {
        private C0363a() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                dq.re = true;
            } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                dq.re = false;
            }
        }
    }

    static {
        px = new Object();
        re = true;
        rg = false;
    }

    public static String m774a(Readable readable) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        CharSequence allocate = CharBuffer.allocate(AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT);
        while (true) {
            int read = readable.read(allocate);
            if (read == -1) {
                return stringBuilder.toString();
            }
            allocate.flip();
            stringBuilder.append(allocate, 0, read);
        }
    }

    private static JSONArray m775a(Collection<?> collection) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Object a : collection) {
            m782a(jSONArray, a);
        }
        return jSONArray;
    }

    static JSONArray m776a(Object[] objArr) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Object a : objArr) {
            m782a(jSONArray, a);
        }
        return jSONArray;
    }

    private static JSONObject m777a(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            m783a(jSONObject, str, bundle.get(str));
        }
        return jSONObject;
    }

    public static void m778a(Context context, String str, WebSettings webSettings) {
        webSettings.setUserAgentString(m786b(context, str));
    }

    public static void m779a(Context context, String str, List<String> list) {
        for (String duVar : list) {
            new du(context, str, duVar).start();
        }
    }

    public static void m780a(Context context, String str, boolean z, HttpURLConnection httpURLConnection) {
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setInstanceFollowRedirects(z);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setRequestProperty("User-Agent", m786b(context, str));
        httpURLConnection.setUseCaches(false);
    }

    public static void m781a(WebView webView) {
        if (VERSION.SDK_INT >= 11) {
            ds.m803a(webView);
        }
    }

    private static void m782a(JSONArray jSONArray, Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            jSONArray.put(m777a((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONArray.put(m794p((Map) obj));
        } else if (obj instanceof Collection) {
            jSONArray.put(m775a((Collection) obj));
        } else if (obj instanceof Object[]) {
            jSONArray.put(m776a((Object[]) obj));
        } else {
            jSONArray.put(obj);
        }
    }

    private static void m783a(JSONObject jSONObject, String str, Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            jSONObject.put(str, m777a((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONObject.put(str, m794p((Map) obj));
        } else if (obj instanceof Collection) {
            if (str == null) {
                str = "null";
            }
            jSONObject.put(str, m775a((Collection) obj));
        } else if (obj instanceof Object[]) {
            jSONObject.put(str, m775a(Arrays.asList((Object[]) obj)));
        } else {
            jSONObject.put(str, obj);
        }
    }

    public static boolean m784a(PackageManager packageManager, String str, String str2) {
        return packageManager.checkPermission(str2, str) == 0;
    }

    public static boolean m785a(ClassLoader classLoader, Class<?> cls, String str) {
        boolean z = false;
        try {
            z = cls.isAssignableFrom(Class.forName(str, false, classLoader));
        } catch (Throwable th) {
        }
        return z;
    }

    private static String m786b(Context context, String str) {
        String str2;
        synchronized (px) {
            if (rf != null) {
                str2 = rf;
            } else {
                if (VERSION.SDK_INT >= 17) {
                    rf = dt.getDefaultUserAgent(context);
                } else if (dv.bD()) {
                    rf = m791j(context);
                } else {
                    dv.rp.post(new C03621(context));
                    while (rf == null) {
                        try {
                            px.wait();
                        } catch (InterruptedException e) {
                            str2 = rf;
                        }
                    }
                }
                rf += " (Mobile; " + str + ")";
                str2 = rf;
            }
        }
        return str2;
    }

    public static Map<String, String> m787b(Uri uri) {
        if (uri == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        UrlQuerySanitizer urlQuerySanitizer = new UrlQuerySanitizer();
        urlQuerySanitizer.setAllowUnregisteredParamaters(true);
        urlQuerySanitizer.setUnregisteredParameterValueSanitizer(UrlQuerySanitizer.getAllButNulLegal());
        urlQuerySanitizer.parseUrl(uri.toString());
        for (ParameterValuePair parameterValuePair : urlQuerySanitizer.getParameterList()) {
            hashMap.put(parameterValuePair.mParameter, parameterValuePair.mValue);
        }
        return hashMap;
    }

    public static void m788b(WebView webView) {
        if (VERSION.SDK_INT >= 11) {
            ds.m804b(webView);
        }
    }

    public static int bA() {
        return VERSION.SDK_INT >= 9 ? 7 : 1;
    }

    public static boolean by() {
        return re;
    }

    public static int bz() {
        return VERSION.SDK_INT >= 9 ? 6 : 0;
    }

    public static boolean m789h(Context context) {
        Intent intent = new Intent();
        intent.setClassName(context, AdActivity.CLASS_NAME);
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, Cast.MAX_MESSAGE_LENGTH);
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            dw.m823z("Could not find com.google.android.gms.ads.AdActivity, please make sure it is declared in AndroidManifest.xml.");
            return false;
        }
        boolean z;
        String str = "com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".";
        if ((resolveActivity.activityInfo.configChanges & 16) == 0) {
            dw.m823z(String.format(str, new Object[]{"keyboard"}));
            z = false;
        } else {
            z = true;
        }
        if ((resolveActivity.activityInfo.configChanges & 32) == 0) {
            dw.m823z(String.format(str, new Object[]{"keyboardHidden"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & TransportMediator.FLAG_KEY_MEDIA_NEXT) == 0) {
            dw.m823z(String.format(str, new Object[]{"orientation"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & JSONzip.end) == 0) {
            dw.m823z(String.format(str, new Object[]{"screenLayout"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & AdRequest.MAX_CONTENT_URL_LENGTH) == 0) {
            dw.m823z(String.format(str, new Object[]{"uiMode"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT) == 0) {
            dw.m823z(String.format(str, new Object[]{"screenSize"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT) != 0) {
            return z;
        }
        dw.m823z(String.format(str, new Object[]{"smallestScreenSize"}));
        return false;
    }

    public static void m790i(Context context) {
        if (!rg) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            context.getApplicationContext().registerReceiver(new C0363a(), intentFilter);
            rg = true;
        }
    }

    private static String m791j(Context context) {
        return new WebView(context).getSettings().getUserAgentString();
    }

    public static JSONObject m794p(Map<String, ?> map) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            for (String str : map.keySet()) {
                m783a(jSONObject, str, map.get(str));
            }
            return jSONObject;
        } catch (ClassCastException e) {
            throw new JSONException("Could not convert map to JSON: " + e.getMessage());
        }
    }

    public static String m795r(String str) {
        return Uri.parse(str).buildUpon().query(null).build().toString();
    }
}
