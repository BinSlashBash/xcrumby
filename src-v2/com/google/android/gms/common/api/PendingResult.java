/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import java.util.concurrent.TimeUnit;

public interface PendingResult<R extends Result> {
    public void a(a var1);

    public R await();

    public R await(long var1, TimeUnit var3);

    public void cancel();

    public boolean isCanceled();

    public void setResultCallback(ResultCallback<R> var1);

    public void setResultCallback(ResultCallback<R> var1, long var2, TimeUnit var4);

    public static interface a {
        public void l(Status var1);
    }

}

