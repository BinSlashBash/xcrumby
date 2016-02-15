/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.common;

import android.content.Intent;
import com.google.android.gms.common.UserRecoverableException;

public class GooglePlayServicesRepairableException
extends UserRecoverableException {
    private final int wQ;

    GooglePlayServicesRepairableException(int n2, String string2, Intent intent) {
        super(string2, intent);
        this.wQ = n2;
    }

    public int getConnectionStatusCode() {
        return this.wQ;
    }
}

