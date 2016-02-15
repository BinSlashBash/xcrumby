/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.IntentSender
 *  android.os.ParcelFileDescriptor
 *  android.os.RemoteException
 */
package com.google.android.gms.drive;

import android.content.IntentSender;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.a;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.internal.CreateFileIntentSenderRequest;
import com.google.android.gms.drive.internal.n;
import com.google.android.gms.drive.internal.u;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.fq;
import java.io.IOException;

public class CreateFileActivityBuilder {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
    private Contents EA;
    private String EB;
    private DriveId EC;
    private MetadataChangeSet Ez;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IntentSender build(GoogleApiClient object) {
        fq.b(this.Ez, (Object)"Must provide initial metadata to CreateFileActivityBuilder.");
        fq.b(this.EA, (Object)"Must provide initial contents to CreateFileActivityBuilder.");
        try {
            this.EA.getParcelFileDescriptor().close();
        }
        catch (IOException var2_3) {}
        this.EA.close();
        fq.a(object.isConnected(), "Client must be connected");
        object = ((n)((Object)object.a(Drive.wx))).fE();
        try {
            return object.a(new CreateFileIntentSenderRequest(this.Ez.fD(), this.EA.fA(), this.EB, this.EC));
        }
        catch (RemoteException var1_2) {
            throw new RuntimeException("Unable to connect Drive Play Service", (Throwable)var1_2);
        }
    }

    public CreateFileActivityBuilder setActivityStartFolder(DriveId driveId) {
        this.EC = fq.f(driveId);
        return this;
    }

    public CreateFileActivityBuilder setActivityTitle(String string2) {
        this.EB = fq.f(string2);
        return this;
    }

    public CreateFileActivityBuilder setInitialContents(Contents contents) {
        this.EA = fq.f(contents);
        return this;
    }

    public CreateFileActivityBuilder setInitialMetadata(MetadataChangeSet metadataChangeSet) {
        this.Ez = fq.f(metadataChangeSet);
        return this;
    }
}

