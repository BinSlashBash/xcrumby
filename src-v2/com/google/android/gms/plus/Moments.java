/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.plus;

import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;

public interface Moments {
    public PendingResult<LoadMomentsResult> load(GoogleApiClient var1);

    public PendingResult<LoadMomentsResult> load(GoogleApiClient var1, int var2, String var3, Uri var4, String var5, String var6);

    public PendingResult<Status> remove(GoogleApiClient var1, String var2);

    public PendingResult<Status> write(GoogleApiClient var1, Moment var2);

    public static interface LoadMomentsResult
    extends Releasable,
    Result {
        public MomentBuffer getMomentBuffer();

        public String getNextPageToken();

        public String getUpdated();
    }

}

