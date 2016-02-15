package com.google.android.gms.analytics;

import android.content.Context;
import android.os.Process;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.TextUtils;
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

/* renamed from: com.google.android.gms.analytics.t */
class C0777t extends Thread implements C0208f {
    private static C0777t tA;
    private volatile boolean mClosed;
    private final Context mContext;
    private volatile String su;
    private volatile ag tB;
    private final LinkedBlockingQueue<Runnable> tw;
    private volatile boolean tx;
    private volatile List<ef> ty;
    private volatile String tz;

    /* renamed from: com.google.android.gms.analytics.t.1 */
    class C02271 implements Runnable {
        final /* synthetic */ Map tC;
        final /* synthetic */ C0777t tD;

        C02271(C0777t c0777t, Map map) {
            this.tD = c0777t;
            this.tC = map;
        }

        public void run() {
            if (TextUtils.isEmpty((CharSequence) this.tC.get("&cid"))) {
                this.tC.put("&cid", this.tD.su);
            }
            if (!GoogleAnalytics.getInstance(this.tD.mContext).getAppOptOut() && !this.tD.m1631s(this.tC)) {
                if (!TextUtils.isEmpty(this.tD.tz)) {
                    C0232u.cy().m69t(true);
                    this.tC.putAll(new HitBuilder().setCampaignParamsFromUrl(this.tD.tz).build());
                    C0232u.cy().m69t(false);
                    this.tD.tz = null;
                }
                this.tD.m1633u(this.tC);
                this.tD.m1632t(this.tC);
                this.tD.tB.m40b(C0234y.m73v(this.tC), Long.valueOf((String) this.tC.get("&ht")).longValue(), this.tD.m1630r(this.tC), this.tD.ty);
            }
        }
    }

    /* renamed from: com.google.android.gms.analytics.t.2 */
    class C02282 implements Runnable {
        final /* synthetic */ C0777t tD;

        C02282(C0777t c0777t) {
            this.tD = c0777t;
        }

        public void run() {
            this.tD.tB.bW();
        }
    }

    /* renamed from: com.google.android.gms.analytics.t.3 */
    class C02293 implements Runnable {
        final /* synthetic */ C0777t tD;

        C02293(C0777t c0777t) {
            this.tD = c0777t;
        }

        public void run() {
            this.tD.tB.bR();
        }
    }

    /* renamed from: com.google.android.gms.analytics.t.4 */
    class C02304 implements Runnable {
        final /* synthetic */ C0777t tD;

        C02304(C0777t c0777t) {
            this.tD = c0777t;
        }

        public void run() {
            this.tD.tB.bY();
        }
    }

    private C0777t(Context context) {
        super("GAThread");
        this.tw = new LinkedBlockingQueue();
        this.tx = false;
        this.mClosed = false;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        start();
    }

    static int m1616H(String str) {
        int i = 1;
        if (!TextUtils.isEmpty(str)) {
            i = 0;
            for (int length = str.length() - 1; length >= 0; length--) {
                char charAt = str.charAt(length);
                i = (((i << 6) & 268435455) + charAt) + (charAt << 14);
                int i2 = 266338304 & i;
                if (i2 != 0) {
                    i ^= i2 >> 21;
                }
            }
        }
        return i;
    }

    private String m1619a(Throwable th) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    static C0777t m1628q(Context context) {
        if (tA == null) {
            tA = new C0777t(context);
        }
        return tA;
    }

    static String m1629r(Context context) {
        try {
            FileInputStream openFileInput = context.openFileInput("gaInstallData");
            byte[] bArr = new byte[AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD];
            int read = openFileInput.read(bArr, 0, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD);
            if (openFileInput.available() > 0) {
                aa.m32w("Too much campaign data, ignoring it.");
                openFileInput.close();
                context.deleteFile("gaInstallData");
                return null;
            }
            openFileInput.close();
            context.deleteFile("gaInstallData");
            if (read <= 0) {
                aa.m35z("Campaign file is empty.");
                return null;
            }
            String str = new String(bArr, 0, read);
            aa.m33x("Campaign found: " + str);
            return str;
        } catch (FileNotFoundException e) {
            aa.m33x("No campaign data found.");
            return null;
        } catch (IOException e2) {
            aa.m32w("Error reading campaign data.");
            context.deleteFile("gaInstallData");
            return null;
        }
    }

