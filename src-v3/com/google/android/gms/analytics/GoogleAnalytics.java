package com.google.android.gms.analytics;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.google.android.gms.analytics.C0232u.C0231a;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class GoogleAnalytics extends TrackerHandler {
    private static boolean uY;
    private static GoogleAnalytics vf;
    private Context mContext;
    private C0208f sH;
    private String so;
    private String sp;
    private boolean uZ;
    private af va;
    private volatile Boolean vb;
    private Logger vc;
    private Set<C0197a> vd;
    private boolean ve;

    /* renamed from: com.google.android.gms.analytics.GoogleAnalytics.a */
    interface C0197a {
        void m26f(Activity activity);

        void m27g(Activity activity);
    }

    /* renamed from: com.google.android.gms.analytics.GoogleAnalytics.b */
    class C0198b implements ActivityLifecycleCallbacks {
        final /* synthetic */ GoogleAnalytics vg;

        C0198b(GoogleAnalytics googleAnalytics) {
            this.vg = googleAnalytics;
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityStarted(Activity activity) {
            this.vg.m1540d(activity);
        }

        public void onActivityStopped(Activity activity) {
            this.vg.m1541e(activity);
        }
    }

    protected GoogleAnalytics(Context context) {
        this(context, C0777t.m1628q(context), C0774r.ci());
    }

    private GoogleAnalytics(Context context, C0208f thread, af serviceManager) {
        this.vb = Boolean.valueOf(false);
        this.ve = false;
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.sH = thread;
        this.va = serviceManager;
        C0770g.m1586n(this.mContext);
        ae.m1563n(this.mContext);
        C0771h.m1592n(this.mContext);
        this.vc = new C0772l();
        this.vd = new HashSet();
        cN();
    }

    private int m1536I(String str) {
        String toLowerCase = str.toLowerCase();
        return "verbose".equals(toLowerCase) ? 0 : "info".equals(toLowerCase) ? 1 : "warning".equals(toLowerCase) ? 2 : "error".equals(toLowerCase) ? 3 : -1;
    }

    private Tracker m1537a(Tracker tracker) {
        if (this.so != null) {
            tracker.set("&an", this.so);
        }
        if (this.sp != null) {
            tracker.set("&av", this.sp);
        }
        return tracker;
    }

    static GoogleAnalytics cM() {
        GoogleAnalytics googleAnalytics;
        synchronized (GoogleAnalytics.class) {
            googleAnalytics = vf;
        }
        return googleAnalytics;
    }

    private void cN() {
        if (!uY) {
            ApplicationInfo applicationInfo;
            try {
                applicationInfo = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 129);
            } catch (NameNotFoundException e) {
                aa.m34y("PackageManager doesn't know about package: " + e);
                applicationInfo = null;
            }
            if (applicationInfo == null) {
                aa.m35z("Couldn't get ApplicationInfo to load gloabl config.");
                return;
            }
            Bundle bundle = applicationInfo.metaData;
            if (bundle != null) {
                int i = bundle.getInt("com.google.android.gms.analytics.globalConfigResource");
                if (i > 0) {
                    C0780w c0780w = (C0780w) new C0779v(this.mContext).m59p(i);
                    if (c0780w != null) {
                        m1543a(c0780w);
                    }
                }
            }
        }
    }

    private void m1540d(Activity activity) {
        for (C0197a f : this.vd) {
            f.m26f(activity);
        }
    }

    private void m1541e(Activity activity) {
        for (C0197a g : this.vd) {
            g.m27g(activity);
        }
    }

    public static GoogleAnalytics getInstance(Context context) {
        GoogleAnalytics googleAnalytics;
        synchronized (GoogleAnalytics.class) {
            if (vf == null) {
                vf = new GoogleAnalytics(context);
            }
            googleAnalytics = vf;
        }
        return googleAnalytics;
    }

    void m1542a(C0197a c0197a) {
        this.vd.add(c0197a);
    }

    void m1543a(C0780w c0780w) {
        aa.m34y("Loading global config values.");
        if (c0780w.cC()) {
            this.so = c0780w.cD();
            aa.m34y("app name loaded: " + this.so);
        }
        if (c0780w.cE()) {
            this.sp = c0780w.cF();
            aa.m34y("app version loaded: " + this.sp);
        }
        if (c0780w.cG()) {
            int I = m1536I(c0780w.cH());
            if (I >= 0) {
                aa.m34y("log level loaded: " + I);
                getLogger().setLogLevel(I);
            }
        }
        if (c0780w.cI()) {
            this.va.setLocalDispatchPeriod(c0780w.cJ());
        }
        if (c0780w.cK()) {
            setDryRun(c0780w.cL());
        }
    }

    void m1544b(C0197a c0197a) {
        this.vd.remove(c0197a);
    }

    @Deprecated
    public void dispatchLocalHits() {
        this.va.dispatchLocalHits();
    }

    public void enableAutoActivityReports(Application application) {
        if (VERSION.SDK_INT >= 14 && !this.ve) {
            application.registerActivityLifecycleCallbacks(new C0198b(this));
            this.ve = true;
        }
    }

    public boolean getAppOptOut() {
        C0232u.cy().m68a(C0231a.GET_APP_OPT_OUT);
        return this.vb.booleanValue();
    }

    public Logger getLogger() {
        return this.vc;
    }

    public boolean isDryRunEnabled() {
        C0232u.cy().m68a(C0231a.GET_DRY_RUN);
        return this.uZ;
    }

    public Tracker newTracker(int configResId) {
        Tracker a;
        synchronized (this) {
            C0232u.cy().m68a(C0231a.GET_TRACKER);
            Tracker tracker = new Tracker(null, this);
            if (configResId > 0) {
                aj ajVar = (aj) new ai(this.mContext).m59p(configResId);
                if (ajVar != null) {
                    tracker.m30a(this.mContext, ajVar);
                }
            }
            a = m1537a(tracker);
        }
        return a;
    }

    public Tracker newTracker(String trackingId) {
        Tracker a;
        synchronized (this) {
            C0232u.cy().m68a(C0231a.GET_TRACKER);
            a = m1537a(new Tracker(trackingId, this));
        }
        return a;
    }

    void m1545q(Map<String, String> map) {
        synchronized (this) {
            if (map == null) {
                throw new IllegalArgumentException("hit cannot be null");
            }
            ak.m45a(map, "&ul", ak.m44a(Locale.getDefault()));
            ak.m45a(map, "&sr", ae.cZ().getValue("&sr"));
            map.put("&_u", C0232u.cy().cA());
            C0232u.cy().cz();
            this.sH.m53q(map);
        }
    }

    public void reportActivityStart(Activity activity) {
        if (!this.ve) {
            m1540d(activity);
        }
    }

    public void reportActivityStop(Activity activity) {
        if (!this.ve) {
            m1541e(activity);
        }
    }

    public void setAppOptOut(boolean optOut) {
        C0232u.cy().m68a(C0231a.SET_APP_OPT_OUT);
        this.vb = Boolean.valueOf(optOut);
        if (this.vb.booleanValue()) {
            this.sH.bR();
        }
    }

    public void setDryRun(boolean dryRun) {
        C0232u.cy().m68a(C0231a.SET_DRY_RUN);
        this.uZ = dryRun;
    }

    @Deprecated
    public void setLocalDispatchPeriod(int dispatchPeriodInSeconds) {
        this.va.setLocalDispatchPeriod(dispatchPeriodInSeconds);
    }

    public void setLogger(Logger logger) {
        C0232u.cy().m68a(C0231a.SET_LOGGER);
        this.vc = logger;
    }
}
