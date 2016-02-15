/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.ads.purchase.InAppPurchase;
import com.google.android.gms.internal.cn;
import com.google.android.gms.internal.dw;

public class cq
implements InAppPurchase {
    private final cn oF;

    public cq(cn cn2) {
        this.oF = cn2;
    }

    @Override
    public String getProductId() {
        try {
            String string2 = this.oF.getProductId();
            return string2;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not forward getProductId to InAppPurchase", (Throwable)var1_2);
            return null;
        }
    }

    @Override
    public void recordPlayBillingResolution(int n2) {
        try {
            this.oF.recordPlayBillingResolution(n2);
            return;
        }
        catch (RemoteException var2_2) {
            dw.c("Could not forward recordPlayBillingResolution to InAppPurchase", (Throwable)var2_2);
            return;
        }
    }

    @Override
    public void recordResolution(int n2) {
        try {
            this.oF.recordResolution(n2);
            return;
        }
        catch (RemoteException var2_2) {
            dw.c("Could not forward recordResolution to InAppPurchase", (Throwable)var2_2);
            return;
        }
    }
}

