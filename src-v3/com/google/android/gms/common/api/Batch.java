package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.C0245a.C0243c;
import com.google.android.gms.common.api.C0245a.C0789a;
import com.google.android.gms.common.api.PendingResult.C0242a;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends C0789a<BatchResult> {
    private int AM;
    private boolean AN;
    private boolean AO;
    private final PendingResult<?>[] AP;
    private final Object li;

    public static final class Builder {
        private List<PendingResult<?>> AR;
        private Looper AS;

        public Builder(GoogleApiClient googleApiClient) {
            this.AR = new ArrayList();
            this.AS = googleApiClient.getLooper();
        }

        public <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken(this.AR.size());
            this.AR.add(pendingResult);
            return batchResultToken;
        }

        public Batch build() {
            return new Batch(this.AS, null);
        }
    }

    /* renamed from: com.google.android.gms.common.api.Batch.1 */
    class C07881 implements C0242a {
        final /* synthetic */ Batch AQ;

        C07881(Batch batch) {
            this.AQ = batch;
        }

        public void m1654l(Status status) {
            synchronized (this.AQ.li) {
                if (this.AQ.isCanceled()) {
                    return;
                }
                if (status.isCanceled()) {
                    this.AQ.AO = true;
                } else if (!status.isSuccess()) {
                    this.AQ.AN = true;
                }
                this.AQ.AM = this.AQ.AM - 1;
                if (this.AQ.AM == 0) {
                    if (this.AQ.AO) {
                        super.cancel();
                    } else {
                        this.AQ.m1659a(new BatchResult(this.AQ.AN ? new Status(13) : Status.Bv, this.AQ.AP));
                    }
                }
            }
        }
    }

    private Batch(List<PendingResult<?>> pendingResultList, Looper looper) {
        super(new C0243c(looper));
        this.li = new Object();
        this.AM = pendingResultList.size();
        this.AP = new PendingResult[this.AM];
        for (int i = 0; i < pendingResultList.size(); i++) {
            PendingResult pendingResult = (PendingResult) pendingResultList.get(i);
            this.AP[i] = pendingResult;
            pendingResult.m127a(new C07881(this));
        }
    }

    public void cancel() {
        super.cancel();
        for (PendingResult cancel : this.AP) {
            cancel.cancel();
        }
    }

    public BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.AP);
    }

    public /* synthetic */ Result m2646d(Status status) {
        return createFailedResult(status);
    }
}
