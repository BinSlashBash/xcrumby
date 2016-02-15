package com.google.android.gms.internal;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.internal.cr.C0346a;
import com.google.android.gms.internal.cu.C0353a;
import com.google.android.gms.internal.cx.C0354a;
import com.google.android.gms.internal.ea.C0368a;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.zip.JSONzip;

public class cs extends C0359do implements C0353a, C0368a {
    private final bq ky;
    private final dz lC;
    private final Object li;
    private final Context mContext;
    private bj mR;
    private final C0346a oG;
    private final Object oH;
    private final C0354a oI;
    private final C0410l oJ;
    private C0359do oK;
    private cz oL;
    private boolean oM;
    private bh oN;
    private bn oO;

    /* renamed from: com.google.android.gms.internal.cs.1 */
    class C03471 implements Runnable {
        final /* synthetic */ cs oP;

        C03471(cs csVar) {
            this.oP = csVar;
        }

        public void run() {
            this.oP.onStop();
        }
    }

    /* renamed from: com.google.android.gms.internal.cs.2 */
    class C03482 implements Runnable {
        final /* synthetic */ cs oP;
        final /* synthetic */ dh oQ;

        C03482(cs csVar, dh dhVar) {
            this.oP = csVar;
            this.oQ = dhVar;
        }

