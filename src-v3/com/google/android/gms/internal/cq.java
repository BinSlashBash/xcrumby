package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.InAppPurchase;

public class cq implements InAppPurchase {
    private final cn oF;

    public cq(cn cnVar) {
        this.oF = cnVar;
    }

    public String getProductId() {
        try {
            return this.oF.getProductId();
        } catch (Throwable e) {
            dw.m817c("Could not forward getProductId to InAppPurchase", e);
            return null;
        }
    }

    public void recordPlayBillingResolution(int billingResponseCode) {
        try {
            this.oF.recordPlayBillingResolution(billingResponseCode);
        } catch (Throwable e) {
            dw.m817c("Could not forward recordPlayBillingResolution to InAppPurchase", e);
        }
    }

    public void recordResolution(int resolution) {
        try {
            this.oF.recordResolution(resolution);
        } catch (Throwable e) {
            dw.m817c("Could not forward recordResolution to InAppPurchase", e);
        }
    }
}
