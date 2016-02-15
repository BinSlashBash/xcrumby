package com.google.android.gms.internal;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import com.google.android.gms.internal.ad.C0323a;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ab implements OnGlobalLayoutListener, OnScrollChangedListener {
    private static final long lw;
    private HashSet<C0426y> lA;
    private final Object li;
    private final WeakReference<dh> ll;
    private WeakReference<ViewTreeObserver> lm;
    private final WeakReference<View> ln;
    private final C0427z lo;
    private final Context lp;
    private final ad lq;
    private boolean lr;
    private final WindowManager ls;
    private final PowerManager lt;
    private final KeyguardManager lu;
    private ac lv;
    private long lx;
    private boolean ly;
    private BroadcastReceiver lz;

    /* renamed from: com.google.android.gms.internal.ab.2 */
    class C03222 extends BroadcastReceiver {
        final /* synthetic */ ab lB;

        C03222(ab abVar) {
            this.lB = abVar;
        }

        public void onReceive(Context context, Intent intent) {
            this.lB.m603d(false);
        }
    }

    /* renamed from: com.google.android.gms.internal.ab.1 */
    class C08231 implements C0323a {
        final /* synthetic */ ab lB;

        C08231(ab abVar) {
            this.lB = abVar;
        }

        public void ay() {
            this.lB.lr = true;
            this.lB.m603d(false);
            this.lB.ap();
        }
    }

    /* renamed from: com.google.android.gms.internal.ab.3 */
    class C08243 implements bb {
        final /* synthetic */ ab lB;

        C08243(ab abVar) {
            this.lB = abVar;
        }

        public void m1998b(dz dzVar, Map<String, String> map) {
            this.lB.m595a(dzVar, (Map) map);
        }
    }

    /* renamed from: com.google.android.gms.internal.ab.4 */
    class C08254 implements bb {
        final /* synthetic */ ab lB;

        C08254(ab abVar) {
            this.lB = abVar;
        }

        public void m1999b(dz dzVar, Map<String, String> map) {
            if (map.containsKey("pingType") && "unloadPing".equals(map.get("pingType"))) {
                this.lB.m601c(this.lB.lq);
                dw.m821x("Unregistered GMSG handlers for: " + this.lB.lo.ao());
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.ab.5 */
    class C08265 implements bb {
        final /* synthetic */ ab lB;

        C08265(ab abVar) {
            this.lB = abVar;
        }

        public void m2000b(dz dzVar, Map<String, String> map) {
            if (map.containsKey("isVisible")) {
                boolean z = "1".equals(map.get("isVisible")) || "true".equals(map.get("isVisible"));
                this.lB.m602c(Boolean.valueOf(z).booleanValue());
            }
        }
    }

    static {
        lw = TimeUnit.MILLISECONDS.toNanos(100);
    }

    public ab(ak akVar, dh dhVar) {
        this(akVar, dhVar, dhVar.oj.bK(), dhVar.oj, new ae(dhVar.oj.getContext(), dhVar.oj.bK()));
    }

    public ab(ak akVar, dh dhVar, dx dxVar, View view, ad adVar) {
        this.li = new Object();
        this.lx = Long.MIN_VALUE;
        this.lA = new HashSet();
        this.ll = new WeakReference(dhVar);
        this.ln = new WeakReference(view);
        this.lm = new WeakReference(null);
        this.ly = true;
        this.lo = new C0427z(Integer.toString(dhVar.hashCode()), dxVar, akVar.lS, dhVar.qs);
        this.lq = adVar;
        this.ls = (WindowManager) view.getContext().getSystemService("window");
        this.lt = (PowerManager) view.getContext().getApplicationContext().getSystemService("power");
        this.lu = (KeyguardManager) view.getContext().getSystemService("keyguard");
        this.lp = view.getContext().getApplicationContext();
        m594a(adVar);
        this.lq.m605a(new C08231(this));
        m599b(this.lq);
        dw.m821x("Tracking ad unit: " + this.lo.ao());
    }

    protected int m592a(int i, DisplayMetrics displayMetrics) {
        return (int) (((float) i) / displayMetrics.density);
    }

    public void m593a(ac acVar) {
        synchronized (this.li) {
            this.lv = acVar;
        }
    }

    protected void m594a(ad adVar) {
        adVar.m608d("http://googleads.g.doubleclick.net/mads/static/sdk/native/sdk-core-v40.html");
    }

    protected void m595a(dz dzVar, Map<String, String> map) {
        m603d(false);
    }

    public void m596a(C0426y c0426y) {
        this.lA.add(c0426y);
    }

    protected void m597a(JSONObject jSONObject) throws JSONException {
        Object jSONArray = new JSONArray();
        JSONObject jSONObject2 = new JSONObject();
        jSONArray.put((Object) jSONObject);
        jSONObject2.put("units", jSONArray);
        this.lq.m607a("AFMA_updateActiveView", jSONObject2);
    }

    protected boolean m598a(View view, boolean z) {
        return view.getVisibility() == 0 && z && view.isShown() && this.lt.isScreenOn() && !this.lu.inKeyguardRestrictedInputMode();
    }

    protected void ap() {
        synchronized (this.li) {
            if (this.lz != null) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.lz = new C03222(this);
            this.lp.registerReceiver(this.lz, intentFilter);
        }
    }

    protected void aq() {
        synchronized (this.li) {
            if (this.lz != null) {
                this.lp.unregisterReceiver(this.lz);
                this.lz = null;
            }
        }
    }

    public void ar() {
        synchronized (this.li) {
            if (this.ly) {
                av();
                aq();
                try {
                    m597a(ax());
                } catch (Throwable e) {
                    dw.m816b("JSON Failure while processing active view data.", e);
                }
                this.ly = false;
                as();
                dw.m821x("Untracked ad unit: " + this.lo.ao());
            }
        }
    }

    protected void as() {
        if (this.lv != null) {
            this.lv.m604a(this);
        }
    }

    public boolean at() {
        boolean z;
        synchronized (this.li) {
            z = this.ly;
        }
        return z;
    }

    protected void au() {
        View view = (View) this.ln.get();
        if (view != null) {
            ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.lm.get();
            ViewTreeObserver viewTreeObserver2 = view.getViewTreeObserver();
            if (viewTreeObserver2 != viewTreeObserver) {
                this.lm = new WeakReference(viewTreeObserver2);
                viewTreeObserver2.addOnScrollChangedListener(this);
                viewTreeObserver2.addOnGlobalLayoutListener(this);
            }
        }
    }

    protected void av() {
        ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.lm.get();
        if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnScrollChangedListener(this);
            viewTreeObserver.removeGlobalOnLayoutListener(this);
        }
    }

    protected JSONObject aw() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("afmaVersion", this.lo.am()).put("activeViewJSON", this.lo.an()).put("timestamp", TimeUnit.NANOSECONDS.toMillis(System.nanoTime())).put("adFormat", this.lo.al()).put("hashCode", this.lo.ao());
        return jSONObject;
    }

    protected JSONObject ax() throws JSONException {
        JSONObject aw = aw();
        aw.put("doneReasonCode", (Object) "u");
        return aw;
    }

    protected void m599b(ad adVar) {
        adVar.m606a("/updateActiveView", new C08243(this));
        adVar.m606a("/activeViewPingSent", new C08254(this));
        adVar.m606a("/visibilityChanged", new C08265(this));
        adVar.m606a("/viewabilityChanged", ba.mG);
    }

    protected JSONObject m600c(View view) throws JSONException {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        view.getLocationOnScreen(iArr);
        view.getLocationInWindow(iArr2);
        JSONObject aw = aw();
        DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        Rect rect = new Rect();
        rect.left = iArr[0];
        rect.top = iArr[1];
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        Rect rect2 = new Rect();
        rect2.right = this.ls.getDefaultDisplay().getWidth();
        rect2.bottom = this.ls.getDefaultDisplay().getHeight();
        Rect rect3 = new Rect();
        boolean globalVisibleRect = view.getGlobalVisibleRect(rect3, null);
        Rect rect4 = new Rect();
        view.getLocalVisibleRect(rect4);
        aw.put("viewBox", new JSONObject().put("top", m592a(rect2.top, displayMetrics)).put("bottom", m592a(rect2.bottom, displayMetrics)).put("left", m592a(rect2.left, displayMetrics)).put("right", m592a(rect2.right, displayMetrics))).put("adBox", new JSONObject().put("top", m592a(rect.top, displayMetrics)).put("bottom", m592a(rect.bottom, displayMetrics)).put("left", m592a(rect.left, displayMetrics)).put("right", m592a(rect.right, displayMetrics))).put("globalVisibleBox", new JSONObject().put("top", m592a(rect3.top, displayMetrics)).put("bottom", m592a(rect3.bottom, displayMetrics)).put("left", m592a(rect3.left, displayMetrics)).put("right", m592a(rect3.right, displayMetrics))).put("localVisibleBox", new JSONObject().put("top", m592a(rect4.top, displayMetrics)).put("bottom", m592a(rect4.bottom, displayMetrics)).put("left", m592a(rect4.left, displayMetrics)).put("right", m592a(rect4.right, displayMetrics))).put("screenDensity", (double) displayMetrics.density).put("isVisible", m598a(view, globalVisibleRect));
        return aw;
    }

    protected void m601c(ad adVar) {
        adVar.m609e("/viewabilityChanged");
        adVar.m609e("/visibilityChanged");
        adVar.m609e("/activeViewPingSent");
        adVar.m609e("/updateActiveView");
    }

    protected void m602c(boolean z) {
        Iterator it = this.lA.iterator();
        while (it.hasNext()) {
            ((C0426y) it.next()).m1217a(this, z);
        }
    }

    protected void m603d(boolean z) {
        synchronized (this.li) {
            if (this.lr && this.ly) {
                long nanoTime = System.nanoTime();
                if (!z || this.lx + lw <= nanoTime) {
                    this.lx = nanoTime;
                    View view = (View) this.ln.get();
                    Object obj = (view == null || ((dh) this.ll.get()) == null) ? 1 : null;
                    if (obj != null) {
                        ar();
                        return;
                    }
                    try {
                        m597a(m600c(view));
                    } catch (Throwable e) {
                        dw.m816b("Active view update failed.", e);
                    }
                    au();
                    as();
                    return;
                }
                return;
            }
        }
    }

    public void onGlobalLayout() {
        m603d(false);
    }

    public void onScrollChanged() {
        m603d(true);
    }
}
