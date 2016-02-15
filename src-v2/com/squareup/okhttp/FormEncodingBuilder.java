/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.internal.Util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public final class FormEncodingBuilder {
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");
    private final StringBuilder content = new StringBuilder();

    public FormEncodingBuilder add(String string2, String string3) {
        if (this.content.length() > 0) {
            this.content.append('&');
        }
        try {
            this.content.append(URLEncoder.encode(string2, "UTF-8")).append('=').append(URLEncoder.encode(string3, "UTF-8"));
            return this;
        }
        catch (UnsupportedEncodingException var1_2) {
            throw new AssertionError(var1_2);
        }
    }

    public RequestBody build() {
        if (this.content.length() == 0) {
            throw new IllegalStateException("Form encoded body must have at least one part.");
        }
        byte[] arrby = this.content.toString().getBytes(Util.UTF_8);
        return RequestBody.create(CONTENT_TYPE, arrby);
    }
}

