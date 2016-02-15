/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 */
package com.google.android.gms.analytics;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.TrackerHandler;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.ae;
import com.google.android.gms.analytics.af;
import com.google.android.gms.analytics.ai;
import com.google.android.gms.analytics.aj;
import com.google.android.gms.analytics.ak;
import com.google.android.gms.analytics.f;
import com.google.android.gms.analytics.g;
import com.google.android.gms.analytics.h;
import com.google.android.gms.analytics.l;
import com.google.android.gms.analytics.r;
import com.google.android.gms.analytics.t;
import com.google.android.gms.analytics.u;
import com.google.android.gms.analytics.v;
import com.google.android.gms.analytics.w;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class GoogleAnalytics
extends TrackerHandler {
    private static boolean uY;
    private static GoogleAnalytics vf;
    private Context mContext;
    private f sH;
    private String so;
    private String sp;
    private boolean uZ;
    private af va;
    private volatile Boolean vb = false;
    private Logger vc;
    private Set<a> vd;
    private boolean ve = false;

    protected GoogleAnalytics(Context context) {
        this(context, t.q(context), r.ci());
    }

    private GoogleAnalytics(Context context, f f2, af af2) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.sH = f2;
        this.va = af2;
        g.n(this.mContext);
        ae.n(this.mContext);
        h.n(this.mContext);
        this.vc = new l();
        this.vd = new HashSet<a>();
        this.cN();
    }

    private int I(String string2) {
        if ("verbose".equals(string2 = string2.toLowerCase())) {
            return 0;
        }
        if ("info".equals(string2)) {
            return 1;
        }
        if ("warning".equals(string2)) {
            return 2;
        }
        if ("error".equals(string2)) {
            return 3;
        }
        return -1;
    }

    private Tracker a(Tracker tracker) {
        if (this.so != null) {
            tracker.set("&an", this.so);
        }
        if (this.sp != null) {
            tracker.set("&av", this.sp);
        }
        return tracker;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static GoogleAnalytics cM() {
        synchronized (GoogleAnalytics.class) {
            return vf;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void cN() {
        Object object;
        if (uY) {
            return;
        }
        try {
            object = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 129);
        }
        catch (PackageManager.NameNotFoundException var2_2) {
            aa.y("PackageManager doesn't know about package: " + (Object)var2_2);
            object = null;
        }
        if (object == null) {
            aa.z("Couldn't get ApplicationInfo to load gloabl config.");
            return;
        }
        object = object.metaData;
        if (object == null) return;
        int n2 = object.getInt("com.google.android.gms.analytics.globalConfigResource");
        if (n2 <= 0) return;
        object = (w)new v(this.mContext).p(n2);
        if (object == null) return;
        this.a((w)object);
    }

    private void d(Activity activity) {
        Iterator<a> iterator = this.vd.iterator();
        while (iterator.hasNext()) {
            iterator.next().f(activity);
        }
    }

    private void e(Activity activity) {
        Iterator<a> iterator = this.vd.iterator();
        while (iterator.hasNext()) {
            iterator.next().g(activity);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static GoogleAnalytics getInstance(Context object) {
        synchronized (GoogleAnalytics.class) {
            if (vf != null) return vf;
            vf = new GoogleAnalytics((Context)object);
            return vf;
        }
    }

    void a(a a2) {
        this.vd.add(a2);
    }

    void a(w w2) {
        int n2;
        aa.y("Loading global config values.");
        if (w2.cC()) {
            this.so = w2.cD();
            aa.y("app name loaded: " + this.so);
        }
        if (w2.cE()) {
            this.sp = w2.cF();
            aa.y("app version loaded: " + this.sp);
        }
        if (w2.cG() && (n2 = this.I(w2.cH())) >= 0) {
            aa.y("log level loaded: " + n2);
            this.getLogger().setLogLevel(n2);
        }
        if (w2.cI()) {
            this.va.setLocalDispatchPeriod(w2.cJ());
        }
        if (w2.cK()) {
            this.setDryRun(w2.cL());
        }
    }

    void b(a a2) {
        this.vd.remove(a2);
    }

    @Deprecated
    public void dispatchLocalHits() {
        this.va.dispatchLocalHits();
    }

    public void enableAutoActivityReports(Application application) {
        if (Build.VERSION.SDK_INT >= 14 && !this.ve) {
            application.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)new b());
            this.ve = true;
        }
    }

    public boolean getAppOptOut() {
        u.cy().a(u.a.uz);
        return this.vb;
    }

    public Logger getLogger() {
        return this.vc;
    }

    public boolean isDryRunEnabled() {
        u.cy().a(u.a.uL);
        return this.uZ;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Tracker newTracker(int n2) {
        synchronized (this) {
            u.cy().a(u.a.uv);
            Tracker tracker = new Tracker(null, this);
            if (n2 <= 0) return this.a(tracker);
            aj aj2 = (aj)new ai(this.mContext).p(n2);
            if (aj2 == null) return this.a(tracker);
            tracker.a(this.mContext, aj2);
            return this.a(tracker);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Tracker newTracker(String object) {
        synchronized (this) {
            u.cy().a(u.a.uv);
            return this.a(new Tracker((String)object, this));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void q(Map<String, String> map) {
        synchronized (this) {
            if (map == null) {
                throw new IllegalArgumentException("hit cannot be null");
            }
            ak.a(map, "&ul", ak.a(Locale.getDefault()));
            ak.a(map, "&sr", ae.cZ().getValue("&sr"));
            map.put("&_u", u.cy().cA());
            u.cy().cz();
            this.sH.q(map);
            return;
        }
    }

    public void reportActivityStart(Activity activity) {
        if (!this.ve) {
            this.d(activity);
        }
    }

    public void reportActivityStop(Activity activity) {
        if (!this.ve) {
            this.e(activity);
        }
    }

    public void setAppOptOut(boolean bl2) {
        u.cy().a(u.a.uy);
        this.vb = bl2;
        if (this.vb.booleanValue()) {
            this.sH.bR();
        }
    }

    public void setDryRun(boolean bl2) {
        u.cy().a(u.a.uK);
        this.uZ = bl2;
    }

    @Deprecated
    public void setLocalDispatchPeriod(int n2) {
        this.va.setLocalDispatchPeriod(n2);
    }

    public void setLogger(Logger logger) {
        u.cy().a(u.a.uM);
        this.vc = logger;
    }

    static interface a {
        public void f(Activity var1);

        public void g(Activity var1);
    }

    class b
    implements Application.ActivityLifecycleCallbacks {
        b() {
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
            GoogleAnalytics.this.d(activity);
        }

        public void onActivityStopped(Activity activity) {
            GoogleAnalytics.this.e(activity);
        }
    }

}

