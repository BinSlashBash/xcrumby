package com.google.android.gms.common;

import android.content.Intent;

public class GooglePlayServicesRepairableException extends UserRecoverableException {
    private final int wQ;

    GooglePlayServicesRepairableException(int connectionStatusCode, String msg, Intent intent) {
        super(msg, intent);
        this.wQ = connectionStatusCode;
    }

    public int getConnectionStatusCode() {
        return this.wQ;
    }
}
