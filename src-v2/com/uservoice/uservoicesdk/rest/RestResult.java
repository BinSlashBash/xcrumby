/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.rest;

import org.json.JSONException;
import org.json.JSONObject;

public class RestResult {
    private Exception exception;
    private JSONObject object;
    private int statusCode;

    public RestResult(int n2, JSONObject jSONObject) {
        this.statusCode = n2;
        this.object = jSONObject;
    }

    public RestResult(Exception exception) {
        this.exception = exception;
    }

    public RestResult(Exception exception, int n2, JSONObject jSONObject) {
        this.exception = exception;
        this.statusCode = n2;
        this.object = jSONObject;
    }

    public Exception getException() {
        return this.exception;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getMessage() {
        String string2;
        if (this.exception == null) {
            string2 = String.valueOf(this.statusCode);
            do {
                return String.format("%s -- %s", string2, this.object);
                break;
            } while (true);
        }
        string2 = this.exception.getMessage();
        return String.format("%s -- %s", string2, this.object);
    }

    public JSONObject getObject() {
        return this.object;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getType() {
        try {
            String string2 = this.object.getJSONObject("errors").getString("type");
            return string2;
        }
        catch (JSONException var1_2) {
            return null;
        }
    }

    public boolean isError() {
        if (this.exception != null || this.statusCode > 400) {
            return true;
        }
        return false;
    }
}

