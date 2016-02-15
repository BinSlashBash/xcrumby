/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.MetadataChangeSet;

public interface DriveFile
extends DriveResource {
    public static final int MODE_READ_ONLY = 268435456;
    public static final int MODE_READ_WRITE = 805306368;
    public static final int MODE_WRITE_ONLY = 536870912;

    public PendingResult<Status> commitAndCloseContents(GoogleApiClient var1, Contents var2);

    public PendingResult<Status> commitAndCloseContents(GoogleApiClient var1, Contents var2, MetadataChangeSet var3);

    public PendingResult<Status> discardContents(GoogleApiClient var1, Contents var2);

    public PendingResult<DriveApi.ContentsResult> openContents(GoogleApiClient var1, int var2, DownloadProgressListener var3);

    public static interface DownloadProgressListener {
        public void onProgress(long var1, long var3);
    }

}

