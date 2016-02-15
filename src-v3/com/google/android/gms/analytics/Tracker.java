package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.google.android.gms.analytics.C0232u.C0231a;
import com.google.android.gms.analytics.GoogleAnalytics.C0197a;
import com.google.android.gms.internal.fq;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Tracker {
    private final TrackerHandler vM;
    private final Map<String, String> vN;
    private ad vO;
    private final C0771h vP;
    private final ae vQ;
    private final C0770g vR;
    private boolean vS;
    private C0765a vT;
    private aj vU;

    /* renamed from: com.google.android.gms.analytics.Tracker.a */
    private class C0765a implements C0197a {
        private C0210i tg;
        private Timer vV;
        private TimerTask vW;
        private boolean vX;
        private boolean vY;
        private int vZ;
        private long wa;
        private boolean wb;
        private long wc;
        final /* synthetic */ Tracker wd;

        /* renamed from: com.google.android.gms.analytics.Tracker.a.a */
        private class C0200a extends TimerTask {
            final /* synthetic */ C0765a wf;

            private C0200a(C0765a c0765a) {
                this.wf = c0765a;
            }

            public void run() {
                this.wf.vX = false;
            }
        }

        /* renamed from: com.google.android.gms.analytics.Tracker.a.1 */
        class C07641 implements C0210i {
            final /* synthetic */ Tracker we;
            final /* synthetic */ C0765a wf;

            C07641(C0765a c0765a, Tracker tracker) {
                this.wf = c0765a;
                this.we = tracker;
            }

            public long currentTimeMillis() {
                return System.currentTimeMillis();
            }
        }

        public C0765a(Tracker tracker) {
            this.wd = tracker;
            this.vX = false;
            this.vY = false;
            this.vZ = 0;
            this.wa = -1;
            this.wb = false;
            this.tg = new C07641(this, tracker);
        }

        private void df() {
            GoogleAnalytics cM = GoogleAnalytics.cM();
            if (cM == null) {
                aa.m32w("GoogleAnalytics isn't initialized for the Tracker!");
            } else if (this.wa >= 0 || this.vY) {
                cM.m1542a(this.wd.vT);
            } else {
                cM.m1544b(this.wd.vT);
            }
        }

        private synchronized void dg() {
            if (this.vV != null) {
                this.vV.cancel();
                this.vV = null;
            }
        }

        public long dc() {
            return this.wa;
        }

        public boolean dd() {
            return this.vY;
        }

        public boolean de() {
            boolean z = this.wb;
            this.wb = false;
            return z;
        }

        boolean dh() {
            return this.wa == 0 || (this.wa > 0 && this.tg.currentTimeMillis() > this.wc + this.wa);
        }

        public void enableAutoActivityTracking(boolean enabled) {
            this.vY = enabled;
            df();
        }

        public void m1547f(Activity activity) {
            C0232u.cy().m68a(C0231a.EASY_TRACKER_ACTIVITY_START);
            dg();
            if (!this.vX && this.vZ == 0 && dh()) {
                this.wb = true;
            }
            this.vX = true;
            this.vZ++;
            if (this.vY) {
                Map hashMap = new HashMap();
                hashMap.put("&t", "appview");
                C0232u.cy().m69t(true);
                this.wd.set("&cd", this.wd.vU != null ? this.wd.vU.m1578h(activity) : activity.getClass().getCanonicalName());
                this.wd.send(hashMap);
                C0232u.cy().m69t(false);
            }
        }

        public void m1548g(Activity activity) {
            C0232u.cy().m68a(C0231a.EASY_TRACKER_ACTIVITY_STOP);
            this.vZ--;
            this.vZ = Math.max(0, this.vZ);
            this.wc = this.tg.currentTimeMillis();
            if (this.vZ == 0) {
                dg();
                this.vW = new C0200a();
                this.vV = new Timer("waitForActivityStart");
                this.vV.schedule(this.vW, 1000);
            }
        }

        public void setSessionTimeout(long sessionTimeout) {
            this.wa = sessionTimeout;
            df();
        }
    }

    Tracker(String trackingId, TrackerHandler handler) {
        this(trackingId, handler, C0771h.cb(), ae.cZ(), C0770g.ca(), new C0781z("tracking"));
    }

    Tracker(String trackingId, TrackerHandler handler, C0771h clientIdDefaultProvider, ae screenResolutionDefaultProvider, C0770g appFieldsDefaultProvider, ad rateLimiter) {
        this.vN = new HashMap();
        this.vM = handler;
        if (trackingId != null) {
            this.vN.put("&tid", trackingId);
        }
        this.vN.put("useSecure", "1");
        this.vP = clientIdDefaultProvider;
        this.vQ = screenResolutionDefaultProvider;
        this.vR = appFieldsDefaultProvider;
        this.vO = rateLimiter;
        this.vT = new C0765a(this);
    }

    void m30a(Context context, aj ajVar) {
        aa.m34y("Loading Tracker config values.");
        this.vU = ajVar;
        if (this.vU.dj()) {
            String dk = this.vU.dk();
            set("&tid", dk);
            aa.m34y("[Tracker] trackingId loaded: " + dk);
        }
        if (this.vU.dl()) {
            dk = Double.toString(this.vU.dm());
            set("&sf", dk);
            aa.m34y("[Tracker] sample frequency loaded: " + dk);
        }
        if (this.vU.dn()) {
            setSessionTimeout((long) this.vU.getSessionTimeout());
            aa.m34y("[Tracker] session timeout loaded: " + dc());
        }
        if (this.vU.m1577do()) {
            enableAutoActivityTracking(this.vU.dp());
            aa.m34y("[Tracker] auto activity tracking loaded: " + dd());
        }
        if (this.vU.dq()) {
            if (this.vU.dr()) {
                set("&aip", "1");
                aa.m34y("[Tracker] anonymize ip loaded: true");
            }
            aa.m34y("[Tracker] anonymize ip loaded: false");
        }
        this.vS = this.vU.ds();
        if (this.vU.ds()) {
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), context));
            aa.m34y("[Tracker] report uncaught exceptions loaded: " + this.vS);
        }
    }

    long dc() {
        return this.vT.dc();
    }

    boolean dd() {
        return this.vT.dd();
    }

    public void enableAdvertisingIdCollection(boolean enabled) {
        if (enabled) {
            if (this.vN.containsKey("&ate")) {
                this.vN.remove("&ate");
            }
            if (this.vN.containsKey("&adid")) {
                this.vN.remove("&adid");
                return;
            }
            return;
        }
        this.vN.put("&ate", null);
        this.vN.put("&adid", null);
    }

    public void enableAutoActivityTracking(boolean enabled) {
        this.vT.enableAutoActivityTracking(enabled);
    }

    public String get(String key) {
        C0232u.cy().m68a(C0231a.GET);
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        if (this.vN.containsKey(key)) {
            return (String) this.vN.get(key);
        }
        if (key.equals("&ul")) {
            return ak.m44a(Locale.getDefault());
        }
        if (this.vP != null && this.vP.m1593C(key)) {
            return this.vP.getValue(key);
        }
        if (this.vQ == null || !this.vQ.m1564C(key)) {
            return (this.vR == null || !this.vR.m1587C(key)) ? null : this.vR.getValue(key);
        } else {
            return this.vQ.getValue(key);
        }
    }

    public void send(Map<String, String> params) {
        C0232u.cy().m68a(C0231a.SEND);
        Map hashMap = new HashMap();
        hashMap.putAll(this.vN);
        if (params != null) {
            hashMap.putAll(params);
        }
        if (TextUtils.isEmpty((CharSequence) hashMap.get("&tid"))) {
            aa.m35z(String.format("Missing tracking id (%s) parameter.", new Object[]{"&tid"}));
        }
        String str = (String) hashMap.get("&t");
        if (TextUtils.isEmpty(str)) {
            aa.m35z(String.format("Missing hit type (%s) parameter.", new Object[]{"&t"}));
            str = UnsupportedUrlFragment.DISPLAY_NAME;
        }
        if (this.vT.de()) {
            hashMap.put("&sc", "start");
        }
        if (str.equals("transaction") || str.equals("item") || this.vO.cS()) {
            this.vM.m31q(hashMap);
        } else {
            aa.m35z("Too many hits sent too quickly, rate limiting invoked.");
        }
    }

    public void set(String key, String value) {
        fq.m982b((Object) key, (Object) "Key should be non-null");
        C0232u.cy().m68a(C0231a.SET);
        this.vN.put(key, value);
    }

    public void setAnonymizeIp(boolean anonymize) {
        set("&aip", ak.m47u(anonymize));
    }

    public void setAppId(String appId) {
        set("&aid", appId);
    }

    public void setAppInstallerId(String appInstallerId) {
        set("&aiid", appInstallerId);
    }

    public void setAppName(String appName) {
        set("&an", appName);
    }

    public void setAppVersion(String appVersion) {
        set("&av", appVersion);
    }

    public void setClientId(String clientId) {
        set("&cid", clientId);
    }

    public void setEncoding(String encoding) {
        set("&de", encoding);
    }

    public void setHostname(String hostname) {
        set("&dh", hostname);
    }

    public void setLanguage(String language) {
        set("&ul", language);
    }

    public void setLocation(String location) {
        set("&dl", location);
    }

    public void setPage(String page) {
        set("&dp", page);
    }

    public void setReferrer(String referrer) {
        set("&dr", referrer);
    }

    public void setSampleRate(double sampleRate) {
        set("&sf", Double.toHexString(sampleRate));
    }

    public void setScreenColors(String screenColors) {
        set("&sd", screenColors);
    }

    public void setScreenName(String screenName) {
        set("&cd", screenName);
    }

    public void setScreenResolution(int width, int height) {
        if (width >= 0 || height >= 0) {
            set("&sr", width + "x" + height);
        } else {
            aa.m35z("Invalid width or height. The values should be non-negative.");
        }
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.vT.setSessionTimeout(1000 * sessionTimeout);
    }

    public void setTitle(String title) {
        set("&dt", title);
    }

    public void setUseSecure(boolean useSecure) {
        set("useSecure", ak.m47u(useSecure));
    }

    public void setViewportSize(String viewportSize) {
        set("&vp", viewportSize);
    }
}
