package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: com.google.android.gms.tagmanager.i */
class C1416i extends df {
    private static final String ID;
    private static final String URL;
    private static final String WC;
    private static final String WD;
    static final String WE;
    private static final Set<String> WF;
    private final C0521a WG;
    private final Context mContext;

    /* renamed from: com.google.android.gms.tagmanager.i.a */
    public interface C0521a {
        aq jY();
    }

    /* renamed from: com.google.android.gms.tagmanager.i.1 */
    class C10781 implements C0521a {
        final /* synthetic */ Context pB;

        C10781(Context context) {
            this.pB = context;
        }

        public aq jY() {
            return C1093y.m2568F(this.pB);
        }
    }

    static {
        ID = C0321a.ARBITRARY_PIXEL.toString();
        URL = C0325b.URL.toString();
        WC = C0325b.ADDITIONAL_PARAMS.toString();
        WD = C0325b.UNREPEATABLE.toString();
        WE = "gtm_" + ID + "_unrepeatable";
        WF = new HashSet();
    }

    public C1416i(Context context) {
        this(context, new C10781(context));
    }

    C1416i(Context context, C0521a c0521a) {
        super(ID, URL);
        this.WG = c0521a;
        this.mContext = context;
    }

    private synchronized boolean bj(String str) {
        boolean z = true;
        synchronized (this) {
            if (!bl(str)) {
                if (bk(str)) {
                    WF.add(str);
                } else {
                    z = false;
                }
            }
        }
        return z;
    }

    boolean bk(String str) {
        return this.mContext.getSharedPreferences(WE, 0).contains(str);
    }

    boolean bl(String str) {
        return WF.contains(str);
    }

    public void m3226z(Map<String, C1367a> map) {
        String j = map.get(WD) != null ? dh.m1460j((C1367a) map.get(WD)) : null;
        if (j == null || !bj(j)) {
            Builder buildUpon = Uri.parse(dh.m1460j((C1367a) map.get(URL))).buildUpon();
            C1367a c1367a = (C1367a) map.get(WC);
            if (c1367a != null) {
                Object o = dh.m1468o(c1367a);
                if (o instanceof List) {
                    for (Object o2 : (List) o2) {
                        if (o2 instanceof Map) {
                            for (Entry entry : ((Map) o2).entrySet()) {
                                buildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                            }
                        } else {
                            bh.m1384w("ArbitraryPixel: additional params contains non-map: not sending partial hit: " + buildUpon.build().toString());
                            return;
                        }
                    }
                }
                bh.m1384w("ArbitraryPixel: additional params not a list: not sending partial hit: " + buildUpon.build().toString());
                return;
            }
            String uri = buildUpon.build().toString();
            this.WG.jY().bz(uri);
            bh.m1386y("ArbitraryPixel: url = " + uri);
            if (j != null) {
                synchronized (C1416i.class) {
                    WF.add(j);
                    cy.m1452a(this.mContext, WE, j, "true");
                }
            }
        }
    }
}
