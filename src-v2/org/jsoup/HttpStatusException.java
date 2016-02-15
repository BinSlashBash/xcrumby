/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup;

import java.io.IOException;

public class HttpStatusException
extends IOException {
    private int statusCode;
    private String url;

    public HttpStatusException(String string2, int n2, String string3) {
        super(string2);
        this.statusCode = n2;
        this.url = string3;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public String toString() {
        return super.toString() + ". Status=" + this.statusCode + ", URL=" + this.url;
    }
}

