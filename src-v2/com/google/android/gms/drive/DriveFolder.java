/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Query;

public interface DriveFolder
extends DriveResource {
    public static final String MIME_TYPE = "application/vnd.google-apps.folder";

    public PendingResult<DriveFileResult> createFile(GoogleApiClient var1, MetadataChangeSet var2, Contents var3);

    public PendingResult<DriveFolderResult> createFolder(GoogleApiClient var1, MetadataChangeSet var2);

    public PendingResult<DriveApi.MetadataBufferResult> listChildren(GoogleApiClient var1);

    public PendingResult<DriveApi.MetadataBufferResult> queryChildren(GoogleApiClient var1, Query var2);

    public static interface DriveFileResult
    extends Result {
        public DriveFile getDriveFile();
    }

    public static interface DriveFolderResult
    extends Result {
        public DriveFolder getDriveFolder();
    }

}

