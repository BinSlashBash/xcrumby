/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.rest;

import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class RestTaskCallback {
    private final Callback<?> callback;

    public RestTaskCallback(Callback<?> callback) {
        this.callback = callback;
    }

    public abstract void onComplete(JSONObject var1) throws JSONException;

    public void onError(RestResult restResult) {
        this.callback.onError(restResult);
    }
}