    private String m1630r(Map<String, String> map) {
        return map.containsKey("useSecure") ? ak.m46d((String) map.get("useSecure"), true) ? "https:" : "http:" : "https:";
    }

    private boolean m1631s(Map<String, String> map) {
        if (map.get("&sf") == null) {
            return false;
        }
        double a = ak.m43a((String) map.get("&sf"), 100.0d);
        if (a >= 100.0d) {
            return false;
        }
        if (((double) (C0777t.m1616H((String) map.get("&cid")) % 10000)) < a * 100.0d) {
            return false;
        }
        String str = map.get("&t") == null ? EnvironmentCompat.MEDIA_UNKNOWN : (String) map.get("&t");
        aa.m34y(String.format("%s hit sampled out", new Object[]{str}));
        return true;
    }

    private void m1632t(Map<String, String> map) {
        C0214m m = C0766a.m1549m(this.mContext);
        ak.m45a(map, "&adid", m.getValue("&adid"));
        ak.m45a(map, "&ate", m.getValue("&ate"));
    }

    private void m1633u(Map<String, String> map) {
        C0214m ca = C0770g.ca();
        ak.m45a(map, "&an", ca.getValue("&an"));
        ak.m45a(map, "&av", ca.getValue("&av"));
        ak.m45a(map, "&aid", ca.getValue("&aid"));
        ak.m45a(map, "&aiid", ca.getValue("&aiid"));
        map.put("&v", "1");
    }

    void m1634a(Runnable runnable) {
        this.tw.add(runnable);
    }

    public void bR() {
        m1634a(new C02293(this));
    }

    public void bW() {
        m1634a(new C02282(this));
    }

    public void bY() {
        m1634a(new C02304(this));
    }

    public LinkedBlockingQueue<Runnable> bZ() {
        return this.tw;
    }

    public Thread getThread() {
        return this;
    }

    protected void init() {
        this.tB.cp();
        this.ty = new ArrayList();
        this.ty.add(new ef("appendVersion", "&_v".substring(1), "ma4.0.1"));
        this.ty.add(new ef("appendQueueTime", "&qt".substring(1), null));
        this.ty.add(new ef("appendCacheBuster", "&z".substring(1), null));
    }

    public void m1635q(Map<String, String> map) {
        Map hashMap = new HashMap(map);
        String str = (String) map.get("&ht");
        if (str != null) {
            try {
                Long.valueOf(str);
            } catch (NumberFormatException e) {
                str = null;
            }
        }
        if (str == null) {
            hashMap.put("&ht", Long.toString(System.currentTimeMillis()));
        }
        m1634a(new C02271(this, hashMap));
    }

    public void run() {
        Process.setThreadPriority(10);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            aa.m35z("sleep interrupted in GAThread initialize");
        }
        try {
            if (this.tB == null) {
                this.tB = new C0776s(this.mContext, this);
            }
            init();
            this.su = C0771h.cb().getValue("&cid");
            if (this.su == null) {
                this.tx = true;
            }
            this.tz = C0777t.m1629r(this.mContext);
            aa.m34y("Initialized GA Thread");
        } catch (Throwable th) {
            aa.m32w("Error initializing the GAThread: " + m1619a(th));
            aa.m32w("Google Analytics will not start up.");
            this.tx = true;
        }
        while (!this.mClosed) {
            try {
                Runnable runnable = (Runnable) this.tw.take();
                if (!this.tx) {
                    runnable.run();
                }
            } catch (InterruptedException e2) {
                aa.m33x(e2.toString());
            } catch (Throwable th2) {
                aa.m32w("Error on GAThread: " + m1619a(th2));
                aa.m32w("Google Analytics is shutting down.");
                this.tx = true;
            }
        }
    }
}
