/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.auth;

import android.content.Intent;
import com.google.android.gms.auth.UserRecoverableAuthException;

public class GooglePlayServicesAvailabilityException
extends UserRecoverableAuthException {
    private final int wQ;

    GooglePlayServicesAvailabilityException(int n2, String string2, Intent intent) {
        super(string2, intent);
        this.wQ = n2;
    }

    public int getConnectionStatusCode() {
        return this.wQ;
    }
}

