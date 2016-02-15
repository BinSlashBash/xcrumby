/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.BatchResult;
import com.google.android.gms.common.api.BatchResultToken;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import java.util.ArrayList;
import java.util.List;

public final class Batch
extends a.a<BatchResult> {
    private int AM;
    private boolean AN;
    private boolean AO;
    private final PendingResult<?>[] AP;
    private final Object li = new Object();

    private Batch(List<PendingResult<?>> list, Looper object) {
        super(new a.c((Looper)object));
        this.AM = list.size();
        this.AP = new PendingResult[this.AM];
        for (int i2 = 0; i2 < list.size(); ++i2) {
            this.AP[i2] = object = list.get(i2);
            object.a(new PendingResult.a(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void l(Status status) {
                    Object object = Batch.this.li;
                    synchronized (object) {
                        if (Batch.this.isCanceled()) {
                            return;
                        }
                        if (status.isCanceled()) {
                            Batch.this.AO = true;
                        } else if (!status.isSuccess()) {
                            Batch.this.AN = true;
                        }
                        Batch.b(Batch.this);
                        if (Batch.this.AM == 0) {
                            if (Batch.this.AO) {
                                Batch.this.cancel();
                            } else {
                                status = Batch.this.AN ? new Status(13) : Status.Bv;
                                Batch.this.a(new BatchResult(status, Batch.this.AP));
                            }
                        }
                        return;
                    }
                }
            });
        }
    }

    static /* synthetic */ int b(Batch batch) {
        int n2 = batch.AM;
        batch.AM = n2 - 1;
        return n2;
    }

    @Override
    public void cancel() {
        super.cancel();
        PendingResult<?>[] arrpendingResult = this.AP;
        int n2 = arrpendingResult.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrpendingResult[i2].cancel();
        }
    }

    public BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.AP);
    }

    @Override
    public /* synthetic */ Result d(Status status) {
        return this.createFailedResult(status);
    }

    public static final class Builder {
        private List<PendingResult<?>> AR = new ArrayList();
        private Looper AS;

        public Builder(GoogleApiClient googleApiClient) {
            this.AS = googleApiClient.getLooper();
        }

        public <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken batchResultToken = new BatchResultToken(this.AR.size());
            this.AR.add(pendingResult);
            return batchResultToken;
        }

        public Batch build() {
            return new Batch(this.AR, this.AS);
        }
    }

}

