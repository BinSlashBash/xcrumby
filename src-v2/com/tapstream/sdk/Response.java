/*
 * Decompiled with CFR 0_110.
 */
package com.tapstream.sdk;

public class Response {
    public String data;
    public String message;
    public int status;

    public Response(int n2, String string2, String string3) {
        this.status = n2;
        this.message = string2;
        this.data = string3;
    }
}

