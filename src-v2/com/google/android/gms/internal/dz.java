/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.MutableContextWrapper
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.WindowManager
 *  android.webkit.DownloadListener
 *  android.webkit.WebChromeClient
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.cc;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.ds;
import com.google.android.gms.internal.dt;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.ea;
import com.google.android.gms.internal.eb;
import com.google.android.gms.internal.ec;
import com.google.android.gms.internal.ed;
import com.google.android.gms.internal.l;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class dz
extends WebView
implements DownloadListener {
    private final Object li = new Object();
    private final WindowManager ls;
    private ak nq;
    private final dx nr;
    private final l oJ;
    private final ea ru;
    private final a rv;
    private cc rw;
    private boolean rx;
    private boolean ry;

    /*
     * Enabled aggressive block sorting
     */
    private dz(a a2, ak ak2, boolean bl2, boolean bl3, l l2, dx dx2) {
        super((Context)a2);
        this.rv = a2;
        this.nq = ak2;
        this.rx = bl2;
        this.oJ = l2;
        this.nr = dx2;
        this.ls = (WindowManager)this.getContext().getSystemService("window");
        this.setBackgroundColor(0);
        ak2 = this.getSettings();
        ak2.setJavaScriptEnabled(true);
        ak2.setSavePassword(false);
        ak2.setSupportMultipleWindows(true);
        ak2.setJavaScriptCanOpenWindowsAutomatically(true);
        dq.a((Context)a2, dx2.rq, (WebSettings)ak2);
        if (Build.VERSION.SDK_INT >= 17) {
            dt.a(this.getContext(), (WebSettings)ak2);
        } else if (Build.VERSION.SDK_INT >= 11) {
            ds.a(this.getContext(), (WebSettings)ak2);
        }
        this.setDownloadListener((DownloadListener)this);
        this.ru = Build.VERSION.SDK_INT >= 11 ? new ec(this, bl3) : new ea(this, bl3);
        this.setWebViewClient((WebViewClient)this.ru);
        if (Build.VERSION.SDK_INT >= 14) {
            this.setWebChromeClient((WebChromeClient)new ed(this));
        } else if (Build.VERSION.SDK_INT >= 11) {
            this.setWebChromeClient((WebChromeClient)new eb(this));
        }
        this.bM();
    }

    public static dz a(Context context, ak ak2, boolean bl2, boolean bl3, l l2, dx dx2) {
        return new dz(new a(context), ak2, bl2, bl3, l2, dx2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void bM() {
        Object object = this.li;
        synchronized (object) {
            if (this.rx || this.nq.lT) {
                if (Build.VERSION.SDK_INT < 14) {
                    dw.v("Disabling hardware acceleration on an overlay.");
                    this.bN();
                } else {
                    dw.v("Enabling hardware acceleration on an overlay.");
                    this.bO();
                }
            } else if (Build.VERSION.SDK_INT < 18) {
                dw.v("Disabling hardware acceleration on an AdView.");
                this.bN();
            } else {
                dw.v("Enabling hardware acceleration on an AdView.");
                this.bO();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void bN() {
        Object object = this.li;
        synchronized (object) {
            if (!this.ry && Build.VERSION.SDK_INT >= 11) {
                ds.d((View)this);
            }
            this.ry = true;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void bO() {
        Object object = this.li;
        synchronized (object) {
            if (this.ry && Build.VERSION.SDK_INT >= 11) {
                ds.e((View)this);
            }
            this.ry = false;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ak R() {
        Object object = this.li;
        synchronized (object) {
            return this.nq;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(Context context, ak ak2) {
        Object object = this.li;
        synchronized (object) {
            this.rv.setBaseContext(context);
            this.rw = null;
            this.nq = ak2;
            this.rx = false;
            dq.b(this);
            this.loadUrl("about:blank");
            this.ru.reset();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(ak ak2) {
        Object object = this.li;
        synchronized (object) {
            this.nq = ak2;
            this.requestLayout();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(cc cc2) {
        Object object = this.li;
        synchronized (object) {
            this.rw = cc2;
            return;
        }
    }

    public void a(String string2, Map<String, ?> object) {
        try {
            object = dq.p(object);
        }
        catch (JSONException var1_2) {
            dw.z("Could not convert parameters to JSON.");
            return;
        }
        this.b(string2, (JSONObject)object);
    }

    public void a(String string2, JSONObject object) {
        Object object2 = object;
        if (object == null) {
            object2 = new JSONObject();
        }
        object = object2.toString();
        object2 = new StringBuilder();
        object2.append("javascript:" + string2 + "(");
        object2.append((String)object);
        object2.append(");");
        this.loadUrl(object2.toString());
    }

    public void b(String string2, JSONObject object) {
        Object object2 = object;
        if (object == null) {
            object2 = new JSONObject();
        }
        object = object2.toString();
        object2 = new StringBuilder();
        object2.append("javascript:AFMA_ReceiveMessage('");
        object2.append(string2);
        object2.append("'");
        object2.append(",");
        object2.append((String)object);
        object2.append(");");
        dw.y("Dispatching AFMA event: " + object2);
        this.loadUrl(object2.toString());
    }

    public void bE() {
        if (!this.bI().bP()) {
            return;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = this.ls.getDefaultDisplay();
        display.getMetrics(displayMetrics);
        try {
            this.b("onScreenInfoChanged", new JSONObject().put("width", displayMetrics.widthPixels).put("height", displayMetrics.heightPixels).put("density", displayMetrics.density).put("rotation", display.getRotation()));
            return;
        }
        catch (JSONException var1_2) {
            dw.b("Error occured while obtaining screen information.", var1_2);
            return;
        }
    }

    public void bF() {
        HashMap<String, String> hashMap = new HashMap<String, String>(1);
        hashMap.put("version", this.nr.rq);
        this.a("onhide", hashMap);
    }

    public void bG() {
        HashMap<String, String> hashMap = new HashMap<String, String>(1);
        hashMap.put("version", this.nr.rq);
        this.a("onshow", hashMap);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public cc bH() {
        Object object = this.li;
        synchronized (object) {
            return this.rw;
        }
    }

    public ea bI() {
        return this.ru;
    }

    public l bJ() {
        return this.oJ;
    }

    public dx bK() {
        return this.nr;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean bL() {
        Object object = this.li;
        synchronized (object) {
            return this.rx;
        }
    }

    public void onDownloadStart(String string2, String string3, String string4, String string5, long l2) {
        try {
            string3 = new Intent("android.intent.action.VIEW");
            string3.setDataAndType(Uri.parse((String)string2), string5);
            this.getContext().startActivity((Intent)string3);
            return;
        }
        catch (ActivityNotFoundException var2_3) {
            dw.v("Couldn't find an Activity to view url/mimetype: " + string2 + " / " + string5);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onMeasure(int n2, int n3) {
        int n4 = Integer.MAX_VALUE;
        Object object = this.li;
        synchronized (object) {
            int n5;
            int n6;
            block10 : {
                if (this.isInEditMode() || this.rx) {
                    super.onMeasure(n2, n3);
                    return;
                }
                int n7 = View.MeasureSpec.getMode((int)n2);
                n5 = View.MeasureSpec.getSize((int)n2);
                int n8 = View.MeasureSpec.getMode((int)n3);
                n6 = View.MeasureSpec.getSize((int)n3);
                n2 = n7 != Integer.MIN_VALUE && n7 != 1073741824 ? Integer.MAX_VALUE : n5;
                if (n8 != Integer.MIN_VALUE) {
                    n3 = n4;
                    if (n8 != 1073741824) break block10;
                }
                n3 = n6;
            }
            if (this.nq.widthPixels > n2 || this.nq.heightPixels > n3) {
                dw.z("Not enough space to show ad. Needs " + this.nq.widthPixels + "x" + this.nq.heightPixels + " pixels, but only has " + n5 + "x" + n6 + " pixels.");
                if (this.getVisibility() != 8) {
                    this.setVisibility(4);
                }
                this.setMeasuredDimension(0, 0);
            } else {
                if (this.getVisibility() != 8) {
                    this.setVisibility(0);
                }
                this.setMeasuredDimension(this.nq.widthPixels, this.nq.heightPixels);
            }
            return;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.oJ != null) {
            this.oJ.a(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void p(boolean bl2) {
        Object object = this.li;
        synchronized (object) {
            this.rx = bl2;
            this.bM();
            return;
        }
    }

    public void setContext(Context context) {
        this.rv.setBaseContext(context);
    }

    private static class a
    extends MutableContextWrapper {
        private Context lp;
        private Activity rz;

        public a(Context context) {
            super(context);
            this.setBaseContext(context);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void setBaseContext(Context context) {
            this.lp = context.getApplicationContext();
            context = context instanceof Activity ? (Activity)context : null;
            this.rz = context;
            super.setBaseContext(this.lp);
        }

        public void startActivity(Intent intent) {
            if (this.rz != null) {
                this.rz.startActivity(intent);
                return;
            }
            intent.setFlags(268435456);
            this.lp.startActivity(intent);
        }
    }

}

