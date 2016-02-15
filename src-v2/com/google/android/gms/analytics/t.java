/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Process
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.a;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.ag;
import com.google.android.gms.analytics.ak;
import com.google.android.gms.analytics.f;
import com.google.android.gms.analytics.g;
import com.google.android.gms.analytics.h;
import com.google.android.gms.analytics.m;
import com.google.android.gms.analytics.s;
import com.google.android.gms.analytics.u;
import com.google.android.gms.analytics.y;
import com.google.android.gms.internal.ef;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

class t
extends Thread
implements f {
    private static t tA;
    private volatile boolean mClosed = false;
    private final Context mContext;
    private volatile String su;
    private volatile ag tB;
    private final LinkedBlockingQueue<Runnable> tw = new LinkedBlockingQueue();
    private volatile boolean tx = false;
    private volatile List<ef> ty;
    private volatile String tz;

    /*
     * Enabled aggressive block sorting
     */
    private t(Context context) {
        super("GAThread");
        this.mContext = context != null ? context.getApplicationContext() : context;
        this.start();
    }

    static int H(String string2) {
        int n2 = 1;
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            int n3 = string2.length();
            int n4 = 0;
            --n3;
            do {
                n2 = n4;
                if (n3 < 0) break;
                n2 = string2.charAt(n3);
                n2 = (n4 << 6 & 268435455) + n2 + (n2 << 14);
                int n5 = 266338304 & n2;
                n4 = n2;
                if (n5 != 0) {
                    n4 = n2 ^ n5 >> 21;
                }
                --n3;
            } while (true);
        }
        return n2;
    }

    private String a(Throwable throwable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        throwable.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    static t q(Context context) {
        if (tA == null) {
            tA = new t(context);
        }
        return tA;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String r(Context context) {
        try {
            Object object = context.openFileInput("gaInstallData");
            byte[] arrby = new byte[8192];
            int n2 = object.read(arrby, 0, 8192);
            if (object.available() > 0) {
                aa.w("Too much campaign data, ignoring it.");
                object.close();
                context.deleteFile("gaInstallData");
                return null;
            }
            object.close();
            context.deleteFile("gaInstallData");
            if (n2 <= 0) {
                aa.z("Campaign file is empty.");
                return null;
            }
            object = new String(arrby, 0, n2);
            aa.x("Campaign found: " + (String)object);
            return object;
        }
        catch (FileNotFoundException var0_1) {
            aa.x("No campaign data found.");
            return null;
        }
        catch (IOException var2_3) {
            aa.w("Error reading campaign data.");
            context.deleteFile("gaInstallData");
            return null;
        }
    }

    private String r(Map<String, String> map) {
        if (map.containsKey("useSecure")) {
            if (ak.d(map.get("useSecure"), true)) {
                return "https:";
            }
            return "http:";
        }
        return "https:";
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean s(Map<String, String> object) {
        if (object.get("&sf") == null) {
            return false;
        }
        double d2 = ak.a((String)object.get("&sf"), 100.0);
        if (d2 >= 100.0) {
            return false;
        }
        if ((double)(t.H((String)object.get("&cid")) % 10000) < d2 * 100.0) {
            return false;
        }
        object = object.get("&t") == null ? "unknown" : (String)object.get("&t");
        aa.y(String.format("%s hit sampled out", object));
        return true;
    }

    private void t(Map<String, String> map) {
        m m2 = a.m(this.mContext);
        ak.a(map, "&adid", m2.getValue("&adid"));
        ak.a(map, "&ate", m2.getValue("&ate"));
    }

    private void u(Map<String, String> map) {
        g g2 = g.ca();
        ak.a(map, "&an", g2.getValue("&an"));
        ak.a(map, "&av", g2.getValue("&av"));
        ak.a(map, "&aid", g2.getValue("&aid"));
        ak.a(map, "&aiid", g2.getValue("&aiid"));
        map.put("&v", "1");
    }

    void a(Runnable runnable) {
        this.tw.add(runnable);
    }

    @Override
    public void bR() {
        this.a(new Runnable(){

            @Override
            public void run() {
                t.this.tB.bR();
            }
        });
    }

    @Override
    public void bW() {
        this.a(new Runnable(){

            @Override
            public void run() {
                t.this.tB.bW();
            }
        });
    }

    @Override
    public void bY() {
        this.a(new Runnable(){

            @Override
            public void run() {
                t.this.tB.bY();
            }
        });
    }

    @Override
    public LinkedBlockingQueue<Runnable> bZ() {
        return this.tw;
    }

    @Override
    public Thread getThread() {
        return this;
    }

    protected void init() {
        this.tB.cp();
        this.ty = new ArrayList<ef>();
        this.ty.add(new ef("appendVersion", "&_v".substring(1), "ma4.0.1"));
        this.ty.add(new ef("appendQueueTime", "&qt".substring(1), null));
        this.ty.add(new ef("appendCacheBuster", "&z".substring(1), null));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void q(Map<String, String> object) {
        String string2;
        final HashMap<String, String> hashMap = new HashMap<String, String>((Map<String, String>)object);
        object = string2 = (String)object.get("&ht");
        if (string2 != null) {
            try {
                Long.valueOf(string2);
                object = string2;
            }
            catch (NumberFormatException var1_2) {
                object = null;
            }
        }
        if (object == null) {
            hashMap.put("&ht", Long.toString(System.currentTimeMillis()));
        }
        this.a(new Runnable(){

            @Override
            public void run() {
                if (TextUtils.isEmpty((CharSequence)((CharSequence)hashMap.get("&cid")))) {
                    hashMap.put("&cid", t.this.su);
                }
                if (GoogleAnalytics.getInstance(t.this.mContext).getAppOptOut() || t.this.s(hashMap)) {
                    return;
                }
                if (!TextUtils.isEmpty((CharSequence)t.this.tz)) {
                    u.cy().t(true);
                    hashMap.putAll(new HitBuilders.HitBuilder().setCampaignParamsFromUrl(t.this.tz).build());
                    u.cy().t(false);
                    t.this.tz = null;
                }
                t.this.u(hashMap);
                t.this.t(hashMap);
                Map<String, String> map = y.v(hashMap);
                t.this.tB.b(map, Long.valueOf((String)hashMap.get("&ht")), t.this.r(hashMap), t.this.ty);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Process.setThreadPriority((int)10);
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException var1_1) {
            aa.z("sleep interrupted in GAThread initialize");
        }
        try {
            if (this.tB == null) {
                this.tB = new s(this.mContext, this);
            }
            this.init();
            this.su = h.cb().getValue("&cid");
            if (this.su == null) {
                this.tx = true;
            }
            this.tz = t.r(this.mContext);
            aa.y("Initialized GA Thread");
        }
        catch (Throwable var1_5) {
            aa.w("Error initializing the GAThread: " + this.a(var1_5));
            aa.w("Google Analytics will not start up.");
            this.tx = true;
        }
        while (!this.mClosed) {
            try {
                try {
                    Runnable runnable = this.tw.take();
                    if (this.tx) continue;
                    runnable.run();
                }
                catch (InterruptedException var1_3) {
                    aa.x(var1_3.toString());
                }
                continue;
            }
            catch (Throwable var1_4) {
                aa.w("Error on GAThread: " + this.a(var1_4));
                aa.w("Google Analytics is shutting down.");
                this.tx = true;
                continue;
            }
            break;
        }
        return;
    }

}

