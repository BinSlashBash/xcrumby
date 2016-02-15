package com.google.android.gms.internal;

import com.google.android.gms.plus.PlusShare;
import java.util.Map;

public final class de {
    private dz lC;
    private final Object li;
    private int oS;
    private String pI;
    private String pJ;
    public final bb pK;
    public final bb pL;

    /* renamed from: com.google.android.gms.internal.de.1 */
    class C08621 implements bb {
        final /* synthetic */ de pM;

        C08621(de deVar) {
            this.pM = deVar;
        }

        public void m2094b(dz dzVar, Map<String, String> map) {
            synchronized (this.pM.li) {
                String str = (String) map.get("errors");
                dw.m823z("Invalid " + ((String) map.get("type")) + " request error: " + str);
                this.pM.oS = 1;
                this.pM.li.notify();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.de.2 */
    class C08632 implements bb {
        final /* synthetic */ de pM;

        C08632(de deVar) {
            this.pM = deVar;
        }

        public void m2095b(dz dzVar, Map<String, String> map) {
            synchronized (this.pM.li) {
                String str = (String) map.get(PlusShare.KEY_CALL_TO_ACTION_URL);
                if (str == null) {
                    dw.m823z("URL missing in loadAdUrl GMSG.");
                    return;
                }
                if (str.contains("%40mediation_adapters%40")) {
                    str = str.replaceAll("%40mediation_adapters%40", dn.m771b(dzVar.getContext(), (String) map.get("check_adapters"), this.pM.pI));
                    dw.m822y("Ad request URL modified to " + str);
                }
                this.pM.pJ = str;
                this.pM.li.notify();
            }
        }
    }

    public de(String str) {
        this.li = new Object();
        this.oS = -2;
        this.pK = new C08621(this);
        this.pL = new C08632(this);
        this.pI = str;
    }

    public void m736b(dz dzVar) {
        synchronized (this.li) {
            this.lC = dzVar;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String bj() {
        /*
        r3 = this;
        r1 = r3.li;
        monitor-enter(r1);
    L_0x0003:
        r0 = r3.pJ;	 Catch:{ all -> 0x001f }
        if (r0 != 0) goto L_0x001b;
    L_0x0007:
        r0 = r3.oS;	 Catch:{ all -> 0x001f }
        r2 = -2;
        if (r0 != r2) goto L_0x001b;
    L_0x000c:
        r0 = r3.li;	 Catch:{ InterruptedException -> 0x0012 }
        r0.wait();	 Catch:{ InterruptedException -> 0x0012 }
        goto L_0x0003;
    L_0x0012:
        r0 = move-exception;
        r0 = "Ad request service was interrupted.";
        com.google.android.gms.internal.dw.m823z(r0);	 Catch:{ all -> 0x001f }
        r0 = 0;
        monitor-exit(r1);	 Catch:{ all -> 0x001f }
    L_0x001a:
        return r0;
    L_0x001b:
        r0 = r3.pJ;	 Catch:{ all -> 0x001f }
        monitor-exit(r1);	 Catch:{ all -> 0x001f }
        goto L_0x001a;
    L_0x001f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x001f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.de.bj():java.lang.String");
    }

    public int getErrorCode() {
        int i;
        synchronized (this.li) {
            i = this.oS;
        }
        return i;
    }
}
