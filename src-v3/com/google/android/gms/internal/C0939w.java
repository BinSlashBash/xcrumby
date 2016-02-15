package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.android.gms.internal.w */
class C0939w implements C0426y {
    private dz kU;

    public C0939w(dz dzVar) {
        this.kU = dzVar;
    }

    public void m2336a(ab abVar, boolean z) {
        Map hashMap = new HashMap();
        hashMap.put("isVisible", z ? "1" : "0");
        this.kU.m832a("onAdVisibilityChanged", hashMap);
    }
}
