package com.uservoice.uservoicesdk.rest;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class RestTaskCallback {
    private final Callback<?> callback;

    public abstract void onComplete(JSONObject jSONObject) throws JSONException;

    public RestTaskCallback(Callback<?> callback) {
        this.callback = callback;
    }

    public void onError(RestResult result) {
        this.callback.onError(result);
    }
}
