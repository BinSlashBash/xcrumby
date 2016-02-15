package com.tapstream.sdk;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.tapstream.sdk.WorkerThread.Factory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;
import oauth.signpost.OAuth;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import uk.co.senab.photoview.IPhotoView;

class PlatformImpl implements Platform {
    private static final String FIRED_EVENTS_KEY = "TapstreamSDKFiredEvents";
    private static final String UUID_KEY = "TapstreamSDKUUID";
    private Context context;

    public PlatformImpl(Context context) {
        this.context = context;
    }

    public String getAdvertisingId() {
        return this.context.getApplicationContext().getSharedPreferences(UUID_KEY, 0).getString("advertisingId", null);
    }

    public String getAppName() {
        PackageManager packageManager = this.context.getPackageManager();
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.context.getPackageName(), 0)).toString();
        } catch (NameNotFoundException e) {
            return this.context.getPackageName();
        }
    }

    public String getAppVersion() {
        try {
            return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return UnsupportedUrlFragment.DISPLAY_NAME;
        }
    }

    public Boolean getLimitAdTracking() {
        SharedPreferences sharedPreferences = this.context.getApplicationContext().getSharedPreferences(UUID_KEY, 0);
        return sharedPreferences.contains("limitAdTracking") ? Boolean.valueOf(sharedPreferences.getBoolean("limitAdTracking", false)) : null;
    }

    public String getLocale() {
        return Locale.getDefault().toString();
    }

    public String getManufacturer() {
        try {
            return Build.MANUFACTURER;
        } catch (Exception e) {
            return null;
        }
    }

    public String getModel() {
        return Build.MODEL;
    }

    public String getOs() {
        return String.format(Locale.US, "Android %s", new Object[]{VERSION.RELEASE});
    }

    public String getPackageName() {
        return this.context.getPackageName();
    }

    public Set<String> getProcessSet() {
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.context.getSystemService("activity")).getRunningAppProcesses();
        Set<String> hashSet = new HashSet();
        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            hashSet.add(runningAppProcessInfo.processName);
        }
        return hashSet;
    }

    public String getReferrer() {
        return this.context.getApplicationContext().getSharedPreferences(UUID_KEY, 0).getString("referrer", null);
    }

    public String getResolution() {
        ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay().getMetrics(new DisplayMetrics());
        return String.format(Locale.US, "%dx%d", new Object[]{Integer.valueOf(r1.widthPixels), Integer.valueOf(r1.heightPixels)});
    }

    public Set<String> loadFiredEvents() {
        return new HashSet(this.context.getApplicationContext().getSharedPreferences(FIRED_EVENTS_KEY, 0).getAll().keySet());
    }

    public String loadUuid() {
        SharedPreferences sharedPreferences = this.context.getApplicationContext().getSharedPreferences(UUID_KEY, 0);
        String string = sharedPreferences.getString("uuid", null);
        if (string != null) {
            return string;
        }
        string = UUID.randomUUID().toString();
        Editor edit = sharedPreferences.edit();
        edit.putString("uuid", string);
        edit.commit();
        return string;
    }

    public ThreadFactory makeWorkerThreadFactory() {
        return new Factory();
    }

    public Response request(String str, String str2, String str3) {
        HttpUriRequest httpPost;
        InputStream content;
        WorkerThread workerThread = (WorkerThread) Thread.currentThread();
        if (str3 == "POST") {
            httpPost = new HttpPost(str);
            if (str2 != null) {
                try {
                    HttpEntity stringEntity = new StringEntity(str2);
                    stringEntity.setContentType(OAuth.FORM_ENCODED);
                    ((HttpPost) httpPost).setEntity(stringEntity);
                } catch (UnsupportedEncodingException e) {
                    return new Response(-1, e.toString(), null);
                }
            }
        }
        httpPost = new HttpGet(str);
        httpPost.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        try {
            HttpResponse execute = workerThread.client.execute(httpPost);
            StatusLine statusLine = execute.getStatusLine();
            try {
                content = execute.getEntity().getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuilder.append(readLine);
                }
                String stringBuilder2 = stringBuilder.toString();
                content.close();
                return statusLine.getStatusCode() == IPhotoView.DEFAULT_ZOOM_DURATION ? new Response(IPhotoView.DEFAULT_ZOOM_DURATION, null, stringBuilder2) : new Response(statusLine.getStatusCode(), statusLine.getReasonPhrase(), null);
            } catch (Exception e2) {
                return new Response(-1, e2.toString(), null);
            } catch (Throwable th) {
                content.close();
            }
        } catch (Exception e22) {
            return new Response(-1, e22.toString(), null);
        }
    }

    public void saveFiredEvents(Set<String> set) {
        Editor edit = this.context.getApplicationContext().getSharedPreferences(FIRED_EVENTS_KEY, 0).edit();
        edit.clear();
        for (String putString : set) {
            edit.putString(putString, UnsupportedUrlFragment.DISPLAY_NAME);
        }
        edit.commit();
    }
}
