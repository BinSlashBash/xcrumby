package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import oauth.signpost.OAuth;
import org.apache.commons.codec.binary.Hex;

public final class FormEncodingBuilder {
    private static final MediaType CONTENT_TYPE;
    private final StringBuilder content;

    public FormEncodingBuilder() {
        this.content = new StringBuilder();
    }

    static {
        CONTENT_TYPE = MediaType.parse(OAuth.FORM_ENCODED);
    }

    public FormEncodingBuilder add(String name, String value) {
        if (this.content.length() > 0) {
            this.content.append('&');
        }
        try {
            this.content.append(URLEncoder.encode(name, Hex.DEFAULT_CHARSET_NAME)).append('=').append(URLEncoder.encode(value, Hex.DEFAULT_CHARSET_NAME));
            return this;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public RequestBody build() {
        if (this.content.length() == 0) {
            throw new IllegalStateException("Form encoded body must have at least one part.");
        }
        return RequestBody.create(CONTENT_TYPE, this.content.toString().getBytes(Util.UTF_8));
    }
}
