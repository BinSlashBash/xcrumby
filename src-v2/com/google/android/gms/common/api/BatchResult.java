/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.BatchResultToken;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.fq;

public final class BatchResult
implements Result {
    private final PendingResult<?>[] AP;
    private final Status wJ;

    BatchResult(Status status, PendingResult<?>[] arrpendingResult) {
        this.wJ = status;
        this.AP = arrpendingResult;
    }

    @Override
    public Status getStatus() {
        return this.wJ;
    }

    /*
     * Enabled aggressive block sorting
     */
    public <R extends Result> R take(BatchResultToken<R> batchResultToken) {
        boolean bl2 = batchResultToken.mId < this.AP.length;
        fq.b(bl2, (Object)"The result token does not belong to this batch");
        PendingResult pendingResult = this.AP[batchResultToken.mId];
        this.AP[batchResultToken.mId] = null;
        return (R)pendingResult.await();
    }
}

