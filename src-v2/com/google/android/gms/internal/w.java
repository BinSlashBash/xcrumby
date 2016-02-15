/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.ab;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.y;
import java.util.HashMap;
import java.util.Map;

class w
implements y {
    private dz kU;

    public w(dz dz2) {
        this.kU = dz2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a(ab object, boolean bl2) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        object = bl2 ? "1" : "0";
        hashMap.put("isVisible", object);
        this.kU.a("onAdVisibilityChanged", hashMap);
    }
}

