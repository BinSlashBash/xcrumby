/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.IntentSender
 *  android.os.RemoteException
 */
package com.google.android.gms.drive;

import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.a;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.OpenFileIntentSenderRequest;
import com.google.android.gms.drive.internal.n;
import com.google.android.gms.drive.internal.u;
import com.google.android.gms.internal.fq;

public class OpenFileActivityBuilder {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
    private String EB;
    private DriveId EC;
    private String[] EQ;

    public IntentSender build(GoogleApiClient object) {
        fq.b(this.EQ, (Object)"setMimeType(String[]) must be called on this builder before calling build()");
        fq.a(object.isConnected(), "Client must be connected");
        object = ((n)((Object)object.a(Drive.wx))).fE();
        try {
            object = object.a(new OpenFileIntentSenderRequest(this.EB, this.EQ, this.EC));
            return object;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeException("Unable to connect Drive Play Service", (Throwable)var1_2);
        }
    }

    public OpenFileActivityBuilder setActivityStartFolder(DriveId driveId) {
        this.EC = fq.f(driveId);
        return this;
    }

    public OpenFileActivityBuilder setActivityTitle(String string2) {
        this.EB = fq.f(string2);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public OpenFileActivityBuilder setMimeType(String[] arrstring) {
        boolean bl2 = arrstring != null && arrstring.length > 0;
        fq.b(bl2, (Object)"mimeTypes may not be null and must contain at least one value");
        this.EQ = arrstring;
        return this;
    }
}

