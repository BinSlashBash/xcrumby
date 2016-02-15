package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.SystemClock;
import android.text.TextUtils;
import com.google.android.gms.internal.db.C0860a;
import com.google.android.gms.internal.ea.C0368a;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import uk.co.senab.photoview.IPhotoView;

public final class dc extends C0860a {
    private static final Object px;
    private static dc py;
    private final Context mContext;
    private final ax pA;
    private final bf pz;

    /* renamed from: com.google.android.gms.internal.dc.1 */
    static class C03561 implements Runnable {
        final /* synthetic */ Context pB;
        final /* synthetic */ cx pC;
        final /* synthetic */ de pD;
        final /* synthetic */ C0368a pE;
        final /* synthetic */ String pF;

        C03561(Context context, cx cxVar, de deVar, C0368a c0368a, String str) {
            this.pB = context;
            this.pC = cxVar;
            this.pD = deVar;
            this.pE = c0368a;
            this.pF = str;
        }

        public void run() {
            dz a = dz.m827a(this.pB, new ak(), false, false, null, this.pC.kK);
            a.setWillNotDraw(true);
            this.pD.m736b(a);
            ea bI = a.bI();
            bI.m843a("/invalidRequest", this.pD.pK);
            bI.m843a("/loadAdURL", this.pD.pL);
            bI.m843a("/log", ba.mM);
            bI.m841a(this.pE);
            dw.m819v("Loading the JS library.");
            a.loadUrl(this.pF);
        }
    }

    /* renamed from: com.google.android.gms.internal.dc.2 */
    static class C08612 implements C0368a {
        final /* synthetic */ String pG;

        C08612(String str) {
            this.pG = str;
        }

        public void m2093a(dz dzVar) {
            String format = String.format("javascript:%s(%s);", new Object[]{"AFMA_buildAdURL", this.pG});
            dw.m822y("About to execute: " + format);
            dzVar.loadUrl(format);
        }
    }

    static {
        px = new Object();
    }

    private dc(Context context, ax axVar, bf bfVar) {
        this.mContext = context;
        this.pz = bfVar;
        this.pA = axVar;
    }

    private static cz m3002a(Context context, ax axVar, bf bfVar, cx cxVar) {
        dw.m819v("Starting ad request from service.");
        bfVar.init();
        dg dgVar = new dg(context);
        if (dgVar.qk == -1) {
            dw.m819v("Device is offline.");
            return new cz(2);
        }
        de deVar = new de(cxVar.applicationInfo.packageName);
        if (cxVar.pg.extras != null) {
            String string = cxVar.pg.extras.getString("_ad");
            if (string != null) {
                return dd.m724a(context, cxVar, string);
            }
        }
        Location a = bfVar.m659a(250);
        String aH = axVar.aH();
        String a2 = dd.m725a(cxVar, dgVar, a, axVar.aI());
        if (a2 == null) {
            return new cz(0);
        }
        dv.rp.post(new C03561(context, cxVar, deVar, m3006p(a2), aH));
        a2 = deVar.bj();
        return TextUtils.isEmpty(a2) ? new cz(deVar.getErrorCode()) : m3003a(context, cxVar.kK.rq, a2);
    }

    public static cz m3003a(Context context, String str, String str2) {
        HttpURLConnection httpURLConnection;
        try {
            int responseCode;
            cz czVar;
            df dfVar = new df();
            URL url = new URL(str2);
            long elapsedRealtime = SystemClock.elapsedRealtime();
            URL url2 = url;
            int i = 0;
            while (true) {
                httpURLConnection = (HttpURLConnection) url2.openConnection();
                dq.m780a(context, str, false, httpURLConnection);
                responseCode = httpURLConnection.getResponseCode();
                Map headerFields = httpURLConnection.getHeaderFields();
                if (responseCode < IPhotoView.DEFAULT_ZOOM_DURATION || responseCode >= 300) {
                    m3005a(url2.toString(), headerFields, null, responseCode);
                    if (responseCode < 300 || responseCode >= 400) {
                        break;
                    }
                    Object headerField = httpURLConnection.getHeaderField("Location");
                    if (TextUtils.isEmpty(headerField)) {
                        dw.m823z("No location header to follow redirect.");
                        czVar = new cz(0);
                        httpURLConnection.disconnect();
                        return czVar;
                    }
                    url2 = new URL(headerField);
                    i++;
                    if (i > 5) {
                        dw.m823z("Too many redirects.");
                        czVar = new cz(0);
                        httpURLConnection.disconnect();
                        return czVar;
                    }
                    dfVar.m751e(headerFields);
                    httpURLConnection.disconnect();
                } else {
                    String url3 = url2.toString();
                    String a = dq.m774a(new InputStreamReader(httpURLConnection.getInputStream()));
                    m3005a(url3, headerFields, a, responseCode);
                    dfVar.m750a(url3, headerFields, a);
                    czVar = dfVar.m752g(elapsedRealtime);
                    httpURLConnection.disconnect();
                    return czVar;
                }
            }
            dw.m823z("Received error HTTP response code: " + responseCode);
            czVar = new cz(0);
            httpURLConnection.disconnect();
            return czVar;
        } catch (IOException e) {
            dw.m823z("Error while connecting to ad server: " + e.getMessage());
            return new cz(2);
        } catch (Throwable th) {
            httpURLConnection.disconnect();
        }
    }

    public static dc m3004a(Context context, ax axVar, bf bfVar) {
        dc dcVar;
        synchronized (px) {
            if (py == null) {
                py = new dc(context.getApplicationContext(), axVar, bfVar);
            }
            dcVar = py;
        }
        return dcVar;
    }

    private static void m3005a(String str, Map<String, List<String>> map, String str2, int i) {
        if (dw.m818n(2)) {
            dw.m822y("Http Response: {\n  URL:\n    " + str + "\n  Headers:");
            if (map != null) {
                for (String str3 : map.keySet()) {
                    dw.m822y("    " + str3 + ":");
                    for (String str32 : (List) map.get(str32)) {
                        dw.m822y("      " + str32);
                    }
                }
            }
            dw.m822y("  Body:");
            if (str2 != null) {
                for (int i2 = 0; i2 < Math.min(str2.length(), 100000); i2 += GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE) {
                    dw.m822y(str2.substring(i2, Math.min(str2.length(), i2 + GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE)));
                }
            } else {
                dw.m822y("    null");
            }
            dw.m822y("  Response Code:\n    " + i + "\n}");
        }
    }

    private static C0368a m3006p(String str) {
        return new C08612(str);
    }

    public cz m3007b(cx cxVar) {
        return m3002a(this.mContext, this.pA, this.pz, cxVar);
    }
}
