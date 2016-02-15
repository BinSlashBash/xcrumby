/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup;

import java.io.IOException;

public class UnsupportedMimeTypeException
extends IOException {
    private String mimeType;
    private String url;

    public UnsupportedMimeTypeException(String string2, String string3, String string4) {
        super(string2);
        this.mimeType = string3;
        this.url = string4;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public String toString() {
        return super.toString() + ". Mimetype=" + this.mimeType + ", URL=" + this.url;
    }
}