        public void run() {
            synchronized (this.oP.li) {
                this.oP.oG.m700a(this.oQ);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.cs.3 */
    class C03493 implements Runnable {
        final /* synthetic */ cs oP;

        C03493(cs csVar) {
            this.oP = csVar;
        }

        public void run() {
            synchronized (this.oP.li) {
                if (this.oP.oL.errorCode != -2) {
                    return;
                }
                this.oP.lC.bI().m841a(this.oP);
                if (this.oP.oL.errorCode == -3) {
                    dw.m822y("Loading URL in WebView: " + this.oP.oL.ol);
                    this.oP.lC.loadUrl(this.oP.oL.ol);
                } else {
                    dw.m822y("Loading HTML in WebView.");
                    this.oP.lC.loadDataWithBaseURL(dq.m795r(this.oP.oL.ol), this.oP.oL.pm, "text/html", Hex.DEFAULT_CHARSET_NAME, null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.cs.4 */
    class C03504 implements Runnable {
        final /* synthetic */ cs oP;
        final /* synthetic */ ct oR;

        C03504(cs csVar, ct ctVar) {
            this.oP = csVar;
            this.oR = ctVar;
        }

        public void run() {
            synchronized (this.oP.li) {
                if (this.oP.oL.errorCode != -2) {
                    return;
                }
                this.oP.lC.bI().m841a(this.oP);
                this.oR.m712b(this.oP.oL);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.cs.a */
    private static final class C0351a extends Exception {
        private final int oS;

        public C0351a(String str, int i) {
            super(str);
            this.oS = i;
        }

        public int getErrorCode() {
            return this.oS;
        }
    }

    public cs(Context context, C0354a c0354a, C0410l c0410l, dz dzVar, bq bqVar, C0346a c0346a) {
        this.oH = new Object();
        this.li = new Object();
        this.oM = false;
        this.ky = bqVar;
        this.oG = c0346a;
        this.lC = dzVar;
        this.mContext = context;
        this.oI = c0354a;
        this.oJ = c0410l;
    }

    private ak m2077a(cx cxVar) throws C0351a {
        if (this.oL.pr == null) {
            throw new C0351a("The ad response must specify one of the supported ad sizes.", 0);
        }
        String[] split = this.oL.pr.split("x");
        if (split.length != 2) {
            throw new C0351a("Could not parse the ad size from the ad response: " + this.oL.pr, 0);
        }
        try {
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            for (ak akVar : cxVar.kN.lU) {
                float f = this.mContext.getResources().getDisplayMetrics().density;
                int i = akVar.width == -1 ? (int) (((float) akVar.widthPixels) / f) : akVar.width;
                int i2 = akVar.height == -2 ? (int) (((float) akVar.heightPixels) / f) : akVar.height;
                if (parseInt == i && parseInt2 == i2) {
                    return new ak(akVar, cxVar.kN.lU);
                }
            }
            throw new C0351a("The ad size from the ad response was not one of the requested sizes: " + this.oL.pr, 0);
        } catch (NumberFormatException e) {
            throw new C0351a("Could not parse the ad size from the ad response: " + this.oL.pr, 0);
        }
    }

    private void m2079a(cx cxVar, long j) throws C0351a {
        synchronized (this.oH) {
            this.oN = new bh(this.mContext, cxVar, this.ky, this.mR);
        }
        this.oO = this.oN.m660a(j, 60000);
        switch (this.oO.nw) {
            case JSONzip.zipEmptyObject /*0*/:
            case Std.STD_FILE /*1*/:
                throw new C0351a("No fill from any mediation ad networks.", 3);
            default:
                throw new C0351a("Unexpected mediation result: " + this.oO.nw, 0);
        }
    }

    private void aZ() throws C0351a {
        if (this.oL.errorCode != -3) {
            if (TextUtils.isEmpty(this.oL.pm)) {
                throw new C0351a("No fill from ad server.", 3);
            } else if (this.oL.po) {
                try {
                    this.mR = new bj(this.oL.pm);
                } catch (JSONException e) {
                    throw new C0351a("Could not parse mediation config: " + this.oL.pm, 0);
                }
            }
        }
    }

    private void m2081b(long j) throws C0351a {
        dv.rp.post(new C03493(this));
        m2085e(j);
    }

    private void m2084d(long j) throws C0351a {
        while (m2086f(j)) {
            if (this.oL != null) {
                synchronized (this.oH) {
                    this.oK = null;
                }
                if (this.oL.errorCode != -2 && this.oL.errorCode != -3) {
                    throw new C0351a("There was a problem getting an ad response. ErrorCode: " + this.oL.errorCode, this.oL.errorCode);
                }
                return;
            }
        }
        throw new C0351a("Timed out waiting for ad response.", 2);
    }

    private void m2085e(long j) throws C0351a {
        while (m2086f(j)) {
            if (this.oM) {
                return;
            }
        }
        throw new C0351a("Timed out waiting for WebView to finish loading.", 2);
    }

    private boolean m2086f(long j) throws C0351a {
        long elapsedRealtime = 60000 - (SystemClock.elapsedRealtime() - j);
        if (elapsedRealtime <= 0) {
            return false;
        }
        try {
            this.li.wait(elapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            throw new C0351a("Ad request cancelled.", -1);
        }
    }

    public void m2087a(cz czVar) {
        synchronized (this.li) {
            dw.m819v("Received ad response.");
            this.oL = czVar;
            this.li.notify();
        }
    }

    public void m2088a(dz dzVar) {
        synchronized (this.li) {
            dw.m819v("WebView finished loading.");
            this.oM = true;
            this.li.notify();
        }
    }

    public void aY() {
        synchronized (this.li) {
            long j;
            ak akVar;
            dw.m819v("AdLoaderBackgroundTask started.");
            cx cxVar = new cx(this.oI, this.oJ.m1183y().m1040a(this.mContext));
            ak akVar2 = null;
            int i = -2;
            long j2 = -1;
            try {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                C0359do a = cu.m714a(this.mContext, cxVar, this);
                synchronized (this.oH) {
                    this.oK = a;
                    if (this.oK == null) {
                        throw new C0351a("Could not start the ad request service.", 0);
                    }
                }
                m2084d(elapsedRealtime);
                j2 = SystemClock.elapsedRealtime();
                aZ();
                if (cxVar.kN.lU != null) {
                    akVar2 = m2077a(cxVar);
                }
                if (this.oL.po) {
                    m2079a(cxVar, elapsedRealtime);
                } else if (this.oL.pu) {
                    m2089c(elapsedRealtime);
                } else {
                    m2081b(elapsedRealtime);
                }
                j = j2;
                akVar = akVar2;
            } catch (C0351a e) {
                i = e.getErrorCode();
                if (i == 3 || i == -1) {
                    dw.m821x(e.getMessage());
                } else {
                    dw.m823z(e.getMessage());
                }
                if (this.oL == null) {
                    this.oL = new cz(i);
                } else {
                    this.oL = new cz(i, this.oL.ni);
                }
                dv.rp.post(new C03471(this));
                j = j2;
                akVar = akVar2;
            }
            if (!TextUtils.isEmpty(this.oL.pw)) {
                try {
                    JSONObject jSONObject = new JSONObject(this.oL.pw);
                } catch (Throwable e2) {
                    dw.m816b("Error parsing the JSON for Active View.", e2);
                }
                dv.rp.post(new C03482(this, new dh(cxVar.pg, this.lC, this.oL.ne, i, this.oL.nf, this.oL.pq, this.oL.orientation, this.oL.ni, cxVar.pj, this.oL.po, this.oO == null ? this.oO.nx : null, this.oO == null ? this.oO.ny : null, this.oO == null ? this.oO.nz : null, this.mR, this.oO == null ? this.oO.nA : null, this.oL.pp, akVar, this.oL.pn, j, this.oL.ps, this.oL.pt, r29)));
            }
            JSONObject jSONObject2 = null;
            if (this.oO == null) {
            }
            if (this.oO == null) {
            }
            if (this.oO == null) {
            }
            if (this.oO == null) {
            }
            dv.rp.post(new C03482(this, new dh(cxVar.pg, this.lC, this.oL.ne, i, this.oL.nf, this.oL.pq, this.oL.orientation, this.oL.ni, cxVar.pj, this.oL.po, this.oO == null ? this.oO.nx : null, this.oO == null ? this.oO.ny : null, this.oO == null ? this.oO.nz : null, this.mR, this.oO == null ? this.oO.nA : null, this.oL.pp, akVar, this.oL.pn, j, this.oL.ps, this.oL.pt, jSONObject2)));
        }
    }

    protected void m2089c(long j) throws C0351a {
        int i;
        int i2;
        ak R = this.lC.m828R();
        if (R.lT) {
            i = this.mContext.getResources().getDisplayMetrics().widthPixels;
            i2 = this.mContext.getResources().getDisplayMetrics().heightPixels;
        } else {
            i = R.widthPixels;
            i2 = R.heightPixels;
        }
        ct ctVar = new ct(this, this.lC, i, i2);
        dv.rp.post(new C03504(this, ctVar));
        m2085e(j);
        if (ctVar.bc()) {
            dw.m819v("Ad-Network indicated no fill with passback URL.");
            throw new C0351a("AdNetwork sent passback url", 3);
        } else if (!ctVar.bd()) {
            throw new C0351a("AdNetwork timed out", 2);
        }
    }

    public void onStop() {
        synchronized (this.oH) {
            if (this.oK != null) {
                this.oK.cancel();
            }
            this.lC.stopLoading();
            dq.m781a(this.lC);
            if (this.oN != null) {
                this.oN.cancel();
            }
        }
    }
}
