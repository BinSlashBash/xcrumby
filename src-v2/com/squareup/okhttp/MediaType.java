/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MediaType {
    private static final Pattern PARAMETER;
    private static final String QUOTED = "\"([^\"]*)\"";
    private static final String TOKEN = "([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)";
    private static final Pattern TYPE_SUBTYPE;
    private final String charset;
    private final String mediaType;
    private final String subtype;
    private final String type;

    static {
        TYPE_SUBTYPE = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
        PARAMETER = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
    }

    private MediaType(String string2, String string3, String string4, String string5) {
        this.mediaType = string2;
        this.type = string3;
        this.subtype = string4;
        this.charset = string5;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static MediaType parse(String string2) {
        Object object = TYPE_SUBTYPE.matcher(string2);
        if (object.lookingAt()) {
            String string3 = object.group(1).toLowerCase(Locale.US);
            String string4 = object.group(2).toLowerCase(Locale.US);
            Object object2 = null;
            Matcher matcher = PARAMETER.matcher(string2);
            int n2 = object.end();
            do {
                if (n2 >= string2.length()) {
                    return new MediaType(string2, string3, string4, (String)object2);
                }
                matcher.region(n2, string2.length());
                if (!matcher.lookingAt()) break;
                String string5 = matcher.group(1);
                object = object2;
                if (string5 != null) {
                    if (!string5.equalsIgnoreCase("charset")) {
                        object = object2;
                    } else {
                        if (object2 != null) {
                            throw new IllegalArgumentException("Multiple charsets: " + string2);
                        }
                        object2 = matcher.group(2) != null ? matcher.group(2) : matcher.group(3);
                        object = object2;
                    }
                }
                n2 = matcher.end();
                object2 = object;
            } while (true);
        }
        return null;
    }

    public Charset charset() {
        if (this.charset != null) {
            return Charset.forName(this.charset);
        }
        return null;
    }

    public Charset charset(Charset charset) {
        if (this.charset != null) {
            charset = Charset.forName(this.charset);
        }
        return charset;
    }

    public boolean equals(Object object) {
        if (object instanceof MediaType && ((MediaType)object).mediaType.equals(this.mediaType)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.mediaType.hashCode();
    }

    public String subtype() {
        return this.subtype;
    }

    public String toString() {
        return this.mediaType;
    }

    public String type() {
        return this.type;
    }
}

