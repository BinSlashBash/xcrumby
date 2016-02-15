/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.ads.purchase;

public interface InAppPurchase {
    public static final int RESOLUTION_CANCELED = 2;
    public static final int RESOLUTION_FAILURE = 0;
    public static final int RESOLUTION_INVALID_PRODUCT = 3;
    public static final int RESOLUTION_SUCCESS = 1;

    public String getProductId();

    public void recordPlayBillingResolution(int var1);

    public void recordResolution(int var1);
}

