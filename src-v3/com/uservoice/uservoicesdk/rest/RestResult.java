package com.uservoice.uservoicesdk.rest;

import org.json.JSONException;
import org.json.JSONObject;

public class RestResult {
    private Exception exception;
    private JSONObject object;
    private int statusCode;

    public RestResult(int statusCode, JSONObject object) {
        this.statusCode = statusCode;
        this.object = object;
    }

    public RestResult(Exception exception) {
        this.exception = exception;
    }

    public RestResult(Exception exception, int statusCode, JSONObject object) {
        this.exception = exception;
        this.statusCode = statusCode;
        this.object = object;
    }

    public boolean isError() {
        return this.exception != null || this.statusCode > 400;
    }

    public JSONObject getObject() {
        return this.object;
    }

    public Exception getException() {
        return this.exception;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getMessage() {
        String str = "%s -- %s";
        Object[] objArr = new Object[2];
        objArr[0] = this.exception == null ? String.valueOf(this.statusCode) : this.exception.getMessage();
        objArr[1] = this.object;
        return String.format(str, objArr);
    }

    public String getType() {
        try {
            return this.object.getJSONObject("errors").getString("type");
        } catch (JSONException e) {
            return null;
        }
    }
}
