/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Parcel
 */
package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.StatusCreator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;

public final class Status
implements Result,
SafeParcelable {
    public static final Status Bv = new Status(0);
    public static final Status Bw = new Status(14);
    public static final Status Bx = new Status(8);
    public static final Status By = new Status(15);
    public static final Status Bz = new Status(16);
    public static final StatusCreator CREATOR = new StatusCreator();
    private final int Ah;
    private final String BA;
    private final PendingIntent mPendingIntent;
    private final int xH;

    public Status(int n2) {
        this(1, n2, null, null);
    }

    Status(int n2, int n3, String string2, PendingIntent pendingIntent) {
        this.xH = n2;
        this.Ah = n3;
        this.BA = string2;
        this.mPendingIntent = pendingIntent;
    }

    public Status(int n2, String string2, PendingIntent pendingIntent) {
        this(1, n2, string2, pendingIntent);
    }

    private String dW() {
        if (this.BA != null) {
            return this.BA;
        }
        return CommonStatusCodes.getStatusCodeString(this.Ah);
    }

    public int describeContents() {
        return 0;
    }

    PendingIntent eo() {
        return this.mPendingIntent;
    }

    String ep() {
        return this.BA;
    }

    @Deprecated
    public ConnectionResult eq() {
        return new ConnectionResult(this.Ah, this.mPendingIntent);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (!(object instanceof Status)) {
            return false;
        }
        object = (Status)object;
        if (this.xH != object.xH) return false;
        if (this.Ah != object.Ah) return false;
        if (!fo.equal(this.BA, object.BA)) return false;
        if (!fo.equal((Object)this.mPendingIntent, (Object)object.mPendingIntent)) return false;
        return true;
    }

    public PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    @Override
    public Status getStatus() {
        return this;
    }

    public int getStatusCode() {
        return this.Ah;
    }

    int getVersionCode() {
        return this.xH;
    }

    public boolean hasResolution() {
        if (this.mPendingIntent != null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return fo.hashCode(new Object[]{this.xH, this.Ah, this.BA, this.mPendingIntent});
    }

    public boolean isCanceled() {
        if (this.Ah == 16) {
            return true;
        }
        return false;
    }

    public boolean isInterrupted() {
        if (this.Ah == 14) {
            return true;
        }
        return false;
    }

    public boolean isSuccess() {
        if (this.Ah <= 0) {
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

    public void writeToParcel(Parcel parcel, int n2) {
        StatusCreator.a(this, parcel, n2);
    }
}

