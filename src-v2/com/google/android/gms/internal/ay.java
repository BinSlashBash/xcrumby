/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.az;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dz;
import java.util.Map;

public final class ay
implements bb {
    private final az mF;

    public ay(az az2) {
        this.mF = az2;
    }

    @Override
    public void b(dz object, Map<String, String> map) {
        object = map.get("name");
        if (object == null) {
            dw.z("App event with no name parameter.");
            return;
        }
        this.mF.onAppEvent((String)object, map.get("info"));
    }
}

