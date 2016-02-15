/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 */
package com.google.android.gms.panorama;

import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;

public interface PanoramaApi {
    public PendingResult<PanoramaResult> loadPanoramaInfo(GoogleApiClient var1, Uri var2);

    public PendingResult<PanoramaResult> loadPanoramaInfoAndGrantAccess(GoogleApiClient var1, Uri var2);

    public static interface PanoramaResult
    extends Result {
        public Intent getViewerIntent();
    }

    public static interface a
    extends PanoramaResult {
    }

}

