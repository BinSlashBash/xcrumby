/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.TrackerHandler;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.ad;
import com.google.android.gms.analytics.ae;
import com.google.android.gms.analytics.aj;
import com.google.android.gms.analytics.ak;
import com.google.android.gms.analytics.g;
import com.google.android.gms.analytics.h;
import com.google.android.gms.analytics.i;
import com.google.android.gms.analytics.u;
import com.google.android.gms.analytics.z;
import com.google.android.gms.internal.fq;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Tracker {
    private final TrackerHandler vM;
    private final Map<String, String> vN = new HashMap<String, String>();
    private ad vO;
    private final h vP;
    private final ae vQ;
    private final g vR;
    private boolean vS;
    private a vT;
    private aj vU;

    Tracker(String string2, TrackerHandler trackerHandler) {
        this(string2, trackerHandler, h.cb(), ae.cZ(), g.ca(), new z("tracking"));
    }

    Tracker(String string2, TrackerHandler trackerHandler, h h2, ae ae2, g g2, ad ad2) {
        this.vM = trackerHandler;
        if (string2 != null) {
            this.vN.put("&tid", string2);
        }
        this.vN.put("useSecure", "1");
        this.vP = h2;
        this.vQ = ae2;
        this.vR = g2;
        this.vO = ad2;
        this.vT = new a();
    }

    void a(Context context, aj object) {
        aa.y("Loading Tracker config values.");
        this.vU = object;
        if (this.vU.dj()) {
            object = this.vU.dk();
            this.set("&tid", (String)object);
            aa.y("[Tracker] trackingId loaded: " + (String)object);
        }
        if (this.vU.dl()) {
            object = Double.toString(this.vU.dm());
            this.set("&sf", (String)object);
            aa.y("[Tracker] sample frequency loaded: " + (String)object);
        }
        if (this.vU.dn()) {
            this.setSessionTimeout(this.vU.getSessionTimeout());
            aa.y("[Tracker] session timeout loaded: " + this.dc());
        }
        if (this.vU.do()) {
            this.enableAutoActivityTracking(this.vU.dp());
            aa.y("[Tracker] auto activity tracking loaded: " + this.dd());
        }
        if (this.vU.dq()) {
            if (this.vU.dr()) {
                this.set("&aip", "1");
                aa.y("[Tracker] anonymize ip loaded: true");
            }
            aa.y("[Tracker] anonymize ip loaded: false");
        }
        this.vS = this.vU.ds();
        if (this.vU.ds()) {
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), context));
            aa.y("[Tracker] report uncaught exceptions loaded: " + this.vS);
        }
    }

    long dc() {
        return this.vT.dc();
    }

    boolean dd() {
        return this.vT.dd();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void enableAdvertisingIdCollection(boolean bl2) {
        if (!bl2) {
            this.vN.put("&ate", null);
            this.vN.put("&adid", null);
            return;
        } else {
            if (this.vN.containsKey("&ate")) {
                this.vN.remove("&ate");
            }
            if (!this.vN.containsKey("&adid")) return;
            {
                this.vN.remove("&adid");
                return;
            }
        }
    }

    public void enableAutoActivityTracking(boolean bl2) {
        this.vT.enableAutoActivityTracking(bl2);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public String get(String string2) {
        u.cy().a(u.a.tR);
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return null;
        }
        if (this.vN.containsKey(string2)) {
            return this.vN.get(string2);
        }
        if (string2.equals("&ul")) {
            return ak.a(Locale.getDefault());
        }
        if (this.vP != null && this.vP.C(string2)) {
            return this.vP.getValue(string2);
        }
        if (this.vQ != null && this.vQ.C(string2)) {
            return this.vQ.getValue(string2);
        }
        if (this.vR == null) return null;
        if (!this.vR.C(string2)) return null;
        return this.vR.getValue(string2);
    }

    public void send(Map<String, String> object) {
        String string2;
        u.cy().a(u.a.tT);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.putAll(this.vN);
        if (object != null) {
            hashMap.putAll((Map<String, String>)object);
        }
        if (TextUtils.isEmpty((CharSequence)((CharSequence)hashMap.get("&tid")))) {
            aa.z(String.format("Missing tracking id (%s) parameter.", "&tid"));
        }
        object = string2 = (String)hashMap.get("&t");
        if (TextUtils.isEmpty((CharSequence)string2)) {
            aa.z(String.format("Missing hit type (%s) parameter.", "&t"));
            object = "";
        }
        if (this.vT.de()) {
            hashMap.put("&sc", "start");
        }
        if (!(object.equals("transaction") || object.equals("item") || this.vO.cS())) {
            aa.z("Too many hits sent too quickly, rate limiting invoked.");
            return;
        }
        this.vM.q(hashMap);
    }

    public void set(String string2, String string3) {
        fq.b(string2, (Object)"Key should be non-null");
        u.cy().a(u.a.tS);
        this.vN.put(string2, string3);
    }

    public void setAnonymizeIp(boolean bl2) {
        this.set("&aip", ak.u(bl2));
    }

    public void setAppId(String string2) {
        this.set("&aid", string2);
    }

    public void setAppInstallerId(String string2) {
        this.set("&aiid", string2);
    }

    public void setAppName(String string2) {
        this.set("&an", string2);
    }

    public void setAppVersion(String string2) {
        this.set("&av", string2);
    }

    public void setClientId(String string2) {
        this.set("&cid", string2);
    }

    public void setEncoding(String string2) {
        this.set("&de", string2);
    }

    public void setHostname(String string2) {
        this.set("&dh", string2);
    }

    public void setLanguage(String string2) {
        this.set("&ul", string2);
    }

    public void setLocation(String string2) {
        this.set("&dl", string2);
    }

    public void setPage(String string2) {
        this.set("&dp", string2);
    }

    public void setReferrer(String string2) {
        this.set("&dr", string2);
    }

    public void setSampleRate(double d2) {
        this.set("&sf", Double.toHexString(d2));
    }

    public void setScreenColors(String string2) {
        this.set("&sd", string2);
    }

    public void setScreenName(String string2) {
        this.set("&cd", string2);
    }

    public void setScreenResolution(int n2, int n3) {
        if (n2 < 0 && n3 < 0) {
            aa.z("Invalid width or height. The values should be non-negative.");
            return;
        }
        this.set("&sr", "" + n2 + "x" + n3);
    }

    public void setSessionTimeout(long l2) {
        this.vT.setSessionTimeout(1000 * l2);
    }

    public void setTitle(String string2) {
        this.set("&dt", string2);
    }

    public void setUseSecure(boolean bl2) {
        this.set("useSecure", ak.u(bl2));
    }

    public void setViewportSize(String string2) {
        this.set("&vp", string2);
    }

    private class com.google.android.gms.analytics.Tracker$a
    implements GoogleAnalytics.a {
        private i tg;
        private Timer vV;
        private TimerTask vW;
        private boolean vX;
        private boolean vY;
        private int vZ;
        private long wa;
        private boolean wb;
        private long wc;

        public com.google.android.gms.analytics.Tracker$a() {
            this.vX = false;
            this.vY = false;
            this.vZ = 0;
            this.wa = -1;
            this.wb = false;
            this.tg = new i(Tracker.this){
                final /* synthetic */ Tracker we;

                @Override
                public long currentTimeMillis() {
                    return System.currentTimeMillis();
                }
            };
        }

        private void df() {
            GoogleAnalytics googleAnalytics = GoogleAnalytics.cM();
            if (googleAnalytics == null) {
                aa.w("GoogleAnalytics isn't initialized for the Tracker!");
                return;
            }
            if (this.wa >= 0 || this.vY) {
                googleAnalytics.a(Tracker.this.vT);
                return;
            }
            googleAnalytics.b(Tracker.this.vT);
        }

        private void dg() {
            synchronized (this) {
                if (this.vV != null) {
                    this.vV.cancel();
                    this.vV = null;
                }
                return;
            }
        }

        public long dc() {
            return this.wa;
        }

        public boolean dd() {
            return this.vY;
        }

        public boolean de() {
            boolean bl2 = this.wb;
            this.wb = false;
            return bl2;
        }

        boolean dh() {
            if (this.wa == 0 || this.wa > 0 && this.tg.currentTimeMillis() > this.wc + this.wa) {
                return true;
            }
            return false;
        }

        public void enableAutoActivityTracking(boolean bl2) {
            this.vY = bl2;
            this.df();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void f(Activity object) {
            u.cy().a(u.a.uQ);
            this.dg();
            if (!this.vX && this.vZ == 0 && this.dh()) {
                this.wb = true;
            }
            this.vX = true;
            ++this.vZ;
            if (this.vY) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("&t", "appview");
                u.cy().t(true);
                Tracker tracker = Tracker.this;
                object = Tracker.this.vU != null ? Tracker.this.vU.h((Activity)object) : object.getClass().getCanonicalName();
                tracker.set("&cd", (String)object);
                Tracker.this.send(hashMap);
                u.cy().t(false);
            }
        }

        @Override
        public void g(Activity activity) {
            u.cy().a(u.a.uR);
            --this.vZ;
            this.vZ = Math.max(0, this.vZ);
            this.wc = this.tg.currentTimeMillis();
            if (this.vZ == 0) {
                this.dg();
                this.vW = new a();
                this.vV = new Timer("waitForActivityStart");
                this.vV.schedule(this.vW, 1000);
            }
        }

        public void setSessionTimeout(long l2) {
            this.wa = l2;
            this.df();
        }

        private class a
        extends TimerTask {
            private a() {
            }

            @Override
            public void run() {
                a.this.vX = false;
            }
        }

    }

}

