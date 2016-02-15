/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.ads.mediation.admob;

import android.os.Bundle;
import com.google.ads.mediation.NetworkExtras;

@Deprecated
public final class AdMobExtras
implements NetworkExtras {
    private final Bundle rP;

    /*
     * Enabled aggressive block sorting
     */
    public AdMobExtras(Bundle bundle) {
        bundle = bundle != null ? new Bundle(bundle) : null;
        this.rP = bundle;
    }

    public Bundle getExtras() {
        return this.rP;
    }
}

