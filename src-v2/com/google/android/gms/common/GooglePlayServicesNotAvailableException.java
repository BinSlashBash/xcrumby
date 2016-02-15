/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.common;

public final class GooglePlayServicesNotAvailableException
extends Exception {
    public final int errorCode;

    public GooglePlayServicesNotAvailableException(int n2) {
        this.errorCode = n2;
    }
}

