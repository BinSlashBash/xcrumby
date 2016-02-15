package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.android.gms.drive.DriveFile;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class dz extends WebView implements DownloadListener {
    private final Object li;
    private final WindowManager ls;
    private ak nq;
    private final dx nr;
    private final C0410l oJ;
    private final ea ru;
    private final C0366a rv;
    private cc rw;
    private boolean rx;
    private boolean ry;

    /* renamed from: com.google.android.gms.internal.dz.a */
    private static class C0366a extends MutableContextWrapper {
        private Context lp;
        private Activity rz;

        public C0366a(Context context) {
            super(context);
            setBaseContext(context);
        }

        public void setBaseContext(Context base) {
            this.lp = base.getApplicationContext();
            this.rz = base instanceof Activity ? (Activity) base : null;
            super.setBaseContext(this.lp);
        }

        public void startActivity(Intent intent) {
            if (this.rz != null) {
                this.rz.startActivity(intent);
                return;
            }
            intent.setFlags(DriveFile.MODE_READ_ONLY);
            this.lp.startActivity(intent);
        }
    }

    private dz(C0366a c0366a, ak akVar, boolean z, boolean z2, C0410l c0410l, dx dxVar) {
        super(c0366a);
        this.li = new Object();
        this.rv = c0366a;
        this.nq = akVar;
        this.rx = z;
        this.oJ = c0410l;
        this.nr = dxVar;
        this.ls = (WindowManager) getContext().getSystemService("window");
        setBackgroundColor(0);
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        dq.m778a((Context) c0366a, dxVar.rq, settings);
        if (VERSION.SDK_INT >= 17) {
            dt.m807a(getContext(), settings);
        } else if (VERSION.SDK_INT >= 11) {
            ds.m801a(getContext(), settings);
        }
        setDownloadListener(this);
        if (VERSION.SDK_INT >= 11) {
            this.ru = new ec(this, z2);
        } else {
            this.ru = new ea(this, z2);
        }
        setWebViewClient(this.ru);
        if (VERSION.SDK_INT >= 14) {
            setWebChromeClient(new ed(this));
        } else if (VERSION.SDK_INT >= 11) {
            setWebChromeClient(new eb(this));
        }
        bM();
    }

    public static dz m827a(Context context, ak akVar, boolean z, boolean z2, C0410l c0410l, dx dxVar) {
        return new dz(new C0366a(context), akVar, z, z2, c0410l, dxVar);
    }

    private void bM() {
        synchronized (this.li) {
            if (this.rx || this.nq.lT) {
                if (VERSION.SDK_INT < 14) {
                    dw.m819v("Disabling hardware acceleration on an overlay.");
                    bN();
                } else {
                    dw.m819v("Enabling hardware acceleration on an overlay.");
                    bO();
                }
            } else if (VERSION.SDK_INT < 18) {
                dw.m819v("Disabling hardware acceleration on an AdView.");
                bN();
            } else {
                dw.m819v("Enabling hardware acceleration on an AdView.");
                bO();
            }
        }
    }

    private void bN() {
        synchronized (this.li) {
            if (!this.ry && VERSION.SDK_INT >= 11) {
                ds.m805d(this);
            }
            this.ry = true;
        }
    }

    private void bO() {
        synchronized (this.li) {
            if (this.ry && VERSION.SDK_INT >= 11) {
                ds.m806e(this);
            }
            this.ry = false;
        }
    }

    public ak m828R() {
        ak akVar;
        synchronized (this.li) {
            akVar = this.nq;
        }
        return akVar;
    }

    public void m829a(Context context, ak akVar) {
        synchronized (this.li) {
            this.rv.setBaseContext(context);
            this.rw = null;
            this.nq = akVar;
            this.rx = false;
            dq.m788b((WebView) this);
            loadUrl("about:blank");
            this.ru.reset();
        }
    }

    public void m830a(ak akVar) {
        synchronized (this.li) {
            this.nq = akVar;
            requestLayout();
        }
    }

    public void m831a(cc ccVar) {
        synchronized (this.li) {
            this.rw = ccVar;
        }
    }

    public void m832a(String str, Map<String, ?> map) {
        try {
            m834b(str, dq.m794p(map));
        } catch (JSONException e) {
            dw.m823z("Could not convert parameters to JSON.");
        }
    }

    public void m833a(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        String jSONObject2 = jSONObject.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:" + str + "(");
        stringBuilder.append(jSONObject2);
        stringBuilder.append(");");
        loadUrl(stringBuilder.toString());
    }

    public void m834b(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        String jSONObject2 = jSONObject.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:AFMA_ReceiveMessage('");
        stringBuilder.append(str);
        stringBuilder.append("'");
        stringBuilder.append(",");
        stringBuilder.append(jSONObject2);
        stringBuilder.append(");");
        dw.m822y("Dispatching AFMA event: " + stringBuilder);
        loadUrl(stringBuilder.toString());
    }

    public void bE() {
        if (bI().bP()) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Display defaultDisplay = this.ls.getDefaultDisplay();
            defaultDisplay.getMetrics(displayMetrics);
            try {
                m834b("onScreenInfoChanged", new JSONObject().put("width", displayMetrics.widthPixels).put("height", displayMetrics.heightPixels).put("density", (double) displayMetrics.density).put("rotation", defaultDisplay.getRotation()));
            } catch (Throwable e) {
                dw.m816b("Error occured while obtaining screen information.", e);
            }
        }
    }

    public void bF() {
        Map hashMap = new HashMap(1);
        hashMap.put("version", this.nr.rq);
        m832a("onhide", hashMap);
    }

    public void bG() {
        Map hashMap = new HashMap(1);
        hashMap.put("version", this.nr.rq);
        m832a("onshow", hashMap);
    }

    public cc bH() {
        cc ccVar;
        synchronized (this.li) {
            ccVar = this.rw;
        }
        return ccVar;
    }

    public ea bI() {
        return this.ru;
    }

    public C0410l bJ() {
        return this.oJ;
    }

    public dx bK() {
        return this.nr;
    }

    public boolean bL() {
        boolean z;
        synchronized (this.li) {
            z = this.rx;
        }
        return z;
    }

    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long size) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(url), mimeType);
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            dw.m819v("Couldn't find an Activity to view url/mimetype: " + url + " / " + mimeType);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = Integer.MAX_VALUE;
        synchronized (this.li) {
            if (isInEditMode() || this.rx) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                return;
            }
            int mode = MeasureSpec.getMode(widthMeasureSpec);
            int size = MeasureSpec.getSize(widthMeasureSpec);
            int mode2 = MeasureSpec.getMode(heightMeasureSpec);
            int size2 = MeasureSpec.getSize(heightMeasureSpec);
            mode = (mode == ExploreByTouchHelper.INVALID_ID || mode == 1073741824) ? size : Integer.MAX_VALUE;
            if (mode2 == ExploreByTouchHelper.INVALID_ID || mode2 == 1073741824) {
                i = size2;
            }
            if (this.nq.widthPixels > mode || this.nq.heightPixels > r0) {
                dw.m823z("Not enough space to show ad. Needs " + this.nq.widthPixels + "x" + this.nq.heightPixels + " pixels, but only has " + size + "x" + size2 + " pixels.");
                if (getVisibility() != 8) {
                    setVisibility(4);
                }
                setMeasuredDimension(0, 0);
            } else {
                if (getVisibility() != 8) {
                    setVisibility(0);
                }
                setMeasuredDimension(this.nq.widthPixels, this.nq.heightPixels);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.oJ != null) {
            this.oJ.m1181a(event);
        }
        return super.onTouchEvent(event);
    }

    public void m835p(boolean z) {
        synchronized (this.li) {
            this.rx = z;
            bM();
        }
    }

    public void setContext(Context context) {
        this.rv.setBaseContext(context);
    }
}
