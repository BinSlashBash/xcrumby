package com.google.android.gms.drive;

import android.content.IntentSender;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.internal.C1308n;
import com.google.android.gms.drive.internal.CreateFileIntentSenderRequest;
import com.google.android.gms.internal.fq;
import java.io.IOException;

public class CreateFileActivityBuilder {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
    private Contents EA;
    private String EB;
    private DriveId EC;
    private MetadataChangeSet Ez;

    public IntentSender build(GoogleApiClient apiClient) {
        fq.m982b(this.Ez, (Object) "Must provide initial metadata to CreateFileActivityBuilder.");
        fq.m982b(this.EA, (Object) "Must provide initial contents to CreateFileActivityBuilder.");
        try {
            this.EA.getParcelFileDescriptor().close();
        } catch (IOException e) {
        }
        this.EA.close();
        fq.m980a(apiClient.isConnected(), "Client must be connected");
        try {
            return ((C1308n) apiClient.m123a(Drive.wx)).fE().m294a(new CreateFileIntentSenderRequest(this.Ez.fD(), this.EA.fA(), this.EB, this.EC));
        } catch (Throwable e2) {
            throw new RuntimeException("Unable to connect Drive Play Service", e2);
        }
    }

    public CreateFileActivityBuilder setActivityStartFolder(DriveId folder) {
        this.EC = (DriveId) fq.m985f(folder);
        return this;
    }

    public CreateFileActivityBuilder setActivityTitle(String title) {
        this.EB = (String) fq.m985f(title);
        return this;
    }

    public CreateFileActivityBuilder setInitialContents(Contents contents) {
        this.EA = (Contents) fq.m985f(contents);
        return this;
    }

    public CreateFileActivityBuilder setInitialMetadata(MetadataChangeSet metadataChangeSet) {
        this.Ez = (MetadataChangeSet) fq.m985f(metadataChangeSet);
        return this;
    }
}
