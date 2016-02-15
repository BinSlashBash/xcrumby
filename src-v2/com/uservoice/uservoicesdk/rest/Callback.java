/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.rest;

import com.uservoice.uservoicesdk.rest.RestResult;

public abstract class Callback<T> {
    public abstract void onError(RestResult var1);

    public abstract void onModel(T var1);
}

