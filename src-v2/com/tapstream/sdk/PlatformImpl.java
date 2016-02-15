/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.WindowManager
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.entity.StringEntity
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.params.HttpParams
 */
package com.tapstream.sdk;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.tapstream.sdk.Platform;
import com.tapstream.sdk.Response;
import com.tapstream.sdk.WorkerThread;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

class PlatformImpl
implements Platform {
    private static final String FIRED_EVENTS_KEY = "TapstreamSDKFiredEvents";
    private static final String UUID_KEY = "TapstreamSDKUUID";
    private Context context;

    public PlatformImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getAdvertisingId() {
        return this.context.getApplicationContext().getSharedPreferences("TapstreamSDKUUID", 0).getString("advertisingId", null);
    }

    @Override
    public String getAppName() {
        Object object = this.context.getPackageManager();
        try {
            object = object.getApplicationLabel(object.getApplicationInfo(this.context.getPackageName(), 0)).toString();
            return object;
        }
        catch (PackageManager.NameNotFoundException var1_2) {
            return this.context.getPackageName();
        }
    }

    @Override
    public String getAppVersion() {
        Object object = this.context.getPackageManager();
        try {
            object = object.getPackageInfo((String)this.context.getPackageName(), (int)0).versionName;
            return object;
        }
        catch (PackageManager.NameNotFoundException var1_2) {
            return "";
        }
    }

    @Override
    public Boolean getLimitAdTracking() {
        SharedPreferences sharedPreferences = this.context.getApplicationContext().getSharedPreferences("TapstreamSDKUUID", 0);
        if (sharedPreferences.contains("limitAdTracking")) {
            return sharedPreferences.getBoolean("limitAdTracking", false);
        }
        return null;
    }

    @Override
    public String getLocale() {
        return Locale.getDefault().toString();
    }

    @Override
    public String getManufacturer() {
        try {
            String string2 = Build.MANUFACTURER;
            return string2;
        }
        catch (Exception var1_2) {
            return null;
        }
    }

    @Override
    public String getModel() {
        return Build.MODEL;
    }

    @Override
    public String getOs() {
        return String.format(Locale.US, "Android %s", Build.VERSION.RELEASE);
    }

    @Override
    public String getPackageName() {
        return this.context.getPackageName();
    }

    @Override
    public Set<String> getProcessSet() {
        Object object = ((ActivityManager)this.context.getSystemService("activity")).getRunningAppProcesses();
        HashSet<String> hashSet = new HashSet<String>();
        object = object.iterator();
        while (object.hasNext()) {
            hashSet.add(((ActivityManager.RunningAppProcessInfo)object.next()).processName);
        }
        return hashSet;
    }

    @Override
    public String getReferrer() {
        return this.context.getApplicationContext().getSharedPreferences("TapstreamSDKUUID", 0).getString("referrer", null);
    }

    @Override
    public String getResolution() {
        Display display = ((WindowManager)this.context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return String.format(Locale.US, "%dx%d", displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    @Override
    public Set<String> loadFiredEvents() {
        return new HashSet<String>(this.context.getApplicationContext().getSharedPreferences("TapstreamSDKFiredEvents", 0).getAll().keySet());
    }

    @Override
    public String loadUuid() {
        String string2;
        SharedPreferences sharedPreferences = this.context.getApplicationContext().getSharedPreferences("TapstreamSDKUUID", 0);
        String string3 = string2 = sharedPreferences.getString("uuid", null);
        if (string2 == null) {
            string3 = UUID.randomUUID().toString();
            string2 = sharedPreferences.edit();
            string2.putString("uuid", string3);
            string2.commit();
        }
        return string3;
    }

    @Override
    public ThreadFactory makeWorkerThreadFactory() {
        return new WorkerThread.Factory();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Response request(String var1_1, String var2_6, String var3_7) {
        block12 : {
            var4_8 = (WorkerThread)Thread.currentThread();
            if (var3_7 != "POST") ** GOTO lbl14
            var1_1 = var3_7 = new HttpPost((String)var1_1);
            if (var2_6 == null) ** GOTO lbl15
            try {
                var1_1 = new StringEntity((String)var2_6);
            }
            catch (UnsupportedEncodingException var1_4) {
                return new Response(-1, var1_4.toString(), null);
            }
            var1_1.setContentType("application/x-www-form-urlencoded");
            ((HttpPost)var3_7).setEntity((HttpEntity)var1_1);
            var1_1 = var3_7;
            ** GOTO lbl15
lbl14: // 1 sources:
            var1_1 = new HttpGet((String)var1_1);
lbl15: // 3 sources:
            var1_1.getParams().setBooleanParameter("http.protocol.expect-continue", false);
            var2_6 = var4_8.client.execute((HttpUriRequest)var1_1);
            var1_1 = var2_6.getStatusLine();
            var2_6 = var2_6.getEntity().getContent();
            var3_7 = new BufferedReader(new InputStreamReader((InputStream)var2_6));
            var4_8 = new StringBuilder();
            while ((var5_9 = var3_7.readLine()) != null) {
                var4_8.append(var5_9);
            }
            break block12;
            catch (Exception var1_5) {
                return new Response(-1, var1_5.toString(), null);
            }
        }
        try {
            var3_7 = var4_8.toString();
            ** GOTO lbl41
        }
        catch (Throwable var1_2) {
            try {
                var2_6.close();
                throw var1_2;
            }
            catch (Exception var1_3) {
                return new Response(-1, var1_3.toString(), null);
            }
lbl41: // 2 sources:
            var2_6.close();
        }
        if (var1_1.getStatusCode() != 200) return new Response(var1_1.getStatusCode(), var1_1.getReasonPhrase(), null);
        return new Response(200, null, (String)var3_7);
    }

    @Override
    public void saveFiredEvents(Set<String> object) {
        SharedPreferences.Editor editor = this.context.getApplicationContext().getSharedPreferences("TapstreamSDKFiredEvents", 0).edit();
        editor.clear();
        object = object.iterator();
        while (object.hasNext()) {
            editor.putString((String)object.next(), "");
        }
        editor.commit();
    }
}

