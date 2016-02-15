/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.KeyguardManager
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Resources
 *  android.graphics.Point
 *  android.graphics.Rect
 *  android.os.PowerManager
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.View
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.ViewTreeObserver$OnScrollChangedListener
 *  android.view.WindowManager
 */
package com.google.android.gms.internal;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ad;
import com.google.android.gms.internal.ae;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.ba;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.dh;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.y;
import com.google.android.gms.internal.z;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ab
implements ViewTreeObserver.OnGlobalLayoutListener,
ViewTreeObserver.OnScrollChangedListener {
    private static final long lw = TimeUnit.MILLISECONDS.toNanos(100);
    private HashSet<y> lA = new HashSet();
    private final Object li = new Object();
    private final WeakReference<dh> ll;
    private WeakReference<ViewTreeObserver> lm;
    private final WeakReference<View> ln;
    private final z lo;
    private final Context lp;
    private final ad lq;
    private boolean lr;
    private final WindowManager ls;
    private final PowerManager lt;
    private final KeyguardManager lu;
    private ac lv;
    private long lx = Long.MIN_VALUE;
    private boolean ly;
    private BroadcastReceiver lz;

    public ab(ak ak2, dh dh2) {
        this(ak2, dh2, dh2.oj.bK(), (View)dh2.oj, new ae(dh2.oj.getContext(), dh2.oj.bK()));
    }

    public ab(ak ak2, dh dh2, dx dx2, View view, ad ad2) {
        this.ll = new WeakReference<dh>(dh2);
        this.ln = new WeakReference<View>(view);
        this.lm = new WeakReference<Object>(null);
        this.ly = true;
        this.lo = new z(Integer.toString(dh2.hashCode()), dx2, ak2.lS, dh2.qs);
        this.lq = ad2;
        this.ls = (WindowManager)view.getContext().getSystemService("window");
        this.lt = (PowerManager)view.getContext().getApplicationContext().getSystemService("power");
        this.lu = (KeyguardManager)view.getContext().getSystemService("keyguard");
        this.lp = view.getContext().getApplicationContext();
        this.a(ad2);
        this.lq.a(new ad.a(){

            @Override
            public void ay() {
                ab.this.lr = true;
                ab.this.d(false);
                ab.this.ap();
            }
        });
        this.b(this.lq);
        dw.x("Tracking ad unit: " + this.lo.ao());
    }

    protected int a(int n2, DisplayMetrics displayMetrics) {
        float f2 = displayMetrics.density;
        return (int)((float)n2 / f2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(ac ac2) {
        Object object = this.li;
        synchronized (object) {
            this.lv = ac2;
            return;
        }
    }

    protected void a(ad ad2) {
        ad2.d("http://googleads.g.doubleclick.net/mads/static/sdk/native/sdk-core-v40.html");
    }

    protected void a(dz dz2, Map<String, String> map) {
        this.d(false);
    }

    public void a(y y2) {
        this.lA.add(y2);
    }

    protected void a(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject2 = new JSONObject();
        jSONArray.put(jSONObject);
        jSONObject2.put("units", jSONArray);
        this.lq.a("AFMA_updateActiveView", jSONObject2);
    }

    protected boolean a(View view, boolean bl2) {
        if (view.getVisibility() == 0 && bl2 && view.isShown() && this.lt.isScreenOn() && !this.lu.inKeyguardRestrictedInputMode()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void ap() {
        Object object = this.li;
        synchronized (object) {
            if (this.lz != null) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.lz = new BroadcastReceiver(){

                public void onReceive(Context context, Intent intent) {
                    ab.this.d(false);
                }
            };
            this.lp.registerReceiver(this.lz, intentFilter);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void aq() {
        Object object = this.li;
        synchronized (object) {
            if (this.lz != null) {
                this.lp.unregisterReceiver(this.lz);
                this.lz = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void ar() {
        Object object = this.li;
        synchronized (object) {
            if (this.ly) {
                this.av();
                this.aq();
                try {
                    this.a(this.ax());
                }
                catch (JSONException var2_2) {
                    dw.b("JSON Failure while processing active view data.", var2_2);
                }
                this.ly = false;
                this.as();
                dw.x("Untracked ad unit: " + this.lo.ao());
            }
            return;
        }
    }

    protected void as() {
        if (this.lv != null) {
            this.lv.a(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean at() {
        Object object = this.li;
        synchronized (object) {
            return this.ly;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void au() {
        View view = this.ln.get();
        if (view == null) {
            return;
        }
        ViewTreeObserver viewTreeObserver = this.lm.get();
        if ((view = view.getViewTreeObserver()) == viewTreeObserver) return;
        this.lm = new WeakReference<View>(view);
        view.addOnScrollChangedListener((ViewTreeObserver.OnScrollChangedListener)this);
        view.addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
    }

    protected void av() {
        ViewTreeObserver viewTreeObserver = this.lm.get();
        if (viewTreeObserver == null || !viewTreeObserver.isAlive()) {
            return;
        }
        viewTreeObserver.removeOnScrollChangedListener((ViewTreeObserver.OnScrollChangedListener)this);
        viewTreeObserver.removeGlobalOnLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
    }

    protected JSONObject aw() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("afmaVersion", this.lo.am()).put("activeViewJSON", this.lo.an()).put("timestamp", TimeUnit.NANOSECONDS.toMillis(System.nanoTime())).put("adFormat", this.lo.al()).put("hashCode", this.lo.ao());
        return jSONObject;
    }

    protected JSONObject ax() throws JSONException {
        JSONObject jSONObject = this.aw();
        jSONObject.put("doneReasonCode", "u");
        return jSONObject;
    }

    protected void b(ad ad2) {
        ad2.a("/updateActiveView", new bb(){

            @Override
            public void b(dz dz2, Map<String, String> map) {
                ab.this.a(dz2, map);
            }
        });
        ad2.a("/activeViewPingSent", new bb(){

            @Override
            public void b(dz dz2, Map<String, String> map) {
                if (map.containsKey("pingType") && "unloadPing".equals(map.get("pingType"))) {
                    ab.this.c(ab.this.lq);
                    dw.x("Unregistered GMSG handlers for: " + ab.this.lo.ao());
                }
            }
        });
        ad2.a("/visibilityChanged", new bb(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void b(dz dz2, Map<String, String> map) {
                if (!map.containsKey("isVisible")) {
                    return;
                }
                boolean bl2 = "1".equals(map.get("isVisible")) || "true".equals(map.get("isVisible"));
                ab.this.c(bl2);
            }
        });
        ad2.a("/viewabilityChanged", ba.mG);
    }

    protected JSONObject c(View view) throws JSONException {
        Object object = new int[2];
        Object object2 = new int[2];
        view.getLocationOnScreen((int[])object);
        view.getLocationInWindow((int[])object2);
        object2 = this.aw();
        DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        Rect rect = new Rect();
        rect.left = object[0];
        rect.top = object[1];
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        object = (Object)new Rect();
        object.right = this.ls.getDefaultDisplay().getWidth();
        object.bottom = this.ls.getDefaultDisplay().getHeight();
        Rect rect2 = new Rect();
        boolean bl2 = view.getGlobalVisibleRect(rect2, null);
        Rect rect3 = new Rect();
        view.getLocalVisibleRect(rect3);
        object2.put("viewBox", new JSONObject().put("top", this.a(object.top, displayMetrics)).put("bottom", this.a(object.bottom, displayMetrics)).put("left", this.a(object.left, displayMetrics)).put("right", this.a(object.right, displayMetrics))).put("adBox", new JSONObject().put("top", this.a(rect.top, displayMetrics)).put("bottom", this.a(rect.bottom, displayMetrics)).put("left", this.a(rect.left, displayMetrics)).put("right", this.a(rect.right, displayMetrics))).put("globalVisibleBox", new JSONObject().put("top", this.a(rect2.top, displayMetrics)).put("bottom", this.a(rect2.bottom, displayMetrics)).put("left", this.a(rect2.left, displayMetrics)).put("right", this.a(rect2.right, displayMetrics))).put("localVisibleBox", new JSONObject().put("top", this.a(rect3.top, displayMetrics)).put("bottom", this.a(rect3.bottom, displayMetrics)).put("left", this.a(rect3.left, displayMetrics)).put("right", this.a(rect3.right, displayMetrics))).put("screenDensity", displayMetrics.density).put("isVisible", this.a(view, bl2));
        return object2;
    }

    protected void c(ad ad2) {
        ad2.e("/viewabilityChanged");
        ad2.e("/visibilityChanged");
        ad2.e("/activeViewPingSent");
        ad2.e("/updateActiveView");
    }

    protected void c(boolean bl2) {
        Iterator<y> iterator = this.lA.iterator();
        while (iterator.hasNext()) {
            iterator.next().a(this, bl2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void d(boolean bl2) {
        Object object = this.li;
        synchronized (object) {
            if (!this.lr || !this.ly) {
                return;
            }
            long l2 = System.nanoTime();
            if (bl2 && this.lx + lw > l2) {
                return;
            }
            this.lx = l2;
            dh dh2 = this.ll.get();
            View view = this.ln.get();
            boolean bl3 = view == null || dh2 == null;
            if (bl3) {
                this.ar();
                return;
            }
            try {
                this.a(this.c(view));
            }
            catch (JSONException var6_5) {
                dw.b("Active view update failed.", var6_5);
            }
            this.au();
            this.as();
            return;
        }
    }

    public void onGlobalLayout() {
        this.d(false);
    }

    public void onScrollChanged() {
        this.d(true);
    }

}

