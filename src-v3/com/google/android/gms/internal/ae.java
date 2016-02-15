package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.ad.C0323a;
import com.google.android.gms.internal.ea.C0368a;
import org.json.JSONObject;

public class ae implements ad {
    private final dz lC;

    /* renamed from: com.google.android.gms.internal.ae.1 */
    class C08271 implements C0368a {
        final /* synthetic */ C0323a lD;
        final /* synthetic */ ae lE;

        C08271(ae aeVar, C0323a c0323a) {
            this.lE = aeVar;
            this.lD = c0323a;
        }

        public void m2001a(dz dzVar) {
            this.lD.ay();
        }
    }

    public ae(Context context, dx dxVar) {
        this.lC = dz.m827a(context, new ak(), false, false, null, dxVar);
    }

    public void m2002a(C0323a c0323a) {
        this.lC.bI().m841a(new C08271(this, c0323a));
    }

    public void m2003a(String str, bb bbVar) {
        this.lC.bI().m843a(str, bbVar);
    }

    public void m2004a(String str, JSONObject jSONObject) {
        this.lC.m833a(str, jSONObject);
    }

    public void m2005d(String str) {
        this.lC.loadUrl(str);
    }

    public void m2006e(String str) {
        this.lC.bI().m843a(str, null);
    }
}
