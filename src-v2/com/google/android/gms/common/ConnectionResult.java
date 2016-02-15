/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 */
package com.google.android.gms.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import com.google.android.gms.internal.fo;

public final class ConnectionResult {
    public static final int API_UNAVAILABLE = 16;
    public static final ConnectionResult Ag = new ConnectionResult(0, null);
    public static final int CANCELED = 13;
    public static final int DATE_INVALID = 12;
    public static final int DEVELOPER_ERROR = 10;
    public static final int DRIVE_EXTERNAL_STORAGE_REQUIRED = 1500;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPTED = 15;
    public static final int INVALID_ACCOUNT = 5;
    public static final int LICENSE_CHECK_FAILED = 11;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;
    public static final int SERVICE_DISABLED = 3;
    public static final int SERVICE_INVALID = 9;
    public static final int SERVICE_MISSING = 1;
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final int TIMEOUT = 14;
    private final int Ah;
    private final PendingIntent mPendingIntent;

    public ConnectionResult(int n2, PendingIntent pendingIntent) {
        this.Ah = n2;
        this.mPendingIntent = pendingIntent;
    }

    private String dW() {
        switch (this.Ah) {
            default: {
                return "unknown status code " + this.Ah;
            }
            case 0: {
                return "SUCCESS";
            }
            case 1: {
                return "SERVICE_MISSING";
            }
            case 2: {
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            }
            case 3: {
                return "SERVICE_DISABLED";
            }
            case 4: {
                return "SIGN_IN_REQUIRED";
            }
            case 5: {
                return "INVALID_ACCOUNT";
            }
            case 6: {
                return "RESOLUTION_REQUIRED";
            }
            case 7: {
                return "NETWORK_ERROR";
            }
            case 8: {
                return "INTERNAL_ERROR";
            }
            case 9: {
                return "SERVICE_INVALID";
            }
            case 10: {
                return "DEVELOPER_ERROR";
            }
            case 11: {
                return "LICENSE_CHECK_FAILED";
            }
            case 13: {
                return "CANCELED";
            }
            case 14: {
                return "TIMEOUT";
            }
            case 15: 
        }
        return "INTERRUPTED";
    }

    public int getErrorCode() {
        return this.Ah;
    }

    public PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    public boolean hasResolution() {
        if (this.Ah != 0 && this.mPendingIntent != null) {
            return true;
        }
        return false;
    }

    public boolean isSuccess() {
        if (this.Ah == 0) {
            return true;
        }
        return false;
    }

    public void startResolutionForResult(Activity activity, int n2) throws IntentSender.SendIntentException {
        if (!this.hasResolution()) {
            return;
        }
        activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), n2, null, 0, 0, 0);
    }

    public String toString() {
        return fo.e(this).a("statusCode", this.dW()).a("resolution", (Object)this.mPendingIntent).toString();
    }
}

