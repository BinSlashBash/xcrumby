/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.plus;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

public interface Account {
    public void clearDefaultAccount(GoogleApiClient var1);

    public String getAccountName(GoogleApiClient var1);

    public PendingResult<Status> revokeAccessAndDisconnect(GoogleApiClient var1);
}

